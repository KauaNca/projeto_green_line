
function gerarNumeroPedido() {
  const ano = new Date().getFullYear();
  const numero = String(Math.floor(Math.random() * 999999)).padStart(6, '0');
  return `#GL-${ano}-${numero}`;
}

// Função para formatar data e hora
function formatarDataHora() {
  const agora = new Date();
  const hoje = agora.toLocaleDateString('pt-BR');
  const hora = agora.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' });
  return `${hoje}, ${hora}`;
}

// Função para formatar endereço completo
function formatarEndereco(dadosCliente) {
  if (!dadosCliente || !dadosCliente.nome) {
    return `Endereço não informado<br>
            Complete seus dados na próxima compra<br>
            para uma experiência melhor`;
  }

  return `${dadosCliente.nome}<br>
          ${dadosCliente.endereco}, ${dadosCliente.numero}${dadosCliente.complemento ? ' - ' + dadosCliente.complemento : ''}<br>
          ${dadosCliente.bairro}<br>
          ${dadosCliente.cidade} - ${dadosCliente.estado}, ${dadosCliente.cep}`;
}

// Função para determinar método de entrega baseado no CEP
function determinarMetodoEntrega(cep) {
  if (!cep) return "Entrega Padrão (3-5 dias úteis)";
  
  const cepNumerico = cep.replace(/\D/g, '');
  
  // Simulação baseada em regiões do Brasil
  if (cepNumerico.startsWith('01') || cepNumerico.startsWith('04') || 
      cepNumerico.startsWith('05') || cepNumerico.startsWith('08')) {
    return "Entrega Expressa (1-2 dias úteis)";
  } else if (cepNumerico.startsWith('2') || cepNumerico.startsWith('3') || 
             cepNumerico.startsWith('7')) {
    return "Entrega Padrão (3-5 dias úteis)";
  } else {
    return "Entrega Estendida (5-7 dias úteis)";
  }
}

// Função para calcular previsão de entrega
function calcularPrevisaoEntrega(metodoEntrega) {
  if (metodoEntrega.includes("Expressa")) {
    return "Previsão: 1-2 dias úteis";
  } else if (metodoEntrega.includes("Padrão")) {
    return "Previsão: 3-5 dias úteis";
  } else {
    return "Previsão: 5-7 dias úteis";
  }
}

// Função para formatar forma de pagamento
function formatarFormaPagamento(dadosFormulario) {
  if (!dadosFormulario || !dadosFormulario.metodoPagamento) {
    return '<i class="bi bi-credit-card me-2"></i>Não informado';
  }

  switch (dadosFormulario.metodoPagamento) {
    case 'CC':
      const ultimosDigitos = dadosFormulario.numeroCartao ? 
        dadosFormulario.numeroCartao.slice(-4) : '0000';
      return `<i class="bi bi-credit-card me-2"></i>Cartão de Crédito ****${ultimosDigitos}`;
    case 'PIX':
      return '<i class="bi bi-qr-code me-2"></i>PIX';
    case 'BB':
      return '<i class="bi bi-file-earmark-text me-2"></i>Boleto Bancário';
    default:
      return '<i class="bi bi-credit-card me-2"></i>Não informado';
  }
}

// Função para calcular impacto sustentável
function calcularImpactoSustentavel(produtos) {
  const totalProdutos = produtos.length;
  const co2Evitado = (totalProdutos * 1.2).toFixed(1); // 1.2kg por produto
  const arvores = Math.ceil(totalProdutos / 2); // 1 árvore a cada 2 produtos
  
  return {
    co2: `${co2Evitado}kg`,
    arvores: arvores
  };
}

