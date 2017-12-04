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
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Created by jiaxin on 2017/11/15.
 */

public class AddContactsActivity extends Activity {
    private int type=0;//type of priority, Low:0 Med:1 High:2
    private String []strings={"Low", "Med","High"};
    SQLiteHelper sQLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_priority);
        sQLiteHelper = new SQLiteHelper(AddContactsActivity.this); // create db

        Button setButton = findViewById(R.id.set);
        Spinner sp = (Spinner) findViewById(R.id.selectPriority);
        final EditText firstName = (EditText) findViewById(R.id.firstName);
        final EditText lastName = (EditText) findViewById(R.id.lastName);
        final EditText phoneNumber = (EditText) findViewById(R.id.phoneNumber);

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
                String fName = firstName.getText().toString();
                String lName = lastName.getText().toString();
                String number = phoneNumber.getText().toString();
                String priority = Integer.toString(type);

                // ContactModel contact = new ContactModel(fName, lName, number, priority);
                Intent intent = new Intent();
                intent.putExtra("firstName", fName);
                intent.putExtra("lastName", lName);
                intent.putExtra("phoneNumber", number);
                intent.putExtra("priority", priority);

                // create the alarm -- should also do this in main activity
                AlarmOperation.enableAlert(AddContactsActivity.this,type);
                Toast.makeText(getApplicationContext(), strings[type]+" Alarm set", Toast.LENGTH_LONG).show();

                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }




}
