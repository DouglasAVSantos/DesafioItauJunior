package spring.boot.desafioItau.model;

import lombok.*;
import spring.boot.desafioItau.dtos.TransacaoDTO;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transacao {

    Long id;
    Double valor;
    OffsetDateTime dataHora;

    public Transacao(TransacaoDTO dto) {
        this.id = null;
        this.valor = dto.getValor();
        this.dataHora = dto.getDataHora();
    }

    public Transacao(Double valor, OffsetDateTime dataHora) {
        this.valor = valor;
        this.dataHora = dataHora;
    }
}


