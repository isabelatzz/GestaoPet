package ProjetoPetShop.entities;

import java.util.Objects;

public abstract class Servico {
    protected int id;
    protected Animal animal;
    protected DataMarcadaServico dataMarcada;
    protected Tamanho tamanho;
    protected String funcionario;
    protected boolean pago;
    protected double valorBase; //mudar isso de local ou talvez criar a regra de negocio
    protected double percentualAcrescimoTamanho; //Veterinário que decide mudar isso de classe ou criar um regra de negocio

    public Servico(int id, Animal animal, DataMarcadaServico dataMarcada,
                   Tamanho tamanho, String funcionario,double valorBase,double percentualAcrescimoTamanho) {
        this.id = id;
        this.animal = animal;
        this.dataMarcada = dataMarcada;
        //this.dataHora = LocalDateTime.now(); botar isso no gerador de pdf (recibo)
        this.tamanho = tamanho;
        this.funcionario = funcionario;
        this.pago = false; //se a pessoa quiser pagar já na hora da criação?
        this.valorBase = valorBase; //trocar isso para ser uma regra de negocio
        this.percentualAcrescimoTamanho = percentualAcrescimoTamanho; //caso queira botar a porcetagem inteiro é só dividir por 100 esse valor
    }

    public abstract double calcularValorTotal();

    public abstract String getDetalhesServico();

    abstract double calcularAcrescimo();

    public abstract void atualizarServico(Servico servicoParaAtualziar);

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

    public DataMarcadaServico getDataMarcada() {
        return dataMarcada;
    }

    public void setDataMarcada(DataMarcadaServico dataMarcada) {
        this.dataMarcada = dataMarcada;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public String getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public double getValorBase() {
        return valorBase;
    }

    public void setValorBase(double valorBase) {
        this.valorBase = valorBase;
    }

    public double getPercentualAcrescimoTamanho() {
        return percentualAcrescimoTamanho;
    }

    public void setPercentualAcrescimoTamanho(double percentualAcrescimoTamanho) {
        this.percentualAcrescimoTamanho = percentualAcrescimoTamanho;
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

}
