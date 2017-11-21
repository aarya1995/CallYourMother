package com.example.alcar.callyourmother;

import android.content.Intent;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button add = findViewById(R.id.addNewContacts);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,AddContactsActivity.class);
                startActivity(i);
            }
        });

        CustomListView = this;

        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
        setListData();

        //Resources res =getResources();
        list = ( ListView )findViewById( R.id.existingPriority );  // List defined in XML ( See Below )

        /**************** Create Custom Adapter *********/
        //adapter=new CustomAdapter( CustomListView, CustomListViewValuesArr,res );
        adapter=new MyBaseAdapter();
        list.setAdapter( adapter );
    }

    /****** Function to set data in ArrayList *************/
    public void setListData()
    {

        for (int i = 0; i < 11; i++) {

            final ListModel sched = new ListModel();

            /******* Firstly take data in model object ******/
            sched.setContactName("Contact Name"+String.valueOf(i));

            /******** Take Model Object in ArrayList **********/
            CustomListViewValuesArr.add( sched );
        }

    }


    /*****************  This function used by adapter ****************/
    public void onItemClick(int mPosition)
    {
        ListModel tempValues = ( ListModel ) CustomListViewValuesArr.get(mPosition);


        // SHOW ALERT

        Toast.makeText(CustomListView,
                ""+tempValues.getContactName(),
                Toast.LENGTH_LONG)
                .show();
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
                convertView=View.inflate(MainActivity.this,R.layout.single_list_priority,null);
            }
            ListModel m = CustomListViewValuesArr.get(position);
            TextView name = (TextView) convertView.findViewById(R.id.contactName);
            Button add =(Button) convertView.findViewById(R.id.add_button);
            Button delete=(Button) convertView.findViewById(R.id.delete_button);
            name.setText(m.getContactName());
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(MainActivity.this,AddContactsActivity.class);
                    startActivity(intent);
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO
                }
            });
            return convertView;
        }
    }
}
