#! /usr/bin/bash
docker compose down
docker build . -f Dockerfile.dataset -t p2-dataset
docker build . -f Dockerfile.cache -t p2-cache 
#docker run -d -p 5000:5000 p2-dataset
docker compose up -d
echo "server running..."
