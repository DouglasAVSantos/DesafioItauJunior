package spring.boot.desafioItau.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        log.info("Buscando Estatisticas nos ultimos "+intervaloEmSegundos+" segundos");

        DoubleSummaryStatistics status = transacaoService.getListaDeTransacoes()
                .stream()
                .filter(c ->
                        c.getDataHora().isAfter(OffsetDateTime.now().minusSeconds(intervaloEmSegundos))
                ).mapToDouble(Transacao::getValor).summaryStatistics();
            log.info("Estatisticas retornadas com sucesso!");
        return status;
    }
}
