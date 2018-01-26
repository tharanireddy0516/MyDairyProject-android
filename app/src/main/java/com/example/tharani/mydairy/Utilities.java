package com.example.tharani.mydairy;
//Package objects contain version information about the implementation and specification of a Java package
//import is libraries imported for writing the code
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Tharani on 1/21/2018.
 */
/**Utilities class is for taking care of save note,loads note and for deleting note
 * saveNote():
 * saves note on private storage area of app
 * taking FileOutputStream to write anything without taking any special permissions
 * the internal storage provides unique space while installing an application
 * ObjectOutputStream is used to serialize the binary form and try catch block to catches the exception
 * writeObject() method is responsible for writing the state of the note object for its particular class
 * getAllSavedNotes():
 * use to read all saved notes
 * FileInputStream:having list of files for deserialize file back to notes taking FileInputStream
 we have list of files then we have to individually load files back to note
 getNoteByFileName()
 this method is used to get the saved note by its file name
 deleteFile():
 This method is used to delete the file by fileName*/
//create  java class Utilities for taking care of saving ,loading, deleting etc
public class Utilities {
    /**
     * String extra for a note's filename
     */
    //later if we want to edit this, making this as constant
    public static final String EXTRAS_NOTE_FILENAME = "EXTRAS_NOTE_FILENAME";
    public static final String FILE_EXTENSION = ".bin";//taking file extension

    /**
     * Save a note on private storage of the app
     *
     * @param context Application's context
     * @param note    The note to be saved
     */
    public static boolean saveNote(Context context, Dairy note) {
      //here taking saveNote method
      //for saving note needs reference to context,
       // context allows us to  save file,load file
       // taking return type is boolean to check save was successful or not
       // taking second argument as note
        String fileName = String.valueOf(note.getDateTime()) + FILE_EXTENSION;
        //for saving file taking fileName and gettingDateTime from note object
        //and giving extension of bin,so can extend the note
        /*by taking FileOutputStream do not need to take any permissions to write any thing
        *the internal storage provides unique space while installing an application*/
        FileOutputStream fos;//for writing taking FileOutputStream
        ObjectOutputStream oos;//to Serialize to binary form taking ObjectOutputStream
        /**writeObject(): method is responsible for writing the state of the note object for its particular class*/
        try {//taking try block
            //try block throws a exception
            fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
            //taking from context openFileOutput method
            //taking MODE_PRIVATE api knows that we want this in the private storage area of application
            oos = new ObjectOutputStream(fos);//making new ObjectOutputStream and passing fos
            oos.writeObject(note);//objectOutputStream to write object and object we have to write is note
            oos.close();//closes oos
        } catch (IOException e) {//catches IOException
            //catch block will handle the exception
            e.printStackTrace();// if a problem occur in program printStackTrace()says where the actual problem occurred
            //tell user the note was saved!
            return false;//tells if something goes wrong
        }

        return true;//or else returns true
    }

    /**
     * Read all saved
     *
     * @param context Application's context
     * @return ArrayList of Note
     */
    static ArrayList<Dairy> getAllSavedNotes(Context context) {//for loading note
        //taking static class and here return type is ArrayList
        ArrayList<Dairy> notes = new ArrayList<>();
        //taking new ArrayList of type Dairy and calling note
        File filesDir = context.getFilesDir();
        //for where files have been save taking File object naming fileDir
        ArrayList<String> noteFiles = new ArrayList<>();
        //taking new Array/list for the files that we are saved

        //add .bin files to the noteFiles list
        for (String file : filesDir.list()) {//saving string file in filesDir of list
            if (file.endsWith(FILE_EXTENSION)) {//saving files ended with FILE_EXTENSION
                noteFiles.add(file);//adding
            }
        }
       /* having list of files for deserialize file back to notes taking FileInputStream
        we have list of files then we have to individually load files back to note
        Serialization is a process of converting an object into a sequence of bytes which can be persisted to a disk or database or can be sent through streams.
         The reverse process of creating object from sequence of bytes is called deserialization.*/

        FileInputStream fis;//read objects and add to list of notes
        ObjectInputStream ois;
       //for getting items into list taking for for loop
        for (int i = 0; i < noteFiles.size(); i++) {
            /*Taking try catch block because IOExceptions always tend to fail
            * ClassNotFoundException:if dairy class is not found then objectInputStream cannot catch the content of file to a note class or object*/
            try {//try block
                //instantiating fis and ois objects
                fis = context.openFileInput(noteFiles.get(i));//getting index i
                ois = new ObjectInputStream(fis);//taking new object and passing fis into this
                //adding new Dairy object ois to readObject
                notes.add((Dairy) ois.readObject());
                fis.close();//closing fis
                ois.close();//closing ois

            } catch (IOException | ClassNotFoundException e) {//catches IOException and ClassNotFoundException
                e.printStackTrace();
                // if a problem occur in program printStackTrace()says where the actual problem occurred in debugger
                return null;//returning null object
            }
        }

        return notes;//returning notes
    }

    /**
     * Loads a note file by its name
     *
     * @param context  Application's context
     * @param fileName Name of the note file
     * @return A Note object, null if something goes wrong!
     */
    public static Dairy getNoteByFileName(Context context, String fileName) {
    //returns only one note by its name
        File file = new File(context.getFilesDir(), fileName);
        //creating new file with first argument as to getFilesDir and second is fileName
        if (file.exists() && !file.isDirectory()) { //checking if file actually exist
            // log message File exist with the respected file name
            Log.v("UTILITIES", "File exist = " + fileName);

            FileInputStream fis; //read objects and add to list of notes
            ObjectInputStream ois;
            // ois class deserialize primitive data and objects previously written using an ObjectOutputStream.

            try { //load the file
                fis = context.openFileInput(fileName);//getting filename
                ois = new ObjectInputStream(fis);//taking new object and passing fis into this
                Dairy note = (Dairy) ois.readObject();
                fis.close();//closing fis
                ois.close();//closing ois

                return note;//returning note

            } catch (IOException | ClassNotFoundException e) {//catches IOException and ClassNotFoundException
                e.printStackTrace();// if a problem occur in program printStackTrace()says where the actual problem occurred in debugger
                return null;
            }

        } else {
            return null;//if file dose not exit returns null
        }
    }
    /**
     * deletes the file
     *
     * @param context  Application's context
     * @param fileName Name of the note file
     * @return delete file, false if not!
     */
    public static boolean deleteFile(Context context, String fileName) {
        //for deleting note
        File dirFiles = context.getFilesDir();//gets file dir
        File file = new File(dirFiles, fileName);//taking new File with dirFiles and file Name

        if (file.exists() && !file.isDirectory()) {//checks if file exists
            return file.delete();//deletes file if we want to delete it
        }

        return false;//or else returns false
    }
}
