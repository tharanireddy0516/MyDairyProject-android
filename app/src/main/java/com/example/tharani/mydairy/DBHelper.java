package com.example.tharani.mydairy;
//Package objects contain version information about the implementation and specification of a Java package
//import is libraries imported for writing the code

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tharani on 2/16/2018.
 */
/**taken DBHelper Class which extends SQLiteOpenHelper
 * SQLiteOpenHelper: class to manage database creation and version management.
 * onCreate :this class takes care of opening the database if it exists, creating it if it does not, and upgrading it as necessary
 * SQLite is  way of storing user data. SQLite is a very light weight */
public class DBHelper extends SQLiteOpenHelper {

    Context context;//context
    public DBHelper(Context context) {
        super(context, "MyNewDiary", null, 1);
        this.context = context;//accessing reference to thsi context
    }
    //taken OnCreate method and created table with title , text , date create at text
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //this method takes care of opening the database if it exists, creating it if it does not, and upgrading it as necessary
        sqLiteDatabase.execSQL("CREATE TABLE DIARY (ID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT,CONTENT TEXT,DATE_CREATEAT TEXT);") ;
    }
/**onUpgrade method which allows when the database needs to be upgraded*/
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    //insertData method for inserting title,content,date_create At
    public void insertData(Dairy dairy){
        SQLiteDatabase database = this.getWritableDatabase();//getsWritableDatabase i.e Create and/or open a database
        ContentValues contentValues = new ContentValues();//new contentValues
        contentValues.put("TITLE",dairy.getTitle());//puts title
        contentValues.put("CONTENT",dairy.getContent());//content
        contentValues.put("DATE_CREATEAT",dairy.getDbdate());//DATE_CreateAt

        long result = database.insert("DIARY",null,contentValues);//inserting DAIRY with return type long
        if(result!=-1){//if results not equals to null
            Toast.makeText(context, "inserted", Toast.LENGTH_SHORT).show();
            //gets toast message if data is inserted
        }else{
            Toast.makeText(context, "not inserted", Toast.LENGTH_SHORT).show();
            //displays toast message if data is not inserted
        }
    }
    //this method helps to get list for dairy class
    public List<Dairy> getlist(){//gets list of Dairy class
        List<Dairy>dairies = new ArrayList<>();//created new arrayList
        SQLiteDatabase database = this.getReadableDatabase();//gets Readable databse
        Cursor cursor = database.rawQuery("SELECT * FROM DIARY", null);
        //cursor for dairy
        if(cursor.moveToFirst()){//if cursor moves to first
            while (!cursor.isAfterLast()){//while not cursor is after lats
                Dairy dairy = new Dairy();//created new dairy object
                dairy.setTitle(cursor.getString(cursor.getColumnIndex("TITLE")));//gets coulum index for title
                dairy.setContent(cursor.getString(cursor.getColumnIndex("CONTENT")));//fo content
                dairy.setDbdate(cursor.getString(cursor.getColumnIndex("DATE_CREATEAT")));//for date_create at

                dairies.add(dairy);//adds dairy objects
                cursor.moveToNext();//cursor moves to first
            }
        }
        return dairies;//returns dairies
    }

}
