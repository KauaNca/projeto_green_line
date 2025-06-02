let estado = {
    id_pessoa: null,
    produtos: []
};

// Inicializa o sistema
async function inicializar() {
    try {
        console.log("Iniciando carregamento...");

        const respostaLogin = await fetch("https://green-line-web.onrender.com/loginDados");
        if (!respostaLogin.ok) throw new Error("Erro na requisição de login");

        const dados = await respostaLogin.json();
        if (!dados.id_pessoa) throw new Error("ID de pessoa não encontrado");

        estado.id_pessoa = dados.id_pessoa;
        console.log("ID Pessoa carregado:", estado.id_pessoa);

        await buscarProdutos();
        await renderizarProdutos();

    } catch (erro) {
        console.error("Erro na inicialização:", erro);
    }
}

// Busca os produtos no banco
async function buscarProdutos() {
    try {
        console.log("Buscando produtos para ID:", estado.id_pessoa);

        const resposta = await fetch('https://green-line-web.onrender.com/buscar-produtos', {
            method: 'POST',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id_pessoa: estado.id_pessoa })
        });

        if (!resposta.ok) throw new Error("Erro ao buscar produtos");

        let dados = await resposta.json();
        estado.produtos = dados.produtos || []; // Garante que seja um array

        const aviso = document.getElementById('aviso');
        if (estado.produtos.length === 0) {
            aviso.classList.remove('d-none');
            aviso.innerHTML = "Nenhum produto encontrado";
            setTimeout(() => aviso.classList.add('d-none'), 5000);
        } else {
            aviso.classList.add('d-none');
        }

        console.log(estado.produtos);

    } catch (erro) {
        console.error("Erro ao buscar produtos:", erro);
    }
}

// Renderiza os produtos no carrinho
async function renderizarProdutos() {
    const container = document.getElementById('produtos-carrinho');
    container.innerHTML = '';

    estado.produtos.forEach((item) => {
        const divProduto = document.createElement("div");
        divProduto.className = "produto-carrinho d-flex button";
        divProduto.setAttribute("data-id", item.id_produto);
        divProduto.setAttribute("data-id-carrinho", item.id_carrinho);

        divProduto.innerHTML = `
            <div class="flex-shrink-0">
                <img src="${item.imagem_principal}" alt="Produto" class="img-produto">
            </div>
            <div class="flex-grow-1 ms-3">
                <div class="d-flex justify-content-between">
                    <div>
                        <h3 class="h6 fw-bold mb-1">${item.nome_produto}</h3>
                        ${item.promocao ? '<span class="badge badge-promocao ms-1">Promoção</span>' : ''}
                    </div>
                    <button class="btn btn-link text-danger p-0 align-self-start excluir-produto">
                        <i class="bi bi-trash"></i>
                    </button>
                </div>
                <div class="d-flex justify-content-between align-items-center mt-2">
                    <div class="d-flex align-items-center">
                        <span class="text-muted">Quantidade: ${item.quantidade}</span>
                    </div>
                    <div class="text-end">
                        ${!item.promocao ? `<div class="">R$ ${item.preco_unitario}</div>` : `<div class="preco-original">R$ ${item.preco_unitario}</div>`}
                        ${item.promocao ? `<div class="preco-promocao">R$ ${item.preco_promocional}</div>` : ''}
                    </div>
                </div>
            </div>
        `;

        // Evento para exclusão de produto
        divProduto.querySelector(".excluir-produto").addEventListener("click", async () => {
            await excluirProduto(item.id_produto, item.id_carrinho, divProduto);
        });

        container.appendChild(divProduto);
    });

    subtotal(); // Atualiza o total após renderizar
}

// Calcula subtotal
function subtotal() {
    let total = document.getElementById('total');
    let quantidade = document.getElementById('quantidade-itens');
    let valor = 0;
    let quant = 0;

    estado.produtos.forEach((item) => {
        valor += parseFloat(item.subtotal) || 0;
        quant += parseInt(item.quantidade) || 0;
    });

    quantidade.innerHTML = quant || 0;
    total.innerHTML = valor.toFixed(2) || "0.00";
}

// Função para remover produto
async function excluirProduto(id_produto, id_carrinho, elemento) {
    try {
        let requisicao = await fetch('https://green-line-web.onrender.com/excluir-produtos', {
            method: 'POST',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id_pessoa: estado.id_pessoa, id_produto, id_carrinho })
        });

        let dados = await requisicao.json();
        if (!requisicao.ok) throw new Error("Erro ao excluir produto");

        if (dados.codigo === 3) {
            elemento.remove();
            estado.produtos = estado.produtos.filter(item => item.id_produto !== id_produto);
            subtotal();
        }

    } catch (erro) {
        console.error("Erro ao excluir produto:", erro);
    }
}

// Evento de inicialização ao carregar a página
document.addEventListener("DOMContentLoaded", async () => {
    await inicializar();
});

const finalizarVenda = document.getElementById('finalizar-venda');

finalizarVenda.addEventListener("click",()=>{
    let produtos = estado.produtos;
    localStorage.setItem("dadosCompra",JSON.stringify(produtos));
    window.location.href = "vendaS.html";
})