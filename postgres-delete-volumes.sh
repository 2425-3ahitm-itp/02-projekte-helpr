#!/bin/bash

VOLUMES=$(docker volume ls --format '{{.Name}}' | grep '_helpr_data$')

if [ -z "$VOLUMES" ]; then
    echo "No matching volumes found. Already cleaned up!"
    exit 0
fi

for VOL in $VOLUMES; do
    echo "Removing volume: $VOL"
    docker volume rm -f "$VOL"
done

echo "Cleanup complete."