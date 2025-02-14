
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class TelaInicial3 extends JFrame {

    JMenuBar menuSuperior = new JMenuBar();
    JMenu Cadastro = new JMenu("Cadastro");
    JMenu Relatorios = new JMenu("Relatórios");
    JMenu Editar = new JMenu("Editar");
    JMenu Financeiro = new JMenu("Financeiro");
    JMenu Agenda = new JMenu("Agenda");
    JMenu Notificacoes = new JMenu("Notificações");
    JMenuItem cadastroUsuario = new JMenuItem("Usuario");
    JMenuItem cadastroProdutos = new JMenuItem("Produtos");
    JMenuItem cadastroCategorias = new JMenuItem("Categorias");
    Font fontePadrao = new Font("Arial", Font.PLAIN, 32);
    Font fonteItem = new Font("Arial", Font.PLAIN, 28);
    JLabel wallpaper = new JLabel(new ImageIcon(getClass().getResource("img/telaInicial.jpg")));
    JPanel linhaBaixo = new JPanel();
 
    public TelaInicial3() {
        // Configuração do menu
        Menu();

        // Configuração do painel inferior (linhaBaixo)
        linhaBaixo.setPreferredSize(new Dimension(1728, 80)); // Tamanho correto do painel
        linhaBaixo.setBackground(Color.black);

        // Configuração do frame
        Frame();
    }

    public void Menu() {
        // Ajustando a aparência do menu superior
        menuSuperior.setBackground(Color.black);
        menuSuperior.setPreferredSize(new Dimension(1728, 103));
        menuSuperior.setBorderPainted(false);

        Cadastro.setForeground(Color.white);
        Cadastro.setFont(fontePadrao);
        Relatorios.setForeground(Color.white);
        Relatorios.setFont(fontePadrao);
        Editar.setForeground(Color.white);
        Editar.setFont(fontePadrao);
        Financeiro.setForeground(Color.white);
        Financeiro.setFont(fontePadrao);
        Agenda.setForeground(Color.white);
        Agenda.setFont(fontePadrao);
        Notificacoes.setForeground(Color.white);
        Notificacoes.setFont(fontePadrao);

        cadastroUsuario.setForeground(Color.black);
        cadastroUsuario.setFont(fonteItem);
        cadastroProdutos.setForeground(Color.black);
        cadastroProdutos.setFont(fonteItem);
        cadastroCategorias.setForeground(Color.black);
        cadastroCategorias.setFont(fonteItem);

        // Adicionando os itens ao menu "Cadastro"
        Cadastro.add(cadastroUsuario);
        Cadastro.add(cadastroProdutos);
        Cadastro.add(cadastroCategorias);

        // Adicionando os menus à barra de menu
        menuSuperior.add(Cadastro);
        menuSuperior.add(Relatorios);
        menuSuperior.add(Editar);
        menuSuperior.add(Financeiro);
        menuSuperior.add(Agenda);
        menuSuperior.add(Notificacoes);

        // Adicionando a barra de menus ao JFrame
        setJMenuBar(menuSuperior);
    }

    public void Frame() {
        // Configuração do JFrame
        setSize(1728, 1117);  // Tamanho da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());  // Usando BorderLayout
        setLocationRelativeTo(null);  // Centraliza a janela
      
        add(BorderLayout.CENTER,wallpaper);
        
        // Adicionando a linha inferior
        add(BorderLayout.SOUTH, linhaBaixo); 
        // Tornando a janela visível
        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaInicial3();
    }
}
