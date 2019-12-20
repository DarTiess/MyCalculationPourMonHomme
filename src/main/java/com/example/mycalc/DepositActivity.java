package com.example.mycalc;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DepositActivity extends AppCompatActivity {

    EditText msName;
    EditText moneyBox;
    TextView endmoneyBox;
    Button delBut;
    Button saveBut;


    DataBaseDeposit sqlDepositHelper;
    SQLiteDatabase db;
    Cursor depositCursor;
    long Id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        msName=(EditText)findViewById(R.id.msName);
        moneyBox=(EditText)findViewById(R.id.money);
        endmoneyBox=(TextView)findViewById(R.id.endmoney);
        delBut=(Button)findViewById(R.id.deleteButton);
        saveBut=(Button)findViewById(R.id.saveButton);

        sqlDepositHelper=new DataBaseDeposit(this);
        db=sqlDepositHelper.getWritableDatabase();

        Bundle extras=getIntent().getExtras();
        if(extras!=null){
            Id=extras.getLong("id");
        }
        if(Id>0){
            depositCursor=db.rawQuery("select * from "+DataBaseDeposit.TABLE+" where "+
                    DataBaseDeposit.COLUMN_ID + "=?", new String[]{String.valueOf(Id)});
            depositCursor.moveToFirst();
            msName.setText(depositCursor.getString(1));
            moneyBox.setText(depositCursor.getString(2));
            endmoneyBox.setText(String.valueOf(depositCursor.getInt(3)));
            depositCursor.close();
        } else {
            goHome();
        }




    }
    public void save(View view){
        ContentValues cv = new ContentValues();
        cv.put(DataBaseDeposit.COLUMN_MONTH, msName.getText().toString());
        cv.put(DataBaseDeposit.COLUMN_ADDMONTHLY, moneyBox.getText().toString());
        cv.put(DataBaseDeposit.COLUMN_TOTALDEPOSIT, Integer.parseInt(endmoneyBox.getText().toString()));

        if (Id > 0) {
            db.update(DatabaseHelper.TABLE, cv, DatabaseHelper.COLUMN_ID + "=" + String.valueOf(Id), null);
        } else {
            db.insert(DatabaseHelper.TABLE, null, cv);
        }
        goHome();
    }
    public void delete(View view){
        db.delete(DataBaseDeposit.TABLE, "_id = ?", new String[]{String.valueOf(Id)});
        Intent intent = new Intent(this, DepositListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void goHome(){

        db.close();

        Intent intent = new Intent(this, DepositListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}