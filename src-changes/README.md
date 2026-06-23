# Mudanças encenadas para a apresentação (Tarefa Final - Gerência de Configuração)

Esta pasta contém os artefatos que devem ser aplicados **ao vivo, durante a apresentação**,
NÃO antes. O repositório principal deve permanecer no estado "antes da mudança"
até o momento da demonstração.

## Roteiro da apresentação (baseado na explicação do professor)

### Fase 0 - Preparação (antes da aula)
1. VM "pelada": `docker ps -a` vazio, nenhuma imagem.
2. Executar `scripts/setup_infra.sh` -> cria e sobe TODOS os containers (homolog, prod, jenkins, sonarqube).
3. Mostrar Homologação funcionando (login + cadastro de receita).
4. Mostrar Produção funcionando (login + cadastro de receita).

### Fase 1 - Mudança nº 1: alteração de código (label)
Em `src/main/resources/templates/receitas/lista.html`, trocar:

    <h2>Receitas Cadastradas</h2>

por:

    <h2>Receitos Cadastrados</h2>

(Mudança de 1 letra/rótulo, conforme pedido pelo professor)

### Fase 2 - Mudança nº 2: nova tabela no banco
1. Copiar `src-changes/V2__cria_tabela_categoria.sql` para
   `src/main/resources/db/migration/V2__cria_tabela_categoria.sql`
2. Copiar `src-changes/Categoria.java` para
   `src/main/java/com/receitas/app/model/Categoria.java`
3. Copiar `src-changes/CategoriaRepository.java` para
   `src/main/java/com/receitas/app/repository/CategoriaRepository.java`

### Fase 3 - QUEBRA PROPOSITAL de qualidade de código (1ª rodada)
Aplicar a alteração de `src-changes/Categoria_QUEBRADA.java` no lugar de `Categoria.java`
(chave/indentação fora do padrão). Fazer commit + push -> pipeline roda -> SonarQube
reprova por "code smell" -> pipeline marcado como FAILED.

### Fase 4 - Correção
Substituir pela versão correta `src-changes/Categoria.java`. Commit + push novamente.
Pipeline roda: testes (20) passam, SonarQube aprova, build gerado.

### Fase 5 - Atualizar Homologação
No Jenkins, clicar em "Build Now" no job `deploy-homologacao` (ou rodar
`scripts/deploy_homolog.sh`). Mostrar:
- Label alterado (Receitos Cadastrados)
- Nova tabela `categoria` via shell (`docker exec -it receitas-db-homolog psql ...`)
- Dados antigos preservados (10 receitas + usuário admin continuam lá)
- Produção AINDA não mudou (label antigo, sem tabela categoria)

### Fase 6 - Atualizar Produção
Rodar `scripts/deploy_prod.sh` (ou job Jenkins `deploy-producao`). Mostrar o mesmo
checklist da Fase 5, agora em Produção.
