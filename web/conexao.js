// conexao.js
const mysql = require('mysql2/promise');
require("dotenv").config();

class Conexao {
    constructor() {
        this.pool = mysql.createPool({
            host:process.env.DB_HOST,
            port:process.env.DB_PORTA,
            user:process.env.DB_USER,
            password: process.env.DB_PASSWORD,
            database: process.env.DB_NAME,
            waitForConnections: true,
            connectionLimit: 10,
            queueLimit: 0
        });
        console.log("💡 Pool de conexões criado com sucesso.");
    }

    async query(sql, params = []) {
        try {
            const [rows] = await this.pool.execute(sql, params);
            return rows;
        } catch (err) {
            console.error("Erro na consulta SQL:", err.message);
            throw err;
        }
    }

    async close() {
        try {
            await this.pool.end();
            console.log("Conexões encerradas.");
        } catch (err) {
            console.error("Erro ao encerrar conexões:", err.message);
        }
    }
}

module.exports = Conexao;
