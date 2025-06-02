require("dotenv").config();
const express = require("express");
const cors = require("cors");
const Database = require("./conexao");
const funcoes = require("./funcoes");
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");
const path = require("path");

const app = express();
app.use(express.json());
app.use(cors());
app.use(express.static('public'));

const db = new Database();
const funcoesUteis = new funcoes();
const segredo = process.env.SEGREDO_JWT;


// Cadastro de usuário
app.post("/cadastrar", async (req, res) => {
    const { nome, email, cpf, telefone, senha } = req.body;

    const inserirPessoa = `INSERT INTO pessoa(nome, email, cpf, telefone,id_tipo_usuario, senha, situacao, imagem_perfil) 
    VALUES (?, ?, ?, ?, 2, ?, 'P', 'perfil.png')`;
    const selecionarId = `SELECT id_pessoa FROM pessoa WHERE email = ? ORDER BY id_pessoa DESC LIMIT 1`;

    try {
        const senhaCriptografada = await bcrypt.hash(senha, 10);
        await db.query(inserirPessoa, [nome, email, cpf, telefone, senhaCriptografada]);

        const resultadoId = await db.query(selecionarId, [email]);
        if (resultadoId.length === 0) {
            return res.status(400).json({ erro: "Erro ao recuperar o ID da pessoa." });
        }
        try {
            await funcoesUteis.enviarEmail(email);
        } catch (erro) {
            console.log("Erro ao enviar o email");
            return;
        }
        res.status(200).json({ mensagem: "Cadastro realizado com sucesso! Verifique seu e-mail para confirmação." });

    } catch (err) {
        console.error("Erro no cadastro:", err);
        res.status(500).json({ erro: "Erro durante o processo de cadastro." });
    }
});

// Validação do token (acesso via link de e-mail)
app.get("/validar", async (req, res) => {
    const { token } = req.query;
    const atualizarSituacao = "UPDATE pessoa SET situacao = 'A' WHERE id_pessoa = ?";
    const sql = "SELECT id_pessoa FROM pessoa WHERE situacao = 'P' AND email = ?;";

    try {
        const payload = jwt.verify(token, segredo);
        const email = payload.email;

        const resultado = await db.query(sql, [email]);

        if (resultado.length > 0) {
            const id_pessoa = resultado[0].id_pessoa;
            try {
                await db.query(atualizarSituacao, [id_pessoa]);
                res.sendFile(path.join(__dirname, "../public", "confirmacaoLogin.html"));
            } catch (erro) {
                console.error("Erro ao atualizar:", erro);
                res.sendFile(path.join(__dirname, "../public", "erro.html"));
            }
        } else {
            res.sendFile(path.join(__dirname, "../public", "erro.html"));

        }
    } catch (erro) {
        console.error("Token inválido ou expirado:", erro);
        res.sendFile(path.join(__dirname, "..", "tokenExpirado.html"));

    }
});


app.get("/verificarEmail", async (req, res) => {
    const { email } = req.query;
    const sql = "SELECT COUNT(*) AS total FROM pessoa WHERE email = ?";

    try {
        const verificacao = await db.query(sql, [email]);
        const existe = verificacao[0].total > 0;

        res.json({ existe });
    } catch (erro) {
        console.error("Erro ao verificar o email: ", erro);
        res.status(500);
    }
});
app.get("/verificarCPF", async (req, res) => {
    const { cpf } = req.query;
    const sql = "SELECT COUNT(*) AS total FROM pessoa WHERE cpf = ?";

    try {
        const verificacao = await db.query(sql, [cpf]);
        const existe = verificacao[0].total > 0;
        res.json({ existe });
    }
    catch (erro) {
        console.error("Erro ao verificar o cpf");
    }
})

// Iniciar servidor
app.listen(process.env.PORTA, () => {
    console.log("🚀 Servidor rodando em backendCadastro: http://localhost:3000");
});
