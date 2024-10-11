import enums.OperatorEnum;
import model.Calculator;
import service.ICalculatorManagerService;
import service.impl.CalculatorManagerServiceImpl;

import java.math.BigDecimal;

public class CalculatorMain {

    private static final String ERROR_MESSAGE = "division can not be zero";

    public static void main(String[] args) {

        //校验简单加法
        verifySimpleAdd();

        //校验简单减法
        verifySimpleSubtract();

        //校验简单乘法
        verifySimpleMultiply();

        //校验简单除法
        verifySimpleDivide();

        //校验简单除法，被除数为0
        verifySimpleDivideWhenDivisionIsZero();

        //校验简单除法，无法整除。
        verifyDivideScale();

        //校验组合运算，加减乘除
        verifyComplexOperate();

        //组合运算，加减乘除。进行一次undo、redo操作
        verifyComplexOperateWithUndoAndRedoOnce();

        //组合运算，加减乘除。进行多次undo、redo操作
        verifyComplexOperateWithUndoAndRedoMulti();

        //组合运算，加减乘除。只有redo操作
        verifyComplexOperateWithRedoOnly();


        System.out.println("恭喜，所有测试用例已通过！");


    }

    /**
     * 验证简单加法操作
     */
    private static void verifySimpleAdd(){
        Calculator calculator = new Calculator();
        ICalculatorManagerService calculatorManager = new CalculatorManagerServiceImpl(calculator);
        // 0 + 10 = 10
        calculatorManager.execute(new BigDecimal(10), OperatorEnum.ADD);
        assert equals(calculatorManager.getResult(),new BigDecimal(10));
    }

    /**
     * 验证简单减法操作
     */
    private static void verifySimpleSubtract(){
        Calculator calculator = new Calculator();
        ICalculatorManagerService calculatorManager = new CalculatorManagerServiceImpl(calculator);
        // 0 - 10 = -10
        calculatorManager.execute(new BigDecimal(10), OperatorEnum.SUBTRACT);
        assert equals(calculatorManager.getResult(),new BigDecimal(-10));
    }

    /**
     * 验证简单乘法操作
     */
    private static void verifySimpleMultiply(){
        Calculator calculator = new Calculator();
        ICalculatorManagerService calculatorManager = new CalculatorManagerServiceImpl(calculator);
        // 0 * 10 = 0
        calculatorManager.execute(new BigDecimal(10), OperatorEnum.MULTIPLY);
        //第一次相当于0*10，所以结果还是等于0
        assert equals(calculatorManager.getResult(),BigDecimal.ZERO);

        // 0 + 1 = 1
        calculatorManager.execute(new BigDecimal(1), OperatorEnum.ADD);
        // 1 * 6 = 6
        calculatorManager.execute(new BigDecimal(6), OperatorEnum.MULTIPLY);
        assert equals(calculatorManager.getResult(),new BigDecimal(6));
    }


    /**
     * 验证简单除法操作
     */
    private static void verifySimpleDivide(){
        Calculator calculator = new Calculator();
        ICalculatorManagerService calculatorManager = new CalculatorManagerServiceImpl(calculator);
        // 0 / 10 = 0
        calculatorManager.execute(new BigDecimal(10), OperatorEnum.DIVIDE);
        assert equals(calculatorManager.getResult(),new BigDecimal(0));

        // 0 + 10 = 10
        calculatorManager.execute(new BigDecimal(10), OperatorEnum.ADD);
        // 10 / 2 = 5
        calculatorManager.execute(new BigDecimal(2), OperatorEnum.DIVIDE);
        assert equals(calculatorManager.getResult(),new BigDecimal(5));
    }


