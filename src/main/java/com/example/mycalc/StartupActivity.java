package com.example.mycalc;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StartupActivity extends AppCompatActivity {


    Date dateNow;
    SimpleDateFormat formatForMonth;

    long id;
    DatabaseHelper dbDeposit;
    SQLiteDatabase db;
    Cursor depositCursor2;
    protected int totalInDep;
    TextView deposit_balance;

    DatabaseHelper dbHelper;
    SQLiteDatabase db2;
    Cursor zpCursor;
    TextView zp_;

    TextView datenowDays;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

       // mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
       // mSwipeRefresh.setOnRefreshListener(this);

        deposit_balance=(TextView)findViewById(R.id.depositBalance);

        datenowDays=(TextView)findViewById(R.id.dateNowDays);

      dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("E dd.MM.yyyy', время:' hh:mm:ss");
         formatForMonth = new SimpleDateFormat("MM");
       datenowDays.setText("Сегодня " + formatForDateNow.format(dateNow));



        dbDeposit=new DatabaseHelper(getApplicationContext());
        db = dbDeposit.getReadableDatabase();


        depositCursor2=db.rawQuery("select  * from "
                + DatabaseHelper.TABLE+" ORDER BY _ID DESC LIMIT 1", null);
        depositCursor2.moveToFirst();

        totalInDep=Integer.parseInt(depositCursor2.getString(3));

        deposit_balance.setText("Остаток на сегодняшний день : "+String.valueOf(totalInDep) +" тг");


    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.hystory_calculation :
                Intent intent = new Intent(this, ListActivity.class);
                startActivity(intent);
                return true;
            case R.id.add_calculation:
                Intent intent1 = new Intent(this, WeekActivity.class);
                startActivity(intent1);
                return true;
            case R.id.add_foodCalculation:
                Intent intent2 = new Intent(this, WeekActivity.class);
                startActivity(intent2);
                return true;
            case R.id.deposit_List:
                Intent intent3 = new Intent(this, DepositListActivity.class);
                startActivity(intent3);
                return true;
            case R.id.nz_List:
                Intent intent4 = new Intent(this, NzListActivity.class);
                startActivity(intent4);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    public void addNewOne(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void onClickHoliday(View view) {

        if(totalInDep<600000){
            Toast.makeText(this, "Для отпуска не хватает средст на счету", Toast.LENGTH_SHORT).show();

        }else {
            Intent intent2 = new Intent(this, DepositListActivity.class);
            startActivity(intent2);
        }

    }

    public void onClickDepositList(View view) {
        Intent intent2 = new Intent(this, DepositListActivity.class);
        startActivity(intent2);
    }

    public void addOneMore(View view) {
           dbHelper=new DatabaseHelper(getApplicationContext());
          db2=dbHelper.getReadableDatabase();
          zpCursor=db2.rawQuery("select  * from "
            + DatabaseHelper.TABLE+" ORDER BY _ID DESC LIMIT 1", null);
          zpCursor.moveToFirst();
          id=Integer.parseInt(zpCursor.getString(0));
        Intent intent = new Intent(getApplicationContext(), CalculatActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);

    }



    public void onRefresh(View view) {
        dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("E dd.MM.yyyy', время:' hh:mm:ss   ");
        formatForMonth = new SimpleDateFormat("MM");
        datenowDays.setText("Сегодня " + formatForDateNow.format(dateNow));

        depositCursor2=db.rawQuery("select  * from "
                + DatabaseHelper.TABLE+" ORDER BY _ID DESC LIMIT 1", null);
        depositCursor2.moveToFirst();

        totalInDep=Integer.parseInt(depositCursor2.getString(3));

        deposit_balance.setText("Остаток на сегодняшний день : "+String.valueOf(totalInDep) +" тг");

    }
}