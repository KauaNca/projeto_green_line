
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TelaInicial extends JFrame {

    JButton cadastro = new JButton("Cadastro");
    JButton relatorio = new JButton("Relatório");
    JButton editar = new JButton("Editar");
    JButton financeiro = new JButton("Financeiro");
    JButton notificacao = new JButton("Notificações");
    JPanel menu = new JPanel();
    Font fonte = new Font("Arial", Font.ROMAN_BASELINE, 32);
    Dimension dimensoes = new Dimension(205, 85);

    public TelaInicial() {
        Botoes();
        Menu();
        Frame();
    }

    public void Botoes() {
    cadastro.setPreferredSize(dimensoes);
    cadastro.setBackground(Color.black);
    cadastro.setForeground(Color.white);
    cadastro.setFont(fonte);
    cadastro.setBorderPainted(false);
    cadastro.setContentAreaFilled(false);
    cadastro.setFocusPainted(false);
    
    relatorio.setPreferredSize(dimensoes);
    relatorio.setBackground(Color.black);
    relatorio.setForeground(Color.white);
    relatorio.setFont(fonte);
    relatorio.setBorderPainted(false);
    relatorio.setContentAreaFilled(false);
    relatorio.setFocusPainted(false);

    editar.setPreferredSize(dimensoes);
    editar.setBackground(Color.black);
    editar.setForeground(Color.white);
    editar.setFont(fonte);
    editar.setBorderPainted(false);
    editar.setContentAreaFilled(false);
    editar.setFocusPainted(false);

    financeiro.setPreferredSize(dimensoes);
    financeiro.setBackground(Color.black);
    financeiro.setForeground(Color.white);
    financeiro.setFont(fonte);
    financeiro.setBorderPainted(false);
    financeiro.setContentAreaFilled(false);
    financeiro.setFocusPainted(false);

    notificacao.setPreferredSize(dimensoes);
    notificacao.setBackground(Color.black);
    notificacao.setForeground(Color.white);
    notificacao.setFont(fonte);
    notificacao.setBorderPainted(false);
    notificacao.setContentAreaFilled(false);
    notificacao.setFocusPainted(false);
    
    menu.add(cadastro);
    menu.add(relatorio);
    menu.add(editar);
    menu.add(financeiro);
    menu.add(notificacao);
}


    public void Menu() {
        menu.setPreferredSize(new Dimension(1728, 98));
        menu.setBackground(Color.black);
        menu.setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    public void Frame() {
        setSize(1728, 1117);
        setLayout(new FlowLayout(FlowLayout.LEADING));
        add(menu);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaInicial();
    }
}
