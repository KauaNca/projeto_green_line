// Configurações globais
const config = {
  apiUrl: 'https://green-line-web.onrender.com',
  produtosPorPagina: 8,
  fallbackImage: 'data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48cmVjdCB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgZmlsbD0iI2VlZSIvPjx0ZXh0IHg9IjEwMCIgeT0iMTAwIiBmb250LWZhbWlseT0iQXJpYWwiIGZvbnQtc2l6ZT0iMTYiIHRleHQtYW5jaG9yPSJtaWRkbGUiIGRvbWluYW50LWJhc2VsaW5lPSJtaWRkbGUiIGZpbGw9IiM5OTkiPk5lbmh1bWEgSW1hZ2VtPC90ZXh0Pjwvc3ZnPg=='
};

// Estado da aplicação
let estado = {
  produtos: [],
  paginaAtual: 1,
  id_pessoa: null,
  id_produto: null,
  quantidadeProduto: null,
  carregando: false,
  filtrosAtivos: {
    textoBusca: '',
    emEstoque: false,
    foraEstoque: false,
    categorias: []
  }
};

// Elementos DOM
const elementos = {
  produtosContainer: document.getElementById('container-produtos'),
  paginacao: document.querySelector('.pagination'),
  filtrosAplicados: document.querySelector('.filter-applied'),
  inputBusca: document.getElementById('inputBusca'),
  statusEstoque: document.getElementById('status-estoque'),
  statusForaEstoque: document.getElementById('status-fora-estoque'),
  categoriaCheckboxes: document.querySelectorAll('input[id^="cat-"]'),
  produtoModal: new bootstrap.Modal('#produtoModal'),
  modalAlert: document.getElementById('modal-alert')
};

// Utilitários
function escapeHtml(str) {
  return str.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;").replace(/'/g, "&#39;");
}

function gerarEstrelas(nota) {
  if (!nota) return '☆☆☆☆☆';
  const estrelasCheias = Math.floor(nota);
  const temMeia = nota % 1 >= 0.5;
  return '★'.repeat(estrelasCheias) + (temMeia ? '½' : '') + '☆'.repeat(5 - estrelasCheias - (temMeia ? 1 : 0));
}

function mostrarFeedback(mensagem, tipo = 'success') {
  const toast = document.createElement('div');
  toast.className = `toast align-items-center text-white bg-${tipo} border-0 position-absolute top-50 start-50 translate-middle`;
  toast.style.zIndex = 1100; 
  toast.innerHTML = `
    <div class="d-flex">
      <div class="toast-body">${escapeHtml(mensagem)}</div>
      <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
    </div>
  `;
  document.body.appendChild(toast);
  new bootstrap.Toast(toast).show();
  setTimeout(() => toast.remove(), 5000);
}

function mostrarLoader() {
  elementos.produtosContainer.innerHTML = `
    <div class="col-12 text-center py-5">
      <div class="spinner-border text-primary"></div>
      <p class="text-muted mt-2">Carregando produtos...</p>
    </div>
  `;
}

function mostrarErroCarregamento() {
  elementos.produtosContainer.innerHTML = `
    <div class="col-12 text-center py-5">
      <i class="bi bi-emoji-frown fs-1 text-danger"></i>
      <p class="text-muted mt-2">Erro ao carregar produtos</p>
      <button class="btn btn-sm btn-outline-primary" onclick="carregarProdutos()">
        <i class="bi bi-arrow-repeat"></i> Tentar novamente
      </button>
    </div>
  `;
}

// Funções de manipulação de produtos
function sanitizarProduto(produto) {
  return {
    ...produto,
    id_produto: parseInt(produto.id_produto) || null,
    preco: Number(produto.preco) || 0,
    preco_promocional: produto.preco_promocional || 0,
    promocao: Boolean(produto.promocao),
    avaliacao: Number(produto.avaliacao) || 0,
    numAvaliacoes: Number(produto.numAvaliacoes) || 0,
    estoque: Number(produto.estoque),
    nome: produto.produto || produto.nome || 'Produto sem nome',
    descricao: produto.descricao || 'Sem descrição',
    categoria: produto.categoria || 'Geral',
    marca: produto.marca || 'Sem marca',
    imagem_1: produto.imagem_1 || config.fallbackImage
  };
}

