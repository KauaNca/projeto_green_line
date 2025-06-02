require("dotenv").config();
const express = require('express');
const database = require('./conexao');
const funcoes = require('./funcoes');
const cors = require('cors');
const path = require('path');

const funcoesUteis = new funcoes();
const app = express();
const bcrypt = require('bcryptjs');
app.use(express.static(path.join(__dirname, '..')));
const db = new database();

app.use(express.json());
app.use(cors({ origin: '*' }));

app.post('/verificarConta', async (req, res) => {
    try {
        const { usuario, senha } = req.body;

        if (!usuario || !senha) {
            return res.status(400).json({ dadosValidos: -1, mensagem: "Usuário e senha são obrigatórios." });
        }

        let sql = usuario.includes("@")
            ? `SELECT * FROM pessoa WHERE email = ?`
            : `SELECT * FROM pessoa WHERE nome = ?`;

        const pesquisa = await db.query(sql, [usuario]);

        if (pesquisa.length === 0) {
            return res.json({ dadosValidos: 0, mensagem: "Conta não encontrada." });
        }

        const { id_pessoa, situacao, senha: senhaHash } = pesquisa[0];

        if (situacao !== 'A') {
            return res.json({ dadosValidos: 1, mensagem: "Conta pendente ou bloqueada." });
        }

        const senhaCorreta = await bcrypt.compare(senha, senhaHash);
        if (!senhaCorreta) {
            return res.json({ dadosValidos: 3, mensagem: "Usuário ou senha incorretos." });
        }

        // Retorna o id_pessoa no caso de sucesso
        return res.json({ 
            dadosValidos: 2, 
            mensagem: "Autenticação bem-sucedida.",
            id_pessoa: id_pessoa,
            nome: pesquisa[0].nome
        });

    } catch (error) {
        console.error("Erro ao verificar conta:", error);
        return res.status(500).json({ error: "Erro interno do servidor. Tente novamente mais tarde." });
    }
});
app.post("/enviarEmail", async (req, res) => {
    const { usuario } = req.body;
    await funcoesUteis.enviarEmail(usuario);
    res.json({ mensagem: "E-mail enviado com sucesso." });
});



app.listen(process.env.PORTA2, () => {
    console.log(`🚀 Servidor rodando -backendLogin- em http://localhost:${process.env.PORTA2}`);
});
