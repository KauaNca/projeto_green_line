import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
    Font fontePadrao = new Font("Arial", Font.PLAIN, 28);
    Font fonteItem = new Font("Arial", Font.PLAIN, 25);
    JLabel wallpaper = new JLabel(new ImageIcon(getClass().getResource("img/telaInicial.jpg")));
    Color corVerde = new Color(29,68,53);
    EmptyBorder bordaItemMenu = new EmptyBorder(0,20,0,20);
 
    public TelaInicial3() {
        Mouse();
        // Configuração do menu
        Menu();
        
        // Configuração do frame
        Frame();
        
    }

    public void Menu() {
        // Ajustando a aparência do menu superior
        menuSuperior.setBackground(corVerde);
        menuSuperior.setPreferredSize(new Dimension(1728, 90));
        menuSuperior.setBorderPainted(false);

        Cadastro.setForeground(Color.white);
        Cadastro.setFont(fontePadrao);
        Cadastro.setBorder(bordaItemMenu);
        Relatorios.setForeground(Color.white);
        Relatorios.setFont(fontePadrao);
        Relatorios.setBorder(bordaItemMenu);
        Editar.setForeground(Color.white);
        Editar.setFont(fontePadrao);
        Editar.setBorder(bordaItemMenu);
        Financeiro.setForeground(Color.white);
        Financeiro.setFont(fontePadrao);
        Financeiro.setBorder(bordaItemMenu);
        Agenda.setForeground(Color.white);
        Agenda.setFont(fontePadrao);
        Agenda.setBorder(bordaItemMenu);
        Notificacoes.setForeground(Color.white);
        Notificacoes.setFont(fontePadrao);
        Notificacoes.setBorder(bordaItemMenu);
        
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
    // Redimensiona a imagem para caber na janela
    ImageIcon imageIcon = new ImageIcon(getClass().getResource("img/telaInicial.jpg"));
    Image image = imageIcon.getImage(); // Transformando em um objeto Image
    Image scaledImage = image.getScaledInstance(1600, 750, java.awt.Image.SCALE_DEFAULT); // Redimensionando a imagem
    ImageIcon scaledIcon = new ImageIcon(scaledImage); // Transformando de volta para um ImageIcon
    wallpaper.setIcon(scaledIcon); // Definindo o ícone redimensionado no JLabel

    // Configuração do JFrame
    setSize(1600, 1000);  // Tamanho da janela
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());  // Usando BorderLayout
    setLocationRelativeTo(null);  // Centraliza a janela
    add(BorderLayout.CENTER, wallpaper);

    // Tornando a janela visível
    setVisible(true);
}
    
    public void Mouse(){
        Cadastro.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e){
                Cadastro.setForeground(Color.black);
            }
            public void mouseExited(MouseEvent e){
                Cadastro.setForeground(Color.white);
            }
        });
        
        Relatorios.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e){
                Relatorios.setForeground(Color.black);
            }
            public void mouseExited(MouseEvent e){
                Relatorios.setForeground(Color.white);
            }
        });
        
        Editar.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e){
                Editar.setForeground(Color.black);
            }
            public void mouseExited(MouseEvent e){
                Editar.setForeground(Color.white);
            }
        });
        
        Financeiro.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e){
                Financeiro.setForeground(Color.black);
            }
            public void mouseExited(MouseEvent e){
                Financeiro.setForeground(Color.white);
            }
        });
        
        Agenda.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e){
                Agenda.setForeground(Color.black);
            }
            public void mouseExited(MouseEvent e){
                Agenda.setForeground(Color.white);
            }
        });
        
        Notificacoes.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e){
                Notificacoes.setForeground(Color.black);
            }
            public void mouseExited(MouseEvent e){
                Notificacoes.setForeground(Color.white);
            }
        });
    }

    public static void main(String[] args) {
        new TelaInicial3();
    }
}
