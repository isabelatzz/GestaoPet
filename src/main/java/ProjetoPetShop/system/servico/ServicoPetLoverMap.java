package ProjetoPetShop.system.servico;

import ProjetoPetShop.data.GravadorDadosServico;
import ProjetoPetShop.entities.Animal;
import ProjetoPetShop.entities.Servico;
import ProjetoPetShop.exception.AnimalNaoExiste;
import ProjetoPetShop.exception.ServicoJaCadastradoException;
import ProjetoPetShop.exception.ServicoNaoCadastradoException;
import ProjetoPetShop.exception.TutorNaoEncontradoException;
import ProjetoPetShop.system.recibo.PDFRecibo;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementação concreta do gerenciador de serviços do PetShop.
 * Responsável por cadastrar, armazenar e gerenciar todos os serviços oferecidos.
 *
 * Utiliza um Map interno para armazenamento eficiente dos serviços por ID.
 */
public class ServicoPetLoverMap implements ServicoInterface {
    private Map<Integer, Servico> servicos;
    private int proximoIdServico;
    private final GravadorDadosServico gravadorServicos;

    public ServicoPetLoverMap() {
        this.gravadorServicos = new GravadorDadosServico("data/servicos.dat");

        this.servicos = carregarServicos();

        this.proximoIdServico = servicos.keySet().stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0) + 1;

        Thread shutdownThread = new Thread(() -> {
            System.out.println("[SHUTDOWN HOOK] Salvando dados antes de fechar...");
            salvarDados();
        });
        Runtime.getRuntime().addShutdownHook(shutdownThread);

    }

    private Map<Integer, Servico> carregarServicos() {
        try {
            Map<Integer, Servico> serviçoMap = gravadorServicos.recuperaDadosServicos();
            Map<Integer, Servico> servicoID = new HashMap<>();
            serviçoMap.forEach((key, servico) -> servicoID.put(servico.getId(), servico));        ;
            return servicoID;
        } catch (IOException e) {
            System.out.println("Arquivo de serviços não encontrado. Iniciando com dados vazios.");
            return new HashMap<>();
        }
    }

    public void salvarDados() {
        try {
            Map<String, Servico> servicoMap = new HashMap<>();
            servicos.forEach((id, animal) -> servicoMap.put(String.valueOf(id), animal));

            gravadorServicos.salvarDadosServicos(servicos);
        } catch (IOException e) {
            System.err.println("Erro ao salvar serviços: " + e.getMessage());
        }
    }

    public void adicionarServico(Servico servico) {
        servico.setId(proximoIdServico);
        servicos.put(proximoIdServico, servico);
        proximoIdServico++;
    }

    @Override
    public void cadastrarServico(Servico servico) {
        validarServico(servico);

        if (servico.getId() == 0) {
            servico.setId(proximoIdServico++);
        } else if (servicos.containsKey(servico.getId())) {
            throw new ServicoJaCadastradoException("ID já existe: " + servico.getId());
        }

        servicos.put(servico.getId(), servico);
        salvarDados();
    }

    private void validarServico(Servico servico) { //Evitar duplicação de código
        if (servico == null) throw new IllegalArgumentException("Serviço não pode ser nulo");
        if (servico.getAnimal() == null) throw new AnimalNaoExiste("Animal não informado");
        if (servico.getAnimal().getTutor() == null) throw new TutorNaoEncontradoException("Tutor não informado");
    }

    @Override
    public String gerarRecibo(int idServico) {
        Servico servico = buscarServicoPorId(idServico);
        return servico.gerarRecibo();
    }

    @Override
    public void gerarReciboPDF(int idServico) {
        if (!servicos.containsKey(idServico)) {
            throw new ServicoNaoCadastradoException("Serviço com ID " + idServico + "não existe");
        }
        Servico servico = servicos.get(idServico);

        if (servico.getAnimal().getTutor() == null){
            throw new IllegalStateException("Serviço não vinculado a um animal");
        }

        try {
            PDFRecibo pdfRecibo = new PDFRecibo();
            String conteudo = servico.gerarRecibo();
            String nomeArquivo = "recibo_" + idServico + "_" +
                    servico.getDataHora().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            pdfRecibo.gerarPDF(conteudo, nomeArquivo);
            System.out.println("Recibo gerado com sucesso: " + nomeArquivo + ".pdf");
        } catch (IOException e) {
            throw new RuntimeException("Falha ao gerar PDF do recibo: " + e.getMessage(), e);
        }
    }


    @Override
    public List<Servico> listarServicosPendentes() {
        return servicos.values().stream()
                .filter(servico -> !servico.isPago())  // Filtra apenas não pagos
                .collect(Collectors.toList());
    }

    public List<Servico> listarTodosServicos() {
        return new ArrayList<>(servicos.values());
    }

    private Servico buscarServicoPorId(int id) {
        Servico servico = servicos.get(id);
        if (servico == null) throw new ServicoNaoCadastradoException("Serviço não encontrado: " + id);
        return servico;
    }

    public void marcarComoPago(int idServico) {
        Servico servico = buscarServicoPorId(idServico);
        servico.setPago(true);
        salvarDados();
    }

}
