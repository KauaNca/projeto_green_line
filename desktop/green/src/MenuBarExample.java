import javax.swing.*;
import java.awt.*;

public class MenuBarExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu Bar Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.BLACK); // Definindo a cor de fundo
        
        JMenu menu = new JMenu("Menu");
        menu.setForeground(Color.WHITE); // Definindo a cor do texto
        
        JMenuItem menuItem = new JMenuItem("Item");
        menuItem.setForeground(Color.BLACK); // Definindo a cor do texto do item
        menuItem.setBackground(Color.WHITE); // Definindo a cor de fundo do item
        
        menu.add(menuItem);
        menuBar.add(menu);
        
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }
}
