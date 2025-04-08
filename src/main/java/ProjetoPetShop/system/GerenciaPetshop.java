package ProjetoPetShop.system;

import ProjetoPetShop.entities.Animal;
import ProjetoPetShop.entities.EspecieAnimal;
import ProjetoPetShop.exception.DonoNaoExisteException;
import ProjetoPetShop.exception.animalJaCadastradoException;

import java.io.IOException;
import java.util.List;

public interface GerenciaPetshop {
    boolean cadastraDadosAnimais (String nome, EspecieAnimal especie, String raca, int idade, String dono) throws animalJaCadastradoException, IOException;
    List<Animal> pesquisaAnimalPorDono (List<Animal> animais, String dono) throws DonoNaoExisteException;
    boolean removerDadosAnimais (String nome) throws IOException;
    void salvarDados() throws IOException;
    void recuperarDados() throws IOException;
}