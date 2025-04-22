package ProjetoPetShop.system.servico;

import ProjetoPetShop.entities.Servico;
import ProjetoPetShop.exception.*;

import java.io.IOException;
import java.util.Collection;

/**
 * Interface que define os serviços para gestão de serviços de petshop.
 * <p>
 * Esta interface estabelece o contrato para operações relacionadas ao cadastro,
 * consulta e gerenciamento de serviços oferecidos pelo petshop, como banho, tosa,
 * consultas veterinárias e outros.
 * </p>
 */
public interface ServicoInterface {

    /**
     * Cadastra um novo serviço no sistema.
     *
     * @param servico Objeto Servico contendo todos os dados necessários
     * @throws ServicoJaExisteException Se já existir um serviço com o mesmo ID
     */
    void cadastrarServico(Servico servico) throws ServicoJaExisteException;

    /**
     * Recupera todos os serviços cadastrados no sistema.
     *
     * @return Coleção de todos os serviços
     * @throws NaoHaServicosCadastradoException Se não houver serviços cadastrados
     */
    Collection<Servico> getServicos() throws NaoHaServicosCadastradoException;

    /**
     * Obtém um serviço específico pelo seu ID.
     *
     * @param id Identificador único do serviço
     * @return Objeto Servico correspondente ao ID
     * @throws ServicoNaoExisteException Se não existir serviço com o ID informado
     */
    Servico getServico(int id) throws ServicoNaoExisteException;

    /**
     * Atualiza os dados de um serviço existente.
     *
     * @param id Identificador único do serviço a ser atualizado
     * @param servico Objeto Servico com os dados atualizados
     * @throws ServicoNaoExisteException Se não existir serviço com o ID informado
     */
    void atualizarServico(int id, Servico servico) throws ServicoNaoExisteException;

    /**
     * Lista todos os serviços agendados para o dia atual.
     *
     * @return Coleção de serviços do dia
     * @throws NaoHaServicosDoDiaException Se não houver serviços agendados para hoje
     */
    Collection<Servico> listarServicosDoDia() throws NaoHaServicosDoDiaException;

    /**
     * Lista todos os serviços com status "pendente".
     *
     * @return Coleção de serviços pendentes
     * @throws NaoHaServicosPendentesException Se não houver serviços pendentes
     */
    Collection<Servico> listarServicosPendentes() throws NaoHaServicosPendentesException;

    /**
     * Remove um serviço do sistema.
     *
     * @param id Identificador único do serviço a ser removido
     * @throws ServicoNaoExisteException Se não existir serviço com o ID informado
     */
    void removerServico(int id) throws ServicoNaoExisteException;

    /**
     * Persiste todos os serviços em armazenamento permanente.
     *
     * @throws IOException Se ocorrer erro durante a operação de I/O
     */
    void gravarServicos() throws IOException;

    /**
     * Recupera todos os serviços do armazenamento permanente.
     *
     * @throws IOException Se ocorrer erro durante a operação de I/O
     */
    void recuperarServicos() throws IOException;
}
