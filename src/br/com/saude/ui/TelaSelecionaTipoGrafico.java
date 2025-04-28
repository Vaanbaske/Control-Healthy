package br.com.saude.ui;

import br.com.saude.dao.PressaoDAO;
import br.com.saude.model.Paciente;
import br.com.saude.model.Usuario;
import br.com.saude.old.TelaGraficoMenu;

import javax.swing.*;
import java.awt.*;

public class TelaSelecionaTipoGrafico extends JFrame {
    private Paciente paciente;

    // Construtor que aceita Paciente
    
    public TelaSelecionaTipoGrafico(Paciente paciente) {
        this.paciente = paciente;
        initComponents();
    }

    // Construtor que aceita Usuario (para admin, por exemplo)
    // NOVO CONSTRUTOR que aceita Usuario
    public TelaSelecionaTipoGrafico(Usuario usuario) {
        this.paciente = new Paciente();
        paciente.setId(usuario.getId());
        paciente.setNome(usuario.getNome());
        // Caso deseje adicionar mais atributos do usuário
        // preencha mais dados se necessário
        initComponents();
    }

    private void initComponents() {
        setTitle("Escolher Tipo de Gráfico");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        JLabel titulo = new JLabel("Escolha o Tipo de Gráfico", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(titulo);

        JButton btnDiario = new JButton("Gráfico Diário");
        JButton btnSemanal = new JButton("Gráfico Semanal");
        JButton btnMensal = new JButton("Gráfico Mensal");
        JButton btnVoltar = new JButton("← Voltar");

        btnDiario.addActionListener(e -> {
            dispose();
            new TelaGraficoDiario(paciente);
            new TelaGraficoDiario(paciente); // precisa estar implementada
        });

        btnSemanal.addActionListener(e -> {
            dispose();
            new TelaGraficoSemanal(paciente);
        });

        btnMensal.addActionListener(e -> {
            dispose();
            new TelaGraficoMensal(paciente);
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaGraficoMenu(paciente); // ou pode voltar para alguma tela anterior
        });

        add(btnDiario);
        add(btnSemanal);
        add(btnMensal);
        add(btnVoltar);

        setVisible(true);
    }
}
