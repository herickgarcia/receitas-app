-- ================================================
-- V2: Nova tabela "categoria" (demanda do cliente)
-- Preserva todos os dados existentes em "receita" e "usuario"
-- ================================================

CREATE TABLE IF NOT EXISTS categoria (
    id        BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL
);

INSERT INTO categoria (descricao) VALUES
('Café da manhã'),
('Almoço'),
('Jantar'),
('Sobremesa'),
('Lanche');
