
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

/**
 *
 * @author guilherme53961526
 */
public class PesquisarUsuario extends javax.swing.JInternalFrame {

    CardLayout card;
    ArrayList<String> usuarios;
    JPopupMenu caixaDeNomes = new JPopupMenu();
    ArrayList<String> filtro;
    Font fonteItem = new Font("Arial", Font.PLAIN, 19);

    public PesquisarUsuario() {
        initComponents();
        perfil1.setIcon(new ImageIcon("imagens/perfil.png"));
        perfil.setIcon(new ImageIcon("imagens/perfil.png"));
        usuarios = new ArrayList<>();
        rdPessoa.setSelected(true);
        desativarTextField(painelPessoa);
        desativarTextField(painelEmpresa);
        nomesUsuarios("F");

    }

    public void desativarTextField(JPanel painel) {
        for (Component component : painel.getComponents()) {
            if (component instanceof JTextField) {
                component.setEnabled(false);
            }
        }
        codigoUsuario.setEnabled(true);
        codigoUsuario1.setEnabled(true);
        cpf.setEnabled(true);
        cnpj.setEnabled(true);

       
    }

    public ImageIcon redimensionamentoDeImagem(ImageIcon imagem, int largura, int altura) {
        Image pegarImagem = imagem.getImage();
        Image redimensionando = pegarImagem.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        ImageIcon imagemRedimensionada = new ImageIcon(redimensionando);
        return imagemRedimensionada;
    }

