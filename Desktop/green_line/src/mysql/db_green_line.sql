CREATE DATABASE green_line;
USE green_line;

CREATE TABLE pessoa (
    id_pessoa INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(40) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE CHECK (email LIKE '%@%.%'),
    senha VARCHAR(255) NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    cpf_cnpj VARCHAR(18) NOT NULL UNIQUE,
    rg VARCHAR(12) NULL,
    genero ENUM('M', 'F', 'O') NULL,
    idade int null
);

CREATE TABLE usuario (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    id_pessoa INT NOT NULL UNIQUE, 
    FOREIGN KEY (id_pessoa) REFERENCES pessoa(id_pessoa) ON DELETE CASCADE,
    id_tipo_usuario INT NOT NULL, 
    FOREIGN KEY (id_tipo_usuario) REFERENCES tipo_usuario(id_tipo),
    nivel_acesso VARCHAR(15) NOT NULL DEFAULT 'Sem acesso',
    situacao ENUM('A','I') NOT NULL,
    data_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- mais flexibilidade
CREATE TABLE tipo_usuario(
id_tipo int primary key auto_increment,
tipo varchar(35) not null
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

CREATE TABLE imagens (
    id_imagem INT PRIMARY KEY AUTO_INCREMENT,
    endereco VARCHAR(255) NOT NULL,
    id_produto INT NOT NULL,
    FOREIGN KEY (id_produto) REFERENCES produto(id_produto)
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
    quantidade_cancelada INT DEFAULT 0 CHECK (quantidade_cancelada >= 0 AND quantidade_cancelada <= quantidade)
);

