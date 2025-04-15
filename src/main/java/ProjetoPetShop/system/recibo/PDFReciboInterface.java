package ProjetoPetShop.system.recibo;

import java.io.IOException;

public interface PDFReciboInterface {
    void gerarPDF(String conteudoRecibo, String nomeArquivo) throws IOException;
}
