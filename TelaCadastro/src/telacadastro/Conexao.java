package telacadastro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 */
public class Conexao {

    public static Connection getConexao() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/teste", "root", "senac");
            System.out.println("conectado");
            return connection;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar com o banco de dados!\nErro: "+ e);
        }
        return null;
    }

public static void main(String[] args) {
Conexao.getConexao();
    }
}