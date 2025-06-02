document.addEventListener('DOMContentLoaded', async () => {
    await verificarEstadoLogin();
});

let elementosHTML = {
    iconeUsuario: document.getElementById('icone-usuario'),
    badgeCarrinho: document.getElementById('badge-carrinho'),
    link_usuario: document.getElementById('link-usuario'),
    administracao: document.getElementById('admDropdown')
};

// Função para verificar estado de login
async function verificarEstadoLogin() {
    if (!elementosHTML.iconeUsuario || !elementosHTML.badgeCarrinho || !elementosHTML.link_usuario) return;

    try {
        let response = await fetch("https://green-line-web.onrender.com/loginDados");

        if (!response.ok) {
            // Se houver erro, limpa o localStorage (usuário não está logado)
            localStorage.removeItem("id_pessoa");
            console.error("Erro na resposta do servidor:", response.statusText);
            return;
        }

        let dados = await response.json();
        console.log("Dados do login:", dados);

        // SÓ armazena no localStorage se houver um usuário válido logado
        if (dados.id_pessoa) {
            localStorage.setItem("id_pessoa", dados.id_pessoa);
        } else {
            localStorage.removeItem("id_pessoa"); // Limpa se não estiver logado
        }

        // Restante da sua lógica...
        if (dados?.trocarDeConta === 1 || dados?.trocar === 1) {
            elementosHTML.iconeUsuario.className = "bi bi-person-check text-success";
            elementosHTML.iconeUsuario.title = "Usuário logado";
            elementosHTML.link_usuario.href = "../public/perfil.html";
        } else {
            // Reseta para estado não logado
            elementosHTML.iconeUsuario.className = "bi bi-person";
            elementosHTML.iconeUsuario.title = "Fazer login";
            elementosHTML.link_usuario.href = "../public/login.html";
        }

        // Atualiza carrinho
        elementosHTML.badgeCarrinho.innerText = dados?.quantidade_produtos > 0 ? dados.quantidade_produtos : "";

        // Administração
        if (elementosHTML.administracao) {
            elementosHTML.administracao.classList.toggle("d-none", dados?.tipo_usuario !== 1);
        }

    } catch (erro) {
        localStorage.removeItem("id_pessoa"); // Limpa em caso de erro
        console.error("Erro ao verificar login:", erro.message);
    }
}