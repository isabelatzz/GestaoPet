package ProjetoPetShop.data;

import ProjetoPetShop.entities.Servico;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GravadorDadosServico {
    private final String arquivoServicos;

    public GravadorDadosServico(String caminhoArquivo) {
        this.arquivoServicos = caminhoArquivo;
        criarArquivoSeNaoExistir();
    }

    private void criarArquivoSeNaoExistir() {
        File file = new File(arquivoServicos);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                salvarDadosServicos(new HashMap<>());
            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo: " + e.getMessage());
            }
        }
    }

    public void salvarDadosServicos(Map<Integer, Servico> servicos) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivoServicos))) {
            out.writeObject(servicos);
        }
    }

    public Map<Integer, Servico> recuperaDadosServicos() throws IOException {
        File file = new File(arquivoServicos);
        if (file.length() == 0) return new HashMap<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivoServicos))) {
            return (Map<Integer, Servico>) in.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Formato de dados inválido.", e);
        }
    }
}
