package ProjetoPetShop.panels;

import ProjetoPetShop.controller.*;
import ProjetoPetShop.theme.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {
    private final ImageIcon miniIcon = new ImageIcon("src/main/resources/resources/miniIcon.png");

    public MainFrame(AnimalController animalController,
                     TutorController tutorController,
                     ServicoController servicoController) {

        // Configuração básica da janela
        super("PetLover - Sistema de PetShop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setMinimumSize(new Dimension(500,600));
        setIconImage(miniIcon.getImage());
        setLocationRelativeTo(null);

        // Aplicar tema
        Theme.applyTheme();

        // Criar header com logo
        JPanel headerPanel = createHeaderPanel();

        // Painel principal com abas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Adicionar abas
        tabbedPane.addTab("Tutores", new TutorPanel(tutorController, new AnimalPanel(animalController, tutorController)));
        tabbedPane.addTab("Animais", new AnimalPanel(animalController, tutorController));
        tabbedPane.addTab("Serviços", new ServicoPanel(servicoController));

        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Theme.PRIMARY_COLOR);
        JLabel footerLabel = new JLabel("PetLover © 2023 - Sistema de Gerenciamento para PetShops");
        footerLabel.setForeground(Color.WHITE);
        footerPanel.add(footerLabel);

        // Layout principal
        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Theme.PRIMARY_COLOR);
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Logo e título
        JLabel logoLabel = new JLabel("PetLover", JLabel.LEFT);
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        logoLabel.setForeground(Color.WHITE);

        // Ícone (pode substituir por uma imagem real)
        JLabel iconLabel = new JLabel("");
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 28));

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(Theme.PRIMARY_COLOR);
        logoPanel.add(iconLabel);
        logoPanel.add(Box.createHorizontalStrut(10));
        logoPanel.add(logoLabel);

        panel.add(logoPanel, BorderLayout.WEST);

        return panel;
    }

    public void showFrame() {
        setVisible(true);
    }
}