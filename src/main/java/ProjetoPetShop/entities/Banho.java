package ProjetoPetShop.entities;


//CLASSE INCOMPLETA. IRÁ SER COMPLETADA -- LÓGICA = CONSULTA


public class Banho extends Servico{
    private boolean usarPerfume;
    private double valorPerfume= 15.0;

    public Banho(int id, Animal animal, double valorBase, boolean usarPerfume) {
        super(id, animal, valorBase);
        this.usarPerfume = usarPerfume;
    }

    @Override
    public double calcularValorTotal() {
        if(usarPerfume){
            return this.getValorBase()+this.valorPerfume;
        } else {
            return this.getValorBase();
        }
    }

    public boolean isUsarPerfume() {
        return usarPerfume;
    }

    public void setUsarPerfume(boolean usarPerfume) {
        this.usarPerfume = usarPerfume;
    }

    public double getValorPerfume() {
        return valorPerfume;
    }

    public void setValorPerfume(double valorPerfume) {
        this.valorPerfume = valorPerfume;
    }
}
