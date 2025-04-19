package ProjetoPetShop.entities;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class Servico {
    private int id;
    private Animal animal;
    private double valorBase;
    private LocalDateTime dataHora;
    private String veterinario;
    private boolean pago;
    private Tamanho tamanho;
    private double percentualAcrescimoTamanho; //Veterinário que decide

    public Servico(int id, Animal animal, double valorBase, String veterinario, Tamanho tamanho,
                   double percentualAcrescimoTamanho) {
        this.id = id;
        this.animal = animal;
        this.valorBase = valorBase;
        this.veterinario = veterinario;
        this.dataHora = LocalDateTime.now();
        this.pago = false;
        this.tamanho = tamanho;
        this.percentualAcrescimoTamanho = percentualAcrescimoTamanho;
    }

    public String gerarRecibo(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format(
                "PetLover\n" +
                        "----------------------------------\n" +
                        "RECIBO DE ATENDIMENTO\n" +
                        "Data/Hora: %s\n" +
                        "Animal: %s (%s)\n" +
                        "Tutor: %s\n" +
                        "CPF Tutor: %s\n" +
                        "----------------------------------\n" +
                        "Serviço: %s\n" +
                        "Responsável: %s\n" +
                        "Valor Base: R$ %.2f\n" +
                        "%s" +  // Detalhes específicos do serviço
                        "----------------------------------\n" +
                        "VALOR TOTAL: R$ %.2f\n" +
                        "Status: %s\n" +
                        "----------------------------------\n" +
                        "Assinatura do Veterinário: ________________\n\n",
                dataHora.format(formatter),
                animal.getNome(),
                animal.getEspecie(),
                animal.getTutor().getNome(),
                animal.getTutor().getCpf(),
                this.getClass().getSimpleName(),
                veterinario,
                valorBase,
                getDetalhesServico(),
                calcularValorTotal(),
                pago ? "PAGO" : "PENDENTE"
        );
    }


    protected double calcularAcrescimo() {
        return getValorBase() * (getPercentualAcrescimoTamanho() / 100);
    }

    public String getDetalhes() {
        return getDetalhesServico();
    }

    protected abstract String getDetalhesServico();

    public LocalDateTime getDataHora() {
        return dataHora;
    }
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getVeterinario() {
        return veterinario;
    }
    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }

    public abstract double calcularValorTotal();

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

    public void marcarComoPago(){
        this.pago = true;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public double getPercentualAcrescimoTamanho() {
        return percentualAcrescimoTamanho;
    }
    public void setPercentualAcrescimoTamanho(double percentualAcrescimoTamanho) {
        this.percentualAcrescimoTamanho = percentualAcrescimoTamanho;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }
    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
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
