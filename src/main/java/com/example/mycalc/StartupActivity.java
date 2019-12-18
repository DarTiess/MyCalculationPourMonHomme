package com.example.mycalc;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StartupActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        TextView headerView = (TextView) findViewById(R.id.headers);
        switch(id){
            case R.id.hystory_calculation :
                headerView.setText("История ");
                Intent intent = new Intent(this, ListActivity.class);
                startActivity(intent);
                return true;
            case R.id.add_calculation:
                headerView.setText("Добавить");
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    public void addNewOne(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void onClickHoliday(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}