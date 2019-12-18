package com.example.mycalc;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
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

public class MainActivity extends AppCompatActivity {
    String[] msNames = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

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
    protected int moneyResult;
    protected int nzResult;
    protected int momResult;
    protected int depositTotal;

  //  ListView calculList;
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

    Bundle arguments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      msName=(Spinner)findViewById(R.id.monseName);
end=(TextView)findViewById(R.id.end);
money=(EditText)findViewById(R.id.moneyStart);
monse=(EditText)findViewById(R.id.monse);
food=(TextView)findViewById(R.id.food);
 week=(TextView)findViewById(R.id.week);
deposit=(EditText)findViewById(R.id.deposit);
        deposit2=(EditText)findViewById(R.id.deposit2);
nz=(EditText)findViewById(R.id.nz);
momme=(TextView)findViewById(R.id.momme);
//calculList=(ListView)findViewById(R.id.list);

dbHelper=new DatabaseHelper(getApplicationContext());
        dbWeekHelper=new DataBaseWeek(getApplicationContext());
        dbDepositHelper=new DataBaseDeposit(getApplicationContext());
        selection = (TextView) findViewById(R.id.selection);

        arguments = getIntent().getExtras();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, msNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         msName.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)parent.getItemAtPosition(position);
                selection.setText(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        msName.setOnItemSelectedListener(itemSelectedListener);

        dbweek = dbWeekHelper.getReadableDatabase();
        weekCursor=dbweek.rawQuery("select  * from "
                + DataBaseWeek.TABLE+" ORDER BY _ID DESC LIMIT 1", null);
        weekCursor.moveToFirst();
        weekResult=Integer.parseInt(weekCursor.getString(4));
        week.setText(String.valueOf(weekResult));


        money.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                TextView textView = (TextView) findViewById(R.id.end);
              summResult=Integer.parseInt( s.toString());
                textView.setText(String.valueOf(summResult));
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

                foodResult=Integer.parseInt( s.toString())*15000;
                TextView textView = (TextView) findViewById(R.id.food);
                textView.setText(String.valueOf(foodResult));

                if(arguments!=null){

                   weekResult = arguments.getInt("price");

                }else{

                    dbweek = dbWeekHelper.getReadableDatabase();
                    weekCursor=dbweek.rawQuery("select  * from "
                                    + DataBaseWeek.TABLE+" ORDER BY _ID DESC LIMIT 1", null);
                    weekCursor.moveToFirst();
                    weekResult=Integer.parseInt(weekCursor.getString(4));
                }


               weekResult=Integer.parseInt( s.toString())*weekResult;
                week.setText(String.valueOf(weekResult));

                momResult=Integer.parseInt(s.toString())*10000;
                TextView textView3=(TextView)findViewById(R.id.momme);
                textView3.setText(String.valueOf(momResult));
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });



      food.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                foodResult=Integer.parseInt(food.getText().toString());}
            public void afterTextChanged(Editable s) {}
        });

       week.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                weekResult=Integer.parseInt(week.getText().toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

         momme.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                momResult=Integer.parseInt(momme.getText().toString());}
            @Override
            public void afterTextChanged(Editable s) {}
        });

        deposit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

                depositResult=Integer.parseInt(deposit.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        deposit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                depositResult2=Integer.parseInt(deposit2.getText().toString());

                depositCursor=dbdeposit.rawQuery("select  * from "
                        + DataBaseDeposit.TABLE+" ORDER BY _ID DESC LIMIT 1", null);
                depositCursor.moveToFirst();

                depositTotal = Integer.parseInt(depositCursor.getString(3)) + depositResult2;
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        nz.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

               nzResult=Integer.parseInt(nz.getText().toString());



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
       /* BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);*/
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        TextView headerView = (TextView) findViewById(R.id.header);
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

        }
        return super.onOptionsItemSelected(item);
    }
    public void onClickGetResult(View view) {

       int summResult2=summResult-(foodResult+weekResult+depositResult+depositResult2+nzResult+momResult);
        end.setText(String.valueOf(summResult2));

        TextView foodPourCent=(TextView)findViewById(R.id.foodPourCent);
        foodPourCent.setText(String.valueOf((100*foodResult)/summResult) +"%");

        TextView weekPourCent=(TextView)findViewById(R.id.weekPourCent);
        weekPourCent.setText(String.valueOf((100*weekResult)/summResult) +"%");

        TextView momPourCent=(TextView)findViewById(R.id.momPourCent);
        momPourCent.setText(String.valueOf((100*momResult)/summResult) +"%");

        TextView depositPourCent=(TextView)findViewById(R.id.depositPourCent);
       depositPourCent.setText(String.valueOf((100*depositResult)/summResult) +"%");

        TextView depositPourCent2=(TextView)findViewById(R.id.deposit2PourCent);
        depositPourCent2.setText(String.valueOf((100*depositResult2)/summResult) +"%");

        TextView nzPourCent=(TextView)findViewById(R.id.nzPourCent);
       nzPourCent.setText(String.valueOf((100*nzResult)/summResult) +"%");

        TextView endPourCent=(TextView)findViewById(R.id.resultPourCent);
        endPourCent.setText(String.valueOf((100*summResult2)/summResult) +"%");
    }
    private final static String FILE_NAME = "calcMine.txt";

    public void onResume() {
        super.onResume();

        db = dbHelper.getReadableDatabase();
        dbdeposit = dbDepositHelper.getReadableDatabase();
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
        cv.put(DatabaseHelper.COLUMN_ENDMONEY, Integer.parseInt(end.getText().toString()));
        if (Id > 0) {
            db.update(DatabaseHelper.TABLE, cv, DatabaseHelper.COLUMN_ID + "=" + String.valueOf(Id), null);
        } else {
            db.insert(DatabaseHelper.TABLE, null, cv);
        }
        ContentValues cv_deposit = new ContentValues();
       dbdeposit = dbDepositHelper.getWritableDatabase();
        cv_deposit.put(DataBaseDeposit.COLUMN_MONTH, selection.getText().toString());
        cv_deposit.put(DataBaseDeposit.COLUMN_ADDMONTHLY, deposit2.getText().toString());
        cv_deposit.put(DataBaseDeposit.COLUMN_TOTALDEPOSIT,String.valueOf(depositTotal));

        if (Id_> 0) {
          dbdeposit.update(DataBaseDeposit.TABLE, cv_deposit, DataBaseDeposit.COLUMN_ID+"="+ String.valueOf(Id_), null);
        } else {
           dbdeposit.insert(DataBaseDeposit.TABLE, null, cv_deposit);
        }


        Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        dbdeposit.close();
        db.close();
        calcCursor.close();
        depositCursor.close();
    }
}
