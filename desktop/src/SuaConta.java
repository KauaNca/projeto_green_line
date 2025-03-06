
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import java.awt.Color;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.Properties;
import javax.swing.UIManager;

/**
 *
 * @author kauan
 */
public class SuaConta extends javax.swing.JInternalFrame {

    TelaInicial tela;
    String codigo;

    public SuaConta(String codigo) {
        initComponents();
        //configurarUIManager();
        this.codigo = codigo;
        recuperarConta(this.codigo);
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
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imagem = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        usuario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        idade = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        telefone = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cpf = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        rg = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cidade = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        uf = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        bairro = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        endereco = new javax.swing.JTextField();
        complemento = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        senha = new javax.swing.JTextField();
        btAlterar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btSelecionar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Sua conta");

        imagem.setBackground(new java.awt.Color(0, 0, 0));
        imagem.setForeground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Usuário");

        usuario.setBackground(new java.awt.Color(255, 255, 255));
        usuario.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        usuario.setForeground(new java.awt.Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Idade");

        idade.setBackground(new java.awt.Color(255, 255, 255));
        idade.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        idade.setForeground(new java.awt.Color(0, 0, 0));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("E-mail");

        email.setBackground(new java.awt.Color(255, 255, 255));
        email.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        email.setForeground(new java.awt.Color(0, 0, 0));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Telefone");

        telefone.setBackground(new java.awt.Color(255, 255, 255));
        telefone.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        telefone.setForeground(new java.awt.Color(0, 0, 0));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Cadastro de Pessoa Física (CPF)");

        cpf.setBackground(new java.awt.Color(255, 255, 255));
        cpf.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        cpf.setForeground(new java.awt.Color(0, 0, 0));

        jLabel7.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Registro Geral (RG)");

        rg.setBackground(new java.awt.Color(255, 255, 255));
        rg.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        rg.setForeground(new java.awt.Color(0, 0, 0));

        jLabel9.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Cidade - Município");

        cidade.setBackground(new java.awt.Color(255, 255, 255));
        cidade.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        cidade.setForeground(new java.awt.Color(0, 0, 0));

        jLabel10.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("UF");

        uf.setBackground(new java.awt.Color(204, 204, 255));
        uf.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        uf.setForeground(new java.awt.Color(0, 0, 0));
        uf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO" }));

        jLabel11.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Bairro");

        bairro.setBackground(new java.awt.Color(255, 255, 255));
        bairro.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        bairro.setForeground(new java.awt.Color(0, 0, 0));

        jLabel12.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Endereco");

        endereco.setBackground(new java.awt.Color(255, 255, 255));
        endereco.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        endereco.setForeground(new java.awt.Color(0, 0, 0));

        complemento.setBackground(new java.awt.Color(255, 255, 255));
        complemento.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        complemento.setForeground(new java.awt.Color(0, 0, 0));

        jLabel13.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Complemento");

        jLabel14.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Senha");

        senha.setBackground(new java.awt.Color(255, 255, 255));
        senha.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        senha.setForeground(new java.awt.Color(0, 0, 0));

        btAlterar.setBackground(new java.awt.Color(253, 126, 20));
        btAlterar.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        btAlterar.setForeground(new java.awt.Color(255, 255, 255));
        btAlterar.setText("Alterar");
        btAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAlterarActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(40, 167, 69));
        jButton2.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Salvar");

        btSelecionar.setBackground(new java.awt.Color(0, 123, 255));
        btSelecionar.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        btSelecionar.setForeground(new java.awt.Color(255, 255, 255));
        btSelecionar.setText("Selecionar foto");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btSelecionar)
                    .addComponent(imagem, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(uf, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(cidade, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel9)))
                                        .addComponent(jLabel1)
                                        .addComponent(usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4)
                                        .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6)
                                        .addComponent(cpf, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(endereco)
                                    .addGap(31, 31, 31)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(671, 671, 671)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(complemento)
                                .addComponent(idade)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(telefone)
                                .addComponent(jLabel5)
                                .addComponent(rg)
                                .addComponent(jLabel7)
                                .addComponent(jLabel11)
                                .addComponent(bairro)))
                        .addGap(67, 67, 67))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(senha, javax.swing.GroupLayout.PREFERRED_SIZE, 714, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(70, 70, 70))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(imagem, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btSelecionar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(usuario, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                            .addComponent(idade))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(email, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                            .addComponent(telefone))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cpf)
                            .addComponent(rg, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(jLabel9))
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(uf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cidade, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(bairro, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(endereco)
                    .addComponent(complemento, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(btAlterar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(senha, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btAlterarActionPerformed
public void recuperarConta(String codigo) {
    System.out.println("COMECOU");
    try (Connection con = Conexao.conexaoBanco()) {
        System.out.println("Conexao estabelecida");
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM dados_pessoais WHERE id_pessoa = ?");
        stmt.setString(1, codigo);
        System.out.println("PreparedStatement configurado");
        ResultSet rs = stmt.executeQuery();
        System.out.println("Query executada");

        if (rs.next()) {
            System.out.println("Dados encontrados");
            usuario.setText(Objects.toString(rs.getString("nome"), ""));
            System.out.println("Nome preenchido");
            email.setText(Objects.toString(rs.getString("email"), ""));
            System.out.println("Email preenchido");
            telefone.setText(Objects.toString(rs.getString("telefone"), ""));
            System.out.println("Telefone preenchido");
            cpf.setText(Objects.toString(rs.getString("cpf_cnpj"), ""));
            System.out.println("CPF preenchido");
            rg.setText(Objects.toString(rs.getString("rg"), ""));
            System.out.println("RG preenchido");
            idade.setText(Objects.toString(rs.getString("idade"), ""));
            System.out.println("Idade preenchida");
            System.out.println("Endereço");

            for (int x = 0; x < uf.getItemCount(); x++) {
                if (uf.getItemAt(x).equals(rs.getString("uf"))) {
                    uf.setSelectedIndex(x);
                    System.out.println("UF selecionado");
                    break;
                }
            }
            cidade.setText(Objects.toString(rs.getString("cidade"), ""));
            System.out.println("Cidade preenchida");
            bairro.setText(Objects.toString(rs.getString("bairro"), ""));
            System.out.println("Bairro preenchido");
            endereco.setText(Objects.toString(rs.getString("endereco"), ""));
            System.out.println("Endereço preenchido");
            complemento.setText(Objects.toString(rs.getString("complemento"), ""));
            System.out.println("Complemento preenchido");
            senha.setText(Objects.toString(rs.getString("senha"), ""));
            System.out.println("Senha preenchida");
        } else {
            System.out.println("Nenhum dado encontrado para o codigo fornecido");
        }
    } catch (SQLException ex) {
        ex.printStackTrace(); // Registre o erro para depuração
    }
}





    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bairro;
    private javax.swing.JButton btAlterar;
    private javax.swing.JButton btSelecionar;
    private javax.swing.JTextField cidade;
    private javax.swing.JTextField complemento;
    private javax.swing.JTextField cpf;
    private javax.swing.JTextField email;
    private javax.swing.JTextField endereco;
    private javax.swing.JTextField idade;
    private javax.swing.JLabel imagem;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField rg;
    private javax.swing.JTextField senha;
    private javax.swing.JTextField telefone;
    private javax.swing.JComboBox<String> uf;
    private javax.swing.JTextField usuario;
    // End of variables declaration//GEN-END:variables
}
