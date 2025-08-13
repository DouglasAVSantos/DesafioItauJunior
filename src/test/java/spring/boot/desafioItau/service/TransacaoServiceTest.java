package spring.boot.desafioItau.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.boot.desafioItau.exception.UnprocessableEntity;
import spring.boot.desafioItau.model.Transacao;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @InjectMocks
    TransacaoService transacaoService;

    @BeforeEach
    void setup() {
        OffsetDateTime now = OffsetDateTime.now();
        Transacao transacaoA = new Transacao(10.00, now.minusSeconds(15));
        Transacao transacaoB = new Transacao(20.00, now.minusSeconds(30));
        Transacao transacaoC = new Transacao(30.00, now.minusSeconds(45));
        List<Transacao> lista  = Arrays.asList(transacaoA,transacaoB,transacaoC);
    }

    @Test
    void popularListaDeTransacoesComSucesso(){
    transacaoService.popularListaDeTransacoes();
    List<Transacao> list = transacaoService.getListaDeTransacoes();
    assertEquals(10,list.size());
    }

    @Test
    void adicionarTransacaoComSucesso(){
        var transacao = new Transacao(10.00, OffsetDateTime.now().minusMinutes(5));
        transacaoService.adicionarTransacao(transacao);
       var result = transacaoService.getListaDeTransacoes();
       assertEquals(1,result.size());
       assertEquals(transacao,result.getFirst());
    }

    @Test
    void lancaExcecaoCasoValorSejaNegativo(){
        var transacao = new Transacao(-10.00, OffsetDateTime.now().minusMinutes(5));
        UnprocessableEntity ex = assertThrows(UnprocessableEntity.class,()->transacaoService.adicionarTransacao(transacao));
        assertEquals("A transação deve ter valor maior ou igual a 0",ex.getMessage());
    }

    @Test
    void lancaExcecaoCasoDataFutura(){
        var transacao = new Transacao(10.00, OffsetDateTime.now().plusMinutes(1));
        UnprocessableEntity ex = assertThrows(UnprocessableEntity.class,()->transacaoService.adicionarTransacao(transacao));
        assertEquals("A transação não deve ter data e hora posterior a data e hora atual",ex.getMessage());
    }

}
















