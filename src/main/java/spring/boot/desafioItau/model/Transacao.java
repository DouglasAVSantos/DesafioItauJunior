package spring.boot.desafioItau.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.boot.desafioItau.dtos.TransacaoDTO;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {
    Double valor;
    OffsetDateTime dataHora;

    public Transacao(TransacaoDTO dto) {
        this.valor = dto.getValor();
        this.dataHora = dto.getDataHora();
    }
}


