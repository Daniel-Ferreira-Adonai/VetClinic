CREATE DATABASE clinicaVeterinaria;
USE clinicaVeterianaria;
SELECT * FROM usuarios;
SELECT * FROM tutor;
SELECT * FROM consulta;
CREATE TABLE tutor (
    id_tutor INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(100) UNIQUE NOT NULL
);
CREATE TABLE animal (
  id_animal INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  especie VARCHAR(50),
  raca VARCHAR(50),
  id_tutor INT,
  FOREIGN KEY (id_tutor) REFERENCES tutor(id_tutor)
);
CREATE TABLE consulta (
  id_consulta INT AUTO_INCREMENT PRIMARY KEY,
  id_animal INT,
  queixa_do_tutor TEXT,
  data_hora DATETIME NOT NULL,
  veterinario VARCHAR(100), 
  preco DECIMAL(10,2),
  diagnostico TEXT,
  FOREIGN KEY (id_animal) REFERENCES animal(id_animal)
);
CREATE TABLE servico (
    id_servico INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10,2),
	imagem_url TEXT,
    tipo ENUM('BANHO', 'TOSA', 'VACINA', 'CONSULTA', 'EXAME', 'OUTRO') DEFAULT 'OUTRO'
);
CREATE TABLE produto (
    id_produto INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    descricao TEXT,
    preco DECIMAL(10,2),
    quantidade_estoque INT,
    imagem_url TEXT
);
CREATE TABLE usuarios (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100),
  email VARCHAR(100) UNIQUE,
  senha VARCHAR(255),
  tipo_usuario ENUM('TUTOR', 'VET', 'ADMIN') NOT NULL
);
CREATE TABLE pedido (
  id INT AUTO_INCREMENT PRIMARY KEY,
  email_cliente VARCHAR(255),
  data DATETIME,
  total DOUBLE
);
CREATE TABLE item_pedido (
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_pedido INT,
  id_item INT,
  nome VARCHAR(255),
  tipo VARCHAR(50),
  preco DOUBLE,
  quantidade INT,
  FOREIGN KEY (id_pedido) REFERENCES pedido(id)
);


#OBS: SÃO VALORES EXEMPLO!

-- CREATE
INSERT INTO tutor (nome, telefone, email)
VALUES ('João Silva', '88999999999', 'joao@teste.com');

-- READ
SELECT * FROM tutor;

-- UPDATE
UPDATE tutor
SET nome = 'João da Silva', telefone = '88988888888'
WHERE id_tutor = 1;

-- DELETE
DELETE FROM tutor
WHERE id_tutor = 1;

-- SELECT por e-mail
SELECT * FROM tutor WHERE email = 'tutor@teste.com';


-- CREATE
INSERT INTO animal (nome, especie, raca, id_tutor)
VALUES ('Rex', 'Cão', 'Labrador', 2);

-- READ
SELECT * FROM animal;

-- UPDATE
UPDATE animal
SET nome = 'Rex Jr.', raca = 'Golden Retriever'
WHERE id_animal = 1;

-- DELETE
DELETE FROM animal
WHERE id_animal = 1;

-- Buscar um animal específico pelo ID
SELECT * FROM animal WHERE id_animal = 1;

-- Listar animais de um tutor específico
SELECT * FROM animal WHERE id_tutor = 2;


-- CREATE
INSERT INTO consulta (id_animal, queixa_do_tutor, data_hora, veterinario, preco, diagnostico)
VALUES (1, 'Tosse frequente', '2025-05-15 14:00:00', 'Dra. Ana', 150.00, 'Traqueobronquite');

-- READ
SELECT * FROM consulta;

-- UPDATE
UPDATE consulta
SET preco = 180.00, diagnostico = 'Revisado: Alergia respiratória'
WHERE id_consulta = 1;

-- DELETE
DELETE FROM consulta
WHERE id_consulta = 1;

-- CREATE
INSERT INTO consulta (id_animal, queixa_do_tutor, data_hora, veterinario, preco, diagnostico)
VALUES (1, 'Tosse frequente', '2025-05-15 14:00:00', 'Dra. Ana', 150.00, 'Traqueobronquite');

-- READ
SELECT * FROM consulta;

-- UPDATE
UPDATE consulta
SET preco = 180.00, diagnostico = 'Revisado: Alergia respiratória'
WHERE id_consulta = 1;

-- DELETE
DELETE FROM consulta
WHERE id_consulta = 1;

-- CREATE
INSERT INTO produto (nome, descricao, preco, quantidade_estoque, imagem_url)
VALUES ('Ração Premium', 'Ração para cães adultos', 120.00, 30, 'img/produto1.png');

-- READ
SELECT * FROM produto;

-- UPDATE
UPDATE produto
SET preco = 115.00, quantidade_estoque = 25
WHERE id_produto = 1;

-- DELETE
DELETE FROM produto
WHERE id_produto = 1;

-- CREATE
INSERT INTO servico (nome, descricao, preco, imagem_url, tipo)
VALUES ('Vacinação Antirrábica', 'Dose única anual', 80.00, 'img/vacina1.png', 'VACINA');

-- READ
SELECT * FROM servico;

-- UPDATE
UPDATE servico
SET preco = 90.00
WHERE id_servico = 1;

-- DELETE
DELETE FROM servico
WHERE id_servico = 1;
-- CREATE
INSERT INTO pedido (email_cliente, data, total)
VALUES ('cliente@teste.com', NOW(), 300.00);

-- READ
SELECT * FROM pedido;

-- UPDATE
UPDATE pedido
SET total = 320.00
WHERE id = 1;

-- DELETE
DELETE FROM pedido
WHERE id = 1;

-- CREATE
INSERT INTO item_pedido (id_pedido, id_item, nome, tipo, preco, quantidade)
VALUES (1, 1, 'Ração Premium', 'PRODUTO', 120.00, 2);

-- READ
SELECT * FROM item_pedido;

-- UPDATE
UPDATE item_pedido
SET quantidade = 3
WHERE id = 1;

-- DELETE
DELETE FROM item_pedido
WHERE id = 1;
