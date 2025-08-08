package spring.boot.desafioItau.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.boot.desafioItau.banco.Banco;
import spring.boot.desafioItau.dtos.Retorno;
import spring.boot.desafioItau.dtos.TransacaoDTO;
import spring.boot.desafioItau.model.Transacao;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@RestController
public class TransacaoController {
    private List<Transacao> banco = new Banco().getListaDeTransacoes();

    @PostMapping("/transacao")
    ResponseEntity<Void> cadastraTransacao(@RequestBody(required = false) TransacaoDTO body) {
        if (body == null) {
            return ResponseEntity.badRequest().build();
        }
        if (body.valor() == null || body.dataHora() == null) {
            return ResponseEntity.unprocessableEntity().build();
        } else {
            if (!body.dataHora().isAfter(OffsetDateTime.now())) {
                Transacao t = new Transacao(body.valor(), body.dataHora());
                Banco transacoes = new Banco();
                banco.add(t);
                System.out.println("adicionado transacao com sucesso");
                return ResponseEntity.ok().build();
            } else {
                System.out.println(body.dataHora().isAfter(OffsetDateTime.now()));
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
            }
        }

    }

    @DeleteMapping("/transacao")
    ResponseEntity<Void> apagaTodosDados() {
        banco.clear();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/popular")
    ResponseEntity<Void> populaBanco() {
        Banco a = new Banco();
        a.popular();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/visualizar")
    Banco visualizar() {
        Banco bancoCompleto = new Banco();
        return bancoCompleto;
    }

    @GetMapping("/estatistica")
    Retorno retornaEstatistica() {
        DoubleSummaryStatistics status = banco.stream().filter(c ->
                c.getDataHora().isAfter(OffsetDateTime.now().minusSeconds(60))
        ).mapToDouble(Transacao::getValor).summaryStatistics();

        return status.getCount() > 0 ? new Retorno(status) : new Retorno();
    }
}
