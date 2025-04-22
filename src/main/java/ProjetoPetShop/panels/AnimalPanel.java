package ProjetoPetShop.panels;

import ProjetoPetShop.controller.AnimalController;
import ProjetoPetShop.controller.TutorController;
import ProjetoPetShop.entities.Animal;
import ProjetoPetShop.entities.Tutor;
import ProjetoPetShop.theme.Theme;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AnimalPanel extends JPanel {
    private final AnimalController animalController;
    private final TutorController tutorController;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private JTextField nomeField;
    private JTextField especieField;
    private JTextField racaField;
    private JTextField idadeField;
    private JComboBox<String> tutorComboBox;
    private JButton cadastrarBtn;
    private JButton editarBtn;
    private JButton removerBtn;
    private JButton limparBtn;

    public AnimalPanel(AnimalController animalController, TutorController tutorController) {
        this.animalController = animalController;
        this.tutorController = tutorController;

        setLayout(new BorderLayout(10, 10));
        setBackground(Theme.BACKGROUND_COLOR);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Painel de formulário
        JPanel formPanel = criarFormPanel();
        add(formPanel, BorderLayout.NORTH);

        // Tabela de animais
        this.tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column){
                return false; //nenhum é editavel
            }

        };
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Espécie");
        tableModel.addColumn("Raça");
        tableModel.addColumn("Idade");
        tableModel.addColumn("Tutor");
        this.table = new JTable(tableModel);
        this.table.setFillsViewportHeight(true);
        this.table.setRowHeight(25);

        // Seleção na tabela
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherCamposComSelecionado(table);
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Theme.SECONDARY_COLOR, 1),
                "Animais Cadastrados",
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

        SwingUtilities.invokeLater(() -> {
            atualizarComboTutores();
        });

        // Atualizar dados iniciais
        atualizarComboTutores();
        atualizarTabela();
    }

    private JPanel criarFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());

        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Theme.SECONDARY_COLOR, 1),
                "Dados do Animal",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                Theme.PRIMARY_COLOR
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos do formulário
        nomeField = new JTextField(20);
        especieField = new JTextField(20);
        racaField = new JTextField(20);
        idadeField = new JTextField(20);
        tutorComboBox = new JComboBox<>();
        tutorComboBox.setPreferredSize(new Dimension(200, 20));

        // Adicionando componentes
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nomeField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Espécie:"), gbc);
        gbc.gridx = 1;
        formPanel.add(especieField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Raça:"), gbc);
        gbc.gridx = 1;
        formPanel.add(racaField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Idade:"), gbc);
        gbc.gridx = 1;
        formPanel.add(idadeField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Tutor:"), gbc);
        gbc.gridx = 1;
        formPanel.add(tutorComboBox, gbc);

        JButton atualizarBtn = criarBotao("Atualizar Tutores", Theme.PRIMARY_COLOR, e -> atualizarComboTutores());
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(atualizarBtn, gbc);

        return formPanel;

    }

    private JPanel criarButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Theme.BACKGROUND_COLOR);

        // Botões estilizados
        cadastrarBtn = criarBotao("Cadastrar", Theme.PRIMARY_COLOR, this::cadastrarAnimal);
        editarBtn = criarBotao("Editar", Theme.PRIMARY_COLOR, this::editarAnimal);
        removerBtn = criarBotao("Remover", Theme.PRIMARY_COLOR, this::removerAnimal);
        limparBtn = criarBotao("Limpar", Theme.SECONDARY_COLOR, this::limparCampos);

        // Estado inicial dos botões
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
            Integer id = (Integer) tableModel.getValueAt(linhaSelecionada, 0);
            Animal animal = animalController.buscarAnimal(id);

            if (animal != null) {
                nomeField.setText(animal.getNome());
                especieField.setText(animal.getEspecie());
                racaField.setText(animal.getRaca());
                idadeField.setText(String.valueOf(animal.getIdade()));

                // Seleciona o tutor no combobox
                String tutorInfo = animal.getTutor().getNome() + " (" + animal.getTutor().getCpf() + ")";
                tutorComboBox.setSelectedItem(tutorInfo);

                // Habilita edição/remoção
                editarBtn.setEnabled(true);
                removerBtn.setEnabled(true);
                cadastrarBtn.setEnabled(false);
            }
        }
    }

    void atualizarComboTutores() {
        try {
            tutorComboBox.removeAllItems();
            List<Tutor> tutores = tutorController.listarTutores();

            if (tutores == null || tutores.isEmpty()) {
                // Mostra mensagem apenas quando o usuário tentar atualizar
                if (tutorComboBox.isShowing()) {
                    JOptionPane.showMessageDialog(this,
                            "Nenhum tutor cadastrado. Cadastre um tutor primeiro.",
                            "Aviso",
                            JOptionPane.WARNING_MESSAGE);
                }
            } else {
                tutores.forEach(tutor -> {
                    if (tutor != null) {
                        tutorComboBox.addItem(tutor.getNome() + " (" + tutor.getCpf() + ")");
                    }
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar tutores: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cadastrarAnimal(ActionEvent e) {
        try {
            validarCampos();

            String tutorSelecionado = (String) tutorComboBox.getSelectedItem();
            String cpfTutor = tutorSelecionado.substring(tutorSelecionado.indexOf("(") + 1, tutorSelecionado.indexOf(")"));

            animalController.cadastrarAnimal(
                    nomeField.getText().trim(),
                    especieField.getText().trim(),
                    racaField.getText().trim(),
                    Integer.parseInt(idadeField.getText().trim()),
                    cpfTutor
            );

            mostrarMensagemSucesso("Animal cadastrado com sucesso!");
            atualizarTabela();
            limparCampos();

        } catch (Exception ex) {
            mostrarMensagemErro("Erro ao cadastrar animal:\n" + ex.getMessage());
        }
    }

    private void editarAnimal(ActionEvent e) {
        try {
            validarCampos();

            int linhaSelecionada = table.getSelectedRow();
            Integer id = (Integer) tableModel.getValueAt(linhaSelecionada, 0);

            String tutorSelecionado = (String) tutorComboBox.getSelectedItem();
            String cpfTutor = tutorSelecionado.substring(tutorSelecionado.indexOf("(") + 1, tutorSelecionado.indexOf(")"));

            Animal animalEditado = new Animal(
                    id,
                    nomeField.getText().trim(),
                    especieField.getText().trim(),
                    racaField.getText().trim(),
                    Integer.parseInt(idadeField.getText().trim()),
                    tutorController.buscarTutorPorCpf(cpfTutor)
            );

            animalController.atualizarAnimal(animalEditado);
            mostrarMensagemSucesso("Animal atualizado com sucesso!");
            atualizarTabela();
            limparCampos();

        } catch (Exception ex) {
            mostrarMensagemErro("Erro ao editar animal:\n" + ex.getMessage());
        }
    }

    private void removerAnimal(ActionEvent e) {
        try {
            int linhaSelecionada = table.getSelectedRow();
            if (linhaSelecionada < 0) {
                throw new IllegalArgumentException("Selecione um animal para remover!");
            }

            Integer id = (Integer) tableModel.getValueAt(linhaSelecionada, 0);

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Tem certeza que deseja remover este animal?",
                    "Confirmar Remoção",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                if (animalController.removerAnimal(id)) {
                    mostrarMensagemSucesso("Animal removido com sucesso!");
                    atualizarTabela();
                    limparCampos();
                } else {
                    mostrarMensagemErro("Falha ao remover animal!");
                }
            }

        } catch (Exception ex) {
            mostrarMensagemErro("Erro ao remover animal:\n" + ex.getMessage());
        }
    }

    private void validarCampos() {
        if (nomeField.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do animal é obrigatório!");
        }
        if (especieField.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("A espécie do animal é obrigatória!");
        }
        if (tutorComboBox.getSelectedItem() == null) {
            throw new IllegalArgumentException("Selecione um tutor!");
        }
        try {
            if (!idadeField.getText().trim().isEmpty()) {
                Integer.parseInt(idadeField.getText().trim());
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Idade deve ser um número inteiro!");
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        especieField.setText("");
        racaField.setText("");
        idadeField.setText("");
        tutorComboBox.setSelectedIndex(-1);

        // Habilita/desabilita botões
        cadastrarBtn.setEnabled(true);
        editarBtn.setEnabled(false);
        removerBtn.setEnabled(false);
    }

    private void limparCampos(ActionEvent e) {
        limparCampos();
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        animalController.listarTodosAnimais().forEach(animal -> {
            tableModel.addRow(new Object[]{
                    animal.getCodigo(),
                    animal.getNome(),
                    animal.getEspecie(),
                    animal.getRaca(),
                    animal.getIdade(),
                    animal.getTutor().getNome()
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