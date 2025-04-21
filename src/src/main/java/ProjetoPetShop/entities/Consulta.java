package ProjetoPetShop.entities;

public class Consulta extends Servico{
    private double valorVacina;
    private double valorMedicamento;
    private String descricao; //isso aqui não ta sendo usado em nenhum lugar importante


    public Consulta(int id, Animal animal, DataMarcadaServico dataMarcada,
                    Tamanho tamanho, String funcionario,
                    double valorBase, double percetualAcrescimoTamanho, String descricao, double valorVacina, double valorMedicamento) {

        super(id, animal,dataMarcada, tamanho, funcionario, valorBase, percetualAcrescimoTamanho);
        this.valorVacina = valorVacina;
        this.valorMedicamento = valorMedicamento;
        this.descricao = descricao;
    }
    @Override
    public double calcularValorTotal() {
        return (super.valorBase+calcularAcrescimo()) + this.valorVacina + this.valorMedicamento;
    }

    @Override
    public String getDetalhesServico() {
        StringBuilder sb = new StringBuilder();

        try {
            // Formatação segura usando String.format()
            sb.append(String.format("║ %-33s ║%n", "CONSULTA VETERINÁRIA"));
            sb.append(String.format("║ %-33s ║%n", " "));  // Linha em branco para espaçamento

            // Adiciona informações básicas formatadas
            sb.append(String.format("║ Tamanho: %-24s ║%n",
                    (getTamanho() != null ? getTamanho().toString() : "Não informado")));

            sb.append(String.format("║ Acréscimo (%%): %-19.2f ║%n",
                    getPercentualAcrescimoTamanho()));

            sb.append(String.format("║ Valor Acréscimo: R$ %-16.2f ║%n",
                    calcularAcrescimo()));

            if (valorVacina > 0) {
                sb.append(String.format("║ Vacina: R$ %-23.2f ║%n", valorVacina));
            }
            if (valorMedicamento > 0) {
                sb.append(String.format("║ Medicamentos: R$ %-16.2f ║%n", valorMedicamento));
            }

        } catch (Exception e) {
            // Fallback seguro em caso de erro de formatação
            System.err.println("Erro ao formatar detalhes da consulta: " + e.getMessage());
            return "║ Erro ao gerar detalhes do serviço ║";
        }
        return sb.toString();
    }

    @Override
    public double calcularAcrescimo() {
        return super.valorBase*super.percentualAcrescimoTamanho;
    }

    @Override
    public void atualizarServico(Servico servicoParaAtualziar) {
        Consulta consultaParaAtualizar = (Consulta) servicoParaAtualziar;
        if (consultaParaAtualizar.getId() > 0){
            this.id = consultaParaAtualizar.getId();
        } else if (consultaParaAtualizar.getAnimal() != null){
            this.animal = consultaParaAtualizar.getAnimal();
        } else if (consultaParaAtualizar.getDataMarcada() != null){
            this.dataMarcada = consultaParaAtualizar.getDataMarcada();
        } else if (consultaParaAtualizar.getTamanho() != null){
            this.tamanho = consultaParaAtualizar.getTamanho();
        } else if (consultaParaAtualizar.funcionario != null){
            this.funcionario = consultaParaAtualizar.getFuncionario();
        } else if (consultaParaAtualizar.getValorBase() > 0.0){
            this.valorBase = consultaParaAtualizar.getValorBase();
        } else if (consultaParaAtualizar.getPercentualAcrescimoTamanho() > 0.0){
            this.percentualAcrescimoTamanho = consultaParaAtualizar.getPercentualAcrescimoTamanho();
        } else if (consultaParaAtualizar.getValorVacina() > 0.0){
            this.valorVacina = consultaParaAtualizar.getValorVacina();
        } else if (consultaParaAtualizar.getDescricao() != null){
            this.descricao = consultaParaAtualizar.getDescricao();
        } else if (consultaParaAtualizar.valorMedicamento > 0.0){
            this.valorMedicamento = consultaParaAtualizar.getValorMedicamento();
        }
    }

    public double getValorVacina() {
        return valorVacina;
    }

    public void setValorVacina(double valorVacina) {
        this.valorVacina = valorVacina;
    }

    public double getValorMedicamento() {
        return valorMedicamento;
    }

    public void setValorMedicamento(double valorMedicamento) {
        this.valorMedicamento = valorMedicamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString(){
        return "TODO Concuslta";
    }
}
