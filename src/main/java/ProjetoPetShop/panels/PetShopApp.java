package ProjetoPetShop.panels;

import ProjetoPetShop.controller.*;
import ProjetoPetShop.system.petlover.PetLoverMap;
import ProjetoPetShop.system.servico.ServicoPetLoverMap;

import javax.swing.*;

public class PetShopApp {
    public static void main(String[] args) {
        // Inicializa os controllers
        PetLoverMap petLoverMap = new PetLoverMap();
        ServicoPetLoverMap servicoMap = new ServicoPetLoverMap();

        AnimalController animalController = new AnimalController(petLoverMap);
        TutorController tutorController = new TutorController(petLoverMap);
        ServicoController servicoController = new ServicoController(petLoverMap, servicoMap);

        // Cria e exibe a janela principal
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(animalController, tutorController, servicoController);
            mainFrame.showFrame();
        });
    }
}
