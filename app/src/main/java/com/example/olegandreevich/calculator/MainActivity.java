package com.example.olegandreevich.calculator;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private final String DIVISION = "/";
    private final String ADDITION = "+";
    private final String MULTIPLICATION = "*";
    private final String SUBTRACTION = "-";
    private final String EQUALLY = "=";
    private final String DOT = ".";

    private Double operand = null;
    private Double lastOperand = null;
    private String lastOperation = EQUALLY;
    private boolean isOperandEmpty = true;
    private String history = "";
    private String etView = "";


    @BindView(R.id.textViewHistory)
    TextView textViewHistory;
    @BindView(R.id.editTextOperand)
    EditText editTextOperand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @OnClick({R.id.btnOne, R.id.btnTwo, R.id.btnThree, R.id.btnFour, R.id.btnFive, R.id.btnSix, R.id.btnSeven, R.id.btnEight, R.id.btnNine, R.id.btnZero})
    public void onNumberButtonClick(View view) {

        if (isOperandEmpty) {
            editTextOperand.setText("");
        }
        Button button = (Button) view;
        editTextOperand.append(button.getText().toString());
        isOperandEmpty = false;


    }

    @OnClick({R.id.btnDivision, R.id.btnAddition, R.id.btnMultiplication, R.id.btnSubtraction})
    public void onOperationClick(View view) {
        Button button = (Button) view;
        String operation = button.getText().toString();
        String number = editTextOperand.getText().toString();

        if (number.length() > 0) {
            try {
                performOperation(number, operation);
            } catch (NumberFormatException e) {
                editTextOperand.setText("");
            }
        }
        isOperandEmpty = true;

    }

    @OnClick(R.id.btnEqually)
    public void onEquallyClick(View view) {
        String editText = editTextOperand.getText().toString();
        if (operand != null && !editText.isEmpty() && !isOperandEmpty) {
            calculate();
            textViewHistory.append(formatDouble(lastOperand));
            textViewHistory.append(" " + EQUALLY + " ");
            textViewHistory.append(formatDouble(operand));
            textViewHistory.append("\n");
            lastOperation = EQUALLY;
            isOperandEmpty = true;
            operand = lastOperand;
            operand = null;
        }
    }

    private void performOperation(String number, String operation) {

        if (lastOperation.equals(EQUALLY)) {
            operand = Double.valueOf(number);
            textViewHistory.append(number + " " + operation + " ");
            lastOperation = operation;
        } else if (!lastOperation.equals(EQUALLY) && isOperandEmpty) {
            history = textViewHistory.getText().toString();
            history = history.substring(0, history.length() - 3);
            history = history + " " + operation + " ";
            textViewHistory.setText(history);
            lastOperation = operation;
        } else if (operand != null && !lastOperation.equals(EQUALLY) && !isOperandEmpty) {
            textViewHistory.append(number);
            textViewHistory.append(" " + operation + " ");
            calculate();
            lastOperation = operation;
        }

    }


    private void calculate() {
        lastOperand = Double.valueOf(editTextOperand.getText().toString());
        switch (lastOperation) {
            case DIVISION:
                if (lastOperand == 0) {
                    operand = 0.0;
                } else {
                    operand /= lastOperand;
                }
                break;
            case MULTIPLICATION:
                operand *= lastOperand;
                break;
            case ADDITION:
                operand += lastOperand;
                break;
            case SUBTRACTION:
                operand -= lastOperand;
                break;
        }
        editTextOperand.setText(formatDouble(operand));
    }



    @OnClick(R.id.btnAC)
    public void onAllClearClick() {
        operand = null;
        lastOperand = null;
        editTextOperand.setText("");
        textViewHistory.setText("");
        lastOperation = EQUALLY;
        isOperandEmpty = true;
        history = "";
    }

    @OnClick(R.id.btnBackspace)
    public void onBackspaceClick() {
        etView = editTextOperand.getText().toString();
        if (etView.length() > 0) {
            editTextOperand.setText(etView.substring(0, etView.length() - 1));
        }
    }

    @OnClick(R.id.btnDot)
    public void onDotClick() {
        etView = editTextOperand.getText().toString();
        if (etView.isEmpty()) {
            editTextOperand.append("0" + DOT);
            isOperandEmpty = false;
        } else if (!etView.contains(DOT)) {
            editTextOperand.append(DOT);
        }
    }

    @OnClick(R.id.btnChangePositiveNegative)
    public void onChangePositiveNegativeClick() {
        etView = editTextOperand.getText().toString();
        if (!etView.contains(SUBTRACTION)) {
            editTextOperand.setText(SUBTRACTION + etView);
        } else {
            editTextOperand.setText(etView.replace(SUBTRACTION, ""));
        }
    }

    public static String formatDouble(double d) {
        if (d == (long) d)
            return String.format("%d", (long) d);
        else
            return String.format("%s", d);
    }
}
