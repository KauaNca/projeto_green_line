DROP DATABASE green_line;
CREATE DATABASE green_line;
USE green_line;

CREATE TABLE pessoa (
    id_pessoa INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(40) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE CHECK (email LIKE '%@%.%'),
    telefone VARCHAR(15) NOT NULL,
    cpf VARCHAR(18) NOT NULL UNIQUE,
    rg VARCHAR(12) NULL,
    genero ENUM('M', 'F', 'O') NULL,
    idade INT NULL
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
CREATE TABLE ImagensUsuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    data_upload DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    caminho_imagem VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
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
    cep VARCHAR(12) NOT NULL,
    cidade VARCHAR(20) NOT NULL,
    bairro VARCHAR(50) NULL,
    endereco TEXT NULL,
    complemento TEXT NULL,
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
ALTER TABLE categoria MODIFY categoria VARCHAR(50);


INSERT INTO categoria (categoria, descricao) VALUES
('Moda Sustentável', 'Roupas e acessórios feitos com materiais ecológicos e métodos de produção éticos.'),
('Cosméticos Naturais', 'Produtos de beleza e cuidados pessoais feitos com ingredientes naturais e sem testes em animais.'),
('Casa Ecológica', 'Produtos para a casa feitos com materiais sustentáveis e eficientes em termos de energia.'),
('Alimentos Orgânicos', 'Alimentos produzidos sem o uso de pesticidas e fertilizantes químicos.'),
('Transporte Sustentável', 'Veículos elétricos, bicicletas e outros meios de transporte ecologicamente corretos.'),
('Reciclagem e Reutilização', 'Produtos feitos a partir de materiais reciclados ou que incentivam a reutilização e redução de resíduos.'),
('Jardinagem Orgânica', 'Ferramentas, sementes e insumos para a jardinagem orgânica e cultivo sustentável.'),
('Educação e Conscientização', 'Livros, cursos e materiais educacionais sobre sustentabilidade e meio ambiente.');


CREATE TABLE subcategorias (
    id_subcat INT PRIMARY KEY AUTO_INCREMENT,
    id_categoria INT NOT NULL,
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria),
    subcategoria VARCHAR(30) NOT NULL UNIQUE,
    descricao TEXT NULL
);
ALTER TABLE subcategorias MODIFY subcategoria VARCHAR(50);

INSERT INTO subcategorias (id_categoria, subcategoria, descricao) VALUES
(1, 'Roupas Orgânicas', 'Vestuário feito com algodão orgânico e outros materiais sustentáveis.'),
(1, 'Acessórios Reciclados', 'Acessórios feitos a partir de materiais reciclados, como garrafas plásticas.'),
(1, 'Calçados Sustentáveis', 'Calçados produzidos com materiais ecológicos e métodos de fabricação éticos.'),
(2, 'Cosméticos Veganos', 'Produtos de beleza sem ingredientes de origem animal.'),
(2, 'Óleos Essenciais', 'Óleos naturais para aromaterapia e cuidados pessoais.'),
(2, 'Sabonetes Naturais', 'Sabonetes artesanais feitos com ingredientes naturais e sem produtos químicos nocivos.'),
(3, 'Móveis Sustentáveis', 'Móveis feitos com madeira de reflorestamento e outros materiais ecológicos.'),
(3, 'Iluminação LED', 'Lâmpadas e sistemas de iluminação eficientes em termos de energia.'),
(3, 'Utensílios de Cozinha Ecológicos', 'Utensílios de cozinha feitos com materiais sustentáveis e duráveis.'),
(4, 'Frutas e Vegetais Orgânicos', 'Produtos hortifrúti cultivados sem o uso de agrotóxicos.'),
(4, 'Grãos e Cereais Orgânicos', 'Grãos e cereais produzidos de forma sustentável.'),
(4, 'Laticínios Orgânicos', 'Leite, queijo e outros laticínios produzidos sem hormônios artificiais e antibióticos.'),
(5, 'Bicicletas Elétricas', 'Bicicletas movidas a energia elétrica para transporte sustentável.'),
(5, 'Patinetes Elétricos', 'Patinetes elétricos como alternativa de transporte ecológico.'),
(5, 'Acessórios para Bicicletas', 'Acessórios e equipamentos para ciclismo sustentável.'),
(6, 'Produtos Reciclados', 'Produtos feitos inteiramente de materiais reciclados.'),
(6, 'Compostagem', 'Sistemas e acessórios para compostagem doméstica.'),
(6, 'Embalagens Sustentáveis', 'Embalagens feitas com materiais recicláveis e biodegradáveis.'),
(7, 'Sementes Orgânicas', 'Sementes de plantas cultivadas sem o uso de químicos.'),
(7, 'Ferramentas de Jardinagem', 'Ferramentas e equipamentos para jardinagem sustentável.'),
(8, 'Livros de Sustentabilidade', 'Livros e materiais sobre práticas sustentáveis.');



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
ALTER TABLE produto MODIFY nome_produto VARCHAR(50);

