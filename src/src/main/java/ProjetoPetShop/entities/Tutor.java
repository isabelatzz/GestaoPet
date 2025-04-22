package ProjetoPetShop.entities;

import ProjetoPetShop.exception.AnimalJaExisteException;
import ProjetoPetShop.exception.AnimalNaoExisteException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa o Tutor do animal de estimação no sistema do PetLover.
 * Cada Tutor está associado a um Animal.
 */


public class Tutor implements Serializable {
    private String nome;
    private String cpf;
    private String telefone;
    private String endereço;
    private String email;
    List<Animal> animais;

    public Tutor(String nome, String cpf, String telefone, String endereço, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereço = endereço;
        this.email = email;
        this.animais = new ArrayList<>();
    }

    public void atualizarTutor(Tutor tutorParaAtualizar){
        if(tutorParaAtualizar.nome != null){
            this.nome = tutorParaAtualizar.getNome();
        } else if (tutorParaAtualizar.getCpf() != null){
            this.cpf = tutorParaAtualizar.getCpf();
        } else if (tutorParaAtualizar.getTelefone() != null){
            this.telefone = tutorParaAtualizar.getTelefone();
        } else if (tutorParaAtualizar.getEndereço() != null){
            this.endereço = tutorParaAtualizar.getEndereço();
        } else if (tutorParaAtualizar.getEmail() != null){
            this.email = tutorParaAtualizar.getEmail();
        }
    }

    public void cadastrarAnimal(String nome, String especie, String raca, Tamanho tamanho, int idade, Tutor tutor) throws AnimalJaExisteException{
        Animal animalCadastro = new Animal(this.animais.size(),nome,especie,raca,tamanho,idade, tutor);
        if(this.animais.isEmpty()){
            this.animais.add(animalCadastro);
        } else {
            for(Animal a: this.animais){
                if(a.getNome().equalsIgnoreCase(nome)){
                    throw new AnimalJaExisteException("O pet: "+nome+" do tutor "+this.nome+" já está cadastrado");
                }
            }
            this.animais.add(animalCadastro);
        }
    }

    public Animal getAnimal(String nome) throws AnimalNaoExisteException{
        for(Animal a: this.animais){
            if(a.getNome().equalsIgnoreCase(nome)){
                return a;
            }
        }
        throw new AnimalNaoExisteException("O pet "+nome+" do tutor "+this.nome+" cpf "+this.cpf+" não existe");
    }

    public void removerAnimal(String nome) throws AnimalNaoExisteException{
        if(this.animais.isEmpty()){
            throw new AnimalNaoExisteException("Não há animais cadastrados para esse tutor");
        } else {
            for(Animal a: this.animais){
                if(a.getNome().equalsIgnoreCase(nome)){
                    this.animais.remove(a);
                    return;
                }
            }
            throw new AnimalNaoExisteException("O animal de nome "+nome+" do tutor "+this.nome);
        }
    }

    public void limparListaDeAnimais(){
        this.animais.clear();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereço() {
        return endereço;
    }

    public void setEndereço(String endereço) {
        this.endereço = endereço;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Animal> getAnimais() {
        return animais;
    }

    public void setAnimais(List<Animal> animais) {
        this.animais = animais;
    }

    public int getQuantidadeAnimaisDoTutor(){
        return this.animais.size();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tutor tutor = (Tutor) o;
        return Objects.equals(cpf, tutor.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cpf);
    }

    @Override
    public String toString() {
        return "TODO Tutor";
    }
}
