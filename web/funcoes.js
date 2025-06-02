require("dotenv").config();
const nodemailer = require("nodemailer");
const jwt = require("jsonwebtoken");

class FuncaoUteis {
    criarToken(email) {
        return jwt.sign({ email }, process.env.SEGREDO_JWT, { expiresIn: '10m' });
    }
    
    async enviarEmail(email, res) {
        try {
            const transportador = nodemailer.createTransport({
                host: 'smtp.gmail.com',
                port: 465,
                secure: true,
                auth: {
                    user: process.env.EMAIL_USER,
                    pass: process.env.EMAIL_PASS
                }
            });
            let token = this.criarToken(email); 
            
            await transportador.sendMail({
                from: 'Green Line <greenline.ecologic@gmail.com>',
                to: email,
                subject: 'Confirmação de email',
                html: `
              <h1>Faça do meio ambiente o seu meio de vida</h1>
              <p>Olá, obrigado por se cadastrar na Green Line! Confirme seu e-mail para começar a usar a plataforma:</p>
              <a href="http://localhost:3000/validar?token=${token}" style="padding:10px 20px; background-color:#007bff; color:white; text-decoration:none; border-radius:5px;">
                Confirmar Email
              </a>
            `
            });
            console.log("✅ E-mail enviado com sucesso.");
            if (res) { 
                res.status(200).json({ mensagem: "Verifique seu e-mail para confirmação." });
            }
        } catch (erro) {
            console.error("Erro ao enviar o email:", erro);
            if (res) {  
                res.status(500).json({ erro: "Erro durante o processo envio" });
            }
        }
    };
    
}

module.exports = FuncaoUteis;