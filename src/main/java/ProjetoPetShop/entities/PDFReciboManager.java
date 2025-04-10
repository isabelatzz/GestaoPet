package ProjetoPetShop.entities;

import java.io.IOException;

public interface PDFReciboManager {
    void gerarPDF(String conteudoRecibo, String nomeArquivo) throws IOException;
}
