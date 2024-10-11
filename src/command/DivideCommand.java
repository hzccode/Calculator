package command;

import enums.OperatorEnum;
import model.Calculator;

import java.math.BigDecimal;

/**
 * 除法操作
 */
public class DivideCommand implements UndoCommand{

    /** 计算器对象 */
    private final Calculator calculator;

    /** 被操作数 */
    private final BigDecimal operateValue;

    public DivideCommand(Calculator calculator, BigDecimal operateValue) {
        this.calculator = calculator;
        this.operateValue = operateValue;
    }

    @Override
    public boolean execute() {
        calculator.calculate(operateValue, OperatorEnum.DIVIDE);
        return true;
    }

    @Override
    public void undo() {
        calculator.calculate(operateValue, OperatorEnum.MULTIPLY);
    }
}
