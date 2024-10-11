package model;

import enums.OperatorEnum;
import strategy.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 计算器类
 */
public class Calculator {

    private static final Map<OperatorEnum, IOperateStrategy> STRATEGY_MAP = new HashMap<>();

    static {
        STRATEGY_MAP.put(OperatorEnum.ADD, new AddOperateStrategy());
        STRATEGY_MAP.put(OperatorEnum.SUBTRACT, new SubtractOperateStrategy());
        STRATEGY_MAP.put(OperatorEnum.MULTIPLY, new MultiplyOperateStrategy());
        STRATEGY_MAP.put(OperatorEnum.DIVIDE, new DivideOperateStrategy());
    }

    /**
     * get result
     * @return result
     */
    public BigDecimal getResult() {
        return result;
    }

    /**
     * 计算结果
     */
    private BigDecimal result = BigDecimal.ZERO;

    /**
     * 计算
     * @param operateValue 被操作的值
     * @param operatorEnum 操作类型
     */
    public void calculate(BigDecimal operateValue, OperatorEnum operatorEnum) {
        IOperateStrategy strategy = STRATEGY_MAP.get(operatorEnum);
        if (Objects.isNull(strategy)) {
            throw new RuntimeException("the operateType not support yet! ");
        }
        result = strategy.calculate(operateValue, this);
    }

}
