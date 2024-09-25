function textoEscondido() {
    let textoEscondido = document.querySelectorAll('.texto-escondido');
    
    window.getComputedStyle(textoEscondido).
    console.log(textoEscondido);
    // Verificar o estilo computado do elemento
    if (window.getComputedStyle(textoEscondido).display === 'none') {
        textoEscondido.style.display = 'block';
    } else {
        textoEscondido.style.display = 'none';
    }
}
