package spring.boot.desafioItau.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import spring.boot.desafioItau.dtos.TransacaoDTO;
import spring.boot.desafioItau.exception.UnprocessableEntity;
import spring.boot.desafioItau.model.Transacao;
import spring.boot.desafioItau.repository.TransacaoRepository;

import java.time.OffsetDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Getter
public class TransacaoService {

    private final TransacaoRepository repository;
    private final Logger log = LoggerFactory.getLogger(TransacaoService.class);

    public void popularListaDeTransacoes() {
        log.info("Populando a lista de Transações para testes");
        for (int i = 0; i < 10; i++) {
            repository.save(Transacao.builder().valor(Math.random()).dataHora(OffsetDateTime.now()).build());
        }
        log.info("Lista Teste Completa.");
    }

    public void adicionarTransacao(TransacaoDTO dto) {
        log.info("Iniciada a verificação para adicionar a transação: " + dto);
        if (dto.getValor() < 0) {
            log.error("A transação deve ter valor maior ou igual a 0");
            throw new UnprocessableEntity("A transação deve ter valor maior ou igual a 0");
        }
        if (dto.getDataHora().isAfter(OffsetDateTime.now())) {
            log.error("A transação não deve ter data e hora posterior a data e hora atual");
            throw new UnprocessableEntity("A transação não deve ter data e hora posterior a data e hora atual");

        }
        dto.setValor(dto.getValor() + 5);
        repository.save(new Transacao(dto));
        log.info("Transação Adicionada com sucesso");
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public List<Transacao> getListaDeTransacoes() {
        return repository.findAll();
    }


}
