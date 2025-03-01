
/**
 *
 * @author kauan
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

public class EscolhaDeCategoria extends javax.swing.JFrame {

    JRadioButton[] categorias;
    ButtonGroup escolhas = new ButtonGroup();
    public String categoria;
    public String nomeCategoria;
    public String id_categoria;

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String Categoria) {
        this.categoria = Categoria;
    }

    public EscolhaDeCategoria() {
        initUI();

        Categorias();
    }

    public void initUI() {
        initComponents();
        setLayout(new BorderLayout());
        painelEscolhas.setPreferredSize(new Dimension(400, 106));
        painelEscolhas.setBackground(new Color(255, 242, 207));
        painelEscolhas.setLayout(new GridLayout(0, 1));
        painelEscolhas.add(titulo);
        painelEscolhas.add(btOK);

        setResizable(false);
        add(painelEscolhas, BorderLayout.CENTER);
        setVisible(true);
    }

    public void Categorias() {
        try (Connection con = Conexao.conexaoBanco()) {
            // Primeiro, conta o número de categorias
            String sql = "SELECT COUNT(*) FROM categoria";
            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            int numeroDeLinhas = 0;
            if (rs.next()) {
                numeroDeLinhas = rs.getInt(1);
            }

            categorias = new JRadioButton[numeroDeLinhas];

            PreparedStatement stmt2 = con.prepareStatement("SELECT categoria FROM categoria");
            ResultSet rs2 = stmt2.executeQuery();

            int x = 0;
            while (rs2.next()) {
                String nomeSubcategoria = rs2.getString("categoria");
                System.out.println(nomeSubcategoria);
                categorias[x] = new JRadioButton(nomeSubcategoria);
                categorias[x].setActionCommand(nomeSubcategoria); // Define o comando de ação
                categorias[x].setFont(new Font("Arial", Font.PLAIN, 18));
                categorias[x].setForeground(Color.black);
                categorias[x].setBackground(new Color(255, 242, 207));
                escolhas.add(categorias[x]);
                painelEscolhas.add(categorias[x]);
                x++;
            }

            rs2.close();
            stmt2.close();
            rs.close();
            stmt.close();

        } catch (Exception ex) {
            new CadastroProdutos().Avisos("imagens/erro.png", "Desculpe, um erro aconteceu");
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelEscolhas = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        btOK = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        titulo.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        titulo.setText("Escolha:");

        btOK.setBackground(new java.awt.Color(50, 205, 50));
        btOK.setForeground(new java.awt.Color(255, 255, 255));
        btOK.setText("OK");
        btOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelEscolhasLayout = new javax.swing.GroupLayout(painelEscolhas);
        painelEscolhas.setLayout(painelEscolhasLayout);
        painelEscolhasLayout.setHorizontalGroup(
            painelEscolhasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelEscolhasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(259, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelEscolhasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btOK)
                .addContainerGap())
        );
        painelEscolhasLayout.setVerticalGroup(
            painelEscolhasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelEscolhasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                .addComponent(btOK))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelEscolhas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelEscolhas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(42, 42, 42))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        if (escolhas.getSelection() != null) {
            categoria = escolhas.getSelection().getActionCommand();
        } else {
            categoria = "Nenhuma"; // Evita null
        }
    }//GEN-LAST:event_formWindowClosed

    private void btOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOKActionPerformed
        if (escolhas.getSelection() != null) {
            categoria = escolhas.getSelection().getActionCommand();
        } else {
            categoria = "Nenhuma"; // Define um valor padrão
        }
        System.out.println(categoria);
        this.dispose();
    }//GEN-LAST:event_btOKActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(EscolhaDeSubcategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        EventQueue.invokeLater(() -> new EscolhaDeSubcategoria().setVisible(true));

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btOK;
    private javax.swing.JPanel painelEscolhas;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
