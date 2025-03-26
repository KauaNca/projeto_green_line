package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CadastroUsuario extends AppCompatActivity {
    private TextView login;
    private EditText nome, email, cpf, telefone, senha, repetirSenha;
    private Button btCadastrar, btCancelar;
    private LinearLayout camposDeCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        // Inicialize os componentes
        login = findViewById(R.id.entrar);
        nome = findViewById(R.id.nome);
        email = findViewById(R.id.email);
        cpf = findViewById(R.id.cpf);
        telefone = findViewById(R.id.telefone);
        senha = findViewById(R.id.senha);
        repetirSenha = findViewById(R.id.senha2);
        btCadastrar = findViewById(R.id.btCadastrar);
        btCancelar = findViewById(R.id.btCancelar);
        camposDeCadastro = findViewById(R.id.camposDeCadastro);

        // Configurações de máscara
        new Mascaras().nome(nome);
        new Mascaras().cpf(cpf);
        new Mascaras().email(email);
        new Mascaras().telefone(telefone);

        // Listener para abrir a tela de login
        login.setOnClickListener(v -> {
            Intent telaLogin = new Intent(CadastroUsuario.this, com.example.mobile.Login.class);
            startActivity(telaLogin);
            finish();
        });

        btCadastrar.setOnClickListener(v -> cadastrarUsuario());
        btCancelar.setOnClickListener(v -> limparCampos(camposDeCadastro));
    }

    private void cadastrarUsuario() {
        if (!componentesVazios(camposDeCadastro)) {
            Toast.makeText(getApplicationContext(), "Campos vazios. Preencha-os", Toast.LENGTH_LONG).show();
            return;
        }

        if (!senhaDupla()) return;

        try {
            Connection con = Conexao.conectar();
            if (con == null) {
                Toast.makeText(this, "Erro na conexão com o banco de dados", Toast.LENGTH_LONG).show();
                return;
            }

            // Inserir na tabela pessoa
            String sqlPessoa = "INSERT INTO pessoa(nome, email, cpf_cnpj, telefone) VALUES (?, ?, ?, ?)";
            PreparedStatement stmtPessoa = con.prepareStatement(sqlPessoa);
            stmtPessoa.setString(1, nome.getText().toString().trim());
            stmtPessoa.setString(2, email.getText().toString().trim());
            stmtPessoa.setString(3, cpf.getText().toString().trim());
            stmtPessoa.setString(4, telefone.getText().toString().trim());
            stmtPessoa.execute();

            // Recuperar ID da pessoa
            String idPessoa = pegarIdPessoa(con);
            if (idPessoa == null) {
                Toast.makeText(this, "Erro ao recuperar ID da pessoa", Toast.LENGTH_LONG).show();
                return;
            }

            // Inserir na tabela usuario
            String sqlUsuario = "INSERT INTO usuario(id_pessoa, id_tipo_usuario, senha, nivel_acesso, situacao) " +
                    "VALUES (?, 3, ?, 'Sem acesso', 'A')";
            PreparedStatement stmtUsuario = con.prepareStatement(sqlUsuario);
            stmtUsuario.setString(1, idPessoa);
            stmtUsuario.setString(2, senha.getText().toString());
            stmtUsuario.execute();

            // Fechar recursos
            stmtPessoa.close();
            stmtUsuario.close();
            con.close();

            limparCampos(camposDeCadastro);
            Toast.makeText(getApplicationContext(), "Cadastro concluído.", Toast.LENGTH_SHORT).show();

            // Navegar para a tela de login
            Intent telaLogin = new Intent(CadastroUsuario.this, com.example.mobile.Login.class);
            startActivity(telaLogin);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao cadastrar: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private boolean componentesVazios(ViewGroup viewGroup) {
        boolean camposPreenchidos = true;
        for (int x = 0; x < viewGroup.getChildCount(); x++) {
            View view = viewGroup.getChildAt(x);
            if (view instanceof EditText) {
                EditText campo = (EditText) view;
                String texto = campo.getText().toString().trim();
                if (texto.isEmpty()) {
                    campo.setError("Campo obrigatório");
                    camposPreenchidos = false;
                }
            }
        }
        return camposPreenchidos;
    }

    private String pegarIdPessoa(Connection con) {
        String idPessoa = null;
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT id_pessoa FROM pessoa ORDER BY id_pessoa DESC LIMIT 1");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                idPessoa = rs.getString("id_pessoa");
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idPessoa;
    }

    private boolean senhaDupla() {
        if (!senha.getText().toString().equals(repetirSenha.getText().toString())) {
            Toast.makeText(this, "Senhas diferentes. Preencha novamente", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void limparCampos(ViewGroup viewGroup) {
        for (int x = 0; x < viewGroup.getChildCount(); x++) {
            View view = viewGroup.getChildAt(x);
            if (view instanceof EditText) {
                EditText campo = (EditText) view;
                campo.setText("");
            }
        }
    }
}