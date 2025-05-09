package ProjetoPetShop;

import ProjetoPetShop.entities.Animal;
import ProjetoPetShop.entities.Servico;
import ProjetoPetShop.entities.Tamanho;
import ProjetoPetShop.entities.Tutor;
import ProjetoPetShop.exception.TutorCadastrado;
import ProjetoPetShop.system.petlover.PetLoverMap;
import ProjetoPetShop.system.servico.ServicoPetLoverMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PetLoverTest {
    private ServicoPetLoverMap sistema;
    private Tutor tutorValido;
    private Animal animalValido;
    private Servico servicoValido;
    private PetLoverMap sistemaPetLover;

    @BeforeEach
    void setUp(){
        try{
            new File("data/animais.dat").delete();
            new File("data/tutores.dat").delete();
        } catch (Exception e){
            System.out.println("Não foi possível limpar arquivos de dados");
        }

        sistema = new ServicoPetLoverMap();
        sistemaPetLover = new PetLoverMap();

        tutorValido = new Tutor("Isabella", "166090224-50", "83996091886",
                "Rua São Pedro,25,RT-PB", "isabella@gmail.com");

        animalValido = new Animal(3, "Bela", "Cachorro", "Pinscher", 9,
                tutorValido);

        servicoValido = new Servico(1, animalValido, 100.0, "Dr João", false,
                Tamanho.MEDIO,10.0) {
            @Override
            protected String getDetalhesServico() {
                return "Banho";
            }

            @Override
            public double calcularValorTotal() {
                return getValorBase() + calcularAcrescimo();
            }
        };
    }

    @Test
    void testGerarRecibo(){
        String recibo = servicoValido.gerarRecibo();
        System.out.println("Conteúdo do recibo:\n" + recibo);

        assertAll(
                () -> assertTrue(recibo.contains("PetLover"), "Cabeçalho do recibo"),
                () -> assertTrue(recibo.contains("Bela"), "Nome do animal"),
                () -> assertTrue(recibo.contains("Isabella"), "Nome do tutor"),
                () -> assertTrue(recibo.contains("R$ 100,00"), "Valor base"),
                () -> assertTrue(recibo.contains("Banho"), "Detalhes específicos"),
                () -> assertTrue(recibo.contains("PENDENTE"), "Status de pagamento")
        );
    }

    @Test
    void testCadastrarServicoComSucesso(){
        sistema.cadastrarServico(servicoValido);

        String recibo = sistema.gerarRecibo(servicoValido.getId());
        assertNotNull(recibo);
        assertTrue(recibo.contains("Banho"));
    }

    @Test
    void testGerarReciboPDFComSucesso() {
        sistema.cadastrarServico(servicoValido);

        assertDoesNotThrow(() -> {
            sistema.gerarReciboPDF(servicoValido.getId());
        }, "Geração de PDF não deveria lançar exceção");
    }

    @Test
    void testListarServiçosPendentes(){
        Servico servicoPendente = new Servico(2, animalValido, 100.00,
                "Dr Isadora", false,Tamanho.PEQUENO, 20.0)
        {
            @Override
            protected String getDetalhesServico() {
                return "Consulta";
            }

            @Override
            public double calcularValorTotal() {
                return getValorBase() + calcularAcrescimo();
            }
        };

        sistema.cadastrarServico(servicoPendente);

        List<Servico> pendentes = sistema.listarServicosPendentes();
        assertEquals(1, pendentes.size(), "Deveria lançar 1 serviço pendente");
        assertEquals("Consulta", pendentes.get(0).getDetalhes(), "Detalhes incorretos");
    }

    @Test
    void testAdicionarServico(){
        Servico novoServico = new Servico(3, animalValido, 75.00, "Dr Igor Meira", true,
                Tamanho.MEDIO, 15.0)
        {
            @Override
            protected String getDetalhesServico() {
                return "Banho";
            }

            @Override
            public double calcularValorTotal() {
                return getValorBase() + calcularAcrescimo();
            }
        };

        assertTrue(sistema.listarTodosServicos().isEmpty(),
                "Deveria começar sem serviços");

        sistema.adicionarServico(novoServico);
        String recibo = sistema.gerarRecibo(novoServico.getId());

        assertAll(
                () -> assertEquals(1, sistema.listarTodosServicos().size(),
                        "Deveria ter 1 serviço cadastrado"),

                () -> assertEquals(1, novoServico.getId(),
                        "Deveria ter atribuído ID automático"),

                () -> assertTrue(recibo.contains("Banho"), "Recibo deveria conter detalhes do serviço"),

                () -> assertEquals(86.25, novoServico.calcularValorTotal(), 0.01,
                        "75 (base) + 11.25 (15%) = 86.25")
        );
    }

    @Test
    void testCadastrarTutorComSucesso(){
        sistemaPetLover.cadastrarTutor(tutorValido);
        Tutor tutorEncontrado = sistemaPetLover.buscarTutorPorCpf(tutorValido.getCpf());

        assertNotNull(tutorEncontrado,"Tutor deveria ser encontrado");
        assertEquals(tutorValido.getCpf(),tutorEncontrado.getCpf(),"CPF não corresponde");
    }

    @Test
    void testCadastrarTutorDuplicadoDeveLançarExceçao(){
        sistemaPetLover.cadastrarTutor(tutorValido);
        assertThrows(
                TutorCadastrado.class,
                () -> sistemaPetLover.cadastrarTutor(tutorValido),
                "Deveria lançar TutorCadastrado ao cadastrar tutor duplicado");
    }

    @Test
    void testCadastrarAnimalComTutorNaoCadastrado(){
        animalValido.setTutor(null);
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    sistemaPetLover.cadastrarAnimal(animalValido);
                }, "Deveria lançar exceção quando tutor não está cadastrado");
    }

    @Test
    void testCadastrarAnimalComSucesso(){
        sistemaPetLover.cadastrarTutor(tutorValido);
        sistemaPetLover.cadastrarAnimal(animalValido);

        Animal animalEncontrado = sistemaPetLover.buscarAnimalPorID(animalValido.getCodigo());

        assertNotNull(animalEncontrado, "Animal deveria ter sido cadastrado");
        assertEquals(animalValido.getNome(), animalEncontrado.getNome(), "Nome do animal não corresponde");
        assertEquals(tutorValido.getCpf(), animalEncontrado.getTutor().getCpf(), "Tutor do animal não corresponde");
    }

    @Test
    void testBuscarAnimalPorIDInexistente(){
        sistemaPetLover.cadastrarTutor(tutorValido);
        sistemaPetLover.cadastrarAnimal(animalValido);

        assertNull(
                sistemaPetLover.buscarAnimalPorID(999),
                "Retornar null porque não existe nenhum animal");
    }

    @Test
    void testListarAnimaisPorTutor(){
        sistemaPetLover.cadastrarTutor(tutorValido);
        sistemaPetLover.cadastrarAnimal(animalValido);

        Animal animal2 = new Animal(2, "Peixinho", "Cachorro",
                "Vira-Lata", 2, tutorValido);
        sistemaPetLover.cadastrarAnimal(animal2);

        List<Animal> animaisDoTutor = sistemaPetLover.listarAnimaisPorTutor(tutorValido.getCpf());

        assertEquals(2, animaisDoTutor.size(), "Deveria retornar 2");
        assertTrue(animaisDoTutor.contains(animalValido), "Lista deveria conter o animal 1");
        assertTrue(animaisDoTutor.contains(animal2), "Lista deveria conter o animal 2");
    }

    @Test
    void testRemoverAnimal(){
        sistemaPetLover.cadastrarTutor(tutorValido);
        sistemaPetLover.cadastrarAnimal(animalValido);

        int idAnimal = animalValido.getCodigo();
        sistemaPetLover.removerAnimal(idAnimal);

        assertNull(sistemaPetLover.buscarAnimalPorID(idAnimal), "Animal deveria ter sido removido");

        // Verificar se foi removido da lista do tutor também
        List<Animal> animaisDoTutor = sistemaPetLover.listarAnimaisPorTutor(tutorValido.getCpf());
        assertFalse(animaisDoTutor.contains(animalValido),
                "Animal deveria ter sido removido da lista do tutor");
    }

    @Test
    void testAtualizarAnimal(){
        sistemaPetLover.cadastrarTutor(tutorValido);
        sistemaPetLover.cadastrarAnimal(animalValido);

        // Modificar o animal
        animalValido.setNome("Rex Atualizado");
        animalValido.setEspecie("Cachorro Atualizado");
        animalValido.setRaca("Labrador Atualizado");
        animalValido.setTutor(tutorValido);

        sistemaPetLover.atualizarAnimal(animalValido);

        Animal animalAtualizado = sistemaPetLover.buscarAnimalPorID(animalValido.getCodigo());

        assertEquals("Rex Atualizado", animalAtualizado.getNome());
        assertEquals("Cachorro Atualizado", animalAtualizado.getEspecie());
        assertEquals("Labrador Atualizado", animalAtualizado.getRaca());
        assertEquals(tutorValido.getCpf(), animalAtualizado.getTutor().getCpf());
    }

    @Test
    void testListarTodosTutores(){
        sistemaPetLover.cadastrarTutor(tutorValido);
        List<Tutor> todosTutores = sistemaPetLover.listarTodosTutores();

        assertEquals(1, todosTutores.size());
        assertTrue(todosTutores.contains(tutorValido));
        assertTrue(todosTutores.contains(tutorValido));
    }

    @Test
    void testListarTodosAnimais(){
        sistemaPetLover.cadastrarTutor(tutorValido);
        sistemaPetLover.cadastrarAnimal(animalValido);

        Animal animal2 = new Animal(2, "Jade", "Gato", "Siamês",
                6, tutorValido);
        sistemaPetLover.cadastrarAnimal(animal2);

        Collection<Animal> todosAnimais = sistemaPetLover.listarTodosAnimais();

        assertEquals(2, todosAnimais.size());
        assertTrue(todosAnimais.contains(animalValido));
        assertTrue(todosAnimais.contains(animal2));
    }








}
