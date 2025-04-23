package ProjetoPetShop.system.petlover;

import ProjetoPetShop.data.GravadorDadosPets;
import ProjetoPetShop.data.GravadorDadosTutores;
import ProjetoPetShop.entities.Animal;
import ProjetoPetShop.entities.Tutor;
import ProjetoPetShop.exception.TutorCadastrado;
import ProjetoPetShop.exception.AnimalNaoExiste;
import ProjetoPetShop.exception.TutorNaoEncontradoException;

import java.io.IOException;
import java.util.*;

/**
 * Implementação concreta do gerenciador de pets e tutores do sistema PetShop.
 * Gerencia o cadastro e relacionamento entre animais e seus tutores.
 */
public class PetLoverMap implements PetLoverInterface {

    private Map<Integer, Animal> animais;
    private Map<String, Tutor> tutores;
    private int proximoIdAnimal;
    private final GravadorDadosPets gravadorAnimais;
    private final GravadorDadosTutores gravadorTutores;


    public PetLoverMap(){
        this.gravadorAnimais = new GravadorDadosPets("data/animais.dat");
        this.gravadorTutores = new GravadorDadosTutores("data/tutores.dat");

        this.tutores = carregarTutores();
        this.animais = carregarAnimais();

        this.proximoIdAnimal = animais.keySet().stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0) + 1;

        Thread shutdownThread = new Thread(() -> {
            System.out.println("[SHUTDOWN HOOK] Salvando dados antes de fechar...");
            salvarDados();
        });
        Runtime.getRuntime().addShutdownHook(shutdownThread);

    }

    private Map<String, Tutor> carregarTutores() {
        try {
            return gravadorTutores.recuperaDadosTutores();
        } catch (IOException e) {
            System.out.println("Arquivo de tutores não encontrado. Iniciando com dados vazios.");
            return new HashMap<>();
        }
    }

    private Map<Integer, Animal> carregarAnimais() {
        try {
            Map<String, Animal> animaisMap = gravadorAnimais.recuperaDadosAnimais();
            Map<Integer, Animal> animaisPorId = new HashMap<>();
            animaisMap.forEach((key, animal) -> animaisPorId.put(animal.getCodigo(), animal));
            return animaisPorId;
        } catch (IOException e) {
            System.out.println("Arquivo de animais não encontrado. Iniciando com dados vazios.");
            return new HashMap<>();
        }
    }

    public void salvarDados() {
        try {
            // Converte Map<Integer, Animal> para Map<String, Animal>
            Map<String, Animal> animaisParaSalvar = new HashMap<>();
            animais.forEach((id, animal) -> animaisParaSalvar.put(String.valueOf(id), animal));

            gravadorAnimais.salvarDadosAnimais(animaisParaSalvar);
            gravadorTutores.salvarDadosTutores(tutores);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
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

        this.proximoIdAnimal = proximoIdAnimal+1;
        salvarDados();
    }


    @Override
    public Animal buscarAnimalPorID(int id) {
        return animais.get(id);
    }

    @Override
    public List<Animal> listarAnimaisPorTutor(String cpf) throws TutorNaoEncontradoException {
        if(this.tutores.containsKey(cpf)){
            return this.tutores.get(cpf).getAnimais();
        } else {
            throw new TutorNaoEncontradoException("O tutor com o cpf "+cpf+" não foi encontrado");
        }
    }

    @Override
    public void removerAnimal(int id) {
        Animal animal = animais.get(id);
        if (animal != null) {
            // Remove do mapa principal
            animais.remove(id);

            // Remove da lista do tutor
            Tutor tutor = animal.getTutor();
            if (tutor != null && tutor.getAnimais() != null) {
                tutor.getAnimais().remove(animal);
            }

            salvarDados();
        }
    }

    @Override
    public Collection<Animal> listarTodosAnimais() {
        return (Collection<Animal>) this.animais.values();
    }


    @Override
    public void atualizarAnimal(Animal animal) {
        if (!animais.containsKey(animal.getCodigo())) {
            throw new AnimalNaoExiste("Animal não encontrado!");
        }
        animais.put(animal.getCodigo(), animal);
        salvarDados();
    }

    /** Métodos de Tutores */

    @Override
    public void cadastrarTutor(Tutor tutor) {
        if (tutores.containsKey(tutor.getCpf())) {
            throw new TutorCadastrado("Tutor já cadastrado!");
        }
        tutores.put(tutor.getCpf(), tutor);
        salvarDados();
    }

    @Override
    public Tutor buscarTutorPorCpf(String cpf) {
        return tutores.get(cpf);
    }

    @Override
    public List<Tutor> listarTodosTutores() {
        return new ArrayList<>(tutores.values());
    }

    public boolean removerTutor(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio!");
        }

        Tutor tutorRemovido = tutores.remove(cpf);
        if (tutorRemovido != null) {
            // Verifica se há animais antes de tentar remover
            if (tutorRemovido.getAnimais() != null) {
                // Cria uma cópia da lista para evitar ConcurrentModificationException
                List<Animal> animaisDoTutor = new ArrayList<>(tutorRemovido.getAnimais());
                for (Animal animal : animaisDoTutor) {
                    animais.remove(animal.getCodigo());
                }
            }
            salvarDados();
            return true;
        }
        return false;
    }

    public boolean atualizarTutor(Tutor tutor) {
        if (tutor == null) {
            throw new IllegalArgumentException("Tutor não pode ser nulo!");
        }

        if (!tutores.containsKey(tutor.getCpf())) {
            return false;
        }

        tutores.put(tutor.getCpf(), tutor);
        salvarDados();
        return true;
    }


}

