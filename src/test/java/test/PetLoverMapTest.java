package test;

import ProjetoPetShop.entities.Animal;
import ProjetoPetShop.entities.Tutor;
import ProjetoPetShop.exception.TutorCadastrado;
import ProjetoPetShop.system.petlover.PetLoverMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PetLoverMapTest {
    private PetLoverMap sistema;
    private Tutor tutorValido;
    private Animal animalValido;


    @BeforeEach
    void setUp() {
        sistema = new PetLoverMap();

        tutorValido = new Tutor("Isabela", "155.090.224-50",
                "83 996091896", "Rua São João, Itapiririca-PB",
                "belinhagames111@gmail.com");

        animalValido = new Animal(1, "Belinha", "Gato",
                "Siamês", 7, tutorValido);

    }
    @Test
    void testCadastrarTutorComSucesso() {
        sistema.cadastrarTutor(tutorValido);
        Tutor tutorEncontrado = sistema.buscarTutorPorCpf(tutorValido.getCpf());

        assertNotNull(tutorEncontrado, "Tutor deveria ter sido cadastrado");
        assertEquals(tutorValido.getCpf(), tutorEncontrado.getCpf(), "CPF não corresponde" +
                "ao Tutor");
    }

    @Test
    void testaCadastrarTutorDuplicadoDeveLançarExceçao(){
        sistema.cadastrarTutor(tutorValido);

        assertThrows(TutorCadastrado.class,
                () -> sistema.cadastrarTutor(tutorValido),
        "Deveria lançar TutorCadastrado ao cadastrar tutor duplicado");
    }

    @Test
    void testCadastrarAnimalComTutorNaoCadastrado(){
        assertThrows(IllegalArgumentException.class, () -> {
            sistema.cadastrarAnimal(animalValido);
        }, "Deveria lançar exceção quando tutor não está cadastrado");
    }

}