// Função principal para carregar dados do pedido
function carregarDadosPedido() {
  try {
    console.log("Iniciando carregamento dos dados do pedido...");

    // Carrega dados da compra
    const dadosCompraStr = localStorage.getItem("dadosCompra");
    const dadosFormularioStr = localStorage.getItem("dadosFormulario");
    
    if (!dadosCompraStr) {
      console.error("Dados da compra não encontrados no localStorage");
      mostrarErro("Dados da compra não encontrados. Redirecionando para a página de vendas...");
      setTimeout(() => {
        window.location.href = "vendas.html";
      }, 3000);
      return;
    }

    // Parse dos dados da compra
    let dadosCompra;
    try {
      dadosCompra = JSON.parse(dadosCompraStr);
      // Se for um objeto único, transforma em array
      if (!Array.isArray(dadosCompra)) {
        dadosCompra = [dadosCompra];
      }
      console.log("Dados da compra carregados:", dadosCompra);
    } catch (error) {
      console.error("Erro ao fazer parse dos dados da compra:", error);
      mostrarErro("Erro nos dados da compra");
      return;
    }

    // Parse dos dados do formulário (opcional)
    let dadosFormulario = {};
    if (dadosFormularioStr) {
      try {
        dadosFormulario = JSON.parse(dadosFormularioStr);
        console.log("Dados do formulário carregados:", dadosFormulario);
      } catch (error) {
        console.error("Erro ao fazer parse dos dados do formulário:", error);
        console.log("Continuando sem dados do formulário...");
      }
    }

    // Calcula valores financeiros
    const subtotal = dadosCompra.reduce((total, item) => {
      return total + (parseFloat(item.subtotal) || 0);
    }, 0);
    
    const frete = 19.90; // Valor fixo do frete
    const desconto = subtotal > 100 ? 10.00 : 0; // Desconto eco para compras acima de R$ 100
    const total = subtotal + frete - desconto;

    // Gera informações do pedido
    const numeroPedido = gerarNumeroPedido();
    const dataConfirmacao = formatarDataHora();
    const metodoEntrega = determinarMetodoEntrega(dadosFormulario.cep);
    const previsaoEntrega = calcularPrevisaoEntrega(metodoEntrega);
    const impacto = calcularImpactoSustentavel(dadosCompra);

    // Monta objeto completo do pedido
    const pedido = {
      numeroPedido: numeroPedido,
      dataConfirmacao: dataConfirmacao,
      previsaoEntrega: previsaoEntrega,
      enderecoEntrega: formatarEndereco(dadosFormulario),
      metodoEntrega: metodoEntrega,
      produtos: dadosCompra.map(item => ({
        nome: item.nome_produto || "Produto",
        quantidade: parseInt(item.quantidade) || 1,
        preco: parseFloat(item.preco_final) || 0,
        subtotal: parseFloat(item.subtotal) || 0
      })),
      subtotal: subtotal,
      frete: frete,
      desconto: desconto,
      total: total,
      formaPagamento: formatarFormaPagamento(dadosFormulario),
      impactoCO2: impacto.co2,
      arvores: impacto.arvores
    };

    console.log("Pedido montado:", pedido);

    // Preenche a página com os dados
    preencherPaginaConfirmacao(pedido);

    // Salva dados do pedido para uso futuro
    localStorage.setItem("ultimoPedido", JSON.stringify(pedido));

    // Simula envio de email (apenas log)
    console.log(`Email de confirmação seria enviado para: ${dadosFormulario.email || 'email não informado'}`);

  } catch (error) {
    console.error("Erro geral ao carregar dados do pedido:", error);
    mostrarErro("Erro inesperado ao carregar dados do pedido");
  }
}

