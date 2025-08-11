package spring.boot.desafioItau.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.desafioItau.controller.dtos.EstatisticaDTO;
import spring.boot.desafioItau.service.EstatisticasService;

import java.util.DoubleSummaryStatistics;

@RequiredArgsConstructor
@RestController
public class EstatisticaController {

    private final EstatisticasService estatisticasService;

    @GetMapping("/estatistica")
    EstatisticaDTO retornaEstatistica(@RequestParam(value = "intervaloEmSegundos", required = false, defaultValue = "60") Integer intervaloEmSegundos) {
        DoubleSummaryStatistics status = estatisticasService.getEstatistica(intervaloEmSegundos);
        return status.getCount() > 0 ? new EstatisticaDTO(status) : new EstatisticaDTO();
    }
}
