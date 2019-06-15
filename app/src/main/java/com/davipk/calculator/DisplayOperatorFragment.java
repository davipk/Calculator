package com.davipk.calculator;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayOperatorFragment extends Fragment implements CalculatorContract.PublishToOperatorView {

    @BindView(R.id.tv_operator_display)
    TextView operator_display;

    public DisplayOperatorFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_display_operator, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void updateOperation(String operation) {
        this.operator_display.setText(operation);
    }
}
