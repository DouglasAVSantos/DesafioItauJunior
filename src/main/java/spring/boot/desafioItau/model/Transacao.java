package spring.boot.desafioItau.model;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transacao {
    Double valor;
    OffsetDateTime dataHora;
}
