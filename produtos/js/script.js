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
    carrinho.push({ nome: produto.nome, preco: produto.preco, quantidade: 1, imagem: produto.imagem });
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
      <img src="${produto.imagem}" alt="${produto.nome}" width="50" height="50">
      <p>Produto: ${produto.nome}</p>
      <p>Quantidade: ${produto.quantidade}</p>
      <p>Preço: R$${produto.preco.toFixed(2)}</p>
      <p>Subtotal: R$${(produto.preco * produto.quantidade).toFixed(2)}</p>
      <button class="remover-produto">Remover</button>
      <button class="comprar-produto">Comprar</button>
    `;
    
    // Adiciona o produto ao carrinho
    carrinhoElement.appendChild(produtoElement);
  });
  
  // Atualiza o valor total do carrinho
  document.getElementById('carrinho-total').innerHTML = `Valor Total: R$${valorTotal.toFixed(2)}`;
  
  // Adiciona evento de clique aos botões de remover produto
  let removerProdutoButtons = document.querySelectorAll('.remover-produto');
  removerProdutoButtons.forEach(button => {
    button.addEventListener('click', function() {
      // Obtém o produto associado ao botão
      let produto = {
        nome: button.parentNode.querySelector('p:nth-child(2)').textContent.replace('Produto: ', ''),
        preco: parseFloat(button.parentNode.querySelector('p:nth-child(4)').textContent.replace('Preço: R$', '')),
        imagem: button.parentNode.querySelector('img').src
      };
      
      // Remove o produto do carrinho
      carrinho = carrinho.filter(item => item.nome !== produto.nome);
      
      // Atualiza o carrinho na tela
      atualizarCarrinho();
    });
  });
  
  // Adiciona evento de clique aos botões de comprar produto
  let comprarProdutoButtons = document.querySelectorAll('.comprar-produto');
  comprarProdutoButtons.forEach(button => {
    button.addEventListener('click', function() {
      // Obtém o produto associado ao botão
      let produto = {
        nome: button.parentNode.querySelector('p:nth-child(2)').textContent.replace('Produto: ', ''),
        preco: parseFloat(button.parentNode.querySelector('p:nth-child(4)').textContent.replace('Preço: R$', '')),
        imagem: button.parentNode.querySelector('img').src
      };
      
      // Compra o produto
      alert(`Você comprou o produto ${produto.nome} por R$${produto.preco.toFixed(2)}`);
      
      // Remove o produto do carrinho
      carrinho = carrinho.filter(item => item.nome !== produto.nome);
      
      // Atualiza o carrinho na tela
      atualizarCarrinho();
    });
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

// Adiciona evento de clique ao botão de comprar
let comprarButton = document.querySelector('.comprar');
comprarButton.addEventListener('click', function() {
 // Compra todos os produtos do carrinho
  carrinho.forEach(produto => {
    alert(`Você comprou o produto ${produto.nome} por R$${produto.preco.toFixed(2)}`);
  });
  
  // Limpa o carrinho
  carrinho = [];
  
  // Atualiza o carrinho na tela
  atualizarCarrinho();
});