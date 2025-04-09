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
        return String.format(
                "Tamanho: %s\n" +
                        "Acréscimo (%%): %.2f\n" +
                        "Valor Acréscimo: R$ %.2f\n" +
                        "Descrição: %s\n",
                getTamanho(),
                getPercentualAcrescimoTamanho(),
                calcularAcrescimo(),
                descricao
        );
    }


    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}

