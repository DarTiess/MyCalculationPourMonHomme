package com.example.mycalc;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TransferFromNzActivity extends AppCompatActivity {

    String[] selected = {"НЗ", "Депозит"};
    Spinner selectedPlaceFrom;
    Spinner selectedPlaceTo;
    TextView selectionFrom;
    TextView selectionTo;
    EditText sumToTransfer;


    DataBaseDeposit dbDeposit;
    SQLiteDatabase db_dep;
    Cursor depositCursor2;
    protected int depositTotal;
    String msName_dep;

    DataBaseNZ dbNZ;
    SQLiteDatabase db_nz;
    Cursor nzCursor2;
    protected int NZTotal;
    long Id=0;
    String msName_nz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_from_nz);

        dbDeposit=new DataBaseDeposit(getApplicationContext());
        dbNZ=new DataBaseNZ(getApplicationContext());

        selectedPlaceFrom=(Spinner)findViewById(R.id.selected_placeFrom);
        selectedPlaceTo=(Spinner)findViewById(R.id.selected_placeTo);
        selectionFrom=(TextView)findViewById(R.id.selectFrom);
        selectionTo=(TextView)findViewById(R.id.selectTo);
        sumToTransfer=(EditText)findViewById(R.id.sum_toTransfer);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selected);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectedPlaceFrom.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)parent.getItemAtPosition(position);
                selectionFrom.setText(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        selectedPlaceFrom.setOnItemSelectedListener(itemSelectedListener);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selected);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectedPlaceTo.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener1 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)parent.getItemAtPosition(position);
                selectionTo.setText(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        selectedPlaceTo.setOnItemSelectedListener(itemSelectedListener1);



    }
    public void onResume() {
        super.onResume();
        db_nz = dbNZ.getReadableDatabase();

        nzCursor2 = db_nz.rawQuery("select  * from "
                + DataBaseNZ.TABLE + " ORDER BY _ID DESC LIMIT 1", null);
        nzCursor2.moveToFirst();
        NZTotal = Integer.parseInt(nzCursor2.getString(3));
        msName_nz=nzCursor2.getString(1);


        db_dep = dbDeposit.getReadableDatabase();

        depositCursor2=db_dep.rawQuery("select  * from "
                + DataBaseDeposit.TABLE+" ORDER BY _ID DESC LIMIT 1", null);
        depositCursor2.moveToFirst();
        depositTotal = Integer.parseInt(depositCursor2.getString(3));
        msName_dep=depositCursor2.getString(1);
    }

    public void makeTransfer(View view) {
        if(selectionFrom.getText().toString()=="НЗ"){
            if(Integer.parseInt(sumToTransfer.getText().toString())>NZTotal){
                Toast.makeText(getApplicationContext(), "Недостаточно средст для списания ", Toast.LENGTH_SHORT).show();
            }
            else {
                ContentValues cv = new ContentValues();
                cv.put(DataBaseNZ.COLUMN_MONTH, msName_nz);
                cv.put(DataBaseNZ.COLUMN_ADDNZMONTHLY,"-"+ sumToTransfer.getText().toString());
                cv.put(DataBaseNZ.COLUMN_TOTALNZ, Integer.parseInt(String.valueOf(NZTotal-(Integer.parseInt(sumToTransfer.getText().toString())))));

                if (Id > 0) {
                    db_nz.update(DataBaseNZ.TABLE, cv, DataBaseNZ.COLUMN_ID + "=" + String.valueOf(Id), null);
                } else {
                    db_nz.insert(DataBaseNZ.TABLE, null, cv);
                }

                ContentValues cv_dp = new ContentValues();
                cv_dp.put(DataBaseDeposit.COLUMN_MONTH, msName_dep);
                cv_dp.put(DataBaseDeposit.COLUMN_ADDMONTHLY, sumToTransfer.getText().toString());
                cv_dp.put(DataBaseDeposit.COLUMN_TOTALDEPOSIT,  Integer.parseInt(String.valueOf(depositTotal+(Integer.parseInt(sumToTransfer.getText().toString())))));

                if (Id > 0) {
                    db_dep.update(DataBaseDeposit.TABLE, cv_dp, DataBaseDeposit.COLUMN_ID + "=" + String.valueOf(Id), null);
                } else {
                    db_dep.insert(DataBaseDeposit.TABLE, null, cv_dp);
                }


                Toast.makeText(getApplicationContext(), "Перевод успешно выполнен ", Toast.LENGTH_SHORT).show();
                goHome();
            }
        }
        else {
            if(Integer.parseInt(sumToTransfer.getText().toString())>depositTotal){
                Toast.makeText(getApplicationContext(), "Недостаточно средст для списания ", Toast.LENGTH_SHORT).show();
            }
            else {
                ContentValues cv = new ContentValues();
                cv.put(DataBaseNZ.COLUMN_MONTH, msName_nz);
                cv.put(DataBaseNZ.COLUMN_ADDNZMONTHLY,sumToTransfer.getText().toString());
                cv.put(DataBaseNZ.COLUMN_TOTALNZ, Integer.parseInt(String.valueOf(NZTotal+(Integer.parseInt(sumToTransfer.getText().toString())))));

                if (Id > 0) {
                    db_nz.update(DataBaseNZ.TABLE, cv, DataBaseNZ.COLUMN_ID + "=" + String.valueOf(Id), null);
                } else {
                    db_nz.insert(DataBaseNZ.TABLE, null, cv);
                }

                ContentValues cv_dp = new ContentValues();
                cv_dp.put(DataBaseDeposit.COLUMN_MONTH, msName_dep);
                cv_dp.put(DataBaseDeposit.COLUMN_ADDMONTHLY, "-"+sumToTransfer.getText().toString());
                cv_dp.put(DataBaseDeposit.COLUMN_TOTALDEPOSIT,  Integer.parseInt(String.valueOf(depositTotal-(Integer.parseInt(sumToTransfer.getText().toString())))));

                if (Id > 0) {
                    db_dep.update(DataBaseDeposit.TABLE, cv_dp, DataBaseDeposit.COLUMN_ID + "=" + String.valueOf(Id), null);
                } else {
                    db_dep.insert(DataBaseDeposit.TABLE, null, cv_dp);
                }


                Toast.makeText(getApplicationContext(), "Перевод успешно выполнен ", Toast.LENGTH_SHORT).show();
                goHome();
            }
        }

    }
    private void goHome(){

        db_nz.close();

        Intent intent = new Intent(this, StartupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}
