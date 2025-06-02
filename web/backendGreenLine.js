require("dotenv").config();
const express = require("express");
const cors = require("cors");
const Database = require("./conexao");
const funcoes = require("./funcoes");
const fs = require("fs");
const path = require("path");
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");
const crypto = require('crypto');

// Configura caminho absoluto para a pasta img/produtos
const uploadDir = path.join(__dirname, '../img/produtos');

// Garante que o diretório existe
if (!fs.existsSync(uploadDir)) {
    fs.mkdirSync(uploadDir, { recursive: true, mode: 0o755 });
}

let estadoLogin = {
    usuario: null,
    id_pessoa: null,
    tipo_usuario: 2,
    quantidade_produtos: 0,
    trocarDeConta: 0,
    ultimaAtualizacao: null
};

const app = express();
app.use(express.json({ limit: '100mb' }));
app.use(cors());
app.use(express.static('public'));

const db = new Database();
const funcoesUteis = new funcoes();
const segredo = process.env.SEGREDO_JWT;

// ==================== ROTAS PADRÃO ====================
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

// ==================== ROTAS DE CADASTRO ====================
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
    } catch (erro) {
        console.error("Erro ao verificar o cpf");
    }
});

// ==================== ROTAS DE VALIDAÇÃO ====================
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

// ==================== ROTAS DE PRODUTOS ====================
app.post('/cadastro-produto', async (req, res) => {
    try {
        const { imagem_1, imagem_2, imagem_3, ...outrosDados } = req.body;

        if (!outrosDados.nome || !outrosDados.categoria) {
            return res.status(400).json({ error: "Nome e categoria são obrigatórios" });
        }

        const processarImagem = async (img, index) => {
            if (!img) return null;
            const nomeArquivo = `produto_${Date.now()}_${index}.jpg`;
            const caminhoCompleto = path.join(uploadDir, nomeArquivo);
            await fs.promises.writeFile(caminhoCompleto, Buffer.from(img, 'base64'));
            return nomeArquivo;
        };

        const [imagem1, imagem2, imagem3] = await Promise.all([
            processarImagem(imagem_1, 1),
            processarImagem(imagem_2, 2),
            processarImagem(imagem_3, 3)
        ]);

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
            produtoId: result.insertId,
            mensagem: "Produto cadastrado com sucesso"
        });

    } catch (error) {
        console.error("Erro no cadastro:", error);
        res.status(500).json({
            error: "Erro interno no servidor",
            detalhes: process.env.NODE_ENV === 'development' ? error.message : undefined
        });
    }
});

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

app.get("/produtos", async (req, res) => {
    try {
        const produtos = await db.query(`
            SELECT * FROM produto 
            WHERE promocao = true AND ativo = true
            ORDER BY id_produto DESC
            LIMIT 12
        `);
        console.log("Produtos encontrados:", produtos);

        res.json({
            success: true,
            data: produtos || []
        });
    } catch (err) {
        console.error("Erro ao buscar produtos:", err);
        res.status(500).json({
            erro: "Erro ao buscar produtos",
            detalhes: process.env.NODE_ENV === "development" ? err.message : undefined
        });
    }
});

// ==================== ROTAS DE CARRINHO ====================
app.post('/buscar-produtos', async (req, res) => {
    try {
        const { id_pessoa } = req.body;

        if (!id_pessoa) {
            return res.status(400).json({
                sucesso: false,
                mensagem: "ID da pessoa é obrigatório"
            });
        }

        console.log(`Buscando produtos para ID: ${id_pessoa}`);
        const resultado = await db.query("SELECT * FROM vw_carrinho_itens_detalhados WHERE id_pessoa = ? AND situacao_item = 'P'", [id_pessoa]);

        return res.json({
            sucesso: true,
            produtos: resultado
        });

    } catch (erro) {
        console.error("Erro ao buscar produtos:", erro);
        return res.status(500).json({
            sucesso: false,
            mensagem: "Erro interno no servidor",
            detalhes: erro.message
        });
    }
});

