#!/bin/bash

COMPOSE_FILE="docker-compose.yaml"

case "$1" in
  up)
    echo "🚀 Starting all services ..."
    docker-compose -f $COMPOSE_FILE up -d
    ;;

  down)
    echo "🛑 Stopping all services ..."
    docker-compose -f  $COMPOSE_FILE down -v
    ;;

  delete)
    echo "💥 Removing all services, networks, and volumes ..."
    docker-compose -f $COMPOSE_FILE down -v --remove-orphans
    ;;

  *)
    echo "Usage: $0 {up|down|delete}"
    exit 1
    ;;
esac