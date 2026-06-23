#!/bin/bash
# ============================================================
# deploy_prod.sh
# Promove a imagem validada em Homologação (receitas-app:homolog)
# para Produção. Flyway aplica as migrations pendentes,
# preservando os dados existentes do banco de Produção.
# ============================================================
set -e

echo "Promovendo imagem homolog -> prod..."
docker tag receitas-app:homolog receitas-app:prod

echo "Atualizando container de Produção..."
docker stop receitas-app-prod || true
docker rm receitas-app-prod || true

docker run -d --name receitas-app-prod \
  --network prod-net \
  -p 8082:8080 \
  -e DB_HOST=receitas-db-prod \
  -e DB_PORT=5432 \
  -e DB_NAME=receitasdb \
  -e DB_USER=postgres \
  -e DB_PASS=postgres \
  receitas-app:prod

echo "============================================"
echo " Produção atualizada: http://localhost:8082"
echo " Flyway aplica as migrations pendentes preservando os dados."
echo "============================================"
