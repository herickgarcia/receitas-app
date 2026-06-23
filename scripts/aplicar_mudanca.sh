#!/bin/bash
# ============================================================
# aplicar_mudanca.sh
# Aplica as DUAS mudanças solicitadas durante a apresentação:
#  1. Alteração de rótulo (label) na tela de receitas
#  2. Nova tabela "categoria" no banco (migration Flyway V2)
#
# Versão CORRETA (sem quebra de qualidade).
# Use --quebrar para aplicar a versão proposital com problema
# de qualidade de código (demonstração do SonarQube falhando).
# ============================================================
set -e

cd "$(dirname "$0")/.."

APP=.
QUEBRAR=false
[ "$1" == "--quebrar" ] && QUEBRAR=true

echo "1) Alterando label em lista.html (Receitas -> Receitos)..."
sed -i 's/<h2>Receitas Cadastradas<\/h2>/<h2>Receitos Cadastrados<\/h2>/' \
  "$APP/src/main/resources/templates/receitas/lista.html"

echo "2) Adicionando migration V2 (tabela categoria)..."
cp src-changes/V2__cria_tabela_categoria.sql \
   "$APP/src/main/resources/db/migration/V2__cria_tabela_categoria.sql"

if [ "$QUEBRAR" = true ]; then
  echo "3) Adicionando Categoria.java -> VERSAO COM PROBLEMA DE QUALIDADE (proposital)"
  cp src-changes/Categoria_QUEBRADA.java \
     "$APP/src/main/java/com/receitas/app/model/Categoria.java"
else
  echo "3) Adicionando Categoria.java -> versao correta"
  cp src-changes/Categoria.java \
     "$APP/src/main/java/com/receitas/app/model/Categoria.java"
fi

cp src-changes/CategoriaRepository.java \
   "$APP/src/main/java/com/receitas/app/repository/CategoriaRepository.java"

echo "============================================"
echo " Mudanças aplicadas localmente (ainda não versionadas)."
echo " Próximo passo: git add . && git commit -m 'feat: novo label e tabela categoria' && git push"
echo "============================================"
