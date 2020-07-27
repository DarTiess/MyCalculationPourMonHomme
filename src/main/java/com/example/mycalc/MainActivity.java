package com.example.mycalc;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Integer.*;

public class MainActivity extends AppCompatActivity {



    Spinner msName;
    TextView selection;
    TextView end;
    EditText money;
    EditText monse;
    TextView food;
    TextView week;
    EditText deposit;
    EditText deposit2;
    EditText nz;
    TextView momme;

    protected int summResult;
    protected int foodResult;
    protected int weekResult;
    protected int depositResult;
    protected int depositResult2;
    protected int nzResult;
    protected int momResult;
    protected int depositTotal;
    protected int nzTotal;

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor calcCursor;
    SimpleCursorAdapter calcAdapter;

    DataBaseWeek dbWeekHelper;
    SQLiteDatabase dbweek;
    Cursor weekCursor;

    DataBaseDeposit dbDepositHelper;
    SQLiteDatabase dbdeposit;
    Cursor depositCursor;

    DataBaseNZ dbNzHelper;
    SQLiteDatabase dbNZ;
    Cursor nzCursor;

    Bundle arguments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         msName=findViewById(R.id.monseName);
        end=findViewById(R.id.end);
        money=findViewById(R.id.moneyStart);
        monse=findViewById(R.id.monse);
        food=findViewById(R.id.food);
        week=findViewById(R.id.week);
        deposit=findViewById(R.id.deposit);
        deposit2=findViewById(R.id.deposit2);
        nz=findViewById(R.id.nz);
        momme=findViewById(R.id.momme);


        dbHelper=new DatabaseHelper(getApplicationContext());
        dbWeekHelper=new DataBaseWeek(getApplicationContext());
        dbDepositHelper=new DataBaseDeposit(getApplicationContext());
        dbNzHelper=new DataBaseNZ(getApplicationContext());


        arguments = getIntent().getExtras();
        selection = findViewById(R.id.selection);

        adapterListener(msName, selection);

        dbweek = dbWeekHelper.getReadableDatabase();
        weekCursor=dbweek.rawQuery("select  * from "
                + DataBaseWeek.TABLE+" ORDER BY _ID DESC LIMIT 1", null);
        weekCursor.moveToFirst();
        weekResult= parseInt(weekCursor.getString(4));
        week.setText(String.valueOf(weekResult));


