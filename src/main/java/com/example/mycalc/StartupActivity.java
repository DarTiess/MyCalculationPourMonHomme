package com.example.mycalc;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
    DataBaseDeposit dbDeposit;
    SQLiteDatabase db;
    Cursor depositCursor2;
    protected int totalInDep;
    TextView deposit_balance;

    DatabaseHelper dbHelper;
    SQLiteDatabase db2;
    Cursor zpCursor;
    protected int zpSal;
    TextView zp_;

    TextView datenowDays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        deposit_balance=(TextView)findViewById(R.id.depositBalance);
        zp_ =(TextView)findViewById(R.id.sal_zp);
        datenowDays=(TextView)findViewById(R.id.dateNowDays);

      dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("E dd.MM.yyyy', время:' hh:mm:ss");
         formatForMonth = new SimpleDateFormat("MM");
       datenowDays.setText("Сегодня " + formatForDateNow.format(dateNow));



        dbDeposit=new DataBaseDeposit(getApplicationContext());
       // dbHelper=new DatabaseHelper(getApplicationContext());

        db = dbDeposit.getReadableDatabase();
       // db2=dbHelper.getReadableDatabase();

        depositCursor2=db.rawQuery("select  * from "
                + DataBaseDeposit.TABLE+" ORDER BY _ID DESC LIMIT 1", null);
        depositCursor2.moveToFirst();

        totalInDep=Integer.parseInt(depositCursor2.getString(3));

        deposit_balance.setText("Ваш баланс на счету : "+String.valueOf(totalInDep) +" тг");


     //   dbHelper=new DatabaseHelper(getApplicationContext());
   //  db2=dbHelper.getReadableDatabase();
      //  zpCursor=db2.rawQuery("select  * from "
          //     + DatabaseHelper.TABLE+" ORDER BY _ID DESC LIMIT 1", null);
      //  zpCursor.moveToFirst();

 // zpSal =Integer.parseInt(zpCursor.getString(2));
  //  zp_.setText(zpCursor.getString(0));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        TextView headerView = (TextView) findViewById(R.id.headers);
        switch(id){
            case R.id.hystory_calculation :
                headerView.setText("История ");
                Intent intent = new Intent(this, ListActivity.class);
                startActivity(intent);
                return true;
            case R.id.add_calculation:
                headerView.setText("Добавить");
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
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
}