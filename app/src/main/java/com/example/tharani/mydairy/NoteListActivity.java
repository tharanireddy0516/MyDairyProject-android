package com.example.tharani.mydairy;
//Package objects contain version information about the implementation and specification of a Java package
/*import is libraries imported for writing the code
* AppCompatActivity is base class for activities
* Bundle handles the orientation of the activity
*/
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {
    //created variables
    ListView listView;
    DBHelper helper;
    List<Dairy> dairyList;
    DBDairyAdapter adapter;
    /*onCreate is the first method in the life cycle of an activity
       savedInstance passes data to super class,data is pull to store state of application
       * setContentView is used to set layout for the activity
       *R is a resource and it is auto generate file
         */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);//finds id
        setSupportActionBar(toolbar);//toolbar
        listView = findViewById(R.id.lv);
        dairyList = new ArrayList<>();//taken new arrayList
        helper = new DBHelper(NoteListActivity.this);//gets new DBHelper with reference to context from noteListActivity
        dairyList = helper.getlist();//gets dairyList
        adapter = new DBDairyAdapter(this,R.layout.view_dairy_item,dairyList);
        //taken newDBDairyAdapter with layout view_dairy_item,dairyList
        listView.setAdapter(adapter);//sets adapter for listView


    }

}
