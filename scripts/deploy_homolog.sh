#!/bin/bash
# ============================================================
# deploy_homolog.sh
# Atualiza o ambiente de Homologação com a última imagem
# buildada (receitas-app:homolog). Flyway aplica as migrations
# pendentes automaticamente, preservando os dados existentes.
# ============================================================
set -e

cd "$(dirname "$0")/.."

echo "Buildando aplicação (testes + sonar já validados pela pipeline)..."
mvn -B clean package -DskipTests

echo "Construindo imagem Docker..."
docker build -t receitas-app:homolog .

echo "Atualizando container de Homologação..."
docker stop receitas-app-homolog || true
docker rm receitas-app-homolog || true

docker run -d --name receitas-app-homolog \
  --network homolog-net \
  -p 8081:8080 \
  -e DB_HOST=receitas-db-homolog \
  -e DB_PORT=5432 \
  -e DB_NAME=receitasdb \
  -e DB_USER=postgres \
  -e DB_PASS=postgres \
  receitas-app:homolog

echo "============================================"
echo " Homologação atualizada: http://localhost:8081"
echo " Flyway aplica as migrations pendentes (ex: V2__cria_tabela_categoria.sql)"
echo " automaticamente, preservando os dados existentes."
echo "============================================"
