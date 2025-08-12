package spring.boot.desafioItau.controller.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
@Getter
@Setter
public class TransacaoDTO {

       private Double valor;
       private OffsetDateTime dataHora;

       public TransacaoDTO(Double valor, OffsetDateTime dataHora){
              this.valor = valor;
              this.dataHora = dataHora;
       }

       public TransacaoDTO(){

       }

}
