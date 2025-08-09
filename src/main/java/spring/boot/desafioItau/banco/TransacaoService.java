package spring.boot.desafioItau.banco;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.boot.desafioItau.model.Transacao;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
@RequiredArgsConstructor
@Service
public class TransacaoService {

    public final List<Transacao> listaDeTransacoes = new ArrayList<>();

    public void popular(){
        for (int i = 0;i<10;i++){
            listaDeTransacoes.add(new Transacao(Math.random(),OffsetDateTime.now()));
        }
    }

    public Boolean verificaValorHora(OffsetDateTime hora, Double valor){
        if (!hora.isAfter(OffsetDateTime.now())) {
            Transacao t = new Transacao(valor, hora);
            setListaDeTransacoes(new Transacao(valor,hora));
            return true;
    }
        return false;
    }

    public DoubleSummaryStatistics estatistica(Integer segundos){
        DoubleSummaryStatistics status = listaDeTransacoes.stream().filter(c ->
                c.getDataHora().isAfter(OffsetDateTime.now().minusSeconds(segundos))
        ).mapToDouble(Transacao::getValor).summaryStatistics();

        return status;
    }

    public List<Transacao> getListaDeTransacoes() {
        return listaDeTransacoes;
    }

    public void setListaDeTransacoes(Transacao transacao) {
        listaDeTransacoes.add(transacao);
    }
}
