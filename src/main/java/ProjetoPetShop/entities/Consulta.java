package ProjetoPetShop.entities;

public class Consulta extends Servico{
    private boolean adicionarVacina;
    private double valorVacina;

    public Consulta(int id, Animal animal, double valorBase, boolean adicionarVacina){
        super(id, animal, valorBase);
        this.adicionarVacina = adicionarVacina;
    }

    @Override
    public double calcularValorParcial() {
        if(adicionarVacina){
            return this.valorVacina+this.getValorBase();
        } else {
            return this.getValorBase();
        }
    }

    public boolean isAdicionarVacina() {
        return adicionarVacina;
    }

    public void setAdicionarVacina(boolean adicionarVacina) {
        this.adicionarVacina = adicionarVacina;
    }

    public double getValorVacina() {
        return valorVacina;
    }

    public void setValorVacina(double valorVacina) {
        this.valorVacina = valorVacina;
    }
}
