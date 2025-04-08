package ProjetoPetShop.entities;

import java.util.Objects;

public abstract class Servico {
    private int id;
    private Animal animal;
    private double valorBase;

    public Servico(int id, Animal animal, double valorBase) {
        this.id = id;
        this.animal = animal;
        this.valorBase = valorBase;
    }

    public abstract double calcularValorParcial();
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public double getValorBase() {
        return valorBase;
    }

    public void setValorBase(double valorBase) {
        this.valorBase = valorBase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Servico servico = (Servico) o;
        return id == servico.id && Objects.equals(animal, servico.animal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, animal);
    }

    @Override
    public String toString() {
        return "Servico{" +
                "id=" + id +
                ", animal=" + animal +
                ", valorBase=" + valorBase +
                '}';
    }
}
