package com.example.mycalc;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DepositListActivity extends AppCompatActivity {

    ListView depositList;
    DataBaseDeposit dbDeposit;
    SQLiteDatabase db;
    Cursor depositCursor;
    Cursor depositCursor2;
    SimpleCursorAdapter depositAdapter;

    TextView totalDeposit;
    protected int depositTotal;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_list);




        depositList=(ListView)findViewById(R.id.listOfDeposit);
        depositList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DepositActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
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

        String[] headers = new String[] {DataBaseDeposit.COLUMN_MONTH,DataBaseDeposit.COLUMN_ADDMONTHLY, DataBaseDeposit.COLUMN_TOTALDEPOSIT};

        depositAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_dropdown_item_1line,
                depositCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);

        depositList.setAdapter(depositAdapter);
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
