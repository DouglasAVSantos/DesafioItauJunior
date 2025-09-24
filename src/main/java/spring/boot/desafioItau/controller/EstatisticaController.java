package spring.boot.desafioItau.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.desafioItau.dtos.EstatisticaDTO;
import spring.boot.desafioItau.service.EstatisticasService;

import java.util.DoubleSummaryStatistics;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/estatistica")
public class EstatisticaController {

    private final EstatisticasService estatisticasService;

    @GetMapping()
    @Operation(description = "EndPoint responsável por retornar estatisticas (count, avg, min, max, sum) das transações cadastradas nos ultimos 60 segundos, esse valor pode ser alterado informando um parametro na requisição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estatisticas retornadas com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<EstatisticaDTO> retornaEstatistica(@RequestParam(value = "intervaloEmSegundos", required = false, defaultValue = "60") Integer intervaloEmSegundos) {
        DoubleSummaryStatistics status = estatisticasService.getEstatistica(intervaloEmSegundos);
        return status.getCount() > 0 ? ResponseEntity.ok(new EstatisticaDTO(status)) : ResponseEntity.ok(new EstatisticaDTO());
    }
}
