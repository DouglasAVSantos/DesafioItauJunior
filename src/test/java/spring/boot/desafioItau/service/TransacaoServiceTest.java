package spring.boot.desafioItau.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.boot.desafioItau.dtos.TransacaoDTO;
import spring.boot.desafioItau.exception.UnprocessableEntity;
import spring.boot.desafioItau.model.Transacao;
import spring.boot.desafioItau.repository.TransacaoRepository;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @InjectMocks
    TransacaoService transacaoService;

    TransacaoRepository repository = mock(TransacaoRepository.class);

    List<Transacao> lista;

    @BeforeEach
    void setup() {
        OffsetDateTime now = OffsetDateTime.now();
        Transacao transacaoA = new Transacao(10.00, now.minusSeconds(15));
        Transacao transacaoB = new Transacao(20.00, now.minusSeconds(30));
        Transacao transacaoC = new Transacao(30.00, now.minusSeconds(45));
        lista = Arrays.asList(transacaoA, transacaoB, transacaoC);
    }


    @Test
    void lancaExcecaoCasoValorSejaNegativo() {
        var transacao = new TransacaoDTO(-10.00, OffsetDateTime.now().minusMinutes(5));
        UnprocessableEntity ex = assertThrows(UnprocessableEntity.class, () -> transacaoService.adicionarTransacao(transacao));
        assertEquals("A transação deve ter valor maior ou igual a 0", ex.getMessage());
    }

    @Test
    void lancaExcecaoCasoDataFutura() {
        var transacao = new TransacaoDTO(10.00, OffsetDateTime.now().plusMinutes(1));
        UnprocessableEntity ex = assertThrows(UnprocessableEntity.class, () -> transacaoService.adicionarTransacao(transacao));
        assertEquals("A transação não deve ter data e hora posterior a data e hora atual", ex.getMessage());
    }

    @Test
    void deveAdicionarTransacaoComSucesso() {
        TransacaoDTO dto = new TransacaoDTO(20.0, OffsetDateTime.now());

        Assertions.assertDoesNotThrow(() -> transacaoService.adicionarTransacao(dto));
        verify(repository).save(any(Transacao.class));
    }

    @Test
    void deveDeletarTudoComSucesso() {
        assertDoesNotThrow(() -> transacaoService.deleteAll());

        verify(repository).deleteAll();
    }

    @Test
    void getListaDeTransacoesComSucesso() {
        when(repository.findAll()).thenReturn(lista);

        List<Transacao> result = transacaoService.getListaDeTransacoes();

        assertEquals(3, result.size());
        assertEquals(10, result.get(0).getValor());
        assertEquals(20, result.get(1).getValor());
        assertEquals(30, result.get(2).getValor());

        verify(repository).findAll();
    }

    @Test
    void populaListaComSucesso() {
        assertDoesNotThrow(() -> transacaoService.popularListaDeTransacoes());

        verify(repository, atLeast(1)).save(any(Transacao.class));
    }

}
















