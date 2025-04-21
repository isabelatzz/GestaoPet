package ProjetoPetShop.entities;

public class Tosa extends Servico{
    private boolean adicionarAcc;
    private double valoraAcc;
    private String descricao;

    public Tosa(int id, Animal animal, DataMarcadaServico dataMarcada,
                Tamanho tamanho, String funcionario, double valorBase,
                double percentualAcrescimoTamanho, boolean adicionarAcc,
                double valoraAcc, String descricao) {
        super(id, animal, dataMarcada, tamanho, funcionario, valorBase, percentualAcrescimoTamanho);
        this.adicionarAcc = adicionarAcc;
        this.valoraAcc = valoraAcc;
        this.descricao = descricao;
    }
    @Override
    public double calcularValorTotal() {
        //Calcular o valor base com o acrescimo, caso tenha o acc na tosa apenas soma o valor do acc
        double calc = super.valorBase+(super.valorBase*super.percentualAcrescimoTamanho);
        if (adicionarAcc) {
            calc += valoraAcc;
        }
        return calc;
    }

    @Override
    public String getDetalhesServico() {
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

    @Override
    double calcularAcrescimo() {
        return super.valorBase*super.percentualAcrescimoTamanho;
    }

    @Override
    public void atualizarServico(Servico servicoParaAtualziar) {
        Tosa tosaParaAtualizar = (Tosa) servicoParaAtualziar;
        if (tosaParaAtualizar.getId() > 0){
            this.id = tosaParaAtualizar.getId();
        } else if (tosaParaAtualizar.getAnimal() != null){
            this.animal = tosaParaAtualizar.getAnimal();
        } else if (tosaParaAtualizar.getDataMarcada() != null){
            this.dataMarcada = tosaParaAtualizar.getDataMarcada();
        } else if (tosaParaAtualizar.getTamanho() != null){
            this.tamanho = tosaParaAtualizar.getTamanho();
        } else if (tosaParaAtualizar.funcionario != null){
            this.funcionario = tosaParaAtualizar.getFuncionario();
        } else if (tosaParaAtualizar.getValorBase() > 0.0){
            this.valorBase = tosaParaAtualizar.getValorBase();
        } else if (tosaParaAtualizar.getPercentualAcrescimoTamanho() > 0.0){
            this.percentualAcrescimoTamanho = tosaParaAtualizar.getPercentualAcrescimoTamanho();
        } else if (tosaParaAtualizar.getValoraAcc() > 0.0){
            this.valoraAcc = tosaParaAtualizar.valoraAcc;
        } else if (tosaParaAtualizar.getDescricao() != null){
            this.descricao = tosaParaAtualizar.getDescricao();
        } else if (tosaParaAtualizar.adicionarAcc == true){
            this.adicionarAcc = false;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString(){
        return "TODO Tosa";
    }

}


