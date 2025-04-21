package ProjetoPetShop.system.petlover;

import ProjetoPetShop.entities.Animal;
import ProjetoPetShop.entities.Tamanho;
import ProjetoPetShop.entities.Tutor;
import ProjetoPetShop.exception.*;

import java.io.IOException;
import java.util.Collection;

/**
 * Interface que define os serviços para gestão de tutores e animais de estimação.
 * <p>
 * Esta interface estabelece o contrato para o sistema de gerenciamento de clientes (tutores)
 * e seus pets, incluindo operações CRUD completas e persistência de dados.
 * </p>
 */
public interface PetLoverInterface {

    /**
     * Cadastra um novo tutor no sistema.
     *
     * @param nome Nome completo do tutor
     * @param cpf CPF do tutor (formato livre, mas deve ser único)
     * @param telefone Telefone para contato
     * @param endereco Endereço completo
     * @param email E-mail válido
     * @throws TutorJaExisteException Se já existir um tutor cadastrado com o mesmo CPF
     */
    void cadastrarTutor(String nome, String cpf, String telefone, String endereco, String email) throws TutorJaExisteException;

    /**
     * Cadastra um novo animal associado a um tutor existente.
     *
     * @param cpf CPF do tutor responsável
     * @param nome Nome do animal
     * @param especie Espécie do animal (ex: "Cachorro", "Gato")
     * @param raca Raça do animal
     * @param tamanho Porte do animal (PEQUENO, MEDIO, GRANDE)
     * @param idade Idade em anos
     * @throws TutorNaoExisteException Se não existir tutor com o CPF informado
     * @throws AnimalJaExisteException Se o tutor já tiver um animal com o mesmo nome
     */
    void cadastrarAnimal(String cpf, String nome, String especie, String raca, Tamanho tamanho, int idade) throws TutorNaoExisteException, AnimalJaExisteException;

    /**
     * Recupera todos os animais associados a um tutor.
     *
     * @param cpfDoTutor CPF do tutor
     * @return Coleção de animais do tutor (pode ser vazia se não houver animais)
     * @throws TutorNaoExisteException Se não existir tutor com o CPF informado
     * @throws NaoHaAnimaisCadastradosException Se o tutor não tiver animais cadastrados
     */
    Collection<Animal> getAnimaisDeTutor(String cpfDoTutor) throws TutorNaoExisteException, NaoHaAnimaisCadastradosException;

    /**
     * Lista todos os tutores cadastrados no sistema.
     *
     * @return Coleção de todos os tutores
     * @throws NaoHaTutoresCadastradosException Se não houver tutores cadastrados
     */
    Collection<Tutor> getTodosOsTutores() throws NaoHaTutoresCadastradosException;

    /**
     * Recupera um animal específico de um tutor.
     *
     * @param cpfDoTutor CPF do tutor
     * @param nomeAnimal Nome do animal a ser recuperado
     * @return Objeto Animal correspondente
     * @throws TutorNaoExisteException Se não existir tutor com o CPF informado
     */
    Animal getAnimalDoTutor(String cpfDoTutor, String nomeAnimal) throws TutorNaoExisteException;

    /**
     * Recupera um tutor pelo CPF.
     *
     * @param cpf CPF do tutor
     * @return Objeto Tutor correspondente
     * @throws TutorNaoExisteException Se não existir tutor com o CPF informado
     */
    Tutor getTutor(String cpf) throws TutorNaoExisteException;

    /**
     * Atualiza os dados de um animal existente.
     *
     * @param cpf CPF do tutor responsável
     * @param nome Nome atual do animal
     * @param animalParaAtualizar Objeto Animal com os novos dados
     * @throws TutorNaoExisteException Se não existir tutor com o CPF informado
     * @throws AnimalNaoExisteException Se não existir animal com o nome informado
     */
    void atualizarAnimal(String cpf, String nome, Animal animalParaAtualizar) throws TutorNaoExisteException, AnimalNaoExisteException;

    /**
     * Atualiza os dados de um tutor existente.
     *
     * @param cpf CPF do tutor a ser atualizado
     * @param tudorParaAtualizar Objeto Tutor com os novos dados
     * @throws TutorNaoExisteException Se não existir tutor com o CPF informado
     */
    void atualizarTutor(String cpf, Tutor tudorParaAtualizar) throws TutorNaoExisteException;

    /**
     * Remove um tutor e todos seus animais associados do sistema.
     *
     * @param cpf CPF do tutor a ser removido
     * @throws TutorNaoExisteException Se não existir tutor com o CPF informado
     */
    void removerTutor(String cpf) throws TutorNaoExisteException;

    /**
     * Remove um animal específico de um tutor.
     *
     * @param cpf CPF do tutor
     * @param nome Nome do animal a ser removido
     * @throws TutorNaoExisteException Se não existir tutor com o CPF informado
     * @throws AnimalNaoExisteException Se não existir animal com o nome informado
     */
    void removerAnimal(String cpf, String nome) throws TutorNaoExisteException, AnimalNaoExisteException;

    /**
     * Persiste todos os tutores e seus respectivos animais em armazenamento permanente.
     *
     * @throws IOException Se ocorrer erro durante a operação de I/O
     */
    void gravarTutoresERespectivosAnimais() throws IOException;

    /**
     * Recupera todos os tutores e seus respectivos animais do armazenamento permanente.
     *
     * @throws IOException Se ocorrer erro durante a operação de I/O
     */
    void recuperarTutoresERecpectivosAnimais() throws IOException;

    /**
     * Área reservada para futuras expansões do sistema com serviços adicionais.
     * <p>
     * Métodos potenciais para implementação futura:
     * <ul>
     *   <li>Agendamento de consultas veterinárias</li>
     *   <li>Agendamento de serviços de banho</li>
     *   <li>Agendamento de serviços de tosa</li>
     *   <li>Gestão completa de serviços</li>
     * </ul>
     */
    /* Prováveis Futuros Métodos Para Serviços
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
