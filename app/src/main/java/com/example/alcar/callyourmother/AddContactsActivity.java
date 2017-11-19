package com.example.alcar.callyourmother;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;


/**
 * Created by jiaxin on 2017/11/15.
 */

public class AddContactsActivity extends Activity {
    private int type=0;//type of priority, Low:0 Med:1 High:2
    private String []strings={"Low", "Med","High"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_priority);

        Button setButton = findViewById(R.id.set);
        Spinner sp = (Spinner) findViewById(R.id.selectPriority);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type=i;//Low:0 Med:1 High:2
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmOperation.enableAlert(AddContactsActivity.this,type);
                Toast.makeText(getApplicationContext(), strings[type]+" Alarm set", Toast.LENGTH_LONG).show();

            }
        });
    }




}
