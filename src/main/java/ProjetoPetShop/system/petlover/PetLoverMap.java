package ProjetoPetShop.system.petlover;

import ProjetoPetShop.entities.Animal;
import ProjetoPetShop.entities.Tutor;
import ProjetoPetShop.exception.TutorCadastrado;
import ProjetoPetShop.exception.AnimalNaoExiste;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementação concreta do gerenciador de pets e tutores do sistema PetShop.
 * Gerencia o cadastro e relacionamento entre animais e seus tutores.
 */
public class PetLoverMap implements PetLoverInterface {

    private Map<Integer, Animal> animais;
    private Map<String, Tutor> tutores;
    private int proximoIdAnimal;


    public PetLoverMap(){
        this.animais = new HashMap<>();
        this.tutores = new HashMap<>();
        this.proximoIdAnimal = 1;
    }

    /** Métodos de Animais */

    @Override
    public void cadastrarAnimal (Animal animal){
        if (animal.getTutor() == null || !tutores.containsKey(animal.getTutor().getCpf())) {
            {
                throw new IllegalArgumentException("Tutor não cadastrado!");
            }
        }
        animal.setCodigo(proximoIdAnimal);
        animais.put(proximoIdAnimal, animal);

        Tutor tutor = animal.getTutor();
        if (tutor.getAnimais() == null) {
            tutor.setAnimais(new ArrayList<>());
        }
        tutor.getAnimais().add(animal);

        proximoIdAnimal++;
    }


    @Override
    public Animal buscarAnimalPorID(int id) {
        return animais.get(id);
    }

    @Override
    public List<Animal> listarAnimaisPorTutor(String cpf) {
        Tutor tutor = tutores.get(cpf);
        if (tutor == null) {
            return new ArrayList<>();
        }
        return tutor.getAnimais();
    }

    @Override
    public void removerAnimal(int id) {
        Animal animal = animais.remove(id);
        if (animal != null) {
            Tutor tutor = animal.getTutor();
            tutor.getAnimais().remove(animal);
        }
    }

    @Override
    public List<Animal> listarTodosAnimais() {
        return new ArrayList<>(animais.values());
    }


    @Override
    public void atualizarAnimal(Animal animal) {
        if (!animais.containsKey(animal.getCodigo())) {
            throw new AnimalNaoExiste("Animal não encontrado!");
        }
        animais.put(animal.getCodigo(), animal);
    }

    /** Métodos de Tutores */

    @Override
    public void cadastrarTutor(Tutor tutor) {
        if (tutores.containsKey(tutor.getCpf())) {
            throw new TutorCadastrado("Tutor já cadastrado!");
        }
        tutores.put(tutor.getCpf(), tutor);
    }

    @Override
    public Tutor buscarTutorPorCpf(String cpf) {
        return tutores.get(cpf);
    }

    @Override
    public List<Tutor> listarTodosTutores() {
        return new ArrayList<>(tutores.values());
    }


    }

