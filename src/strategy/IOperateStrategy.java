package strategy;

import model.Calculator;

import java.math.BigDecimal;

public interface IOperateStrategy {

    BigDecimal calculate(BigDecimal operateValue, Calculator calculator);

}
