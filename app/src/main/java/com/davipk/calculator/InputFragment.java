package com.davipk.calculator;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class InputFragment extends Fragment {

    private CalculatorContract.ForwardInputInteractionToPresenter forwardInteraction;

    public InputFragment() {
    }

    public void setPresenter(CalculatorContract.ForwardInputInteractionToPresenter forwardInteraction) {
        this.forwardInteraction = forwardInteraction;
    }

    @OnClick({R.id.btn_number_zero, R.id.btn_number_one, R.id.btn_number_two, R.id.btn_number_three,
            R.id.btn_number_four, R.id.btn_number_five, R.id.btn_number_six, R.id.btn_number_seven,
            R.id.btn_number_eight, R.id.btn_number_nine})
    public void onNumberClick(Button b) {
        forwardInteraction.onNumberClick(b.getText().toString());
    }

    @OnClick({R.id.btn_operator_add, R.id.btn_operator_subtract, R.id.btn_operator_multiply,
            R.id.btn_operator_divide})
    public void onOperatorClick(Button b) {
        forwardInteraction.onOperatorClick(b.getText().toString());
    }

    @OnClick({R.id.btn_operator_inverse, R.id.btn_operator_sqrt,
            R.id.btn_operator_square, R.id.btn_operator_percent})
    public void onSpecialOperatorClick(Button b) {
        String operation = "";
        switch (b.getId()) {
            case R.id.btn_operator_square:
                operation = "sqr";
                break;
            case R.id.btn_operator_inverse:
                operation = "inv";
                break;
            default:
                operation = b.getText().toString();
        }
        forwardInteraction.onSpecialOperatorClick(operation);
    }

    @OnClick(R.id.btn_number_dot)
    public void onDecimalClick(Button b) {
        forwardInteraction.onDecimalClick();
    }

    @OnClick(R.id.btn_operator_negate)
    public void onNegateClick(Button b) {
        forwardInteraction.onNegateClick();
    }

    @OnClick(R.id.btn_clear)
    public void onClearClick(Button b) {
        forwardInteraction.onClearClick();
    }

    @OnClick(R.id.btn_clear_entry)
    public void onClearEntryClick(Button b) {
        forwardInteraction.onClearEntryClick();
    }

    @OnClick(R.id.btn_backspace)
    public void onBackspaceClick(Button b) {
        forwardInteraction.onBackspaceClick();
    }

    @OnClick(R.id.btn_operator_evaluate)
    public void onEvaluateClick(Button b) {
        forwardInteraction.onEvaluateClick();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_input, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

}
