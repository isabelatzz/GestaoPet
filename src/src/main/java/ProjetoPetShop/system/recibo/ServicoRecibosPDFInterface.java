package ProjetoPetShop.system.recibo;

import ProjetoPetShop.entities.Servico;

import java.io.IOException;

/**
 * Interface que define os serviços para geração de recibos em formato PDF.
 * <p>
 * Esta interface estabelece o contrato para classes que implementam a geração de
 * recibos, incluindo formatação do conteúdo e criação de arquivos PDF.
 * </p>
 */
public interface ServicoRecibosPDFInterface {

    /**
     * Gera um arquivo PDF de recibo para um serviço específico.
     * <p>
     * Este método coordena todo o processo de geração do recibo, desde a formatação
     * do conteúdo até a criação do arquivo PDF final.
     * </p>
     *
     * @param servico O objeto Servico contendo os dados necessários para gerar o recibo
     * @throws IllegalStateException Se ocorrer algum erro durante o processo de geração
     */
    void gerarReciboPDF(Servico servico);

    /**
     * Cria um arquivo PDF a partir de um conteúdo textual especificado.
     * <p>
     * Implementações deste método devem tratar a conversão do texto para formato PDF
     * e salvar o arquivo no sistema com o nome fornecido.
     * </p>
     *
     * @param conteudoRecibo O conteúdo textual a ser convertido para PDF
     * @param nomeArquivo O nome do arquivo PDF a ser gerado (sem extensão)
     * @throws IOException Se ocorrer um erro de I/O durante a criação do arquivo
     * @throws IllegalArgumentException Se o conteúdo ou nome do arquivo forem inválidos
     */
    void gerarPDF(String conteudoRecibo, String nomeArquivo) throws IOException;

    /**
     * Formata os dados de um serviço em um texto estruturado para compor o recibo.
     * <p>
     * Este método deve organizar as informações do serviço em um layout adequado
     * para impressão em recibo, incluindo dados do prestador, cliente, valores e
     * descrição do serviço.
     * </p>
     *
     * @param servico O objeto Servico contendo os dados a serem formatados
     * @return String com o conteúdo formatado do recibo
     * @throws IllegalArgumentException Se o objeto Servico for nulo ou contiver dados inválidos
     */
    String gerarReciboFormatado(Servico servico);
}