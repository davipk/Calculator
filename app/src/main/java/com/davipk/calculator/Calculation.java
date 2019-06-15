package com.davipk.calculator;

import android.util.Pair;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;


/**
 * This class handles everything in regards to basic calculator operations
 */
public class Calculation {

    private CalculationResult calculationResult;
    private String currentExpression;
    private String lastEvaluatedExpression;
    private boolean isEvaluated;
    private boolean isOperatorSelected;
    private List<Pair<String, String>> operators = new ArrayList<>();

    public void setCalculationResultListener(CalculationResult calculationResult) {
        this.calculationResult = calculationResult;
        currentExpression = "0";
        refreshExpression();
    }

    public void refreshExpression() {
        calculationResult.onExpressionChanged(currentExpression, displayOperatorExpression(), true);
    }

    public void refreshExpression(String error) {
        calculationResult.onExpressionChanged(error, displayOperatorExpression(), false);
    }

    public Pair<String, String> getLastOperator() {
        if (operators.isEmpty()) {
            return null;
        } else {
            return operators.get(operators.size() - 1);
        }
    }

    /**
     * Deletes the last character of current expression if non-zero.
     */
    public void backspace() {
        if (currentExpression.length() != 1) {
            currentExpression = currentExpression.substring(0, currentExpression.length() - 1);
            if (currentExpression.equals("-")) {
                currentExpression = "0";
            }
        } else {
            currentExpression = "0";
        }
        refreshExpression();
    }

    /**
     * Deletes the entire current expression if non-zero.
     */
    public void clearEntry() {
        if (!currentExpression.equals("0")) {
            currentExpression = "0";
        }
        refreshExpression();
    }

    /**
     * Deletes the current and operator expression entry.
     */
    public void clear() {
        if (!currentExpression.equals("0")) {
            currentExpression = "0";
        }
        isEvaluated = false;
        isOperatorSelected = false;
        operators.clear();
        lastEvaluatedExpression = null;
        refreshExpression();
    }

    /**
     * Appends the number to currentExpression upon getting clicked.
     *
     * @param number the character that is getting added
     */
    public void addNumber(String number) {
        if (currentExpression.length() >= 30) {
            refreshExpression("Cannot exceed 30 digits.");
        } else {
            if (currentExpression.equals("0") || isEvaluated) {
                isEvaluated = false;
                currentExpression = "";
            }
            isOperatorSelected = false;
            currentExpression += number;
            refreshExpression();
        }
    }

    /**
     * Adds decimal places if not present upon getting clicked.
     */
    public void addDecimal() {
        if (isEvaluated) {
            isEvaluated = false;
            currentExpression = "0";
        }
        if (!currentExpression.contains(".")) {
            currentExpression += ".";
        }
        refreshExpression();
    }

    /**
     * Flips positive/negative signs of current expression.
     * First checks whether the first character is negative or positive.
     * If negative, remove the first negative sign.
     * If positive, simply add negative sign in the first character.
     */
    public void negate() {
        if (currentExpression.equals("0")) {
            return;
        }
        String newExpression;
        if (currentExpression.charAt(0) == '-') {
            newExpression = currentExpression.substring(1);
        } else {
            newExpression = "-" + currentExpression;
        }
        currentExpression = newExpression;
        refreshExpression();
    }

    /**
     * Performs various operations depending on what has been clicked.
     *
     * @param operator distinguishes what type of operation it is
     *                 supports addition, subtraction, division, and multiplication.
     */
    public void addOperation(String operator) {
        if (currentExpression.equals("0.") || currentExpression.equals("-0.")) {
            currentExpression = "0";
        }
        String tempExpression = currentExpression;
        if (isOperatorSelected && getLastOperator() != null) {
            tempExpression = getLastOperator().second;
            operators.remove(getLastOperator());
        } else {
            evaluate(false);
        }
        operators.add(new Pair<String, String>(operator, tempExpression));
        isOperatorSelected = true;
        refreshExpression();
    }

    /**
     * Performs various special operations depending on what has been clicked.
     *
     * @param operator type of operation
     *                 supports percentage, square, square root, and inverse.
     */
    public void addSpecialOperation(String operator) {
        if (currentExpression.equals("0.") || currentExpression.equals("-0.")) {
            currentExpression = "0";
        }
        operators.clear();
        Pair<String, String> toAdd = new Pair<String, String>(operator, currentExpression);
        operators.add(toAdd);
        double expression = Double.parseDouble(currentExpression);
        double result;
        switch (operator) {
            case "%":
                result = expression / 100.00;
                break;
            case "√":
                if (expression < 0) {
                    refreshExpression("You cannot square root a negative number.");
                    return;
                }
                result = Math.sqrt(expression);
                break;
            case "sqr":
                result = expression * expression;
                break;
            case "inv":
                if (expression == 0) {
                    refreshExpression("You cannot divide by zero.");
                    return;
                }
                result = 1 / expression;
                break;
            default:
                refreshExpression("Unknown special operator: " + operator);
                return;
        }
        BigDecimal bd = new BigDecimal(result);
        bd = bd.round(new MathContext(10));
        currentExpression = bd.stripTrailingZeros().toPlainString();
        lastEvaluatedExpression = currentExpression;
        isEvaluated = true;
        refreshExpression();
        operators.remove(toAdd);
    }

    /**
     * Checks current expression and last operator.
     * If exists, then it evaluates the operation as a BigDecimal by default.
     * After, it rounds up to ten significant digits and outputs the result.
     *
     * @param clear true = clears the program, else call clearEntry() and record expression.
     */
    public void evaluate(boolean clear) {
        if (!operators.isEmpty()) {
            BigDecimal first = new BigDecimal(lastEvaluatedExpression != null ? lastEvaluatedExpression : getLastOperator().second);
            BigDecimal second = new BigDecimal(currentExpression);
            BigDecimal result;
            switch (getLastOperator().first) {
                case "+":
                    result = first.add(second);
                    break;
                case "-":
                    result = first.subtract(second);
                    break;
                case "×":
                    result = first.multiply(second);
                    break;
                case "÷":
                    if (second.doubleValue() == 0) {
                        refreshExpression("You cannot divide by zero.");
                        return;
                    }
                    result = first.divide(second, new MathContext(10));
                    break;
                default:
                    refreshExpression("Unknown operator: " + getLastOperator().first);
                    return;
            }
            System.out.println("ex: " + first.toPlainString() + " " + getLastOperator().first + " " + second.toPlainString() + " = " + result.toPlainString() + " / re: " + displayOperatorExpression() + " " + currentExpression + " = " + result.toPlainString());
            String tempExp = result.stripTrailingZeros().toPlainString();
            if (clear) {
                clear();
            } else {
                clearEntry();
                lastEvaluatedExpression = tempExp;
            }
            currentExpression = tempExp;
            refreshExpression();
        }
        isEvaluated = true;
    }

    /**
     * Returns the current operator expression to the display
     *
     * @return operator expression
     */
    public String displayOperatorExpression() {
        if (operators.isEmpty()) {
            return "";
        } else {
            String output = "";
            for (Pair<String, String> operator : operators) {
                if (operator.first.equals("√") || operator.first.equals("sqr") || operator.first.equals("inv")) {
                    output += " " + operator.first + "(" + operator.second + ")";
                } else {
                    output += " " + operator.second + " " + operator.first;
                }
            }
            return output;
        }
    }

    interface CalculationResult {
        void onExpressionChanged(String result, String operation, boolean successful);
    }

}
