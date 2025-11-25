#!/bin/bash

COMPOSE_FILE="docker-compose.yaml"
COMPOSE_UI_FILE="compose-ui.yaml"

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

  build)
      echo "🔨 Building UI image ..."
      docker-compose -f $COMPOSE_UI_FILE build

      echo "🚀 Running UI services ..."
      docker-compose -f $COMPOSE_UI_FILE up -d
      ;;

  *)
    echo "Usage: $0 {up|down|delete}"
    exit 1
    ;;
esac