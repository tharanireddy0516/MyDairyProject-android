package com.example.tharani.mydairy;
//Package objects contain version information about the implementation and specification of a Java package
/*import is libraries imported for writing the code
* AppCompatActivity is base class for activities
* Bundle handles the orientation of the activity
*/
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
/**  onCreateOptionsMenu(): by using this method it shows the option menu that is add button to create new note
 * onOptionsItemSelected():This method is used to pass the menu item selected here it passes the add button
 * using switch cases for id:action_main_new_note to create note
 * and starts DairyActivity class for Writing the note.
 * onResume():This is the state in which the app interacts with the user
 * by suing adapter loads all saved notes
 * getAllSavedNotes():by using this method from Utilities class shows all saved notes
 * comparing saved notes based on creation time and date of notes by using comparator class
 * setOnItemClickListener():on clicking to saved notes it loads in DairyActivity
 *  getNoteByFileName():by using this method which was created in utilities class gets created note by file name
 *  putExtra:add data directly to the Bundle via the overloaded putExtra() methods of the Intent objects*/

public class MainActivity extends AppCompatActivity {
    //giving reference to variable
    private ListView  mListNotes;
    /*onCreate is the first method in the life cycle of an activity
       savedInstance passes data to super class,data is pull to store state of application
       * setContentView is used to set layout for the activity
       *R is a resource and it is auto generate file
         */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListNotes = findViewById(R.id.list_View);
        //here getting reference
    }
    /**created onCreateOptionsMenu() method  inflating  menu resource
    onCreateOptionsMenu(): by using this it shows the option menu
     getMenuInflater(): finding menu from resources and inflated in activity and calling the inflate method
    and passing arguments that is menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater() method will Inflate a menu hierarchy from the specified XML resource.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    /**
     * Creating onOptionsItemSelected() method.
     * This method passes the MenuItem selected.
     * identifies the item by calling getItemId() method,
     which returns the unique ID for the menu item .
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    //by clicking on add button we can create new note
        /*A switch statement allows a variable to be tested for equality against a list of values.
        Each value is called a case, and the variable being switched on is checked for each case.*/
        switch (item.getItemId()) {// Creating Switch Case for item selection from the menu
            case R.id.action_main_new_note: //creating case for id for create note
                /*. Intents allow you to interact with components from the same applications
                 as well as with components contributed by other applications
                 startActivity() method you can define that the intent should be used to start an activity*/
                //run DairyActivity in new note mode
                startActivity(new Intent(this, DairyActivity.class));
            //here starts DairyActivity to write new note
                //Intent can be used to bring new activity i.e DairyActivity
                //startActivity and passing new DairyActivity
                break;//terminates
        }

        return super.onOptionsItemSelected(item);//returns onOptionsItemSelected
        //here after writing note save the note by clicking save buttons and returns to create button
    }
/*onResume() is the method in the life cycle of activity
 *  This is the state in which the app interacts with the user*/
    @Override
    protected void onResume() {
        super.onResume();// Always call the superclass method first
        /*Adapters are responsible for supplying the data and creating the views representing each item
        * listview works with constructors called adapters*/
        //load saved notes into the listview
        //first, reset the listview i.e sets adapter to null
        mListNotes.setAdapter(null);
        //getting ArrayList ofDairy class
        //calling Utilities class for context passing getApplicationContext
        //getApplicationContext:Returns the context for the entire application
        ArrayList<Dairy> notes = Utilities.getAllSavedNotes(getApplicationContext());
      //gets all saved notes from utilities class
        //sort notes from new to old
        /*Collections is an utility class in java.util package.
        It consists of only static methods which are used to operate on objects of type Collection.*/
        Collections.sort(notes, new Comparator<Dairy>() {
            //sort notes from new to old
            //comparing saved notes by taking Dairy class
            @Override
            public int compare(Dairy lhs, Dairy rhs) {
               // notes are compared based on date and time the newest note will be placed first using compare method
                if(lhs.getDateTime() > rhs.getDateTime()) {//checking if lhs DateTime greater than rhs
                    return -1;//returns
                } else {
                    return 1;
                }
            }
        });

        if(notes != null && notes.size() > 0) { //check if we have any notes!
            final DairyAdapter na = new DairyAdapter(this, R.layout.view_dairy_item, notes);
            //if we have saved notes
            //taking new DairyAdapter and passing reference to context
            // for convertView taking layout view_dairy_item for saved notes
            mListNotes.setAdapter(na);//sets adapter

            //set click listener for items in the list, by clicking each item the note should be loaded into DairyActivity
            mListNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                /*An AdapterView is a group of widgets i.e view components in Android that include the ListView, Spinner, and GridView.*/
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //run the NoteActivity in view/edit mode
                    String fileName = ((Dairy) mListNotes.getItemAtPosition(position)).getDateTime()
                            + Utilities.FILE_EXTENSION;
                    //taking fileName and gets item at position by getting date time and adding extension in Utilities class
                    //now in utilities class taking getNoteByFileName method to get notes by file name
                    Intent viewNoteIntent = new Intent(getApplicationContext(), DairyActivity.class);//loads note and view the note in DairyActvity
                    //getApplicationContext() : Returns the context for the entire application
                    //for showing note inside the DairyActivity
                    viewNoteIntent.putExtra(Utilities.EXTRAS_NOTE_FILENAME, fileName);
                    //passing data between activity and shows the note by file name
                    /* putExtra() adds extended data to the intent with two parameters with extra data class Utilities and file name
                    * add data directly to the Bundle via the overloaded putExtra() methods of the Intent objects*/
                    startActivity(viewNoteIntent);//starts viewNoteIntent
                  //startActivity for launching activity
                }
            });
        } else { //remind user that we have no notes!
            Toast.makeText(getApplicationContext(), "you have no saved notes!\ncreate some new notes :)"
                    , Toast.LENGTH_SHORT).show();
            //here it shows Toast notification "you have no saved notes create some new notes" if their is no saved notes
        }
    }

}