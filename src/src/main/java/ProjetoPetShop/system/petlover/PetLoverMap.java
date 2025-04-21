package ProjetoPetShop.system.petlover;

import ProjetoPetShop.entities.Animal;
import ProjetoPetShop.entities.Tamanho;
import ProjetoPetShop.entities.Tutor;
import ProjetoPetShop.exception.*;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PetLoverMap implements PetLoverInterface {
    private Map<String, Tutor> tutores; //cpf é a chave

    public PetLoverMap() {
        this.tutores = new HashMap<>();
    }

    @Override
    public void cadastrarTutor(String nome, String cpf, String telefone, String endereco, String email) throws TutorJaExisteException {
        if(this.tutores.containsKey(cpf)) throw new TutorJaExisteException("O tutor com o cpf "+cpf+" já está cadastrado");
        this.tutores.put(cpf,new Tutor(nome,cpf,telefone,endereco,email));
    }

    @Override
    public void cadastrarAnimal(String cpf, String nome, String especie, String raca, Tamanho tamanho, int idade) throws TutorNaoExisteException, AnimalJaExisteException {
        if(!this.tutores.containsKey(cpf)) throw new TutorNaoExisteException("Não há um tutor com o cpf "+cpf+" cadastrado");
        this.tutores.get(cpf).cadastrarAnimal(nome,especie,raca,tamanho,idade, this.tutores.get(cpf));
    }

    @Override
    public Collection<Animal> getAnimaisDeTutor(String cpfDoTutor) throws TutorNaoExisteException {
        Tutor t = this.tutores.get(cpfDoTutor);
        if(t == null) throw new TutorNaoExisteException("O tutor com o cpf "+cpfDoTutor+" não está cadastrado");
        if(t.getAnimais().isEmpty()) throw new NaoHaAnimaisCadastradosException("Não há animais cadastrados para esse tutor");
        return t.getAnimais();
    }

    @Override
    public Collection<Tutor> getTodosOsTutores() throws NaoHaTutoresCadastradosException {
        if(this.tutores.isEmpty()) throw new NaoHaTutoresCadastradosException("Não há tutores cadastrados no sistema");
        return this.tutores.values();
    }

    @Override
    public Animal getAnimalDoTutor(String cpfDoTutor, String nomeAnimal) throws TutorNaoExisteException {
        if(!this.tutores.containsKey(cpfDoTutor)) throw new TutorNaoExisteException("O tutor com o cpf "+cpfDoTutor+ "não está cadastrado");
        return this.tutores.get(cpfDoTutor).getAnimal(nomeAnimal);
    }

    @Override
    public Tutor getTutor(String cpf) throws TutorNaoExisteException {
        Tutor t = this.tutores.get(cpf);
        if(t == null) throw new TutorNaoExisteException("Não há tutor cadastrado com o cpf "+cpf);
        else return t;
    }

    @Override
    public void atualizarAnimal(String cpf, String nome, Animal animalParaAtualizar) throws TutorNaoExisteException, AnimalNaoExisteException {
        if(this.tutores.containsKey(cpf)){
            for(Animal a: this.tutores.get(cpf).getAnimais()){
                if(a.getNome().equalsIgnoreCase(nome)){
                    a.atualizarAnimal(animalParaAtualizar);
                    return;
                }
                throw new AnimalNaoExisteException("O animal de nome "+nome+ " do tutor com o cpf "+cpf+" não existe");
            }
        } else {
            throw new TutorNaoExisteException("O tutor do animal que você quer atualzar não existe");
        }
    }

    @Override
    public void atualizarTutor(String cpf, Tutor tudorParaAtualizar) throws TutorNaoExisteException {
        if(this.tutores.containsKey(cpf)){

        } else {
            throw new TutorNaoExisteException("O tutor que você quer atualizar não existe");
        }
    }

    @Override
    public void removerTutor(String cpf) throws TutorNaoExisteException {
        if(this.tutores.containsKey(cpf)) this.tutores.remove(cpf);
        else throw new TutorNaoExisteException("O tutor com o cpf "+cpf+" não existe");
    }

    @Override
    public void removerAnimal(String cpf, String nome) throws TutorNaoExisteException {
        if(this.tutores.containsKey(cpf)){
            this.tutores.get(cpf).removerAnimal(nome);
        } else {
            throw new TutorNaoExisteException("O tutor não existe");
        }
    }

    @Override
    public void gravarTutoresERespectivosAnimais() throws IOException {

    }

    @Override
    public void recuperarTutoresERecpectivosAnimais() throws IOException {

    }
}

