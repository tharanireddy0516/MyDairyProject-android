package com.example.tharani.mydairy;
//Package objects contain version information about the implementation and specification of a Java package
/*import is libraries imported for writing the code
* AppCompatActivity is base class for activities
* Bundle handles the orientation of the activity
*/
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

/**taking DairyActivity class for checking view or edit note bundle is set,checks if note is loaded or not And
 * updating widgets of loaded note that is  mEtTitle,mEtContent,mNoteCreationTime
 * if any changes occurs in saved note it gets updates here
 * onCreateOptionsMenu():by using this method it shows the option menu that is save button to create,view or updating note and cancel and delete button
 * onOptionsItemSelected():This method is used to pass the menu item selected here it passes the save,view or update,revert,delete button
 *  onBackPressed():used for calling action cancel method that is same as backPressed
 *  actionDelete():used for setting alert dialog by showing if we want to delete the note or not
 *  actionCancel():goes back normally or goes back with changing note we will use this
 *   checkNoteAltred():Check to see if a loaded note/new note has been changed by user or not
 *   validateAndSaveNote(): Validate the title and content and save the note and finally exit the activity and go back to MainActivity*/
//created class DairyActivity which extends AppCompatActivity and the launcher will be MainActivity
public class DairyActivity extends AppCompatActivity implements DatePickerFragment.OnFragmentInteractionListener{
    //giving reference to variables
    private boolean mIsViewingOrUpdating; //state of the activity
    private long mNoteCreationTime;//note creation time
    private String mFileName;//FileName
    private Dairy mLoadedNote,dairy = null;//loaded note i.e saved old note
    private EditText mEtTitle;//for title
    private EditText mEtContent;//for content
    ImageButton imageButton;
    @Override
    /*onCreate is the first method in the life cycle of an activity
  * savedInstance passes data to super class,data is pull to store state of application
  * setContentView is used to set layout for the activity
  *R is a resource and it is auto generate file
  */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dairy);
        //here getting reference for mEtTile,mEtContent,mFileName
        mEtTitle =  findViewById(R.id.note_et_title);
        mEtContent = findViewById(R.id.note_et_content);
        imageButton = findViewById(R.id.addnotesbutton);
        dairy = new Dairy();
        //check if view/edit note bundle is set, otherwise user wants to create new note
        mFileName = getIntent().getStringExtra(Utilities.EXTRAS_NOTE_FILENAME);
        //checking whether we got the note by filename or not
        if(mFileName != null && !mFileName.isEmpty() && mFileName.endsWith(Utilities.FILE_EXTENSION)) {
            //for showing the previously created note by its file name
            mLoadedNote = Utilities.getNoteByFileName(getApplicationContext(), mFileName);
            if (mLoadedNote != null) {//checking if note is loaded or not
                //update the widgets from the loaded note
                mEtTitle.setText(mLoadedNote.getTitle());//for title
                mEtContent.setText(mLoadedNote.getContent());//for content
                mNoteCreationTime = mLoadedNote.getDateTime();//for creationTime of note
                mIsViewingOrUpdating = true;
            }
        } else { //if user wants to create a new note
            mNoteCreationTime = System.currentTimeMillis();//gets note creation time
            mIsViewingOrUpdating = false;//if its is false
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment pickerFragment = new DatePickerFragment();
                pickerFragment.show(getSupportFragmentManager(),"datePicker");
            }
        });
    }
    /**created onCreateOptionsMenu() method  inflating  menu resource
     onCreateOptionsMenu(): by using this it shows the option menu
     getMenuInflater(): finding menu from resources and inflated in activity and calling the inflate method
     and passing arguments that is menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //load menu based on the state we are in (new, view/update/delete)
        if(mIsViewingOrUpdating) { // viewing or updating a note
            getMenuInflater().inflate(R.menu.menu_dairy_view, menu);
        } else { //if we wants to create a new note
            getMenuInflater().inflate(R.menu.menu_dairy_add, menu);
        }
        return true;
    }
    /**
     * Creating onOptionsItemSelected() method.
     * This method passes the MenuItem selected.
     * You can identify the item by calling getItemId() method,
     which returns the unique ID for the menu item .
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {// Creating Switch Case for item selection from the menu
            //gets item id of particular menu
            case R.id.action_save_note:
                //save the note

            case R.id.action_update: //or update :P
                validateAndSaveNote();//calling the function from Utilities class
                DBHelper dBhelper = new DBHelper(this);
                dairy.setTitle(mEtTitle.getText().toString());
                dairy.setContent(mEtContent.getText().toString());
                dBhelper.insertData(dairy);
                break;//terminates

            case R.id.action_delete://case for delete the note
                actionDelete();//calling the actionDelete() method
                break;

            case R.id.action_cancel: //cancel the note
                actionCancel();//calling the actionCancel() methods
                break;


        }

        return super.onOptionsItemSelected(item);//returns to item
    }

    /**
     * Back button press is same as cancel action...so should be handled in the same manner!
     */
    @Override
    public void onBackPressed() {
        actionCancel();//calling actionCancel method
    }

    /**
     * Handle delete action
     */
    private void actionDelete() {
        //taking AlertDialog to ask user if he really wants to delete the note!
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this)
                // Setting Dialog Title
                .setTitle("delete note")
                // Setting Dialog Message
                .setMessage("really do you want to delete the note?")
                //setting YES button
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if old note not null then here we can deleteFile by fileName by creating method in Utilities class
                        if(mLoadedNote != null && Utilities.deleteFile(getApplicationContext(), mFileName)) {
                            //toast message if the file is deleted by title displaying in short period of time
                            Toast.makeText(DairyActivity.this, mLoadedNote.getTitle() + " is deleted"
                                    , Toast.LENGTH_LONG).show();
                        } else {
                            //toast message if we cant delete the note
                            Toast.makeText(DairyActivity.this, "can not delete the note '" + mLoadedNote.getTitle() + "'"
                                    , Toast.LENGTH_SHORT).show();
                        }
                        finish();//finishes the activity
                    }
                })
                //do nothing on clicking NO button cannot handle this so setting event handler to null
                .setNegativeButton("NO", null);

        dialogDelete.show();//shows dialogDelete
    }

    /**
     * Handle cancel action
     */
    private void actionCancel() {

        if(!checkNoteAltred()) { //if note is not altered by user (user only viewed the note/or did not write anything)
            finish(); //just exit the activity and go back to MainActivity
        } else { //we want to remind user to decide about saving the changes or not, by showing a dialog
            AlertDialog.Builder dialogCancel = new AlertDialog.Builder(this)
                    .setTitle("discard changes...")
                    .setMessage("are you sure you do not want to save changes to this note?")
                    //setting onClickListener  for DialogInterface if we want to make changes
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish(); //just go back to main activity
                        }
                    })
                    .setNegativeButton("NO", null); //null = stay in the activity!
            dialogCancel.show();//shows the dialogCancel
        }
    }

    /**
     * Check to see if a loaded note/new note has been changed by user or not
     * @return true if note is changed, otherwise false
     */
    private boolean checkNoteAltred() {
        if(mIsViewingOrUpdating) { //if in view/update mode
            //here it returns loaded note if its is not null and if any changes are made
            return mLoadedNote != null && (!mEtTitle.getText().toString().equalsIgnoreCase(mLoadedNote.getTitle())
                    || !mEtContent.getText().toString().equalsIgnoreCase(mLoadedNote.getContent()));

        } else { //if in new note mode
            return !mEtTitle.getText().toString().isEmpty() || !mEtContent.getText().toString().isEmpty();
            //returning false if the note is not changed
        }
    }

    /**
     * Validate the title and content and save the note and finally exit the activity and go back to MainActivity
     */
    private void validateAndSaveNote() {

        //get the content of widgets to make a note object
        String title = mEtTitle.getText().toString();
        String content = mEtContent.getText().toString();

        //see if user has entered anything
        if(title.isEmpty()) { //if title is empty
            //shows the pop up message  as "please enter a title!" in short period of time
            Toast.makeText(DairyActivity.this, "please enter a title!"
                    , Toast.LENGTH_SHORT).show();
            return;//returns
        }

        if(content.isEmpty()) { //if content is empty
            //shows pop up message as "please enter a content for your note!" in short period of time
            Toast.makeText(DairyActivity.this, "please enter a content for your note!"
                    , Toast.LENGTH_SHORT).show();
            return;//or else return to method
        }

        //set the creation time, if new note, now, otherwise the loaded note's creation time
        if(mLoadedNote != null) {//if loaded note is not null
            mNoteCreationTime = mLoadedNote.getDateTime();//for loaded note it gets by date time
            //gets date time of old note by creation time of note
        } else {
            //for new note gets by currentTimeMillis
            mNoteCreationTime = System.currentTimeMillis();
        }

        /*finally save the note
        getting constructors which are taken in Dairy class that is title and content
        * taking context this because here the activity is context*/
        if(Utilities.saveNote(this, new Dairy(mNoteCreationTime, title, content))) { //success!
            //tell user the note was saved!
            Toast.makeText(this, "note has been saved", Toast.LENGTH_LONG).show();
            //here it displays the Toast message "note has been saved" if note is saved in long period of time
            //Toast is a notification
        } else { //failed to save the note!
            Toast.makeText(this, "can not save the note. make sure you have enough space " +
                    "on your device", Toast.LENGTH_SHORT).show();
            //here it displays the Toast message "can not save the note. make sure you have enough space" if space is not their
        }

        finish(); //exit the activity, should return us to MainActivity
    }
    /*taken fragment interaction method and created dairy object and setted Dbdate*/
    @Override
    public void onFragmentInteraction(List<Integer> uri) {
        dairy = new Dairy();//taken new dairy object
        String dbdate = uri.get(0)+"/"+uri.get(1)+"/"+uri.get(2);
        dairy.setDbdate(dbdate);//sets Dbdate
    }
}