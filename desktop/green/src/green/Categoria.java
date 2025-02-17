/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package green;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Categoria extends javax.swing.JFrame {

    private String descricaoAntiga;
    private int idCategoria;
    private String descricao;
    private Integer idCategoriaPai;

    /**
     * Creates new form Categoria
     */
    public Categoria() {
       initComponents();
        carregarDadosTabela(); 
        carregarCategoriasCombo();

}
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        descrica = new javax.swing.JTextField();
        btCadastrar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        carregarCategoriasCombo = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 102));

        jLabel1.setText("Cadastrar SubCategorias");

        btCadastrar.setText("Cadastrar");
        btCadastrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btCadastrarMouseClicked(evt);
            }
        });
        btCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadastrarActionPerformed(evt);
            }
        });

        jButton2.setText("Alterar");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id_Categoria", "SubCategoria ", "Categoria "
            }
        ));
        tabela.setSelectionBackground(new java.awt.Color(102, 102, 102));
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabela);

        carregarCategoriasCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carregarCategoriasComboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(descrica, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(btCadastrar)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(carregarCategoriasCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descrica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btCadastrar)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(carregarCategoriasCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void btCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadastrarActionPerformed
    }//GEN-LAST:event_btCadastrarActionPerformed

    private void btCadastrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btCadastrarMouseClicked
 try {
            Connection con = Conexao.conexaoBanco();
            String sql = "INSERT INTO categoria (descricao, id_categoria_pai) VALUES (?, ?);";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, descrica.getText());

            Integer idCategoriaPai = getIdCategoriaSelecionada(); 
            stmt.setObject(2, idCategoriaPai); 

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
        }
    }//GEN-LAST:event_tabelaMouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
         try {
            Connection con = Conexao.conexaoBanco();
            if (con == null) {
                JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados.");
                return;
            }

            if (descrica.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "A descrição não pode estar vazia.");
                return;
            }

            String sql = "UPDATE categoria SET descricao = ? WHERE id_categoria = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, descrica.getText());
            stmt.setInt(2, idCategoria); 

            int rowsAffected = stmt.executeUpdate();
            stmt.close();
            con.close();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Alteração feita com sucesso!");
                descrica.setText(null);
                carregarDadosTabela();
            } else {
                JOptionPane.showMessageDialog(null, "Nenhuma categoria foi alterada. Verifique se a descrição antiga existe.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Categoria.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao alterar categoria: " + ex.getMessage());
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
            String sql = "SELECT id_categoria, descricao FROM categoria WHERE id_categoria_pai IS NULL"; 
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            carregarCategoriasCombo.removeAllItems(); 

            while (rs.next()) {
                int idCategoria = rs.getInt("id_categoria");
                String descricao = rs.getString("descricao");
                carregarCategoriasCombo.addItem(descricao + " (ID: " + idCategoria + ")");
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
            String sql = "SELECT c.id_categoria, c.descricao, cp.descricao AS categoria_pai " +
                         "FROM categoria c LEFT JOIN categoria cp ON c.id_categoria_pai = cp.id_categoria " +
                         "ORDER BY c.id_categoria DESC;";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            DefaultTableModel modeloTabela = (DefaultTableModel) tabela.getModel();
            modeloTabela.setNumRows(0); 

            while (rs.next()) {
                Object[] dados = {
                    rs.getInt("id_categoria"), 
                    rs.getString("descricao"),  
                    rs.getString("categoria_pai") 
                };
                modeloTabela.addRow(dados); 
            }

            stmt.close();
            rs.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Categoria.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao carregar categorias: " + ex.getMessage());
 
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
        }
        return null; 
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCadastrar;
    private javax.swing.JComboBox<String> carregarCategoriasCombo;
    private javax.swing.JTextField descrica;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables

}