        money.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                TextView textView = findViewById(R.id.end);
                if(TextUtils.isEmpty(money.getText().toString())){
                     Toast.makeText(getApplicationContext(), "Введите денежную сумму ", Toast.LENGTH_SHORT).show();

                 } else{
                    summResult= parseInt( s.toString());
                    textView.setText(String.valueOf(summResult));
                 }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
    });

        monse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(monse.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Введите колличество недель в текущем месяце ", Toast.LENGTH_SHORT).show();

                } else {
                    foodResult = parseInt(s.toString()) * 15000;
                    TextView textView =  findViewById(R.id.food);
                    textView.setText(String.valueOf(foodResult));

                    if (arguments != null) {

                        weekResult = arguments.getInt("price");

                    } else {

                        dbweek = dbWeekHelper.getReadableDatabase();
                        weekCursor = dbweek.rawQuery("select  * from "
                                + DataBaseWeek.TABLE + " ORDER BY _ID DESC LIMIT 1", null);
                        weekCursor.moveToFirst();
                        weekResult = parseInt(weekCursor.getString(4));
                    }


                    weekResult = parseInt(s.toString()) * weekResult;
                    week.setText(String.valueOf(weekResult));

                    momResult = parseInt(s.toString()) * 10000;
                    TextView textView3 =  findViewById(R.id.momme);
                    textView3.setText(String.valueOf(momResult));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

     textChangeListener(food);
     textChangeListener(week);
     textChangeListener(momme);

        deposit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                depositResult=0;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(TextUtils.isEmpty(deposit.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Введите денежную сумму перечисленную на депозит 1", Toast.LENGTH_SHORT).show();


                } else {
                    depositResult = parseInt(deposit.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        deposit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                depositResult2 = 0;
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(deposit2.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Введите денежную сумму перечисленную на депозит 2", Toast.LENGTH_SHORT).show();
                    depositResult2 = 0;
                } else {
                    depositResult2 = parseInt(deposit2.getText().toString());
                }
                    depositCursor = dbdeposit.rawQuery("select  * from "
                            + DataBaseDeposit.TABLE + " ORDER BY _ID DESC LIMIT 1", null);
                    depositCursor.moveToFirst();

                    depositTotal = parseInt(depositCursor.getString(3)) + depositResult2;

                }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        nzResult=0;
        nz.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                nzResult=0;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(TextUtils.isEmpty(nz.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Введите денежную сумму НЗ ", Toast.LENGTH_SHORT).show();

                } else {
                    nzResult = parseInt(nz.getText().toString());
                }
                   nzCursor = dbNZ.rawQuery("select  * from "
                            + DataBaseNZ.TABLE + " ORDER BY _ID DESC LIMIT 1", null);
                    nzCursor.moveToFirst();

                    nzTotal = parseInt(nzCursor.getString(3)) + nzResult;
                }




            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    public void textChangeListener(final TextView textView){
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                parseInt(textView.getText().toString());
            }
            public void afterTextChanged(Editable s) {}
        });

    }

    public void adapterListener(Spinner spinner, final TextView textView) {

        String[] monse = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, monse);
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                textView.setText(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

       spinner.setOnItemSelectedListener(itemSelectedListener);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        TextView headerView =findViewById(R.id.header);
        switch(id){
            case R.id.hystory_calculation :
                headerView.setText("История ");
                Intent intent = new Intent(this, ListActivity.class);
                startActivity(intent);
                return true;
            case R.id.add_calculation:
                headerView.setText("Недельные");
                Intent intent1 = new Intent(this, WeekActivity.class);
                startActivity(intent1);
                return true;
            case R.id.add_foodCalculation:
                headerView.setText("Еда");
                Intent intent2 = new Intent(this, WeekActivity.class);
                startActivity(intent2);
                return true;
            case R.id.deposit_List:
                headerView.setText("Депозит");
                Intent intent3 = new Intent(this, DepositListActivity.class);
                startActivity(intent3);
                return true;
            case R.id.nz_List:
                headerView.setText("НЗ");
                Intent intent4 = new Intent(this, NzListActivity.class);
                startActivity(intent4);
                return true;
            case R.id.settings:
                Intent intent5 = new Intent(this, SettingsActivity.class);
                startActivity(intent5);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onClickGetResult(View view) {

       int summResult2=summResult-(foodResult+weekResult+depositResult+depositResult2+nzResult+momResult);
        end.setText(String.valueOf(summResult2));

        TextView foodPourCent=findViewById(R.id.foodPourCent);
        foodPourCent.setText(calculatePourcent(foodResult,summResult));

        TextView weekPourCent=findViewById(R.id.weekPourCent);
        weekPourCent.setText(calculatePourcent(weekResult,summResult));

        TextView momPourCent=findViewById(R.id.momPourCent);
        momPourCent.setText(calculatePourcent(momResult,summResult));

        TextView depositPourCent=findViewById(R.id.depositPourCent);
       depositPourCent.setText(calculatePourcent(depositResult,summResult));

        TextView depositPourCent2=findViewById(R.id.deposit2PourCent);
        depositPourCent2.setText(calculatePourcent(depositResult2,summResult));

        TextView nzPourCent=findViewById(R.id.nzPourCent);
       nzPourCent.setText(calculatePourcent(nzResult,summResult));

        TextView endPourCent=findViewById(R.id.resultPourCent);
        endPourCent.setText(calculatePourcent(summResult2,summResult));
    }

    public String calculatePourcent(int firstvalue, int totalSumm){

        return (100 * firstvalue) / totalSumm +"%";
    }
    public void onResume() {
        super.onResume();

        db = dbHelper.getReadableDatabase();
        dbdeposit = dbDepositHelper.getReadableDatabase();
        dbNZ = dbNzHelper.getReadableDatabase();
        calcCursor =  db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);

        String[] headers = new String[] {DatabaseHelper.COLUMN_MSNAME, DatabaseHelper.COLUMN_MONEY, DatabaseHelper.COLUMN_ENDMONEY};

        calcAdapter = new SimpleCursorAdapter(this, android.R.layout.test_list_item,
                calcCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);

    }

    long Id=0;
    long Id_=0;
    public void add(View view){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_MSNAME,  selection.getText().toString());
        cv.put(DatabaseHelper.COLUMN_MONEY, money.getText().toString());
        cv.put(DatabaseHelper.COLUMN_ENDMONEY, parseInt(end.getText().toString()));
        if (Id > 0) {
            db.update(DatabaseHelper.TABLE, cv, DatabaseHelper.COLUMN_ID + "=" + (Id), null);
        } else {
            db.insert(DatabaseHelper.TABLE, null, cv);
        }

        ContentValues cv_deposit = new ContentValues();
       dbdeposit = dbDepositHelper.getWritableDatabase();
        cv_deposit.put(DataBaseDeposit.COLUMN_MONTH, selection.getText().toString());
        cv_deposit.put(DataBaseDeposit.COLUMN_ADDMONTHLY, deposit2.getText().toString());
        cv_deposit.put(DataBaseDeposit.COLUMN_TOTALDEPOSIT,String.valueOf(depositTotal));

        if (Id_> 0) {
          dbdeposit.update(DataBaseDeposit.TABLE, cv_deposit, DataBaseDeposit.COLUMN_ID+"="+ (Id_), null);
        } else {
           dbdeposit.insert(DataBaseDeposit.TABLE, null, cv_deposit);
        }

        ContentValues cv_nz = new ContentValues();
        dbNZ = dbNzHelper.getWritableDatabase();
        cv_nz.put(DataBaseNZ.COLUMN_MONTH, selection.getText().toString());
        cv_nz.put(DataBaseNZ.COLUMN_ADDNZMONTHLY, nz.getText().toString());
        cv_nz.put(DataBaseNZ.COLUMN_TOTALNZ,String.valueOf(nzTotal));

        if (Id_> 0) {
            dbNZ.update(DataBaseNZ.TABLE, cv_nz, DataBaseNZ.COLUMN_ID+"="+ (Id_), null);
        } else {
            dbNZ.insert(DataBaseNZ.TABLE, null, cv_nz);
        }

        Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
        Intent intent2 = new Intent(this, StartupActivity.class);
        startActivity(intent2);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        dbdeposit.close();
        db.close();
        dbNZ.close();
      //  calcCursor.close();
        //depositCursor.close();
      //  nzCursor.close();
    }
}