    /**
     * 验证简单除法操作，被除数为0
     * 异常case
     */
    private static void verifySimpleDivideWhenDivisionIsZero(){
        try {
            Calculator calculator = new Calculator();
            ICalculatorManagerService calculatorManager = new CalculatorManagerServiceImpl(calculator);
            // 0 / 10 = 0
            calculatorManager.execute(new BigDecimal(10), OperatorEnum.DIVIDE);
            assert calculatorManager.getResult().compareTo(BigDecimal.ZERO) == 0;

            // 0 + 10 = 10
            calculatorManager.execute(new BigDecimal(10), OperatorEnum.ADD);
            // 10 / 0
            calculatorManager.execute(new BigDecimal(0), OperatorEnum.DIVIDE);
        } catch (Exception e) {
            //校验被除数为0的场景，抛的异常为IllegalArgumentException
            assert e instanceof IllegalArgumentException;
            //校验被除数为0的场景，校验抛的异常描述信息是否相等
            assert ERROR_MESSAGE.equalsIgnoreCase(e.getMessage());
        }

    }

    /**
     * 验证复合操作，加减乘除
     */
    private static void verifyComplexOperate(){
        Calculator calculator = new Calculator();
        ICalculatorManagerService calculatorManager = new CalculatorManagerServiceImpl(calculator);
        // 0 + 10 = 10
        calculatorManager.execute(new BigDecimal(10), OperatorEnum.ADD);
        assert equals(calculatorManager.getResult(),new BigDecimal(10));

        // 10 * 2 = 20
        calculatorManager.execute(new BigDecimal(2), OperatorEnum.MULTIPLY);
        assert equals(calculatorManager.getResult(),new BigDecimal(20));

        // 20 / 4 = 5
        calculatorManager.execute(new BigDecimal(4), OperatorEnum.DIVIDE);
        assert equals(calculatorManager.getResult(),new BigDecimal(5));

        // 5 - 2 = 3
        calculatorManager.execute(new BigDecimal(2), OperatorEnum.SUBTRACT);
        assert equals(calculatorManager.getResult(),new BigDecimal(3));

    }


    /**
     * 验证复合操作，加减乘除,验证一次undo、redo操作
     */
    private static void verifyComplexOperateWithUndoAndRedoOnce(){
        Calculator calculator = new Calculator();
        ICalculatorManagerService calculatorManager = new CalculatorManagerServiceImpl(calculator);
        // 0 + 10 = 10
        calculatorManager.execute(new BigDecimal(10), OperatorEnum.ADD);
        assert equals(calculatorManager.getResult(),new BigDecimal(10));

        // 10 * 2 = 20
        calculatorManager.execute(new BigDecimal(2), OperatorEnum.MULTIPLY);
        assert equals(calculatorManager.getResult(),new BigDecimal(20));

        // 20 / 4 = 5
        calculatorManager.execute(new BigDecimal(4), OperatorEnum.DIVIDE);
        assert equals(calculatorManager.getResult(),new BigDecimal(5));

        // 5 - 2 = 3
        calculatorManager.execute(new BigDecimal(2), OperatorEnum.SUBTRACT);
        assert equals(calculatorManager.getResult(),new BigDecimal(3));

        //undo操作，3 -> 5
        calculatorManager.undo();
        assert equals(calculatorManager.getResult(),new BigDecimal(5));

        //redo操作,5 -> 3
        calculatorManager.redo();
        assert equals(calculatorManager.getResult(),new BigDecimal(3));

    }


