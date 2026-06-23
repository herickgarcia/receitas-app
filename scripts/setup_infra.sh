#!/bin/bash
# ============================================================
# setup_infra.sh
# Cria TODA a infraestrutura do zero (Homologação + Produção +
# Jenkins + SonarQube) de forma automatizada.
#
# Pré-requisito: VM "pelada" (sem containers/imagens criadas).
# Uso: ./setup_infra.sh
# ============================================================
set -e

cd "$(dirname "$0")/.."

echo "============================================"
echo " Validando ambiente (deve estar vazio)..."
echo "============================================"
docker ps -a
docker images

echo "============================================"
echo " Subindo Homologação, Produção, Jenkins e SonarQube"
echo "============================================"
cd infra
docker compose up -d --build

echo "============================================"
echo " Aguardando serviços ficarem saudáveis..."
echo "============================================"
sleep 15
docker compose ps

echo "============================================"
echo " Ambientes criados:"
echo "   Homologação: http://localhost:8081"
echo "   Produção:    http://localhost:8082"
echo "   Jenkins:     http://localhost:8090"
echo "   SonarQube:   http://localhost:9000"
echo "============================================"
echo " Login padrão da aplicação: admin / admin123"
echo "============================================"
