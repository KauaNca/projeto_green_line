import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Gabriel54274586
 */
public class Conexao {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3307/green_line";
    private static final String USER = "root";
    private static final String PASS = "senac";

    public static Connection conexaoBanco() {

        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);

        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Erro no banco de dados", ex);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage());
            System.out.println("ERRO: " + e);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            System.out.println("ERRO: " + e);
        }
        return null;
    }

    public static void main(String args[]) {
        new Conexao();
        System.out.println("CONEXÃO FEITA");
    }

}
