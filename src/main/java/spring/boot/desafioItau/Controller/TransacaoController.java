package spring.boot.desafioItau.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.boot.desafioItau.banco.TransacaoService;
import spring.boot.desafioItau.dtos.EstatisticaDTO;
import spring.boot.desafioItau.dtos.TransacaoDTO;
import spring.boot.desafioItau.model.Transacao;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TransacaoController {

    private  final TransacaoService transacaoService;


    @PostMapping("/transacao")
    ResponseEntity<Void> cadastraTransacao(@RequestBody(required = false) TransacaoDTO body) {
        if (body == null) {
            return ResponseEntity.badRequest().build();
        }
        if (body.valor() == null || body.dataHora() == null) {
            return ResponseEntity.unprocessableEntity().build();
        } else {
            if (transacaoService.verificaValorHora(body.dataHora(),body.valor())) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
            }
        }
    }

    @DeleteMapping("/transacao")
    ResponseEntity<Void> apagaTodosDados() {
        transacaoService.getListaDeTransacoes().clear();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/popular")
    ResponseEntity<Void> populaBanco() {
        transacaoService.popular();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/visualizar")
    List<Transacao> visualizar() {
        return transacaoService.getListaDeTransacoes();
    }

    @GetMapping("/estatistica")
    EstatisticaDTO retornaEstatistica() {
        DoubleSummaryStatistics status = transacaoService.estatistica(60);
        return status.getCount() > 0 ? new EstatisticaDTO(status) : new EstatisticaDTO();
    }
}
