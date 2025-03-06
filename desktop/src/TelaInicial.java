
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import java.awt.*;
import java.awt.event.*;
import java.util.Properties;
import javax.swing.*;
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
    JPanel linhaDeBaixo = new JPanel();
    Color corVerde = new Color(29, 68, 53);
    Color corDeFundo = new Color(255, 242, 207);
    EmptyBorder bordaItemMenu = new EmptyBorder(0, 20, 0, 20);
    public String codigo;
    public String tipo_usuario;

    public String getCodigo() {
        return codigo;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }
    public TelaInicial(){
        
    }
    public TelaInicial(String codigo, String tipo_usuario) {
        mensagemBoaVindas();
        configurarUIManager();
        inicializarComponentes();
        this.codigo = codigo;
        this.tipo_usuario = tipo_usuario;
        
        if(codigo.isBlank() && tipo_usuario.isBlank()){
            dispose();
        }
        if(tipo_usuario.equals("2")){
            Cadastro.setEnabled(false);
            Vendas.setEnabled(false);
            Relatorios.setEnabled(false);
        }
        
    }

    private void configurarUIManager() {
        try {
            // Configurar propriedades personalizadas do JTattoo
            Properties props = new Properties();
            props.put("logoString", ""); // Remove o texto "JTattoo" (ou substitua por algo personalizado)
            McWinLookAndFeel.setCurrentTheme(props);
            // Configurando o tema do JTattoo
            UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        UIManager.put("MenuBar.background", corVerde);
        UIManager.put("MenuBar.foreground", Color.WHITE);
    }

    private void inicializarComponentes() {
        configurarMenu();
        configurarFrame();
        configurarEventos();
    }

    private void mensagemBoaVindas() {
        JOptionPane.showMessageDialog(
                null,
                "<html><h2>Bem-vindo!</h2></html>",
                "Mensagem",
                JOptionPane.OK_OPTION, new ImageIcon("imagens/notificacao.png")
        );

    }

    private void configurarMenu() {
        // Sobrescrevendo o JMenuBar para aplicar a cor de fundo
        menuSuperior = new JMenuBar() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(corVerde); // Define a cor desejada
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        menuSuperior.setPreferredSize(new Dimension(1300, 90));
        menuSuperior.setBorderPainted(false);

        // Personalizando os menus e itens
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

        // Adicionando itens aos menus
        Cadastro.add(cadastroUsuario);
        Cadastro.add(cadastroProdutos);
        Cadastro.add(cadastroCategorias);
        Produtos.add(editarUsuario);
        Produtos.add(PesquisarEEditar);
        Produtos.add(editarCategorias);
        SuaConta.add(seusDados);
        SuaConta.add(sair);
        Vendas.add(ItemVendas);

        // Adicionando menus à barra de menus
        menuSuperior.add(Cadastro);
        menuSuperior.add(Relatorios);
        menuSuperior.add(Produtos);
        menuSuperior.add(Vendas);
        menuSuperior.add(Notificacoes);
        menuSuperior.add(Configuracoes);
        menuSuperior.add(SuaConta);

        // Adicionando o menu ao JFrame
        setJMenuBar(menuSuperior);
    }

    private void personalizacaoJMenu(JMenu item) {
        item.setFont(fontePadrao);
        item.setBorder(bordaItemMenu);
        item.setForeground(Color.WHITE);
    }

    private void personalizacaoJMenuItem(JMenuItem item) {
        item.setForeground(Color.BLACK);
        item.setFont(fonteItem);
    }

    private void configurarFrame() {
        // Configuração do JFrame
        setSize(1300, 770);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        linhaDeBaixo.setPreferredSize(new Dimension(1400, 20));
        linhaDeBaixo.setBackground(corVerde);
        painelPrincipal.setBackground(corDeFundo);

        add(painelPrincipal, BorderLayout.CENTER);
        add(linhaDeBaixo, BorderLayout.SOUTH);

        setVisible(true);
        setResizable(false);
    }

    private void configurarEventos() {
        // Eventos para mouse nos menus
        adicionarEventoMouse(Cadastro);
        adicionarEventoMouse(Relatorios);
        adicionarEventoMouse(Produtos);
        adicionarEventoMouse(Vendas);
        adicionarEventoMouse(Notificacoes);
        adicionarEventoMouse(Configuracoes);
        adicionarEventoMouse(SuaConta);

        // Eventos de clique nos itens do menu
        sair.addActionListener(e -> {
            new Login();
            dispose();
        });

        cadastroProdutos.addActionListener(e -> {
            CadastroProdutos produtos = new CadastroProdutos();
            centralizarTela(produtos);
        });

        cadastroCategorias.addActionListener(e -> {
            CadastroCategoria categorias = new CadastroCategoria();
            centralizarTela(categorias);
        });

        ItemVendas.addActionListener(e -> {
            TelaVendas novaTelaVendas = new TelaVendas();
            centralizarTela(novaTelaVendas);
        });

        PesquisarEEditar.addActionListener(e -> {
            PesquisaProdutos pesquisa = new PesquisaProdutos();
            centralizarTela(pesquisa);
        });
        seusDados.addActionListener(e->{
            SuaConta conta = new SuaConta(codigo);
            centralizarTela(conta);
        });
    }

    private void adicionarEventoMouse(JMenu menu) {
        menu.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                menu.setForeground(Color.BLACK);
            }

            public void mouseExited(MouseEvent e) {
                menu.setForeground(Color.WHITE);
            }
        });
    }

    private void centralizarTela(JInternalFrame tela) {
        painelPrincipal.add(tela);
        tela.setLocation(
                (painelPrincipal.getWidth() - tela.getWidth()) / 2,
                (painelPrincipal.getHeight() - tela.getHeight()) / 2
        );
        tela.setVisible(true);
        tela.toFront();
    }
}
