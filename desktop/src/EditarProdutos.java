import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.ImageIcon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Kaua33500476
 */
public class EditarProdutos extends javax.swing.JInternalFrame {

    int contagem = 0;
    int repeticao = 0;
    int numeroLinhas = 0;
    int numeroImagens = 0;
    int numeroImagensBanco = 0;
    JButton confirmar = new JButton("Confirmar");
    File arquivo;
    String enderecoImagemBanco1;
    String enderecoImagemBanco2;
    String enderecoNovo1;
    String enderecoNovo2;
    String[] enderecosImagens = new String[2];
    String Subcategorias;
    EscolhaDeSubcategoria janelaSubcategorias;
    EscolhaDeCategoria janelaCategoria;
    String id_produto;
    String Produto;
    String Preco;
    String Descricao;
    String Marca;
    String Estoque;
    String Categoria;
    String Subcategoria;
    private JPopupMenu sugestoesProdutos = new JPopupMenu();
    List<String> produtos;
    Font fonteItem = new Font("Arial", Font.PLAIN, 15);
         private boolean atualizandoMascara = false;


    public EditarProdutos() {
        initComponents();
        Inicio();
        nomesProdutos();

        seta.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                slide.start();
            }

        });
        imagem1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Usar lógica encapsulada para validar e atualizar enderecoImagem1
                atualizarEnderecoImagem1();
                enderecoNovo1 = imagem1.getText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Se imagem1 for apagada, atualizar enderecoImagem1 e numeroImagens
                if (imagem1.getText().isBlank()) {
                    numeroImagens = imagem2.getText().isBlank() ? 0 : 1;
                    System.out.println("Imagem1 removida. Número de imagens: " + numeroImagens);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Não utilizado, pois esse evento é voltado para atributos de estilo
            }

            // Método anterior ajustado para validar e atualizar enderecoImagem1
            private void atualizarEnderecoImagem1() {
                File arquivoImagem = new File("imagens/produtos/" + imagem1.getText());
                if (arquivoImagem.exists() && !arquivoImagem.isDirectory()) {
                    enderecoNovo1 = imagem1.getText();
                    System.out.println("imagem1 em enderecoImagem1: " + enderecoNovo1);
                    numeroImagens = imagem2.getText().isBlank() ? 1 : 2; // Atualiza o número de imagens
                    System.out.println("Número de imagens atualizado: " + numeroImagens);
                } else {
                    new CadastroProdutos().Avisos("imagens/sinal-de-aviso.png", "Imagem não encontrada");
                }
            }
        });

        imagem2.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Usar lógica encapsulada para validar e atualizar enderecoImagem2
                atualizarEnderecoImagem2();
                enderecoNovo2 = imagem2.getText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Se imagem2 for apagada, atualizar enderecoImagem2 e numeroImagens
                if (imagem2.getText().isBlank()) {
                    enderecoNovo2 = ""; // Substitui null por ""
                    numeroImagens = imagem1.getText().isBlank() ? 0 : 1;
                    System.out.println("Imagem2 removida. Número de imagens: " + numeroImagens);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Não utilizado, pois esse evento é voltado para atributos de estilo
            }

            // Método anterior ajustado para validar e atualizar enderecoImagem2
            private void atualizarEnderecoImagem2() {
                File arquivoImagem = new File("imagens/produtos/" + imagem2.getText());
                if (arquivoImagem.exists() && !arquivoImagem.isDirectory()) {
                    enderecoNovo2 = imagem2.getText();
                    System.out.println("imagem2 em enderecoImagem2: " + enderecoNovo2);
                    numeroImagens = imagem1.getText().isBlank() ? 1 : 2; // Atualiza o número de imagens
                    System.out.println("Número de imagens atualizado: " + numeroImagens);
                } else {
                    new CadastroProdutos().Avisos("imagens/sinal-de-aviso.png", "Imagem não encontrada");
                }
            }
        });

    }

    public void nomesProdutos() {
        try (Connection con = Conexao.conexaoBanco()) {
            PreparedStatement stmt = con.prepareStatement("SELECT nome_produto FROM produto");
            ResultSet rs = stmt.executeQuery();
            produtos = new ArrayList<>();
            while (rs.next()) {
                produtos.add(rs.getString("nome_produto"));
            }
            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            System.out.println("Sem acesso aos nomes dos produtos");
        }
    }

    public void Inicio() {
        nomeProduto.setEnabled(false);
        preco.setEnabled(false);
        descricao.setEnabled(false);
        marca.setEnabled(false);
        estoque.setEnabled(false);
        categoria.setEnabled(false);
        subcategoria.setEnabled(false);
        Imagens.setVisible(false);
        imagem1.setVisible(false);
        imagem2.setVisible(false);
        deletar1.setVisible(false);
        deletar2.setVisible(false);
        btConfirmar.setVisible(false);
        sem_imagem.setIcon(sem_imagem());
        seta.setIcon(new ImageIcon("imagens/seta-direita.png"));
        applyTextAndNumberFilter(preco);
        applyNumberOnlyMask(estoque);
        applyMoneyMask(preco);
        pesquisa.setIcon(new ImageIcon("imagens/lupa.png"));
        btSelecionarImagens.setEnabled(false);
        btTrocarSubcategoria.setVisible(false);
        btTrocarCategoria.setVisible(false);
        btSelecionarImagens.setVisible(false);
        seta.setVisible(false);
    }

    Timer slide = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Atribuindo valores aos endereços de imagens
            System.out.println(enderecoNovo1 + enderecoNovo2);
            enderecosImagens[0] = enderecoNovo1;
            enderecosImagens[1] = enderecoNovo2;

            // Lógica para alternar as imagens
            contagem = (contagem + 1) % enderecosImagens.length;
            ImageIcon proximaImagem = new ImageIcon("imagens/produtos/" + enderecosImagens[contagem]);

            if (proximaImagem.getIconWidth() == -1) {
                System.out.println("Imagem não encontrada");
                slide.stop();
            } else {
                sem_imagem.setIcon(redimensionamentoDeImagem(proximaImagem, 250, 216));
                slide.stop();
            }

        }
    });

    public EditarProdutos(String Subcategoria) {
        this.Subcategorias = janelaSubcategorias.getSubcategoria();
        System.out.println(Subcategoria);
    }

    public String puxarSubcategoria(String subcategoria) {
        try {
            Connection con = Conexao.conexaoBanco();
            String sql = "SELECT id_subcat FROM subcategorias WHERE subcategoria = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, subcategoria);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Subcategorias = rs.getString("id_subcat");
            }

            stmt.close();
            rs.close();
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar categorias: " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            e.printStackTrace();
        }
        return Subcategorias;
    }

    public ImageIcon sem_imagem() {
        ImageIcon imagem = new ImageIcon("imagens/sem_imagem.jpg");
        Image redimensionar = imagem.getImage();
        Image redimensionar2 = redimensionar.getScaledInstance(250, 216, Image.SCALE_SMOOTH);
        ImageIcon imagemRedimensionada = new ImageIcon(redimensionar2);
        return imagemRedimensionada;
    }

    public ImageIcon redimensionamentoDeImagem(File arquivo) {
        ImageIcon imagem = new ImageIcon(arquivo.getPath());
        Image pegarImagem = imagem.getImage();
        Image redimensionando = pegarImagem.getScaledInstance(250, 216, Image.SCALE_SMOOTH);
        ImageIcon imagemRedimensionada = new ImageIcon(redimensionando);
        return imagemRedimensionada;
    }

    public ImageIcon redimensionamentoDeImagem(ImageIcon imagem, int largura, int altura) {
        Image pegarImagem = imagem.getImage();
        Image redimensionando = pegarImagem.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        ImageIcon imagemRedimensionada = new ImageIcon(redimensionando);
        return imagemRedimensionada;
    }

    public void applyTextAndNumberFilter(JTextField textField) {
        // Define a formatter to validate text and number input
        AbstractDocument doc = (AbstractDocument) textField.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("[a-zA-Z0-9\\s]*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[a-zA-Z0-9\\s]*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    public void applyNumberOnlyMask(JTextField textField) {
        AbstractDocument doc = (AbstractDocument) textField.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("[0-9]*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[0-9]*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                super.remove(fb, offset, length);
            }
        });
    }

    public void applyMoneyMask(JTextField textField) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

        // Define a formatter to format currency input
        AbstractDocument doc = (AbstractDocument) textField.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("[0-9.,]*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[0-9.,]*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        // Add a key listener to update the text field format as the user types
        preco.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    String text = textField.getText();
                    if (!text.isEmpty()) {
                        textField.setText(currencyFormat.format(Double.parseDouble(text.replaceAll("[^0-9]", ""))));
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    private void atualizarMascara() {
    if (atualizandoMascara) return; // Evita loops
    atualizandoMascara = true;
    SwingUtilities.invokeLater(() -> {
        String texto = nomeProduto.getText();
        nomeProduto.setText(texto.replaceAll("[^a-zA-Z0-9áéíóúâêîôûãõçÁÉÍÓÚÂÊÎÔÛÃÕÇñÑ~\\s]", ""));
        atualizandoMascara = false;
    });
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sem_imagem = new javax.swing.JLabel();
        btSelecionarImagens = new javax.swing.JButton();
        pesquisar = new javax.swing.JTextField();
        pesquisa = new javax.swing.JLabel();
        codigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        nomeProduto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        preco = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descricao = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        marca = new javax.swing.JTextField();
        estoque = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btCancelar1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        categoria = new javax.swing.JTextField();
        deletar1 = new javax.swing.JLabel();
        deletar2 = new javax.swing.JLabel();
        subcategoria = new javax.swing.JTextField();
        Imagens = new javax.swing.JLabel();
        imagem1 = new javax.swing.JTextField();
        imagem2 = new javax.swing.JTextField();
        btAlterar = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        btConfirmar = new javax.swing.JButton();
        btTrocarSubcategoria = new javax.swing.JButton();
        btTrocarCategoria = new javax.swing.JButton();
        seta = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Produtos");

        sem_imagem.setBackground(new java.awt.Color(255, 255, 255));

        btSelecionarImagens.setBackground(new java.awt.Color(102, 102, 255));
        btSelecionarImagens.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btSelecionarImagens.setForeground(new java.awt.Color(255, 255, 255));
        btSelecionarImagens.setText("Selecionar imagens");
        btSelecionarImagens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btSelecionarImagensMouseClicked(evt);
            }
        });
        btSelecionarImagens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSelecionarImagensActionPerformed(evt);
            }
        });

        pesquisar.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        pesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pesquisarKeyReleased(evt);
            }
        });

        pesquisa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pesquisaMouseClicked(evt);
            }
        });

        codigo.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                codigoKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 0, 21)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Código");

        jLabel11.setFont(new java.awt.Font("Arial", 0, 21)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Produto");

        nomeProduto.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        nomeProduto.setDisabledTextColor(new java.awt.Color(51, 51, 51));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 21)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Preço:");

        preco.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        preco.setDisabledTextColor(new java.awt.Color(51, 51, 51));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 21)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Descrição:");

        descricao.setColumns(20);
        descricao.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        descricao.setRows(5);
        descricao.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jScrollPane1.setViewportView(descricao);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 21)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Marca:");

        marca.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        marca.setDisabledTextColor(new java.awt.Color(51, 51, 51));

        estoque.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        estoque.setDisabledTextColor(new java.awt.Color(51, 51, 51));

        jLabel7.setFont(new java.awt.Font("Arial", 0, 21)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Estoque:");

        btCancelar1.setBackground(new java.awt.Color(204, 204, 255));
        btCancelar1.setFont(new java.awt.Font("Arial", 0, 21)); // NOI18N
        btCancelar1.setForeground(new java.awt.Color(51, 51, 51));
        btCancelar1.setText("Últimas vendas");
        btCancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelar1ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Arial", 0, 21)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Categoria:");

        categoria.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        categoria.setDisabledTextColor(new java.awt.Color(51, 51, 51));

        deletar1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        deletar1.setForeground(new java.awt.Color(0, 0, 255));
        deletar1.setText("Deletar");
        deletar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deletar1MouseClicked(evt);
            }
        });

        deletar2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        deletar2.setForeground(new java.awt.Color(0, 0, 255));
        deletar2.setText("Deletar");
        deletar2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deletar2MouseClicked(evt);
            }
        });

        subcategoria.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        subcategoria.setDisabledTextColor(new java.awt.Color(51, 51, 51));

        Imagens.setFont(new java.awt.Font("Arial", 0, 21)); // NOI18N
        Imagens.setForeground(new java.awt.Color(0, 0, 0));
        Imagens.setText("Imagens");

        imagem1.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        imagem1.setDisabledTextColor(new java.awt.Color(51, 51, 51));

        imagem2.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        imagem2.setDisabledTextColor(new java.awt.Color(51, 51, 51));

        btAlterar.setBackground(new java.awt.Color(50, 205, 50));
        btAlterar.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        btAlterar.setForeground(new java.awt.Color(255, 255, 255));
        btAlterar.setText("Alterar");
        btAlterar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btAlterarMouseClicked(evt);
            }
        });

        btCancelar.setBackground(new java.awt.Color(169, 169, 169));
        btCancelar.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        btCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btCancelar.setText("Cancelar");
        btCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarActionPerformed(evt);
            }
        });

        btConfirmar.setBackground(new java.awt.Color(50, 205, 50));
        btConfirmar.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        btConfirmar.setForeground(new java.awt.Color(255, 255, 255));
        btConfirmar.setText("Confirmar");
        btConfirmar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btConfirmarMouseClicked(evt);
            }
        });

        btTrocarSubcategoria.setBackground(new java.awt.Color(255, 102, 51));
        btTrocarSubcategoria.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        btTrocarSubcategoria.setForeground(new java.awt.Color(255, 255, 255));
        btTrocarSubcategoria.setText("Trocar");
        btTrocarSubcategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTrocarSubcategoriaActionPerformed(evt);
            }
        });

        btTrocarCategoria.setBackground(new java.awt.Color(255, 102, 51));
        btTrocarCategoria.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        btTrocarCategoria.setForeground(new java.awt.Color(255, 255, 255));
        btTrocarCategoria.setText("Trocar");
        btTrocarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTrocarCategoriaActionPerformed(evt);
            }
        });

        seta.setPreferredSize(new java.awt.Dimension(24, 24));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(sem_imagem, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                        .addComponent(btSelecionarImagens, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(seta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Imagens, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(666, 666, 666)
                        .addComponent(deletar2)
                        .addContainerGap(42, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(marca)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(estoque, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btCancelar1))
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btConfirmar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btAlterar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btCancelar))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btTrocarCategoria)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(imagem1, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(deletar1))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(subcategoria)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(btTrocarSubcategoria))
                                    .addComponent(imagem2)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(pesquisar)
                                        .addGap(10, 10, 10)
                                        .addComponent(pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(nomeProduto)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2)
                                        .addComponent(preco, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(18, 18, 18))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sem_imagem, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(seta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btSelecionarImagens)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                            .addComponent(pesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(preco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(nomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(31, 31, 31)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(estoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(marca)
                            .addComponent(btCancelar1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(categoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(subcategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btTrocarSubcategoria)
                            .addComponent(btTrocarCategoria))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Imagens)
                            .addComponent(imagem1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(imagem2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(deletar1)
                            .addComponent(deletar2))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btCancelar)
                            .addComponent(btAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btConfirmar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(24, 24, 24))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void SelecionarImagens() {
        if (contagem == 0) {
            new CadastroProdutos().Avisos("imagens/sinal-de-aviso.png", "Escolha imagens que possuam largura acima de 250px e altura de 216px");
            contagem++;
        }

        JFileChooser selecionar = new JFileChooser();
        // Caminho completo para o diretório na Área de Trabalho
        selecionar.setCurrentDirectory(new File("imagens"));//Define o diretório inicial que será exibido quando o diálogo for aberto.

        selecionar.setDialogTitle("Escolha a imagem do produto"); //Define o título da caixa de diálogo.
        selecionar.setFileSelectionMode(JFileChooser.FILES_ONLY); //Define se o usuário pode selecionar arquivos, diretórios ou ambos.
        selecionar.setMultiSelectionEnabled(false); // Permite selecionar vários arquivos
        selecionar.setApproveButtonText("Selecionar"); //Define o texto do botão OPEN. Mais usado quando o DialogType é CUSTOM_DIALOG
        selecionar.setAcceptAllFileFilterUsed(false); //Define se terá a opção de Aceitar Todos Os Arquivos.
        selecionar.setDialogType(JFileChooser.OPEN_DIALOG); //Define o tipo de processo que será: normal,salvar ou customizado.

        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagens", "jpg", "png", "jpge"); //Permite definir filtros para limitar os tipos de arquivos que podem ser selecionados.
        selecionar.setFileFilter(filtro); //Apenas passar o filtro.

        int retorno = selecionar.showOpenDialog(this);

        if (retorno == JFileChooser.APPROVE_OPTION) {
            arquivo = selecionar.getSelectedFile(); //Pega o endereço do arquivo, logo é possível manipular.
            String nomeArquivo = arquivo.getName();
            if (imagem1.getText().isEmpty() || imagem1.getText() == null) {
                imagem1.setText(nomeArquivo);

            } else {
                imagem2.setText(nomeArquivo);

            }
            sem_imagem.setIcon(redimensionamentoDeImagem(arquivo));
        }
        contagem = 0;
    }
    private void btSelecionarImagensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btSelecionarImagensMouseClicked
        SelecionarImagens();

    }//GEN-LAST:event_btSelecionarImagensMouseClicked

    private void codigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try (Connection con = Conexao.conexaoBanco()) {

                // Consulta principal
                String query = "SELECT * FROM vw_produto_detalhado WHERE id_produto = ?";
                try (PreparedStatement stmt = con.prepareStatement(query)) {
                    stmt.setString(1, codigo.getText());
                    try (ResultSet rs = stmt.executeQuery()) {
                        // Verifica se há resultados
                        if (rs.next()) {
                            id_produto = rs.getString("id_produto");
                            Produto = rs.getString("nome_produto");
                            Preco = rs.getString("preco");
                            Descricao = rs.getString("descricao");
                            Marca = rs.getString("marca");
                            Estoque = rs.getString("estoque");
                            Categoria = rs.getString("categoria");
                            Subcategoria = rs.getString("subcategoria");
                            enderecoImagemBanco1 = rs.getString("endereco");
                            enderecoImagemBanco2 = rs.getString("endereco2");

                            // Atualiza campos na interface
                            codigo.setText(id_produto);
                            nomeProduto.setText(Produto);
                            preco.setText(Preco);
                            descricao.setText(Descricao);
                            marca.setText(Marca);
                            estoque.setText(Estoque);
                            categoria.setText(Categoria);
                            subcategoria.setText(Subcategoria);
                            imagem1.setText(enderecoImagemBanco1);
                            imagem2.setText(enderecoImagemBanco2);

                            // Exibe a primeira imagem redimensionada
                            if (enderecoImagemBanco1 != null) {
                                sem_imagem.setIcon(redimensionamentoDeImagem(
                                        new ImageIcon("imagens/produtos/" + enderecoImagemBanco1), 245, 270));
                            }

                        } else {
                            // Caso nenhum produto seja encontrado
                            new CadastroProdutos().Avisos("imagens/erro.png", "Produto não encontrado");
                        }
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage());
                System.out.println("ERRO: " + e);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
                System.out.println("ERRO: " + e);
            }
        }
    }//GEN-LAST:event_codigoKeyReleased

    private void btCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btCancelar1ActionPerformed

    private void deletar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deletar1MouseClicked
        // Limpa o conteúdo de imagem1
        imagem1.setText("");

        if (!imagem2.getText().isBlank()) {
            // Se imagem2 tiver valor, mova para imagem1
            imagem1.setText(imagem2.getText());
            imagem2.setText(""); // Limpa imagem2
            numeroImagens = 1; // Atualiza o número de imagens
            System.out.println("Imagem1 foi deletada. Imagem2 movida para Imagem1.");
        } else {
            // Caso ambas estejam vazias
            numeroImagens = 0;
            System.out.println("Imagem1 deletada. Nenhuma imagem restante.");
        }
    }//GEN-LAST:event_deletar1MouseClicked

    private void deletar2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deletar2MouseClicked
        // Limpa o conteúdo de imagem2
        imagem2.setText("");
        // Atualiza numeroImagens com base no estado de imagem1
        if (!imagem1.getText().isBlank()) {
            numeroImagens = 1; // Apenas imagem1 restante
            System.out.println("Imagem2 deletada. Apenas Imagem1 permanece.");
        } else {
            numeroImagens = 0; // Nenhuma imagem restante
            System.out.println("Imagem2 deletada. Nenhuma imagem restante.");
        }
    }//GEN-LAST:event_deletar2MouseClicked
    private int camposVazios() {
        String[] valoresFormularios = new String[]{
            nomeProduto.getText(), preco.getText(), descricao.getText(),
            marca.getText(), estoque.getText(), subcategoria.getText(),};

        int camposVazios = 0;
        for (int x = 0; x < valoresFormularios.length; x++) {
            if (valoresFormularios[x].isBlank()) {
                camposVazios++;
            }
        }

        if (camposVazios > 0) {
            new CadastroProdutos().Avisos("imagens/sinal-de-aviso.png", "Campos não preenchidos");
            return 1;
        }

        return 0;
    }

    public void camposImagens() {
        if (imagem1.getText().isBlank()) {
            new CadastroProdutos().Avisos("imagens/sinal-de-aviso.png", "Selecione uma imagem");
            return;
        }

        if (imagem2.getText().isBlank()) {
            int resposta = JOptionPane.showConfirmDialog(
                    null,
                    "Deseja somente uma imagem do produto?",
                    "Imagem",
                    JOptionPane.YES_NO_OPTION
            );
            System.out.println("ERRO 1");
            if (resposta == JOptionPane.NO_OPTION) {
                if (repeticao == 0) {
                    new CadastroProdutos().Avisos("imagens/sinal-de-aviso.png", "Escolha imagens que possuam largura acima de 250px e altura de 216px");
                    repeticao++;
                }
                JFileChooser selecionar = new JFileChooser();
                // Caminho completo para o diretório na Área de Trabalho
                selecionar.setCurrentDirectory(new File("imagens"));//Define o diretório inicial que será exibido quando o diálogo for aberto.

                selecionar.setDialogTitle("Escolha a imagem do produto"); //Define o título da caixa de diálogo.
                selecionar.setFileSelectionMode(JFileChooser.FILES_ONLY); //Define se o usuário pode selecionar arquivos, diretórios ou ambos.
                selecionar.setMultiSelectionEnabled(false); // Permite selecionar vários arquivos
                selecionar.setApproveButtonText("Selecionar"); //Define o texto do botão OPEN. Mais usado quando o DialogType é CUSTOM_DIALOG
                selecionar.setAcceptAllFileFilterUsed(false); //Define se terá a opção de Aceitar Todos Os Arquivos.
                selecionar.setDialogType(JFileChooser.OPEN_DIALOG); //Define o tipo de processo que será: normal,salvar ou customizado.

                FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagens", "jpg", "png", "jpge"); //Permite definir filtros para limitar os tipos de arquivos que podem ser selecionados.
                selecionar.setFileFilter(filtro); //Apenas passar o filtro.

                int retorno = selecionar.showOpenDialog(this);

                if (retorno == JFileChooser.APPROVE_OPTION) {
                    arquivo = selecionar.getSelectedFile(); //Pega o endereço do arquivo, logo é possível manipular.
                    String nomeArquivo = arquivo.getName();
                    if (imagem1.getText().isEmpty() || imagem1.getText() == null) {
                        imagem1.setText(nomeArquivo);

                    } else {
                        imagem2.setText(nomeArquivo);

                    }
                    sem_imagem.setIcon(redimensionamentoDeImagem(arquivo));
                }
                return;
            } else {
                System.out.println("ERRO 2");
                imagem2.setText("");
            }
        }
        System.out.println("ERRO 3");

    }

    private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
        codigo.setText("");
        pesquisar.setText("");
        nomeProduto.setText("");
        descricao.setText("");
        preco.setText("");
        categoria.setText("");
        marca.setText("");
        subcategoria.setText("");
        estoque.setText("");
        imagem1.setText("");
        imagem2.setText("");
        Inicio();
    }//GEN-LAST:event_btCancelarActionPerformed

    private void btAlterarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btAlterarMouseClicked
        if (codigo.getText().isBlank()) {
            new CadastroProdutos().Avisos("imagens/sinal-de-aviso.png", "Nenhum produto pesquisado");
            return;
        } else {
            int resposta = JOptionPane.showConfirmDialog(null, "Deseja alterar dados deste produto?", "Alterar?", JOptionPane.OK_CANCEL_OPTION);

            if (resposta == JOptionPane.OK_OPTION) {
                Imagens.setVisible(true);
                imagem1.setVisible(true);
                imagem1.setEditable(false);
                imagem2.setEditable(false);
                imagem2.setVisible(true);
                deletar1.setVisible(true);
                deletar2.setVisible(true);
                btConfirmar.setLocation(btAlterar.getWidth(), btAlterar.getHeight());
                btAlterar.setVisible(false);
                btConfirmar.setVisible(true);
                nomeProduto.setEnabled(true);
                preco.setEnabled(true);
                descricao.setEnabled(true);
                marca.setEnabled(true);
                estoque.setEnabled(true);
                categoria.setEnabled(false);
                subcategoria.setEnabled(true);
                subcategoria.setEditable(true);
                btSelecionarImagens.setEnabled(true);
                btTrocarSubcategoria.setVisible(true);
                btTrocarCategoria.setVisible(true);
                btSelecionarImagens.setVisible(true);
                seta.setVisible(true);
            }
        }

    }//GEN-LAST:event_btAlterarMouseClicked

    private void btConfirmarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btConfirmarMouseClicked
        System.out.println("COMECOU");
        int camposVazios = camposVazios();
        if (camposVazios == 1) {
            return;
        }

        System.out.println("CAMPOS VAZIOS OK");
        camposImagens();

        System.out.println("IMAGENS OK");
        boolean camposDiferentes = Alteracoes();
        if (camposDiferentes == false) {
            new CadastroProdutos().Avisos("imagens/sinal-de-aviso.png", "Alterações nulas");
            return;
        };
        System.out.println("CAMPOS DIFERENTES OK");
        boolean existeImagens = existeImagem();
        System.out.println("IMAGENS OK");
        if (existeImagens == true) {
            try (Connection con = Conexao.conexaoBanco()) {
                PreparedStatement stmt = con.prepareStatement("UPDATE produto "
                        + "SET nome_produto = ?, descricao = ?, preco = ?, marca = ?, estoque = ?, "
                        + "id_subcat = (SELECT id_subcat FROM subcategorias WHERE subcategoria = ?) WHERE id_produto = ?");
                stmt.setString(1, nomeProduto.getText());
                stmt.setString(2, descricao.getText());
                stmt.setString(3, preco.getText());
                stmt.setString(4, marca.getText());
                stmt.setString(5, estoque.getText());
                stmt.setString(6, subcategoria.getText());
                stmt.setString(7, codigo.getText());
                stmt.execute();
                PreparedStatement stmt2;
                System.out.println(numeroImagens);

                stmt2 = con.prepareStatement("UPDATE imagens SET endereco = ?,endereco2 = ? WHERE id_produto = ?");
                stmt2.setString(1, enderecoNovo1);
                stmt2.setString(2, enderecoNovo2);
                stmt2.setString(3, codigo.getText());
                stmt2.execute();

                //stmt2.close();
                stmt.close();
                con.close();
                System.out.println("FIM CONEXÃO");
                new CadastroProdutos().Avisos("imagens/confirmacao.png", "Produto alterado");
                Apagar();

            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

        }
    }//GEN-LAST:event_btConfirmarMouseClicked

    private void btTrocarSubcategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTrocarSubcategoriaActionPerformed
        pegarSubcategoria();
    }//GEN-LAST:event_btTrocarSubcategoriaActionPerformed

    private void btTrocarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTrocarCategoriaActionPerformed
        pegarCategoria();
    }//GEN-LAST:event_btTrocarCategoriaActionPerformed

    private void btSelecionarImagensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSelecionarImagensActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btSelecionarImagensActionPerformed

    private void pesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pesquisarKeyReleased
      String pesquisa = pesquisar.getText().toLowerCase();
        sugestoesProdutos.setVisible(false);
        
        if (!pesquisa.isEmpty()) {
            // Filtra a lista usando Streams
            List<String> filtro = produtos.stream()
                    .filter(produto -> produto.toLowerCase().contains(pesquisa))
                    .collect(Collectors.toList());

            if (!filtro.isEmpty()) {
                sugestoesProdutos.removeAll(); // Limpa itens antigos
                for (String p : filtro) {
                    JMenuItem item = new JMenuItem(p);
                    item.setFont(fonteItem);

                    item.addActionListener(e -> {
                        pesquisar.setText(p);
                        sugestoesProdutos.setVisible(false);
                    });

                    sugestoesProdutos.add(item);
                }
                sugestoesProdutos.show(pesquisar, 0, pesquisar.getHeight());
            }
        }

    }//GEN-LAST:event_pesquisarKeyReleased

    private void pesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pesquisaMouseClicked

        try (Connection con = Conexao.conexaoBanco()) {

            // Consulta principal
            String query = "SELECT * FROM vw_produto_detalhado WHERE nome_produto = ?";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setString(1, pesquisar.getText());
                try (ResultSet rs = stmt.executeQuery()) {
                    // Verifica se há resultados
                    if (rs.next()) {
                        id_produto = rs.getString("id_produto");
                        Produto = rs.getString("nome_produto");
                        Preco = rs.getString("preco");
                        Descricao = rs.getString("descricao");
                        Marca = rs.getString("marca");
                        Estoque = rs.getString("estoque");
                        Categoria = rs.getString("categoria");
                        Subcategoria = rs.getString("subcategoria");
                        enderecoImagemBanco1 = rs.getString("endereco");
                        enderecoImagemBanco2 = rs.getString("endereco2");

                        // Atualiza campos na interface
                        codigo.setText(id_produto);
                        nomeProduto.setText(Produto);
                        preco.setText(Preco);
                        descricao.setText(Descricao);
                        marca.setText(Marca);
                        estoque.setText(Estoque);
                        categoria.setText(Categoria);
                        subcategoria.setText(Subcategoria);
                        imagem1.setText(enderecoImagemBanco1);
                        imagem2.setText(enderecoImagemBanco2);

                        // Exibe a primeira imagem redimensionada
                        if (enderecoImagemBanco1 != null) {
                            sem_imagem.setIcon(redimensionamentoDeImagem(
                                    new ImageIcon("imagens/produtos/" + enderecoImagemBanco1), 245, 270));
                        }

                    } else {
                        // Caso nenhum produto seja encontrado
                        new CadastroProdutos().Avisos("imagens/erro.png", "Produto não encontrado");
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage());
            System.out.println("ERRO: " + e);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            System.out.println("ERRO: " + e);
        }

    }//GEN-LAST:event_pesquisaMouseClicked
    private void pegarCategoria() {
        try {
            janelaCategoria = new EscolhaDeCategoria();
            janelaCategoria.setLocation(categoria.getX(), categoria.getY());

            janelaCategoria.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    String CategoriaSelecionada = janelaCategoria.getCategoria();
                    if (CategoriaSelecionada == null || CategoriaSelecionada.isBlank()) {
                        System.out.println("Nenhuma subcategoria foi selecionada.");
                    } else {
                        System.out.println("Subcategoria selecionada: " + CategoriaSelecionada);
                        categoria.setText(CategoriaSelecionada);
                        subcategoria.setText("");
                    }
                }
            });
            janelaCategoria.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace(); // Garante que erros sejam registrados
        }
    }

    private void pegarSubcategoria() {

        if (!categoria.getText().isBlank()) {
            String id_categoria = "";

            try (Connection con = Conexao.conexaoBanco(); PreparedStatement stmt = con.prepareStatement("SELECT id_categoria FROM categoria WHERE categoria = ?")) {

                stmt.setString(1, categoria.getText());

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        id_categoria = rs.getString("id_categoria"); // Obtém o ID corretamente
                    }
                }

                // Se a categoria não for encontrada, exibe a mensagem e interrompe a execução
                if (id_categoria.isEmpty()) {
                    System.out.println("Nenhuma categoria encontrada para: " + categoria.getText());
                    return; // Sai do método para evitar continuar com a criação da janelaSubcategorias
                }

                // Criação da janelaSubcategorias somente se a categoria foi encontrada
                try {
                    janelaSubcategorias = new EscolhaDeSubcategoria(id_categoria);
                    janelaSubcategorias.setLocation(subcategoria.getX(), subcategoria.getY());

                    janelaSubcategorias.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent e) {
                            String subcategoriaSelecionada = janelaSubcategorias.getSubcategoria();
                            if (subcategoriaSelecionada == null || subcategoriaSelecionada.isBlank()) {
                                System.out.println("Nenhuma subcategoria foi selecionada.");
                            } else {
                                System.out.println("Subcategoria selecionada: " + subcategoriaSelecionada);
                                subcategoria.setText(subcategoriaSelecionada); // Agora o valor será atualizado corretamente
                            }
                        }
                    });
                    janelaSubcategorias.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace(); // Garante que erros sejam registrados
                }

            } catch (Exception e) {
                e.printStackTrace(); // Captura exceções na conexão ou consulta
            }
        }
    }

    public boolean Alteracoes() {
        boolean diferente = false;
        String[] valoresFormularios = new String[]{
            nomeProduto.getText(),
            preco.getText(),
            descricao.getText(),
            marca.getText(),
            subcategoria.getText(),
            estoque.getText(),
            enderecoImagemBanco1,
            enderecoImagemBanco2
        };

        String[] valoresPuxados = new String[]{
            Produto,
            Preco,
            Descricao,
            Marca,
            Subcategoria,
            Estoque,
            enderecoNovo1,
            enderecoNovo2
        };

        for (int x = 0; x < valoresFormularios.length; x++) {
            if (!valoresFormularios[x].equals(valoresPuxados[x])) {
                diferente = true;
                break; // Interrompe o loop quando uma diferença é encontrada
            }
        }
        return diferente;
    }

    public boolean existeImagem() {
        if (!imagem1.getText().isBlank()) {
            File arquivo = new File("imagens/produtos/" + imagem1.getText());
            if (arquivo.exists() || !arquivo.isDirectory()) {
                System.out.println("Imagem1 existe");
            }
        } else {
            new CadastroProdutos().Avisos("imagens/sinal-de-aviso.png", "Imagem não encontrada. Escolhe outra");
            SelecionarImagens();
            return false;

        }
        if (!imagem2.getText().isBlank()) {
            File arquivo = new File("imagens/produtos/" + imagem2.getText());
            if (arquivo.exists() || !arquivo.isDirectory()) {
                System.out.println("Imagem2 existe");
            }
        } else {
            return true;
        }
        return true; // Todas as imagens válidas foram encontradas
    }

    public void Apagar() {

        if (pesquisar != null) {
            pesquisar.setText("");
        }
        if (nomeProduto != null) {
            nomeProduto.setText("");
        }

        if (descricao != null) {
            descricao.setText("");
        }

        if (preco != null) {
            preco.setText("");
        }
        if (categoria != null) {
            categoria.setText("");
        }
        if (estoque != null) {
            estoque.setText("");
        }
        if (imagem1 != null) {
            imagem1.setText("");
        }
        if (imagem2 != null) {
            imagem2.setText("");
        }
        if (sem_imagem != null) {
            sem_imagem.setIcon(sem_imagem());
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Imagens;
    private javax.swing.JButton btAlterar;
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btCancelar1;
    private javax.swing.JButton btConfirmar;
    private javax.swing.JButton btSelecionarImagens;
    private javax.swing.JButton btTrocarCategoria;
    private javax.swing.JButton btTrocarSubcategoria;
    private javax.swing.JTextField categoria;
    private javax.swing.JTextField codigo;
    private javax.swing.JLabel deletar1;
    private javax.swing.JLabel deletar2;
    private javax.swing.JTextArea descricao;
    private javax.swing.JTextField estoque;
    private javax.swing.JTextField imagem1;
    private javax.swing.JTextField imagem2;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField marca;
    private javax.swing.JTextField nomeProduto;
    private javax.swing.JLabel pesquisa;
    private javax.swing.JTextField pesquisar;
    private javax.swing.JTextField preco;
    private javax.swing.JLabel sem_imagem;
    private javax.swing.JLabel seta;
    private javax.swing.JTextField subcategoria;
    // End of variables declaration//GEN-END:variables

}
