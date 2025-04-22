package ProjetoPetShop.system.servico;

import ProjetoPetShop.entities.Servico;
import java.util.List;

/**
 * Operações básicas para gerenciar serviços veterinários.
 */
public interface ServicoInterface {
    /**
     * Gera um recibo em formato texto para o serviço especificado.
     *
     * @param idServico o ID do serviço a ser recuperado
     * @return string formatada contendo os detalhes do recibo
     * @throws ProjetoPetShop.exception.ServicoNaoCadastradoException se nenhum serviço for encontrado com o ID fornecido
     * @throws IllegalArgumentException se o ID for inválido (≤ 0)
     */
    String gerarRecibo (int idServico);

    /**
     * Gera e salva um recibo em formato PDF para o serviço especificado.
     *
     * <p>Implementação padrão vazia - classes concretas devem sobrescrever.</p>
     *
     * @param idServico o ID do serviço
     * @throws ProjetoPetShop.exception.ServicoNaoCadastradoException se o serviço não existir
     * @throws IllegalStateException se ocorrer erro na geração do PDF
     */
    default void gerarReciboPDF(int idServico) {
    }

    /**
     * Recupera uma lista de todos os serviços não pagos (pendentes).
     *
     * @return lista imutável de serviços pendentes, ordenados por data
     * @implNote Implementações devem garantir que a lista retornada seja imutável
     */
    List<Servico> listarServicosPendentes ();

    /**
     * Adiciona um novo serviço ao sistema com ID automático.
     *
     * @param servico o serviço a ser adicionado
     * @throws IllegalArgumentException se o serviço for nulo ou inválido
     * @throws IllegalStateException se ocorrer erro no armazenamento
     */
    void adicionarServico(Servico servico);

    /**
     * Cadastra um serviço com validação completa.
     *
     * <p>Difere de {@link #adicionarServico(Servico)} por realizar validações
     * adicionais antes do cadastro.</p>
     *
     * @param servico o serviço a ser cadastrado
     * @throws ProjetoPetShop.exception.AnimalNaoExiste se o animal associado não existir
     * @throws ProjetoPetShop.exception.TutorNaoEncontradoException se o tutor não estiver cadastrado
     * @throws ProjetoPetShop.exception.ServicoJaCadastradoException se o ID já estiver em uso
     */
    void cadastrarServico(Servico servico);

    /**
     * Recupera todos os serviços cadastrados no sistema.
     *
     * @return lista imutável contendo todos os serviços
     * @implSpec A implementação deve:
     * <ul>
     *   <li>Retornar lista vazia se não houver serviços</li>
     *   <li>Garantir que a lista seja imutável</li>
     *   <li>Manter a ordem de inserção</li>
     * </ul>
     */
    List<Servico> listarTodosServicos();

    private Servico buscarServicoPorId(int id) {
        return null;
    }

    private void validarServico(Servico servico) {
    }
}
