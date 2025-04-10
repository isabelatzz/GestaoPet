package ProjetoPetShop.entities;

import java.util.List;

public interface ServicoInterface {
    /* Métodos de Recibos */

    String gerarRecibo (int idServico);

    default void gerarReciboPDF(int idServico) {
    }

    List<Servico> listarServicosPendentes ();
    void adicionarServico(Servico servico);
    public void cadastrarServico(Servico servico);
}
