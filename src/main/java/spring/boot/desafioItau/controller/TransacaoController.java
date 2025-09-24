package spring.boot.desafioItau.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.boot.desafioItau.dtos.TransacaoDTO;
import spring.boot.desafioItau.service.TransacaoService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;
    private Logger log = LoggerFactory.getLogger(TransacaoController.class);

    @PostMapping
    @Operation(description = "EndPoint responsável por cadastrar transações.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "O body da requisição esta vazio."),
            @ApiResponse(responseCode = "422", description = "Existem campos que não atendem aos requisitos de transação."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    }
    )
    ResponseEntity<Void> cadastraTransacao(@RequestBody(required = false) TransacaoDTO body) {
        if (body == null) {
            log.error("Json Body não pode ser nulo");
            return ResponseEntity.badRequest().build();
        }
        if (body.getValor() == null || body.getDataHora() == null) {
            log.error("Existem campos nulos \nValor: " + body.getValor() + "\nData: " + body.getDataHora());
            return ResponseEntity.unprocessableEntity().build();
        } else {
            transacaoService.adicionarTransacao(body);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }


    @DeleteMapping
    @Operation(description = "EndPoint responsável por deletar todas as transações cadastradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todas as transações foram deletadas com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    ResponseEntity<Void> apagaTodosDados() {
        transacaoService.getListaDeTransacoes().clear();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/popular")
    @Operation(description = "EndPoint responsável por criar transações para fins de testes \nEle cria 10 transações.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todas as transações foram criadas com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    ResponseEntity<Void> populaBanco() {
        transacaoService.popularListaDeTransacoes();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/visualizar")
    @Operation(description = "EndPoint responsável por visualizar todas as transações cadastradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todas as transações foram retornadas com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    ResponseEntity<List<TransacaoDTO>> visualizar() {
        return ResponseEntity.ok(transacaoService.getListaDeTransacoes().stream()
                .map(m -> new TransacaoDTO(m.getValor(), m.getDataHora())).toList());
    }


}
