package br.com.saude.ui;

import javax.swing.*;
import br.com.saude.dao.PacienteDAO;
import br.com.saude.model.Paciente;
import java.awt.GridLayout;

public class TelaCadastroPaciente extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField campoNome, campoIdade, campoEndereco, campoTelefone;
    private JButton botaoSalvar;

    public TelaCadastroPaciente() {
        setTitle("Cadastro de Paciente");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));
        setLocationRelativeTo(null);

        campoNome     = new JTextField();
        campoIdade    = new JTextField();
        campoEndereco = new JTextField();
        campoTelefone = new JTextField();
        botaoSalvar   = new JButton("Salvar");

        add(new JLabel("Nome:"));     add(campoNome);
        add(new JLabel("Idade:"));    add(campoIdade);
        add(new JLabel("Endereço:")); add(campoEndereco);
        add(new JLabel("Telefone:")); add(campoTelefone);
        add(new JLabel(""));          add(botaoSalvar);

        botaoSalvar.addActionListener(e -> salvarPaciente());

        setVisible(true);
    }

    private void salvarPaciente() {
        try {
            String nome     = campoNome.getText();
            int idade       = Integer.parseInt(campoIdade.getText());
            String endereco = campoEndereco.getText();
            String telefone = campoTelefone.getText();

            Paciente paciente = new Paciente(0, nome, idade, endereco, telefone);
            new PacienteDAO().inserirPaciente(paciente);

            JOptionPane.showMessageDialog(this, "Paciente cadastrado com sucesso!");
            campoNome.setText("");
            campoIdade.setText("");
            campoEndereco.setText("");
            campoTelefone.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new TelaCadastroPaciente();
    }
}
