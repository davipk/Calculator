package com.davipk.calculator;

public class CalculatorPresenter implements CalculatorContract.ForwardInputInteractionToPresenter,
        Calculation.CalculationResult {

    private CalculatorContract.PublishToView publishResult;
    private CalculatorContract.PublishToOperatorView publishOperatorResult;
    private Calculation calc;

    public CalculatorPresenter(CalculatorContract.PublishToView publishResult, CalculatorContract.PublishToOperatorView publishOperatorResult) {
        this.publishResult = publishResult;
        this.publishOperatorResult = publishOperatorResult;
        calc = new Calculation();
        calc.setCalculationResultListener(this);
    }

    @Override
    public void onNumberClick(String number) {
        calc.addNumber(number);
    }

    @Override
    public void onDecimalClick() {
        calc.addDecimal();
    }

    @Override
    public void onNegateClick() {
        calc.negate();
    }

    @Override
    public void onEvaluateClick() {
        calc.evaluate(true);
    }

    @Override
    public void onOperatorClick(String operator) {
        calc.addOperation(operator);
    }

    @Override
    public void onSpecialOperatorClick(String operator) {
        calc.addSpecialOperation(operator);
    }

    @Override
    public void onClearClick() {
        calc.clear();
    }

    @Override
    public void onClearEntryClick() {
        calc.clearEntry();
    }

    @Override
    public void onBackspaceClick() {
        calc.backspace();
    }

    @Override
    public void onExpressionChanged(String result, String operation, boolean successful) {
        if (successful) {
            publishResult.showResult(result);
            publishOperatorResult.updateOperation(operation);
        } else {
            publishResult.showErrorMessage(result);
        }
    }
}
