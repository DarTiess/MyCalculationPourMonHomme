package com.example.mycalc;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    EditText forHolliday;
    EditText forCar;
    EditText forAppartement;

    TextView isforHolliday;
    TextView isforCar;
    TextView isforAppartement;

    ListView destList;

    DataBaseDestination dbDestination;
    SQLiteDatabase db;
    Cursor destCursor;
    Cursor destCursor1;
    SimpleCursorAdapter destAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        dbDestination=new DataBaseDestination(this);
        db=dbDestination.getWritableDatabase();
        dbDestination=new DataBaseDestination(getApplicationContext());

        forHolliday = (EditText) findViewById(R.id.forHoliday);
        forAppartement = (EditText) findViewById(R.id.forAppartement);
        forCar = (EditText) findViewById(R.id.forCar);

        isforAppartement = (TextView) findViewById(R.id.isForAppartement);
        isforCar = (TextView) findViewById(R.id.isForCar);
        isforHolliday = (TextView) findViewById(R.id.isForHoliday);

        destList=(ListView)findViewById(R.id.destlist);
        destList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long idd) {
                db.delete(DataBaseDestination.TABLE, "_id = ?", new String[]{String.valueOf(idd)});
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.hystory_calculation:
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

        db = dbDestination.getReadableDatabase();

      destCursor=db.rawQuery("select * from "+DataBaseDestination.TABLE+" where "+
              DataBaseDestination.COLUMN_DEFINITION + "=?", new String[]{"Отпуск"});
      destCursor.moveToFirst();
      isforHolliday.setText(destCursor.getString(2));

      destCursor=db.rawQuery("select * from "+DataBaseDestination.TABLE+" where "+
              DataBaseDestination.COLUMN_DEFINITION + "=?", new String[]{"Машина"});
      destCursor.moveToFirst();
      isforCar.setText(destCursor.getString(2));

      destCursor=db.rawQuery("select * from "+DataBaseDestination.TABLE+" where "+
              DataBaseDestination.COLUMN_DEFINITION + "=?", new String[]{"Квартира"});
      destCursor.moveToFirst();
      isforAppartement.setText(destCursor.getString(2));


      destCursor1 =  db.rawQuery("select * from "+ DataBaseDestination.TABLE, null);

      String[] headers = new String[] {DataBaseDestination.COLUMN_DEFINITION,DataBaseDestination.COLUMN_VALUE};

      destAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,
              destCursor1, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);

    destList.setAdapter(destAdapter);



    }
    long Id=0;

    public void onSaveDetination(View view) {
        if (TextUtils.isEmpty(forHolliday.getText().toString()) || TextUtils.isEmpty(forAppartement.getText().toString()) || TextUtils.isEmpty(forCar.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Заполните все строки ", Toast.LENGTH_SHORT).show();

        } else {
            ContentValues cv = new ContentValues();
            ContentValues cv1 = new ContentValues();
            ContentValues cv2 = new ContentValues();
            cv.put(DataBaseDestination.COLUMN_VALUE, forHolliday.getText().toString());
            cv1.put(DataBaseDestination.COLUMN_VALUE, forAppartement.getText().toString());
            cv2.put(DataBaseDestination.COLUMN_VALUE, forCar.getText().toString());


            db.update(DataBaseDestination.TABLE, cv, DataBaseDestination.COLUMN_DEFINITION + "=?", new String[]{"Отпуск"});
            db.update(DataBaseDestination.TABLE, cv1, DataBaseDestination.COLUMN_DEFINITION + "=?", new String[]{"Квартира"});
            db.update(DataBaseDestination.TABLE, cv2, DataBaseDestination.COLUMN_DEFINITION + "=?", new String[]{"Машина"});

            Toast.makeText(this, "Настройки сохранены", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, StartupActivity.class);
            startActivity(intent);
        }
    }
    public void delete(View view){
        db.delete(DataBaseDeposit.TABLE, "_id = ?", new String[]{String.valueOf(Id)});
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        db.close();
        destCursor.close();
    }
}