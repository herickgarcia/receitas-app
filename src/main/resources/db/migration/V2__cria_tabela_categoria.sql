CREATE TABLE categoria (
    id BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL
);

INSERT INTO categoria (descricao) VALUES ('Doce');
INSERT INTO categoria (descricao) VALUES ('Salgado');
INSERT INTO categoria (descricao) VALUES ('Bebida');
