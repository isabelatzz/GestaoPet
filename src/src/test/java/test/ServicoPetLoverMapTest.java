package test;

import ProjetoPetShop.entities.*;
import ProjetoPetShop.exception.NaoHaServicosCadastradoException;
import ProjetoPetShop.system.recibo.ServicoRecibosPDFSystem;
import ProjetoPetShop.system.servico.ServicoPetLoverMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServicoPetLoverMapTest {
    private ServicoPetLoverMap sistema;
    private ServicoRecibosPDFSystem gerarReciboPDF;
    private Tutor tutorValido;
    private Animal animalValido;
    private Servico servicoValido;

    @BeforeEach
    void setUp(){
        sistema = new ServicoPetLoverMap();

        tutorValido = new Tutor("Isabela", "155090224-50", "83996091896",
                "Rua São João,23,ITA-PB", "isabela@gmail.com");

        animalValido = new Animal(3, "Bela", "Cachorro",
                "Pinscher", Tamanho.MEDIO, 5, tutorValido);

        servicoValido = new Banho(1, animalValido,new DataMarcadaServico(1,1,2003),Tamanho.MEDIO,"Dr.João",100.0, 10.0,"Banho à seco");

        gerarReciboPDF = new ServicoRecibosPDFSystem();
    }

    @Test
    void testGerarRecibo(){
        String recibo = gerarReciboPDF.gerarReciboFormatado(servicoValido);
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
        String recibo = gerarReciboPDF.gerarReciboFormatado(servicoValido);
        assertNotNull(recibo);
        assertTrue(recibo.contains("Banho"));
    }

    @Test
    void testGerarReciboPDFComSucesso() {
        assertDoesNotThrow(() -> {
            gerarReciboPDF.gerarReciboPDF(servicoValido);
        }, "Geração de PDF não deveria lançar exceção");
    }

    @Test
    void testListarServiçosPendentes(){
        Servico servicoPendente = new Consulta(2, animalValido,new DataMarcadaServico(2,2,2002),animalValido.getTamanho(),
                "Dra.Isadora",  100.00,
                20.0,"Consulta de rotina",100,50);

        sistema.cadastrarServico(servicoPendente);

        List<Servico> pendentes = (ArrayList)sistema.listarServicosPendentes();
        assertEquals(1, pendentes.size(), "Deveria lançar 1 serviço pendente");
    }

    @Test
    void testAdicionarServico(){
        Servico novoServico = new Banho(3, animalValido, new DataMarcadaServico(3,3,2003),animalValido.getTamanho(),
                "Dr Igor Meira",75.00, 15.0, "Banho à seco");

        try {
            sistema.getServicos();
        } catch (NaoHaServicosCadastradoException e){
            e.printStackTrace();
        }

        sistema.cadastrarServico(novoServico);
        String recibo = this.gerarReciboPDF.gerarReciboFormatado(novoServico);

        assertAll(
                () -> assertEquals(1, sistema.getServicos().size(),
                        "Deveria ter 1 serviço cadastrado"),

                () -> assertEquals(1, novoServico.getId(),
                        "Deveria ter atribuído ID automático"),

                () -> assertTrue(recibo.contains("Banho"), "Recibo deveria conter detalhes do serviço"),

                () -> assertEquals(1200.0, novoServico.calcularValorTotal(), 0.01,
                        "75 (base) + 11.25 (15%) = 86.25")
        );
    }
}
