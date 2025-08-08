package spring.boot.desafioItau.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransacaoDTO(

       Double valor,
       @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
       OffsetDateTime dataHora
) {
}
