package spring.boot.desafioItau.controller.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.DoubleSummaryStatistics;

@Getter
@Setter
public class EstatisticaDTO {

    private Double min;
    private Double max;
    private Double avg;
    private Double sum;
    private Long count;

    public EstatisticaDTO() {
        this.min = 0.0;
        this.max = 0.0;
        this.count = 0L;
        this.sum = 0.0;
        this.avg = 0.0;
    }

    public EstatisticaDTO(
            DoubleSummaryStatistics dto
    ) {
        this.min = dto.getMin();
        this.max = dto.getMax();
        this.count = dto.getCount();
        this.sum = dto.getSum();
        this.avg = dto.getAverage();

    }
}
