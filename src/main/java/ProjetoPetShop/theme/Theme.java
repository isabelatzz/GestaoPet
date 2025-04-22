package ProjetoPetShop.theme;

import javax.swing.*;
import java.awt.*;

public class Theme {
    public static final Color PRIMARY_COLOR = new Color(102, 51, 153); // Roxo escuro
    public static final Color SECONDARY_COLOR = new Color(147, 112, 219); // Roxo médio
    public static final Color BACKGROUND_COLOR = new Color(240, 240, 245);
    public static final Color TEXT_COLOR = new Color(50, 50, 50);
    public static final Color LIGHT_ACCENT = new Color(230, 230, 250);

    public static void applyTheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Personalização de componentes
            UIManager.put("Panel.background", SECONDARY_COLOR);
            UIManager.put("Button.background", PRIMARY_COLOR);
            UIManager.put("Button.foreground", Color.BLACK);
            UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 12));
            UIManager.put("Button.border", BorderFactory.createEmptyBorder(8, 15, 8, 15));
            UIManager.put("TextField.border", BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(SECONDARY_COLOR, 1),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 12));
            UIManager.put("TableHeader.background", PRIMARY_COLOR);
            UIManager.put("TableHeader.foreground", Color.BLACK);
            UIManager.put("TabbedPane.selected", PRIMARY_COLOR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}