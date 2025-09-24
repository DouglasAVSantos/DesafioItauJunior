package spring.boot.desafioItau.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spring.boot.desafioItau.exception.NotFound;
import spring.boot.desafioItau.model.Transacao;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class TransacaoRepositoryTest {

    TransacaoRepository repository;



    @BeforeEach
    void setUp() {
        repository = new TransacaoRepository();
        repository.deleteAll();
    }

    @Test
    void saveComSucesso() {
        var transacao = Transacao.builder()
                .valor(15.0)
                .dataHora(OffsetDateTime.now())
                .build();
        var result = repository.save(transacao);
        assertEquals(1, result.getId());
        assertEquals(15.0, result.getValor());
    }

    @Test
    void findByIdComSucesso() {
        var transacao = Transacao.builder()
                .valor(10.0)
                .dataHora(OffsetDateTime.now())
                .build();
        repository.save(transacao);

        Transacao result = repository.findById(transacao.getId()).orElseThrow();

        assertEquals(1, result.getId());
        assertEquals(10.0, result.getValor());
    }

    @Test
    void deleteByIdComSucesso() {
        var transacao = Transacao.builder()
                .valor(10.0)
                .dataHora(OffsetDateTime.now())
                .build();
        repository.save(transacao);

        assertDoesNotThrow(() -> repository.deleteById(transacao.getId()));
        var repo = repository.findAll();
        assertEquals(0,repo.size());
    }

    @Test
    void lancaNotFoundQuandoChamaDeleteById() {
        var transacao = Transacao.builder()
                .valor(10.0)
                .dataHora(OffsetDateTime.now())
                .build();

        NotFound result = assertThrows(NotFound.class, () -> repository.deleteById(10L));

        assertEquals("não encontrado", result.getMessage());
    }

    @Test
    void findAllComSucesso() {
        var transacao = Transacao.builder()
                .valor(10.0)
                .dataHora(OffsetDateTime.now())
                .build();
        repository.save(transacao);
        var lista = assertDoesNotThrow(() -> repository.findAll());

        assertEquals(1, lista.size());
        assertEquals(10.0, lista.get(0).getValor());
    }

    @Test
    void deleteAllComSucesso() {
        var transacao = Transacao.builder()
                .valor(10.0)
                .dataHora(OffsetDateTime.now())
                .build();
        repository.save(transacao);
        assertDoesNotThrow(() -> repository.deleteAll());
        var lista = assertDoesNotThrow(() -> repository.findAll());

        assertEquals(0, lista.size());
    }


}
