package com.davipk.calculator;

public interface CalculatorContract {

    //Our View handles these methods (DisplayFragment)
    interface PublishToView {
        void showResult(String result);

        void showErrorMessage(String message);
    }

    interface PublishToOperatorView {
        void updateOperation(String operation);
    }

    //passes click events from our View (InputFragment) to the presenter
    interface ForwardInputInteractionToPresenter {
        void onNumberClick(String number);

        void onDecimalClick();

        void onNegateClick();

        void onEvaluateClick();

        void onOperatorClick(String operator);

        void onSpecialOperatorClick(String operator);

        void onClearClick();

        void onClearEntryClick();

        void onBackspaceClick();
    }
}
