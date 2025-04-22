package ProjetoPetShop.system.recibo;


import ProjetoPetShop.entities.Animal;
import ProjetoPetShop.entities.Servico;
import ProjetoPetShop.exception.ServicoNaoCadastradoException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServicoRecibosPDFSystem implements ServicoRecibosPDFInterface {
    public static LocalDateTime dataDeHoje = LocalDateTime.now();


    @Override
    public void gerarReciboPDF(Servico servico) {
        if (servico == null) {
            throw new ServicoNaoCadastradoException("Serviço com ID " + servico.getId() + "não existe");
        }
        try {
            String reciboFormatado = gerarReciboFormatado(servico);
            String nomeArquivo = "recibo_" + servico.getId() + "_" + this.dataDeHoje.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            gerarPDF(reciboFormatado, nomeArquivo);
            System.out.println("Recibo gerado com sucesso: " + nomeArquivo + ".pdf");
        } catch (IOException e) {
            throw new RuntimeException("Falha ao gerar PDF do recibo: " + e.getMessage(), e);
        }
    }

    @Override
    public void gerarPDF(String conteudoRecibo, String nomeArquivo) throws IOException {

        Path outputDir = Paths.get("recibos");
        outputDir.toFile().mkdirs();

        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            try (PDPageContentStream content = new PDPageContentStream(doc, page)) {
                // Configuração básica
                content.setFont(PDType1Font.HELVETICA, 12);
                float y = 700; // Posição Y inicial

                // Processa cada linha corretamente
                String[] linhas = conteudoRecibo.split("\\r?\\n");

                for (String linha : linhas) {
                    // Remove caracteres não suportados
                    String linhaLimpa = linha.replaceAll("[^\\x00-\\x7F]", "");

                    if (!linhaLimpa.isEmpty()) {
                        content.beginText();
                        content.newLineAtOffset(50, y);
                        content.showText(linhaLimpa);
                        content.endText(); // Fecha cada bloco de texto
                        y -= 15; // Espaçamento entre linhas
                    }
                }
            }

            // Salva o documento
            doc.save("recibos/" + nomeArquivo + ".pdf");
        }
    }

    public String gerarReciboFormatado(Servico servico){
        Animal animal = servico.getAnimal();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format(
                "PetLover\n" +
                        "----------------------------------\n" +
                        "RECIBO DE ATENDIMENTO\n" +
                        "Data/Hora: %s\n" +
                        "Animal: %s (%s)\n" +
                        "Tutor: %s\n" +
                        "CPF Tutor: %s\n" +
                        "----------------------------------\n" +
                        "Serviço: %s\n" +
                        "Responsável: %s\n" +
                        "Valor Base: R$ %.2f\n" +
                        "%s" +  // Detalhes específicos do serviço
                        "----------------------------------\n" +
                        "VALOR TOTAL: R$ %.2f\n" +
                        "Status: %s\n" +
                        "----------------------------------\n" +
                        "Assinatura do Veterinário: ________________\n",
                servico.getDataMarcada(),
                animal.getNome(),
                animal.getEspecie(),
                animal.getTutor().getNome(),
                animal.getTutor().getCpf(),
                this.getClass().getSimpleName(),
                servico.getFuncionario(),
                servico.getValorBase(),
                servico.getDetalhesServico(),
                servico.calcularValorTotal(),
                servico.isPago() ? "PAGO" : "PENDENTE"
        );
    }
}