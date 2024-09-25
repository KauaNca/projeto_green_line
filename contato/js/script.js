let nome = window.document.getElementById('nome');

let email = document.querySelector('#email');

let assunto = document.querySelector('#assunto');


let nomeOk = false;
let emailOk = false;
let assuntoOk = false;



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