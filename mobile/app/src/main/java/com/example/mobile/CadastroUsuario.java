package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CadastroUsuario extends AppCompatActivity {
    private TextView login;
    private EditText nome,email,cpf,telefone,senha,repetirSenha;
    private Button btCadastrar;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_usuario);

        // Inicialize o TextView usando findViewById
        login = findViewById(R.id.entrar);

        // Configure o listener somente após a inicialização
        login.setOnClickListener(v -> {
            Intent telaLogin = new Intent(CadastroUsuario.this, com.example.mobile.Login.class);
            startActivity(telaLogin);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nome = findViewById(R.id.nome);
        email = findViewById(R.id.email);
        cpf = findViewById(R.id.cpf);
        telefone = findViewById(R.id.telefone);
        senha = findViewById(R.id.senha);
        repetirSenha = findViewById(R.id.senha2);
        btCadastrar = findViewById(R.id.btCadastrar);

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_pessoa;
                ViewGroup view = findViewById(R.id.main);
                if(componentesVazios(view)){
                    if(senhaDupla()){
                        try {
                            Connection con = Conexao.conectar();
                            PreparedStatement stmt = con.prepareStatement("INSERT INTO pessoa(nome,email,cpf,telefone) VALUES (?,?,?,?)");
                            stmt.setString(1,nome.getText().toString().trim());
                            stmt.setString(2,email.getText().toString().trim());
                            stmt.setString(3,cpf.getText().toString().trim());
                            stmt.setString(4,telefone.getText().toString().trim());
                            stmt.execute();
                            id_pessoa = pegarIdPessoa();

                            PreparedStatement stmt2 = con.prepareStatement("INSERT INTO usuario(id_pessoa,id_tipo_usuario,senha,nivel_acesso,situacao)" +
                                    "VALUES(?,3,?,Sem acesso,A)");
                            stmt2.setString(1,id_pessoa);
                            stmt2.setString(2,senha.getText().toString());
                            stmt2.execute();

                        }
                        catch (Exception e){

                        }
                    }

                }
            }
        });

    }
    private boolean componentesVazios(ViewGroup viewGroup){
       boolean camposPreenchidos = true;
        for(int x = 0; x< viewGroup.getChildCount();x++){
            View view = viewGroup.getChildAt(x);
            if(view instanceof EditText){
                EditText campo = (EditText) view;
                String texto = campo.getText().toString().trim();
                if(texto.isBlank()){
                    campo.setError("Campo obrigatório");
                    camposPreenchidos = false;
                }
            }
        }
        return camposPreenchidos;
    }
    private String pegarIdPessoa(){
        String id_pessoa = null;
        try {
            Connection con = Conexao.conectar();
            PreparedStatement stmt = con.prepareStatement("SELECT id_pessoa FROM pessoa ORDER BY id_pessoa DESC LIMIT 1;");
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                id_pessoa = rs.getString("id_pessoa");
            }
            else{
                Toast.makeText(this,"ID pessoa não encontrado",Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return id_pessoa;
    }
    private boolean senhaDupla(){
        boolean senhasIguais = true;
        if(senha.getText().toString()!=repetirSenha.getText().toString()){
            Toast.makeText(this,"Senhas diferentes.Preencha novamente",Toast.LENGTH_SHORT).show();
            senhasIguais = false;
        }
        else {
            senhasIguais = true;
        }
        return senhasIguais;
    }
}