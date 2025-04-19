package ProjetoPetShop.system.recibo;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PDFRecibo implements PDFReciboInterface {
    public void gerarPDF(String conteudo, String nomeArquivo) throws IOException {

        Path outpudir = Paths.get("recibos");
        outpudir.toFile().mkdirs();

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Configuração da fonte e tamanho
                contentStream.setFont(PDType1Font.COURIER, 10);
                contentStream.beginText();

                // Posição inicial (PDFBox usa coordenadas de baixo para cima)
                contentStream.newLineAtOffset(50, 700);

                // Dividir o conteúdo por linhas e adicionar ao PDF
                String[] linhas = conteudo.split("\n");
                for (String linha : linhas) {
                    contentStream.showText(linha);
                    contentStream.newLineAtOffset(0, -15); // Move para a próxima linha
                }

                contentStream.endText();
            }

            // Salva o documento
            document.save("recibos/" + nomeArquivo + ".pdf");
        }
    }
}