app.post('/excluir-produtos', async (req, res) => {
    try {
        const { id_produto, id_carrinho } = req.body;

        if (!id_produto && !id_carrinho) {
            return res.status(400).json({
                sucesso: false,
                mensagem: "ID produto e carrinho não estão aqui"
            });
        }

        console.log(`Buscando produtos do ID carrinho: ${id_carrinho}`);

        try {
            await db.query("UPDATE carrinho_itens SET situacao ='R' WHERE id_produto = ? AND id_carrinho = ?;", [id_produto, id_carrinho]);
        } catch (erro) {
            console.log("Erro ao excluir", erro);
            return res.json({
                sucesso: false,
                mensagem: "Item não excluido",
                codigo: 2
            });
        }
        return res.json({
            sucesso: true,
            mensagem: "Item Excluído com sucesso",
            codigo: 3
        });

    } catch (erro) {
        console.error("Erro ao buscar produtos:", erro);
        return res.status(500).json({
            sucesso: false,
            mensagem: "Erro interno no servidor",
            codigo: 1,
            detalhes: erro.message
        });
    }
});

// ==================== ROTAS DE LOGIN ====================
app.post("/loginDados", async (req, res) => {
    console.log("Corpo recebido:", req.body); 

    if (!req.body || !req.body.usuario) {
        return res.status(400).json({ erro: "Usuário é obrigatório" });
    }

    const { usuario, trocar } = req.body;

    try {
        const respostaPessoa = await db.query(
            "SELECT id_pessoa, id_tipo_usuario FROM pessoa WHERE nome = ?",
            [usuario]
        );

        if (respostaPessoa.length > 0) {
            estadoLogin.id_pessoa = Number(respostaPessoa[0].id_pessoa);
            estadoLogin.tipo_usuario = Number(respostaPessoa[0].id_tipo_usuario);

            let respostaCarrinho = await db.query(
                "SELECT SUM(quantidade_pendente) AS numero_carrinho FROM vw_quantidade_pendente WHERE id_pessoa = ?",
                [estadoLogin.id_pessoa]
            );

            estadoLogin.quantidade_produtos = respostaCarrinho.length > 0 ? Number(respostaCarrinho[0].numero_carrinho || 0) : 0;
        } else {
            estadoLogin.id_pessoa = null;
        }

        estadoLogin.usuario = usuario;
        estadoLogin.trocarDeConta = Number(trocar) || 0;
        estadoLogin.ultimaAtualizacao = new Date();

        res.json({
            status: "sucesso",
            usuario: estadoLogin.usuario,
            id_pessoa: estadoLogin.id_pessoa,
            tipo_usuario: estadoLogin.tipo_usuario,
            quantidade: estadoLogin.quantidade_produtos,
            trocar: estadoLogin.trocarDeConta,
            atualizadoEm: estadoLogin.ultimaAtualizacao
        });
    } catch (error) {
        console.error("Erro no login:", error);
        res.status(500).json({ erro: "Erro interno no servidor", detalhes: error.message });
    }
});

app.get("/loginDados", async (req, res) => {
    try {
        let respostaCarrinho = await db.query(
            "SELECT SUM(quantidade_pendente) AS numero_carrinho FROM vw_quantidade_pendente WHERE id_pessoa = ?",
            [estadoLogin.id_pessoa]
        );

        estadoLogin.quantidade_produtos = respostaCarrinho.length > 0 ? Number(respostaCarrinho[0].numero_carrinho || 0) : 0;

        res.json(estadoLogin);
    } catch (error) {
        console.error("Erro ao buscar estado de login:", error);
        res.status(500).json({ erro: "Erro ao buscar estado de login", detalhes: error.message });
    }
});

app.post('/logout', (req, res) => {
    console.log("Requisição de logout recebida");
    let codigo = req.body.codigo || 0;
    console.log("Código de logout recebido:", codigo);
    
    estadoLogin = {
        usuario: null,
        id_pessoa: null,
        tipo_usuario: 2,
        quantidade_produtos: 0,
        trocarDeConta: 0,
        ultimaAtualizacao: null
    };

    console.log("Estado de login após logout:", estadoLogin);
    
    res.json({ status: "sucesso", mensagem: "Usuário desconectado com sucesso" });
});

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

// ==================== ROTAS DE PERFIL ====================
const validarEmail = (email) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);

const gerarHashSenha = async (senha) => {
  const salt = await bcrypt.genSalt(10);
  const hash = await bcrypt.hash(senha, salt);
  return { hash, salt };
};

app.get('/', (req, res) => {
  res.json({
    message: 'API de Usuários Segura',
    endpoints: {
      cadastro: 'POST /pessoa',
      login: 'POST /login',
      listar: 'GET /pessoa',
      buscar: 'GET /pessoa/:id_pessoa',
      atualizar: 'PUT /pessoa/:id_pessoa',
      deletar: 'DELETE /pessoa/:id_pessoa'
    }
  });
});

