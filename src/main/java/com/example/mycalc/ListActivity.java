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

import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {
    ListView calculList;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor calcCursor;
    SimpleCursorAdapter calcAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        calculList=(ListView)findViewById(R.id.listhystory);
        calculList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CalculatActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        dbHelper=new DatabaseHelper(getApplicationContext());


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
    public void onResume() {
        super.onResume();

        db = dbHelper.getReadableDatabase();

        calcCursor =  db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);

        String[] headers = new String[] {DatabaseHelper.COLUMN_MSNAME,DatabaseHelper.COLUMN_MONEY, DatabaseHelper.COLUMN_ENDMONEY};

        calcAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_dropdown_item_1line,
                calcCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        // header.setText("Найдено элементов: " + String.valueOf(userCursor.getCount()));
        calculList.setAdapter(calcAdapter);
    }
    public void add(View view){
        Intent intent = new Intent(this, CalculatActivity.class);
        startActivity(intent);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();

        db.close();
        calcCursor.close();
    }
}
