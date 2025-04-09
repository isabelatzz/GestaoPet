package ProjetoPetShop.entities;

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

    public void addAnimal(Animal animal) {
        this.animais.add(animal);
    }
    public void removeAnimal(Animal animal) {
        this.animais.remove(animal);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tutor tutor = (Tutor) o;
        return Objects.equals(nome, tutor.nome) && Objects.equals(cpf, tutor.cpf) && Objects.equals(telefone, tutor.telefone) && Objects.equals(endereço, tutor.endereço) && Objects.equals(email, tutor.email) && Objects.equals(animais, tutor.animais);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, cpf, telefone, endereço, email, animais);
    }

    @Override
    public String toString() {
        return "Tutor{" +
                "nome='" + nome + '\'' +
                ", cpf=" + cpf +
                ", telefone='" + telefone + '\'' +
                ", endereço='" + endereço + '\'' +
                ", email='" + email + '\'' +
                ", animais=" + animais +
                '}';
    }
}
