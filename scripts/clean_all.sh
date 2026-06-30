#!/bin/bash
# ============================================================
# clean_all.sh
# Remove TODOS os containers, imagens e volumes do projeto,
# deixando a VM "pelada" para a apresentação.
# ============================================================
set -e

cd "$(dirname "$0")/../infra"

echo "Parando e removendo containers, redes e volumes..."
docker compose down -v --rmi all || true

# Remove imagens órfãs que possam ter ficado
docker image prune -af

echo "============================================"
echo " Estado atual da VM:"
echo "============================================"
docker ps -a
docker images
