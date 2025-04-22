package ProjetoPetShop.controller;

import ProjetoPetShop.system.petlover.PetLoverMap;
import ProjetoPetShop.entities.Animal;
import ProjetoPetShop.entities.Tutor;
import ProjetoPetShop.exception.AnimalNaoExiste;

import java.util.List;

public class AnimalController {
    private final PetLoverMap petLoverMap;

    public AnimalController(PetLoverMap petLoverMap) {
        this.petLoverMap = petLoverMap;
    }

    public void cadastrarAnimal(String nome, String especie, String raca, int idade, String cpfTutor) {
        try {
            Tutor tutor = petLoverMap.buscarTutorPorCpf(cpfTutor);
            if (tutor == null) {
                throw new IllegalArgumentException("Tutor não encontrado!");
            }

            Animal animal = new Animal(0, nome, especie, raca, idade, tutor);
            petLoverMap.cadastrarAnimal(animal);

        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao cadastrar animal: " + e.getMessage());
        }
    }

    public Animal buscarAnimal(int id) {
        Animal animal = petLoverMap.buscarAnimalPorID(id);
        if (animal == null) {
            throw new AnimalNaoExiste("Animal não encontrado com ID: " + id);
        }
        return animal;
    }

    public List<Animal> listarAnimaisPorTutor(String cpfTutor) {
        return petLoverMap.listarAnimaisPorTutor(cpfTutor);
    }

    public List<Animal> listarTodosAnimais() {
        return petLoverMap.listarTodosAnimais();
    }

    public boolean removerAnimal(int id) {
        try {
            petLoverMap.removerAnimal(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void atualizarAnimal(Animal animal) {
        try {
            petLoverMap.atualizarAnimal(animal);
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao atualizar animal: " + e.getMessage());
        }
    }

}