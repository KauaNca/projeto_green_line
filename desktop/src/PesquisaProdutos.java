
import java.awt.Color;
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
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.util.ArrayList;

/**
 *
 * @author Kaua33500476
 */
public class PesquisaProdutos extends javax.swing.JInternalFrame {

    int contagem = 0;
    int numeroLinhas = 0;
    int numeroImagens = 0;
    String enderecoImagem1;
    String enderecoImagem2;
    File arquivo;
    ArrayList<String> enderecosImagens = new ArrayList<>();
    String Subcategorias;
    EscolhaDeSubcategoria janela;
    String id_produto;
    String Produto;
    String Preco;
    String Descricao;
    String Marca;
    String Estoque;
    String Categoria;
    String Subcategoria;

    public PesquisaProdutos() {
        initComponents();
        Inicio();

        passarImagem.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                slide.start();
            }

        });

    }

    public void Inicio() {
        nomeProduto.setEnabled(false);
        preco.setEnabled(false);
        descricao.setEnabled(false);
        marca.setEnabled(false);
        estoque.setEnabled(false);
        categoria.setEnabled(false);
        subcategoria.setEnabled(false);
        labelImagens.setVisible(false);
        imagem1.setVisible(false);
        imagem2.setVisible(false);
        deletar1.setVisible(false);
        deletar2.setVisible(false);
        btAlterar.setBackground(new Color(169, 169, 169));
        jPanel1.setBackground(Color.white);
        sem_imagem.setIcon(sem_imagem());
        passarImagem.setIcon(new ImageIcon("imagens/seta-direita.png"));
        applyTextAndNumberFilter(pesquisar);
        applyTextAndNumberFilter(preco);
        applyNumberOnlyMask(estoque);
        applyMoneyMask(preco);
        pesquisa.setIcon(new ImageIcon("imagens/lupa.png"));

    }

    Timer slide = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            enderecoImagem1 = imagem1.getText();
            enderecoImagem2 = imagem2.getText();
            enderecosImagens.add(enderecoImagem1);
            enderecosImagens.add(enderecoImagem2);
            contagem = (contagem + 1) % enderecosImagens.size();
            ImageIcon proximaImagem = new ImageIcon("imagens/produtos/" + enderecosImagens.get(contagem));
            if (proximaImagem.getIconWidth() == -1) {
                System.out.println("Imagem não encontrada");
                slide.stop();
            } else {
                sem_imagem.setIcon(redimensionamentoDeImagem(proximaImagem, 250, 216));
                slide.stop();
            }

        }
    }
    );

    public PesquisaProdutos(String Subcategoria) {
        this.Subcategorias = janela.getSubcategoria();
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

    public void cadastrarProduto() {
        try (Connection con = Conexao.conexaoBanco()) {
            String sql = "INSERT INTO produto(nome_produto,descricao,preco,marca,estoque,id_subcat)"
                    + " VALUES (?,?,?,?,?,(SELECT id_subcat FROM subcategorias WHERE subcategoria = ?))";
            PreparedStatement stmt = con.prepareStatement(sql);
            System.out.println(pesquisar.getText() + ", " + descricao.getText() + ", " + preco.getText() + ", " + categoria.getText() + ", " + estoque.getText() + ", " + Subcategorias);

            stmt.setString(1, pesquisar.getText());
            stmt.setString(2, descricao.getText());
            stmt.setString(3, preco.getText());
            stmt.setString(4, categoria.getText());
            stmt.setString(5, estoque.getText());
            stmt.setString(6, janela.getSubcategoria());
            stmt.execute();
            stmt.close();
            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Aconteceu algum erro", "Exceção", JOptionPane.INFORMATION_MESSAGE);
            e.printStackTrace();
        }
    }

    public void Avisos(String endereco, String mensagem) {
        ImageIcon imagem = new ImageIcon(endereco);
        if (imagem.getIconWidth() == -1) {
            System.out.println("Ícone não encontrado");
        } else {
            // Redimensionar ícone se for necessário
            Image image = imagem.getImage();
            Image scaledImage = image.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            String mensagemFormatada = "<html><h2>" + mensagem + "</h2></html>";
            JLabel titulo = new JLabel(mensagemFormatada);
            JOptionPane.showMessageDialog(null, titulo, "Mensagem", JOptionPane.INFORMATION_MESSAGE, scaledIcon);
        }
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelProdutos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        sem_imagem = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pesquisar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descricao = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        preco = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        estoque = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btSelecionarImagens = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        btAlterar = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        passarImagem = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        codigo = new javax.swing.JTextField();
        nomeProduto = new javax.swing.JTextField();
        pesquisa = new javax.swing.JLabel();
        marca = new javax.swing.JTextField();
        subcategoria = new javax.swing.JTextField();
        btCancelar1 = new javax.swing.JButton();
        imagem1 = new javax.swing.JTextField();
        imagem2 = new javax.swing.JTextField();
        labelImagens = new javax.swing.JLabel();
        categoria = new javax.swing.JTextField();
        deletar1 = new javax.swing.JLabel();
        deletar2 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Produtos");

        painelProdutos.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        sem_imagem.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(sem_imagem, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(sem_imagem, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel3.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel3.setText("Código");

        pesquisar.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel4.setText("Descrição:");

        descricao.setColumns(20);
        descricao.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        descricao.setRows(5);
        descricao.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jScrollPane1.setViewportView(descricao);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel5.setText("Preço:");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel6.setText("Marca:");

        preco.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        preco.setDisabledTextColor(new java.awt.Color(51, 51, 51));

        jLabel7.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel7.setText("Estoque:");

        estoque.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        estoque.setDisabledTextColor(new java.awt.Color(51, 51, 51));

        jLabel9.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel9.setText("Categoria:");

        btSelecionarImagens.setBackground(new java.awt.Color(102, 102, 255));
        btSelecionarImagens.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btSelecionarImagens.setForeground(new java.awt.Color(255, 255, 255));
        btSelecionarImagens.setText("Selecionar imagens");
        btSelecionarImagens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btSelecionarImagensMouseClicked(evt);
            }
        });

        btAlterar.setBackground(new java.awt.Color(50, 205, 50));
        btAlterar.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        btAlterar.setForeground(new java.awt.Color(255, 255, 255));
        btAlterar.setText("Alterar");
        btAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAlterarActionPerformed(evt);
            }
        });

        btCancelar.setBackground(new java.awt.Color(169, 169, 169));
        btCancelar.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        btCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btCancelar.setText("Cancelar");
        btCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel11.setText("Produto");

        codigo.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                codigoKeyReleased(evt);
            }
        });

        nomeProduto.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        nomeProduto.setDisabledTextColor(new java.awt.Color(51, 51, 51));

        marca.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        marca.setDisabledTextColor(new java.awt.Color(51, 51, 51));

        subcategoria.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        subcategoria.setDisabledTextColor(new java.awt.Color(51, 51, 51));

        btCancelar1.setBackground(new java.awt.Color(204, 204, 255));
        btCancelar1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        btCancelar1.setForeground(new java.awt.Color(51, 51, 51));
        btCancelar1.setText("Últimas vendas");
        btCancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelar1ActionPerformed(evt);
            }
        });

        imagem1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        imagem1.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        imagem1.setEnabled(false);

        imagem2.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        imagem2.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        imagem2.setEnabled(false);

        labelImagens.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        labelImagens.setText("Imagens");

        categoria.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        categoria.setDisabledTextColor(new java.awt.Color(51, 51, 51));

        deletar1.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        deletar1.setForeground(new java.awt.Color(0, 0, 255));
        deletar1.setText("Deletar");
        deletar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deletar1MouseClicked(evt);
            }
        });

        deletar2.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        deletar2.setForeground(new java.awt.Color(0, 0, 255));
        deletar2.setText("Deletar");
        deletar2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deletar2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout painelProdutosLayout = new javax.swing.GroupLayout(painelProdutos);
        painelProdutos.setLayout(painelProdutosLayout);
        painelProdutosLayout.setHorizontalGroup(
            painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelProdutosLayout.createSequentialGroup()
                .addGroup(painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelProdutosLayout.createSequentialGroup()
                        .addGap(0, 20, Short.MAX_VALUE)
                        .addGroup(painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelProdutosLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(64, 64, 64)
                                .addComponent(btSelecionarImagens))
                            .addGroup(painelProdutosLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(62, 62, 62))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelProdutosLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(passarImagem)
                        .addGap(73, 73, 73)))
                .addGroup(painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel8)
                        .addGroup(painelProdutosLayout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addGap(31, 31, 31)
                            .addComponent(marca, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(estoque, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(72, 72, 72)
                            .addComponent(btCancelar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jLabel4)
                        .addComponent(jScrollPane1)
                        .addGroup(painelProdutosLayout.createSequentialGroup()
                            .addGroup(painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelProdutosLayout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(nomeProduto))
                                .addComponent(pesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelProdutosLayout.createSequentialGroup()
                                    .addGap(9, 9, 9)
                                    .addComponent(pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(painelProdutosLayout.createSequentialGroup()
                                    .addGap(69, 69, 69)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(preco)))))
                    .addGroup(painelProdutosLayout.createSequentialGroup()
                        .addGroup(painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(painelProdutosLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(labelImagens)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(painelProdutosLayout.createSequentialGroup()
                                .addComponent(categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(subcategoria))
                            .addGroup(painelProdutosLayout.createSequentialGroup()
                                .addGroup(painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(deletar1)
                                    .addComponent(imagem1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(imagem2)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelProdutosLayout.createSequentialGroup()
                                        .addGap(0, 75, Short.MAX_VALUE)
                                        .addGroup(painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelProdutosLayout.createSequentialGroup()
                                                .addComponent(btAlterar)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btCancelar))
                                            .addComponent(deletar2, javax.swing.GroupLayout.Alignment.TRAILING))))))))
                .addGap(39, 39, 39))
        );
        painelProdutosLayout.setVerticalGroup(
            painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelProdutosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE))
            .addGroup(painelProdutosLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelProdutosLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelProdutosLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(passarImagem))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelProdutosLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btSelecionarImagens)
                                    .addComponent(jLabel10))
                                .addGap(61, 61, 61))))
                    .addGroup(painelProdutosLayout.createSequentialGroup()
                        .addGroup(painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pesquisar)
                            .addComponent(pesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(28, 28, 28)
                        .addGroup(painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(preco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(nomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelProdutosLayout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(estoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(marca))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8))
                            .addGroup(painelProdutosLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btCancelar1)))
                        .addGap(34, 34, 34)
                        .addGroup(painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(subcategoria)
                            .addComponent(categoria))
                        .addGap(33, 33, 33)))
                .addGroup(painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imagem1)
                    .addComponent(imagem2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelImagens))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deletar1)
                    .addComponent(deletar2))
                .addGap(79, 79, 79)
                .addGroup(painelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btCancelar)
                    .addComponent(btAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pesquisar.getAccessibleContext().setAccessibleParent(null);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelProdutos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deletar2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deletar2MouseClicked
        imagem2.setText("");
    }//GEN-LAST:event_deletar2MouseClicked

    private void deletar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deletar1MouseClicked
        imagem1.setText("");
    }//GEN-LAST:event_deletar1MouseClicked

    private void btCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btCancelar1ActionPerformed

    private void codigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try (Connection con = Conexao.conexaoBanco()) {
                PreparedStatement stmt1 = con.prepareStatement("SELECT COUNT(*) FROM vw_produto_detalhado WHERE id_produto = ?");
                stmt1.setString(1, codigo.getText());
                ResultSet rs1 = stmt1.executeQuery();
                numeroLinhas = 0;
                if (rs1.next()) {
                    numeroLinhas = rs1.getInt(1);
                }
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM vw_produto_detalhado WHERE id_produto = ?");
                stmt.setString(1, codigo.getText());
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    id_produto = rs.getString("id_produto");
                    Produto = rs.getString("nome_produto");
                    Preco = rs.getString("preco");
                    Descricao = rs.getString("descricao");
                    Marca = rs.getString("marca");
                    Estoque = rs.getString("estoque");
                    Categoria = rs.getString("categoria");
                    Subcategoria = rs.getString("subcategoria");

                    do {
                        enderecosImagens.add(rs.getString("endereco"));
                        contagem++;
                    } while (contagem <= numeroLinhas);
                    if (enderecosImagens.size() == 1) {
                        enderecoImagem1 = enderecosImagens.get(0);
                        numeroImagens = 1;

                    } else {

                        enderecoImagem1 = enderecosImagens.get(0);
                        enderecoImagem2 = enderecosImagens.get(1);
                        numeroImagens = 2;
                    }

                    codigo.setText(id_produto);
                    nomeProduto.setText(Produto);
                    preco.setText(Preco);
                    descricao.setText(Descricao);
                    marca.setText(Marca);
                    estoque.setText(Estoque);
                    categoria.setText(Categoria);
                    subcategoria.setText(Subcategoria);
                    sem_imagem.setIcon(new ImageIcon("imagens/produtos/" + enderecoImagem1));
                    imagem1.setText(enderecoImagem1);
                    imagem2.setText(enderecoImagem2);

                } else {
                    new CadastroProdutos().Avisos("imagens/erro.png", "Produto não encontrado");
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

    private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
        codigo.setText("");
        pesquisar.setText("");
        nomeProduto.setText(null);
        descricao.setText(null);
        preco.setText(null);
        categoria.setText(null);
        marca.setText(null);
        subcategoria.setText(null);
        estoque.setText(null);
        sem_imagem.setIcon(sem_imagem());
    }//GEN-LAST:event_btCancelarActionPerformed

    private void btAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarActionPerformed
        try {
            btAlterar.setBackground(new Color(50, 205, 50));
            labelImagens.setVisible(true);
            imagem1.setVisible(true);
            imagem2.setVisible(true);
            deletar1.setVisible(true);
            deletar2.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um erro inesperado. Tente daqui a pouco!", "Mensagem", JOptionPane.ERROR_MESSAGE);
            dispose();

        }
    }//GEN-LAST:event_btAlterarActionPerformed

    private void btSelecionarImagensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btSelecionarImagensMouseClicked

        if (contagem == 0) {
            Avisos("imagens/sinal-de-aviso.png", "Escolha imagens que possuam largura acima de 250px e altura de 216px");
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
                numeroImagens = 1;

            } else {
                imagem2.setText(nomeArquivo);
                numeroImagens = 2;
            }
            sem_imagem.setIcon(redimensionamentoDeImagem(arquivo));
        }
    }//GEN-LAST:event_btSelecionarImagensMouseClicked
    public void Apagar() {

        if (pesquisar != null) {
            pesquisar.setText("");
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

    public void carregarCategorias() {
        try {
            Connection con = Conexao.conexaoBanco();
            String sql = "SELECT id_categoria, categoria FROM categoria ORDER BY id_categoria ASC;";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Adiciona cada categoria ao JComboBox
                //id_categoria = rs.getString("id_categoria");

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
    }

    public static void main(String[] args) {
        new PesquisaProdutos();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAlterar;
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btCancelar1;
    private javax.swing.JButton btSelecionarImagens;
    private javax.swing.JTextField categoria;
    private javax.swing.JTextField codigo;
    private javax.swing.JLabel deletar1;
    private javax.swing.JLabel deletar2;
    private javax.swing.JTextArea descricao;
    private javax.swing.JTextField estoque;
    private javax.swing.JTextField imagem1;
    private javax.swing.JTextField imagem2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelImagens;
    private javax.swing.JTextField marca;
    private javax.swing.JTextField nomeProduto;
    private javax.swing.JPanel painelProdutos;
    private javax.swing.JLabel passarImagem;
    private javax.swing.JLabel pesquisa;
    private javax.swing.JTextField pesquisar;
    private javax.swing.JTextField preco;
    private javax.swing.JLabel sem_imagem;
    private javax.swing.JTextField subcategoria;
    // End of variables declaration//GEN-END:variables
}
