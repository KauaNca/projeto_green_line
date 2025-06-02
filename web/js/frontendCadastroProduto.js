// IIFE cria um escopo isolado, evitando que variáveis e funções internas vazem para o escopo global (window).
(function () {
    'use strict';
    const formulario = document.getElementById('formulario-produto');
    const campoDescricao = document.getElementById('descricao-curta');
    const contadorDescricao = document.getElementById('contador-descricao');
    const campoDetalhada = document.getElementById('descricao-detalhada');
    const contadorDetalhada = document.getElementById('contador-detalhada');
    const imagensInput = document.getElementById('imagens-produto');
    const errorElement = document.getElementById('error-message');
    const successElement = document.getElementById('success-message');

    // Configurações
    const MAX_IMAGE_SIZE_MB = 5;
    const MAX_IMAGES = 3;
    //const BACKEND_URL = '';

    // Inicializa contadores
    contadorDescricao.textContent = campoDescricao.value.length;
    contadorDetalhada.textContent = campoDetalhada.value.length;

    // Event listeners para contagem de caracteres
    campoDescricao.addEventListener('input', () => {
        contadorDescricao.textContent = campoDescricao.value.length;
    });

    campoDetalhada.addEventListener('input', () => {
        contadorDetalhada.textContent = campoDetalhada.value.length;
    });

    // Função para converter arquivo para base64 com validação
    async function fileToBase64(file) {
        return new Promise((resolve, reject) => {
            // Verifica tamanho do arquivo
            if (file.size > MAX_IMAGE_SIZE_MB * 1024 * 1024) {
                reject(new Error(`A imagem ${file.name} excede o limite de ${MAX_IMAGE_SIZE_MB}MB`));
                return;
            }

            const reader = new FileReader();
            reader.onload = () => {
                try {
                    const base64String = reader.result.split(',')[1];
                    if (!base64String) {
                        throw new Error('Formato de imagem inválido');
                    }
                    resolve(base64String);
                } catch (error) {
                    reject(error);
                }
            };
            reader.onerror = error => reject(error);
            reader.readAsDataURL(file);
        });
    }

    // Função para mostrar mensagens de feedback
    function showFeedback(message, isSuccess = false) {
        if (isSuccess) {
            successElement.textContent = message;
            successElement.style.display = 'block';
            errorElement.style.display = 'none';
        } else {
            errorElement.textContent = message;
            errorElement.style.display = 'block';
            successElement.style.display = 'none';
        }
        
        setTimeout(() => {
            errorElement.style.display = 'none';
            successElement.style.display = 'none';
        }, 5000);
    }

    // Função para formatar preço
    function formatPrice(value) {
        if (!value) return '0,00';
        return parseFloat(value.replace(',', '.'))
            .toFixed(2)
            .replace('.', ',');
    }

    // Função para atualizar a pré-visualização
    const atualizarPreVisualizacao = () => {
        const nome = document.getElementById('nome-produto').value || 'Nome do produto';
        const descricao = document.getElementById('descricao-curta').value || 'Descrição curta';
        const preco = document.getElementById('preco').value
            ? `R$ ${formatPrice(document.getElementById('preco').value)}`
            : 'R$ 0,00';
        const ativo = document.getElementById('produto-ativo').checked;
        const promocao = document.getElementById('promocao').checked;

        document.getElementById('preview-nome').textContent = nome;
        document.getElementById('preview-descricao').textContent = descricao;
        document.getElementById('preview-preco').textContent = preco;
        document.getElementById('badge-ativo').style.display = ativo ? 'inline-block' : 'none';
        document.getElementById('badge-promocao').classList.toggle('d-none', !promocao);
        document.getElementById('preco-promocional-div').classList.toggle('d-none', !promocao);

        const arquivoImagem = document.getElementById('imagens-produto');
        if (arquivoImagem.files && arquivoImagem.files[0]) {
            const leitor = new FileReader();
            leitor.onload = function (e) {
                document.getElementById('imagem-preview').src = e.target.result;
            };
            leitor.readAsDataURL(arquivoImagem.files[0]);
        }
    };

    // Event listener para o formulário
    formulario.addEventListener('submit', async (event) => {
        event.preventDefault();

        // Validação do formulário
        if (!formulario.checkValidity()) {
            event.stopPropagation();
            formulario.classList.add('was-validated');
            showFeedback('Por favor, preencha todos os campos obrigatórios');
            return;
        }

        try {
            // Mostrar estado de carregamento
            const submitButton = formulario.querySelector('button[type="submit"]');
            const originalButtonText = submitButton.textContent;
            submitButton.disabled = true;
            submitButton.innerHTML = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Enviando...';

            // Processar imagens
            const imagens = document.getElementById('imagens-produto').files;
            
            // Validar número de imagens
            if (imagens.length > MAX_IMAGES) {
                throw new Error(`Máximo de ${MAX_IMAGES} imagens permitidas`);
            }

            // Converter imagens para base64
            const imagensBase64 = await Promise.all(
                Array.from(imagens).slice(0, MAX_IMAGES).map(fileToBase64)
            );

            // Preparar dados para envio
            const dados = {
                nome: document.getElementById('nome-produto').value,
                marca: document.getElementById('marca-produto').value,
                descricao_curta: document.getElementById('descricao-curta').value,
                descricao_detalhada: document.getElementById('descricao-detalhada').value,
                categoria: document.getElementById('categoria').value,
                promocao: document.getElementById('promocao').checked,
                estoque: document.getElementById('estoque').value,
                peso: document.getElementById('peso').value || 0,
                dimensoes: document.getElementById('dimensoes').value || "0x0x0",
                preco: document.getElementById('preco').value || 0,
                preco_promocional: document.getElementById('preco-promocional').value || 0,
                ativo: document.getElementById('produto-ativo').checked,
                parcelas: document.getElementById('parcelas').value,
                avaliacao: document.getElementById('avaliacao').value,
                quantidade_avaliacao: document.getElementById('quantidade-avaliacoes').value,
                imagem_1: imagensBase64[0] || null,
                imagem_2: imagensBase64[1] || null,
                imagem_3: imagensBase64[2] || null
            };

            // Enviar para o backend
            const response = await fetch('https://green-line-web.onrender.com/cadastro-produto', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dados)
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.error || `Erro HTTP: ${response.status}`);
            }

            const data = await response.json();
            console.log('Sucesso:', data);
            showFeedback('Produto cadastrado com sucesso!', true);
            
            // Resetar formulário após sucesso
            formulario.reset();
            formulario.classList.remove('was-validated');
            atualizarPreVisualizacao();

        } catch (error) {
            console.error('Falha no cadastro:', error);
            showFeedback(`Erro ao cadastrar: ${error.message}`);
        } finally {
            // Restaurar botão de submit
            const submitButton = formulario.querySelector('button[type="submit"]');
            submitButton.disabled = false;
            submitButton.textContent = originalButtonText;
            formulario.classList.add('was-validated');
        }
    });

    // Event listeners para atualização em tempo real
    document.querySelectorAll('#formulario-produto input, #formulario-produto textarea, #formulario-produto select')
        .forEach(elemento => {
            elemento.addEventListener('input', atualizarPreVisualizacao);
            elemento.addEventListener('change', atualizarPreVisualizacao);
        });

    // Formatadores de preço
    document.querySelectorAll('#preco, #preco-promocional').forEach(elemento => {
        elemento.addEventListener('input', function () {
            let valor = this.value.replace(/\D/g, '');
            valor = (valor / 100).toFixed(2).replace('.', ',');
            this.value = valor;
        });
    });

    // Validação customizada para imagens
    imagensInput.addEventListener('change', () => {
        const files = imagensInput.files;
        if (files.length > MAX_IMAGES) {
            showFeedback(`Máximo de ${MAX_IMAGES} imagens permitidas`);
            imagensInput.value = '';
        }
        
        for (let file of files) {
            if (file.size > MAX_IMAGE_SIZE_MB * 1024 * 1024) {
                showFeedback(`A imagem ${file.name} excede o limite de ${MAX_IMAGE_SIZE_MB}MB`);
                imagensInput.value = '';
                break;
            }
        }
        
        atualizarPreVisualizacao();
    });
})();