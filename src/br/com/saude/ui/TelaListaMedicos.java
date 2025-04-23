package br.com.saude.ui;

import br.com.saude.dao.UsuarioDAO;
import br.com.saude.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListaMedicos extends JFrame {
    private final TelaInicialAdmin telaAnterior;

    public TelaListaMedicos(TelaInicialAdmin telaAnterior) {
        this.telaAnterior = telaAnterior;

        setTitle("Lista de Médicos");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Médicos Cadastrados", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome", "Email"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(model);

        List<Usuario> medicos = new UsuarioDAO().listarPorTipo("medico");
        for (Usuario m : medicos) {
            model.addRow(new Object[]{m.getId(), m.getNome(), m.getEmail()});
        }

        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaInicialAdmin(telaAnterior.getAdmin()); // ← aqui está a correção
        });

        JPanel rodape = new JPanel();
        rodape.add(btnVoltar);
        add(rodape, BorderLayout.SOUTH);

        setVisible(true);
    }
}