    public void nomesUsuarios(String tipo_pessoa) {
        usuarios.clear();
        try {
            Connection con = Conexao.conexaoBanco();
            PreparedStatement stmt = con.prepareStatement("SELECT nome FROM pessoa WHERE tipo_pessoa = ?");
            stmt.setString(1, tipo_pessoa);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                usuarios.add(rs.getString("nome"));
            }
            for (String nomes : usuarios) {
                System.out.println(nomes);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pesquisarNome() {
        String texto = tfPesquisar.getText();
        filtro = new ArrayList<>();
        filtro.clear();
        if (!texto.isEmpty()) {
            for (String nome : usuarios) {
                if (nome.contains(texto)) {
                    filtro.add(nome);
                }
            }
            caixaDeNomes.removeAll();
            if (!filtro.isEmpty()) {
                for (String nome : filtro) {
                    JMenuItem item = new JMenuItem(nome);
                    item.addActionListener(e -> {
                        tfPesquisar.setText(nome);
                        item.setFont(fonteItem);
                        caixaDeNomes.setVisible(false);
                    });
                    caixaDeNomes.add(item);
                }
                caixaDeNomes.setVisible(true);
                caixaDeNomes.show(tfPesquisar, 0, tfPesquisar.getHeight());
            } else {
                caixaDeNomes.setVisible(false);
            }

        } else {

            caixaDeNomes.removeAll();
            caixaDeNomes.setVisible(false);
        }

    }

    private void pesquisarPessoa() {
        try {
            Connection con = Conexao.conexaoBanco();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM dados_pesquisa WHERE nome = ? AND tipo_pessoa = 'F'");
            stmt.setString(1, tfPesquisar.getText());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                codigoUsuario.setText(rs.getString("id_usuario"));
                nome.setText(rs.getString("nome"));
                email.setText(rs.getString("email"));
                telefone.setText(rs.getString("telefone"));
                cpf.setText(rs.getString("cpf_cnpj"));
                rg.setText(rs.getString("rg"));
                idade.setText(rs.getString("idade"));
                estado.setText(rs.getString("uf"));
                cep.setText(rs.getString("cep"));
                cidade.setText(rs.getString("cidade"));
                bairro.setText(rs.getString("bairro"));
                endereco.setText(rs.getString("endereco"));
                complemento.setText(rs.getString("complemento"));

                ImageIcon foto = new ImageIcon("imagens/usuarios/" + rs.getString("caminho_imagem"));
                perfil.setIcon(redimensionamentoDeImagem(foto, 205, 227));

            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "<html> <h3> Não foi possível encontrar este nome</h3> </html>");

        }
    }

    private void pesquisarEmpresa() {
        try {
            Connection con = Conexao.conexaoBanco();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM dados_pesquisa WHERE nome = ? AND tipo_pessoa = 'J'");
            stmt.setString(1, tfPesquisar.getText());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                codigoUsuario1.setText(rs.getString("id_usuario"));
                nomeEmpresa.setText(rs.getString("razao_social"));
                emailEmpresa.setText(rs.getString("email"));
                telefoneEmpresa.setText(rs.getString("telefone"));
                cnpj.setText(rs.getString("cpf_cnpj"));
                nomeFantasia.setText(rs.getString("nome"));
                dataFundacao.setText(rs.getString("data_fundacao"));
                estado1.setText(rs.getString("uf"));
                cep1.setText(rs.getString("cep"));
                cidade1.setText(rs.getString("cidade"));
                bairroEmpresa.setText(rs.getString("bairro"));
                endereco1.setText(rs.getString("endereco"));
                setorAtividade.setText(rs.getString("setor_atividade"));

                ImageIcon foto = new ImageIcon("imagens/usuarios/" + rs.getString("caminho_imagem"));
                perfil1.setIcon(redimensionamentoDeImagem(foto, 247, 227));

            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "<html> <h3> Não foi possível encontrar este nome</h3> </html>");

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tipoPessoa = new javax.swing.ButtonGroup();
        tfPesquisar = new javax.swing.JTextField();
        btnProcurar = new javax.swing.JButton();
        rdEmpresa = new javax.swing.JRadioButton();
        rdPessoa = new javax.swing.JRadioButton();
        JCard = new javax.swing.JPanel();
        painelPessoa = new javax.swing.JPanel();
        perfil = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        nome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        telefone = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        idade = new javax.swing.JTextField();
        dataNascimento = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        estado = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cidade = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        bairro = new javax.swing.JTextField();
        endereco = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        codigoUsuario = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        complemento = new javax.swing.JTextField();
        btComprasUsuario = new javax.swing.JButton();
        cpf = new javax.swing.JFormattedTextField();
        rg = new javax.swing.JFormattedTextField();
        cep = new javax.swing.JFormattedTextField();
        btCancelar1 = new javax.swing.JButton();
        painelEmpresa = new javax.swing.JPanel();
        perfil1 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        nomeEmpresa = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        emailEmpresa = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        telefoneEmpresa = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        estado1 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        cep1 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        cidade1 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        bairroEmpresa = new javax.swing.JTextField();
        endereco1 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        setorAtividade = new javax.swing.JTextField();
        btCompras = new javax.swing.JButton();
        nomeFantasia = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        inscricaoEstadual = new javax.swing.JFormattedTextField();
        codigoUsuario1 = new javax.swing.JFormattedTextField();
        dataFundacao = new javax.swing.JTextField();
        cnpj = new javax.swing.JTextField();
        btCancelar = new javax.swing.JButton();

        setClosable(true);
        setTitle("Pesquisar Usuários");

        tfPesquisar.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        tfPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfPesquisarKeyReleased(evt);
            }
        });

        btnProcurar.setBackground(new java.awt.Color(50, 205, 50));
        btnProcurar.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        btnProcurar.setForeground(new java.awt.Color(255, 255, 255));
        btnProcurar.setText("Procurar");
        btnProcurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcurarActionPerformed(evt);
            }
        });

        tipoPessoa.add(rdEmpresa);
        rdEmpresa.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rdEmpresa.setText("Empresa");
        rdEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdEmpresaActionPerformed(evt);
            }
        });

        tipoPessoa.add(rdPessoa);
        rdPessoa.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rdPessoa.setText("Pessoa");
        rdPessoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdPessoaActionPerformed(evt);
            }
        });

        JCard.setLayout(new java.awt.CardLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel1.setText("Nome");

        nome.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        nome.setSelectedTextColor(new java.awt.Color(51, 51, 51));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel2.setText("E-mail");

        email.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        email.setEnabled(false);
        email.setSelectedTextColor(new java.awt.Color(51, 51, 51));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel3.setText("Cadastro de Pessoa Física (CPF)");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel4.setText("Registro Geral (RG)");

        telefone.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        telefone.setSelectedTextColor(new java.awt.Color(51, 51, 51));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel5.setText("Telefone");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel6.setText("Idade");

        idade.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        idade.setSelectedTextColor(new java.awt.Color(51, 51, 51));

        dataNascimento.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        dataNascimento.setSelectedTextColor(new java.awt.Color(51, 51, 51));

        jLabel7.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel7.setText("Data de nascimento");

        jLabel8.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel8.setText("Estado");

        estado.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        estado.setSelectedTextColor(new java.awt.Color(51, 51, 51));

        jLabel9.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel9.setText("CEP");

        jLabel10.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel10.setText("Cidade");

        cidade.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        cidade.setSelectedTextColor(new java.awt.Color(51, 51, 51));

        jLabel11.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel11.setText("Bairro");

        bairro.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        bairro.setSelectedTextColor(new java.awt.Color(51, 51, 51));

        endereco.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        endereco.setSelectedTextColor(new java.awt.Color(51, 51, 51));

        jLabel12.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel12.setText("Endereço");

        jLabel13.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel13.setText("Código");

        codigoUsuario.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        codigoUsuario.setSelectedTextColor(new java.awt.Color(51, 51, 51));
        codigoUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigoUsuarioKeyPressed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel14.setText("Complemento");

        complemento.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        complemento.setSelectedTextColor(new java.awt.Color(51, 51, 51));

        btComprasUsuario.setBackground(new java.awt.Color(255, 165, 0));
        btComprasUsuario.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btComprasUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btComprasUsuario.setText("Compras");

        cpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        cpf.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        cpf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cpfKeyReleased(evt);
            }
        });

        rg.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        rg.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N

        try {
            cep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        cep.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N

        btCancelar1.setBackground(new java.awt.Color(255, 0, 0));
        btCancelar1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btCancelar1.setForeground(new java.awt.Color(255, 255, 255));
        btCancelar1.setText("Cancelar");
        btCancelar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btCancelar1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout painelPessoaLayout = new javax.swing.GroupLayout(painelPessoa);
        painelPessoa.setLayout(painelPessoaLayout);
        painelPessoaLayout.setHorizontalGroup(
            painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPessoaLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(painelPessoaLayout.createSequentialGroup()
                        .addComponent(btComprasUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btCancelar1))
                    .addGroup(painelPessoaLayout.createSequentialGroup()
                        .addComponent(perfil, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(painelPessoaLayout.createSequentialGroup()
                                .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(endereco, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelPessoaLayout.createSequentialGroup()
                                            .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel8)
                                                .addComponent(estado, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(painelPessoaLayout.createSequentialGroup()
                                                    .addComponent(jLabel9)
                                                    .addGap(195, 195, 195))
                                                .addGroup(painelPessoaLayout.createSequentialGroup()
                                                    .addComponent(cep)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                            .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel10)
                                                .addComponent(cidade, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bairro)
                                    .addComponent(complemento)
                                    .addGroup(painelPessoaLayout.createSequentialGroup()
                                        .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel14))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(painelPessoaLayout.createSequentialGroup()
                                .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2)
                                    .addComponent(email, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                                    .addComponent(cpf))
                                .addGap(18, 18, 18)
                                .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(painelPessoaLayout.createSequentialGroup()
                                        .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(idade, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(dataNascimento)))
                                    .addGroup(painelPessoaLayout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(rg)))
                            .addGroup(painelPessoaLayout.createSequentialGroup()
                                .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(codigoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nome, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(telefone, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel3))))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        painelPessoaLayout.setVerticalGroup(
            painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPessoaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelPessoaLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(estado, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelPessoaLayout.createSequentialGroup()
                        .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(painelPessoaLayout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(35, 35, 35))
                                .addComponent(telefone, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(painelPessoaLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nome, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(painelPessoaLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(codigoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelPessoaLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelPessoaLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(idade, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelPessoaLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cpf, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                            .addComponent(rg))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(painelPessoaLayout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addGap(35, 35, 35))
                                .addGroup(painelPessoaLayout.createSequentialGroup()
                                    .addComponent(jLabel10)
                                    .addGap(35, 35, 35)))
                            .addGroup(painelPessoaLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(bairro, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cidade, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cep)))))
                    .addComponent(perfil, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(painelPessoaLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(endereco, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painelPessoaLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(complemento, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(painelPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btComprasUsuario)
                    .addComponent(btCancelar1))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        JCard.add(painelPessoa, "painelPessoa");

        jLabel15.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel15.setText("Empresa");

        nomeEmpresa.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel16.setText("E-mail");

        emailEmpresa.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel17.setText("Cadastro Nacional de Pessoa Jurídica (CNPJ)");

        jLabel18.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel18.setText("Inscrição Estadual");

        telefoneEmpresa.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel19.setText("Telefone");

        jLabel21.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel21.setText("Data de fundação");

        jLabel22.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel22.setText("Estado");

        estado1.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N

        jLabel23.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel23.setText("CEP");

        cep1.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel24.setText("Cidade");

        cidade1.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N

        jLabel25.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel25.setText("Bairro");

        bairroEmpresa.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N

        endereco1.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel26.setText("Endereço");

        jLabel27.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel27.setText("Código");

        jLabel29.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel29.setText("Setor de atividade");

        setorAtividade.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N

        btCompras.setBackground(new java.awt.Color(255, 165, 0));
        btCompras.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btCompras.setText("Compras");

        nomeFantasia.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N

        jLabel30.setFont(new java.awt.Font("Arial", 0, 19)); // NOI18N
        jLabel30.setText("Nome fantasia");

        try {
            inscricaoEstadual.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        codigoUsuario1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        codigoUsuario1.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        codigoUsuario1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigoUsuario1KeyPressed(evt);
            }
        });

        dataFundacao.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N

        cnpj.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        cnpj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cnpjKeyPressed(evt);
            }
        });

        btCancelar.setBackground(new java.awt.Color(255, 0, 0));
        btCancelar.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btCancelar.setText("Cancelar");
        btCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btCancelarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout painelEmpresaLayout = new javax.swing.GroupLayout(painelEmpresa);
        painelEmpresa.setLayout(painelEmpresaLayout);
        painelEmpresaLayout.setHorizontalGroup(
            painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelEmpresaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(perfil1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelEmpresaLayout.createSequentialGroup()
                        .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelEmpresaLayout.createSequentialGroup()
                                .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(painelEmpresaLayout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addGap(67, 67, 67))
                                    .addGroup(painelEmpresaLayout.createSequentialGroup()
                                        .addComponent(cnpj)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addComponent(dataFundacao, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(painelEmpresaLayout.createSequentialGroup()
                                .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(setorAtividade, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(painelEmpresaLayout.createSequentialGroup()
                                        .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel29)
                                            .addComponent(jLabel26)
                                            .addComponent(endereco1, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(37, 37, 37)
                                        .addComponent(jLabel25)))
                                .addGap(0, 195, Short.MAX_VALUE)))
                        .addGap(16, 16, 16))
                    .addGroup(painelEmpresaLayout.createSequentialGroup()
                        .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btCancelar)
                            .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelEmpresaLayout.createSequentialGroup()
                                    .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(painelEmpresaLayout.createSequentialGroup()
                                            .addComponent(jLabel30)
                                            .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(nomeFantasia))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(emailEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel16)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelEmpresaLayout.createSequentialGroup()
                                    .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(codigoUsuario1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel27))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(nomeEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel15))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel19)
                                        .addComponent(telefoneEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(painelEmpresaLayout.createSequentialGroup()
                                    .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel22)
                                        .addComponent(estado1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(painelEmpresaLayout.createSequentialGroup()
                                            .addGap(30, 30, 30)
                                            .addComponent(jLabel23)
                                            .addGap(177, 177, 177))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelEmpresaLayout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(cep1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelEmpresaLayout.createSequentialGroup()
                                            .addGap(0, 0, Short.MAX_VALUE)
                                            .addComponent(bairroEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelEmpresaLayout.createSequentialGroup()
                                            .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(cidade1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel24))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel18)
                                                .addComponent(inscricaoEstadual, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addComponent(btCompras, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        painelEmpresaLayout.setVerticalGroup(
            painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelEmpresaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(painelEmpresaLayout.createSequentialGroup()
                        .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelEmpresaLayout.createSequentialGroup()
                                .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(painelEmpresaLayout.createSequentialGroup()
                                            .addComponent(jLabel19)
                                            .addGap(35, 35, 35))
                                        .addComponent(telefoneEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(painelEmpresaLayout.createSequentialGroup()
                                        .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel15)
                                            .addComponent(jLabel27))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(nomeEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(painelEmpresaLayout.createSequentialGroup()
                                        .addGap(29, 29, 29)
                                        .addComponent(codigoUsuario1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(6, 6, 6)
                                .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelEmpresaLayout.createSequentialGroup()
                                        .addComponent(jLabel30)
                                        .addGap(47, 47, 47))
                                    .addGroup(painelEmpresaLayout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(emailEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(nomeFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel21))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(dataFundacao, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(13, 13, 13)
                                .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(painelEmpresaLayout.createSequentialGroup()
                                        .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel22)
                                            .addComponent(jLabel23))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(cep1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(estado1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(painelEmpresaLayout.createSequentialGroup()
                                        .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel24)
                                            .addComponent(jLabel18))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(cidade1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(inscricaoEstadual, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(perfil1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(endereco1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bairroEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(painelEmpresaLayout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(35, 35, 35)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(btCompras))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(setorAtividade, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btCancelar))
                .addGap(1, 1, 1))
        );

        JCard.add(painelEmpresa, "painelEmpresa");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(JCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfPesquisar)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(rdPessoa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdEmpresa)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnProcurar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProcurar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdEmpresa)
                    .addComponent(rdPessoa))
                .addGap(18, 18, 18)
                .addComponent(JCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void rdEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdEmpresaActionPerformed
        card = (CardLayout) JCard.getLayout();
        card.show(JCard, "painelEmpresa");
        nomesUsuarios("J");
    }//GEN-LAST:event_rdEmpresaActionPerformed

    private void rdPessoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdPessoaActionPerformed
        card = (CardLayout) JCard.getLayout();
        card.show(JCard, "painelPessoa");
        nomesUsuarios("F");
    }//GEN-LAST:event_rdPessoaActionPerformed

    private void tfPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPesquisarKeyReleased
        pesquisarNome();
    }//GEN-LAST:event_tfPesquisarKeyReleased

    private void btnProcurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcurarActionPerformed

        codigoUsuario.setEnabled(false);
        email.setEnabled(false);
        cpf.setEnabled(false);
        codigoUsuario1.setEnabled(false);
        emailEmpresa.setEnabled(false);
        nomeFantasia.setEnabled(false);
        cnpj.setEnabled(false);
        if (rdPessoa.isSelected()) {
            pesquisarPessoa();
        } else {
            pesquisarEmpresa();
        }
        tfPesquisar.setText("");

    }//GEN-LAST:event_btnProcurarActionPerformed

    private void codigoUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoUsuarioKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            try {
                Connection con = Conexao.conexaoBanco();
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM dados_pesquisa WHERE id_usuario = ? AND tipo_pessoa = 'F'");
                stmt.setString(1, codigoUsuario.getText());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    codigoUsuario.setText(rs.getString("id_usuario"));
                    nome.setText(rs.getString("nome"));
                    email.setText(rs.getString("email"));
                    telefone.setText(rs.getString("telefone"));
                    cpf.setText(rs.getString("cpf_cnpj"));
                    rg.setText(rs.getString("rg"));
                    idade.setText(rs.getString("idade"));
                    estado.setText(rs.getString("uf"));
                    cep.setText(rs.getString("cep"));
                    cidade.setText(rs.getString("cidade"));
                    bairro.setText(rs.getString("bairro"));
                    endereco.setText(rs.getString("endereco"));
                    complemento.setText(rs.getString("complemento"));

                    ImageIcon foto = new ImageIcon("imagens/usuarios/" + rs.getString("caminho_imagem"));
                    perfil.setIcon(redimensionamentoDeImagem(foto, 205, 227));

                } else {
                    JOptionPane.showMessageDialog(null, "Código de usuário não encontrado");
                }
                rs.close();
                stmt.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "<html> <h3> Não foi possível encontrar este nome</h3> </html>");

            }
        }

    }//GEN-LAST:event_codigoUsuarioKeyPressed

    private void btCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btCancelarMouseClicked
      perfil1.setIcon(new ImageIcon("imagens/perfil.png"));
        perfil.setIcon(new ImageIcon("imagens/perfil.png"));
        usuarios = new ArrayList<>();
        

        if (rdEmpresa.isSelected()) {
            for (Component component : painelEmpresa.getComponents()) {
            if (component instanceof JTextField) {
                ((JTextField) component).setText("");
            }
        }
            nomesUsuarios("J");
        } else {
            for (Component component : painelPessoa.getComponents()) {
            if (component instanceof JTextField) {
                ((JTextField) component).setText("");
            }
            nomesUsuarios("F");
        }
        }

        desativarTextField(painelPessoa);
        desativarTextField(painelEmpresa);

    }//GEN-LAST:event_btCancelarMouseClicked

    private void btCancelar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btCancelar1MouseClicked
      perfil1.setIcon(new ImageIcon("imagens/perfil.png"));
        perfil.setIcon(new ImageIcon("imagens/perfil.png"));
        usuarios = new ArrayList<>();
        

        if (rdEmpresa.isSelected()) {
            for (Component component : painelEmpresa.getComponents()) {
            if (component instanceof JTextField) {
                ((JTextField) component).setText("");
            }
        }
            nomesUsuarios("J");
        } else {
            for (Component component : painelPessoa.getComponents()) {
            if (component instanceof JTextField) {
                ((JTextField) component).setText("");
            }
            nomesUsuarios("F");
        }
        }

        desativarTextField(painelPessoa);
        desativarTextField(painelEmpresa);
    }//GEN-LAST:event_btCancelar1MouseClicked

    private void cpfKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cpfKeyReleased
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            try {
                Connection con = Conexao.conexaoBanco();
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM dados_pesquisa WHERE cpf_cnpj = ? AND tipo_pessoa = 'F'");
                stmt.setString(1, cpf.getText());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    codigoUsuario.setText(rs.getString("id_usuario"));
                    nome.setText(rs.getString("nome"));
                    email.setText(rs.getString("email"));
                    telefone.setText(rs.getString("telefone"));
                    cpf.setText(rs.getString("cpf_cnpj"));
                    rg.setText(rs.getString("rg"));
                    idade.setText(rs.getString("idade"));
                    estado.setText(rs.getString("uf"));
                    cep.setText(rs.getString("cep"));
                    cidade.setText(rs.getString("cidade"));
                    bairro.setText(rs.getString("bairro"));
                    endereco.setText(rs.getString("endereco"));
                    complemento.setText(rs.getString("complemento"));

                    ImageIcon foto = new ImageIcon("imagens/usuarios/" + rs.getString("caminho_imagem"));
                    perfil.setIcon(redimensionamentoDeImagem(foto, 205, 227));

                } else {
                    JOptionPane.showMessageDialog(null, "CPF não encontrado");
                }
                rs.close();
                stmt.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "<html> <h3> Não foi possível encontrar este nome</h3> </html>");

            }}
    }//GEN-LAST:event_cpfKeyReleased

    private void cnpjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cnpjKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            try {
            Connection con = Conexao.conexaoBanco();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM dados_pesquisa WHERE cpf_cnpj = ? AND tipo_pessoa = 'J'");
            stmt.setString(1, cnpj.getText());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                codigoUsuario1.setText(rs.getString("id_usuario"));
                nomeEmpresa.setText(rs.getString("razao_social"));
                emailEmpresa.setText(rs.getString("email"));
                telefoneEmpresa.setText(rs.getString("telefone"));
                cnpj.setText(rs.getString("cpf_cnpj"));
                nomeFantasia.setText(rs.getString("nome"));
                dataFundacao.setText(rs.getString("data_fundacao"));
                estado1.setText(rs.getString("uf"));
                cep1.setText(rs.getString("cep"));
                cidade1.setText(rs.getString("cidade"));
                bairroEmpresa.setText(rs.getString("bairro"));
                endereco1.setText(rs.getString("endereco"));
                setorAtividade.setText(rs.getString("setor_atividade"));

                ImageIcon foto = new ImageIcon("imagens/usuarios/" + rs.getString("caminho_imagem"));
                perfil1.setIcon(redimensionamentoDeImagem(foto, 247, 227));

            }
            else{
                JOptionPane.showMessageDialog(null,"CNPJ não encontrado");
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "<html> <h3> Não foi possível encontrar este nome</h3> </html>");

        }
            }
    }//GEN-LAST:event_cnpjKeyPressed

    private void codigoUsuario1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoUsuario1KeyPressed
           if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            try {
            Connection con = Conexao.conexaoBanco();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM dados_pesquisa WHERE id_usuario = ? AND tipo_pessoa = 'J'");
            stmt.setString(1, codigoUsuario1.getText());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                codigoUsuario1.setText(rs.getString("id_usuario"));
                nomeEmpresa.setText(rs.getString("razao_social"));
                emailEmpresa.setText(rs.getString("email"));
                telefoneEmpresa.setText(rs.getString("telefone"));
                cnpj.setText(rs.getString("cpf_cnpj"));
                nomeFantasia.setText(rs.getString("nome"));
                dataFundacao.setText(rs.getString("data_fundacao"));
                estado1.setText(rs.getString("uf"));
                cep1.setText(rs.getString("cep"));
                cidade1.setText(rs.getString("cidade"));
                bairroEmpresa.setText(rs.getString("bairro"));
                endereco1.setText(rs.getString("endereco"));
                setorAtividade.setText(rs.getString("setor_atividade"));

                ImageIcon foto = new ImageIcon("imagens/usuarios/" + rs.getString("caminho_imagem"));
                perfil1.setIcon(redimensionamentoDeImagem(foto, 247, 227));

            }
            else{
                JOptionPane.showMessageDialog(null,"Código de usuário não encontrado");
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "<html> <h3> Não foi possível encontrar este nome</h3> </html>");

        }
            }
    }//GEN-LAST:event_codigoUsuario1KeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JCard;
    private javax.swing.JTextField bairro;
    private javax.swing.JTextField bairroEmpresa;
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btCancelar1;
    private javax.swing.JButton btCompras;
    private javax.swing.JButton btComprasUsuario;
    private javax.swing.JButton btnProcurar;
    private javax.swing.JFormattedTextField cep;
    private javax.swing.JTextField cep1;
    private javax.swing.JTextField cidade;
    private javax.swing.JTextField cidade1;
    private javax.swing.JTextField cnpj;
    private javax.swing.JTextField codigoUsuario;
    private javax.swing.JFormattedTextField codigoUsuario1;
    private javax.swing.JTextField complemento;
    private javax.swing.JFormattedTextField cpf;
    private javax.swing.JTextField dataFundacao;
    private javax.swing.JTextField dataNascimento;
    private javax.swing.JTextField email;
    private javax.swing.JTextField emailEmpresa;
    private javax.swing.JTextField endereco;
    private javax.swing.JTextField endereco1;
    private javax.swing.JTextField estado;
    private javax.swing.JTextField estado1;
    private javax.swing.JTextField idade;
    private javax.swing.JFormattedTextField inscricaoEstadual;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField nome;
    private javax.swing.JTextField nomeEmpresa;
    private javax.swing.JTextField nomeFantasia;
    private javax.swing.JPanel painelEmpresa;
    private javax.swing.JPanel painelPessoa;
    private javax.swing.JLabel perfil;
    private javax.swing.JLabel perfil1;
    private javax.swing.JRadioButton rdEmpresa;
    private javax.swing.JRadioButton rdPessoa;
    private javax.swing.JFormattedTextField rg;
    private javax.swing.JTextField setorAtividade;
    private javax.swing.JTextField telefone;
    private javax.swing.JTextField telefoneEmpresa;
    private javax.swing.JTextField tfPesquisar;
    private javax.swing.ButtonGroup tipoPessoa;
    // End of variables declaration//GEN-END:variables
}
