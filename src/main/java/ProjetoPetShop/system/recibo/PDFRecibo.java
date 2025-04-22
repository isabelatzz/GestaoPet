package ProjetoPetShop.system.recibo;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PDFRecibo implements PDFReciboInterface {

    @Override
    public void gerarPDF(String conteudo, String nomeArquivo) throws IOException {
        // Define pasta de saída dentro do diretório de execução
        Path outputDir = Paths.get(System.getProperty("user.dir"), "recibos");
        if (Files.notExists(outputDir)) {
            Files.createDirectories(outputDir);
        }

        // Caminho final do arquivo
        Path pdfPath = outputDir.resolve(nomeArquivo + ".pdf");
        System.out.println("Salvando recibo em: " + pdfPath.toAbsolutePath());

        try (PDDocument document = new PDDocument()) {
            // Cria página e adiciona ao documento
            PDPage page = new PDPage();
            document.addPage(page);

            PDType0Font unicodeFont;

            // Carrega a fonte Unicode interna do PDFBox (LiberationSans)
            try (InputStream fontStream = PDFRecibo.class.getResourceAsStream(
                    "/org/apache/pdfbox/resources/ttf/LiberationSans-Regular.ttf")) {
                if (fontStream == null) {
                    throw new IOException("Fonte LiberationSans não encontrada no classpath");
                }
                unicodeFont = PDType0Font.load(document, fontStream, true);
            }


            // Abre o stream de conteúdo com try-with-resources
            try (PDPageContentStream cs = new PDPageContentStream(document, page)) {
                cs.setFont(unicodeFont, 12);
                cs.beginText();
                cs.newLineAtOffset(50, 700);

                try {
                    // Escreve cada linha; se falhar aqui, endText() ainda será chamado
                    for (String linha : conteudo.split("\n")) {
                        if (!linha.trim().isEmpty()) {
                            cs.showText(linha);
                            cs.newLineAtOffset(0, -15);
                        }
                    }
                } finally {
                    cs.endText();
                }
            }

            // Salva e fecha o documento
            document.save(pdfPath.toString());
        }
        // 3) Abre o PDF no aplicativo padrão (pode ser o navegador)
        Path abs = pdfPath.toAbsolutePath();
        if (!Files.exists(abs)) {
            throw new IOException("PDF não encontrado em: " + abs);
        }
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(abs.toFile());
        } else {
            System.err.println("API Desktop não suportada; abra manualmente: " + abs);
        }
    }
    }

