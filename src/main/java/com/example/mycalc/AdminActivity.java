package com.example.mycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.List;

public class AdminActivity extends AppCompatActivity {

    ListView _id;
    ListView _month;
    ListView _summe;
    ListView _total;

    DataBaseDeposit dbDeposit;
    SQLiteDatabase db;
    Cursor depositCursor;
    Cursor depositCursor2;
    SimpleCursorAdapter monthAdapter;
    SimpleCursorAdapter idAdapter;
    SimpleCursorAdapter summeAdapter;
    SimpleCursorAdapter totalAdapter;

    TextView totalDeposit;
    protected int depositTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

         _month=(ListView) findViewById(R.id._monthe);
         _id=(ListView)findViewById(R.id._id);
         _summe=(ListView)findViewById(R.id._summe);
         _total=(ListView)findViewById(R.id._total);



        dbDeposit=new DataBaseDeposit(getApplicationContext());
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

    public void onResume() {
        super.onResume();
        db = dbDeposit.getReadableDatabase();

        totalDeposit=(TextView)findViewById(R.id.Total_InDeposit);

        depositCursor2=db.rawQuery("select  * from "
                + DataBaseDeposit.TABLE+" ORDER BY _ID DESC LIMIT 1", null);
        depositCursor2.moveToFirst();

        depositTotal = Integer.parseInt(depositCursor2.getString(3));

        totalDeposit.setText(" Итого на счету : "+(String.valueOf(depositTotal)));




        depositCursor =  db.rawQuery("select * from "+ DataBaseDeposit.TABLE, null);

        String[] months = new String[] {DataBaseDeposit.COLUMN_MONTH};
        String[] ids = new String[] {DataBaseDeposit.COLUMN_ID};
        String[] summes = new String[] {DataBaseDeposit.COLUMN_ADDMONTHLY};
        String[] totals = new String[] {DataBaseDeposit.COLUMN_TOTALDEPOSIT};

        monthAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_dropdown_item_1line,
                depositCursor, months, new int[]{android.R.id.text1}, 0);

        _month.setAdapter(monthAdapter);

        idAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_dropdown_item_1line,
                depositCursor, ids, new int[]{android.R.id.text1}, 0);

        _id.setAdapter(idAdapter);

        summeAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_dropdown_item_1line,
                depositCursor, summes, new int[]{android.R.id.text1}, 0);

        _summe.setAdapter(summeAdapter);

        totalAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_dropdown_item_1line,
                depositCursor, totals, new int[]{android.R.id.text1}, 0);

        _total.setAdapter(totalAdapter);
    }
    public void add(View view){
        Intent intent = new Intent(this, DepositActivity.class);
        startActivity(intent);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();

        db.close();
        depositCursor.close();
    }
}