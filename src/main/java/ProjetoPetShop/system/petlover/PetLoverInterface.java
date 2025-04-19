package ProjetoPetShop.system.petlover;

import ProjetoPetShop.entities.Animal;
import ProjetoPetShop.entities.Tutor;

import java.util.List;
/**
 * Interface principal do sistema PetShop que define as operações para gestão
 * de animais e seus tutores.
 *
 * <p>Esta interface estabelece o contrato básico para todas as implementações
 * do sistema de gerenciamento PetLover, abrangendo:</p>
 *
 * <ul>
 *   <li>Cadastro e gestão de animais</li>
 *   <li>Cadastro e gestão de tutores</li>
 *   <li>Relações entre animais e tutores</li>
 * </ul>
 *
 * <p><b>Thread Safety:</b> Implementações devem documentar explicitamente
 * sua política de concorrência.</p>
 */
public interface PetLoverInterface {
    /* Métodos de Animais */

    /**
     * Cadastra um novo animal no sistema.
     *
     * @param animal o animal a ser cadastrado (não pode ser nulo)
     * @throws IllegalArgumentException se o animal for nulo ou inválido
     * @throws ProjetoPetShop.exception.TutorNaoEncontradoException se o tutor do animal não estiver cadastrado
     * @throws ProjetoPetShop.exception.AnimalExiste se o animal já estiver registrado
     */
    void cadastrarAnimal (Animal animal);

    /**
     * Recupera um animal pelo seu ID único.
     *
     * @param id o ID do animal a ser buscado
     * @return o animal correspondente ao ID, ou null se não encontrado
     * @throws IllegalArgumentException se o ID for inválido (≤ 0)
     */
    Animal buscarAnimalPorID (int id);

    /**
     * Lista todos os animais associados a um tutor específico.
     *
     * @param cpf o CPF do tutor (formato: 123.456.789-09)
     * @return lista imutável de animais do tutor (vazia se não houver)
     * @throws IllegalArgumentException se o CPF for nulo ou inválido
     * @implNote A lista retornada deve ser ordenada por nome do animal
     */
    List<Animal> listarAnimaisPorTutor (String cpf);

    /**
     * Remove um animal do sistema pelo seu ID.
     *
     * @param id o ID do animal a ser removido
     * @throws ProjetoPetShop.exception.AnimalNaoExiste se nenhum animal com o ID for encontrado
     * @throws IllegalStateException se o animal não puder ser removido
     */
    void removerAnimal (int id);

    /**
     * Recupera todos os animais cadastrados no sistema.
     *
     * @return lista imutável contendo todos os animais
     * @implSpec A implementação deve:
     * <ul>
     *   <li>Retornar lista vazia se não houver animais</li>
     *   <li>Garantir que a lista seja imutável</li>
     *   <li>Manter a ordem de inserção</li>
     * </ul>
     */
    List<Animal> listarTodosAnimais ();


    /**
     * Atualiza os dados de um animal existente.
     *
     * @param animal o animal com os dados atualizados (deve conter ID válido)
     * @throws ProjetoPetShop.exception.AnimalNaoExiste se o animal não estiver cadastrado
     * @throws IllegalArgumentException se o animal for nulo ou inválido
     */
    void atualizarAnimal (Animal animal);

    /* Métodos de Tutores */

    /**
     * Cadastra um novo tutor no sistema.
     *
     * @param tutor o tutor a ser cadastrado (não pode ser nulo)
     * @throws IllegalArgumentException se o tutor for nulo ou inválido
     * @throws ProjetoPetShop.exception.TutorCadastrado se já existir tutor com o mesmo CPF
     */
    void cadastrarTutor(Tutor tutor);

    /**
     * Recupera um tutor pelo seu CPF.
     *
     * @param cpf o CPF do tutor (formato: 123.456.789-09)
     * @return o tutor correspondente, ou null se não encontrado
     * @throws IllegalArgumentException se o CPF for nulo ou inválido
     */
    Tutor buscarTutorPorCpf(String cpf);

    /**
     * Recupera todos os tutores cadastrados no sistema.
     *
     * @return lista imutável contendo todos os tutores
     * @implSpec A implementação deve:
     * <ul>
     *   <li>Retornar lista vazia se não houver tutores</li>
     *   <li>Garantir que a lista seja imutável</li>
     *   <li>Ordenar por nome do tutor</li>
     * </ul>
     */
    List<Tutor> listarTodosTutores();

    /** Prováveis Futuros Métodos Para Serviços

    void agendarConsulta(Consulta consulta);
    void agendarBanho(Banho banho);
    void agendarTosa(Tosa tosa);
    Servico buscarServico(int id);
    void atualizarServico(Servico servico);
    void cancelarServico(int id);
    List<Servico> listarServicosPorAnimal(int idAnimal);
    List<Servico> listarServicosPorTutor(String cpfTutor);

     */
}