async function carregarProdutos() {
  try {
    mostrarLoader();
    const response = await fetch(`${config.apiUrl}/produto`);
    if (!response.ok) throw new Error(`HTTP ${response.status}`);
    const data = await response.json();
    console.log('Produtos recebidos:', data);
    if (Array.isArray(data)) {
      estado.produtos = data.map(sanitizarProduto);
      aplicarFiltros();
    } else {
      throw new Error('Dados inválidos recebidos da API');
    }
  } catch (erro) {
    console.error('Erro ao carregar produtos:', erro);
    mostrarErroCarregamento();
  }
}

// Funções de renderização
function criarCardProduto(produto) {
  const card = document.createElement('div');
  card.className = 'col-12 col-sm-6 col-md-4 col-lg-3 mb-4';
  const precoFormatado = produto.promocao
    ? `<span style="text-decoration: line-through; font-size: 0.9rem;">R$ ${produto.preco.toFixed(2)}</span>
       <span class="fs-5 ms-2">R$ ${(produto.preco * 0.8).toFixed(2)}</span>`
    : `<span class="fs-5">R$ ${produto.preco.toFixed(2)}</span>`;
  card.innerHTML = `
    <div class="card h-100 cursor point" onclick="abrirModalProduto(${JSON.stringify(produto).replace(/"/g, '&quot;')})">
      <img src="${produto.imagem_1}" 
           class="card-img-top" 
           alt="${escapeHtml(produto.nome)}"
           loading="lazy"
           style="height: 200px; object-fit: contain;"
           onerror="this.src='${config.fallbackImage}'">
      <div class="card-body">
        <h6 class="card-title">${escapeHtml(produto.nome)}</h6>
        <div class="d-flex justify-content-between align-items-center mb-2">
          <span class="text-warning">${gerarEstrelas(produto.avaliacao)}</span>
          <small class="text-muted">${produto.numAvaliacoes} av.</small>
        </div>
        <p class="card-text text-muted small">${escapeHtml(produto.descricao_curta.substring(0, 60))}${produto.descricao_curta.length > 60 ? '...' : ''}</p>
        <p class="fw-bold mb-0 ${produto.promocao ? 'text-success' : ''}">
          ${precoFormatado}
        </p class="">
        ${!produto.estoque != 0 ? '<span class="badge bg-secondary mt-2">Fora de estoque</span>' : ''}
        <p class="">
          ${produto.categoria ? `<span class="badge bg-success mt-2">${produto.categoria}</span>` : ''}
        </p>
      </div>
    </div>
  `;
  return card;
}

function renderizarProdutos(produtosFiltrados) {
  elementos.produtosContainer.innerHTML = '';
  if (!produtosFiltrados.length) {
    elementos.produtosContainer.innerHTML = `
      <div class="col-12 text-center py-5">
        <i class="bi bi-search fs-1 text-muted"></i>
        <p class="text-muted mt-2">Nenhum produto encontrado com esses filtros</p>
        <button class="btn btn-sm btn-outline-primary" onclick="resetarFiltros()">
          Limpar filtros
        </button>
      </div>
    `;
    return;
  }
  const fragment = document.createDocumentFragment();
  const inicio = (estado.paginaAtual - 1) * config.produtosPorPagina;
  const fim = inicio + config.produtosPorPagina;
  produtosFiltrados.slice(inicio, fim).forEach(produto => {
    const card = criarCardProduto(produto);
    fragment.appendChild(card);
  });
  elementos.produtosContainer.appendChild(fragment);
  criarPaginacao(produtosFiltrados.length);
}

