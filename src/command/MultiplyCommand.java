package command;

import enums.OperatorEnum;
import model.Calculator;

import java.math.BigDecimal;

/**
 * 乘法实现类
 */
public class MultiplyCommand implements UndoCommand{

    /** 计算器对象 */
    private final Calculator calculator;

    /** 被操作数 */
    private final BigDecimal operateValue;

    public MultiplyCommand(Calculator calculator, BigDecimal operateValue) {
        this.calculator = calculator;
        this.operateValue = operateValue;
    }


    @Override
    public boolean execute() {
        calculator.calculate(operateValue, OperatorEnum.MULTIPLY);
        return true;
    }

    /**
     * 乘法的undo操作相当于做除法
     */
    @Override
    public void undo() {
        calculator.calculate(operateValue,OperatorEnum.DIVIDE);
    }
}
