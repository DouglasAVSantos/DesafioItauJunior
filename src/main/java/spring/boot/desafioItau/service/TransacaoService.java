package spring.boot.desafioItau.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import spring.boot.desafioItau.controller.dtos.TransacaoDTO;
import spring.boot.desafioItau.exception.UnprocessableEntity;
import spring.boot.desafioItau.model.Transacao;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TransacaoService {

    private final List<Transacao> listaDeTransacoes = new ArrayList<>();
    private final Logger log = LoggerFactory.getLogger(TransacaoService.class);

    public void popularListaDeTransacoes() {
        log.info("Populando a lista de Transações para testes");
        for (int i = 0; i < 10; i++) {
            listaDeTransacoes.add(new Transacao(Math.random(), OffsetDateTime.now()));
        }
        log.info("Lista Teste Completa.");
    }

    public void adicionarTransacao(Transacao transacao) {
        log.info("Iniciada a verificação para adicionar a transação: " + transacao);
        if (transacao.getValor() < 0) {
            log.error("A transação deve ter valor maior ou igual a 0");
            throw new UnprocessableEntity("A transação deve ter valor maior ou igual a 0");
        }
        if (transacao.getDataHora().isAfter(OffsetDateTime.now())) {
            log.error("A transação não deve ter data e hora posterior a data e hora atual");
            throw new UnprocessableEntity("A transação não deve ter data e hora posterior a data e hora atual");

        }
        listaDeTransacoes.add(transacao);
        log.info("Transação Adicionada com sucesso");
    }

    public List<Transacao> getListaDeTransacoes() {
        return listaDeTransacoes;
    }


}
