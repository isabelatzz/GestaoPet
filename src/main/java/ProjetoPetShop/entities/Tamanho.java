package ProjetoPetShop.entities;

public enum Tamanho {
    PEQUENO(0.1),
    MEDIO(0.2),
    GRANDE(0.3);

    private double acrescismo;

    Tamanho(double acrescismo){
        this.acrescismo = acrescismo;
    }

    public double getAcrescismo(){
        return acrescismo;
    }
    public void setAcrescismo(double acrescismo){
        this.acrescismo = acrescismo;
    }

}
