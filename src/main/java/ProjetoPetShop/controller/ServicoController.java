package ProjetoPetShop.controller;

import ProjetoPetShop.entities.*;
import ProjetoPetShop.system.petlover.PetLoverMap;
import ProjetoPetShop.system.servico.ServicoPetLoverMap;

import java.time.LocalDateTime;
import java.util.List;

public class ServicoController {
    private final PetLoverMap petLoverMap;
    private final ServicoPetLoverMap servicoMap;

    public ServicoController(PetLoverMap petLoverMap, ServicoPetLoverMap servicoMap) {
        this.petLoverMap = petLoverMap;
        this.servicoMap = servicoMap;
    }

    public void agendarServico(int idAnimal, String tipoServico, LocalDateTime data,
                               double valorBase, String veterinario,
                               boolean adicionarAcessorios, double valorAcessorios,
                               String descricao,boolean pago,Tamanho tamanho) {

        Animal animal = petLoverMap.buscarAnimalPorID(idAnimal);
        if (valorBase <= 0) {
            throw new IllegalArgumentException("Valor inválido!");
        }

        Servico servico = switch (tipoServico.toLowerCase()) {
            case "tosa" -> new Tosa(
                    0, // ID será gerado automaticamente
                    animal,
                    valorBase,
                    veterinario,
                    adicionarAcessorios,
                    valorAcessorios,
                    descricao,
                    pago,
                    tamanho,
                    calcularAcrescimoTamanho(tamanho, valorBase)
            );

            case "banho" -> new Banho(
                    0, // ID será gerado automaticamente
                    animal,
                    valorBase,
                    veterinario,
                    descricao,
                    pago,
                    tamanho,
                    calcularAcrescimoTamanho(tamanho, valorBase)
            );

            default -> throw new IllegalArgumentException("Tipo de serviço inválido: " + tipoServico);
        };

        servicoMap.cadastrarServico(servico);
    }

    private double calcularAcrescimoTamanho(Tamanho tamanho, double valorBase) {
        return switch (tamanho) {
            case PEQUENO -> 0.0;
            case MEDIO -> valorBase * 0.1; // 10%
            case GRANDE -> valorBase * 0.2; // 20%
        };
    }

    public List<Servico> listarServicos() {
        return servicoMap.listarTodosServicos();
    }

    public void gerarReciboPDF(int idServico) {
        servicoMap.gerarReciboPDF(idServico); // aqui você repassa a chamada para o map
    }

    public void salvarDados(){
        this.servicoMap.salvarDados();
    }

}
