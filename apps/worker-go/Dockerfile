FROM golang:1.22-alpine AS build
WORKDIR /app

COPY go.mod ./
RUN go mod download

COPY . .
RUN CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build -o /bin/worker ./cmd/worker

FROM alpine:3.20
WORKDIR /app

COPY --from=build /bin/worker /app/worker

ENTRYPOINT ["/app/worker"]
