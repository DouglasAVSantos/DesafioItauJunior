package spring.boot.desafioItau.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;

public record TransacaoDTO(

       Double valor,
       @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
       OffsetDateTime dataHora
) {
}
