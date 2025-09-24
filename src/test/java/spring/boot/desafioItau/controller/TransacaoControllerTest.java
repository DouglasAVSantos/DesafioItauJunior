package spring.boot.desafioItau.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spring.boot.desafioItau.dtos.TransacaoDTO;
import spring.boot.desafioItau.service.TransacaoService;

import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class TransacaoControllerTest {

    @InjectMocks
    private TransacaoController controller;

    @Mock
    private TransacaoService service;
    private TransacaoDTO dto;

    @BeforeEach
    void setUp() {
        dto = new TransacaoDTO(200.0, OffsetDateTime.now());
    }

    @Test
    void cadastraComSucesso() {

        ResponseEntity<Void> result = Assertions.assertDoesNotThrow(() -> controller.cadastraTransacao(dto));

        Assertions.assertEquals(HttpStatus.CREATED,result.getStatusCode());
        Mockito.verify(service).adicionarTransacao(dto);
    }

    @Test
    void badRequestAoCadastrar(){
        ResponseEntity<Void> result = controller.cadastraTransacao(null);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,result.getStatusCode());
        Mockito.verify(service,never()).adicionarTransacao(any(TransacaoDTO.class));
    }

    @Test
    void unprocessableEntityAoCadastrarVazio(){
        TransacaoDTO vazio = new TransacaoDTO(200.1,null);
        ResponseEntity<Void> result = controller.cadastraTransacao(vazio);

        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,result.getStatusCode());
        Mockito.verify(service,never()).adicionarTransacao(any(TransacaoDTO.class));
    }

    @Test
    void unprocessableEntityAoCadastrar(){
        TransacaoDTO vazio = new TransacaoDTO(null,OffsetDateTime.now());
        ResponseEntity<Void> result = controller.cadastraTransacao(vazio);

        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,result.getStatusCode());
        Mockito.verify(service,never()).adicionarTransacao(any(TransacaoDTO.class));
    }
}
