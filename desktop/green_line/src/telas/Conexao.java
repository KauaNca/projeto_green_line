package telas;
import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Gabriel54274586
 */
public class Conexao {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
      private static final String URL = "jdbc:mysql://localhost:3307/green";
      private static final String USER = "root";
      private static final String PASS = "senac";
   
   
    public static Connection conexaoBanco() throws SQLException {
       
       
        try {
             Class.forName(DRIVER);
             Component preComponent = null;
                 
             
             
             return DriverManager.getConnection(URL,USER, PASS);
           
         } catch (ClassNotFoundException ex) {
            Component parentComponent = null;
           
             
      throw new RuntimeException("Erro no banco de dados",ex);
       
         }      
    }
    
    
    
    
}
