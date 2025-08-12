package spring.boot.desafioItau.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import spring.boot.desafioItau.model.Transacao;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;

@Service
@RequiredArgsConstructor
public class EstatisticasService {

    private final TransacaoService transacaoService;
    private Logger log = LoggerFactory.getLogger(EstatisticasService.class);

    public DoubleSummaryStatistics getEstatistica(Integer intervaloEmSegundos) {
        Long inicio = System.currentTimeMillis();
        log.info("Buscando Estatisticas nos ultimos " + intervaloEmSegundos + " segundos");

        DoubleSummaryStatistics status = transacaoService.getListaDeTransacoes()
                .stream()
                .filter(c ->
                        c.getDataHora().isAfter(OffsetDateTime.now().minusSeconds(intervaloEmSegundos))
                ).mapToDouble(Transacao::getValor).summaryStatistics();
        Long termino = System.currentTimeMillis();
        log.info("O tempo de processamento para retornar as estatisticas foi de " + (termino - inicio) + " ms");
        log.info("Estatisticas retornadas com sucesso!");
        return status;
    }
}
