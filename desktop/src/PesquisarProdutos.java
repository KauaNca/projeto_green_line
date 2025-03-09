
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

import javax.swing.JOptionPane;

import javax.swing.Timer;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

/**
 *
 * @author Kaua33500476
 */
public class PesquisarProdutos extends javax.swing.JInternalFrame {

    int contagem = 0;
    File arquivo;
    private boolean atualizandoMascara = false;

    String enderecoImagemBanco1;
    String enderecoImagemBanco2;
    String enderecoNovo1;
    String enderecoNovo2;
    String[] enderecosImagens = new String[2];
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

    public PesquisarProdutos() {
        initComponents();
        Inicio();
        nomesProdutos();

        seta.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                slide.start();
            }

        });

    }

    public void nomesProdutos() {
        try (Connection con = Conexao.conexaoBanco()) {
            PreparedStatement stmt = con.prepareStatement("SELECT nome_produto FROM produto WHERE LOWER(nome_produto) LIKE ?");
            stmt.setString(1, "%" + pesquisar.getText() + "%");
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
        sem_imagem.setIcon(sem_imagem());
        seta.setIcon(new ImageIcon("imagens/seta-direita.png"));
        pesquisa.setIcon(new ImageIcon("imagens/lupa.png"));
        seta.setVisible(false);
    }

    private void atualizarMascara() {
        if (atualizandoMascara) {
            return; // Evita loops
        }
        atualizandoMascara = true;
        SwingUtilities.invokeLater(() -> {
            String texto = nomeProduto.getText();
            nomeProduto.setText(texto.replaceAll("[^a-zA-Z0-9áéíóúâêîôûãõçÁÉÍÓÚÂÊÎÔÛÃÕÇñÑ~\\s]", ""));
            atualizandoMascara = false;
        });
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

    public ImageIcon sem_imagem() {
        ImageIcon imagem = new ImageIcon("imagens/sem_imagem.jpg");
        Image redimensionar = imagem.getImage();
        Image redimensionar2 = redimensionar.getScaledInstance(250, 216, Image.SCALE_SMOOTH);
        ImageIcon imagemRedimensionada = new ImageIcon(redimensionar2);
        return imagemRedimensionada;
    }

    public ImageIcon redimensionamentoDeImagem(ImageIcon imagem, int largura, int altura) {
        Image pegarImagem = imagem.getImage();
        Image redimensionando = pegarImagem.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        ImageIcon imagemRedimensionada = new ImageIcon(redimensionando);
        return imagemRedimensionada;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sem_imagem = new javax.swing.JLabel();
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
        btUltimasVendas = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        categoria = new javax.swing.JTextField();
        subcategoria = new javax.swing.JTextField();
        Imagens = new javax.swing.JLabel();
        imagem1 = new javax.swing.JTextField();
        imagem2 = new javax.swing.JTextField();
        btCancelar = new javax.swing.JButton();
        seta = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Produtos");

        sem_imagem.setBackground(new java.awt.Color(255, 255, 255));

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

        btUltimasVendas.setBackground(new java.awt.Color(204, 204, 255));
        btUltimasVendas.setFont(new java.awt.Font("Arial", 0, 21)); // NOI18N
        btUltimasVendas.setForeground(new java.awt.Color(51, 51, 51));
        btUltimasVendas.setText("Últimas vendas");
        btUltimasVendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btUltimasVendasActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Arial", 0, 21)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Categoria:");

        categoria.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        categoria.setDisabledTextColor(new java.awt.Color(51, 51, 51));

        subcategoria.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        subcategoria.setDisabledTextColor(new java.awt.Color(51, 51, 51));

        Imagens.setFont(new java.awt.Font("Arial", 0, 21)); // NOI18N
        Imagens.setForeground(new java.awt.Color(0, 0, 0));
        Imagens.setText("Imagens");

        imagem1.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        imagem1.setDisabledTextColor(new java.awt.Color(51, 51, 51));

        imagem2.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        imagem2.setDisabledTextColor(new java.awt.Color(51, 51, 51));

        btCancelar.setBackground(new java.awt.Color(169, 169, 169));
        btCancelar.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        btCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btCancelar.setText("Cancelar");
        btCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarActionPerformed(evt);
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
                    .addComponent(sem_imagem, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Imagens, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(marca, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(estoque, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btUltimasVendas))
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btCancelar))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(imagem1, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(subcategoria)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(seta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(btUltimasVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(categoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(subcategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Imagens)
                            .addComponent(imagem1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(imagem2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(54, 54, 54)
                        .addComponent(btCancelar)
                        .addGap(24, 24, 24))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void codigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            seta.setVisible(true);
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

    private void btUltimasVendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btUltimasVendasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btUltimasVendasActionPerformed


    private void pesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pesquisarKeyReleased
        String pesquisa = pesquisar.getText().toLowerCase();
        sugestoesProdutos.setVisible(false);
        seta.setVisible(true);
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
        sem_imagem.setIcon(sem_imagem());
    }//GEN-LAST:event_btCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Imagens;
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btUltimasVendas;
    private javax.swing.JTextField categoria;
    private javax.swing.JTextField codigo;
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
