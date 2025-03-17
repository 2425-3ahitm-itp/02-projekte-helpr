#!/bin/bash

cd javafx/src/main/docker || exit

# Run trap to ensure docker compose down is executed when script is terminated
trap 'echo "Shutting down containers..."; docker compose down' EXIT INT TERM

# Start containers
docker compose up