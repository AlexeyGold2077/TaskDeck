clear &&
mvn clean package &&
docker compose down -v &&
docker compose build &&
docker compose up
