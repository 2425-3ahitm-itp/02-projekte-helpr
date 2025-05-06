#!/bin/bash

cd javafx/src/main/docker || exit

# Start containers
echo "Shutting down Containers..."
docker compose down