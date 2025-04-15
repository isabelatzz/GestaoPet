package ProjetoPetShop.testClasses;

import ProjetoPetShop.entities.*;
import ProjetoPetShop.system.servico.ServicoPetLoverMap;

public class TesteGeracaoPDF {
    public static void main(String [] args) {

        ServicoPetLoverMap servico = new ServicoPetLoverMap();

        Tutor tutor = new Tutor(
                "Davi",
                "155.090.224-50",
                "83 996091896",
                "Rua São João",
                "davi@gmail.com"
        );

        Animal animal = new Animal(
                1,
                "Belinha",
                "Gato",
                "Siamês",
                4,
                tutor
        );


        Servico consulta = new Consulta(
                0,
                animal,
                "Ana",
                120.0,
                50,
                60,
                "Animal dócil",
                Tamanho.PEQUENO,
                10.0
        );

        servico.cadastrarServico(consulta);

        servico.gerarReciboPDF(consulta.getId());
        System.out.println("PDF gerado na pasta 'recibos' do projeto!");
    }
}
