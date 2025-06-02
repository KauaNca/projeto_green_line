const botaoEnviar = document.getElementById('enviar-recuperacao');
botaoEnviar.addEventListener("click", async (event) => {
    event.preventDefault(); // Evita recarregar a página ao enviar o formulário

    const inputEmail = document.getElementById('email').value;
    try {
        const resposta = await fetch(`https://green-line-web.onrender.com/enviar-email?email=${inputEmail}`);
        const dado = await resposta.json();
        console.log(dado); 

        if (dado.conclusao === 2) {
            mudarAlerta("success", "foi");
        } else {
            mudarAlerta("danger", "não");
        }
    } catch (error) {
        console.error("Erro ao enviar email:", error);
        mudarAlerta("danger", "falhou");
    }
});
function mudarAlerta(cor, texto) {
    const alert = document.getElementById('resposta');
    alert.className = `alert alert-${cor} d-block`; // Remove "d-none"
    alert.innerHTML = `Email ${texto} enviado`;
}

//ESTÁ FALTANDO A LÓGICA DE MUDAR A SENHA
//POR AGORA, PENSEI EM REINICIAR A SENHA DO USUÁRIO DO EMAIL