// [1] CADASTRO (POST)
app.post('/pessoa', async (req, res) => {
    const { nome, email, telefone, cpf, id_tipo_usuario, senha, situacao, imagem_perfil } = req.body;
  
    // Validações
    if (!nome || !email || !senha) {
      return res.status(400).json({ error: 'Nome, e-mail e senha são obrigatórios' });
    }
  
    if (!validarEmail(email)) {
      return res.status(400).json({ error: 'Formato de e-mail inválido' });
    }
  
    if (senha.length < 2) {
      return res.status(400).json({ error: 'Senha deve ter no mínimo 2 caracteres' });
    }
  
    try {
      // Verifica se usuário já existe
      const [existing] = await db.query('SELECT id_pessoa FROM users WHERE email = ?', [email]);
      if (existing.length > 0) {
        return res.status(409).json({ error: 'E-mail já cadastrado' });
      }
  
      // Gera hash da senha
      const { hash, salt } = await gerarHashSenha(password);
  
      // Insere no banco
      const [result] = await db.query('INSERT INTO users SET ?', {
        nome,
        email,
        telefone,
        cpf,
        id_tipo_usuario,
        senha_hash: hash,
        senha_salt: salt,
        situacao,
        imagem_perfil
  
      });
  
      // Retorna resposta (sem dados sensíveis)
      res.status(201).json({
        id_pessoa: result.insertId,
        message: 'Usuário criado com sucesso',
        pessoa: { nome, email, telefone, cpf, id_tipo_usuario, senha, situacao, imagem_perfil }
      });
  
    } catch (err) {
      console.error('Erro no cadastro:', err);
      res.status(500).json({ error: 'Erro interno no servidor' });
    }
  });
  
  // [2] LOGIN (POST)
  app.post('/login', async (req, res) => {
    const { email, senha } = req.body;
  
    if (!email || !senha) {
      return res.status(400).json({ error: 'E-mail e senha são obrigatórios' });
    }
  
    try {
      // Busca pessoa
      const [pessoa] = await db.query('SELECT * FROM pessoa WHERE email = ?', [email]);
      if (pessoa.length === 0) {
        return res.status(401).json({ error: 'Credenciais inválidas' });
      }
  
      const user = pessoa[0];
  
      // Verifica senha
      const senhaValida = await bcrypt.compare(senha, user.senha_hash);
      if (!senhaValida) {
        return res.status(401).json({ error: 'Credenciais inválidas' });
      }
  
      // Gera token simples (em produção, use JWT)
      const token = crypto.randomBytes(16).toString('hex');
  
      res.json({
        id_pessoa: user.id_pessoa,
        nome: user.nome,
        email: user.email,
        telefone: user.telefone,
        cpf: user.cpf,
        id_tipo_usuario: user.id_tipo_usuario,
        situacao: user.situacao,
        imagem_perfil: user.imagem_perfil,
        token // Em produção, use JWT
      });
  
    } catch (err) {
      res.status(500).json({ error: 'Erro no servidor' });
    }
  });
  
  
  // [3] LISTAR USUÁRIOS (GET) - (Protegida em produção)
  app.get('/pessoa', async (req, res) => {
    try {
      const [rows] = await db.query('SELECT * FROM pessoa');
      res.json(rows);
    } catch (err) {
      res.status(500).json({ error: 'Erro ao buscar usuários' });
    }
  });
  
  // [4] BUSCAR USUÁRIO POR ID (GET) - Versão corrigida
  app.get("/pessoa/:id_pessoa", async (req, res) => {
    try {
        console.log("[BACK] Requisição recebida para buscar pessoa. ID:", req.params.id_pessoa);
  
        // Converte o ID para número antes da validação
        const id = Number(req.params.id_pessoa);
        if (isNaN(id) || id <= 0) {
            return res.status(400).json({ error: "ID inválido" });
        }
  
        // Consulta no banco
        const [rows] = await db.query(
            "SELECT id_pessoa, nome, email, telefone, cpf, id_tipo_usuario, situacao, imagem_perfil FROM pessoa WHERE id_pessoa = ?",
            [id]
        );
  
        if (!rows || rows.length === 0) {
            return res.status(404).json({ 
                error: "Usuário não encontrado",
                details: `Nenhum usuário encontrado com o ID ${id}`
            });
        }
  
        // Retorna os dados
        res.json(rows[0]);
    } catch (err) {
        console.error("Erro ao buscar usuário:", err.message);
        res.status(500).json({ 
            error: "Erro ao buscar usuário",
            details: err.message
        });
    }
  });
  // [5] ATUALIZAR USUÁRIO (PUT) - (Protegida em produção)
  app.put('/pessoa/:id_pessoa', async (req, res) => {
    const { nome, telefone} = req.body;
  
    try {
      const [result] = await db.query(
        'UPDATE pessoa SET nome = ?, telefone = ?, ? WHERE id_pessoa = ?',
        [nome, telefone, req.params.id_pessoa]
      );
      
      if (result.affectedRows === 0) {
        return res.status(404).json({ error: 'Usuário não encontrado' });
      }
      
      res.json({ message: 'Usuário atualizado com sucesso' });
    } catch (err) {
      res.status(500).json({ error: 'Erro ao atualizar usuário' });
    }
  });
  
  // [6] DELETAR USUÁRIO (DELETE) - (Protegida em produção)
  app.delete('/pessoa/:id_pessoa', async (req, res) => {
    try {
      const [result] = await db.query('DELETE FROM pessoa WHERE id_pessoa = ?', [req.params.id_pessoa]);
  
      if (result.affectedRows === 0) {
        return res.status(404).json({ error: 'Usuário não encontrado' });
      }
      
      res.status(204).end();
    } catch (err) {
      res.status(500).json({ error: 'Erro ao deletar usuário' });
    }
  });
  
  // [7] OBTER DADOS DO USUÁRIO LOGADO (GET) - Protegida
  // Rota para obter dados do usuário logado
  app.get('/pessoa/me', async (req, res) => {
    try {
      // Em produção, você deve verificar o token JWT aqui
      const token = req.headers.authorization?.split(' ')[1];
      if (!token) {
        return res.status(401).json({ error: 'Token não fornecido' });
      }
  
      // Simples verificação - em produção, decodifique o token JWT para obter o ID
      const userId = req.body.userId || req.query.userId; // Adapte conforme sua autenticação
      
      const [rows] = await db.query(
        'SELECT id_pessoa, nome, email, telefone, cpf, situacao, imagem_perfil FROM pessoa WHERE id_pessoa = ?',
        [userId]
      );
      
      if (rows.length === 0) {
        return res.status(404).json({ error: 'Usuário não encontrado' });
      }
      
      res.json(rows[0]);
    } catch (err) {
      console.error('Erro ao buscar usuário:', err);
      res.status(500).json({ 
        error: 'Erro ao buscar usuário',
        details: err.message 
      });
    }
  });
  
  // [8] ATUALIZAR IMAGEM DE PERFIL (PUT)
  app.put('/pessoa/:id_pessoa/imagem', async (req, res) => {
      try {
          const { imagem_perfil } = req.body;
          const id = parseInt(req.params.id_pessoa);
          
          if (isNaN(id) || id <= 0) {
              return res.status(400).json({ error: 'ID inválido' });
          }
  
          const [result] = await db.query(
              'UPDATE pessoa SET imagem_perfil = ? WHERE id_pessoa = ?',
              [imagem_perfil, id]
          );
          
          if (result.affectedRows === 0) {
              return res.status(404).json({ error: 'Usuário não encontrado' });
          }
          
          res.json({ message: 'Imagem de perfil atualizada com sucesso' });
      } catch (err) {
          console.error('Erro ao atualizar imagem:', err);
          res.status(500).json({ error: 'Erro ao atualizar imagem de perfil' });
      }
  });

// ==================== ROTAS DE PEDIDOS ====================
app.post("/pedidos", async (req, res) => {
    let venda = req.body;
    console.log(venda);
    res.status(200).json({ mensagem: "Pedido recebido" });
});

// ==================== ROTAS DE CARRINHO ====================
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
        let carrinhos = await db.query(
            'SELECT id_carrinho FROM carrinho WHERE id_pessoa = ? AND situacao = ?',
            [id_pessoa, 'P']
        );

        let id_carrinho;

        if (carrinhos.length === 0) {
            let result = await db.query(
                'INSERT INTO carrinho (id_pessoa, situacao) VALUES (?, "P")',
                [id_pessoa]
            );
            id_carrinho = result.insertId;
        } else {
            id_carrinho = carrinhos[0].id_carrinho;
        }

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

// ==================== ROTA DE TESTE ====================
app.get('/teste', (req, res) => {
    res.json({ mensagem: "API está funcionando!" });
});

// ==================== INICIAR SERVIDOR ====================
app.listen(3010, () => {
    console.log("🚀 Servidor rodando em backendCadastro: http://localhost:3010");
});