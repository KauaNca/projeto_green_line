// Configurações globais
const config = {
  apiUrl: 'https://green-line-web.onrender.com',
  apiUrlCarrinho: 'https://green-line-web.onrender.com',
  fallbackImage: 'data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48cmVjdCB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgZmlsbD0iI2VlZSIvPjx0ZXh0IHg9IjEwMCIgeT0iMTAwIiBmb250LWZhbWlseT0iQXJpYWwiIGZvbnQtc2l6ZT0iMTYiIHRleHQtYW5jaG9yPSJtaWRkbGUiIGRvbWluYW50LWJhc2VsaW5lPSJtaWRkbGUiIGZpbGw9IiM5OTkiPk5lbmh1bWEgSW1hZ2VtPC90ZXh0Pjwvc3ZnPg=='
};
// Inicialização
document.addEventListener("DOMContentLoaded", inicializarApp);

// Cache de elementos DOM
const elementos = {
  carrossel: document.getElementById('carousel-imagens'),
  carrosselContainer: document.getElementById('carousel-index'),
  produtosContainer: document.getElementById('container-produtos'),
  iconeUsuario: document.getElementById('icone-usuario'),
  produtoModal: new bootstrap.Modal('#produtoModal'),
  modalAlert: document.getElementById('modal-alert')
};

// Estado da aplicação
let estado = {
  imagensCarrossel: [],
  produtos: [],
  carregando: false,
  id_pessoa: null,
  id_produto: null,
  quantidadeProdutos: 1,
};


// ==================== FUNÇÕES PRINCIPAIS ====================

async function inicializarApp() {
  try {
    estado.carregando = true;    
    await Promise.all([
      carregarProdutosPromocao()
    ]);
  } catch (erro) {
    console.error('Erro na inicialização:', erro);
    mostrarFeedback('Opa, algo deu errado! Tente recarregar.', 'danger');
  } finally {
    estado.carregando = false;
  }
}

// ==================== FUNÇÕES DE CARREGAMENTO ====================
async function carregarProdutosPromocao() {
  try {
    mostrarLoader();

    const response = await fetch(`${config.apiUrl}/produtos`);
    const { success, data, message } = await response.json();

    if (!success || !Array.isArray(data)) {
      throw new Error(message || 'Dados inválidos');
    }

    estado.produtos = data.map(sanitizarProduto);
    renderizarProdutos();

  } catch (erro) {
    console.error('Erro:', erro);
    mostrarErroCarregamento();
  }
}

// ==================== FUNÇÕES DE RENDERIZAÇÃO ====================

function renderizarProdutos() {
  console.log('Renderizando produtos:', estado.produtos);
  elementos.produtosContainer.innerHTML = '';

  if (!estado.produtos.length) {
    elementos.produtosContainer.innerHTML = `
      <div class="col-12 text-center py-5">
        <i class="bi bi-tag fs-1 text-muted"></i>
        <p class="text-muted mt-2">Nenhum produto em promoção no momento</p>
      </div>
    `;
    return;
  }

  const fragment = document.createDocumentFragment();

  estado.produtos.forEach(produto => {
    const card = criarCardProduto(produto);
    fragment.appendChild(card);
  });

  elementos.produtosContainer.appendChild(fragment);
}

function criarCardProduto(produto) {
  const card = document.createElement('div');
  card.className = 'col-12 col-sm-6 col-md-4 col-lg-3 mb-4';
  card.setAttribute('data-id', produto.id_produto);

  const dados = {
    nome: produto.produto || 'Produto',
    descricao: produto.descricao || 'Descrição não disponível',
    preco: Number(produto.preco) || 0,
    preco_promocional: produto.preco_promocional || 0,
    promocao: Boolean(produto.promocao),
    imagem: produto.imagem_1 || config.fallbackImage,
    categoria: produto.categoria || 'Geral',
    promocao: Boolean(produto.promocao),
    avaliacao: Math.min(Math.max(Number(produto.avaliacao) || 0, 5), 5),
    avaliacoes: Math.max(Number(produto.quantidade_avaliacoes) || 0, 0),
    id_produto: card.getAttribute('data-id'),
  };

  const precoFormatado = dados.promocao
    ? `<span style="text-decoration: line-through; font-size: 0.9rem;">R$ ${dados.preco.toFixed(2)}</span>
       <span class="fs-5 ms-2 text-danger">R$ ${(dados.preco * 0.8).toFixed(2)}</span>`
    : `<span class="fs-5">R$ ${dados.preco.toFixed(2)}</span>`;

  const dadosEscapados = escapeHtml(JSON.stringify(dados));

  card.innerHTML = `
    <div class="card h-100" style="width:350px" onclick="abrirModalProduto('${dadosEscapados}')">
      <img src="${dados.imagem}" 
           class="card-img-top" 
           alt="${dados.nome}"
           loading="lazy"
           style="height: 200px; object-fit: contain;"
           onerror="this.src='${config.fallbackImage}'">
      <div class="card-body">
        <h6 class="card-title">${dados.nome}</h6>
        <div class="d-flex justify-content-between align-items-center mb-2">
          <span class="text-warning">${gerarEstrelas(dados.avaliacao)}</span>
          <small class="text-muted">${dados.avaliacoes} av.</small>
        </div>
        <p class="card-text text-muted small">${dados.descricao.substring(0, 60)}...</p>
        <p class="fw-bold mb-0 ${dados.promocao ? 'text-dark' : ''}">
          ${precoFormatado}
        </p>
      </div>
    </div>
  `;

  return card;
}

