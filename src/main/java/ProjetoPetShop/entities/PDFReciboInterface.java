package ProjetoPetShop.entities;

import java.io.IOException;

public interface PDFReciboInterface {
    void gerarPDF(String conteudoRecibo, String nomeArquivo) throws IOException;
}
