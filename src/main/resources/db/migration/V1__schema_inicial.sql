-- ================================================
-- V1: Schema inicial - Registro de Receitas
-- ================================================

CREATE TABLE IF NOT EXISTS usuario (
    id        BIGSERIAL PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL,
    login     VARCHAR(50)  NOT NULL UNIQUE,
    senha     VARCHAR(255) NOT NULL,
    situacao  VARCHAR(10)  NOT NULL CHECK (situacao IN ('ATIVO', 'INATIVO'))
);

CREATE TABLE IF NOT EXISTS receita (
    id            BIGSERIAL PRIMARY KEY,
    nome          VARCHAR(100)   NOT NULL,
    descricao     TEXT           NOT NULL,
    data_registro DATE           NOT NULL DEFAULT CURRENT_DATE,
    custo         NUMERIC(10, 2) NOT NULL,
    tipo_receita  VARCHAR(10)    NOT NULL CHECK (tipo_receita IN ('DOCE', 'SALGADO'))
);

-- Usuario padrao (senha: admin123)
INSERT INTO usuario (nome, login, senha, situacao) VALUES
('Administrador', 'admin', 'admin123', 'ATIVO');

-- 10 receitas de exemplo
INSERT INTO receita (nome, descricao, data_registro, custo, tipo_receita) VALUES
('Bolo de Cenoura com Cobertura', 'Bolo de cenoura fofinho com cobertura de chocolate.', '2024-01-10', 18.50, 'DOCE'),
('Coxinha de Frango', 'Coxinha tradicional com recheio de frango desfiado e catupiry.', '2024-01-12', 32.00, 'SALGADO'),
('Brigadeiro Tradicional', 'Brigadeiro cremoso de chocolate ao leite.', '2024-01-15', 12.00, 'DOCE'),
('Esfiha de Carne', 'Esfiha aberta de carne moida temperada.', '2024-01-18', 28.00, 'SALGADO'),
('Pudim de Leite Condensado', 'Pudim classico cremoso com calda de caramelo.', '2024-01-20', 15.00, 'DOCE'),
('Pao de Queijo', 'Pao de queijo mineiro crocante por fora e macio por dentro.', '2024-01-22', 22.00, 'SALGADO'),
('Mousse de Maracuja', 'Mousse leve e cremosa de maracuja.', '2024-01-25', 20.00, 'DOCE'),
('Quibe Frito', 'Quibe frito crocante com recheio de carne e hortela.', '2024-01-28', 35.00, 'SALGADO'),
('Torta de Limao', 'Torta de limao com massa crocante e recheio cremoso.', '2024-02-01', 25.00, 'DOCE'),
('Pastel de Forno de Frango', 'Pastel de forno com recheio de frango cremoso.', '2024-02-05', 30.00, 'SALGADO');
