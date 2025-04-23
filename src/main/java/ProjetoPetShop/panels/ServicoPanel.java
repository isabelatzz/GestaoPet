package ProjetoPetShop.panels;

import ProjetoPetShop.controller.ServicoController;
import ProjetoPetShop.entities.Tamanho;
import ProjetoPetShop.theme.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServicoPanel extends JPanel {
    private final ServicoController controller;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JComboBox<String> tipoServicoCombo;
    private final JComboBox<Tamanho> tamanhoCombo;
    private final JTextField idAnimalField;
    private final JTextField valorField;
    private final JTextField vetField;
    private final JCheckBox acessoriosCheck;
    private final JTextField valorAcessoriosField;
    private final JCheckBox pagoCheckBox;
    private final JTextArea descricaoArea;
    private final JButton gerarReciboBtn;

    public ServicoPanel(ServicoController controller) {
        this.controller = controller;

        // Inicialização dos componentes
        this.tableModel = new DefaultTableModel(
                new String[]{"ID", "Tipo", "Animal", "Valor", "Data", "Status"}, 0);
        this.table = new JTable(tableModel);
        this.tipoServicoCombo = new JComboBox<>(new String[]{"Banho", "Tosa", "Banho e Tosa", "Consulta"});
        this.tamanhoCombo = new JComboBox<>(Tamanho.values());
        this.idAnimalField = new JTextField(15);
        this.valorField = new JTextField(15);
        this.vetField = new JTextField(15);
        this.acessoriosCheck = new JCheckBox("Adicionar acessórios?");
        this.valorAcessoriosField = new JTextField("0.0", 15);
        this.pagoCheckBox = new JCheckBox("Pago?");
        this.descricaoArea = new JTextArea(3, 20);
        this.gerarReciboBtn = new JButton("Gerar Recibo PDF");

        configurarUI();
    }

    private void configurarUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Theme.BACKGROUND_COLOR);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Configuração da tabela
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);

        // Painel de formulário
        JPanel formPanel = criarFormPanel();

        // Layout principal
        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Configura listeners
        configurarListeners();

        // Atualiza dados iniciais
        atualizarTabela();
    }

    private JPanel criarFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());

        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Theme.SECONDARY_COLOR, 1),
                "Agendamentos de Serviços",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                Theme.PRIMARY_COLOR
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Adicionando componentes
        adicionarComponente(formPanel, gbc, 0, "ID Animal:", idAnimalField);
        adicionarComponente(formPanel, gbc, 1, "Tipo Serviço:", tipoServicoCombo);
        adicionarComponente(formPanel, gbc, 2, "Valor Base:", valorField);
        adicionarComponente(formPanel, gbc, 3, "Veterinário:", vetField);
        adicionarComponente(formPanel, gbc, 4, "Tamanho:", tamanhoCombo);

        // Acessórios
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(acessoriosCheck, gbc);
        gbc.gridx = 1;
        formPanel.add(valorAcessoriosField, gbc);

        // Descrição
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(pagoCheckBox,gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(descricaoArea), gbc);


        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE); // ou Theme.BACKGROUND_COLOR, se quiser manter o mesmo fundo
        buttonPanel.add(gerarReciboBtn);

        JButton agendarBtn = new JButton("Agendar Serviço");
        agendarBtn.addActionListener(e -> {
            agendarServico();
            this.controller.salvarDados();
        });
        buttonPanel.add(agendarBtn);

        gbc.gridx = 1; gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.EAST;
        setBackground(Theme.BACKGROUND_COLOR);
        formPanel.add(buttonPanel, gbc);


        return formPanel;
    }

    private void adicionarComponente(JPanel panel, GridBagConstraints gbc, int linha, String label, JComponent componente) {
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(componente, gbc);
    }

    private void configurarListeners() {
        acessoriosCheck.addActionListener(e -> {
            valorAcessoriosField.setEnabled(acessoriosCheck.isSelected());
            if (!acessoriosCheck.isSelected()) {
                valorAcessoriosField.setText("0.0");
            }
        });

        gerarReciboBtn.addActionListener(e -> gerarReciboPDF());
    }

    private void agendarServico() {
        try {
            controller.agendarServico(
                    Integer.parseInt(idAnimalField.getText()),
                    tipoServicoCombo.getSelectedItem().toString(),
                    LocalDateTime.now(),
                    Double.parseDouble(valorField.getText()),
                    vetField.getText(),
                    acessoriosCheck.isSelected(),
                    Double.parseDouble(valorAcessoriosField.getText()),
                    descricaoArea.getText(),
                    pagoCheckBox.isSelected(),
                    (Tamanho) tamanhoCombo.getSelectedItem()
            );
            JOptionPane.showMessageDialog(this, "Serviço agendado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            atualizarTabela();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valores numéricos inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void gerarReciboPDF() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um serviço na tabela para gerar o recibo",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idServico = (int) tableModel.getValueAt(selectedRow, 0);
        Path pdfPath = Paths.get(
                System.getProperty("user.dir"),
                "recibos",
                "recibo_" + idServico + "_" +
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
                        + ".pdf"
        );

        // 1) Tenta gerar o PDF e, se der problema, mostra um alerta e sai
        try {
            controller.gerarReciboPDF(idServico);
        } catch (Exception genEx) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao gerar recibo: " + genEx.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2) Tenta abrir o PDF no visualizador padrão — se falhar aqui, só loga no console
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(pdfPath.toFile());
            } catch (IOException openEx) {
                openEx.printStackTrace(); // sem diálogo para o usuário
            }
        }
    }


    private void limparCampos() {
        idAnimalField.setText("");
        valorField.setText("");
        vetField.setText("");
        valorAcessoriosField.setText("0.0");
        descricaoArea.setText("");
        acessoriosCheck.setSelected(false);
        tipoServicoCombo.setSelectedIndex(0);
        pagoCheckBox.setSelected(false);
        tamanhoCombo.setSelectedIndex(0);
        valorAcessoriosField.setEnabled(false);
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        try {
            controller.listarServicos().forEach(servico -> {
                tableModel.addRow(new Object[]{
                        servico.getId(),
                        servico.getTipo(),
                        servico.getAnimal().getNome(),
                        "R$ " + String.format("%.2f", servico.getValorTotal()),
                        servico.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                        servico.isPago() ? "Pago" : "Pendente"
                });
            });

        } catch (NullPointerException e){
            e.printStackTrace();
        }


    }
}