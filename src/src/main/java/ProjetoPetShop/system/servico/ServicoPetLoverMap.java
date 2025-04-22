package ProjetoPetShop.system.servico;

import ProjetoPetShop.entities.Servico;
import ProjetoPetShop.exception.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class ServicoPetLoverMap implements ServicoInterface {
    private Map<Integer, Servico> servicos;
    private int proximoIdServico;

    public ServicoPetLoverMap() {
        this.servicos = new HashMap<>();
        this.proximoIdServico = 1;
    }

    public void cadastrarServico(Servico servico){
        servico.setId(proximoIdServico);
        servicos.put(proximoIdServico, servico);
        proximoIdServico++;
    }

    @Override
    public Collection<Servico> getServicos() throws NaoHaServicosCadastradoException {
        if(this.servicos.values().isEmpty()) throw new NaoHaServicosCadastradoException("Não há serviços cadastrados");
        return this.servicos.values();
    }

    @Override
    public Servico getServico(int id) throws ServicoNaoExisteException {
        if(this.servicos.containsKey(id)) return this.servicos.get(id);
        else throw new ServicoNaoExisteException("Não há serviços com esse ID");
    }

    @Override
    public void atualizarServico(int id, Servico servico) {
        if(this.servicos.containsKey(id)){
            this.servicos.get(id).atualizarServico(servico);
        } else {
            throw new ServicoNaoExisteException("O serviço que você quer atualizar não existe");
        }
    }

    @Override
    public Collection<Servico> listarServicosDoDia() throws NaoHaServicosDoDiaException {
        LocalDateTime dataDeHoje = LocalDateTime.now();
        Collection<Servico> servicosDoDia = new ArrayList<>();
        for(Servico s: this.servicos.values()){
            if(s.getDataMarcada().getDia() == dataDeHoje.getDayOfMonth() && s.getDataMarcada().getMes() == dataDeHoje.getMonthValue()){
                servicosDoDia.add(s);
            }
        }
        if(servicosDoDia.isEmpty()) throw new NaoHaServicosDoDiaException("Não há servicos para hoje");
        else return servicosDoDia;
    }

    @Override
    public Collection<Servico> listarServicosPendentes() throws NaoHaServicosPendentesException {
        Collection<Servico> servicosPendentes = new ArrayList<>();
        for(Servico s: this.servicos.values()) if(!s.isPago()) servicosPendentes.add(s);
        if(servicosPendentes.isEmpty()) throw new NaoHaServicosPendentesException("Não há serviços pendentes");
        return servicosPendentes;
    }

    @Override
    public void removerServico(int id) throws ServicoNaoExisteException {
        if(this.servicos.containsKey(id)) this.servicos.remove(id);
        else throw new ServicoNaoExisteException("Não há serviços com o ID: "+id);
    }

    @Override
    public void gravarServicos() throws IOException {

    }

    @Override
    public void recuperarServicos() throws IOException {

    }

}
