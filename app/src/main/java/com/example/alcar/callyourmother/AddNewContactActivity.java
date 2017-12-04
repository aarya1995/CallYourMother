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
import java.util.HashMap;

import java.util.ArrayList;

public class AddNewContactActivity extends Activity {

    Cursor cursor;
    String name;
    ArrayList<String> contacts;
    HashMap<String, ContactModel> nameToContact = new HashMap<String, ContactModel>();
    public static final String TAG = "Abhas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_priority);
        final AutoCompleteTextView textView = findViewById(R.id.autoContact);

        Button add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String contactName = textView.getText().toString();
                ContactModel currContact = nameToContact.get(contactName);
                Log.i(TAG, currContact.toString());

                Intent i = new Intent(AddNewContactActivity.this, AddContactsActivity.class);
                i.putExtra("OperationType", "Insert");
                i.putExtra("firstName", currContact.getFirstName());
                i.putExtra("lastName", currContact.getLastName());
                i.putExtra("number", currContact.getPhoneNumber());
                startActivityForResult(i, 1);
            }
        });


        contacts = new ArrayList<String>();
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            contacts.add(name); // for autocomplete text view

            if (!nameToContact.containsKey(name)) {
                String[] nameArr = name.split(" ");
                String firstName = nameArr[0];
                String lastName = "";

                if (nameArr.length > 1) {
                    lastName = nameArr[1];
                }

                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                ContactModel contact = new ContactModel(firstName, lastName, number);

                nameToContact.put(name, contact);
                Log.i("Contact Info:", contact.toString());
            }

            Log.i("Contacts:", name);
        }

        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, contacts);

        textView.setAdapter(adapter);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String fName = data.getStringExtra("firstName");
            String lName = data.getStringExtra("lastName");
            String phoneNumber = data.getStringExtra("phoneNumber");
            String priority = data.getStringExtra("priority");

            if (requestCode == 1) { // Send back to MainActivity
                Intent intent = new Intent();
                intent.putExtra("firstName", fName);
                intent.putExtra("lastName", lName);
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("priority", priority);

                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}