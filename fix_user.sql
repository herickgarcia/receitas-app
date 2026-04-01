DELETE FROM usuario;
INSERT INTO usuario (nome, login, senha, situacao) 
VALUES ('Administrador', 'admin', '$2a$10$slYQmyNdGzin7olVmy0O.OPST9/PgBkqquzi.Ss7KIUgO2t0jKMm2', 'ATIVO');
SELECT login, LENGTH(senha) as tamanho, SUBSTR(senha, 1, 10) as inicio FROM usuario;
