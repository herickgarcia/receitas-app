#!/bin/bash
# ============================================================
# corrigir_qualidade.sh
# Corrige o problema de qualidade de código proposital,
# substituindo Categoria.java pela versão correta.
# Executar após a pipeline falhar no SonarQube.
# ============================================================
set -e

cd "$(dirname "$0")/.."

cp src-changes/Categoria.java \
   src/main/java/com/receitas/app/model/Categoria.java

echo "============================================"
echo " Categoria.java corrigido."
echo " Próximo passo: git add . && git commit -m 'fix: corrige problema de qualidade de codigo' && git push"
echo "============================================"
