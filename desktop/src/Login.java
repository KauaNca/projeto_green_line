
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Login extends javax.swing.JFrame {

    private String codigo;
    private String tipo_usuario;
    private String nivel_acesso;
    JLabel imagem2 = new JLabel();
    Color corVerde = new Color(29, 68, 53);
    EmptyBorder margensInternas = new EmptyBorder(5, 5, 0, 0);

    public Login() {
        initComponents();

        Frame();
        usuario.setBorder(margensInternas);
        codigoCampo.setBorder(margensInternas);
        senha.setBorder(margensInternas);
        painelPrincipal.setBackground(corVerde);
        usuario.setEnabled(false);
        usuario.setEditable(false);
        imagem.setIcon(new ImageIcon("imagens/usuarios/usuario.png"));
        codigoCampo.setFocusTraversalKeysEnabled(false);

    }

    public void Frame() {
        setLayout(new BorderLayout());  // Usando BorderLayout
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        senha = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        btLogin = new javax.swing.JButton();
        btSair = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        codigoCampo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        apagarCodigo = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        usuario = new javax.swing.JTextField();
        imagem = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 242, 207));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        painelPrincipal.setBackground(new java.awt.Color(0, 51, 0));
        painelPrincipal.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Senha");

        senha.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        senha.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Usuário");

        btLogin.setBackground(new java.awt.Color(50, 205, 50));
        btLogin.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        btLogin.setForeground(new java.awt.Color(255, 255, 255));
        btLogin.setText("Entrar");
        btLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btLoginMouseClicked(evt);
            }
        });

        btSair.setBackground(new java.awt.Color(169, 169, 169));
        btSair.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        btSair.setForeground(new java.awt.Color(255, 255, 255));
        btSair.setText("Sair");
        btSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSairActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Berlin Sans FB Demi", 0, 40)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Green Line");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(245, 245, 245));
        jLabel5.setText("Código");

        codigoCampo.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        codigoCampo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        codigoCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigoCampoKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Sem cadastro?");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Esqueceu sua senha?");

        apagarCodigo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        apagarCodigo.setForeground(new java.awt.Color(255, 255, 255));
        apagarCodigo.setText("X");
        apagarCodigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                apagarCodigoMouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("X");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("X");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        usuario.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        usuario.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        usuario.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        imagem.setIcon(new javax.swing.ImageIcon("C:\\Users\\kauan\\OneDrive\\Área de Trabalho\\projeto_green_line\\desktop\\imagens\\usuarios\\usuario.png")); // NOI18N

        javax.swing.GroupLayout painelPrincipalLayout = new javax.swing.GroupLayout(painelPrincipal);
        painelPrincipal.setLayout(painelPrincipalLayout);
        painelPrincipalLayout.setHorizontalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPrincipalLayout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelPrincipalLayout.createSequentialGroup()
                        .addGroup(painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGap(24, 24, 24)
                        .addGroup(painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(senha)
                                .addComponent(codigoCampo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27))
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(apagarCodigo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelPrincipalLayout.createSequentialGroup()
                        .addGroup(painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelPrincipalLayout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addComponent(jLabel6))
                            .addGroup(painelPrincipalLayout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addComponent(jLabel7))
                            .addGroup(painelPrincipalLayout.createSequentialGroup()
                                .addComponent(btLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btSair, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(67, 67, 67)))
                .addGap(32, 32, 32))
            .addGroup(painelPrincipalLayout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addGroup(painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imagem, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        painelPrincipalLayout.setVerticalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imagem, javax.swing.GroupLayout.PREFERRED_SIZE, 190, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(codigoCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(apagarCodigo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(4, 4, 4)
                .addGroup(painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(senha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btLogin)
                    .addComponent(btSair))
                .addGap(46, 46, 46))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void numeroAcesso() {
        try {
            System.out.println("SALVANDO O ACESSO");
            Connection con = Conexao.conexaoBanco();
            String sql = "INSERT INTO acessos(id_usuario,nome_usuario) VALUES (?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, codigo);
            stmt.setString(2, usuario.getText());
            stmt.execute();
            System.out.println("ACESSO SALVO");
            stmt.close();
            con.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
        }

    }

    private void Login() {
        if (codigoCampo.getText().isBlank() || senha.getText().isBlank()) {
            new CadastroProdutos().Avisos("imagens/erro.png", "Preencha os campos");
            return;
        } else {
            try {
                Connection con = Conexao.conexaoBanco();
                String sql = "SELECT id_usuario, id_tipo_usuario, nivel_acesso "
                        + "FROM usuario INNER JOIN pessoa ON pessoa.id_pessoa = usuario.id_pessoa "
                        + "WHERE nome = ? AND senha = ?;";

                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, usuario.getText());
                stmt.setString(2, senha.getText());

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    codigo = rs.getString("id_usuario");
                    tipo_usuario = rs.getString("id_tipo_usuario");
                    nivel_acesso = rs.getString("nivel_acesso");

                    if (codigo.equals(codigoCampo.getText()) && nivel_acesso.equals("Com acesso")) {
                        new TelaInicial(codigo, tipo_usuario);

                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuário comum | Sem acesso | Fale com ADM");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Senha ou Usuário incorreto!");
                }

                rs.close();
                stmt.close();
                con.close();
                numeroAcesso();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
            }
        }

    }
    private void btLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btLoginMouseClicked
        Login();
    }//GEN-LAST:event_btLoginMouseClicked

    private void btSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSairActionPerformed
        dispose();
    }//GEN-LAST:event_btSairActionPerformed

    private void apagarCodigoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_apagarCodigoMouseClicked
        codigoCampo.setText("");
        usuario.setText("");

    }//GEN-LAST:event_apagarCodigoMouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        codigoCampo.setText("");
        usuario.setText("");
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        senha.setText("");

    }//GEN-LAST:event_jLabel9MouseClicked

    private void codigoCampoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoCampoKeyPressed
        codigoCampo.setFocusTraversalKeysEnabled(false);
        ImageIcon imagemUsuario;
        String usuarioImagem;
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            Connection con = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                con = Conexao.conexaoBanco();
                String nomeUsuario;
                String sql = "SELECT * FROM login WHERE id_usuario = ?";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, codigoCampo.getText());
                rs = stmt.executeQuery();

                if (rs.next()) {
                    nomeUsuario = rs.getString("nome");
                    usuarioImagem = rs.getString("caminho_imagem");

                    imagemUsuario = new ImageIcon("imagens/usuarios/" + usuarioImagem);

                    if (imagemUsuario.getIconWidth() == -1) {
                        System.out.println("Imagem não encontrada");
                    } else {

                        usuario.setText(nomeUsuario);
                        codigoCampo.setFocusTraversalKeysEnabled(true);

                        imagem.setIcon(redimensionamentoDeImagem(imagemUsuario, 200, 142));

                        painelPrincipal.add(imagem2); // Adiciona novamente ao painel
                        painelPrincipal.revalidate();
                        painelPrincipal.repaint();
                    }

                } else {
                    new CadastroProdutos().Avisos("imagens/sinal-de-aviso.png", "Usuário não encontrado. Faça um cadastro ou contate os administradores.");
                }
            } catch (Exception e) {
                new CadastroProdutos().Avisos("imagens/erro.png", "Houve um erro. Tente novamente mais tarde");
                e.printStackTrace();
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (con != null) {
                        con.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_codigoCampoKeyPressed
    public ImageIcon redimensionamentoDeImagem(ImageIcon imagem, int largura, int altura) {
        Image pegarImagem = imagem.getImage();
        Image redimensionando = pegarImagem.getScaledInstance(largura, altura, Image.SCALE_DEFAULT);
        ImageIcon imagemRedimensionada = new ImageIcon(redimensionando);
        return imagemRedimensionada;
    }
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Login();
        }
    }//GEN-LAST:event_formKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel apagarCodigo;
    private javax.swing.JButton btLogin;
    private javax.swing.JButton btSair;
    private javax.swing.JTextField codigoCampo;
    private javax.swing.JLabel imagem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JPasswordField senha;
    private javax.swing.JTextField usuario;
    // End of variables declaration//GEN-END:variables
}
