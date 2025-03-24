package com.example.mobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mobile.ui.home.HomeFragment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.transform.Result;

public class Login extends AppCompatActivity {
private Button btnEntrar;
private EditText email,senha;


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
    email = findViewById(R.id.usuario);
    senha = findViewById(R.id.campoSenha);
    btnEntrar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!email.getText().toString().isBlank() && !senha.getText().toString().isBlank()){
                Connection con = DatabaseConnection.getConnection();
                if(con == null){
                    System.out.println("Conexao nula");
                }
                try {
                    PreparedStatement stmt = con.prepareStatement("SELECT nome,senha " +
                            "FROM usuario INNER JOIN pessoa ON pessoa.id_pessoa = usuario.id_pessoa WHERE nome = ? AND senha = ?");
                    stmt.setString(1,email.getText().toString());
                    stmt.setString(2,senha.getText().toString());
                    ResultSet rs = stmt.executeQuery();
                    if(rs.next()){
                        Intent home = new Intent(Login.this, MainActivity.class);
                        startActivity(home);
                        finish();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    });
    }
}