# Registro de Receitas - Pipeline CI/CD (Gerência de Configuração de Software)

Aplicação web para gerenciamento de receitas (doces e salgados), desenvolvida com Java Spring Boot,
PostgreSQL e Thymeleaf, com pipeline completo de Integração, Homologação e Produção (Tarefa Final
da disciplina 4815207 - Gerência de Configuração de Software - 2026/A).

## Tecnologias

- Java 21 / Spring Boot 3.2
- Spring Security, Spring Data JPA, Thymeleaf
- PostgreSQL 16
- Flyway (versionamento de banco de dados)
- Docker / Docker Compose
- Jenkins (CI/CD)
- SonarQube (qualidade de código)
- JUnit 5 + Mockito + AssertJ + MockMvc (testes automatizados)

## Funcionalidades

- Login com autenticação via Spring Security
- Listagem de receitas com filtro por tipo (doce/salgado) e busca por nome
- CRUD completo de receitas (criar, visualizar, editar, excluir)

## Credenciais padrão

| Login | Senha    |
|-------|----------|
| admin | admin123 |

---

## Estrutura do repositório

```
.
├── src/                          # Código-fonte Spring Boot
│   ├── main/java/com/receitas/app/
│   ├── main/resources/
│   │   ├── templates/
│   │   └── db/migration/         # Migrations Flyway (V1, V2...)
│   └── test/java/...              # 22 testes JUnit5/Mockito/MockMvc
├── Dockerfile                     # build multi-stage da aplicação
├── pom.xml
├── infra/
│   └── docker-compose.yml         # 6 containers (homolog, prod, jenkins, sonarqube)
├── jenkins/
│   ├── Dockerfile                  # imagem Jenkins customizada (Docker CLI + Maven)
│   ├── Jenkinsfile                 # pipeline declarativo
│   └── plugins.txt
├── scripts/
│   ├── setup_infra.sh              # cria toda a infraestrutura do zero
│   ├── clean_all.sh                # remove tudo (volta ao estado "pelado")
│   ├── aplicar_mudanca.sh          # aplica a mudança (label + tabela categoria)
│   ├── corrigir_qualidade.sh       # corrige o erro proposital de qualidade
│   ├── deploy_homolog.sh           # atualiza Homologação
│   └── deploy_prod.sh              # atualiza Produção
├── registro-mudanca/
│   └── RDM-2026-001.md             # Registro de Mudança (etapa A)
├── src-changes/                    # artefatos da mudança a ser aplicada na demo
│   └── README.md                   # roteiro passo a passo da apresentação
└── Arquitetura_Pipeline_ReceitasApp.docx
```

---

## Como executar (VM Univates)

### 1. Garantir VM "pelada"
```bash
sudo docker ps -a
sudo docker images
sudo docker volume ls
```
Se houver algo de execuções anteriores: `./scripts/clean_all.sh`

### 2. Criar toda a infraestrutura
```bash
git clone <URL_DO_REPOSITORIO>
cd receitas-app
./scripts/setup_infra.sh
```

Isso cria automaticamente (via `docker compose`):
- `receitas-db-homolog` + `receitas-app-homolog` (porta 8081)
- `receitas-db-prod` + `receitas-app-prod` (porta 8082)
- `jenkins` (porta 8090)
- `sonarqube` (porta 9000)

### 3. Acessar
- Homologação: `http://VM:8081` (login admin / admin123)
- Produção: `http://VM:8082` (login admin / admin123)
- Jenkins: `http://VM:8090`
- SonarQube: `http://VM:9000` (login admin / admin)

---

## Roteiro da apresentação

Ver `src-changes/README.md` para o passo a passo completo (registro da mudança,
implementação, versionamento, integração, atualização de Homologação e Produção).

---

## Banco de dados

### Tabela `receita`
| Coluna         | Tipo           |
|----------------|----------------|
| id             | BIGSERIAL PK   |
| nome           | VARCHAR(100)   |
| descricao      | TEXT           |
| data_registro  | DATE           |
| custo          | NUMERIC(10,2)  |
| tipo_receita   | VARCHAR(10)    |

### Tabela `usuario`
| Coluna   | Tipo          |
|----------|---------------|
| id       | BIGSERIAL PK  |
| nome     | VARCHAR(100)  |
| login    | VARCHAR(50)   |
| senha    | VARCHAR(255)  |
| situacao | VARCHAR(10)   |

### Tabela `categoria` (adicionada via migration V2 durante a demo)
| Coluna    | Tipo          |
|-----------|---------------|
| id        | BIGSERIAL PK  |
| descricao | VARCHAR(100)  |

O versionamento do schema é feito via **Flyway** (`src/main/resources/db/migration/`),
preservando os dados existentes a cada atualização.
