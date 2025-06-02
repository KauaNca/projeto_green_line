//Variáveis
let emailValido;
let cpfValido;

const formularioCadastro = document.getElementById('formularioCadastro');
let dadosUsuario = [];
formularioCadastro.addEventListener('submit', function (e) {
    e.preventDefault();
    let infoVal = true;

    const nome = document.getElementById('username').value;
    const nomeErro = document.getElementById('nomeErro');
    const cpf = document.getElementById('cpf').value;
    const cpfErro = document.getElementById('cpfErro');
    const email = document.getElementById('email').value;
    const emailErro = document.getElementById('emailErro');
    const telefone = document.getElementById('telefone').value;
    const telefoneErro = document.getElementById('telefoneErro');
    const senha = document.getElementById('password').value;
    const senhaErro = document.getElementById('senhaErro');

    // Limpa mensagens de erro
    nomeErro.textContent = "";
    cpfErro.textContent = "";
    emailErro.textContent = "";
    telefoneErro.textContent = "";
    senhaErro.textContent = "";

    // Validações
    const nameRegex = /^[a-zA-Z\s]{1,30}$/;
    if (!nameRegex.test(nome)) {
        infoVal = false;
    }

    const emailRegex = /^([a-zA-Z0-9._%+-]+)@([a-zA-Z0-9.-]+)\.([a-zA-Z]{2,})$/;
    if (!emailRegex.test(email)) {
        infoVal = false;
    }

    const phoneRegex = /^\(\d{2}\)\s9\d{4}-\d{4}$/;
    if (!phoneRegex.test(telefone)) {
        infoVal = false;
    }

    const cpfRegex = /^\d{3}\.\d{3}\.\d{3}-\d{2}$|^\d{11}$/;
    if (!cpfRegex.test(cpf)) {
        infoVal = false;
    }
    // Expressão regular para pelo menos 1 letra e 1 número
    const passRegex = /^(?=.*[a-zA-Z])(?=.*\d).{5,}$/;
    if (!passRegex.test(senha)) {
        infoVal = false;
    }


    if (infoVal && cpfValido == 1 && emailValido == 1) {
        dadosUsuario = { nome, email, cpf, telefone, senha };
        fetch("https://green-line-web.onrender.com/cadastrar", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(dadosUsuario)
        })
            .then(response => response.json())
            .then(data => {
                console.log("Resposta do servidor:", data);
                // Redireciona após o sucesso do cadastro
                window.location.href = "/public/login.html";
            })
            .catch(error => console.error("Erro ao cadastrar:", error));
    }
    else{
        const mensagemUsuario = document.getElementById('mensagem-superior');
        mensagemUsuario.className = "d-block text-danger fw-bold -2 text-center mb-2";
    }

});

async function verificarEmail() {
    const email = document.getElementById('email').value;
    const emailErro = document.getElementById('emailErro');
    const emailCadastrado = document.getElementById('emailCadastrado');

    if (!email) return;

    try {
        const verificacao = await fetch(`https://green-line-web.onrender.com/verificarEmail?email=${email}`);
        const resposta = await verificacao.json();

        if (resposta.existe) {
            console.log("✅ Email existente");
            emailErro.className = "d-block alert bi bi-exclamation-circle-fill text-bg-danger";
            emailCadastrado.className = "d-block text-danger fw-bold -0 text-end"
            emailValido = 0;
        } else {
            console.log("❌ Email não encontrado");
            emailErro.className = "d-block alert bi bi-envelope-fill text-bg-success";
            emailCadastrado.className = "d-none text-danger fw-bold -0 text-end"
            emailValido = 1;
        }
    } catch (erro) {
        console.error("Erro ao verificar o email", erro);
        alert("Erro ao verificar o email. Tente novamente mais tarde.");
    }
}

async function verificarCPF() {
    const cpf = document.getElementById('cpf').value;
    const cpfErro = document.getElementById('cpfErro');
    const cpfCadastrado = document.getElementById('cpfCadastrado');

    if (!cpf) return;

    try {
        const verificacao = await fetch(`https://green-line-web.onrender.com/verificarCPF?cpf=${cpf}`);
        const resposta = await verificacao.json();

        if (resposta.existe) {
            console.log("✅ CPF existente");
            cpfErro.className = "d-block alert bi bi-exclamation-circle-fill text-bg-danger";
            cpfCadastrado.className = "d-block text-danger fw-bold -0 text-end"
            cpfValido = 0;
        } else {
            console.log("❌ CPF não encontrado");
            cpfErro.className = "d-block alert bi bi-card-list text-bg-success";
            cpfCadastrado.className = "d-none text-danger fw-bold -0 text-end"
            cpfValido = 1;
        }
    } catch (erro) {
        console.error("Erro ao verificar o CPF", erro);
        alert("Erro ao verificar o CPF. Tente novamente mais tarde.");
    }
}


//MÁSCARAS
//CPF
document.getElementById('cpf').addEventListener('input', function (e) {
    let value = e.target.value.replace(/\D/g, '');
    value = value.replace(/(\d{3})(\d)/, '$1.$2');
    value = value.replace(/(\d{3})(\d)/, '$1.$2');
    value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    e.target.value = value;
});
//TELEFONE
document.getElementById('telefone').addEventListener('input', function (e) {
    let value = e.target.value.replace(/\D/g, ''); // Remove caracteres não numéricos
    value = value.replace(/(\d{2})(\d)/, '($1) $2'); // Adiciona parênteses e espaço após DDD
    value = value.replace(/(\d{5})(\d)/, '$1-$2'); // Adiciona hífen no meio do número
    e.target.value = value;
});

