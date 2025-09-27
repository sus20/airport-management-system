#!/bin/bash

case "$1" in
  up)
    echo "🚀 Starting infra (Kafka) ..."
    docker-compose -f docker-compose.infra.yaml up -d

    echo "⏳ Waiting 10s for infra to initialize ..."
    sleep 10

    echo "🚀 Starting flight service ..."
    docker-compose -f docker-compose.flight.yaml up -d

    echo "🚀 Starting passenger service ..."
    docker-compose -f docker-compose.passenger.yaml up -d
    ;;

  down)
    echo "🛑 Stopping all services ..."
    docker-compose -f docker-compose.passenger.yaml -f docker-compose.flight.yaml -f docker-compose.infra.yaml down
    ;;

  delete)
    echo "💥 Removing all services, networks, and volumes ..."
    docker-compose -f docker-compose.passenger.yaml -f docker-compose.flight.yaml -f docker-compose.infra.yaml down -v --remove-orphans
    ;;

  *)
    echo "Usage: $0 {up|down|delete}"
    exit 1
    ;;
esac
