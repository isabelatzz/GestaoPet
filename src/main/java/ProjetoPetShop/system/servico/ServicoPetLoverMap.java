package ProjetoPetShop.system.servico;

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

    public ServicoPetLoverMap() {
        this.servicos = new HashMap<>();
        this.proximoIdServico = 1;
    }

    public void adicionarServico(Servico servico) {
        servico.setId(proximoIdServico);
        servicos.put(proximoIdServico, servico);
        proximoIdServico++;
    }

    @Override
    public void cadastrarServico(Servico servico) {
        if (servico == null) {
            throw new IllegalArgumentException("Serviço não pode ser nulo");
        }

        if (servico.getAnimal() == null) {
            throw new AnimalNaoExiste("Animal associado ao serviço não foi informado");
        }

        if (servico.getAnimal().getTutor() == null) {
            throw new TutorNaoEncontradoException("Tutor do animal não foi informado");
        }
        if (servico.getId() == 0) {
            servico.setId(proximoIdServico++);
        }
        if (servicos.containsKey(servico.getId())) {
            throw new ServicoJaCadastradoException("Já existe um serviço cadastrado com o ID: " + servico.getId());
        }

        servicos.put(servico.getId(), servico);
    }

    @Override
    public String gerarRecibo(int idServico) {
        Servico servico = servicos.get(idServico);
        if (servico == null) {
            throw new ServicoNaoCadastradoException("Serviço não encontrado!");
        }
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

}
