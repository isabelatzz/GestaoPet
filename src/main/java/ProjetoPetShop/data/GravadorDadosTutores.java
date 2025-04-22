package ProjetoPetShop.data;

import ProjetoPetShop.entities.Tutor;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class GravadorDadosTutores {
    private final String arquivoTutores;

    // 1. Construtor: recebe o caminho do arquivo e cria o diretório se não existir
    public GravadorDadosTutores(String caminhoArquivo) {
        this.arquivoTutores = caminhoArquivo;
        criarArquivoSeNaoExistir();
    }

    // 2. Cria o arquivo vazio se ele não existir
    private void criarArquivoSeNaoExistir() {
        File file = new File(arquivoTutores);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs(); // Cria o diretório "data" se necessário
                file.createNewFile();
                salvarDadosTutores(new HashMap<>()); // Inicializa com um Map vazio
            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo de tutores: " + e.getMessage());
            }
        }
    }

    // 3. Salva o Map de tutores no arquivo
    public void salvarDadosTutores(Map<String, Tutor> tutores) throws IOException {
        Path path = Paths.get("data/tutores.dat");
        Files.createDirectories(path.getParent());

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(path.toFile()))) {
            oos.writeObject(tutores);
        }
    }

    // 4. Carrega o Map de tutores do arquivo
    @SuppressWarnings("unchecked")
    public Map<String, Tutor> recuperaDadosTutores() throws IOException {
        File file = new File(arquivoTutores);
        if (file.length() == 0) {
            return new HashMap<>(); // Retorna vazio se o arquivo estiver vazio
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivoTutores))) {
            return (Map<String, Tutor>) in.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Formato de dados inválido no arquivo.", e);
        }
    }
}