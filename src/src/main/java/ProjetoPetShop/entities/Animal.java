package ProjetoPetShop.entities;

import java.io.Serializable;

/**
 * Representa um animal de estimação no sistema do PetLover.
 * Cada animal está associado a um Tutor.
 */

public class Animal implements Serializable {
    private int codigo;
    private String nome;
    private String especie;
    private String raca;
    private Tamanho tamanho;
    private int idade;
    private Tutor tutor;
    //private List<Servico> servicosFeitos;

    public Animal(int codigo,String nome, String especie, String raca, Tamanho tamanho,int idade, Tutor tutor) {
        this.codigo = codigo;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.tamanho = tamanho;
        this.idade = idade;
        this.tutor = tutor;
    }
    public void atualizarAnimal(Animal animalParaAtualizar){
        if(animalParaAtualizar.getNome() != null){
            this.nome = animalParaAtualizar.getNome();
        } else if(animalParaAtualizar.getEspecie() != null){
            this.especie = animalParaAtualizar.getEspecie();
        } else if (animalParaAtualizar.getRaca() != null) {
            this.raca = animalParaAtualizar.getRaca();
        } else if (animalParaAtualizar.getTamanho() != null){
            this.tamanho = animalParaAtualizar.getTamanho();
        } else if (animalParaAtualizar.getIdade() > 0){
            this.idade = animalParaAtualizar.getIdade();
        } else if (animalParaAtualizar.getTutor() != null) {
            this.tutor = animalParaAtualizar.getTutor();
        }
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }


    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        if (idade <= 0) {
            throw new IllegalArgumentException("Idade não pode ser negativa");
        }
        this.idade = idade;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    @Override
    public String toString() {
        return "TODO Animal";
    }
}