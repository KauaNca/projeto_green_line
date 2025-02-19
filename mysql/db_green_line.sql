CREATE DATABASE green_line;
USE green_line;

CREATE TABLE pessoa (
    id_pessoa INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(40) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE CHECK (email LIKE '%@%.%'),
    telefone VARCHAR(15) NOT NULL,
    cpf_cnpj VARCHAR(18) NOT NULL UNIQUE,
    rg VARCHAR(12) NULL,
    genero ENUM('M', 'F', 'O') NULL,
    idade int null
);
-- mais flexibilidade
CREATE TABLE tipo_usuario(
id_tipo int primary key auto_increment,
tipo varchar(35) not null
);
CREATE TABLE usuario (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    id_pessoa INT NOT NULL UNIQUE, 
    FOREIGN KEY (id_pessoa) REFERENCES pessoa(id_pessoa) ON DELETE CASCADE,
    id_tipo_usuario INT NOT NULL, 
    FOREIGN KEY (id_tipo_usuario) REFERENCES tipo_usuario(id_tipo),
	senha VARCHAR(255) NOT NULL,
    nivel_acesso VARCHAR(15) NOT NULL DEFAULT 'Sem acesso',
    situacao ENUM('A','I') NOT NULL,
    data_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);



CREATE TABLE acessos (
    id_acesso INT PRIMARY KEY AUTO_INCREMENT, 
    id_usuario INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    data_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE enderecos (
    id_endereco INT PRIMARY KEY AUTO_INCREMENT,
    uf CHAR(2) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    bairro VARCHAR(50) NULL,
    apartamento VARCHAR(15) NULL,
    situacao CHAR(1) NOT NULL DEFAULT 'A' CHECK (situacao IN ('A', 'I')), -- evita que seja colocado outros valores fora do comum
    id_pessoa INT NOT NULL,
    FOREIGN KEY (id_pessoa) REFERENCES pessoa(id_pessoa) ON DELETE CASCADE
);

CREATE TABLE forma_pagamento(
	id_f_pagamento int primary key auto_increment,
    tipo varchar(50) not null
);

CREATE TABLE pagamento (
    id_pagamento INT PRIMARY KEY AUTO_INCREMENT,
    pagamento_situacao CHAR(1) DEFAULT 'P' CHECK (pagamento_situacao IN ('F', 'P', 'C')),
    data_pagamento DATETIME NOT NULL,
    id_f_pagamento INT NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    num_parcelas INT DEFAULT 1 CHECK (num_parcelas >= 1),
   parcelamento_situacao ENUM('Em aberto','Pago','Cancelado') NULL,
    transacao_id VARCHAR(100) UNIQUE NULL,
    bandeira_cartao VARCHAR(20) NULL,
    FOREIGN KEY (id_f_pagamento) REFERENCES forma_pagamento(id_f_pagamento)
);


CREATE TABLE pedidos (
    id_pedido INT PRIMARY KEY AUTO_INCREMENT,
    data_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    situacao CHAR(1) NOT NULL DEFAULT 'P' CHECK (situacao IN ('F', 'P', 'C')),
    id_usuario INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    id_pagamento INT NOT NULL,
    FOREIGN KEY (id_pagamento) REFERENCES pagamento(id_pagamento),
    status_pagamento CHAR(1) NOT NULL DEFAULT 'P' CHECK (status_pagamento IN ('P', 'F', 'C')),
    endereco_entrega TEXT NULL, -- por enquanto null
    metodo_entrega VARCHAR(50) NULL -- por enquanto null
);

CREATE TABLE categoria (
    id_categoria INT PRIMARY KEY AUTO_INCREMENT,
    categoria VARCHAR(25) NOT NULL,
    descricao TEXT NULL
);

CREATE TABLE subcategorias (
    id_subcat INT PRIMARY KEY AUTO_INCREMENT,
    id_categoria INT NOT NULL,
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria),
    subcategoria VARCHAR(30) NOT NULL UNIQUE,
    descricao TEXT NULL
);


CREATE TABLE produto (
    id_produto INT PRIMARY KEY AUTO_INCREMENT,
    nome_produto VARCHAR(30) NOT NULL,
    descricao TEXT NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    marca VARCHAR(20) NULL DEFAULT NULL,
    estoque INT NOT NULL CHECK (estoque >= 0),
    id_subcat INT NOT NULL,
    FOREIGN KEY (id_subcat) REFERENCES subcategorias(id_subcat)
);

CREATE TABLE imagens (
    id_imagem INT PRIMARY KEY AUTO_INCREMENT,
    endereco VARCHAR(255) NOT NULL,
    id_produto INT NOT NULL,
    FOREIGN KEY (id_produto) REFERENCES produto(id_produto)
);


CREATE TABLE pedidos_produtos (
    id_pedido INT NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido),
    id_produto INT NOT NULL,
    FOREIGN KEY (id_produto) REFERENCES produto(id_produto),
    valor DECIMAL(10,2) NOT NULL,
    quantidade INT NOT NULL CHECK (quantidade >= 1),
    desconto DECIMAL(10,2) DEFAULT 0 CHECK (desconto >= 0),
    subtotal DECIMAL(10,2) GENERATED ALWAYS AS ((valor - desconto) * quantidade) STORED,
    cancelado CHAR(1) NOT NULL DEFAULT 'N' CHECK (cancelado IN ('S', 'N', 'P')),
    quantidade_cancelada INT DEFAULT 0,
    CONSTRAINT chk_quantidade_cancelada CHECK (quantidade_cancelada >= 0 AND quantidade_cancelada <= quantidade)
);

DROP DATABASE green_line;

-- INSERINDO DADOS

INSERT INTO pessoa (nome, email,  telefone, cpf_cnpj, rg, genero, idade) VALUES 
('Kauã', 'kaua@example.com',  '1234567890', '123.456.789-09', '12.345.678-9', 'M', 21),
('Gabriel', 'gabriel@example.com',  '0987654321', '987.654.321-00', '98.765.432-1', 'M', 25),
('Edenilson', 'edenilson@example.com',  '1122334455', '112.233.445-67', '11.223.344-5', 'M', 30);

INSERT INTO tipo_usuario (tipo) VALUES 
('Administrador'), 
('Usuário Comum');

INSERT INTO usuario (id_pessoa, id_tipo_usuario, senha, nivel_acesso, situacao) VALUES 
((SELECT id_pessoa FROM pessoa WHERE nome = 'Kauã'), 1, '123','Com acesso', 'A'),
((SELECT id_pessoa FROM pessoa WHERE nome = 'Gabriel'), 1, '123','Com acesso', 'A'),
((SELECT id_pessoa FROM pessoa WHERE nome = 'Edenilson'), 1, '123','Com acesso', 'A');

SELECT * FROM tipo_usuario;

SELECT nome FROM usuario INNER JOIN pessoa ON pessoa.id_pessoa = usuario.id_pessoa WHERE id_usuario = 1;

SELECT id_usuario, id_tipo_usuario, nivel_acesso FROM usuario INNER JOIN pessoa ON pessoa.id_pessoa = usuario.id_pessoa WHERE nome = 'Kauã'AND senha = 123;