    /**
     * 验证复合操作，加减乘除,验证多次undo、redo操作
     */
    private static void verifyComplexOperateWithUndoAndRedoMulti(){
        Calculator calculator = new Calculator();
        ICalculatorManagerService calculatorManager = new CalculatorManagerServiceImpl(calculator);
        // 0 + 10 = 10
        calculatorManager.execute(new BigDecimal(10), OperatorEnum.ADD);
        assert equals(calculatorManager.getResult(),new BigDecimal(10));

        // 10 * 2 = 20
        calculatorManager.execute(new BigDecimal(2), OperatorEnum.MULTIPLY);
        assert equals(calculatorManager.getResult(),new BigDecimal(20));

        // 20 / 4 = 5
        calculatorManager.execute(new BigDecimal(4), OperatorEnum.DIVIDE);
        assert equals(calculatorManager.getResult(),new BigDecimal(5));

        // 5 - 2 = 3
        calculatorManager.execute(new BigDecimal(2), OperatorEnum.SUBTRACT);
        assert equals(calculatorManager.getResult(),new BigDecimal(3));

        //undo操作，3 -> 5
        calculatorManager.undo();
        assert equals(calculatorManager.getResult(),new BigDecimal(5));

        // undo操作，5 -> 20
        calculatorManager.undo();
        assert equals(calculatorManager.getResult(),new BigDecimal(20));

        //redo操作，20 -> 5
        calculatorManager.redo();
        assert equals(calculatorManager.getResult(),new BigDecimal(5));

        //redo操作，5 -> 3
        calculatorManager.redo();
        assert equals(calculatorManager.getResult(),new BigDecimal(3));

        // undo操作，3 -> 5
        calculatorManager.undo();
        assert equals(calculatorManager.getResult(),new BigDecimal(5));

        //add操作，5 + 4 = 9
        calculatorManager.execute(new BigDecimal(4), OperatorEnum.ADD);
        assert equals(calculatorManager.getResult(),new BigDecimal(9));

        //undo操作，9 -> 5
        calculatorManager.undo();
        assert equals(calculatorManager.getResult(),new BigDecimal(5));

        //redo操作, 5 -> 9
        calculatorManager.redo();
        assert equals(calculatorManager.getResult(),new BigDecimal(9));

    }


    /**
     * 验证复合操作，加减乘除,验证仅redo操作，如果没有undo操作的话，redoStack为空，此时返回的是之前的值
     */
    private static void verifyComplexOperateWithRedoOnly(){
        Calculator calculator = new Calculator();
        ICalculatorManagerService calculatorManager = new CalculatorManagerServiceImpl(calculator);
        // 0 + 10 = 10
        calculatorManager.execute(new BigDecimal(10), OperatorEnum.ADD);
        assert equals(calculatorManager.getResult(),new BigDecimal(10));

        // 10 * 2 = 20
        calculatorManager.execute(new BigDecimal(2), OperatorEnum.MULTIPLY);
        assert equals(calculatorManager.getResult(),new BigDecimal(20));

        //20 / 4 = 5
        calculatorManager.execute(new BigDecimal(4), OperatorEnum.DIVIDE);
        assert equals(calculatorManager.getResult(),new BigDecimal(5));

        // 5 - 2 = 3
        calculatorManager.execute(new BigDecimal(2), OperatorEnum.SUBTRACT);
        assert equals(calculatorManager.getResult(),new BigDecimal(3));

        //redo不生效，result=3
        calculatorManager.redo();
        assert equals(calculatorManager.getResult(),new BigDecimal(3));

        //redo不生效，result=3
        calculatorManager.redo();
        assert equals(calculatorManager.getResult(),new BigDecimal(3));

    }

    /**
     * 校验除法，当无法整除时，保留3位有效数字
     */
    private static void verifyDivideScale(){
        Calculator calculator = new Calculator();
        ICalculatorManagerService calculatorManager = new CalculatorManagerServiceImpl(calculator);
        //0 + 10 = 10
        calculatorManager.execute(new BigDecimal(10), OperatorEnum.ADD);
        assert equals(calculatorManager.getResult(),new BigDecimal(10));

        // 10 / 3 = 3.333
        calculatorManager.execute(new BigDecimal(3), OperatorEnum.DIVIDE);
        assert equals(calculatorManager.getResult().setScale(3),new BigDecimal("3.333"));

    }


    /**
     * 判断BigDecimal值是否相等
     * @param origin
     * @param source
     * @return
     */
    private static boolean equals(BigDecimal origin, BigDecimal source) {
        if (origin == null && source == null) {
            return true;
        }
        if (origin == null || source == null) {
            return false;
        }
        return origin.compareTo(source) == 0;
    }

}