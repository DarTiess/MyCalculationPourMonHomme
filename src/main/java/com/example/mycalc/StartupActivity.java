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
    DatabaseHelper dbHelpre;
    SQLiteDatabase db;
    Cursor dbCursor;
    protected int totalInPocket;
    TextView deposit_balance;

    DatabaseHelper dbHelper;
    SQLiteDatabase db2;
    Cursor zpCursor;
    TextView zp_;

    DataBaseDestination dbDestination;
    SQLiteDatabase dbDest;
    Cursor destCursor;

    DataBaseDeposit dbDeposit;
    SQLiteDatabase db_depos;
    Cursor depositCursor;

    TextView datenowDays;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        deposit_balance=(TextView)findViewById(R.id.depositBalance);

        datenowDays=(TextView)findViewById(R.id.dateNowDays);

      dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("E dd.MM.yyyy', время:' hh:mm:ss");
         formatForMonth = new SimpleDateFormat("MM");
       datenowDays.setText("Сегодня " + formatForDateNow.format(dateNow));



        dbHelpre =new DatabaseHelper(getApplicationContext());
        db = dbHelpre.getReadableDatabase();

        dbDestination=new DataBaseDestination(getApplicationContext());
        dbDest=dbDestination.getReadableDatabase();

        dbDeposit=new DataBaseDeposit(getApplicationContext());
        db_depos=dbDeposit.getReadableDatabase();

        dbCursor =db.rawQuery("select  * from "
                + DatabaseHelper.TABLE+" ORDER BY _ID DESC LIMIT 1", null);
        dbCursor.moveToFirst();

        totalInPocket =Integer.parseInt(dbCursor.getString(3));

        deposit_balance.setText("Остаток на сегодняшний день : "+String.valueOf(totalInPocket) +" тг");


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
            case R.id.settings:
                Intent intent5 = new Intent(this, SettingsActivity.class);
                startActivity(intent5);
                return true;
            case R.id.adminPanel:
                Intent intent6 = new Intent(this, AdminActivity.class);
                startActivity(intent6);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    public void addNewOne(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void onClickHoliday(View view) {
        destCursor=dbDest.rawQuery("select * from "+DataBaseDestination.TABLE+" where "+
                DataBaseDestination.COLUMN_DEFINITION + "=?", new String[]{"Отпуск"});
        destCursor.moveToFirst();

       depositCursor =db_depos.rawQuery("select  * from "
                + DataBaseDeposit.TABLE+" ORDER BY _ID DESC LIMIT 1", null);
        depositCursor.moveToFirst();

        if(Integer.parseInt(depositCursor.getString(3)) <Integer.parseInt(destCursor.getString(2))){
            Toast.makeText(this, "Для отпуска не хватает средст на счету", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, "Поздравляю, вы заслужили оптуск", Toast.LENGTH_SHORT).show();
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

        dbCursor =db.rawQuery("select  * from "
                + DatabaseHelper.TABLE+" ORDER BY _ID DESC LIMIT 1", null);
        dbCursor.moveToFirst();

        totalInPocket =Integer.parseInt(dbCursor.getString(3));

        deposit_balance.setText("Остаток на сегодняшний день : "+String.valueOf(totalInPocket) +" тг");

    }

    public void onClickBuyCar(View view) {
        destCursor=dbDest.rawQuery("select * from "+DataBaseDestination.TABLE+" where "+
                DataBaseDestination.COLUMN_DEFINITION + "=?", new String[]{"Машина"});
        destCursor.moveToFirst();

        depositCursor =db_depos.rawQuery("select  * from "
                + DataBaseDeposit.TABLE+" ORDER BY _ID DESC LIMIT 1", null);
        depositCursor.moveToFirst();

        if(Integer.parseInt(depositCursor.getString(3)) <Integer.parseInt(destCursor.getString(2))){
            Toast.makeText(this, "Для покупки машины не хватает средст на счету", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, "Поздравляю, вы можете купить машину", Toast.LENGTH_SHORT).show();
            Intent intent2 = new Intent(this, DepositListActivity.class);
            startActivity(intent2);
        }
    }

    public void onClickBuyHome(View view) {
        destCursor=dbDest.rawQuery("select * from "+DataBaseDestination.TABLE+" where "+
                DataBaseDestination.COLUMN_DEFINITION + "=?", new String[]{"Квартира"});
        destCursor.moveToFirst();

        depositCursor =db_depos.rawQuery("select  * from "
                + DataBaseDeposit.TABLE+" ORDER BY _ID DESC LIMIT 1", null);
        depositCursor.moveToFirst();

        if(Integer.parseInt(depositCursor.getString(3)) <Integer.parseInt(destCursor.getString(2))){
            Toast.makeText(this, "На квартиру нужно еще копить, не хватает средств...", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, "Ура! У нас будет свой дом!", Toast.LENGTH_SHORT).show();
            Intent intent2 = new Intent(this, DepositListActivity.class);
            startActivity(intent2);
        }
    }
}