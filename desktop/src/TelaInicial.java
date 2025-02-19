
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TelaInicial extends JFrame {

    JMenuBar menuSuperior = new JMenuBar();
    JMenu Cadastro = new JMenu("Cadastro");
    JMenu Relatorios = new JMenu("Relatórios");
    JMenu Editar = new JMenu("Editar");
    JMenu Agenda = new JMenu("Agenda");
    JMenu Notificacoes = new JMenu("Notificações");
    JMenu Configuracoes = new JMenu("Configurações");
    JMenu SuaConta = new JMenu("Sua conta");
    JMenuItem cadastroUsuario = new JMenuItem("Usuário");
    JMenuItem cadastroProdutos = new JMenuItem("Produtos");
    JMenuItem cadastroCategorias = new JMenuItem("Categorias");
    JMenuItem editarUsuario = new JMenuItem("Usuário");
    JMenuItem editarProdutos = new JMenuItem("Produtos");
    JMenuItem editarCategorias = new JMenuItem("Categorias");
    JMenuItem sair = new JMenuItem("Sair");
    JMenuItem seusDados = new JMenuItem("Seus dados");
    Font fontePadrao = new Font("Arial", Font.PLAIN, 28);
    Font fonteItem = new Font("Arial", Font.PLAIN, 25);
    JPanel painelDeFundo = new JPanel();
    JPanel linhaDeBaixo = new JPanel();
    Color corVerde = new Color(29, 68, 53);
    Color corDeFundo = new Color(255, 242, 207);
    EmptyBorder bordaItemMenu = new EmptyBorder(0, 20, 0, 20);

    public TelaInicial() {
        JOptionPane();
        Menu();
        Frame();
        Mouse();

    }

    public void JOptionPane() {
        ImageIcon imagem = new ImageIcon(TelaInicial.class.getResource("imagens/notificacao.png"));
        if (imagem.getIconWidth() == -1) {
            System.out.println("Ícone não encontrado");
        } else {
            // Redimensionar ícone se for necessário
            Image image = imagem.getImage();
            Image scaledImage = image.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
        String messagem = "<html><h1>Bem-vindo!</h1><p>Estamos felizes em tê-lo conosco.</p></html>";
        JLabel titulo = new JLabel(messagem);
        JOptionPane.showMessageDialog(null, titulo, "Bem-vindo!",JOptionPane.INFORMATION_MESSAGE,scaledIcon);
    }
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
        Agenda.setForeground(Color.white);
        Agenda.setFont(fontePadrao);
        Agenda.setBorder(bordaItemMenu);
        Notificacoes.setForeground(Color.white);
        Notificacoes.setFont(fontePadrao);
        Notificacoes.setBorder(bordaItemMenu);
        Configuracoes.setForeground(Color.white);
        Configuracoes.setFont(fontePadrao);
        Configuracoes.setBorder(bordaItemMenu);
        SuaConta.setForeground(Color.white);
        SuaConta.setFont(fontePadrao);
        SuaConta.setBorder(bordaItemMenu);

        cadastroUsuario.setForeground(Color.black);
        cadastroUsuario.setFont(fonteItem);
        cadastroProdutos.setForeground(Color.black);
        cadastroProdutos.setFont(fonteItem);
        cadastroCategorias.setForeground(Color.black);
        cadastroCategorias.setFont(fonteItem);

        editarUsuario.setForeground(Color.black);
        editarUsuario.setFont(fonteItem);
        editarProdutos.setForeground(Color.black);
        editarProdutos.setFont(fonteItem);
        editarCategorias.setForeground(Color.black);
        editarCategorias.setFont(fonteItem);

        sair.setForeground(Color.black);
        sair.setFont(fonteItem);
        seusDados.setForeground(Color.black);
        seusDados.setFont(fonteItem);

        // Adicionando os itens ao menu "Cadastro"
        Cadastro.add(cadastroUsuario);
        Cadastro.add(cadastroProdutos);
        Cadastro.add(cadastroCategorias);

        Editar.add(editarUsuario);
        Editar.add(editarProdutos);
        Editar.add(editarCategorias);

        SuaConta.add(seusDados);
        SuaConta.add(sair);

        // Adicionando os menus à barra de menu
        menuSuperior.add(Cadastro);
        menuSuperior.add(Relatorios);
        menuSuperior.add(Editar);
        menuSuperior.add(Agenda);
        menuSuperior.add(Notificacoes);
        menuSuperior.add(Configuracoes);
        menuSuperior.add(SuaConta);

        // Adicionando a barra de menus ao JFrame
        setJMenuBar(menuSuperior);
    }

    public void Frame() {
        // Configuração do JFrame
        setSize(1400, 800);  // Tamanho da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());  // Usando BorderLayout
        setLocationRelativeTo(null);  // Centraliza a janela
        painelDeFundo.setPreferredSize(new Dimension(1400, 800));
        painelDeFundo.setBackground(corDeFundo);
        linhaDeBaixo.setPreferredSize(new Dimension(1400, 60));
        linhaDeBaixo.setBackground(corVerde);
        add(painelDeFundo);
        add(BorderLayout.SOUTH, linhaDeBaixo);
        // Tornando a janela visível
        setVisible(true);
    }

    public void Mouse() {
        Cadastro.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                Cadastro.setForeground(Color.black);
            }

            public void mouseExited(MouseEvent e) {
                Cadastro.setForeground(Color.white);
            }
        });

        Relatorios.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                Relatorios.setForeground(Color.black);
            }

            public void mouseExited(MouseEvent e) {
                Relatorios.setForeground(Color.white);
            }
        });

        Editar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                Editar.setForeground(Color.black);
            }

            public void mouseExited(MouseEvent e) {
                Editar.setForeground(Color.white);
            }
        });

        SuaConta.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                SuaConta.setForeground(Color.black);
            }

            public void mouseExited(MouseEvent e) {
                SuaConta.setForeground(Color.white);
            }
        });

        Agenda.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                Agenda.setForeground(Color.black);
            }

            public void mouseExited(MouseEvent e) {
                Agenda.setForeground(Color.white);
            }
        });

        Notificacoes.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                Notificacoes.setForeground(Color.black);
            }

            public void mouseExited(MouseEvent e) {
                Notificacoes.setForeground(Color.white);
            }
        });

        sair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();

                dispose();
            }
        });
    }

    public static void main(String[] args) {
        new TelaInicial();
    }
}
