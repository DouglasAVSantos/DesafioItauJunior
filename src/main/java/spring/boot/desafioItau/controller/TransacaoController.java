package spring.boot.desafioItau.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.boot.desafioItau.controller.dtos.TransacaoDTO;
import spring.boot.desafioItau.model.Transacao;
import spring.boot.desafioItau.service.TransacaoService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TransacaoController {

    private final TransacaoService transacaoService;
    private Logger log = LoggerFactory.getLogger(TransacaoController.class);

    @PostMapping("/transacao")
    ResponseEntity<Void> cadastraTransacao(@RequestBody(required = false) TransacaoDTO body) {
        if (body == null) {
            log.error("Json Body não pode ser nulo");
            return ResponseEntity.badRequest().build();
        }
        if (body.valor() == null || body.dataHora() == null) {
            log.error("Existem campos nulos \nValor: " + body.valor() + "\nData: " + body.dataHora());
            return ResponseEntity.unprocessableEntity().build();
        } else {
            transacaoService.adicionarTransacao(new Transacao(body.valor(), body.dataHora()));
            return ResponseEntity.ok().build();
        }
    }


    @DeleteMapping("/transacao")
    ResponseEntity<Void> apagaTodosDados() {
        transacaoService.getListaDeTransacoes().clear();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/popular")
    ResponseEntity<Void> populaBanco() {
        transacaoService.popularListaDeTransacoes();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/visualizar")
    List<Transacao> visualizar() {
        return transacaoService.getListaDeTransacoes();
    }


}
