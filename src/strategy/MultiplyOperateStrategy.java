package strategy;

import model.Calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 乘法操作策略
 */
public class MultiplyOperateStrategy implements IOperateStrategy{

    /**
     * 乘法，保留3位有效数字，
     * 舍入模式：四舍五入
     * @param operateValue 操作数
     * @param calculator 计算器对象
     * @return 计算结果
     */
    @Override
    public BigDecimal calculate(BigDecimal operateValue, Calculator calculator) {
        return calculator.getResult().multiply(operateValue).setScale(3, RoundingMode.HALF_UP);
    }
}
