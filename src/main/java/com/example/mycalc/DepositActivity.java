package com.example.mycalc;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DepositActivity extends AppCompatActivity {

    EditText msName;
    EditText moneyBox;
    TextView endmoneyBox;


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


    private void goHome(){

        db.close();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}