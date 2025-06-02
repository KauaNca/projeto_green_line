require("dotenv").config();
const express = require("express");
const cors = require("cors");
const fs = require("fs");
const path = require("path");
const conexao = require('./conexao.js');
const app = express();
const db = new conexao();

// Configurações
app.use(express.json({ limit: '100mb' }));
app.use(cors({
    origin: process.env.FRONTEND_URL || '*',
    methods: ['GET', 'POST']
}));

// Configura caminho absoluto para a pasta img/produtos
const uploadDir = path.join(__dirname, '../img/produtos');

// Garante que o diretório existe
if (!fs.existsSync(uploadDir)) {
    fs.mkdirSync(uploadDir, { recursive: true, mode: 0o755 });
}

app.post('/cadastro-produto', async (req, res) => {
    try {
        const { imagem_1, imagem_2, imagem_3, ...outrosDados } = req.body;

        // Validação básica
        if (!outrosDados.nome || !outrosDados.categoria) {
            return res.status(400).json({ error: "Nome e categoria são obrigatórios" });
        }

        // Processamento de imagens
        const processarImagem = async (img, index) => {
            if (!img) return null;

            const nomeArquivo = `produto_${Date.now()}_${index}.jpg`;
            const caminhoCompleto = path.join(uploadDir, nomeArquivo);

            await fs.promises.writeFile(caminhoCompleto, Buffer.from(img, 'base64'));
            return nomeArquivo;
        };

        // Processa imagens em paralelo (melhor performance)
        const [imagem1, imagem2, imagem3] = await Promise.all([
            processarImagem(imagem_1, 1),
            processarImagem(imagem_2, 2),
            processarImagem(imagem_3, 3)
        ]);

        // Inserção no banco (usando sua classe Conexao atual)
        const sql = `
      INSERT INTO produto (
        produto, descricao, descricao_curta, preco, 
        preco_promocional, promocao, marca, avaliacao, 
        quantidade_avaliacoes, estoque, parcelas_permitidas, 
        peso_kg, dimensoes, ativo, imagem_1, imagem_2, imagem_3, categoria
      ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    `;

        const result = await db.query(sql, [
            outrosDados.nome,
            outrosDados.descricao_detalhada || '',
            outrosDados.descricao_curta || '',
            parseFloat(outrosDados.preco) || 0,
            parseFloat(outrosDados.preco_promocional) || 0,
            outrosDados.promocao ? 1 : 0,
            outrosDados.marca || '',
            parseInt(outrosDados.avaliacao) || 0,
            parseInt(outrosDados.quantidade_avaliacao) || 0,
            parseInt(outrosDados.estoque) || 0,
            parseInt(outrosDados.parcelas) || 0,
            parseFloat(outrosDados.peso) || 0,
            outrosDados.dimensoes || '0x0x0',
            outrosDados.ativo ? 1 : 0,
            imagem1,
            imagem2,
            imagem3,
            outrosDados.categoria
        ]);

        res.status(201).json({
            success: true,
            produtoId: result.insertId, // Assumindo que seu MySQL retorna isso
            mensagem: "Produto cadastrado com sucesso"
        });

    } catch (error) {
        console.error("Erro no cadastro:", error);

        // Limpeza opcional (remove imagens se o banco falhou)
        if (imagem1) fs.unlinkSync(path.join(uploadDir, imagem1));
        if (imagem2) fs.unlinkSync(path.join(uploadDir, imagem2));
        if (imagem3) fs.unlinkSync(path.join(uploadDir, imagem3));

        res.status(500).json({
            error: "Erro interno no servidor",
            detalhes: process.env.NODE_ENV === 'development' ? error.message : undefined
        });
    }
});

// Iniciar servidor
const PORT = process.env.PORTA6 || 3005;
app.listen(PORT, () => {
    console.log(`Servidor rodando - backendCadastroProduto - na porta ${PORT}`);
    console.log(`Uploads sendo salvos em: ${uploadDir}`);
});