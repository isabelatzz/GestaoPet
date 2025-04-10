package ProjetoPetShop.testClasses;


import ProjetoPetShop.entities.Animal;
import ProjetoPetShop.entities.Banho;
import ProjetoPetShop.entities.Tamanho;
import ProjetoPetShop.entities.Tutor;

public class TesteClasseBanho {
    public static void main (String[] args) {
        Tutor tutor = new Tutor ("Maria Isabela", "155.090.224-50", "83 996091896",
                "Rua São Joao, Itapororoca", "araujobelinha@hotmail.com");

        Animal animal = new Animal (1,"Belinha", "Gato",
                "GATO", 4, tutor);

        // 3. Criar o serviço de banho
        Banho banho = new Banho(
                1,                      // ID
                animal,                 // Animal
                70.0,                   // Valor base
                "Dr. Pet",              // Veterinário
                "Banho com shampoo hipoalergênico", // Descrição
                Tamanho.PEQUENO,        // Tamanho
                5.0                     // % de acréscimo
        );

        // 4. Marcar como pago (opcional)
        banho.marcarComoPago();

        // 5. Gerar e imprimir o recibo
        System.out.println(banho.gerarRecibo());
    }
}


