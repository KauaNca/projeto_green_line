let nome = window.document.getElementById('nome');

let email = document.querySelector('#email');

let assunto = document.querySelector('#assunto');


let nomeOk = false;
let emailOk = false;
let assuntoOk = false;

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


function validaNome() {
    let txtNome = document.querySelector("#txtNome");
    if(nome.value.length < 3){
        txtNome.innerHTML = "Nome inválido!!";
        txtNome.style.color = "red";
    } else{
        txtNome.innerHTML = "Nome valido";
        txtNome.style.color = "green";
        nomeOk= true;
    }   
}

function validaEmail(){
    let txtEmail = document.querySelector("#txtEmail")

    if(email.value.indexOf("@") == -1 || email.value.indexOf(".") == -1){
        txtEmail.innerHTML = "E-mail inválido!!"
         txtEmail.style.color = "red"
    }else{
        txtEmail.innerHTML = "Nome valido"
        txtEmail.style.color = "green"
        emailOk= true
    }   
    
}

function validaAssunto(){
    let txtAssunto = document.querySelector("#txtAssunto")

    if (assunto.value.length >= 100){
        txtAssunto.innerHTML = "Texto muito grande!!"
        txtAssunto.style.color = "red"
    }else{
        txtAssunto.innerHTML = "O texto deve ter no máximo 100 Caracteres"
        txtAssunto.style.color = "green"
        assuntoOk = true
    }   
}

function enviar(){
    if(nomeOk == true && emailOk == true && assuntoOk == true){
        alert("Formulario enviado com sucesso!!")
    }else{
        alert("Preencha o formulario corretamente")
    }
}