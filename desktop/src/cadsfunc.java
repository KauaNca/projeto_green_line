
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author kauan
 */
public class cadsfunc extends javax.swing.JInternalFrame {

    TelaInicial tela;
    private String codigoUsuario;
    private int contagem = 0;
    private String caminhoImagem;
    private String[] dadosBanco;
    private String[] novosDados;
    private String arquivoEscolhido;

    public cadsfunc(String codigo) {
        initComponents();
        this.codigoUsuario = codigo;
        initUI();
        recuperarConta(codigo);
    }

    private void initUI() {
        // Itera sobre os componentes do painel e desabilita os campos de texto
        for (Component component : getContentPane().getComponents()) {
            if (component instanceof JTextField) {
                ((JTextField) component).setEnabled(false);
            }
        }
        btSelecionar.setVisible(false);
        btSalvar.setVisible(false);
        btCancelar.setVisible(false);
        
    }

    public void recuperarConta(String bancoCodigo) {
        System.out.println("COMECOU");
        try (Connection con = Conexao.conexaoBanco()) {
            System.out.println("Conexao estabelecida");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM dados_pessoais WHERE id_pessoa = ?");
            stmt.setString(1, bancoCodigo);
            System.out.println("PreparedStatement configurado");
            ResultSet rs = stmt.executeQuery();
            System.out.println("Query executada");

            if (rs.next()) {
                System.out.println("Dados encontrados");
                dadosBanco = new String[]{
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("telefone"),
                    rs.getString("cpf_cnpj"),
                    rs.getString("rg"),
                    rs.getString("idade"),
                    rs.getString("cep"),
                    rs.getString("uf"),
                    rs.getString("cidade"),
                    rs.getString("bairro"),
                    rs.getString("endereco"),
                    rs.getString("complemento")};

                usuario.setText(Objects.toString(rs.getString("nome"), ""));
                System.out.println("Nome preenchido");
                codigo.setText(Objects.toString(rs.getString("id_usuario"), ""));
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
                cep.setText(Objects.toString(rs.getString("cep"), ""));

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

                // Verificar se a coluna caminho_imagem existe e não é nula
                caminhoImagem = rs.getString("caminho_imagem");
                if (caminhoImagem != null) {
                    System.out.println("Imagem: " + caminhoImagem);
                    ImageIcon imagemUsuario = new ImageIcon("imagens/usuarios/" + caminhoImagem);
                    if (imagemUsuario.getIconWidth() == -1) {
                        System.out.println("Imagem não encontrada");
                    } else {
                        imagem.setIcon(redimensionamentoDeImagem(imagemUsuario, 204, 227));
                    }
                } else {
                    System.out.println("caminho_imagem é nulo ou não existe.");
                }

            } else {
                System.out.println("Nenhum dado encontrado para o código fornecido");
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Registre o erro para depuração
        }
    }

    public ImageIcon redimensionamentoDeImagem(ImageIcon imagem, int largura, int altura) {
        Image pegarImagem = imagem.getImage();
        Image redimensionando = pegarImagem.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        ImageIcon imagemRedimensionada = new ImageIcon(redimensionando);
        return imagemRedimensionada;
    }

    public void SelecionarImagens() {
        File arquivo;

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
            arquivo = selecionar.getSelectedFile(); //Pega o endereço do arquivo, é possível manipular.
            arquivoEscolhido = arquivo.getName();

            imagem.setIcon(redimensionamentoDeImagem(new ImageIcon("imagens/usuarios/" + arquivoEscolhido), 202, 227));
        }
    }

    private boolean novosDados() {
         novosDados = new String[]{
            usuario.getText(),
            email.getText(),
            telefone.getText(),
            cpf.getText(),
            rg.getText(),
            idade.getText(),
            cep.getText(),
            uf.getSelectedItem().toString(),
            cidade.getText(),
            bairro.getText(),
            endereco.getText(),
            complemento.getText()
        };
       for(int x = 0; x < novosDados.length;x++){
         System.out.println(novosDados[x] + " ");
     }

        for (int x = 0; x < novosDados.length; x++) {
            if (!Objects.equals(dadosBanco[x], novosDados[x])) {
                System.out.println("Houve mudanças");
                return true;
            }
        }
        System.out.println("Nenhuma mudança foi detectada");
        return false;
    }

