package br.com.saude.ui;

import br.com.saude.dao.PacienteDAO;
import br.com.saude.dao.UsuarioDAO;
import br.com.saude.model.Paciente;
import br.com.saude.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaAtribuirPaciente extends JFrame {
    private static final long serialVersionUID = 1L;
    private final TelaInicialAdmin telaAnterior;

    public TelaAtribuirPaciente(TelaInicialAdmin telaAnterior) {
        this.telaAnterior = telaAnterior;

        setTitle("Atribuir Paciente a Médico");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Atribuir Paciente a Médico", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel painelCentral = new JPanel(new GridLayout(4, 1, 10, 10));
        JComboBox<Paciente> cbPacientes = new JComboBox<>();
        JComboBox<Usuario> cbMedicos   = new JComboBox<>();

        // Carrega os dados
        List<Paciente> pacientes = new PacienteDAO().listarSemMedico();
        List<Usuario> medicos     = new UsuarioDAO().listarPorTipo("medico");

        for (Paciente p : pacientes) cbPacientes.addItem(p);
        for (Usuario m   : medicos)   cbMedicos.addItem(m);

        painelCentral.add(new JLabel("Paciente sem médico:"));
        painelCentral.add(cbPacientes);
        painelCentral.add(new JLabel("Médicos disponíveis:"));
        painelCentral.add(cbMedicos);

        add(painelCentral, BorderLayout.CENTER);

        JButton btnAtribuir = new JButton("Atribuir");
        btnAtribuir.addActionListener(e -> {
            Paciente pacienteSelecionado = (Paciente) cbPacientes.getSelectedItem();
            Usuario  medicoSelecionado   = (Usuario)  cbMedicos.getSelectedItem();

            if (pacienteSelecionado != null && medicoSelecionado != null) {
                boolean sucesso = new PacienteDAO()
                    .atribuirMedico(pacienteSelecionado.getId(), medicoSelecionado.getId());
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Paciente atribuído com sucesso!");
                    dispose();
                    // Usa o getter para recuperar o admin
                    new TelaInicialAdmin(telaAnterior.getAdmin());
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao atribuir paciente!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaInicialAdmin(telaAnterior.getAdmin());
        });

        JPanel rodape = new JPanel();
        rodape.add(btnAtribuir);
        rodape.add(btnVoltar);
        add(rodape, BorderLayout.SOUTH);

        setVisible(true);
    }
}
