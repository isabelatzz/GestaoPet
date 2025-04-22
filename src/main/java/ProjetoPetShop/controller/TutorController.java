package ProjetoPetShop.controller;

import ProjetoPetShop.entities.Tutor;
import ProjetoPetShop.exception.TutorCadastrado;
import ProjetoPetShop.system.petlover.PetLoverMap;

import javax.swing.*;
import java.util.List;

public class TutorController {
    private final PetLoverMap petLoverMap;

    public TutorController(PetLoverMap petLoverMap) {
        this.petLoverMap = petLoverMap;
    }

    // No TutorController
    public void cadastrarTutor(Tutor tutor) {
        System.out.println("Iniciando cadastro de tutor: " + tutor.getNome());
        try {
            petLoverMap.cadastrarTutor(tutor);
            System.out.println("Tutor cadastrado no mapa");
            petLoverMap.salvarDados();
            System.out.println("Dados salvos com sucesso");
        } catch (Exception e) {
            System.err.println("ERRO ao cadastrar: " + e.getMessage());
            throw new IllegalArgumentException("Erro ao cadastrar tutor: " + e.getMessage());
        }
    }

    public void testarRecuperacao() {
        System.out.println("Tutores no sistema:");
        petLoverMap.listarTodosTutores().forEach(t ->
                System.out.println(t.getNome() + " - " + t.getCpf()));
    }

    public List<Tutor> listarTutores() {
        return petLoverMap.listarTodosTutores();
    }

    public boolean removerTutor(String cpf) {
        return petLoverMap.removerTutor(cpf);
    }

    public Tutor buscarTutorPorCpf(String cpf) {
        return petLoverMap.buscarTutorPorCpf(cpf);
    }

    public boolean atualizarTutor(Tutor tutor) {
        return petLoverMap.atualizarTutor(tutor);
    }
}