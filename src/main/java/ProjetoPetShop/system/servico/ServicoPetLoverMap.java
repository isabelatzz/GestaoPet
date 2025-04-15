package ProjetoPetShop.system.servico;

import ProjetoPetShop.entities.Servico;
import ProjetoPetShop.exception.AnimalNaoExiste;
import ProjetoPetShop.exception.ServicoJaCadastradoException;
import ProjetoPetShop.exception.ServicoNaoCadastradoException;
import ProjetoPetShop.exception.TutorNaoEncontradoException;
import ProjetoPetShop.system.recibo.PDFRecibo;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        // Validações iniciais
        if (servico == null) {
            throw new IllegalArgumentException("Serviço não pode ser nulo");
        }

        // Verifica se o animal existe
        if (servico.getAnimal() == null) {
            throw new AnimalNaoExiste("Animal associado ao serviço não foi informado");
        }

        // Verifica se o tutor existe
        if (servico.getAnimal().getTutor() == null) {
            throw new TutorNaoEncontradoException("Tutor do animal não foi informado");
        }

        // Atribui ID automático se não tiver
        if (servico.getId() == 0) {
            servico.setId(proximoIdServico++);
        }

        // Verifica se já existe serviço com este ID
        if (servicos.containsKey(servico.getId())) {
            throw new ServicoJaCadastradoException("Já existe um serviço cadastrado com o ID: " + servico.getId());
        }

        // Adiciona o serviço ao mapa
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
        return List.of();
    }

}
