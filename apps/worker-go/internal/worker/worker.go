package worker

import (
	"context"
	"encoding/json"
	"fmt"
	"log"
	"time"

	"github.com/redis/go-redis/v9"
)

type Worker struct {
	client    *redis.Client
	streamKey string
	lastID    string
}

type DomainEvent struct {
	Type    string                 `json:"type"`
	Payload map[string]interface{} `json:"payload"`
}

func New(redisAddr, streamKey string) *Worker {
	return &Worker{
		client: redis.NewClient(&redis.Options{
			Addr: redisAddr,
		}),
		streamKey: streamKey,
		lastID:    "$",
	}
}

func (w *Worker) Run(ctx context.Context) error {
	if err := w.client.Ping(ctx).Err(); err != nil {
		return fmt.Errorf("redis ping failed: %w", err)
	}

	for {
		select {
		case <-ctx.Done():
			return nil
		default:
		}

		streams, err := w.client.XRead(ctx, &redis.XReadArgs{
			Streams: []string{w.streamKey, w.lastID},
			Count:   25,
			Block:   5 * time.Second,
		}).Result()

		if err != nil {
			if err == redis.Nil {
				continue
			}
			if ctx.Err() != nil {
				return nil
			}
			log.Printf("xread failed: %v", err)
			time.Sleep(1 * time.Second)
			continue
		}

		for _, stream := range streams {
			for _, message := range stream.Messages {
				if err := w.handleMessage(message); err != nil {
					log.Printf("message %s failed: %v", message.ID, err)
					continue
				}
				w.lastID = message.ID
			}
		}
	}
}

func (w *Worker) handleMessage(message redis.XMessage) error {
	eventTypeRaw, ok := message.Values["type"]
	if !ok {
		return fmt.Errorf("missing event type")
	}

	payloadRaw, ok := message.Values["payload"]
	if !ok {
		return fmt.Errorf("missing payload")
	}

	eventType, ok := eventTypeRaw.(string)
	if !ok {
		return fmt.Errorf("invalid type field")
	}

	payloadString, ok := payloadRaw.(string)
	if !ok {
		return fmt.Errorf("invalid payload field")
	}

	payload := map[string]interface{}{}
	if err := json.Unmarshal([]byte(payloadString), &payload); err != nil {
		return fmt.Errorf("could not decode payload: %w", err)
	}

	log.Printf("event=%s id=%s payload=%v", eventType, message.ID, payload)

	if eventType == "vote.cast" {
		log.Printf("ranking projection hook triggered for message=%s", message.ID)
	}

	return nil
}
