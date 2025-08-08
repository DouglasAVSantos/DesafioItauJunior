package spring.boot.desafioItau.banco;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.boot.desafioItau.model.Transacao;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Banco {
    static public List<Transacao> listaDeTransacoes = new ArrayList<>();

    public void popular(){
        for (int i = 0;i<10;i++){
            listaDeTransacoes.add(new Transacao(Math.random(),OffsetDateTime.now()));
        }
    }

    public List<Transacao> getListaDeTransacoes() {
        return listaDeTransacoes;
    }

    public void setListaDeTransacoes(Transacao transacao) {
        listaDeTransacoes.add(transacao);
    }
}
