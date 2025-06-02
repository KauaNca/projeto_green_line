require('dotenv').config();
const express = require('express');
const mysql = require('mysql2/promise');
const bcrypt = require('bcryptjs');
const crypto = require('crypto');


const app = express();
app.use(express.json());

// Configuração do MySQL
let db;
mysql.createConnection({
  database: process.env.DB_NAME,
  host: process.env.DB_HOST,
  user: process.env.DB_USER ,
  password: process.env.DB_PASSWORD ,
  port: process.env.DB_PORT 

})
.then(connection => {
  db = connection;
  console.log('✅ Conectado ao MySQL');
})
.catch(err => {
  console.error('❌ Erro no MySQL:', err.message);
});

// ==================== FUNÇÕES AUXILIARES ====================
const validarEmail = (email) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);

const gerarHashSenha = async (senha) => {
  const salt = await bcrypt.genSalt(10);
  const hash = await bcrypt.hash(senha, salt);
  return { hash, salt };
};

const cors = require('cors');
app.use(cors()); // Libera acesso de qualquer origem

// ==================== ROTAS ====================

// Rota raiz (documentação básica)
app.get('/', (req, res) => {
  res.json({
    message: 'API de Usuários Segura',
    endpoints: {
      cadastro: 'POST /pessoa',
      login: 'POST /login',
      listar: 'GET /pessoa',
      buscar: 'GET /pessoa/:id_pessoa',
      atualizar: 'PUT /pessoa/:id_pessoa (autenticado)',
      deletar: 'DELETE /pessoa/:id_pessoa (autenticado)'
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

// ==================== INICIAR SERVIDOR ====================
const PORTA8 = process.env.PORTA8 || 3008;
app.listen(PORTA8, () => {
  console.log(`✅ Servidor rodando -backendPerfil- na porta ${PORTA8}`);
  console.log(`🔗 Acesse a documentação em http://localhost:${PORTA8}/`);
  console.log(`🔗 Acesse SERVER a API em http://localhost:${PORTA8}/pessoa`);
  console.log(`🔗 Acesse SERVER a API em http://localhost:${PORTA8}/pessoa/:id_pessoa`);
});