package ProjetoPetShop.gui.controller;

import ProjetoPetShop.entities.Animal;
import ProjetoPetShop.entities.EspecieAnimal;
import ProjetoPetShop.system.GerenciaPetshop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PetAddController implements ActionListener {
    private GerenciaPetshop gerenciaPetshop;
    private JFrame framePet;

    public PetAddController(GerenciaPetshop gerenciaPetshop, JFrame framePet) {
        this.gerenciaPetshop = gerenciaPetshop;
        this.framePet = framePet;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String nome = JOptionPane.showInputDialog(framePet, "Nome do Animal: ");

        EspecieAnimal[] especies = EspecieAnimal.values();
        EspecieAnimal especieEscolhida = (EspecieAnimal) JOptionPane.showInputDialog(framePet,
                "Escolha o Espécie: ",
                "Seleção da Espécie",
                JOptionPane.QUESTION_MESSAGE,
                null,
                especies,
                especies[0]);

        String raca = JOptionPane.showInputDialog(framePet, "Raça do animal: ");

        int idade = Integer.parseInt(JOptionPane.showInputDialog(framePet, "Idade do animal: "));

        String dono = JOptionPane.showInputDialog(framePet, "Nome do dono do animal: ");

        Animal animal = new Animal(nome, especieEscolhida, raca, idade, dono);

        try {
            boolean cadastrou = gerenciaPetshop.cadastraDadosAnimais(nome, especieEscolhida, raca, idade, dono);
            if (cadastrou) {
                JOptionPane.showMessageDialog(framePet, "Dados cadastrados com sucesso!");
            } else {
                JOptionPane.showMessageDialog(framePet, "Falha no cadastro do animal.");
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(framePet, erro.getMessage());
            erro.printStackTrace();
        }

    }
}
