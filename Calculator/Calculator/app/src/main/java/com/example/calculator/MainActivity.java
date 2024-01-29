package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;


import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resultTv , solutionTv;

    Button buttonC , buttonBrackOpen , buttonBrackClose;
    Button buttonDivide , buttonMultiply , buttonPlus , buttonMinus , buttonEquals;
    Button button0 , button1 , button2 , button3 , button4 , button5, button6 , button7 , button8 , button9;
    Button buttonAC , buttonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTv = findViewById(R.id.textView_Result);
        solutionTv = findViewById(R.id.textView_Solution);

        assignId(buttonC,R.id.buttonC);
        assignId(buttonBrackOpen,R.id.buttonOpen);
        assignId(buttonBrackClose,R.id.buttonClose);
        assignId(buttonDivide,R.id.buttonDivide);
        assignId(buttonMultiply,R.id.buttonMultiply);
        assignId(buttonPlus,R.id.buttonPlus);
        assignId(buttonMinus,R.id.buttonMinus);
        assignId(buttonEquals,R.id.buttonEqual);
        assignId(button0,R.id.button_0);
        assignId(button1,R.id.button_1);
        assignId(button2,R.id.button_2);
        assignId(button3,R.id.button_3);
        assignId(button4,R.id.button_4);
        assignId(button5,R.id.button_5);
        assignId(button6,R.id.button_6);
        assignId(button7,R.id.button_7);
        assignId(button8,R.id.button_8);
        assignId(button9,R.id.button_9);
        assignId(buttonAC,R.id.buttonAc);
    }

    void assignId(Button btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();
        String dataToCalcuate = solutionTv.getText().toString();
        String lastRes=resultTv.getText().toString();

        if (buttonText.equals("AC")) {
            solutionTv.setText("0");
            resultTv.setText(("0"));
            return;
        }

        if (buttonText.equals("=")){
            solutionTv.setText(resultTv.getText());
            return;
        }

        if (buttonText.equals("C")){
            dataToCalcuate = dataToCalcuate.substring(0,dataToCalcuate.length()-1);
            if (dataToCalcuate.length() == 0){
                dataToCalcuate = "0";
            }
            solutionTv.setText(dataToCalcuate);
            lastRes=resultTv.getText().toString();
            resultTv.setText(lastRes);
        }
        else {
            dataToCalcuate = dataToCalcuate+buttonText;
        }
        if (resultTv.getText().equals("0")) {
            dataToCalcuate = buttonText;
            solutionTv.setText(buttonText);
        }

        solutionTv.setText(dataToCalcuate);

        /*String lastChar=dataToCalcuate.substring(dataToCalcuate.length() - 1);
        if((lastChar=="+")||(lastChar=="-")||(lastChar=="/")||(lastChar=="*"))
        {
            dataToCalcuate = dataToCalcuate.substring(0,dataToCalcuate.length()-1);
            finalResult= getResult(dataToCalcuate);
            resultTv.setText(finalResult);
        }*/

        String finalResult= getResult(dataToCalcuate);

        if(!finalResult.equals("Err")){
            resultTv.setText(finalResult);
        }

    }
    String getResult(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initSafeStandardObjects();
            String finalResult = context.evaluateString(scriptable, data,"Javascript",1,null).toString();
            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        } catch (Exception e){
            return "Err";
        }
    }
}