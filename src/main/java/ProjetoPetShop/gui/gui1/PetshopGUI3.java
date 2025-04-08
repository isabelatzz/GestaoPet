package ProjetoPetShop.gui.gui1;

import ProjetoPetShop.gui.controller.PetAddController;
import ProjetoPetShop.gui.controller.PetRemoveController;
import ProjetoPetShop.gui.controller.PetSearchController;
import ProjetoPetShop.system.GerenciaPetshop;
import ProjetoPetShop.system.GerenciaPetshopMAP;

import javax.swing.*;
import java.awt.*;

public class PetshopGUI3 extends JFrame {
    JLabel linha1, linha2;
    ImageIcon boloImg = new ImageIcon("./imgs/bolo.jpg");
    ImageIcon addImg = new ImageIcon("./imgs/icons/add_person.png");
    ImageIcon pesqImg = new ImageIcon("./imgs/icons/search_person.png");
    ImageIcon removeImg = new ImageIcon("./imgs/icons/remove_person.png");
    JButton botaoAdicionar, botaoPesquisar, botaoRemover;
    GerenciaPetshop gerenciaPetshop = new GerenciaPetshopMAP();

    public PetshopGUI3() {
        setTitle("Gestão PetLov");
        setSize(800, 600); //tamanho da janela
        setLocation(150, 150);
        setResizable(false);
        getContentPane().setBackground(Color.white);


        linha1 = new JLabel("PETLOV", JLabel.CENTER);
        linha1.setForeground(Color.red);
        linha1.setFont(new Font("Serif", Font.BOLD, 24));
        linha2 = new JLabel(boloImg, JLabel.CENTER);

        botaoAdicionar = new JButton("Adicionar", addImg);
        botaoAdicionar.addActionListener(new PetAddController(gerenciaPetshop, this));
        botaoPesquisar = new JButton("Pesquisar", pesqImg);
        botaoPesquisar.addActionListener(new PetSearchController(gerenciaPetshop, this));
        botaoRemover = new JButton("Remover", removeImg);
        botaoRemover.addActionListener(new PetRemoveController(gerenciaPetshop, this));

        getContentPane().setLayout(new GridLayout(3, 2));
        getContentPane().add(linha1);
        getContentPane().add(botaoAdicionar);
        getContentPane().add(linha2);
        getContentPane().add(botaoPesquisar);
        getContentPane().add(new JLabel());
        getContentPane().add(botaoRemover);

    }

    public static void main(String [] args){
        JFrame framePet = new PetShopGUI();
        framePet.setVisible(true);
        framePet.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
