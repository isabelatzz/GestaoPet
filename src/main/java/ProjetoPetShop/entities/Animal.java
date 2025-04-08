package ProjetoPetShop.entities;

import java.util.Objects;

public class Animal {
    private int codigo;
    private String nome;
    private String especie;
    private String raca;
    private int idade;
    private Tutor tutor;

    public Animal(int codigo, String nome, String especie, String raca, int idade, Tutor tutor) {
        this.codigo = codigo;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.tutor = tutor;
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
        this.idade = idade;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return codigo == animal.codigo && Objects.equals(tutor, animal.tutor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, tutor);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", especie='" + especie + '\'' +
                ", raca='" + raca + '\'' +
                ", idade=" + idade +
                ", tutor=" + tutor +
                '}';
    }
}
