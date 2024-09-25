// Armazena os produtos no carrinho
let carrinho = [];

// Função para adicionar produtos ao carrinho
function adicionarAoCarrinho(produto) {
  // Verifica se o produto já está no carrinho
  let produtoExistente = carrinho.find(item => item.nome === produto.nome);
  
  if (produtoExistente) {
    // Se o produto já está no carrinho, incrementa a quantidade
    produtoExistente.quantidade++;
  } else {
    // Se o produto não está no carrinho, adiciona-o com quantidade 1
    carrinho.push({ nome: produto.nome, preco: produto.preco, quantidade: 1 });
  }
  
  // Atualiza o carrinho na tela
  atualizarCarrinho();
}

// Função para atualizar o carrinho na tela
function atualizarCarrinho() {
  // Seleciona o elemento do carrinho
  let carrinhoElement = document.getElementById('carrinho-produtos');
  
  // Limpa o conteúdo do carrinho
  carrinhoElement.innerHTML = '';
  
  // Calcula o valor total do carrinho
  let valorTotal = 0;
  
  // Percorre os produtos no carrinho e adiciona-os à tela
  carrinho.forEach(produto => {
    valorTotal += produto.preco * produto.quantidade;
    
    // Cria um elemento para o produto
    let produtoElement = document.createElement('div');
    produtoElement.innerHTML = `
      <p>Produto: ${produto.nome}</p>
      <p>Quantidade: ${produto.quantidade}</p>
      <p>Preço: R$${produto.preco.toFixed(2)}</p>
      <p>Subtotal: R$${(produto.preco * produto.quantidade).toFixed(2)}</p>
    `;
    
    // Adiciona o produto ao carrinho
    carrinhoElement.appendChild(produtoElement);
  });
  
  // Atualiza o valor total do carrinho
  document.getElementById('carrinho-total').innerHTML = `Valor Total: R$${valorTotal.toFixed(2)}`;
}

// Adiciona evento de clique aos botões de comprar
let comprarButtons = document.getElementsByClassName('agendamento-botao');
for (let i = 0; i < comprarButtons.length; i++) {
  let button = comprarButtons[i];
  button.addEventListener('click', function() {
    // Obtém o produto associado ao botão
    let produto = {
      nome: button.parentNode.parentNode.querySelector('.body-txt').textContent,
      preco: parseFloat(button.parentNode.parentNode.querySelector('.preco-servico').textContent.replace('R$', ''))
    };
    
    // Adiciona o produto ao carrinho
    adicionarAoCarrinho(produto);
  });
}

// Adiciona evento de clique ao botão de carrinho
let carrinhoButton = document.querySelector('.carrinho img');
carrinhoButton.addEventListener('click', function() {
  // Mostra ou esconde o carrinho
  let carrinhoElement = document.getElementById('carrinho');
  if (carrinhoElement.style.display === 'block') {
    carrinhoElement.style.display = 'none';
  } else {
    carrinhoElement.style.display = 'block';
  }
});

// Adiciona evento de clique ao botão de limpar o carrinho
let limparCarrinhoButton = document.getElementById('limpar-carrinho');
limparCarrinhoButton.addEventListener('click', function() {
  // Limpa o carrinho
  limparCarrinho();
});