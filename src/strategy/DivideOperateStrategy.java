package strategy;

import model.Calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 除法操作策略实现
 */
public class DivideOperateStrategy implements IOperateStrategy{
    /**
     * 除法，被除数不能为0
     * @param operateValue 操作数，不能为0
     * @param calculator 计算器对象
     * @return 返回结果
     */
    @Override
    public BigDecimal calculate(BigDecimal operateValue, Calculator calculator) {
        if (operateValue.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("division can not be zero");
        }
        return calculator.getResult().divide(operateValue,3, RoundingMode.HALF_UP);

    }
}
