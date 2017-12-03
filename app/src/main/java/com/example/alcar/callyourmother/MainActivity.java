package com.example.alcar.callyourmother;

import android.content.Intent;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import java.util.ArrayList;
import java.util.zip.Inflater;


public class MainActivity extends Activity {

    ListView list;
    MyBaseAdapter adapter;
    public  MainActivity CustomListView = null;
    public ArrayList<ContactModel> CustomListViewValuesArr = new ArrayList<ContactModel>();
    SQLiteHelper sQLiteHelper;
    public static final String TAG = "Abhas";
    private final String[] permissions = {"android.permission.READ_CONTACTS"};
    // private String rowID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions(permissions,200);

        sQLiteHelper = new SQLiteHelper(MainActivity.this); // create db
        Button add = findViewById(R.id.addNewContacts);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, AddNewContactActivity.class);
                // will be used to differentiate between Insert & Edit
                i.putExtra("OperationType", "Insert");
                startActivityForResult(i,1);
            }
        });

        CustomListView = this;
        setListData(); // not doing what it's supposed to
        list = ( ListView )findViewById( R.id.existingPriority );  // List defined in XML ( See Below )


        adapter=new MyBaseAdapter();
        list.setAdapter( adapter );
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String fName = data.getStringExtra("firstName");
            String lName = data.getStringExtra("lastName");
            String phoneNumber = data.getStringExtra("phoneNumber");
            String priority = data.getStringExtra("priority");

            ContactModel contact = new ContactModel(fName, lName, phoneNumber, priority);
            Log.i("Request Code: ", Integer.toString(requestCode));

            if (requestCode == 1) { // Insert
                Log.i("Attempting to insert: ", contact.toString());
                sQLiteHelper.insertRecord(contact);
                Log.i(TAG, "SUCCESSFUL INSERT!");
            } else if (requestCode == 2) {
                Log.i("Attempting to update: ", contact.toString());
                sQLiteHelper.updateRecord(contact);
                Log.i(TAG, "SUCCESSFUL EDIT!");

            }

            setListData(); // refresh list view
            // list.setAdapter(adapter); // re-inflation
            adapter.notifyDataSetChanged();
            Log.i(TAG, "Database operation went smoothly");
        }
    }


    public void setListData()
    {
        ArrayList<ContactModel> contacts = sQLiteHelper.getAllRecords();
        ContactModel contact;

        for (int i = 0; i < contacts.size(); i++) {

            contact = contacts.get(i);

            Log.i(TAG, contact.toString());

            final ListModel existingPriorityList = new ListModel();

           /**This is just manually adding contact, need to figure out how to get contacts from priority**/

            CustomListViewValuesArr.add(contact);
        }

    }

    class MyBaseAdapter extends BaseAdapter{

        public int getCount(){
            return CustomListViewValuesArr.size();
        }
        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView=View.inflate(MainActivity.this,R.layout.single_list_priority,null);;
            }
            final ContactModel m = CustomListViewValuesArr.get(position);
            TextView name = (TextView) convertView.findViewById(R.id.contactName);
            name.setText(m.getFirstName() + " " + m.getLastName());
            Button edit = (Button) convertView.findViewById(R.id.edit_button);
            Button delete = (Button) convertView.findViewById(R.id.delete_button);
            /**edit button need to be linked to correct function. Here it is just an example**/
            edit.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent edit_priority = new Intent(MainActivity.this, AddContactsActivity.class);
                    edit_priority.putExtra("OperationType", "Edit");
                    edit_priority.putExtra("firstName", m.getFirstName());
                    edit_priority.putExtra("lastName", m.getLastName());
                    edit_priority.putExtra("number", m.getPhoneNumber());
                    edit_priority.putExtra("priority", m.getPriority());
                    startActivityForResult(edit_priority, 2);
                }
            });
            /** delete button need to be implemented to have proper function **/
            delete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent delete_priority = new Intent(MainActivity.this, null);
                }
            });
            return convertView;
        }
    }
}
