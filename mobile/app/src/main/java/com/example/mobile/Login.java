package com.example.mobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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
import java.sql.SQLException;

public class Login extends AppCompatActivity {
private Button btnEntrar;
private EditText usuario,senha;
private TextView cadastro;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    btnEntrar = findViewById(R.id.btnEntrar);
    usuario = findViewById(R.id.usuario);
    senha = findViewById(R.id.campoSenha);
    cadastro = findViewById(R.id.cadastrar);

        if(usuario.getText().toString().isBlank() && senha.getText().toString().isBlank()) {
            btnEntrar.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#D7D6D7")));
        }
    btnEntrar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!usuario.getText().toString().isBlank() && !senha.getText().toString().isBlank()){
                if (!isEmailValid(usuario.getText().toString()) && !isNameValid(usuario.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Por gentileza, insira email ou nome válidos.",Toast.LENGTH_LONG).show();
                    return;
                }
                Connection con = Conexao.conectar();
                if(con == null){
                    System.out.println("Conexao nula");
                    return;
                }
                try {
                    PreparedStatement stmt = null;
                    if(isEmailValid(usuario.getText().toString())){
                        stmt = con.prepareStatement("SELECT email,senha " +
                                "FROM mobile_login WHERE email = ? AND senha = ?");
                    }else if(isNameValid(usuario.getText().toString())){
                       stmt = con.prepareStatement("SELECT nome,senha " +
                                "FROM mobile_login WHERE nome = ? AND senha = ?");
                    }

                    stmt.setString(1, usuario.getText().toString());
                    stmt.setString(2,senha.getText().toString());
                    ResultSet rs = stmt.executeQuery();
                    if(rs.next()){
                        Intent home = new Intent(Login.this, MainActivity.class);
                        startActivity(home);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Usuário e senha estão errados ou não existem.",Toast.LENGTH_LONG).show();

                    }
                    rs.close();
                    stmt.close();
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }
            else {
                Toast.makeText(getApplicationContext(),"Campos de usuário e senha estão vazios",Toast.LENGTH_LONG).show();

            }
        }
    });
    cadastro.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent cadastro = new Intent(Login.this,CadastroUsuario.class);
            startActivity(cadastro);
            finish();
        }
    });
    }
    /**
     * Valida se o texto inserido é um e-mail válido.
     */
    private boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    /**
     * Valida se o texto inserido é um nome válido (apenas letras).
     */
    private boolean isNameValid(String name) {
        String nameRegex = "^[a-zA-Z\\s]+$"; // Somente letras e espaços
        return name.matches(nameRegex);
    }
}