package com.example.alcar.callyourmother;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.ArrayList;

public class AddNewContactActivity extends Activity {

    Cursor cursor;
    String name;
    ArrayList<String> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_priority);

        Button add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddNewContactActivity.this, AddContactsActivity.class);
                startActivity(i);
            }
        });

        final AutoCompleteTextView textView = findViewById(R.id.autoContact);

        contacts = new ArrayList<String>();
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            contacts.add(name);

            Log.i("Contacts:", name);
        }

        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, contacts);

        textView.setAdapter(adapter);
    }
}
