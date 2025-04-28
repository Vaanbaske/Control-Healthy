package br.com.saude.ui;

import br.com.saude.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaInicialAdmin extends JFrame {
    private static final long serialVersionUID = 1L;
    private final Usuario admin;

    public TelaInicialAdmin(Usuario admin) {
        this.admin = admin;

        setTitle("Painel do Administrador: " + admin.getNome());
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Painel do Administrador", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        JPanel painelCentral = new JPanel(new GridLayout(7, 1, 10, 10));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton btnListarMedicos       = new JButton("Listar Médicos");
        JButton btnListarPacientes     = new JButton("Listar Pacientes");
        JButton btnAtribuirPaciente    = new JButton("Atribuir Paciente a Médico");
        JButton btnCadastrarUsuario    = new JButton("Cadastrar Novo Usuário");
        JButton btnGerarGrafico        = new JButton("Gerar Gráfico de Paciente");
        JButton btnInserirPressao      = new JButton("Inserir Dados de Pressão");
        JButton btnSair                = new JButton("Sair");
        JButton btnVoltarMenu = new JButton("Menu Inicial");

        btnVoltarMenu.addActionListener(e -> {
            dispose();
            new TelaEscolhaLogin();
        });
        painelCentral.add(btnVoltarMenu);


        btnListarMedicos.addActionListener(e -> {
            dispose();
            new TelaListaMedicos(this);
        });
        btnListarPacientes.addActionListener(e -> {
            dispose();
            new TelaListaPacientes(this);
        });
        btnAtribuirPaciente.addActionListener(e -> {
            dispose();
            new TelaAtribuirPaciente(this);
        });
        btnCadastrarUsuario.addActionListener(e -> {
            dispose();
            new TelaCadastroUsuarioAdmin(this);
        });
        btnGerarGrafico.addActionListener(e -> {
            dispose();
            // passa o próprio admin para TelaSelecionaTipoGrafico
            new TelaSelecionaTipoGrafico(admin);
        });
        btnInserirPressao.addActionListener(e -> {
            dispose();
            new TelaInserirPressaoAdmin(this);
        });
        btnSair.addActionListener(e -> {
            dispose();
            new TelaEscolhaLogin();
        });

        painelCentral.add(btnListarMedicos);
        painelCentral.add(btnListarPacientes);
        painelCentral.add(btnAtribuirPaciente);
        painelCentral.add(btnCadastrarUsuario);
        painelCentral.add(btnGerarGrafico);
        painelCentral.add(btnInserirPressao);
        painelCentral.add(btnSair);

        add(painelCentral, BorderLayout.CENTER);
        setVisible(true);
    }

    /** Getter para acesso ao admin por outras telas */
    public Usuario getAdmin() {
        return admin;
    }
}
