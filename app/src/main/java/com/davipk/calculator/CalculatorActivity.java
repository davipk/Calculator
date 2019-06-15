package com.davipk.calculator;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        DisplayOperatorFragment displayOperatorFragment = (DisplayOperatorFragment) getSupportFragmentManager()
                .findFragmentById(R.id.cont_fragment_display_operator);

        DisplayFragment displayFragment = (DisplayFragment) getSupportFragmentManager()
                .findFragmentById(R.id.cont_fragment_display);

        InputFragment inputFragment = (InputFragment) getSupportFragmentManager()
                .findFragmentById(R.id.cont_fragment_input);

        CalculatorPresenter presenter = new CalculatorPresenter(displayFragment, displayOperatorFragment);
        inputFragment.setPresenter(presenter);

    }
}
