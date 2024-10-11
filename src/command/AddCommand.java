package command;

import enums.OperatorEnum;
import model.Calculator;

import java.math.BigDecimal;

/**
 * 加法指令
 */
public class AddCommand implements UndoCommand{

    private final Calculator calculator;

    private final BigDecimal operateValue;

    public AddCommand(Calculator calculator, BigDecimal operateValue) {
        this.calculator = calculator;
        this.operateValue = operateValue;
    }

    @Override
    public boolean execute() {
        calculator.calculate(operateValue, OperatorEnum.ADD);
        return true;
    }

    /**
     * 加法的撤销操作相当于做减法
     */
    @Override
    public void undo() {
        calculator.calculate(operateValue, OperatorEnum.SUBTRACT);
    }
}
