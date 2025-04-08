package ProjetoPetShop.gui.controller;

import ProjetoPetShop.system.GerenciaPetshop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PetRemoveController implements ActionListener {
    private GerenciaPetshop gerenciaPetshop;
    private JFrame framePet;

    public PetRemoveController(GerenciaPetshop gerenciaPetshop, JFrame framePet) {
        this.gerenciaPetshop = gerenciaPetshop;
        this.framePet = framePet;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nome = JOptionPane.showInputDialog(framePet, "Digite o nome do pet que deseja remover: ");
        try {
            gerenciaPetshop.removerDadosAnimais(nome);
            JOptionPane.showMessageDialog(framePet, "Pet removido com sucesso!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(framePet, "Pet não removido");
        }
    }
}
