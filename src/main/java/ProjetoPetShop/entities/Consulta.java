package ProjetoPetShop.entities;

import java.io.Serializable;

public class Consulta extends Servico implements Serializable {
    private double valorVacina;
    private double valorMedicamento;
    private String descricao;


    public Consulta (int id,
                     Animal animal,
                     String veterinario,
                     double valorBase,
                     double valorVacina,
                     double valorMedicamento,
                     String descricao,
                     boolean pago,
                     Tamanho tamanho,
                     double acrescimoTamanho) {

        super(id, animal, valorBase, veterinario, pago,tamanho, acrescimoTamanho);
        this.valorVacina = valorVacina;
        this.valorMedicamento = valorMedicamento;
        this.descricao = descricao;
    }


    @Override
    public double calcularValorTotal() {
     return getValorBase() + valorVacina + valorMedicamento + calcularAcrescimo();
    }

    @Override
    protected String getDetalhesServico() {

        StringBuilder sb = new StringBuilder();

        try {
            sb.append(String.format("║ %-33s ║%n", "CONSULTA VETERINÁRIA"));
            sb.append(String.format("║ %-33s ║%n", " "));
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
            System.err.println("Erro ao formatar detalhes da consulta: " + e.getMessage());
            return "║ Erro ao gerar detalhes do serviço ║";
        }
        return sb.toString();
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

}
