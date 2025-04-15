package ProjetoPetShop.system.petlover;

import ProjetoPetShop.entities.Animal;
import ProjetoPetShop.entities.Tutor;

import java.util.List;

public interface PetLoverInterface {
    /* Métodos de Animais */
    void cadastrarAnimal (Animal animal);
    Animal buscarAnimalPorID (int id);
    List<Animal> listarAnimaisPorTutor (String cpf);
    void removerAnimal (int id);
    List<Animal> listarTodosAnimais ();
    void atualizarAnimal (Animal animal);

    /* Métodos de Tutores */
    void cadastrarTutor(Tutor tutor);
    Tutor buscarTutorPorCpf(String cpf);
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
