package test;

import ProjetoPetShop.entities.Animal;
import ProjetoPetShop.entities.Servico;
import ProjetoPetShop.entities.Tamanho;
import ProjetoPetShop.entities.Tutor;
import ProjetoPetShop.system.servico.ServicoPetLoverMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServicoPetLoverMapTest {
    private ServicoPetLoverMap sistema;
    private Tutor tutorValido;
    private Animal animalValido;
    private Servico servicoValido;

    @BeforeEach
    void setUp(){
        sistema = new ServicoPetLoverMap();

        tutorValido = new Tutor("Isabela", "155090224-50", "83996091896",
                "Rua São João,23,ITA-PB", "isabela@gmail.com");

        animalValido = new Animal(3, "Bela", "Cachorro", "Pinscher", 9,
                tutorValido);

        servicoValido = new Servico(1, animalValido, 100.0, "Dr João",
                Tamanho.MEDIO, 10.0) {
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
                () -> assertTrue(recibo.contains("Isabela"), "Nome do tutor"),
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
                "Dr Isadora", Tamanho.PEQUENO, 20.0)
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
        Servico novoServico = new Servico(3, animalValido, 75.00, "Dr Igor Meira",
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
}
