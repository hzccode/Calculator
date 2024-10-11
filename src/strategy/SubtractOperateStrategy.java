package strategy;

import model.Calculator;

import java.math.BigDecimal;

/**
 * 减法操作策略实现
 */
public class SubtractOperateStrategy implements IOperateStrategy{

    /**
     * 减法操作
     * @param operateValue 操作数
     * @param calculator 计算器对象
     * @return 计算结果
     */
    @Override
    public BigDecimal calculate(BigDecimal operateValue, Calculator calculator) {
        return calculator.getResult().subtract(operateValue);
    }
}
