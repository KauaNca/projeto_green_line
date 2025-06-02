require("dotenv").config();
const express = require("express");
const cors = require("cors");
const Funcoes = require("./funcoes");
const app = express();

// Configurações
app.use(express.json());
app.use(cors());

const funcoesUteis = new Funcoes();

// Rotas
app.get("/enviar-email", async (req, res) => {
    const email = req.query.email?.trim();
    if (!email || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
        return res.status(400).json({ conclusao: 1, mensagem: "Email inválido" });
    }

    try {
        await funcoesUteis.enviarEmail(email);
        return res.json({ conclusao: 2, mensagem: "Email enviado com sucesso" });
    } catch (erro) {
        console.error("Erro ao enviar o email:", erro);
        return res.status(500).json({ conclusao: 3, mensagem: "Falha ao enviar email" });
    }
});

// Iniciar servidor
const PORT = process.env.PORTA5 || 3004;
app.listen(PORT, () => {
    console.log(`Servidor rodando -backendPadrao- na porta ${PORT}`);
});