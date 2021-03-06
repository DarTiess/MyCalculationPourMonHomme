package com.example.mycalc;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CalculatActivity extends AppCompatActivity {

    EditText msName;
    EditText moneyBox;
    TextView endmoneyBox;
    Button delBut;
    Button saveBut;
 EditText minus_money;
 int minusOfMoney;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor calcCursor;
    long Id=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculat);

        msName=(EditText)findViewById(R.id.msName);
        moneyBox=(EditText)findViewById(R.id.money);
        endmoneyBox=(TextView)findViewById(R.id.endmoney);
        delBut=(Button)findViewById(R.id.deleteButton);
        saveBut=(Button)findViewById(R.id.saveButton);
       minus_money=(EditText)findViewById(R.id.minus);

        sqlHelper=new DatabaseHelper(this);
        db=sqlHelper.getWritableDatabase();

        Bundle extras=getIntent().getExtras();
        if(extras!=null){
            Id=extras.getLong("id");
        }
        if(Id>0){
            calcCursor=db.rawQuery("select * from "+DatabaseHelper.TABLE+" where "+
                    DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(Id)});
            calcCursor.moveToFirst();
            msName.setText(calcCursor.getString(1));
            moneyBox.setText(calcCursor.getString(2));
            endmoneyBox.setText(String.valueOf(calcCursor.getInt(3)));
            calcCursor.close();
        } else {

            delBut.setVisibility(View.GONE);
        }



    }

    public void save(View view) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_MSNAME, msName.getText().toString());
        cv.put(DatabaseHelper.COLUMN_MONEY, moneyBox.getText().toString());
        if (TextUtils.isEmpty(minus_money.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Введите денежную сумму НЗ ", Toast.LENGTH_SHORT).show();
            minusOfMoney = Integer.parseInt(endmoneyBox.getText().toString());
        } else {
            minusOfMoney = Integer.parseInt(endmoneyBox.getText().toString()) - Integer.parseInt(minus_money.getText().toString());
        }
            cv.put(DatabaseHelper.COLUMN_ENDMONEY, String.valueOf(minusOfMoney));

            if (Id > 0) {
                db.update(DatabaseHelper.TABLE, cv, DatabaseHelper.COLUMN_ID + "=" + String.valueOf(Id), null);
            } else {
                db.insert(DatabaseHelper.TABLE, null, cv);
            }
            goHome();

    }
    public void delete(View view){
        db.delete(DatabaseHelper.TABLE, "_id = ?", new String[]{String.valueOf(Id)});
        Intent intent = new Intent(this, ListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    private void goHome(){

        db.close();

        Intent intent = new Intent(this, StartupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
