-- ================================================
-- Script de população do banco de dados
-- Registro de Receitas
-- ================================================

-- Criação das tabelas (caso não existam)
CREATE TABLE IF NOT EXISTS usuario (
    id        BIGSERIAL PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL,
    login     VARCHAR(50)  NOT NULL UNIQUE,
    senha     VARCHAR(255) NOT NULL,
    situacao  VARCHAR(10)  NOT NULL CHECK (situacao IN ('ATIVO', 'INATIVO'))
);

CREATE TABLE IF NOT EXISTS receita (
    id           BIGSERIAL PRIMARY KEY,
    nome         VARCHAR(100)   NOT NULL,
    descricao    TEXT           NOT NULL,
    data_registro DATE          NOT NULL DEFAULT CURRENT_DATE,
    custo        NUMERIC(10, 2) NOT NULL,
    tipo_receita VARCHAR(10)    NOT NULL CHECK (tipo_receita IN ('DOCE', 'SALGADO'))
);

-- ================================================
-- Usuário padrão
-- Senha: admin123 (hash BCrypt)
-- ================================================
INSERT INTO usuario (nome, login, senha, situacao) VALUES
('Administrador', 'admin', '$2a$10$7QfnHne9FX8JBOfOlZpkXunlrNnB2oZNB2Bl8aXmb22qhbDgEXg4i', 'ATIVO');

-- ================================================
-- 10 Receitas
-- ================================================
INSERT INTO receita (nome, descricao, data_registro, custo, tipo_receita) VALUES
('Bolo de Cenoura com Cobertura',
 'Bolo de cenoura fofinho com cobertura de chocolate. Ingredientes: 3 cenouras, 3 ovos, 1 xícara de óleo, 2 xícaras de farinha, 2 xícaras de açúcar, 1 colher de fermento. Modo de preparo: bater no liquidificador a cenoura, ovos e óleo, misturar com a farinha e açúcar, assar por 40 minutos a 180°C.',
 '2024-01-10', 18.50, 'DOCE'),

('Coxinha de Frango',
 'Coxinha tradicional com recheio de frango desfiado e catupiry. Ingredientes: 500g de farinha de trigo, 500ml de caldo de frango, 300g de frango cozido desfiado, 100g de catupiry, sal a gosto. Modo de preparo: fazer a massa com caldo e farinha, rechear com frango e catupiry, modelar e fritar.',
 '2024-01-12', 32.00, 'SALGADO'),

('Brigadeiro Tradicional',
 'Brigadeiro cremoso de chocolate ao leite. Ingredientes: 1 lata de leite condensado, 3 colheres de achocolatado, 1 colher de manteiga, chocolate granulado. Modo de preparo: misturar e cozinhar em fogo médio mexendo sempre até soltar do fundo, enrolar e passar no granulado.',
 '2024-01-15', 12.00, 'DOCE'),

('Esfiha de Carne',
 'Esfiha aberta de carne moída temperada. Ingredientes: massa de esfiha, 500g de carne moída, 2 tomates, 1 cebola, suco de limão, sal, pimenta e hortelã. Modo de preparo: temperar a carne crua, abrir a massa, colocar o recheio e assar por 20 minutos a 200°C.',
 '2024-01-18', 28.00, 'SALGADO'),

('Pudim de Leite Condensado',
 'Pudim clássico cremoso com calda de caramelo. Ingredientes: 1 lata de leite condensado, a mesma medida de leite integral, 3 ovos inteiros, 1 xícara de açúcar para a calda. Modo de preparo: fazer a calda, bater os demais ingredientes no liquidificador, colocar na forma e assar em banho-maria por 1 hora.',
 '2024-01-20', 15.00, 'DOCE'),

('Pão de Queijo',
 'Pão de queijo mineiro crocante por fora e macio por dentro. Ingredientes: 500g de polvilho azedo, 1 xícara de leite, 1 xícara de óleo, 2 ovos, 200g de queijo parmesão, sal. Modo de preparo: escaldar o polvilho com leite e óleo quentes, adicionar ovos e queijo, modelar e assar a 180°C por 30 minutos.',
 '2024-01-22', 22.00, 'SALGADO'),

('Mousse de Maracujá',
 'Mousse leve e cremosa de maracujá. Ingredientes: 1 lata de leite condensado, 1 caixa de creme de leite, 1 xícara de suco de maracujá concentrado, 1 envelope de gelatina sem sabor. Modo de preparo: bater tudo no liquidificador, adicionar a gelatina dissolvida e levar à geladeira por 4 horas.',
 '2024-01-25', 20.00, 'DOCE'),

('Quibe Frito',
 'Quibe frito crocante com recheio de carne e hortelã. Ingredientes: 500g de trigo para quibe, 500g de carne moída, 1 cebola, hortelã fresca, sal, pimenta síria, óleo para fritar. Modo de preparo: hidratar o trigo, misturar com a carne e temperos, modelar no formato oval e fritar em óleo quente.',
 '2024-01-28', 35.00, 'SALGADO'),

('Torta de Limão',
 'Torta de limão com massa crocante e recheio cremoso. Ingredientes: 200g de biscoito maisena, 100g de manteiga, 1 lata de leite condensado, suco de 4 limões, 1 caixa de creme de leite, 3 claras para merengue. Modo de preparo: triturar biscoito com manteiga para a massa, misturar leite condensado com limão e creme de leite para o recheio, finalizar com merengue.',
 '2024-02-01', 25.00, 'DOCE'),

('Pastel de Forno de Frango',
 'Pastel de forno com recheio de frango cremoso. Ingredientes: 500g de farinha de trigo, 1 tablete de fermento, 1 ovo, 1 xícara de leite morno, 3 colheres de manteiga, sal, 300g de frango desfiado, 100g de requeijão, milho e cheiro-verde. Modo de preparo: fazer a massa, rechear com frango temperado e requeijão, pincelar com gema e assar a 180°C por 25 minutos.',
 '2024-02-05', 30.00, 'SALGADO');
