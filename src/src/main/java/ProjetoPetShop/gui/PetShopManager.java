package ProjetoPetShop.gui;

import ProjetoPetShop.entities.*;
import ProjetoPetShop.system.petlover.PetLoverMap;
import ProjetoPetShop.system.recibo.ServicoRecibosPDFSystem;
import ProjetoPetShop.system.servico.ServicoPetLoverMap;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class PetShopManager{
    private JFrame mainFrame, frameAcao;
    private JPanel mainPanel, painelAcao;
    private JLabel mainLabel, rotuloAcao;
    private JMenuBar mainMenuBar;
    private JMenu menuCadastro, menuAlterar, menuRemover, menuServicos, menuRecibos;
    private JMenuItem cadastrarTutor, cadastrarAnimal, cadastrarServico;
    private JMenuItem alterarTutor, alterarAnimal, alterarServico;
    private JMenuItem removerTutor, removerAnimal, removerServico;
    private JMenuItem servicosDia, servicosPendentes;
    private JMenuItem gerarRecibos, gerarPDF;
    private JTextField cadNomeTutor, cadCPFTutor, cadTelefone, cadEndereco, cadEmail;
    private JComboBox<Tamanho> tamanhoJComboBox;


    private final ImageIcon actionBG = new ImageIcon("src/main/resources/fundoDesfocado.png");
    private final ImageIcon mainBG = new ImageIcon("src/main/resources/mainBG.png");
    private final ImageIcon miniIcon = new ImageIcon("src/main/resources/miniIcon.png");
    private final ImageIcon iconNull = new ImageIcon((String)null);
    private PetLoverMap sistemaGerenciamentoTutorAnimal = new PetLoverMap();
    private ServicoRecibosPDFSystem sistemaRecibosPDF = new ServicoRecibosPDFSystem();
    private ServicoPetLoverMap sistemaGerenciamentoServicos = new ServicoPetLoverMap();

    public void initialize(){


        contruirJanelaAcao();


        Font fontPadrao = new Font(Font.DIALOG_INPUT, Font.BOLD,16);
        this.mainFrame = new JFrame("PetLover - PetShop");
        Color corDaBarra = this.mainFrame.getForeground();
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setSize(mainBG.getIconWidth(),mainBG.getIconHeight());
        this.mainFrame.setLocationRelativeTo(null);
        this.mainFrame.setResizable(true);
        this.mainFrame.setIconImage(miniIcon.getImage());
        this.mainFrame.setLayout(new GridBagLayout());


        this.mainPanel = new JPanel(new GridBagLayout());
        this.mainLabel = new JLabel(mainBG);
        {
            UIManager.put("Menu.font",fontPadrao);
            UIManager.put("MenuItem.font", fontPadrao);

            this.mainMenuBar = new JMenuBar();

            this.menuCadastro = new JMenu("Cadastrar");
            menuCadastro.setBorder(BorderFactory.createLineBorder(corDaBarra, 1));
            menuCadastro.setIconTextGap(8);
            menuCadastro.setBorderPainted(true);
            menuCadastro.setForeground(corDaBarra);
            menuCadastro.menuSelectionChanged(false);
            menuCadastro.addSeparator();

            this.cadastrarTutor = new JMenuItem("Tutor");
            cadastrarTutor.addActionListener(al -> {
                reconstruirJanelaAcaoParaCadastrarTutor();
                this.frameAcao.setVisible(true);
                this.esconderTelaPrincipal();
            });
            this.cadastrarAnimal = new JMenuItem("Animal");
            cadastrarAnimal.addActionListener(al ->{
                reconstruirJanelaAcaoParaCadastrarAnimal();
                this.frameAcao.setVisible(true);
                this.esconderTelaPrincipal();

            });
            this.cadastrarServico = new JMenuItem("Serviço");
            cadastrarServico.addActionListener(al -> {
                reconstruirJanelaAcaoParaCadastrarServicos();
                this.frameAcao.setVisible(true);
                this.esconderTelaPrincipal();
            });

            menuCadastro.add(cadastrarTutor);
            menuCadastro.add(cadastrarAnimal);
            menuCadastro.add(cadastrarServico);

            mainMenuBar.add(menuCadastro);

            this.menuAlterar = new JMenu("Alterar");
            menuAlterar.setBorder(BorderFactory.createLineBorder(corDaBarra, 1));
            menuAlterar.setIconTextGap(8);
            menuAlterar.setBorderPainted(true);
            menuAlterar.setForeground(corDaBarra);
            menuAlterar.menuSelectionChanged(false);
            menuAlterar.addSeparator();

            this.alterarTutor = new JMenuItem("Tutor");
            alterarTutor.addActionListener(al -> {

            });
            this.alterarAnimal = new JMenuItem("Animal");
            alterarAnimal.addActionListener(al->{

            });
            this.alterarServico = new JMenuItem("Serviço");
            alterarServico.addActionListener(al->{

            });

            menuAlterar.add(alterarTutor);
            menuAlterar.add(alterarAnimal);
            menuAlterar.add(alterarServico);

            mainMenuBar.add(menuAlterar);

            this.menuRemover = new JMenu("Remover");
            menuRemover.setBorder(BorderFactory.createLineBorder(corDaBarra, 1));
            menuRemover.setIconTextGap(8);
            menuRemover.setBorderPainted(true);
            menuRemover.setForeground(corDaBarra);
            menuRemover.menuSelectionChanged(false);
            menuRemover.addSeparator();

            this.removerTutor = new JMenuItem("Tutor");
            removerTutor.addActionListener(al -> {

            });

            this.removerAnimal = new JMenuItem("Animal");
            removerAnimal.addActionListener(al -> {

            });

            this.removerServico = new JMenuItem("Serviço");
            removerServico.addActionListener(al->{

            });

            menuRemover.add(removerTutor);
            menuRemover.add(removerAnimal);
            menuRemover.add(removerServico);

            mainMenuBar.add(menuRemover);

            this.menuServicos = new JMenu("Serviços");
            menuServicos.setBorder(BorderFactory.createLineBorder(corDaBarra, 1));
            menuServicos.setIconTextGap(8);
            menuServicos.setBorderPainted(true);
            menuServicos.setForeground(corDaBarra);
            menuServicos.menuSelectionChanged(false);
            menuServicos.addSeparator();

            this.servicosDia = new JMenuItem("Serviços do dia");
            servicosDia.addActionListener(al -> {

            });
            this.servicosPendentes = new JMenuItem("Serviços pendentes");
            servicosPendentes.addActionListener(al -> {

            });

            menuServicos.add(servicosDia);
            menuServicos.add(servicosPendentes);

            mainMenuBar.add(menuServicos);

            this.menuRecibos = new JMenu("Recibos");
            menuRecibos.setBorder(BorderFactory.createLineBorder(corDaBarra, 1));
            menuRecibos.setIconTextGap(8);
            menuRecibos.setBorderPainted(true);
            menuRecibos.setForeground(corDaBarra);
            menuRecibos.menuSelectionChanged(false);
            menuRecibos.addSeparator();

            this.gerarRecibos = new JMenuItem("Gerar recibo");
            gerarRecibos.addActionListener(al -> {

            });
            this.gerarPDF = new JMenuItem("Gerar PDF");
            gerarPDF.addActionListener(al -> {

            });

            menuRecibos.add(gerarRecibos);
            menuRecibos.add(gerarPDF);

            mainMenuBar.add(menuRecibos);
        }
        this.mainLabel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,0,0,0);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weighty = 1.0;
        gbc.weightx = 1.0;
        this.mainLabel.add(mainMenuBar,gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        this.mainPanel.add(mainLabel);
        this.mainFrame.add(mainPanel);
        this.mainFrame.setVisible(true);
    }

    public void contruirJanelaAcao(){
        this.frameAcao = new JFrame();
        frameAcao.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameAcao.setSize(actionBG.getIconWidth(),actionBG.getIconHeight());
        frameAcao.setLocationRelativeTo(null);
        frameAcao.setResizable(true);
        frameAcao.setIconImage(miniIcon.getImage());
        frameAcao.setLayout(new GridBagLayout());

        this.rotuloAcao = new JLabel(actionBG);
        rotuloAcao.setLayout(new GridBagLayout());
    }

    public void reconstruirJanelaAcaoParaCadastrarTutor(){
        this.frameAcao = new JFrame("Cadastrar Tutor");
        frameAcao.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameAcao.setSize(actionBG.getIconWidth(),actionBG.getIconHeight());
        frameAcao.setLocationRelativeTo(null);
        frameAcao.setResizable(true);
        frameAcao.setIconImage(miniIcon.getImage());
        frameAcao.setLayout(new GridBagLayout());

        this.rotuloAcao = new JLabel(actionBG);
        rotuloAcao.setLayout(new GridBagLayout());
        GridBagConstraints gbcCad = new GridBagConstraints();
        gbcCad.insets = new Insets(5,5,5,5);
        gbcCad.anchor = GridBagConstraints.EAST;
        cadNomeTutor = adicionarCampoAoActionFrame(gbcCad,"Nome",0);
        cadCPFTutor = adicionarCampoAoActionFrame(gbcCad,"CPF",1);
        cadTelefone = adicionarCampoAoActionFrame(gbcCad,"Telefone",2);
        cadEndereco = adicionarCampoAoActionFrame(gbcCad, "Endereço",3);
        cadEmail = adicionarCampoAoActionFrame(gbcCad,"Email",4);

        gbcCad.gridx = 0;
        gbcCad.gridy = 5;
        gbcCad.gridwidth = 0;
        gbcCad.anchor = GridBagConstraints.CENTER;
        JButton cadastrarCadBotao = new JButton("Cadastrar");
        cadastrarCadBotao.addActionListener(al->{
            this.sistemaGerenciamentoTutorAnimal.cadastrarTutor(
                    cadNomeTutor.getText(),
                    cadCPFTutor.getText(),
                    cadTelefone.getText(),
                    cadEndereco.getText(),
                    cadEmail.getText());
            limparFields();
        });
        rotuloAcao.add(cadastrarCadBotao, gbcCad);
        gbcCad.gridwidth = 1;
        JButton voltarCadBotao = new JButton("Voltar");
        voltarCadBotao.addActionListener(al->{
            this.mostrarTelaPrincipal();
            this.frameAcao.setVisible(false);
        });
        rotuloAcao.add(voltarCadBotao,gbcCad);
        gbcCad.anchor = GridBagConstraints.CENTER;
        this.frameAcao.add(rotuloAcao, gbcCad);
    }

    public void reconstruirJanelaAcaoParaCadastrarAnimal(){
        this.frameAcao = new JFrame("Cadastrar Animal");
        frameAcao.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameAcao.setSize(actionBG.getIconWidth(),actionBG.getIconHeight());
        frameAcao.setLocationRelativeTo(null);
        frameAcao.setResizable(true);
        frameAcao.setIconImage(miniIcon.getImage());
        frameAcao.setLayout(new GridBagLayout());

        this.rotuloAcao = new JLabel(actionBG);
        rotuloAcao.setLayout(new GridBagLayout());

        GridBagConstraints gbcCad = new GridBagConstraints();
        gbcCad.insets = new Insets(5,5,5,5);
        gbcCad.anchor = GridBagConstraints.EAST;
        cadNomeTutor = adicionarCampoAoActionFrame(gbcCad,"Nome",0);
        cadCPFTutor = adicionarCampoAoActionFrame(gbcCad,"Especie",1);
        cadTelefone = adicionarCampoAoActionFrame(gbcCad,"Raça",2);
        JLabel rotuloTamanho = new JLabel("Tamanho");
        rotuloTamanho.setFont(new Font("sans-serif", Font.BOLD,16));
        rotuloTamanho.setForeground(new Color(0xFFFFFF));
        gbcCad.gridx = 0;
        gbcCad.gridy = 3;
        rotuloAcao.add(rotuloTamanho,gbcCad);
        tamanhoJComboBox = new JComboBox<>(Tamanho.values());
        tamanhoJComboBox.setSize(50,20);
        gbcCad.gridx = 1;
        gbcCad.gridy = 3;
        gbcCad.anchor = GridBagConstraints.WEST;
        rotuloAcao.add(tamanhoJComboBox,gbcCad);
        gbcCad.anchor = GridBagConstraints.EAST;
        cadEndereco = adicionarCampoAoActionFrame(gbcCad, "Idade",4);
        cadEmail = adicionarCampoAoActionFrame(gbcCad,"CPF do tutor",5);

        gbcCad.gridx = 0;
        gbcCad.gridy = 6;
        gbcCad.gridwidth = 0;
        gbcCad.anchor = GridBagConstraints.CENTER;
        JButton cadastrarCadBotao = new JButton("Cadastrar");
        cadastrarCadBotao.addActionListener(al->{ //String cpf, String nome, String especie, String raca, Tamanho tamanho, int idade
            this.sistemaGerenciamentoTutorAnimal.cadastrarAnimal(
                    cadEmail.getText(),
                    cadNomeTutor.getText(),
                    cadCPFTutor.getText(),
                    cadTelefone.getText(),
                    (Tamanho) tamanhoJComboBox.getSelectedItem(),
                    Integer.parseInt(cadEndereco.getText())
            );
            limparFields();
        });
        rotuloAcao.add(cadastrarCadBotao, gbcCad);
        gbcCad.gridwidth = 1;
        JButton voltarCadBotao = new JButton("Voltar");
        voltarCadBotao.addActionListener(al->{
            this.mostrarTelaPrincipal();
            this.frameAcao.setVisible(false);
        });
        rotuloAcao.add(voltarCadBotao,gbcCad);
        gbcCad.anchor = GridBagConstraints.CENTER;
        this.frameAcao.add(rotuloAcao, gbcCad);
    }

    public void reconstruirJanelaAcaoParaCadastrarServicos(){
        this.frameAcao = new JFrame("Selecione o tipo de serviço");
        frameAcao.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameAcao.setSize(actionBG.getIconWidth(),actionBG.getIconHeight());
        frameAcao.setLocationRelativeTo(null);
        frameAcao.setResizable(true);
        frameAcao.setIconImage(miniIcon.getImage());
        frameAcao.setLayout(new GridBagLayout());

        this.rotuloAcao = new JLabel(actionBG);
        rotuloAcao.setLayout(new GridBagLayout());
        JButton banhoBotao, consultaBotao, tosaBotao;
        banhoBotao = new JButton("Banho");
        banhoBotao.addActionListener(al->{ //int id, Animal animal, DataMarcadaServico dataMarcada,Tamanho tamanho, String funcionario, double valorBase,double percentualAcrescimoTamanho, String descricao
            String cpf = JOptionPane.showInputDialog(frameAcao,
                    "Insira o CPF do tutor do animal:","Cadastrar banho", JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString();
            String nomeAnimal = JOptionPane.showInputDialog(frameAcao,"Insira o nome do pet:","Cadastrar banho",JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString();
            Animal animal = this.sistemaGerenciamentoTutorAnimal.getAnimalDoTutor(cpf,nomeAnimal);
            String dataStr = JOptionPane.showInputDialog(frameAcao, "Insira a data e hora da consulta\nEx.: 01/01/2024","Cadastrar banho",JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString();
            String [] dataSplit = dataStr.split("/");
            DataMarcadaServico data = new DataMarcadaServico(Integer.parseInt(dataSplit[0]),Integer.parseInt(dataSplit[1]),Integer.parseInt(dataSplit[2]));
            String funcionario = JOptionPane.showInputDialog(frameAcao,"Insira qual o nome do funcionario responsável pelo serviço:","Cadastrar banho",JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString();
            double valorBase = Double.parseDouble(JOptionPane.showInputDialog(frameAcao,"Insira o valor base do serviço:",
                    "Cadastrar banho",JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString());
            double percentualAcrescimo = Double.parseDouble(JOptionPane.showInputDialog(frameAcao,"Insira o percentual de acrescimo:",
                    "Cadastrar banho", JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString());
            String descricao = JOptionPane.showInputDialog(frameAcao,"Insira a descrição do serviço","Cadastrar banho",
                    JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString();
            Servico banho = new Banho(0,animal, data,animal.getTamanho(),funcionario,valorBase, percentualAcrescimo, descricao);
            this.sistemaGerenciamentoServicos.cadastrarServico(banho);
        });
        consultaBotao = new JButton("Consulta");
        consultaBotao.addActionListener(al->{
//            int id, Animal animal, DataMarcadaServico dataMarcada,
//                    Tamanho tamanho, String funcionario,
//            double valorBase, double percetualAcrescimoTamanho, String descricao, double valorVacina, double valorMedicamento
            String cpf = JOptionPane.showInputDialog(frameAcao,
                    "Insira o CPF do tutor do animal:","Cadastrar consulta", JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString();
            String nomeAnimal = JOptionPane.showInputDialog(frameAcao,"Insira o nome do pet:","Cadastrar consulta",JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString();
            Animal animal = this.sistemaGerenciamentoTutorAnimal.getAnimalDoTutor(cpf,nomeAnimal);
            String dataStr = JOptionPane.showInputDialog(frameAcao, "Insira a data e hora da consulta\nEx.: 01/01/2024","Cadastrar consulta",JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString();
            String [] dataSplit = dataStr.split("/");
            DataMarcadaServico data = new DataMarcadaServico(Integer.parseInt(dataSplit[0]),Integer.parseInt(dataSplit[1]),Integer.parseInt(dataSplit[2]));
            String funcionario = JOptionPane.showInputDialog(frameAcao,"Insira qual o nome do funcionario responsável pelo serviço:",
                    "Cadastrar consulta",JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString();
            double valorBase = Double.parseDouble(JOptionPane.showInputDialog(frameAcao,"Insira o valor base do serviço:",
                    "Cadastrar consulta",JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString());
            double percentualAcrescimo = Double.parseDouble(JOptionPane.showInputDialog(frameAcao,"Insira o percentual de acrescimo:",
                    "Cadastrar consulta", JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString());
            String descricao = JOptionPane.showInputDialog(frameAcao,"Insira a descrição do serviço","Cadastrar banho",
                    JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString();
            double valorVacina = Double.parseDouble(JOptionPane.showInputDialog(frameAcao,"Insira o valor da vacina","Cadastrar consulta",JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString());
            double valorMedicamento = Double.parseDouble(JOptionPane.showInputDialog(frameAcao,"Insira o valor do medicamento", "Cadastrar consulta",JOptionPane.QUESTION_MESSAGE, iconNull,null,null).toString());
            Servico consulta = new Consulta(0,animal,data,animal.getTamanho(),funcionario,valorBase,percentualAcrescimo,descricao,valorVacina,valorMedicamento);
            this.sistemaGerenciamentoServicos.cadastrarServico(consulta);
        });
        tosaBotao = new JButton("Tosa");
        tosaBotao.addActionListener(al->{
//            int id, Animal animal, DataMarcadaServico dataMarcada,
//                    Tamanho tamanho, String funcionario, double valorBase,
//            double percentualAcrescimoTamanho, boolean adicionarAcc,
//            double valoraAcc, String descricao
            String cpf = JOptionPane.showInputDialog(frameAcao,
                    "Insira o CPF do tutor do animal:","Cadastrar tosa", JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString();
            String nomeAnimal = JOptionPane.showInputDialog(frameAcao,"Insira o nome do pet:","Cadastrar tosa",JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString();
            Animal animal = this.sistemaGerenciamentoTutorAnimal.getAnimalDoTutor(cpf,nomeAnimal);
            String dataStr = JOptionPane.showInputDialog(frameAcao, "Insira a data e hora da consulta\nEx.: 01/01/2024","Cadastrar tosa",JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString();
            String [] dataSplit = dataStr.split("/");
            DataMarcadaServico data = new DataMarcadaServico(Integer.parseInt(dataSplit[0]),Integer.parseInt(dataSplit[1]),Integer.parseInt(dataSplit[2]));
            String funcionario = JOptionPane.showInputDialog(frameAcao,"Insira qual o nome do funcionario responsável pelo serviço:",
                    "Cadastrar tosa",JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString();
            double valorBase = Double.parseDouble(JOptionPane.showInputDialog(frameAcao,"Insira o valor base do serviço:",
                    "Cadastrar tosa",JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString());
            double percentualAcrescimo = Double.parseDouble(JOptionPane.showInputDialog(frameAcao,"Insira o percentual de acrescimo:",
                    "Cadastrar tosa", JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString());
            boolean acc;
            double valorAcc;
            if(JOptionPane.showInputDialog(frameAcao,"Colocar acessessório?\nS/N","Cadastrar tosa",JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString().equalsIgnoreCase("S")){
                acc = true;
                valorAcc = Double.parseDouble(JOptionPane.showInputDialog(frameAcao,"Insira o valor do acessessório","Cadastrar toda",JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString());
            } else {
                acc = false;
                valorAcc = 0.0;
            }
            String descricao = JOptionPane.showInputDialog(frameAcao,"Insira a descrição do serviço","Cadastrar banho",
                    JOptionPane.QUESTION_MESSAGE,iconNull,null,null).toString();
            Servico tosa = new Tosa(0,animal,data,animal.getTamanho(),funcionario,valorBase,percentualAcrescimo,acc,valorAcc,descricao);
            this.sistemaGerenciamentoServicos.cadastrarServico(tosa);
        });
        JButton voltarBotao = new JButton("Voltar");
        voltarBotao.addActionListener(al -> {
            this.frameAcao.setVisible(false);
            this.mainFrame.setVisible(true);
        });
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.SOUTH;
        rotuloAcao.add(voltarBotao,gbc);
        gbc.insets = new Insets(20,40,20,40);
        gbc.anchor = GridBagConstraints.CENTER;
        rotuloAcao.add(banhoBotao, gbc);
        rotuloAcao.add(consultaBotao, gbc);
        rotuloAcao.add(tosaBotao, gbc);

        this.frameAcao.add(rotuloAcao, gbc);
    }

    private JTextField adicionarCampoAoActionFrame(GridBagConstraints gbcM, String labelText, int linha){
        JLabel label2 = new JLabel(labelText);
        label2.setFont(new Font("sans-serif", Font.BOLD, 16));
        label2.setForeground(new Color(0xFFFFFF));
        gbcM.gridx = 0;
        gbcM.gridy = linha;
        this.rotuloAcao.add(label2, gbcM);

        JTextField field = new JTextField(20);
        gbcM.gridx = 1;
        gbcM.gridy = linha;
        this.rotuloAcao.add(field, gbcM);

        return field;
    }

    public void limparFields(){
        cadNomeTutor.setText("");
        cadCPFTutor.setText("");
        cadTelefone.setText("");
        cadEndereco.setText("");
        cadEmail.setText("");
    }
    public void mostrarTelaPrincipal(){
        this.mainFrame.setVisible(true);
    }
    public void esconderTelaPrincipal(){
        this.mainFrame.setVisible(false);
    }
    public static void main(String [] args){
        PetShopManager sistema = new PetShopManager();
        sistema.initialize();
    }
}