// Função para preencher a página de confirmação
function preencherPaginaConfirmacao(pedido) {
  try {
    console.log("Preenchendo página com dados do pedido...");

    // Número do pedido
    const numeroPedidoEl = document.getElementById("numeroPedido");
    if (numeroPedidoEl) {
      numeroPedidoEl.textContent = pedido.numeroPedido;
    }

    // Data de confirmação
    const dataConfirmacaoEl = document.getElementById("dataConfirmacao");
    if (dataConfirmacaoEl) {
      dataConfirmacaoEl.textContent = pedido.dataConfirmacao;
    }

    // Previsão de entrega
    const previsaoEntregaEl = document.getElementById("previsaoEntrega");
    if (previsaoEntregaEl) {
      previsaoEntregaEl.textContent = pedido.previsaoEntrega;
    }

    // Endereço de entrega
    const enderecoEntregaEl = document.getElementById("enderecoEntrega");
    if (enderecoEntregaEl) {
      enderecoEntregaEl.innerHTML = pedido.enderecoEntrega;
    }

    // Método de entrega
    const metodoEntregaEl = document.getElementById("metodoEntrega");
    if (metodoEntregaEl) {
      metodoEntregaEl.innerHTML = `<i class="bi bi-truck me-2"></i>${pedido.metodoEntrega}`;
    }

    // Produtos do pedido
    const produtosContainer = document.getElementById("produtosPedido");
    if (produtosContainer) {
      produtosContainer.innerHTML = ""; // Limpa conteúdo anterior
      
      pedido.produtos.forEach((produto, index) => {
        const produtoHTML = `
          <div class="order-item d-flex justify-content-between align-items-center py-3 ${index < pedido.produtos.length - 1 ? 'border-bottom' : ''}">
            <div class="item-info">
              <h5 class="item-name mb-1">${produto.nome}</h5>
              <p class="item-details text-muted mb-0">
                Quantidade: ${produto.quantidade} × R$ ${produto.preco.toFixed(2).replace('.', ',')}
              </p>
            </div>
            <div class="item-total">
              <strong>R$ ${produto.subtotal.toFixed(2).replace('.', ',')}</strong>
            </div>
          </div>
        `;
        produtosContainer.insertAdjacentHTML('beforeend', produtoHTML);
      });
    }

    // Resumo financeiro
    const subtotalEl = document.getElementById("subtotal");
    if (subtotalEl) {
      subtotalEl.textContent = `R$ ${pedido.subtotal.toFixed(2).replace('.', ',')}`;
    }

    const freteEl = document.getElementById("frete");
    if (freteEl) {
      freteEl.textContent = `R$ ${pedido.frete.toFixed(2).replace('.', ',')}`;
    }

    const descontoEl = document.getElementById("desconto");
    if (descontoEl) {
      descontoEl.textContent = `-R$ ${pedido.desconto.toFixed(2).replace('.', ',')}`;
      // Oculta linha de desconto se for zero
      if (pedido.desconto === 0) {
        const descontoRow = descontoEl.closest('.summary-row');
        if (descontoRow) descontoRow.style.display = 'none';
      }
    }

    const totalEl = document.getElementById("total");
    if (totalEl) {
      totalEl.textContent = `R$ ${pedido.total.toFixed(2).replace('.', ',')}`;
    }

    // Forma de pagamento
    const formaPagamentoEl = document.getElementById("formaPagamento");
    if (formaPagamentoEl) {
      formaPagamentoEl.innerHTML = pedido.formaPagamento;
    }

    // Impacto sustentável
    const ecoTextEl = document.querySelector(".eco-text");
    if (ecoTextEl) {
      const ecoText = `Com esta compra, você evitou <strong>${pedido.impactoCO2}</strong> de CO₂ 
                       e contribuiu para o plantio de <strong>${pedido.arvores} árvore${pedido.arvores > 1 ? 's' : ''}</strong>!`;
      ecoTextEl.innerHTML = ecoText;
    }

    console.log("Página preenchida com sucesso!");

  } catch (error) {
    console.error("Erro ao preencher página de confirmação:", error);
    mostrarErro("Erro ao exibir dados do pedido");
  }
}

