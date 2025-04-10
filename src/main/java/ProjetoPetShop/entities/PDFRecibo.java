package ProjetoPetShop.entities;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PDFRecibo implements PDFReciboManager {

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
}