// Funções de paginação
function criarPaginacao(totalProdutos) {
  elementos.paginacao.innerHTML = '';
  const totalPaginas = Math.ceil(totalProdutos / config.produtosPorPagina);
  for (let i = 1; i <= totalPaginas; i++) {
    const li = document.createElement('li');
    li.className = `page-item ${i === estado.paginaAtual ? 'active' : ''}`;
    li.innerHTML = `<a class="page-link" href="#">${i}</a>`;
    li.addEventListener('click', (e) => {
      e.preventDefault();
      estado.paginaAtual = i;
      aplicarFiltros(false);
      window.scrollTo({ top: 0, behavior: 'smooth' });
    });
    elementos.paginacao.appendChild(li);
  }
}

// Funções de filtro
function criarTagFiltro(texto, onClick) {
  const tag = document.createElement('span');
  tag.className = 'filter-tag';
  tag.innerHTML = `
    ${texto}
    <button type="button" aria-label="Remover filtro">
      <i class="fas fa-times" style="font-size: 0.75rem;"></i>
    </button>
  `;
  tag.querySelector('button').addEventListener('click', onClick);
  return tag;
}

function atualizarFiltrosAplicados() {
  elementos.filtrosAplicados.innerHTML = '';
  const { textoBusca, emEstoque, foraEstoque, categorias } = estado.filtrosAtivos;
  if (textoBusca) {
    elementos.filtrosAplicados.appendChild(criarTagFiltro(`Busca: ${escapeHtml(textoBusca)}`, () => {
      elementos.inputBusca.value = '';
      atualizarFiltros();
    }));
  }
  if (emEstoque) {
    elementos.filtrosAplicados.appendChild(criarTagFiltro('Em estoque', () => {
      elementos.statusEstoque.checked = false;
      atualizarFiltros();
    }));
  }
  if (foraEstoque) {
    elementos.filtrosAplicados.appendChild(criarTagFiltro('Fora de estoque', () => {
      elementos.statusForaEstoque.checked = false;
      atualizarFiltros();
    }));
  }
  categorias.forEach(categoria => {
    const checkbox = document.getElementById(`cat-${categoria}`);
    if (checkbox) {
      elementos.filtrosAplicados.appendChild(criarTagFiltro(
        escapeHtml(checkbox.parentElement.textContent.trim()),
        () => {
          checkbox.checked = false;
          atualizarFiltros();
        }
      ));
    }
  });
  if (!elementos.filtrosAplicados.children.length) {
    elementos.filtrosAplicados.innerHTML = `
      <span class="filter-tag">
        Tudo
        <button type="button" aria-label="Nenhum filtro ativo">
          <i class="fas fa-times" style="font-size: 0.75rem;"></i>
        </button>
      </span>
    `;
  }
}

function aplicarFiltros(resetarPagina = true) {
  if (resetarPagina) estado.paginaAtual = 1;

  const { textoBusca, emEstoque, foraEstoque, categorias } = estado.filtrosAtivos;

  const produtosFiltrados = estado.produtos.filter(produto => {
    if (textoBusca && !produto.nome.toLowerCase().includes(textoBusca.toLowerCase())) {
      return false;
    }

    // Se filtrando por "em estoque" e o produto não tem estoque, exclui
if (emEstoque && !(produto.estoque > 0)) return false;

// Se filtrando por "fora de estoque" e o produto tem estoque, exclui
if (foraEstoque && !(produto.estoque == 0)) return false;

    if (categorias.length > 0) {
      const categoriaProduto = produto.categoria.toLowerCase();
      const encontrou = categorias.some(cat => categoriaProduto.includes(cat.toLowerCase()));
      if (!encontrou) return false;
    }

    return true;
  });

  renderizarProdutos(produtosFiltrados);
  atualizarFiltrosAplicados();
}

function atualizarFiltros() {
  estado.filtrosAtivos = {
    textoBusca: elementos.inputBusca.value.trim(),
    emEstoque: elementos.statusEstoque.checked,
    foraEstoque: elementos.statusForaEstoque.checked,
    categorias: Array.from(elementos.categoriaCheckboxes)
      .filter(cb => cb.checked && cb.id !== 'cat-todos')
      .map(cb => cb.id.replace('cat-', ''))
  };
  aplicarFiltros();
}

