
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class Categoria extends javax.swing.JFrame {

 private String descricaoAntiga;
    private int idCategoria;
    private String descricao;
    private Integer idCategoriaPai;
    private JTextArea jTextArea1;
    /**
     * Creates new form Categoria
     */
    public Categoria() {
       initComponents();
        carregarDadosTabela();
        carregarCategoriasCombo();
        setResizable(false);
}
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        descricaoArea = new javax.swing.JTextArea();
        descrica = new javax.swing.JTextField();
        btCadastrar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        carregarCategoriasCombo = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 102));

        jPanel1.setBackground(new java.awt.Color(255, 242, 207));

        tabela.setBackground(new java.awt.Color(255, 242, 207));
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "SubCategoria ", "Categoria "
            }
        ));
        tabela.setSelectionBackground(new java.awt.Color(102, 102, 102));
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabela);

        jLabel1.setText("Descrição:");

        descricaoArea.setColumns(20);
        descricaoArea.setRows(5);
        jScrollPane2.setViewportView(descricaoArea);

        btCadastrar.setBackground(new java.awt.Color(43, 189, 49));
        btCadastrar.setText("Cadastrar");
        btCadastrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btCadastrarMouseClicked(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(204, 204, 204));
        jButton2.setText("Alterar");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        carregarCategoriasCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione uma opção" }));
        carregarCategoriasCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carregarCategoriasComboActionPerformed(evt);
            }
        });

        jLabel2.setText("Cadastrar SubCategorias");

        jLabel3.setText("Categoria:");

        jLabel4.setText("Nome:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(descrica, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel3)
                                .addComponent(carregarCategoriasCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(btCadastrar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton2)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel2)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(20, 20, 20)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(carregarCategoriasCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(descrica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(btCadastrar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 674, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void btCadastrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btCadastrarMouseClicked
 try {
        Connection con = Conexao.conexaoBanco();
        String sql = "INSERT INTO subcategorias (id_categoria, subcategoria, descricao) VALUES (?, ?, ?);";
        PreparedStatement stmt = con.prepareStatement(sql);
        
        Integer idCategoriaSelecionada = getIdCategoriaSelecionada();
        stmt.setInt(1, idCategoriaSelecionada);
        stmt.setString(2, descrica.getText());
        stmt.setString(3, descricaoArea.getText());

        stmt.executeUpdate();
        stmt.close();
        JOptionPane.showMessageDialog(null, "SubCategoria cadastrada com sucesso!");
        carregarDadosTabela();
        carregarCategoriasCombo();
    } catch (SQLException ex) {
        Logger.getLogger(Categoria.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
    }
    }//GEN-LAST:event_btCadastrarMouseClicked

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
    int linha = tabela.getSelectedRow();
    if (linha >= 0) {
        
        descricaoAntiga = tabela.getValueAt(linha, 1).toString(); 
        idCategoria = (int) tabela.getValueAt(linha, 0); 
        descrica.setText(descricaoAntiga); 
        System.out.println("ID da subcategoria selecionada: " + idCategoria);
    }
    }//GEN-LAST:event_tabelaMouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
     try {
        Connection con = Conexao.conexaoBanco();
        if (con == null) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados.");
            return;
        }

        if (idCategoria == 0) {
            JOptionPane.showMessageDialog(null, "Selecione uma subcategoria para alterar.");
            return;
        }

        if (descrica.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "A descrição não pode estar vazia.");
            return;
        }

        Integer idNovaCategoria = getIdCategoriaSelecionada();
        if (idNovaCategoria == null) {
            JOptionPane.showMessageDialog(null, "Selecione uma nova categoria.");
            return;
        }

        // Atualiza a subcategoria no banco de dados
        String sql = "UPDATE subcategorias SET id_categoria = ?, subcategoria = ?, descricao = ? WHERE id_subcat = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, idNovaCategoria); 
        stmt.setString(2, descrica.getText());
        stmt.setString(3, descricaoArea.getText()); 
        stmt.setInt(4, idCategoria); 

        int rowsAffected = stmt.executeUpdate();
        stmt.close();
        con.close();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Subcategoria alterada com sucesso!");
            descrica.setText(null);
            descricaoArea.setText(null);
            carregarDadosTabela(); 
        } else {
            JOptionPane.showMessageDialog(null, "Nenhuma subcategoria foi alterada. Verifique se a descrição antiga existe.");
        }
    } catch (SQLException ex) {
        Logger.getLogger(Categoria.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Erro ao alterar subcategoria: " + ex.getMessage());
    }
    }//GEN-LAST:event_jButton2MouseClicked

    private void carregarCategoriasComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carregarCategoriasComboActionPerformed
        String selectedItem = (String) carregarCategoriasCombo.getSelectedItem();
if (selectedItem != null) {
    
    System.out.println("Categoria selecionada: " + selectedItem);
   
    Integer idCategoriaPai = getIdCategoriaSelecionada();
    System.out.println("ID da categoria pai selecionada: " + idCategoriaPai);
}
    }//GEN-LAST:event_carregarCategoriasComboActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Categoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Categoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Categoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Categoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Categoria().setVisible(true);
            }
        });
    }
    
   private void carregarCategoriasCombo() {
    try {
        Connection con = Conexao.conexaoBanco();
        String sql = "SELECT id_categoria, categoria FROM categoria ORDER BY categoria ASC;";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        carregarCategoriasCombo.removeAllItems(); // Limpa os itens existentes
        while (rs.next()) {
            // Adiciona cada categoria ao JComboBox
            carregarCategoriasCombo.addItem(rs.getString("categoria") + " (ID: " + rs.getInt("id_categoria") + ")");
        }

        stmt.close();
        rs.close();
        con.close();
    } catch (SQLException ex) {
        Logger.getLogger(Categoria.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Erro ao carregar categorias: " + ex.getMessage());
    }
}
    private void carregarDadosTabela() {
    try {
        Connection con = Conexao.conexaoBanco();
        String sql = "SELECT s.id_subcat, s.subcategoria, c.categoria " +
                     "FROM subcategorias s JOIN categoria c ON s.id_categoria = c.id_categoria " +
                     "ORDER BY s.id_subcat DESC;";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        DefaultTableModel modeloTabela = (DefaultTableModel) tabela.getModel();
        modeloTabela.setNumRows(0); 

        while (rs.next()) {
            Object[] dados = {
                rs.getInt("id_subcat"), 
                rs.getString("subcategoria"),  
                rs.getString("categoria") 
            };
            modeloTabela.addRow(dados); 
        }

        stmt.close();
        rs.close();
        con.close();
    } catch (SQLException ex) {
        Logger.getLogger(Categoria.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Erro ao carregar subcategorias: " + ex.getMessage());
    }
}
    private Integer getIdCategoriaSelecionada() {
    String selectedItem = (String) carregarCategoriasCombo.getSelectedItem();
    if (selectedItem != null) {
        String[] parts = selectedItem.split(" \\(ID: ");
        if (parts.length > 1) {
            String idString = parts[1].replace(")", ""); 
            return Integer.parseInt(idString); 
        }
    } else {
        // Mensagem de erro quando nenhuma categoria é selecionada
        JOptionPane.showMessageDialog(null, "Por favor, selecione uma categoria.", "Erro", JOptionPane.ERROR_MESSAGE);
    }
    return null; 
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCadastrar;
    private javax.swing.JComboBox<String> carregarCategoriasCombo;
    private javax.swing.JTextField descrica;
    private javax.swing.JTextArea descricaoArea;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables

}
