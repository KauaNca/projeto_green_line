const formularioLogin = document.getElementById('formularioLogin');


const botaoEntrar = document.getElementById('entrar-na-conta');
const mensagemUsuario = document.getElementById('mensagem-superior');

formularioLogin.addEventListener('submit', async function (e) {
    e.preventDefault();

    // Obter valores dos campos
    const usuario = document.getElementById('usuario').value.trim();
    const senha = document.getElementById('senha').value.trim();

    // Resetar estados
    mensagemUsuario.textContent = '';
    mensagemUsuario.className = 'd-none';
    botaoEntrar.disabled = true;
    botaoEntrar.classList.replace('btn-success', 'btn-secondary');

    // Validação básica
    if (!usuario || !senha) {
        mostrarMensagem('Preencha todos os campos.', 'warning');
        reativarBotao();
        return;
    }

    try {
        const resposta = await fetch('https://green-line-web.onrender.com/verificarConta', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ usuario, senha })
        });

        const dados = await resposta.json();
        console.log('Resposta:', dados);

        switch (dados.dadosValidos) {
            case 0:
                mostrarMensagem('Conta não encontrada.', 'danger');
                break;
            case 1:
                mostrarMensagem("Email não verificado. Verifique sua caixa de entrada.", "warning");
                if (usuario.includes("@")) {
                    try {
                        await fetch("https://green-line-web.onrender.com/enviarEmail", {
                            method: "POST",
                            headers: { "Content-Type": "application/json" },
                            body: JSON.stringify({ usuario })
                        });
                    } catch (erro) {
                        console.log("Erro ao enviar o email: ", erro);
                    }

                }
                break;
            case 2:
                try {
                    console.log(usuario);
                    await fetch("https://green-line-web.onrender.com/loginDados", {
                        method: "POST",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify({
                            usuario: usuario,
                            trocar: 1
                        })
                    });
                    localStorage.setItem("usuario", usuario);
                    window.location.href = '../index.html';
                } catch (erro) {
                    console.log("Erro ao enviar o email: ", erro);
                }

                return;
            case 3:
                mostrarMensagem('Credenciais inválidas.', 'danger');
                break;
            default:
                mostrarMensagem('Erro desconhecido.', 'warning');
        }
    } catch (erro) {
        console.error('Erro:', erro);
        mostrarMensagem('Falha na conexão. Tente novamente.', 'danger');
    } finally {
        reativarBotao();
    }
});

function mostrarMensagem(texto, tipo) {
    mensagemUsuario.textContent = texto;
    mensagemUsuario.className = `d-block alert text-bg-${tipo} text-center mb-2`;
}

function reativarBotao() {
    botaoEntrar.disabled = false;
    botaoEntrar.classList.replace('btn-secondary', 'btn-success');
}