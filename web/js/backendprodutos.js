const express = require("express");
const cors = require("cors");
const Conexao = require("./conexao");
require("dotenv").config();

const app = express();

app.use(express.json());
app.use(cors({ origin: '*' }));

// Inicializa a conexão com o banco de dados
const db = new Conexao();

// Rota para listar produtos
app.get("/produto", async (req, res) => {
    try {
        const produtos = await db.query("SELECT * FROM produto");

        if (!produtos || produtos.length === 0) {
            return res.status(404).json({ mensagem: "Nenhum produto encontrado" });
        }

        res.json(produtos);
    } catch (err) {
        console.error("Erro ao buscar produtos:", err);
        res.status(500).json({
            erro: "Erro ao buscar produtos",
            detalhes: process.env.NODE_ENV === 'development' ? err.message : undefined
        });
    }
});

// Rota padrão para verificar se o servidor está rodando
app.get("/", (req, res) => {
    res.json({ mensagem: "API de produtos está funcionando" });
});

app.post("/pedidos", async (req, res) => {
    let venda = req.body;
    console.log(venda);
    res.status(200).json({ mensagem: "Pedido recebido" }); // Adicionei resposta
});

// Rota para adicionar item ao carrinho (CORRIGIDA)
app.post('/carrinho', async (req, res) => {
    let { id_pessoa, id_produto, quantidade } = req.body;
    console.log("Dados recebidos:", req.body);

    if (!id_pessoa || !id_produto || !quantidade) {
        return res.status(400).json({
            codigo: 0,
            mensagem: "Dados incompletos"
        });
    }

    try {
        // Verificar se já existe um carrinho pendente para o usuário (com await)
        let carrinhos = await db.query(
            'SELECT id_carrinho FROM carrinho WHERE id_pessoa = ? AND situacao = ?',
            [id_pessoa, 'P']
        );

        let id_carrinho;

        if (carrinhos.length === 0) {
            // Criar novo carrinho
            let result = await db.query(
                'INSERT INTO carrinho (id_pessoa, situacao) VALUES (?, "P")',
                [id_pessoa]
            );
            id_carrinho = result.insertId;
        } else {
            id_carrinho = carrinhos[0].id_carrinho;
        }

        // Verificar se o produto já está no carrinho (com await)
        const itens = await db.query(
            'SELECT * FROM view_carrinho_produtos WHERE id_pessoa = ? AND id_produto = ? AND situacao_item = ? ',
            [id_pessoa, id_produto,"P"]
        );

        if (itens.length > 0) {
            return res.status(200).json({
                codigo: 1,
                mensagem: "ITEM_DUPLICADO",
                sucesso: false
            });
        }

        // Adicionar item ao carrinho (com await)
        await db.query(
            'INSERT INTO carrinho_itens (id_carrinho, id_produto, quantidade) VALUES (?, ?, ?)',
            [id_carrinho, id_produto, quantidade]
        );

        res.status(201).json({
            codigo: 2,
            mensagem: "Item adicionado ao carrinho com sucesso",
            sucesso: true,
            id_carrinho: id_carrinho
        });

    } catch (error) {
        console.error('Erro no servidor:', error);
        res.status(500).json({
            codigo: -1,
            mensagem: "Erro interno no servidor: " + error.message,
            sucesso: false
        });
    }
});

const porta = process.env.PORTA4 || 3003;
app.listen(porta, () => {
    console.log(`Servidor rodando -backendprodutos- na porta ${porta}`);
});

