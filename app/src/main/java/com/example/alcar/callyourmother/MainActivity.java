package com.example.alcar.callyourmother;

import android.Manifest;
import android.content.Intent;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;


public class MainActivity extends Activity {

    ListView list;
    MyBaseAdapter adapter;
    public  MainActivity CustomListView = null;
    public ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>();
    private final String[] permissions = {"android.permission.READ_CONTACTS"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions(permissions,200);

        Button add = findViewById(R.id.addNewContacts);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddNewContactActivity.class);
                startActivity(i);
            }
        });

        CustomListView = this;
        setListData();
        list = ( ListView )findViewById( R.id.existingPriority );  // List defined in XML ( See Below )


        adapter=new MyBaseAdapter();
        list.setAdapter( adapter );
    }


    public void setListData()
    {
        /**need to change i<11 to the actually list in database**/
        for (int i = 0; i < 11; i++) {

            final ListModel existingPriorityList = new ListModel();

           /**This is just manually adding contact, need to figure out how to get contacts from priority**/
            existingPriorityList.setContactName("Contact Name"+String.valueOf(i));


            CustomListViewValuesArr.add(existingPriorityList);
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
            ListModel m = CustomListViewValuesArr.get(position);
            TextView name = (TextView) convertView.findViewById(R.id.contactName);
            name.setText(m.getContactName());
            Button edit = (Button) convertView.findViewById(R.id.edit_button);
            Button delete = (Button) convertView.findViewById(R.id.delete_button);
            /**edit button need to be linked to correct function. Here it is just an example**/
            edit.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent edit_priority = new Intent(MainActivity.this, AddContactsActivity.class);
                    startActivity(edit_priority);
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
