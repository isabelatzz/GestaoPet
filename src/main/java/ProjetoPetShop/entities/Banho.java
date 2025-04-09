package ProjetoPetShop.entities;


public class Banho extends Servico {
    private String descricao;

    public Banho(int id, Animal animal, double valorBase, String veterinario, String descricao, Tamanho tamanho
            , double acrescimoTamanho) {
        super(id, animal, valorBase, veterinario, tamanho, acrescimoTamanho);
        this.descricao = descricao;
    }

    @Override
    public double calcularValorTotal() {
        return getValorBase() + calcularAcrescimo();
    }

    @Override
    protected String getDetalhesServico() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("║ Tamanho: %-26s ║\n", getTamanho()));
        sb.append(String.format("║ Acréscimo (%%): %-19.2f ║\n", getPercentualAcrescimoTamanho()));
        sb.append(String.format("║ Valor Acréscimo: R$ %-16.2f ║\n", calcularAcrescimo()));
        sb.append(String.format("║ Descrição: %-24s ║\n", descricao));
        return sb.toString();
    }


    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}

