package spring.boot.desafioItau.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.DoubleSummaryStatistics;
@Getter
@Setter
public class Retorno {

    Double min;
    Double max;
    Double avg;
    Double sum;
    Long count;

    public Retorno(){
        this.min=0.0;
        this.max=0.0;
        this.count=0L;
        this.sum=0.0;
        this.avg=0.0;
    }

    public Retorno(
            DoubleSummaryStatistics dto
    ){
        this.min= dto.getMin();
        this.max= dto.getMax();
        this.count= dto.getCount();
        this.sum= dto.getSum();
        this.avg= dto.getAverage();

    }
}