    private void atualizarDados() throws SQLException {
        Connection con = null;

        try {
            System.out.println("[DEBUG] Iniciando conexão com o banco de dados...");
            con = Conexao.conexaoBanco(); // Obtém a conexão
            con.setAutoCommit(false); // Inicia uma transação
            System.out.println("[DEBUG] Conexão estabelecida e transação iniciada.");

            // Primeiro SELECT para buscar o ID da pessoa
            String idPessoa = "";
            String sql = "SELECT id_pessoa FROM pessoa WHERE cpf_cnpj = ?";
            PreparedStatement stmt1 = con.prepareStatement(sql);
            stmt1.setString(1, cpf.getText());
            System.out.println("[DEBUG] Executando consulta para buscar id_pessoa...");
            ResultSet rs = stmt1.executeQuery();

            if (rs.next()) {
                idPessoa = rs.getString("id_pessoa");
                System.out.println("[DEBUG] ID da pessoa encontrada: " + idPessoa);
            } else {
                System.out.println("[DEBUG] Pessoa não encontrada. Realizando rollback...");
                con.rollback(); // Reverte a transação
                return; // Sai do método
            }

            // Atualiza dados na tabela "pessoa"
            System.out.println("[DEBUG] Atualizando dados na tabela 'pessoa'...");
            String SQL = "UPDATE pessoa SET nome = ?, email = ?, telefone = ?, cpf_cnpj = ?, rg = ?, idade = ? WHERE id_pessoa = ?";
            PreparedStatement stmt2 = con.prepareStatement(SQL);
            stmt2.setString(1, usuario.getText());
            stmt2.setString(2, email.getText());
            stmt2.setString(3, telefone.getText());
            stmt2.setString(4, cpf.getText());
            stmt2.setString(5, rg.getText());
            stmt2.setString(6, idade.getText());
            stmt2.setString(7, idPessoa);
            stmt2.execute();
            System.out.println("[DEBUG] Atualização na tabela 'pessoa' concluída.");

            // Atualiza dados na tabela "endereco"
            System.out.println("[DEBUG] Atualizando dados na tabela 'endereco'...");
            String SQL2 = "UPDATE enderecos SET uf = ?, cep = ?, cidade = ?, bairro = ?, endereco = ?, complemento = ? WHERE id_pessoa = ?";
            PreparedStatement stmt3 = con.prepareStatement(SQL2);
            stmt3.setString(1, uf.getSelectedItem().toString());
            stmt3.setString(2, cep.getText());
            stmt3.setString(3, cidade.getText());
            stmt3.setString(4, bairro.getText());
            stmt3.setString(5, endereco.getText());
            stmt3.setString(6, complemento.getText());
            stmt3.setString(7, idPessoa);
            stmt3.execute();
            System.out.println("[DEBUG] Atualização na tabela 'endereco' concluída.");

            // Atualiza dados na tabela "imagensUsuarios", se necessário
            if (!caminhoImagem.equals(arquivoEscolhido)) {
                System.out.println("[DEBUG] Atualizando dados na tabela 'imagensUsuarios'...");
                String SQL3 = "UPDATE ImagensUsuarios SET caminho_imagem = ? WHERE id_usuario = ?";
                PreparedStatement stmt4 = con.prepareStatement(SQL3);
                stmt4.setString(1, caminhoImagem);
                stmt4.setString(2, codigo.getText());
                stmt4.execute();
                System.out.println("[DEBUG] Atualização na tabela 'imagensUsuarios' concluída.");
            } else {
                System.out.println("[DEBUG] Atualizando dados na tabela 'imagensUsuarios'...");
                String SQL3 = "UPDATE ImagensUsuarios SET caminho_imagem = ? WHERE id_usuario = ?";
                PreparedStatement stmt4 = con.prepareStatement(SQL3);
                stmt4.setString(1, arquivoEscolhido);
                stmt4.setString(2, codigo.getText());
                stmt4.execute();
                System.out.println("[DEBUG] Atualização na tabela 'imagensUsuarios' concluída.");
            }

            con.commit(); // Confirma as alterações no banco de dados
            System.out.println("[DEBUG] Alterações confirmadas no banco de dados. Transação concluída com sucesso.");

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Reverte as alterações em caso de erro
                    System.out.println("[DEBUG] Rollback realizado devido a erro.");
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                    System.out.println("[DEBUG] Erro ao executar rollback.");
                }
            }
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close(); // Fecha a conexão no final
                    System.out.println("[DEBUG] Conexão com o banco de dados encerrada.");
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                    System.out.println("[DEBUG] Erro ao fechar a conexão.");
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imagem = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        usuario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        codigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        idade = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        telefone = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cpf = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        rg = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        uf = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cidade = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        bairro = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        endereco = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        complemento = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        senha = new javax.swing.JTextField();
        btModificar = new javax.swing.JButton();
        btSalvar = new javax.swing.JButton();
        btSelecionar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        cep = new javax.swing.JTextField();
        btCancelar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setTitle("Seus dados");

