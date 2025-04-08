package ProjetoPetShop.entities;

//CLASSE INCOMPLETA. IRÁ SER COMPLETADA -- LÓGICA = CONSULTA

public class Tosa extends Servico{
    private boolean adicionarAcc;
    private double valoraAcc;

    public Tosa(int id, Animal animal, double valorBase, boolean adicionarAcc){
        super(id, animal, valorBase);
        this.adicionarAcc = adicionarAcc;
    }

    @Override
    public double calcularValorTotal() {
        if (adicionarAcc) {
            return this.getValorBase() + this.valoraAcc;
        } else {
            return this.getValorBase();
        }
    }
    public boolean isAdicionarAcc() {
        return adicionarAcc;
    }

    public void setAdicionarAcc(boolean adicionarAcc) {
        this.adicionarAcc = adicionarAcc;
    }

    public double getValoraAcc() {
        return valoraAcc;
    }

    public void setValoraAcc(double valoraAcc) {
        this.valoraAcc = valoraAcc;
    }


}


