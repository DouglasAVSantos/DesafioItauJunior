package spring.boot.desafioItau.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.boot.desafioItau.model.Transacao;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstatisticaServiceTest {

    @InjectMocks
    private EstatisticasService estatisticasService;

    @Mock
    private TransacaoService transacaoService;


    @BeforeEach
    void setup() {
        estatisticasService = new EstatisticasService(transacaoService);
    }

    @Test
    void getEstatisticaComTransacoesFiltradasDeveRetornarResumoCorreto() {
        OffsetDateTime agora = OffsetDateTime.now();
        Transacao t1 = new Transacao(10.0, agora.minusSeconds(10));
        Transacao t2 = new Transacao(20.0, agora.minusSeconds(30));
        Transacao t3 = new Transacao(30.0, agora.minusSeconds(70));

        List<Transacao> lista = Arrays.asList(t1, t2, t3);
        when(transacaoService.getListaDeTransacoes()).thenReturn(lista);

        DoubleSummaryStatistics stats = estatisticasService.getEstatistica(60);

        assertEquals(2, stats.getCount());
        assertEquals(10.0, stats.getMin());
        assertEquals(20.0, stats.getMax());
        assertEquals(15.0, stats.getAverage());
        assertEquals(30.0, stats.getSum());

        verify(transacaoService, times(1)).getListaDeTransacoes();
    }

    @Test
    void getEstatisticaSemTransacoesNoIntervaloDeveRetornarZero() {

        OffsetDateTime agora = OffsetDateTime.now();
        Transacao t1 = new Transacao(10.0, agora.minusSeconds(100));
        List<Transacao> lista = Arrays.asList(t1);

        when(transacaoService.getListaDeTransacoes()).thenReturn(lista);

        DoubleSummaryStatistics stats = estatisticasService.getEstatistica(60);

        assertEquals(0, stats.getCount());

        verify(transacaoService, times(1)).getListaDeTransacoes();
    }

}