        jLabel1.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel1.setText("Nome");

        usuario.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        usuario.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel2.setText("Usuário");

        codigo.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        codigo.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel3.setText("Idade");

        idade.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        idade.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel4.setText("Email");

        email.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        email.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel5.setText("Telefone");

        telefone.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        telefone.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel6.setText("Cadastro de Pessoa Física (CPF)");

        cpf.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        cpf.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel7.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel7.setText("Registro Geral (RG)");

        rg.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        rg.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel8.setText("UF");

        uf.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        uf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO" }));

        jLabel9.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel9.setText("Cidade");

        cidade.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        cidade.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel10.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel10.setText("Bairro");

        bairro.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        bairro.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel11.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel11.setText("Endereço");

        endereco.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        endereco.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel12.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel12.setText("Complemento");

        complemento.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        complemento.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel13.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel13.setText("Senha");

        senha.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        senha.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        btModificar.setBackground(new java.awt.Color(255, 102, 0));
        btModificar.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        btModificar.setForeground(new java.awt.Color(255, 255, 255));
        btModificar.setText("Modificar");
        btModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btModificarActionPerformed(evt);
            }
        });

        btSalvar.setBackground(new java.awt.Color(0, 204, 51));
        btSalvar.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        btSalvar.setForeground(new java.awt.Color(255, 255, 255));
        btSalvar.setText("Salvar");
        btSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarActionPerformed(evt);
            }
        });

        btSelecionar.setBackground(new java.awt.Color(51, 51, 255));
        btSelecionar.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btSelecionar.setForeground(new java.awt.Color(255, 255, 255));
        btSelecionar.setText("Selecionar foto");
        btSelecionar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btSelecionarMouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel14.setText("CEP");

        cep.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        cep.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        btCancelar.setBackground(new java.awt.Color(255, 0, 51));
        btCancelar.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        btCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btCancelar.setText("Cancelar");
        btCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btSelecionar, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imagem, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addComponent(cpf, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(email, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(endereco)
                    .addComponent(senha)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(uf, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel14)
                            .addComponent(cep, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(cidade)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(idade, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(telefone, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(rg, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(bairro, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(complemento, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(51, 51, 51))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(imagem, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cpf, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btSelecionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(idade, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(telefone, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rg, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bairro, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cep, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(endereco, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(complemento, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(uf, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cidade, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(senha, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btModificarActionPerformed
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente alterar seus dados?", "Confirmação", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (resposta == JOptionPane.OK_OPTION) {
            for (Component component : getContentPane().getComponents()) {
                if (component instanceof JTextField) {
                    ((JTextField) component).setEnabled(true);
                }
                codigo.setEnabled(false);
                btCancelar.setVisible(true);
                btSelecionar.setVisible(true);
                btSalvar.setVisible(true);
                btModificar.setVisible(false);
            }
        }

    }//GEN-LAST:event_btModificarActionPerformed

    private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
        initUI();
        btModificar.setVisible(true);
        imagem.setIcon(new ImageIcon("imagens/usuarios/" + caminhoImagem));
    }//GEN-LAST:event_btCancelarActionPerformed

    private void btSelecionarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btSelecionarMouseClicked
        SelecionarImagens();
    }//GEN-LAST:event_btSelecionarMouseClicked

    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
        if (novosDados()) {
            System.out.println("Novos dados passou");
            try {
                atualizarDados();
                System.out.println("Passou do atualizarDados");
                initUI();
                System.out.println("Passou do initUI");
                btModificar.setVisible(true);
                recuperarConta(codigoUsuario);
            } catch (SQLException ex) {
                System.out.println("ERRO AO ATUALIZAR");
                dispose();
            }
        }
    }//GEN-LAST:event_btSalvarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bairro;
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btModificar;
    private javax.swing.JButton btSalvar;
    private javax.swing.JButton btSelecionar;
    private javax.swing.JTextField cep;
    private javax.swing.JTextField cidade;
    private javax.swing.JTextField codigo;
    private javax.swing.JTextField complemento;
    private javax.swing.JTextField cpf;
    private javax.swing.JTextField email;
    private javax.swing.JTextField endereco;
    private javax.swing.JTextField idade;
    private javax.swing.JLabel imagem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField rg;
    private javax.swing.JTextField senha;
    private javax.swing.JTextField telefone;
    private javax.swing.JComboBox<String> uf;
    private javax.swing.JTextField usuario;
    // End of variables declaration//GEN-END:variables
}
