package br.com.saude.ui;

import br.com.saude.dao.UsuarioDAO;
import br.com.saude.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListaMedicos extends JFrame {
<<<<<<< HEAD
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
=======
    private JTable tabela;
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public TelaListaMedicos(JFrame telaAnterior) {
        setTitle("Lista de Médicos");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Lista de Médicos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        tabela = new JTable();
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);
        preencherTabela();

        JPanel painelBotoes = new JPanel(new FlowLayout());
>>>>>>> 733c997 (Telas finais adicionadas)

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            dispose();
<<<<<<< HEAD
            new TelaInicialAdmin(telaAnterior.getAdmin()); // ← aqui está a correção
        });

        JPanel rodape = new JPanel();
        rodape.add(btnVoltar);
        add(rodape, BorderLayout.SOUTH);

        setVisible(true);
    }
=======
            telaAnterior.setVisible(true);
        });

        painelBotoes.add(btnVoltar);
        add(painelBotoes, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void preencherTabela() {
        String[] colunas = {"ID", "Nome", "Email"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        List<Usuario> medicos = usuarioDAO.listarPorTipo("medico");
        for (Usuario u : medicos) {
            model.addRow(new Object[]{
                u.getId(),
                u.getNome(),
                u.getEmail()
            });
        }

        tabela.setModel(model);
    }
>>>>>>> 733c997 (Telas finais adicionadas)
}
