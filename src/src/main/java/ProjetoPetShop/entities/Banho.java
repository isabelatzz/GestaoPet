package ProjetoPetShop.entities;


public class Banho extends Servico {
    private String descricao;

    public Banho(int id, Animal animal, DataMarcadaServico dataMarcada,
                 Tamanho tamanho, String funcionario, double valorBase,
                 double percentualAcrescimoTamanho, String descricao) {
        super(id, animal, dataMarcada, tamanho, funcionario, valorBase, percentualAcrescimoTamanho);
        this.descricao = descricao;
    }
    @Override
    public double calcularValorTotal() {
        return super.valorBase+calcularAcrescimo();
    }

    @Override
    public String getDetalhesServico() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("║ Tamanho: %-26s ║\n", getTamanho()));
        sb.append(String.format("║ Acréscimo (%%): %-19.2f ║\n", getPercentualAcrescimoTamanho()));
        sb.append(String.format("║ Valor Acréscimo: R$ %-16.2f ║\n", calcularAcrescimo()));
        sb.append(String.format("║ Descrição: %-24s ║\n", descricao));
        return sb.toString();
    }

    @Override
    public double calcularAcrescimo() {
        return super.valorBase*super.percentualAcrescimoTamanho;
    }

    @Override
    public void atualizarServico(Servico servicoParaAtualziar) {
        Banho banhoParaAtualizar = (Banho) servicoParaAtualziar;
        if (banhoParaAtualizar.getId() > 0){
            this.id = banhoParaAtualizar.getId();
        } else if (banhoParaAtualizar.getAnimal() != null){
            this.animal = banhoParaAtualizar.getAnimal();
        } else if (banhoParaAtualizar.getDataMarcada() != null){
            this.dataMarcada = banhoParaAtualizar.getDataMarcada();
        } else if (banhoParaAtualizar.getTamanho() != null){
            this.tamanho = banhoParaAtualizar.getTamanho();
        } else if (banhoParaAtualizar.funcionario != null){
            this.funcionario = banhoParaAtualizar.getFuncionario();
        } else if (banhoParaAtualizar.getValorBase() > 0.0){
            this.valorBase = banhoParaAtualizar.getValorBase();
        } else if (banhoParaAtualizar.getPercentualAcrescimoTamanho() > 0.0){
            this.percentualAcrescimoTamanho = banhoParaAtualizar.getPercentualAcrescimoTamanho();
        } else if (banhoParaAtualizar.getDescricao() != null){
            this.descricao = banhoParaAtualizar.getDescricao();
        }
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString(){
        return "TODO Banho";
    }
}

