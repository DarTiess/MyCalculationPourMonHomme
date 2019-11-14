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
    EditText nz;
    TextView momme;
    protected int summResult;
    protected int foodResult;
    protected int weekResult;
    protected int depositResult;
    protected int moneyResult;
    protected int nzResult;
    protected int momResult;

  //  ListView calculList;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor calcCursor;
    SimpleCursorAdapter calcAdapter;

    DataBaseWeek dbWeekHelper;
    SQLiteDatabase dbweek;
    Cursor weekCursor;
    SimpleCursorAdapter weekAdapter;

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
nz=(EditText)findViewById(R.id.nz);
momme=(TextView)findViewById(R.id.momme);
//calculList=(ListView)findViewById(R.id.list);

dbHelper=new DatabaseHelper(getApplicationContext());
        dbWeekHelper=new DataBaseWeek(getApplicationContext());
        selection = (TextView) findViewById(R.id.selection);

        arguments = getIntent().getExtras();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, msNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         msName.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

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
            public void afterTextChanged(Editable s) {

            }
        });



      food.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                foodResult=Integer.parseInt(food.getText().toString());

 /*TextView textView=(TextView)findViewById(R.id.end);
 summResult=summResult-foodResult;
 textView.setText(String.valueOf(summResult));*/
            }


            public void afterTextChanged(Editable s) {

            }
        });

    week.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                weekResult=Integer.parseInt(week.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        momme.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                momResult=Integer.parseInt(momme.getText().toString());
            /*    TextView textView=(TextView)findViewById(R.id.end);
              summResult=summResult-weekResult;
                textView.setText(String.valueOf(summResult));*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        deposit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

                depositResult=Integer.parseInt(deposit.getText().toString());
           /*     TextView textView=(TextView)findViewById(R.id.end);
                summResult=summResult-depositResult;
                textView.setText(String.valueOf(summResult));*/


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        nz.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

               nzResult=Integer.parseInt(nz.getText().toString());
           /*     TextView textView=(TextView)findViewById(R.id.end);
                summResult=summResult-depositResult;
                textView.setText(String.valueOf(summResult));*/


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

        }
        return super.onOptionsItemSelected(item);
    }
    public void onClickGetResult(View view) {

       int summResult2=summResult-(foodResult+weekResult+depositResult+nzResult+momResult);
        end.setText(String.valueOf(summResult2));

        TextView foodPourCent=(TextView)findViewById(R.id.foodPourCent);
        foodPourCent.setText(String.valueOf((100*foodResult)/summResult) +"%");

        TextView weekPourCent=(TextView)findViewById(R.id.weekPourCent);
        weekPourCent.setText(String.valueOf((100*weekResult)/summResult) +"%");

        TextView momPourCent=(TextView)findViewById(R.id.momPourCent);
        momPourCent.setText(String.valueOf((100*momResult)/summResult) +"%");

        TextView depositPourCent=(TextView)findViewById(R.id.depositPourCent);
       depositPourCent.setText(String.valueOf((100*depositResult)/summResult) +"%");

        TextView nzPourCent=(TextView)findViewById(R.id.nzPourCent);
       nzPourCent.setText(String.valueOf((100*nzResult)/summResult) +"%");

        TextView endPourCent=(TextView)findViewById(R.id.resultPourCent);
        endPourCent.setText(String.valueOf((100*summResult2)/summResult) +"%");
    }
    private final static String FILE_NAME = "calcMine.txt";
  /*  public void saveText(View view) {

        FileOutputStream fos = null;
        try {


            String text1 ="Приход: "+ money.getText().toString() +"\nИтог: "+end.getText().toString()+"\n";


            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text1.getBytes());

            Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
        }
        catch(IOException ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally{
            try{
                if(fos!=null)
                    fos.close();
            }
            catch(IOException ex){

                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void openText(View view) {

        FileInputStream fin = null;

        try {
            fin = openFileInput(FILE_NAME);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            end.setText(text);
        }
        catch(IOException ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{

            try{
                if(fin!=null)
                    fin.close();
            }
            catch(IOException ex){

                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
*/

/*
    public void savedb(View view) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("appCalc.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS calcul (money INTEGER, endmoney INTEGER)");

       int moneydb=Integer.parseInt(money.getText().toString());
        db.execSQL("INSERT INTO calcul VALUES ( 20   ,20);");
    }

    public void opendb(View view) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("appCalc.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT * FROM calcul;", null);
        TextView textView = (TextView) findViewById(R.id.end);
        if(query.moveToFirst()){
            do{

                int money = query.getInt(0);
                int endmoney = query.getInt(1);
                textView.append(" money: " + money + "endmoney"+endmoney+"\n");
            }
            while(query.moveToNext());
        }
        query.close();
        db.close();
    }*/
    public void onResume() {
        super.onResume();

        db = dbHelper.getReadableDatabase();

        calcCursor =  db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);

        String[] headers = new String[] {DatabaseHelper.COLUMN_MSNAME, DatabaseHelper.COLUMN_MONEY, DatabaseHelper.COLUMN_ENDMONEY};

        calcAdapter = new SimpleCursorAdapter(this, android.R.layout.test_list_item,
                calcCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
       // header.setText("Найдено элементов: " + String.valueOf(userCursor.getCount()));
      //  calculList.setAdapter(calcAdapter);
    }
    long Id=0;
    public void add(View view){
      //  Intent intent = new Intent(this, CalculatActivity.class);
       // startActivity(intent);

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_MSNAME,  selection.getText().toString());
        cv.put(DatabaseHelper.COLUMN_MONEY, money.getText().toString());
        cv.put(DatabaseHelper.COLUMN_ENDMONEY, Integer.parseInt(end.getText().toString()));

        if (Id > 0) {
            db.update(DatabaseHelper.TABLE, cv, DatabaseHelper.COLUMN_ID + "=" + String.valueOf(Id), null);
        } else {
            db.insert(DatabaseHelper.TABLE, null, cv);
        }
        Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();

        db.close();
        calcCursor.close();
    }
}
