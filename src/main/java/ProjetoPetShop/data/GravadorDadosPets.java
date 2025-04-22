package ProjetoPetShop.data;

import ProjetoPetShop.entities.Animal;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GravadorDadosPets {
    private final String arquivoAnimais;

    // 1. Recebe o caminho do arquivo no construtor (mais flexível)
    public GravadorDadosPets(String caminhoArquivo) {
        this.arquivoAnimais = caminhoArquivo;
        criarArquivoSeNaoExistir();
    }

    // 2. Cria o arquivo vazio se não existir
    private void criarArquivoSeNaoExistir() {
        File file = new File(arquivoAnimais);
        if (!file.exists()) {
            try {
                file.createNewFile();
                salvarDadosAnimais(new HashMap<>()); // Inicializa com Map vazio
            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo: " + e.getMessage());
            }
        }
    }

    // 3. Salva dados com tratamento de erro mais claro
    public void salvarDadosAnimais(Map<String, Animal> animais) throws IOException {
        if (animais == null) {
            throw new IllegalArgumentException("Mapa de animais não pode ser nulo.");
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivoAnimais))) {
            out.writeObject(animais);
        }
        // IOException já é repassada pelo método
    }

    // 4. Recupera dados com validação
    public Map<String, Animal> recuperaDadosAnimais() throws IOException {
        File file = new File(arquivoAnimais);
        if (file.length() == 0) { // Arquivo vazio = retorna Map vazio
            return new HashMap<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivoAnimais))) {
            return (Map<String, Animal>) in.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Formato de dados inválido no arquivo.", e);
        }
    }
}