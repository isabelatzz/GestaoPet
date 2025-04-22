package test;

import ProjetoPetShop.entities.Animal;
import ProjetoPetShop.entities.Tutor;
import ProjetoPetShop.exception.TutorCadastrado;
import ProjetoPetShop.system.petlover.PetLoverMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PetLoverMapTest {
    private PetLoverMap sistema;
    private Tutor tutorValido;
    private Animal animalValido;


    @BeforeEach
    void setUp() {
        sistema = new PetLoverMap();

        tutorValido = new Tutor("Isabaaela", "155.197.224-50",
                "83 99609189896", "Rua São João, Itapiririca-PB",
                "belinhagames111@gmjdail.com");

        animalValido = new Animal(1, "Belinha", "Gato",
                "Siamês", 7, tutorValido);

    }

    @Test
    void testCadastrarTutorComSucesso(){
        sistema.cadastrarTutor(tutorValido);
        Tutor tutorEncontrado = sistema.buscarTutorPorCpf(tutorValido.getCpf());

        assertNotNull(tutorEncontrado,"Tutor deveria ser encontrado");
        assertEquals(tutorValido.getCpf(),tutorEncontrado.getCpf(),"CPF não corresponde");
    }



    @Test
    void testCadastrarTutorDuplicadoDeveLançarExceçao(){
        sistema.cadastrarTutor(tutorValido);
        assertThrows(TutorCadastrado.class, () -> sistema.cadastrarTutor(tutorValido),"Deveria lançar TutorCadastrado ao cadastrar tutor duplicado");


    }

    @Test
    void testCadastrarAnimalComTutorNaoCadastrado(){
        sistema.cadastrarAnimal(animalValido);

        assertThrows(IllegalArgumentException.class, () -> {
            sistema.cadastrarAnimal(animalValido);
        }, "Deveria lançar exceção quando tutor não está cadastrado");
    }

    @Test
    void testCadastrarAnimalComSucesso(){
        sistema.cadastrarTutor(tutorValido);
        sistema.cadastrarAnimal(animalValido);

        Animal animalEncontrado = sistema.buscarAnimalPorID(animalValido.getCodigo());

        assertNotNull(animalEncontrado, "Animal deveria ter sido cadastrado");
        assertEquals(animalValido.getNome(), animalEncontrado.getNome(), "Nome do animal não corresponde");
        assertEquals(tutorValido.getCpf(), animalEncontrado.getTutor().getCpf(), "Tutor do animal não corresponde");
    }

    @Test
    void testBuscarAnimalPorIDInexistente(){
        sistema.cadastrarTutor(tutorValido);
        sistema.cadastrarAnimal(animalValido);

        assertNotNull(sistema.buscarAnimalPorID(1), "Animal com ID=1 deveria existir!");
    }

    @Test
    void testListarAnimaisPorTutor(){
        sistema.cadastrarTutor(tutorValido);
        sistema.cadastrarAnimal(animalValido);

        Animal animal2 = new Animal(2, "Peixinho", "Cachorro",
                "Vira-Lata", 2, tutorValido);
        sistema.cadastrarAnimal(animal2);

        List<Animal> animaisDoTutor = sistema.listarAnimaisPorTutor(tutorValido.getCpf());

        assertEquals(2, animaisDoTutor.size(), "Deveria retornar 2");
        assertTrue(animaisDoTutor.contains(animalValido), "Lista deveria conter o animal 1");
        assertTrue(animaisDoTutor.contains(animal2), "Lista deveria conter o animal 2");
    }

    @Test
    void testRemoverAnimal(){
        sistema.cadastrarTutor(tutorValido);
        sistema.cadastrarAnimal(animalValido);

        int idAnimal = animalValido.getCodigo();
        sistema.removerAnimal(idAnimal);

        assertNull(sistema.buscarAnimalPorID(idAnimal), "Animal deveria ter sido removido");

        // Verificar se foi removido da lista do tutor também
        List<Animal> animaisDoTutor = sistema.listarAnimaisPorTutor(tutorValido.getCpf());
        assertTrue(animaisDoTutor.isEmpty(), "Lista de animais deveria estar vazia");
    }

    @Test
    void testAtualizarAnimal(){
        sistema.cadastrarTutor(tutorValido);
        sistema.cadastrarAnimal(animalValido);

        Tutor novoTutor = new Tutor("Val", "123.567.334.90", "83 98854-2586",
                "Rua São Paulo, Ita-PB", "valaraujo@gmail.com");
        sistema.cadastrarTutor(novoTutor);

        // Modificar o animal
        animalValido.setNome("Rex Atualizado");
        animalValido.setEspecie("Cachorro Atualizado");
        animalValido.setRaca("Labrador Atualizado");
        animalValido.setTutor(novoTutor);

        sistema.atualizarAnimal(animalValido);

        Animal animalAtualizado = sistema.buscarAnimalPorID(animalValido.getCodigo());

        assertEquals("Rex Atualizado", animalAtualizado.getNome());
        assertEquals("Cachorro Atualizado", animalAtualizado.getEspecie());
        assertEquals("Labrador Atualizado", animalAtualizado.getRaca());
        assertEquals(novoTutor.getCpf(), animalAtualizado.getTutor().getCpf());
    }

    @Test
    void testListarTodosTutores(){
        sistema.cadastrarTutor(tutorValido);

        Tutor tutor2 = new Tutor("Jailson", "123.456.678-90", "83996091896",
                "Rua São João, MME-PB", "jailson@gmail.com");
        sistema.cadastrarTutor(tutor2);

        List<Tutor> todosTutores = sistema.listarTodosTutores();

        assertEquals(2, todosTutores.size());
        assertTrue(todosTutores.contains(tutorValido));
        assertTrue(todosTutores.contains(tutor2));
    }

    @Test
    void testListarTodosAnimais(){
        sistema.cadastrarTutor(tutorValido);
        sistema.cadastrarAnimal(animalValido);

        Animal animal2 = new Animal(2, "Jade", "Gato", "Siamês",
                6, tutorValido);
        sistema.cadastrarAnimal(animal2);

        List<Animal> todosAnimais = sistema.listarTodosAnimais();

        assertEquals(2, todosAnimais.size());
        assertTrue(todosAnimais.contains(animalValido));
        assertTrue(todosAnimais.contains(animal2));
    }

}

