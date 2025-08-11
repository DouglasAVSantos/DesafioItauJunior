package spring.boot.desafioItau.controller.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;

import java.time.OffsetDateTime;

public record TransacaoDTO(

       Double valor,
       @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
       OffsetDateTime dataHora
) {
}
