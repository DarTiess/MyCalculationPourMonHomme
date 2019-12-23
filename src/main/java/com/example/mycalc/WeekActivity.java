package com.example.mycalc;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

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

    DataBaseWeek dbHelper;
    SQLiteDatabase db;
    Cursor weekCursor;
    SimpleCursorAdapter weekAdapter;

    ListView calculList;
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
        dbHelper=new DataBaseWeek(this);
        db=dbHelper.getWritableDatabase();


        calculList=(ListView)findViewById(R.id.listweeks);
        calculList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CalculatActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        dbHelper=new DataBaseWeek(getApplicationContext());
        road.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(road.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Введите денежную сумму выделенную на дорожные расходы ", Toast.LENGTH_SHORT).show();

                } else {
                    roadIntSum = Integer.parseInt(s.toString()) * 5;
                    roadSum.setText(String.valueOf(roadIntSum));
                }
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
                if(TextUtils.isEmpty(lunch.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Введите денежную сумму на обед ", Toast.LENGTH_SHORT).show();

                } else {
                    lunchIntSum = Integer.parseInt(s.toString()) * 5;
                    lunchSum.setText(String.valueOf(lunchIntSum));
                }
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
                if(TextUtils.isEmpty(cigarret.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Введите денежную сумму растрат на сигареты ", Toast.LENGTH_SHORT).show();

                } else {
                    cigarreIntSum = Integer.parseInt(s.toString()) * 5;
                    cigareSum.setText(String.valueOf(cigarreIntSum));
                }
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


    public void onResume() {
        super.onResume();

        db = dbHelper.getReadableDatabase();

       weekCursor =  db.rawQuery("select * from "+ DataBaseWeek.TABLE, null);

        String[] headers = new String[] {DataBaseWeek.COLUMN_ROAD, DataBaseWeek.COLUMN_LUNCH, DataBaseWeek.COLUMN_CIGARRE,DataBaseWeek.COLUMN_RESULT_FOR_WEEK};

        weekAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                weekCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        calculList.setAdapter(weekAdapter);
    }
    long Id=0;
    public void add(View view){


        resultIntWeek=cigarreIntSum+roadIntSum+lunchIntSum;
        resultWeek.setText(String.valueOf(resultIntWeek));


        ContentValues cv1 = new ContentValues();
        cv1.put(DataBaseWeek.COLUMN_ROAD,  road.getText().toString());
        cv1.put(DataBaseWeek.COLUMN_LUNCH, lunch.getText().toString());
        cv1.put(DataBaseWeek.COLUMN_CIGARRE, cigarret.getText().toString());
        cv1.put(DataBaseWeek.COLUMN_RESULT_FOR_WEEK, resultWeek.getText().toString());


        if (Id > 0) {
            db.update(DataBaseWeek.TABLE, cv1, DataBaseWeek.COLUMN_ID + "=" + String.valueOf(Id), null);
        } else {
            db.insert(DataBaseWeek.TABLE, null, cv1);
        }
        Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("price", resultIntWeek);
        startActivity(intent);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();

        db.close();
        weekCursor.close();
    }
}
