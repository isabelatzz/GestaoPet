package test;

import ProjetoPetShop.entities.Animal;
import ProjetoPetShop.entities.Tamanho;
import ProjetoPetShop.entities.Tutor;
import ProjetoPetShop.exception.NaoHaAnimaisCadastradosException;
import ProjetoPetShop.exception.TutorCadastrado;
import ProjetoPetShop.exception.TutorJaExisteException;
import ProjetoPetShop.exception.TutorNaoExisteException;
import ProjetoPetShop.system.petlover.PetLoverMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

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

        animalValido = new Animal(0, "Belinha", "Gato",
                "Siamês", Tamanho.MEDIO,3,tutorValido);

    }
    @Test
    void testCadastrarTutorComSucesso() {
        sistema.cadastrarTutor("Isabela", "155.090.224-50",
                "83 996091896", "Rua São João, Itapiririca-PB",
                "belinhagames111@gmail.com");
        Tutor tutorEncontrado = sistema.getTutor(tutorValido.getCpf());

        assertNotNull(tutorEncontrado, "Tutor deveria ter sido cadastrado");
        assertEquals(tutorValido.getCpf(), tutorEncontrado.getCpf(), "CPF não corresponde" +
                "ao Tutor");
    }

    @Test
    void testCadastrarTutorDuplicadoDeveLançarExceçao(){
        sistema.cadastrarTutor("Isabela", "155.090.224-50",
                "83 996091896", "Rua São João, Itapiririca-PB",
                "belinhagames111@gmail.com");

        try{
            sistema.cadastrarTutor("Isabela", "155.090.224-50",
                    "83 996091896", "Rua São João, Itapiririca-PB",
                    "belinhagames111@gmail.com");
        } catch (TutorJaExisteException e){
            e.printStackTrace();
        }
    }

    @Test
    void testCadastrarAnimalComTutorNaoCadastrado(){
        try {
            sistema.cadastrarAnimal("0000000", "Belinha", "Gato",
                    "Siamês", Tamanho.MEDIO,3);
        } catch (TutorNaoExisteException e){
            e.printStackTrace();
        }
    }

    @Test
    void testCadastrarAnimalComSucesso(){
        sistema.cadastrarTutor("Isabela", "155.090.224-50",
                "83 996091896", "Rua São João, Itapiririca-PB",
                "belinhagames111@gmail.com");
        sistema.cadastrarAnimal("155.090.224-50", "Belinha", "Gato",
                "Siamês", Tamanho.MEDIO,3);

        Animal animalEncontrado = sistema.getAnimalDoTutor("155.090.224-50","Belinha");

        assertNotNull(animalEncontrado, "Animal deveria ter sido cadastrado");
        assertEquals(animalValido.getNome(), animalEncontrado.getNome(), "Nome do animal não corresponde");
        assertEquals(tutorValido.getCpf(), animalEncontrado.getTutor().getCpf(), "Tutor do animal não corresponde");
    }


    @Test
    void testListarAnimaisPorTutor(){
        sistema.cadastrarTutor("Isabela", "155.090.224-50",
                "83 996091896", "Rua São João, Itapiririca-PB",
                "belinhagames111@gmail.com");
        sistema.cadastrarAnimal("155.090.224-50", "Belinha", "Gato",
                "Siamês", Tamanho.MEDIO,3);

        sistema.cadastrarAnimal("155.090.224-50", "Peixinho", "Cachorro",
                "Vira-Lata", Tamanho.PEQUENO,1);

        Collection<Animal> animaisDoTutor = sistema.getAnimaisDeTutor("155.090.224-50");

        assertEquals(2, animaisDoTutor.size(), "Deveria retornar 2");
    }

    @Test
    void testRemoverAnimal(){
        sistema.cadastrarTutor("Isabela", "155.090.224-50",
                "83 996091896", "Rua São João, Itapiririca-PB",
                "belinhagames111@gmail.com");
        sistema.cadastrarAnimal("155.090.224-50", "Belinha", "Gato",
                "Siamês", Tamanho.MEDIO,3);
        Collection<Animal> animaisDoTutor = sistema.getAnimaisDeTutor("155.090.224-50");
        assertEquals(1, animaisDoTutor.size());

        sistema.removerAnimal("155.090.224-50","Belinha");

        try {
            Collection<Animal> animaisDoTutorRemove = sistema.getAnimaisDeTutor("155.090.224-50");
        } catch (NaoHaAnimaisCadastradosException e){
            e.printStackTrace();
        }
    }

//    @Test
//    void testAtualizarAnimal(){ //TODO: fazer um método que atualiza os dados das entidades
//        sistema.cadastrarTutor("Isabela", "155.090.224-50",
//                "83 996091896", "Rua São João, Itapiririca-PB",
//                "belinhagames111@gmail.com");
//        sistema.cadastrarAnimal("155.090.224-50", "Belinha", "Gato",
//                "Siamês", Tamanho.MEDIO,3);
//
//        sistema.cadastrarTutor("Val", "123.567.334.90", "83 98854-2586",
//                "Rua São Paulo, Ita-PB", "valaraujo@gmail.com");
//
//        // Modificar o animal
//        animalValido.setNome("Rex Atualizado");
//        animalValido.setEspecie("Cachorro Atualizado");
//        animalValido.setRaca("Labrador Atualizado");
//        animalValido.setTutor(novoTutor);
//
//        sistema.atualizarAnimal(animalValido);
//
//        Animal animalAtualizado = sistema.buscarAnimalPorID(animalValido.getCodigo());
//
//        assertEquals("Rex Atualizado", animalAtualizado.getNome());
//        assertEquals("Cachorro Atualizado", animalAtualizado.getEspecie());
//        assertEquals("Labrador Atualizado", animalAtualizado.getRaca());
//        assertEquals(novoTutor.getCpf(), animalAtualizado.getTutor().getCpf());
//    }

    @Test
    void testListarTodosTutores(){
        sistema.cadastrarTutor("Isabela", "155.090.224-50",
                "83 996091896", "Rua São João, Itapiririca-PB",
                "belinhagames111@gmail.com");
        sistema.cadastrarTutor("Jailson", "123.456.678-90", "83996091896",
                "Rua São João, MME-PB", "jailson@gmail.com");

        Collection<Tutor> todosTutores = sistema.getTodosOsTutores();

        assertEquals(2, todosTutores.size());
    }

    @Test
    void testListarTodosAnimais(){
        sistema.cadastrarTutor("Isabela", "155.090.224-50",
                "83 996091896", "Rua São João, Itapiririca-PB",
                "belinhagames111@gmail.com");
        sistema.cadastrarAnimal("155.090.224-50", "Belinha", "Gato",
                "Siamês", Tamanho.MEDIO,3);
        sistema.cadastrarAnimal("155.090.224-50", "Peixinho", "Cachorro",
                "Vira-Lata", Tamanho.PEQUENO,1);

        sistema.cadastrarAnimal("155.090.224-50","Jade", "Gato", "Siamês",
                Tamanho.PEQUENO,2);

        Collection<Animal> todosAnimais = sistema.getAnimaisDeTutor("155.090.224-50");

        assertEquals(3, todosAnimais.size());
    }
}

