package service.impl;

import command.*;
import enums.OperatorEnum;
import model.Calculator;
import service.ICalculatorManagerService;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Stack;

/**
 * 计算管理类
 */
public class CalculatorManagerServiceImpl implements ICalculatorManagerService {

    /**
     * undo 命令栈
     */
    private Stack<UndoCommand> undoStack = new Stack<>();

    /**
     * redo命令栈
     */
    private Stack<UndoCommand> redoStack = new Stack<>();

    /**
     * 计算器对象
     */
    private final Calculator calculator;

    public CalculatorManagerServiceImpl(Calculator calculator) {
        this.calculator = calculator;
    }



    @Override
    public void execute(BigDecimal operateValue, OperatorEnum operatorEnum) {
        Command command = getCommand(calculator, operateValue, operatorEnum);
        if (command instanceof UndoCommand) {
            undoStack.push((UndoCommand) command);
        }
        boolean execSuccess = command.execute();
        if (execSuccess) {
            //计算成功，则清空redo
            redoStack.clear();
        }
    }

    /**
     * 执行undo操作
     */
    @Override
    public void undo(){
        if (undoStack.isEmpty()) {
            return;
        }
        UndoCommand undoCommand = undoStack.pop();
        if (Objects.nonNull(undoCommand)) {
            redoStack.push(undoCommand);
            undoCommand.undo();
        }

    }

    /**
     * redo操作
     */
    @Override
    public void redo(){
        //redo栈为空，则不进行处理
        if (redoStack.isEmpty()) {
            return;
        }
        UndoCommand command = redoStack.pop();
        undoStack.push(command);
        command.execute();
    }

    @Override
    public BigDecimal getResult() {
        return calculator.getResult();
    }

    /**
     * 根据操作类型获取相应的操作指令
     * @param calculator 计算器对象
     * @param operateValue 被操作数
     * @param operatorEnum 操作类型
     * @return @see main.java.com.hetufei.command.Command
     */
    private Command getCommand(Calculator calculator,BigDecimal operateValue, OperatorEnum operatorEnum){
        switch (operatorEnum) {
            case ADD:
                return new AddCommand(calculator, operateValue);
            case SUBTRACT:
                return new SubtractCommand(calculator, operateValue);
            case MULTIPLY:
                return new MultiplyCommand(calculator,operateValue);
            case DIVIDE:
                return new DivideCommand(calculator, operateValue);
            default:
                throw new IllegalArgumentException("this operation not support yet! ");
        }
    }
}
