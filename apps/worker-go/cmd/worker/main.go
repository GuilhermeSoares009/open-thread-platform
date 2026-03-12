package main

import (
	"context"
	"log"
	"os"
	"os/signal"
	"syscall"
	"time"

	"github.com/guilhermesoares009/openthread/worker-go/internal/worker"
)

func main() {
	redisAddr := getEnv("REDIS_ADDR", "localhost:6379")
	streamKey := getEnv("EVENT_STREAM_KEY", "openthread.events")

	w := worker.New(redisAddr, streamKey)

	ctx, cancel := signal.NotifyContext(context.Background(), syscall.SIGINT, syscall.SIGTERM)
	defer cancel()

	log.Printf("worker starting redis=%s stream=%s", redisAddr, streamKey)
	if err := w.Run(ctx); err != nil {
		log.Printf("worker stopped with error: %v", err)
	}

	time.Sleep(500 * time.Millisecond)
	log.Print("worker stopped")
}

func getEnv(key, fallback string) string {
	value := os.Getenv(key)
	if value == "" {
		return fallback
	}
	return value
}