CREATE TABLE imagens (
    id_imagem INT PRIMARY KEY AUTO_INCREMENT,
    endereco VARCHAR(255) NOT NULL,
    endereco2 VARCHAR(255) NOT NULL,
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

-- INSERINDO DADOS

INSERT INTO pessoa (nome, email,  telefone, cpf, rg, genero, idade) VALUES 
('Kauã', 'kaua@example.com',  '1234567890', '123.456.789-09', '12.345.678-9', 'M', 21),
('Gabriel', 'gabriel@example.com',  '0987654321', '987.654.321-00', '98.765.432-1', 'M', 25),
('Edenilson', 'edenilson@example.com',  '1122334455', '112.233.445-67', '11.223.344-5', 'M', 30),
('Fabricio', 'fafa@example.com',  '1122354455', '112.233.455-67', '11.223.374-5', 'M', 70),
('Guilherme', 'gui@example.com',  '1127334455', '112.233.465-67', '11.223.384-5', 'M', 20);

INSERT INTO tipo_usuario (tipo) VALUES 
('Administrador'),
('Usuário Comum');



INSERT INTO produto(nome_produto,descricao,preco,marca,estoque,id_subcat)
VALUES ('grao','ddfffh',15.22,'fg',15,(SELECT id_subcat FROM subcategorias WHERE subcategoria = 'Roupas Orgânicas'));

CREATE VIEW vw_produto_detalhado AS
SELECT produto.id_produto,nome_produto,produto.descricao,preco,marca,estoque,categoria,subcategoria,endereco,endereco2 FROM produto
INNER JOIN imagens ON imagens.id_produto = produto.id_produto
INNER JOIN subcategorias ON subcategorias.id_subcat = produto.id_subcat
INNER JOIN categoria ON categoria.id_categoria = subcategorias.id_categoria;


INSERT INTO produto (nome_produto, descricao, preco, marca, estoque, id_subcat) VALUES
('Camisa de Algodão Orgânico', 'Camisa feita com 100% algodão orgânico.', 79.90, 'EcoWear', 50, 1),
('Brinco Ecológico Pétalas Laranjada', 'Brincos feitos a partir de garrafas plásticas recicladas.', 25.00, 'EcoJewels', 30, 1),
('Tênis de Material Reciclado', 'Tênis ecológicos feitos com materiais reciclados.', 120.00, 'GreenSneakers', 40, 1),
('Loção Corporal Hidratante Uva Vegana 250ml', 'Rica em ativos derivados da uva. Possui antioxidantes e emolientes que promovem a proteção cutânea e auxilia na melhoria da firmeza da pele. 
Sua textura é leve e absorve rapidamente deixando sua pele delicadamente perfumada e com um toque sedoso.', 93.50, 'Empório Essenza', 25, 2),
('Óleo Essencial de Lavanda', 'Óleo essencial de lavanda para aromaterapia.', 30.00, 'ViaAroma', 60, 2),
('Sabonete de Alecrim', 'Sabonete artesanal de alecrim, feito com ingredientes naturais.', 15.00, 'NaturalSoap', 100, 2),
('Mesa de Madeira de Reflorestamento', 'Mesa feita com madeira de reflorestamento.', 450.00, 'EcoFurniture', 10, 3),
('Lâmpada LED', 'Lâmpada LED eficiente em termos de energia.', 20.00, 'BrightEco', 200, 3),
('Conjunto de Utensílios de Bambu', 'Conjunto de utensílios de cozinha feitos com bambu sustentável.', 35.00, 'EcoKitchen', 80, 3),
('Maçã Orgânica', 'Maçãs cultivadas sem o uso de agrotóxicos.', 10.00, 'OrganicFarm', 150, 4),
('Arroz Integral Orgânico', 'Arroz integral produzido de forma sustentável.', 25.00, 'EcoGrains', 100, 4),
('Leite Orgânico', 'Leite produzido sem hormônios artificiais e antibióticos.', 8.50, 'GreenDairy', 200, 4),
('Bicicleta Elétrica', 'Bicicleta movida a energia elétrica para transporte sustentável.', 3200.00, 'EcoRide', 15, 5),
('Patinete Elétrico', 'Patinete elétrico como alternativa de transporte ecológico.', 1500.00, 'GreenScoot', 20, 5),
('Capacete para Ciclistas', 'Capacete para ciclistas feito com materiais sustentáveis.', 80.00, 'CycleSafe', 50, 5),
('Caderno de Papel Reciclado', 'Caderno feito inteiramente de papel reciclado.', 12.00, 'EcoStationery', 70, 6),
('Composteira Doméstica', 'Sistema de compostagem para uso doméstico.', 250.00, 'GreenCompost', 15, 6),
('Embalagem Biodegradável', 'Embalagem feita com materiais biodegradáveis.', 5.00, 'EcoPack', 500, 6),
('Sementes de Tomate Orgânico', 'Sementes de tomate cultivadas sem o uso de químicos.', 4.00, 'OrganicSeeds', 200, 7),
('Pá de Jardinagem', 'Pá de jardinagem feita com materiaiacessosacessoss sustentáveis.', 20.00, 'GreenGardening', 60, 7),
('Livro sobre Sustentabilidade', 'Livro sobre práticas sustentáveis.', 50.00, 'EcoBooks', 30, 8);

INSERT INTO imagens (endereco,endereco2,id_produto) VALUES ("camiseta_algodao_organico.jpg","",2),
("Brinco Ecológico Pétalas Laranjada.jpg","",3),("tenis.jpg","",4),("Locao Corporal Hidratante Uva Vegana.jpg","",5),("oleo_essencial_de_lavanda.jpg","",6);

ALTER TABLE acessos ADD nome_usuario VARCHAR(20) NOT NULL;

INSERT INTO enderecos (uf, cep, cidade, bairro, endereco, complemento, situacao, id_pessoa)
VALUES ('RJ', '98765-432', 'Rio de Janeiro', 'Bairro Novo', 'Rua das Flores, 789', 'Apto 101', 'A', 1);

INSERT INTO enderecos (uf, cep, cidade, bairro, endereco, complemento, situacao, id_pessoa)
VALUES ('RJ', '98765-432', 'Rio de Janeiro', 'Bairro Novo', 'Rua das Flores, 789', 'Apto 101', 'A', 1);

INSERT INTO enderecos (uf, cep, cidade, bairro, endereco, complemento, situacao, id_pessoa)
VALUES ('SP', '12345-678', 'São Paulo', 'Centro', 'Av. Paulista, 1000', 'Apto 102', 'A', 2);

INSERT INTO enderecos (uf, cep, cidade, bairro, endereco, complemento, situacao, id_pessoa)
VALUES ('MG', '87654-321', 'Belo Horizonte', 'Savassi', 'Rua Paraíba, 200', 'Apto 103', 'A', 3);

INSERT INTO enderecos (uf, cep, cidade, bairro, endereco, complemento, situacao, id_pessoa)
VALUES ('RS', '54321-876', 'Porto Alegre', 'Moinhos de Vento', 'Rua Padre Chagas, 300', 'Apto 104', 'A', 4);

INSERT INTO enderecos (uf, cep, cidade, bairro, endereco, complemento, situacao, id_pessoa)
VALUES ('BA', '65432-987', 'Salvador', 'Pituba', 'Av. Magalhães Neto, 400', 'Apto 105', 'A', 5);

CREATE VIEW dados_pessoais
AS
SELECT p.id_pessoa,usuario.id_usuario, nome, email, telefone, cpf, rg, idade,uf,cep,cidade,bairro,endereco,complemento,senha,caminho_imagem FROM pessoa p INNER JOIN enderecos e ON p.id_pessoa = e.id_pessoa
INNER JOIN usuario ON p.id_pessoa = usuario.id_pessoa
INNER JOIN ImagensUsuarios IU ON IU.id_usuario = usuario.id_usuario;

CREATE VIEW dados_pesquisa
AS
SELECT p.id_pessoa,usuario.id_usuario, nome, email, telefone, cpf, rg, 
idade, uf,cep,cidade,bairro,endereco,complemento,caminho_imagem FROM pessoa p INNER JOIN enderecos e ON p.id_pessoa = e.id_pessoa
INNER JOIN usuario ON p.id_pessoa = usuario.id_pessoa
INNER JOIN ImagensUsuarios IU ON IU.id_usuario = usuario.id_usuario;

INSERT INTO usuario (id_pessoa, id_tipo_usuario, senha, nivel_acesso, situacao) VALUES 
((SELECT id_pessoa FROM pessoa WHERE nome = 'Kauã'), 2, '123','Com acesso', 'A'),
((SELECT id_pessoa FROM pessoa WHERE nome = 'Gabriel'), 1, '123','Com acesso', 'A'),
((SELECT id_pessoa FROM pessoa WHERE nome = 'Edenilson'), 1, '123','Com acesso', 'A'),
((SELECT id_pessoa FROM pessoa WHERE nome = 'Fabricio'), 1, '123','Com acesso', 'A'),
((SELECT id_pessoa FROM pessoa WHERE nome = 'Guilherme'), 1, '123','Com acesso', 'A');

INSERT INTO ImagensUsuarios (id_usuario,caminho_imagem) VALUES (1,"kaua.jpeg"),(2,"gabriel.jpeg"),(3,"edenilson.jpg"),
(4,"fabricio.jpg"),(5,"guilherme.jpg");

CREATE VIEW login AS
SELECT us.id_usuario,nome,caminho_imagem FROM usuario us INNER JOIN pessoa ON pessoa.id_pessoa = us.id_pessoa 
INNER JOIN ImagensUsuarios IU ON IU.id_usuario = us.id_usuario;

CREATE INDEX idx_nome_produto ON produto(nome_produto);
CREATE INDEX idx_nome_usuario ON pessoa(nome);


CREATE VIEW web_usuarios AS
SELECT nome,email,senha,situacao FROM usuario 
INNER JOIN pessoa ON pessoa.id_pessoa = usuario.id_pessoa;










