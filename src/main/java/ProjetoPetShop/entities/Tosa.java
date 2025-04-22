package ProjetoPetShop.entities;

import java.io.Serializable;

public class Tosa extends Servico implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean adicionarAcc;
    private double valoraAcc;
    private String descricao;

    public Tosa(int id, Animal animal,
                double valorBase,
                String veterinario,
                boolean adicionarAcc,
                double valoraAcc,
                String descricao, boolean pago,Tamanho tamanho,
                double acrescimoTamanho){
        super(id, animal, valorBase, veterinario, pago ,tamanho, acrescimoTamanho);
        this.adicionarAcc = adicionarAcc;
        this.valoraAcc = valoraAcc;
        this.descricao = descricao;
    }

    @Override
    public double calcularValorTotal() {
        double valorTotal = getValorBase() + calcularAcrescimo();
        if (adicionarAcc) {
            valorTotal += valoraAcc;
        }
        return valorTotal;
    }

    @Override
    protected String getDetalhesServico() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("║ Tamanho: %-26s ║\n", getTamanho()));
        sb.append(String.format("║ Acréscimo (%): %-19.2f ║\n", getPercentualAcrescimoTamanho()));
        sb.append(String.format("║ Valor Acréscimo: R$ %-16.2f ║\n", calcularAcrescimo()));

        if (adicionarAcc) {
            sb.append(String.format("║ Acessórios: R$ %-20.2f ║\n", valoraAcc));
        }
        sb.append(String.format("║ Descrição: %-24s ║\n", descricao));
        return sb.toString();
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

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}