function abrirModalProduto(dadosProduto) {
  let produto;
  try {
    produto = typeof dadosProduto === 'string' ? JSON.parse(dadosProduto) : dadosProduto;
  } catch (e) {
    console.error('JSON inválido:', e);
    mostrarFeedback('Erro ao abrir produto', 'danger');
    return;
  }

  console.log('Abrindo modal para produto:', produto);
  estado.id_produto = produto.id_produto || null;
  console.log('ID do produto:', estado.id_produto);
  if (!estado.id_produto) {
    console.error('ID do produto não encontrado');
    mostrarFeedback('Produto inválido', 'danger');
    return;
  }
  
  // Preencher dados do modal
  document.getElementById('produtoModalLabel').textContent = produto.nome;
  
  const imgEl = document.getElementById('produtoModalImagem');
  imgEl.src = produto.imagem || config.fallbackImage;
  imgEl.onerror = () => imgEl.src = config.fallbackImage;
  
  document.getElementById('produtoModalDescricao').textContent = produto.descricao;
  document.getElementById('produtoModalMarca').textContent = produto.marca;
  
  const catEl = document.getElementById('produtoModalCategoria');
  if (catEl) {
    catEl.textContent = produto.categoria;
    catEl.className = `badge mb-2 bg-${produto.promocao ? 'danger' : 'success'}`;
  }
  
  
  // ÁREA REFORMULADA - EXIBIÇÃO DE PREÇOS
  const precoContainer = document.getElementById('produtoModalPreco');
  const precoBase = (Number(produto.preco) || 0).toLocaleString('pt-BR', {
    style: 'currency',
    currency: 'BRL'
  });
  
  if (produto.promocao && produto.preco_promocional) {
    const precoPromo = (Number(produto.preco_promocional) || 0).toLocaleString('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    });
    
    precoContainer.innerHTML = `
      <div class="price-container">
        <span class="original-price">${precoBase}</span>
        <span class="discount-price">${precoPromo}</span>
        ${produto.preco_promocional ? 
          `<span class="discount-badge">
            ${Math.round(100 - (produto.preco_promocional / produto.preco * 100))}% OFF
          </span>` : ''
        }
      </div>
    `;
  } else {
    precoContainer.innerHTML = `
      <div class="price-container">
        <span class="current-price">${precoBase}</span>
      </div>
    `;
  }
  
  // Renderizar estrelas de avaliação
  const stars = document.getElementById('produtoModalAvaliacao');
  stars.innerHTML = '';
  console.log('Avaliação do produto:', produto.avaliacao);
  
  if (produto.avaliacao != null) {
    const fullStars = Math.floor(produto.avaliacao);
    const halfStar = produto.avaliacao % 1 >= 0.5;
    const emptyStars = 5 - fullStars - (halfStar ? 1 : 0);
    
    for (let i = 0; i < fullStars; i++) stars.innerHTML += '<i class="fas fa-star text-yellow-400 text-xs"></i>';
    if (halfStar) stars.innerHTML += '<i class="fas fa-star-half-alt text-yellow-400 text-xs"></i>';
    for (let i = 0; i < emptyStars; i++) stars.innerHTML += '<i class="far fa-star text-yellow-400 text-xs"></i>';
    stars.innerHTML += `<span class="text-xs text-gray-600 ml-1">(${produto.avaliacoes})</span>`;
  }
  
  elementos.produtoModal.show();
}

// Função auxiliar para renderizar estrelas
function renderizarEstrelas(avaliacao) {
  if (!avaliacao) return '<span class="text-sm text-gray-500">Sem avaliações</span>';
  
  const fullStars = Math.floor(avaliacao);
  const halfStar = avaliacao % 1 >= 0.5;
  const emptyStars = 5 - fullStars - (halfStar ? 1 : 0);
  
  let html = '';
  for (let i = 0; i < fullStars; i++) html += '<i class="fas fa-star text-yellow-400"></i>';
  if (halfStar) html += '<i class="fas fa-star-half-alt text-yellow-400"></i>';
  for (let i = 0; i < emptyStars; i++) html += '<i class="far fa-star text-yellow-400"></i>';
  
  return html;
}

// ==================== FUNÇÕES AUXILIARES ====================

function sanitizarProduto(produto) {
  return {
    ...produto,
    preco: Number(produto.preco) || 0,
    avaliacoes: Number(produto.avaliacoes) || 0,
    estoque: Boolean(produto.estoque),
    promocao: Boolean(produto.promocao)
  };
}

function escapeHtml(str) {
  return str.replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;")
    .replace(/"/g, "&quot;")
    .replace(/'/g, "&#39;");
}

function gerarEstrelas(nota) {
  const estrelasCheias = Math.floor(nota);
  const temMeia = nota % 1 >= 0.5;
  return '★'.repeat(estrelasCheias) + (temMeia ? '½' : '') + '☆'.repeat(5 - estrelasCheias - (temMeia ? 1 : 0));
}

function mostrarLoader() {
  elementos.produtosContainer.innerHTML = `
    <div class="col-12 text-center py-5">
      <div class="spinner-border text-success"></div>
      <p class="text-muted mt-2">Carregando produtos...</p>
    </div>
  `;
}

function mostrarErroCarregamento() {
  elementos.produtosContainer.innerHTML = `
    <div class="col-12 text-center py-5">
      <i class="bi bi-emoji-frown fs-1 text-danger"></i>
      <p class="text-muted mt-2">Erro ao carregar produtos</p>
      <button class="btn btn-sm btn-outline-success" onclick="carregarProdutosPromocao()">
        <i class="bi bi-arrow-repeat"></i> Tentar novamente
      </button>
    </div>
  `;
}

function mostrarFeedback(mensagem, tipo = 'success') {
  const toast = document.createElement('div');
  toast.className = `toast align-items-center text-white bg-${tipo} border-0 position-fixed bottom-0 end-0 m-3`;
  toast.innerHTML = `
    <div class="d-flex">
      <div class="toast-body">${mensagem}</div>
      <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
    </div>
  `;
  document.body.appendChild(toast);
  new bootstrap.Toast(toast).show();
  setTimeout(() => toast.remove(), 5000);
}

// ==================== EVENT LISTENERS ====================

document.addEventListener('DOMContentLoaded', () => {
  // Controle de quantidade
  document.querySelectorAll('[data-action="increase"]').forEach(btn =>
    btn.addEventListener('click', () => adjustQuantity('quantidadeModal', 1))
  );
  document.querySelectorAll('[data-action="decrease"]').forEach(btn =>
    btn.addEventListener('click', () => adjustQuantity('quantidadeModal', -1))
  );

    // Botão "Comprar Agora"
document.getElementById('btnComprarAgora').addEventListener('click', async () => {
  console.log('Botão Comprar Agora clicado');
  let usuario = localStorage.getItem('id_pessoa');
    if (usuario) {
      estado.id_pessoa = Number(usuario);
      console.log(`Usuário autenticado: ${estado.id_pessoa}`);
    }
  // Verificar se o usuário está logado
  console.log('id_pessoa:', estado.id_pessoa);

  // Validação de login
  if (!estado.id_pessoa || estado.id_pessoa === '' || estado.id_pessoa === 'null') {
    console.log('Usuário não logado, exibindo feedback');
    mostrarFeedback("Por favor, faça login ou cadastro para prosseguir", "danger");
    return;
  }

  // Validação da quantidade
  const quantidadeInput = document.getElementById('quantidadeModal');
  if (!quantidadeInput) {
    console.error('Elemento quantidadeModal não encontrado');
    mostrarFeedback("Erro interno: elemento de quantidade não encontrado", "danger");
    return;
  }
  const qtd = parseInt(quantidadeInput.value, 10);
  console.log('Quantidade selecionada:', qtd);
  if (isNaN(qtd) || qtd <= 0) {
    console.log('Quantidade inválida, exibindo feedback');
    mostrarFeedback("Quantidade inválida!", 'danger');
    return;
  }

  // Extrair dados do produto
  const nome_produto = document.getElementById('produtoModalLabel').textContent;
const precoElement = document.getElementById('produtoModalPreco');
let precoText;

// Obter o texto do preço de forma mais segura
if (precoElement) {
  const discountPrice = precoElement.querySelector('span.discount-price');
  const currentPrice = precoElement.querySelector('span.current-price');
  
  precoText = discountPrice ? discountPrice.textContent : 
             currentPrice ? currentPrice.textContent : '';
} else {
  console.error('Elemento de preço não encontrado');
  mostrarFeedback("Erro: elemento de preço não encontrado", "danger");
  return;
}
  console.log('Texto do preço:', precoText);

  // Validação do preço melhorada
let preco_final;
try {
  // Remove todos os caracteres não numéricos exceto vírgula e ponto
  const cleaned = precoText.replace(/[^\d,.-]/g, '')
                          .replace(/\./g, '')  // Remove pontos de milhar
                          .replace(',', '.');   // Converte vírgula decimal para ponto
  
  preco_final = parseFloat(cleaned);
  
  if (isNaN(preco_final)) {
    throw new Error('Preço inválido após limpeza');
  }
  
  console.log('Preço convertido:', preco_final);
} catch (error) {
  console.error('Erro ao converter preço:', error);
  console.error('Texto original:', precoText);
  mostrarFeedback("Erro: preço do produto inválido", "danger");
  return;
}
  
  const subtotal = (preco_final * qtd);
  const dadosCompra = {
    nome_produto,
    preco_final,
    quantidade: qtd,
    subtotal,
    id_pessoa: estado.id_pessoa,
    data: new Date().toISOString(),
  };

  console.log("Dados da compra:", dadosCompra);
  localStorage.setItem("dadosCompra", JSON.stringify(dadosCompra));
  console.log('Redirecionando para vendas.html');
  window.location.href = "public/vendas.html";
});

// Botão "Adicionar ao Carrinho"
document.getElementById('btnAddCarrinho').addEventListener('click', async () => {
  // Validação de login
  let usuario = localStorage.getItem('id_pessoa');
    if (usuario) {
      estado.id_pessoa = Number(usuario);
      console.log(`Usuário autenticado: ${estado.id_pessoa}`);
    }
  if (!estado.id_pessoa ||  estado.id_pessoa == '' || estado.id_pessoa == 'null') {
    console.log('Usuário não logado, exibindo feedback');
    mostrarFeedback("Por favor, faça login ou cadastro para prosseguir", "danger");
    return;
  }

  const quantidade = parseInt(document.getElementById('quantidadeModal').value, 10);
  if (isNaN(quantidade) || quantidade <= 0) {
    mostrarFeedback("Quantidade inválida!", 'danger');
    return;
  }

  try {
    const requisicao = await fetch(`${config.apiUrlCarrinho}/carrinho`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        id_pessoa: estado.id_pessoa,
        id_produto: estado.id_produto,
        quantidade
      })
    });

    const resposta = await requisicao.json();
    const itemResultado = document.getElementById('item-resultado');

    if (resposta.codigo === 1 || resposta.mensagem === "ITEM_DUPLICADO") {
      itemResultado.classList.remove('d-none');
      itemResultado.innerHTML = "⚠️ Este item já está no seu carrinho";
      setTimeout(() => itemResultado.classList.add('d-none'), 500);
    } else if (resposta.sucesso) {
      let badge = document.getElementById('badge-carrinho');
      badge.textContent = parseInt(badge.textContent || '0') + 1;
      itemResultado.classList.remove('d-none');
      itemResultado.innerHTML = "✔️ Item adicionado ao carrinho!";
      itemResultado.classList.add('text-success');
      setTimeout(() => {
        itemResultado.classList.add('d-none');
        itemResultado.classList.remove('text-success');
      }, 5000);
    } else {
      itemResultado.classList.remove('d-none');
      itemResultado.innerHTML = `❌ Erro: ${resposta.mensagem || "Erro desconhecido"}`;
      itemResultado.classList.add('text-danger');
      setTimeout(() => itemResultado.classList.add('d-none'), 500);
    }
  } catch (erro) {
    console.error("Erro ao adicionar ao carrinho:", erro);
    const itemResultado = document.getElementById('item-resultado');
    itemResultado.classList.remove('d-none');
    itemResultado.innerHTML = "❌ Falha na conexão com o servidor";
    itemResultado.classList.add('text-danger');
    setTimeout(() => itemResultado.classList.add('d-none'), 500);
  }
});
});

// Função auxiliar para ajustar quantidade
function adjustQuantity(id, change) {
  const input = document.getElementById(id);
  let value = parseInt(input.value) || 0;
  value += change;
  if (value < 1) value = 1;
  input.value = value;
}

