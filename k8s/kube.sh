#!/bin/bash

if [ "$1" = "up" ]; then
  echo "🚀 Starting all Kubernetes services ..."
  kubectl apply -f namespace.yaml
  kubectl apply -f .
elif [ "$1" = "down" ]; then
  echo "🛑 Stopping all Kubernetes services ..."
  kubectl delete -f .
elif [ "$1" = "restart" ]; then
  echo "🔄 Restarting all Kubernetes services ..."
  kubectl delete -f .
  kubectl apply -f namespace.yaml
  kubectl apply -f .
else
  echo "Usage: ./kube.sh {up|down|restart}"
fi