function resetarFiltros() {
  elementos.inputBusca.value = '';
  elementos.statusEstoque.checked = false;
  elementos.statusForaEstoque.checked = false;
  elementos.categoriaCheckboxes.forEach(cb => cb.checked = cb.id === 'cat-todos');
  atualizarFiltros();
}

// Funções do modal de produto
function adjustQuantity(elementId, change) {
  const input = document.getElementById(elementId);
  let value = parseInt(input.value, 10) || 1;
  value = Math.max(1, value + change);
  input.value = value;
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
  estado.id_produto = produto.id_produto;
  
  // Preencher informações do modal
  const label = document.getElementById('produtoModalLabel');
  if (label) label.textContent = produto.nome;
  
  const imgEl = document.getElementById('produtoModalImagem');
  if (imgEl) {
    imgEl.src = produto.imagem_1 || produto.imagem_2 || produto.imagem_3 || config.fallbackImage;
    imgEl.onerror = () => imgEl.src = config.fallbackImage;
  }
  
  const descEl = document.getElementById('produtoModalDescricao');
  if (descEl) descEl.textContent = produto.descricao;
  
  const marcaEl = document.getElementById('produtoModalMarca');
  if (marcaEl) marcaEl.textContent = produto.marca;
  
  const catEl = document.getElementById('produtoModalCategoria');
  if (catEl) {
    catEl.textContent = produto.categoria;
    catEl.className = `badge mb-2 bg-${produto.promocao ? 'danger' : 'success'}`;
  }
  
  const estoqueEl = document.getElementById('produtoModalEstoque');
  if (produto.estoque ==  0 || produto.estoque == null) {
    estoqueEl.innerHTML = `<span class="badge bg-secondary">Fora de estoque</span>`;
    let botaoComprar = document.querySelector('#btnComprarAgora');
    let botaoCarrinho = document.querySelector('#btnAddCarrinho');
    if (botaoComprar) {
      botaoComprar.disabled = true;
      botaoComprar.classList.add("text-bg-secondary");
    }
    if (botaoCarrinho) {
      botaoCarrinho.disabled = true;
      botaoCarrinho.classList.add("text-bg-secondary");
    }
  }else{
    estoqueEl.innerHTML = `<span class="badge bg-success">Em estoque</span>`;
    let botaoComprar = document.querySelector('#btnComprarAgora');
    let botaoCarrinho = document.querySelector('#btnAddCarrinho');
    if (botaoComprar) {
      botaoComprar.disabled = false;
      botaoComprar.classList.remove("text-bg-secondary");
    }
    if (botaoCarrinho) {
      botaoCarrinho.disabled = false;
      botaoCarrinho.classList.remove("text-bg-secondary");
    }
  }

  // Preços
  const precoContainer = document.getElementById('produtoModalPreco');
  const precoBase = (Number(produto.preco) || 0).toLocaleString('pt-BR', {
    style: 'currency',
    currency: 'BRL'
  });
  if (precoContainer) {
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
  }
  
  // Avaliação
  const stars = document.getElementById('produtoModalAvaliacao');
  if (stars) {
    stars.innerHTML = '';
    if (produto.avaliacao != null) {
      const fullStars = Math.floor(produto.avaliacao);
      const halfStar = produto.avaliacao % 1 >= 0.5;
      const emptyStars = 5 - fullStars - (halfStar ? 1 : 0);
      for (let i = 0; i < fullStars; i++) stars.innerHTML += '<i class="fas fa-star text-yellow-400 text-xs"></i>';
      if (halfStar) stars.innerHTML += '<i class="fas fa-star-half-alt text-yellow-400 text-xs"></i>';
      for (let i = 0; i < emptyStars; i++) stars.innerHTML += '<i class="far fa-star text-yellow-400 text-xs"></i>';
      stars.innerHTML += `<span class="text-xs text-gray-600 ml-1">(${produto.numAvaliacoes})</span>`;
    }
  }
  
  // Mostrar o modal
  if (elementos.produtoModal) {
    elementos.produtoModal.show();
  } else {
    console.error('Modal não inicializado corretamente');
    mostrarFeedback('Erro ao abrir o modal', 'danger');
  }
}