// Função para mostrar erro na página
function mostrarErro(mensagem) {
  const main = document.querySelector("main");
  if (main) {
    const errorDiv = document.createElement("div");
    errorDiv.className = "alert alert-danger mx-3 mt-3";
    errorDiv.innerHTML = `
      <h4><i class="bi bi-exclamation-triangle me-2"></i>Ops! Algo deu errado</h4>
      <p>${mensagem}</p>
      <div class="mt-3">
        <button class="btn btn-success me-2" onclick="window.location.href='vendas.html'">
          <i class="bi bi-arrow-left me-2"></i>Voltar para Vendas
        </button>
        <button class="btn btn-outline-success" onclick="window.location.href='produtos.html'">
          <i class="bi bi-shop me-2"></i>Ver Produtos
        </button>
      </div>
    `;
    main.insertBefore(errorDiv, main.firstChild);
  }
}

// Funções para os botões de ação
function continuarComprando() {
  // Limpa dados da compra atual
  localStorage.removeItem("dadosCompra");
  localStorage.removeItem("dadosFormulario");
  
  // Redireciona para produtos
  window.location.href = "produtos.html";
}

function acompanharPedido() {
  const ultimoPedido = localStorage.getItem("ultimoPedido");
  if (ultimoPedido) {
    const pedido = JSON.parse(ultimoPedido);
    alert(`Acompanhe seu pedido ${pedido.numeroPedido} através do nosso WhatsApp ou email de confirmação.`);
  } else {
    alert("Dados do pedido não encontrados.");
  }
}

function baixarComprovante() {
  // Simula download do comprovante
  window.print();
}

function entrarContato() {
  // Redireciona para página de contato
  window.location.href = "contato.html";
}

// Função para carregar produtos relacionados (simulação)
function carregarProdutosRelacionados() {
  const produtosContainer = document.getElementById("produtosRelacionados");
  if (!produtosContainer) return;

  // Produtos relacionados simulados
  const produtosRelacionados = [
    { nome: "Camiseta Orgânica", preco: 45.90, imagem: "../img/produto1.jpg" },
    { nome: "Mochila Sustentável", preco: 89.90, imagem: "../img/produto2.jpg" },
    { nome: "Garrafa Reutilizável", preco: 29.90, imagem: "../img/produto3.jpg" }
  ];

  produtosRelacionados.forEach(produto => {
    const produtoHTML = `
      <div class="col-md-4 mb-3">
        <div class="card h-100">
          <img src="${produto.imagem}" class="card-img-top" alt="${produto.nome}" style="height: 200px; object-fit: cover;">
          <div class="card-body d-flex flex-column">
            <h5 class="card-title">${produto.nome}</h5>
            <p class="card-text text-success fw-bold mt-auto">R$ ${produto.preco.toFixed(2).replace('.', ',')}</p>
            <button class="btn btn-outline-success btn-sm">Ver Produto</button>
          </div>
        </div>
      </div>
    `;
    produtosContainer.insertAdjacentHTML('beforeend', produtoHTML);
  });
}

// Inicialização quando a página carrega
document.addEventListener("DOMContentLoaded", function() {
  console.log("Página de confirmação carregada - GreenLine");
  
  // Carrega dados do pedido
  carregarDadosPedido();
  
  // Carrega produtos relacionados
  carregarProdutosRelacionados();
  
  // Adiciona event listeners para botões se não estiverem definidos inline
  const btnContinuar = document.querySelector('button[onclick="continuarComprando()"]');
  const btnAcompanhar = document.querySelector('button[onclick="acompanharPedido()"]');
  const btnBaixar = document.querySelector('button[onclick="baixarComprovante()"]');
  const btnContato = document.querySelector('button[onclick="entrarContato()"]');
  
  // Log para debug
  console.log("Event listeners configurados para os botões de ação");
});

// Função para limpar dados após confirmação (chamada quando sair da página)
window.addEventListener('beforeunload', function() {
  // Mantém dados do último pedido, mas limpa dados temporários após um tempo
  setTimeout(() => {
    localStorage.removeItem("dadosCompra");
    localStorage.removeItem("dadosFormulario");
  }, 300000); // Remove após 5 minutos
});