package command;

import enums.OperatorEnum;
import model.Calculator;

import java.math.BigDecimal;

/**
 * 减法指令
 */
public class SubtractCommand implements UndoCommand{

    /** 计算器对象 */
    private final Calculator calculator;

    /** 被操作数 */
    private final BigDecimal operateValue;

    public SubtractCommand(Calculator calculator, BigDecimal operateValue) {
        this.calculator = calculator;
        this.operateValue = operateValue;
    }

    @Override
    public boolean execute() {
        calculator.calculate(operateValue, OperatorEnum.SUBTRACT);
        return true;
    }

    /**
     * 减法的undo操作相当于做加法
     */
    @Override
    public void undo() {
        calculator.calculate(operateValue, OperatorEnum.ADD);
    }
}