// Funções de autenticação
async function verificarEstadoLogin() {
  try {
    console.log('Iniciando verificação de login...');
    const response = await fetch('https://green-line-web.onrender.com/loginDados', {
      credentials: 'include'
    });
    console.log('Status da resposta:', response.status);
    if (!response.ok) {
      throw new Error(`Falha na verificação: HTTP ${response.status}`);
    }

    const dados = await response.json();
    console.log('Dados recebidos:', dados);

    if (dados && dados.id_pessoa) {
      estado.id_pessoa = parseInt(dados.id_pessoa);
      localStorage.setItem('id_pessoa', estado.id_pessoa);
      console.log('id_pessoa atualizado:', estado.id_pessoa);
      return true;
    } else {
      console.log('id_pessoa não encontrado nos dados:', dados);
      return false;
    }
  } catch (erro) {
    console.error('Erro ao verificar login:', erro);
    return false;
  }
}

// Funções de inicialização e configuração
function configurarEventos() {
  // Eventos de filtro
  elementos.inputBusca.addEventListener('input', () => {
    estado.filtrosAtivos.textoBusca = elementos.inputBusca.value.trim();
    aplicarFiltros();
  });

  elementos.statusEstoque.addEventListener('change', () => {
    estado.filtrosAtivos.emEstoque = elementos.statusEstoque.checked;
    aplicarFiltros();
  });

  elementos.statusForaEstoque.addEventListener('change', () => {
    estado.filtrosAtivos.foraEstoque = elementos.statusForaEstoque.checked;
    aplicarFiltros();
  });

  elementos.categoriaCheckboxes.forEach(checkbox => {
    checkbox.addEventListener('change', () => {
      if (checkbox.id === 'cat-todos' && checkbox.checked) {
        elementos.categoriaCheckboxes.forEach(cb => {
          if (cb.id !== 'cat-todos') cb.checked = false;
        });
      } else if (checkbox.id !== 'cat-todos' && checkbox.checked) {
        document.getElementById('cat-todos').checked = false;
      }
      atualizarFiltros();
    });
  });

  // Eventos do modal
  const produtoModal = document.getElementById('produtoModal');
  produtoModal.addEventListener('hidden.bs.modal', () => {
    const inputQuantidade = document.getElementById('quantidadeModal');
    inputQuantidade.value = 1;
    estado.id_produto = null;
    const itemResultado = document.getElementById('item-resultado');
    itemResultado.classList.add('d-none');
    itemResultado.innerHTML = '';
  });

  // Eventos de quantidade
  document.querySelectorAll('[data-action="increase"]').forEach(btn =>
    btn.addEventListener('click', () => adjustQuantity('quantidadeModal', 1))
  );

  document.querySelectorAll('[data-action="decrease"]').forEach(btn =>
    btn.addEventListener('click', () => adjustQuantity('quantidadeModal', -1))
  );

  // Eventos de compra/carrinho
  document.getElementById('btnComprarAgora').addEventListener('click', async () => {
    if (!estado.id_pessoa) {
      mostrarFeedback("Por favor, faça login ou cadastro para prosseguir", "danger");
      return;
    }
// Obter quantidade
const qtdInput = document.getElementById('quantidadeModal');
const qtd = parseInt(qtdInput.value, 10);

// Validar quantidade
if (isNaN(qtd) || qtd <= 0 || qtd > 100) { 
  mostrarFeedback("Quantidade inválida! Por favor, insira um valor entre 1 e 1F00.", 'danger');
  return;
}
console.log("Quantidade selecionada:", qtd);

// Obter nome do produto
const nome_produto = document.getElementById('produtoModalLabel').textContent.trim();

// Obter elementos de preço
const originalPriceEl = document.querySelector('#produtoModalPreco .original-price');
const discountPriceEl = document.querySelector('#produtoModalPreco .discount-price');

if (!originalPriceEl) {
  mostrarFeedback("Erro ao obter preço do produto.", 'danger');
  return;
}

// Extrair preço de forma segura
function extrairPreco(elemento) {
  if (!elemento) return null;
  
  const precoTexto = elemento.textContent.trim();
  const precoNumerico = parseFloat(
    precoTexto
      .replace(/[^\d,]/g, '')  
      .replace(',', '.')
  );
  
  return isNaN(precoNumerico) ? null : precoNumerico;
}


let preco_final;
const isPromocao = window.getComputedStyle(originalPriceEl).textDecoration.includes('line-through');

if (isPromocao && discountPriceEl) {
  preco_final = extrairPreco(discountPriceEl);
} else {
  preco_final = extrairPreco(originalPriceEl);
}

// Validar preço
if (preco_final === null || preco_final <= 0) {
  mostrarFeedback("Preço do produto inválido.", 'danger');
  return;
}

console.log("Preço final:", preco_final);

// Calcular subtotal com arredondamento
const subtotal = Math.round((preco_final * qtd) * 100) / 100;
console.log("Subtotal:", subtotal.toFixed(2));


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
    window.location.href = "vendas.html";
  });

  document.getElementById('btnAddCarrinho').addEventListener('click', async () => {
    if (!estado.id_pessoa) {
      mostrarFeedback("Por favor, faça login ou cadastro para prosseguir", "danger");
      return;
    }

    const quantidade = parseInt(document.getElementById('quantidadeModal').value, 10);
    if (isNaN(quantidade) || quantidade <= 0) {
      mostrarFeedback("Quantidade inválida!", 'danger');
      return;
    }

    try {
      const requisicao = await fetch(`${config.apiUrl}/carrinho`, {
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
        setTimeout(() => itemResultado.classList.add('d-none'), 2000);
      } else if (resposta.sucesso) {
        let badge = document.getElementById('badge-carrinho');
        badge.textContent = parseInt(badge.textContent || '0') + 1;
        itemResultado.classList.remove('d-none');
        itemResultado.innerHTML = "✔️ Item adicionado ao carrinho!";
        itemResultado.classList.add('text-success');
        setTimeout(() => {
          itemResultado.classList.add('d-none');
          itemResultado.classList.remove('text-success');
        }, 3000);
      } else {
        itemResultado.classList.remove('d-none');
        itemResultado.innerHTML = `❌ Erro: ${resposta.mensagem || "Erro desconhecido"}`;
        itemResultado.classList.add('text-danger');
        setTimeout(() => itemResultado.classList.add('d-none'), 2000);
      }
    } catch (erro) {
      console.error("Erro ao adicionar ao carrinho:", erro);
      const itemResultado = document.getElementById('item-resultado');
      itemResultado.classList.remove('d-none');
      itemResultado.innerHTML = "❌ Falha na conexão com o servidor";
      itemResultado.classList.add('text-danger');
      setTimeout(() => itemResultado.classList.add('d-none'), 2000);
    }
  });
}

// Inicialização da aplicação
async function inicializarApp() {
  try {
    estado.carregando = true;
    // Carrega id_pessoa do localStorage como fallback inicial
    const storedId = localStorage.getItem('id_pessoa');
    if (storedId) {
      estado.id_pessoa = parseInt(storedId);
      console.log('id_pessoa carregado do localStorage:', estado.id_pessoa);
    }
    await Promise.all([carregarProdutos(), verificarEstadoLogin()]);
    configurarEventos();
    console.log('Estado após inicialização:', estado);
  } catch (erro) {
    console.error('Erro na inicialização:', erro);
    mostrarFeedback('Opa, algo deu errado! Tente recarregar.', 'danger');
  } finally {
    estado.carregando = false;
  }
}

// Inicia a aplicação quando o DOM estiver carregado
document.addEventListener("DOMContentLoaded", inicializarApp);