package ProjetoPetShop.gui.gui1;

import ProjetoPetShop.entities.EspecieAnimal;
import ProjetoPetShop.gui.controller.PetRemoveController;
import ProjetoPetShop.gui.controller.PetSearchController;
import ProjetoPetShop.system.GerenciaPetshop;
import ProjetoPetShop.system.GerenciaPetshopMAP;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PetShopGUI extends JFrame {
    JLabel linha1, linha2;
    ImageIcon logoImg = new ImageIcon("./img/petshop-onlne.jpg");
    JMenuBar barraDeMenu = new JMenuBar();
    GerenciaPetshop gerenciaPetshop = new GerenciaPetshopMAP();

    public PetShopGUI() {
        setTitle("Gestão de PetShop");
        setSize(700, 600);
        setLocation(150, 150);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.white);

        // Configurando Layout principal
        setLayout(new BorderLayout());

        // Criando labels e adicionando ao painel
        linha1 = new JLabel("Sistema de Gestão de PetShop", JLabel.CENTER);
        linha1.setForeground(Color.black);
        linha1.setFont(new Font("Serif", Font.BOLD, 25));

        linha2 = new JLabel(logoImg, JLabel.CENTER);

        JPanel panelPrincipal = new JPanel(new GridLayout(2, 3));
        panelPrincipal.add(linha1);
        panelPrincipal.add(linha2);

        add(panelPrincipal, BorderLayout.CENTER);

        // Criando menus e itens de menu
        JMenu menuCadastrar = new JMenu("Cadastrar");
        JMenuItem menuCadastrarAnimal = new JMenuItem("Cadastrar Animal");
        menuCadastrar.add(menuCadastrarAnimal);

        JMenu menuPesquisar = new JMenu("Pesquisar");
        JMenuItem menuPesquisarAnimalPorDono = new JMenuItem("Pesquisar Animal Por Dono");
        menuPesquisar.add(menuPesquisarAnimalPorDono);

        JMenu menuRemover = new JMenu("Remover");
        JMenuItem menuRemoverAnimal = new JMenuItem("Remover Animal");
        menuRemover.add(menuRemoverAnimal);

        // Adicionando ação aos itens de menu
        menuPesquisarAnimalPorDono.addActionListener(new PetSearchController(gerenciaPetshop, this));
        menuRemoverAnimal.addActionListener(new PetRemoveController(gerenciaPetshop, this));

        menuCadastrarAnimal.addActionListener((ae) -> {
            String nome = JOptionPane.showInputDialog(this, "Qual o nome do animal?");
            EspecieAnimal[] especies = EspecieAnimal.values();
            JComboBox<EspecieAnimal> comboBox = new JComboBox<>(especies);

            int opcao = JOptionPane.showConfirmDialog(this, comboBox, "Escolha a espécie do animal", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (opcao == JOptionPane.OK_OPTION) {
                EspecieAnimal especieAnimal = (EspecieAnimal) comboBox.getSelectedItem();
                String raca = JOptionPane.showInputDialog(this, "Qual a raça do seu animal?");
                int idade = Integer.parseInt(JOptionPane.showInputDialog(this, "Qual idade do animal?"));
                String dono = JOptionPane.showInputDialog(this, "Qual seu nome? ");

                boolean cadastrou = false;
                try {
                    cadastrou = gerenciaPetshop.cadastraDadosAnimais(nome, especieAnimal, raca, idade, dono);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if (cadastrou) {
                    JOptionPane.showMessageDialog(this, "Animal cadastrado");
                } else {
                    JOptionPane.showMessageDialog(this, "Animal não foi cadastrado. Verifique se já não existia.");
                }
            }
        });

        // Adicionando menus à barra de menu
        barraDeMenu.add(menuCadastrar);
        barraDeMenu.add(menuPesquisar);
        barraDeMenu.add(menuRemover);

        // Definindo a barra de menu na janela
        setJMenuBar(barraDeMenu);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame framePet = new PetShopGUI();
            framePet.setVisible(true);
        });
    }
}
