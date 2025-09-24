package spring.boot.desafioItau.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spring.boot.desafioItau.dtos.EstatisticaDTO;
import spring.boot.desafioItau.service.EstatisticasService;

import java.util.DoubleSummaryStatistics;

@ExtendWith(MockitoExtension.class)
public class EstatisticaControllerTest {

    @InjectMocks
    private EstatisticaController controller;

    @Mock
    private EstatisticasService service;

    private DoubleSummaryStatistics status;

    @BeforeEach
    void setUp(){
        status = new DoubleSummaryStatistics(2,5,10,15);
    }

    @Test
    void deveRetornarEstatistica(){
        Mockito.when(service.getEstatistica(60)).thenReturn(status);

        ResponseEntity<EstatisticaDTO> result = controller.retornaEstatistica(60);

        Assertions.assertEquals(HttpStatus.OK,result.getStatusCode());
        Assertions.assertEquals(2,result.getBody().getCount());
        Assertions.assertEquals(5,result.getBody().getMin());
        Assertions.assertEquals(10,result.getBody().getMax());
        Assertions.assertEquals(15,result.getBody().getSum());
    }

    @Test
    void deveRetornarEstatisticaZerada(){
        DoubleSummaryStatistics statusZerado = new DoubleSummaryStatistics(0,0,0,0);
        Mockito.when(service.getEstatistica(60)).thenReturn(statusZerado);

        ResponseEntity<EstatisticaDTO> result = controller.retornaEstatistica(60);

        Assertions.assertEquals(HttpStatus.OK,result.getStatusCode());
        Assertions.assertEquals(0,result.getBody().getCount());
        Assertions.assertEquals(0,result.getBody().getMin());
        Assertions.assertEquals(0,result.getBody().getMax());
        Assertions.assertEquals(0,result.getBody().getSum());
    }
}
