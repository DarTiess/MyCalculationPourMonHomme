package com.example.mycalc;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WeekActivity extends AppCompatActivity {

    EditText road;
    EditText lunch;
    EditText cigarret;

    TextView roadSum;
    TextView lunchSum;
    TextView cigareSum;

    TextView resultWeek;

   int roadIntSum;
    int lunchIntSum;
    int cigarreIntSum;
    int resultIntWeek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);
        road=(EditText) findViewById(R.id.road);
        lunch=(EditText)findViewById(R.id.lunch);
        cigarret=(EditText)findViewById(R.id.cigarret);

        roadSum=(TextView)findViewById(R.id.roadSum);
        lunchSum=(TextView)findViewById(R.id.lunchSum);
        cigareSum=(TextView)findViewById(R.id.cigarreSum);
        resultWeek=(TextView)findViewById(R.id.resultOfWeek);



        road.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                roadIntSum=Integer.parseInt(s.toString())*5;
                roadSum.setText(String.valueOf(roadIntSum));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lunch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                lunchIntSum=Integer.parseInt(s.toString())*5;
                lunchSum.setText(String.valueOf(lunchIntSum));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cigarret.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                cigarreIntSum=Integer.parseInt(s.toString())*5;
                cigareSum.setText(String.valueOf(cigarreIntSum));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void getResultOfWeek(View view) {

        resultIntWeek=cigarreIntSum+roadIntSum+lunchIntSum;
        resultWeek.setText(String.valueOf(resultIntWeek));
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("price", resultIntWeek);
        startActivity(intent);
    }
}
