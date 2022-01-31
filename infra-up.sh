echo "Starting postgres container.."
docker run --name payten-postgres \
  -e POSTGRES_USER=payten-user \
  -e POSTGRES_PASSWORD=payten-password \
  -d \
  -p 5432:5432 \
  postgres

echo "Starting redis container.."
docker run --name payten-redis \
  -d \
  -e ALLOW_EMPTY_PASSWORD=yes \
  -p 6379:6379 \
  bitnami/redis:latest