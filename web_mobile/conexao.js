const mysql = require('mysql2');

// Configuração da conexão
const connection = mysql.createConnection({
    host: '127.0.0.1',
    port: 3307, // Verifique se essa é a porta correta
    user: 'root',
    password: 'senac',
    database: 'green_line'
});

// Testando a conexão
connection.connect((err) => {
    if (err) {
        console.error('Erro ao conectar:', err.message);
        return;
    }
    console.log('Conexão bem-sucedida!');
});