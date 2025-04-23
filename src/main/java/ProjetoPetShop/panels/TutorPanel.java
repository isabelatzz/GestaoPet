package ProjetoPetShop.panels;

import ProjetoPetShop.controller.TutorController;
import ProjetoPetShop.entities.Tutor;
import ProjetoPetShop.theme.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TutorPanel extends JPanel {
    private final TutorController controller;
    private final DefaultTableModel tableModel;
    private final AnimalPanel animalPanel;
    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField telefoneField;
    private JTextField enderecoField;
    private JTextField emailField;
    private JButton cadastrarBtn;
    private JButton editarBtn;
    private JButton removerBtn;
    private JButton limparBtn;

    public TutorPanel(TutorController controller, AnimalPanel animalPanel) {
        this.controller = controller;
        this.animalPanel = animalPanel;
        setLayout(new BorderLayout(10, 10));
        setBackground(Theme.BACKGROUND_COLOR);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Painel de formulário
        JPanel formPanel = criarFormPanel();
        add(formPanel, BorderLayout.NORTH);

        // Tabela de tutores
        this.tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column){
                return false; //nenhum é editavel
            }

        };
        tableModel.addColumn("Nome");
        tableModel.addColumn("CPF");
        tableModel.addColumn("Telefone");
        tableModel.addColumn("Telefone");
        tableModel.addColumn("Endereço");

        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);

        // Adiciona listener para seleção na tabela
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                preencherCamposComSelecionado(table);
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Theme.SECONDARY_COLOR, 1),
                "Tutores Cadastrados",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                Theme.PRIMARY_COLOR
        ));

        // Painel de botões
        JPanel buttonPanel = criarButtonPanel();

        // Layout principal
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Atualizar tabela inicial
        atualizarTabela();
    }

    private JPanel criarFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Theme.SECONDARY_COLOR, 1),
                "Dados do Tutor",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                Theme.PRIMARY_COLOR
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Inicialização dos campos
        nomeField = new JTextField(20);
        cpfField = new JTextField(20);
        telefoneField = new JTextField(20);
        enderecoField = new JTextField(20);
        emailField = new JTextField(20);

        // Adicionando componentes
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nome Completo:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nomeField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cpfField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        formPanel.add(telefoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Endereço:"), gbc);
        gbc.gridx = 1;
        formPanel.add(enderecoField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("E-mail:"), gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        return formPanel;
    }

    private JPanel criarButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Theme.BACKGROUND_COLOR);

        // Botões estilizados
        cadastrarBtn = criarBotao("Cadastrar", Theme.PRIMARY_COLOR, e -> cadastrarTutor());
        editarBtn = criarBotao("Editar", Theme.PRIMARY_COLOR, e -> editarTutor());
        removerBtn = criarBotao("Remover", Theme.PRIMARY_COLOR, e -> removerTutor());
        limparBtn = criarBotao("Limpar", Theme.PRIMARY_COLOR, e -> limparCampos());

        // Desabilita editar/remover inicialmente
        editarBtn.setEnabled(false);
        removerBtn.setEnabled(false);

        buttonPanel.add(cadastrarBtn);
        buttonPanel.add(editarBtn);
        buttonPanel.add(removerBtn);
        buttonPanel.add(limparBtn);

        return buttonPanel;
    }

    private JButton criarBotao(String texto, Color cor, java.awt.event.ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.setBackground(cor);
        botao.setForeground(Color.BLACK);
        botao.setFocusPainted(false);
        botao.addActionListener(acao);
        return botao;
    }

    private void preencherCamposComSelecionado(JTable table) {
        int linhaSelecionada = table.getSelectedRow();
        if (linhaSelecionada >= 0) {
            String cpf = (String) tableModel.getValueAt(linhaSelecionada, 1);
            Tutor tutor = controller.buscarTutorPorCpf(cpf);

            if (tutor != null) {
                nomeField.setText(tutor.getNome());
                cpfField.setText(tutor.getCpf());
                telefoneField.setText(tutor.getTelefone());
                enderecoField.setText(tutor.getEndereço());
                emailField.setText(tutor.getEmail());

                // Habilita edição/remoção
                editarBtn.setEnabled(true);
                removerBtn.setEnabled(true);
                cadastrarBtn.setEnabled(false);
            }
        }
    }

    private void cadastrarTutor() {
        try {
            validarCampos();

            Tutor novoTutor = new Tutor(
                    nomeField.getText().trim(),
                    cpfField.getText().trim(),
                    telefoneField.getText().trim(),
                    enderecoField.getText().trim(),
                    emailField.getText().trim()
            );

            controller.cadastrarTutor(novoTutor);
            mostrarMensagemSucesso("Tutor cadastrado com sucesso!");
            atualizarTabela();
            limparCampos();

            if (animalPanel != null) {
                animalPanel.atualizarComboTutores();
            }

        } catch (Exception ex) {
            mostrarMensagemErro("Erro ao cadastrar tutor:\n" + ex.getMessage());
        }

    }

    private void editarTutor() {
        try {
            validarCampos();

            Tutor tutorEditado = new Tutor(
                    nomeField.getText().trim(),
                    cpfField.getText().trim(),
                    telefoneField.getText().trim(),
                    enderecoField.getText().trim(),
                    emailField.getText().trim()
            );

            if (controller.atualizarTutor(tutorEditado)) {
                mostrarMensagemSucesso("Tutor atualizado com sucesso!");
                atualizarTabela();
                limparCampos();
            } else {
                mostrarMensagemErro("Tutor não encontrado para edição!");
            }

        } catch (Exception ex) {
            mostrarMensagemErro("Erro ao editar tutor:\n" + ex.getMessage());
        }
    }

    private void removerTutor() {
        try {
            String cpf = cpfField.getText().trim();
            if (cpf.isEmpty()) {
                throw new IllegalArgumentException("Selecione um tutor para remover!");
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Tem certeza que deseja remover este tutor?",
                    "Confirmar Remoção",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                if (controller.removerTutor(cpf)) {
                    mostrarMensagemSucesso("Tutor removido com sucesso!");
                    atualizarTabela();
                    limparCampos();
                } else {
                    mostrarMensagemErro("Tutor não encontrado para remoção!");
                }
            }

        } catch (Exception ex) {
            mostrarMensagemErro("Erro ao remover tutor:\n" + ex.getMessage());
        }
    }

    private void validarCampos() {
        if (nomeField.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do tutor é obrigatório!");
        }
        if (cpfField.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF do tutor é obrigatório!");
        }
        if (telefoneField.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("O telefone do tutor é obrigatório!");
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        cpfField.setText("");
        telefoneField.setText("");
        enderecoField.setText("");
        emailField.setText("");

        // Habilita/desabilita botões
        cadastrarBtn.setEnabled(true);
        editarBtn.setEnabled(false);
        removerBtn.setEnabled(false);

        nomeField.requestFocus();
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        controller.listarTutores().forEach(tutor -> {
            tableModel.addRow(new Object[]{
                    tutor.getNome(),
                    tutor.getCpf(),
                    tutor.getTelefone(),
                    tutor.getEmail(),
                    tutor.getEndereço()
            });
        });
    }

    private void mostrarMensagemSucesso(String mensagem) {
        JOptionPane.showMessageDialog(
                this,
                mensagem,
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void mostrarMensagemErro(String mensagem) {
        JOptionPane.showMessageDialog(
                this,
                mensagem,
                "Erro",
                JOptionPane.ERROR_MESSAGE
        );
    }
}