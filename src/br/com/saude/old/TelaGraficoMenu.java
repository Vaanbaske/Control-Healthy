package br.com.saude.old;

import br.com.saude.model.Paciente;
import br.com.saude.ui.TelaSelecionaTipoGrafico;

import javax.swing.*;
import java.awt.*;

public class TelaGraficoMenu extends JFrame {
    private Paciente paciente;

    public TelaGraficoMenu(Paciente paciente) {
        this.paciente = paciente;

        setTitle("Gráficos de Pressão Arterial");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Visualizar Gráficos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        JButton botaoGerar = new JButton("Gerar Gráfico");
        botaoGerar.addActionListener(e -> {
            dispose(); // fecha essa janela
            new TelaSelecionaTipoGrafico(paciente); // abre próxima
        });

        JPanel painel = new JPanel();
        painel.add(botaoGerar);
        add(painel, BorderLayout.CENTER);

        setVisible(true);
    }
}
