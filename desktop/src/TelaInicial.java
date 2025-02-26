
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
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
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
    JMenu Produtos = new JMenu("Produtos");
    JMenu Vendas = new JMenu("Vendas");
    JMenu Notificacoes = new JMenu("Notificações");
    JMenu Configuracoes = new JMenu("Configurações");
    JMenu SuaConta = new JMenu("Sua conta");
    JMenuItem ItemVendas = new JMenuItem("Vendas");
    JMenuItem cadastroUsuario = new JMenuItem("Usuário");
    JMenuItem cadastroProdutos = new JMenuItem("Produtos");
    JMenuItem cadastroCategorias = new JMenuItem("Categorias");
    JMenuItem editarUsuario = new JMenuItem("Usuário");
    JMenuItem PesquisarEEditar = new JMenuItem("Pesquisar e editar");
    JMenuItem editarCategorias = new JMenuItem("Categorias");
    JMenuItem sair = new JMenuItem("Sair");
    JMenuItem seusDados = new JMenuItem("Seus dados");
    JDesktopPane painelPrincipal = new JDesktopPane();
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
        ImageIcon imagem = new ImageIcon("imagens/notificacao.png");
        if (imagem.getIconWidth() == -1) {
            System.out.println("Ícone não encontrado");
        } else {
            // Redimensionar ícone se for necessário
            Image image = imagem.getImage();
            Image scaledImage = image.getScaledInstance(45, 45, Image.SCALE_DEFAULT);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            String messagem = "<html><h1>Bem-vindo!</h1><p>Estamos felizes em tê-lo conosco.</p></html>";
            JLabel titulo = new JLabel(messagem);
            JOptionPane.showMessageDialog(null, titulo, "Bem-vindo!", JOptionPane.INFORMATION_MESSAGE, scaledIcon);
        }
    }
    public void personalizacaoJMenu(JMenu item){
        item.setBackground(Color.white);
        item.setFont(fontePadrao);
        item.setBorder(bordaItemMenu);
        item.setForeground(Color.white);
    }
    public void personalizacaoJMenuItem(JMenuItem item){
        item.setForeground(Color.black);
        item.setFont(fonteItem);
  
    }

    public void Menu() {
        // Ajustando a aparência do menu superior
        menuSuperior.setBackground(corVerde);
        menuSuperior.setPreferredSize(new Dimension(1728, 90));
        menuSuperior.setBorderPainted(false);
        personalizacaoJMenu(Cadastro);
        personalizacaoJMenu(Relatorios);
        personalizacaoJMenu(Produtos);
        personalizacaoJMenu(Vendas);
        personalizacaoJMenu(Notificacoes);
        personalizacaoJMenu(Configuracoes);
        personalizacaoJMenu(SuaConta);
        personalizacaoJMenuItem(cadastroUsuario);
        personalizacaoJMenuItem(cadastroProdutos);
        personalizacaoJMenuItem(cadastroCategorias);
        personalizacaoJMenuItem(editarUsuario);
        personalizacaoJMenuItem(PesquisarEEditar);
        personalizacaoJMenuItem(editarCategorias);
        personalizacaoJMenuItem(sair);
        personalizacaoJMenuItem(seusDados);
        personalizacaoJMenuItem(ItemVendas);


        // Adicionando os itens ao menu "Cadastro"
        Cadastro.add(cadastroUsuario);
        Cadastro.add(cadastroProdutos);
        Cadastro.add(cadastroCategorias);

        Produtos.add(editarUsuario);
        Produtos.add(PesquisarEEditar);
        Produtos.add(editarCategorias);

        SuaConta.add(seusDados);
        SuaConta.add(sair);

        Vendas.add(ItemVendas);

        // Adicionando os menus à barra de menu
        menuSuperior.add(Cadastro);
        menuSuperior.add(Relatorios);
        menuSuperior.add(Produtos);
        menuSuperior.add(Vendas);
        menuSuperior.add(Notificacoes);
        menuSuperior.add(Configuracoes);
        menuSuperior.add(SuaConta);

        // Adicionando a barra de menus ao JFrame
        setJMenuBar(menuSuperior);
    }

    public void Frame() {
        // Configuração do JFrame
        setSize(1300, 770);  // Tamanho da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());  // Usando BorderLayout
        setLocationRelativeTo(null);  // Centraliza a janela
        //painelDeFundo.setPreferredSize(new Dimension(1400, 800));
        //painelDeFundo.setBackground(corDeFundo);
        linhaDeBaixo.setPreferredSize(new Dimension(1400, 20));
        linhaDeBaixo.setBackground(corVerde);
        painelPrincipal.setPreferredSize(new Dimension(1400, 800));
        painelPrincipal.setBackground(corDeFundo);
        //add(painelDeFundo);
        add(painelPrincipal);
        add(BorderLayout.SOUTH, linhaDeBaixo);
        // Tornando a janela visível
        setVisible(true);
        setResizable(false);
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

        Produtos.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                Produtos.setForeground(Color.black);
            }

            public void mouseExited(MouseEvent e) {
                Produtos.setForeground(Color.white);
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

        Vendas.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                Vendas.setForeground(Color.black);
            }

            public void mouseExited(MouseEvent e) {
                Vendas.setForeground(Color.white);
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
        cadastroProdutos.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                CadastroProdutos produtos = new CadastroProdutos();
                CentralizarTela(produtos);
            }
        });

        cadastroCategorias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CadastroCategoria categorias = new CadastroCategoria();
                CentralizarTela(categorias);
            }
        });

// add telavendas a tela principal  
        ItemVendas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaVendas novaTelaVendas = new TelaVendas();
                CentralizarTela(novaTelaVendas);
            }
        });
        PesquisarEEditar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                PesquisaProdutos pp = new PesquisaProdutos();
                CentralizarTela(pp);
            }
            
        });
    }

    public void CentralizarTela(JInternalFrame tela) {
        int x = (painelPrincipal.getWidth() - tela.getWidth()) / 2;
        int y = (painelPrincipal.getHeight() - tela.getHeight()) / 2;
        tela.setLocation(x, y);
        painelPrincipal.add(tela);
        tela.setVisible(true);
    }

    public static void main(String[] args) {
        new TelaInicial();
    }
}
