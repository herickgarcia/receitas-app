# Registro de Receitas

Aplicação web para gerenciamento de receitas (doces e salgados), desenvolvida com Java Spring Boot, PostgreSQL e Thymeleaf.

## Tecnologias

- Java 17
- Spring Boot 3.2
- Spring Security
- Spring Data JPA
- PostgreSQL 16
- Thymeleaf
- Docker / Docker Compose
- Maven

## Funcionalidades

- Login com autenticação via Spring Security
- Listagem de receitas com filtro por tipo (doce/salgado) e busca por nome
- CRUD completo de receitas (criar, visualizar, editar, excluir)

## Credenciais padrão

| Login | Senha    |
|-------|----------|
| admin | admin123 |

---

## Executar com Docker (recomendado)

### Pré-requisitos
- Docker
- Docker Compose

### Comandos

```bash
# Clonar o repositório
git clone <URL_DO_REPOSITORIO>
cd receitas-app

# Subir a aplicação (banco + app)
docker compose up --build -d

# Acessar em:
http://localhost:8080
```

### Parar a aplicação

```bash
docker compose down
```

---

## Executar localmente (sem Docker)

### Pré-requisitos
- Java 17+
- Maven 3.9+
- PostgreSQL rodando localmente

### Configurar banco

```sql
CREATE DATABASE receitasdb;
```

Execute o script de população:
```bash
psql -U postgres -d receitasdb -f src/main/resources/db/seed.sql
```

### Iniciar a aplicação

```bash
mvn spring-boot:run
```

Acesse: `http://localhost:8080`

---

## Estrutura do projeto

```
receitas-app/
├── src/
│   └── main/
│       ├── java/com/receitas/app/
│       │   ├── config/          # Configuração do Spring Security
│       │   ├── controller/      # Controllers (Auth + Receita)
│       │   ├── model/           # Entidades JPA (Receita, Usuario)
│       │   ├── repository/      # Repositórios Spring Data
│       │   ├── service/         # Regras de negócio
│       │   └── ReceitasAppApplication.java
│       └── resources/
│           ├── templates/       # Templates Thymeleaf
│           │   ├── auth/        # Tela de login
│           │   └── receitas/    # Lista, formulário, detalhe
│           ├── db/
│           │   └── seed.sql     # Script com 10 receitas + 1 usuário
│           └── application.properties
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

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
