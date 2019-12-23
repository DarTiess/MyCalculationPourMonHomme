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

public class NzActivity extends AppCompatActivity {

    EditText msName;
    EditText moneyBox;
    TextView endmoneyBox;
    Button delBut;
    Button saveBut;


   DataBaseNZ sqlNzHelper;
    SQLiteDatabase db;
    Cursor nzCursor;
    long Id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nz);

        msName=(EditText)findViewById(R.id.msName);
        moneyBox=(EditText)findViewById(R.id.money);
        endmoneyBox=(TextView)findViewById(R.id.endmoney);
        delBut=(Button)findViewById(R.id.deleteButton);
        saveBut=(Button)findViewById(R.id.saveButton);

        sqlNzHelper=new DataBaseNZ(this);
        db=sqlNzHelper.getWritableDatabase();

        Bundle extras=getIntent().getExtras();
        if(extras!=null){
            Id=extras.getLong("id");
        }
        if(Id>0){
            nzCursor=db.rawQuery("select * from "+DataBaseNZ.TABLE+" where "+
                    DataBaseNZ.COLUMN_ID + "=?", new String[]{String.valueOf(Id)});
            nzCursor.moveToFirst();
            msName.setText(nzCursor.getString(1));
            moneyBox.setText(nzCursor.getString(2));
            endmoneyBox.setText(String.valueOf(nzCursor.getInt(3)));
            nzCursor.close();
        } else {
            goHome();
        }




    }
    public void save(View view){
        ContentValues cv = new ContentValues();
        cv.put(DataBaseNZ.COLUMN_MONTH, msName.getText().toString());
        cv.put(DataBaseNZ.COLUMN_ADDNZMONTHLY, moneyBox.getText().toString());
        cv.put(DataBaseNZ.COLUMN_TOTALNZ, Integer.parseInt(endmoneyBox.getText().toString()));

        if (Id > 0) {
            db.update(DataBaseNZ.TABLE, cv, DataBaseNZ.COLUMN_ID + "=" + String.valueOf(Id), null);
        } else {
            db.insert(DataBaseNZ.TABLE, null, cv);
        }
        goHome();
    }
    public void delete(View view){
        db.delete(DataBaseNZ.TABLE, "_id = ?", new String[]{String.valueOf(Id)});
        Intent intent = new Intent(this, NzListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void goHome(){

        db.close();

        Intent intent = new Intent(this, NzListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}