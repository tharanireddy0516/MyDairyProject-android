package com.example.tharani.mydairy;

/**
 * Created by Tharani on 2/16/2018.
 */

import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**created diary objects i.e  mDateTime,mTitle,mContent
 * getTitle():Using getTitle( ) method for retrieving the Title
 * setTile():by using this method we can change the title
 * getDateTime():Using getDateTime( ) method for retrieving the Date and time of the created note
 * setDateTime()://bu using this method the datee time will gets changed according to the default time
 * getDateTimeFormatted(): This method is used to show dateTime in this dd/MM/yyyy HH:mm:ss format and gets default time zone
 * getContent():Using getContent( ) method for retrieving the content of the note
 * setContent():setContent method is used to  change the content*/
    /*Here first taken java class Dairy it represents  dairy objects
    * implementing Serializable interface which does not provide any methods
    * serialization is saving the current state of objects as binary file that is used for later use
    * Serialization is a process of converting an object into a sequence of bytes
    * which can be persisted to a disk or database or can be sent through streams
    * here jrm will know that the Dairy object is just serializable
    * after pressing save button on menu the diary content will save*/
public class Dairy implements Serializable {
    //declaring variables
    private long mDateTime; // DateTime is created for creation time of the note
    private String mTitle; // string object mTitle is created for title of note
    private String mContent,Dbdate; //content of the note
    /*creating constructors for all three variables
      constructor is to initialize the objects of a class Dairy*/
    public Dairy(long dateInMillis, String title, String content) {
        mDateTime = dateInMillis;
        mTitle = title;
        mContent = content;
    }
    /*Created Constructor for all variables i.e mTitle,mContent and dbdate for db*/
    public Dairy(String mTitle, String mContent, String dbdate) {
        this.mTitle = mTitle;
        this.mContent = mContent;
        Dbdate = dbdate;
    }
/*taken empty constructor*/
    public Dairy() {
    }

    public String getDbdate() {//gets Dbdate
        return Dbdate;//returns dbdate
    }

    public void setDbdate(String dbdate) {//sets Dbdate
        Dbdate = dbdate;//create dbdate
    }

    /*taking getters and setters for DateTime,title,content
     * The get method is used to obtain or retrieve DateTime,title,content values.
     * A set method is used to store the variables*/
    public void setDateTime(long dateTime) {
        mDateTime = dateTime;//storing dateTime object
    }

    public void setTitle(String title) {
        mTitle = title;//storing title
    }

    public void setContent(String content) {
        mContent = content;//stores the content
    }
    //retrieving the DateTime
    public long getDateTime() {
        return mDateTime;//returns to mDateTime variable
    }

    /**
     * Get date time as a formatted string
     * @param context The context is used to convert the string to user set locale
     * @return String containing the date and time of the creation of the note dairy
     */
    public String getDateTimeFormatted(Context context) {
        /*getting reference to context because we want to use this timeZone of user so if we want to get the time zone here  giving reference to context
        Context allows access to application-specific resources and classes, as well as calls for application-level operations such as launching activities, broadcasting and receiving intents*/
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"
                , context.getResources().getConfiguration().locale);

        //here the dateFormat of dairy not will be dd/MM/yyyy HH:mm:ss
        //here getting locale by using getResources and by get configuration
        //Usually a locale identifier consists of at least a language identifier and a region identifier.
        // locale is a set of parameters that defines the user's language, country and any special variant preferences that the user wants to see in their user interface.
        //get an instance of Resources with Context.getResources()
        formatter.setTimeZone(TimeZone.getDefault());//gets the default timeZone for the android device
        return formatter.format(new Date(mDateTime));
        //returns the formatter and creating new date object and passing mDateTime
    }
    //gets the title
    public String getTitle() {
        return mTitle;//returns mTitle
    }
    //gets the content
    public String getContent() {
        return mContent;//returns content
    }
}
