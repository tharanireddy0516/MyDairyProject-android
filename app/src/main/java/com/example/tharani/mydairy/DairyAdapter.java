package com.example.tharani.mydairy;
//Package objects contain version information about the implementation and specification of a Java package
/*import is libraries imported for writing the code
* AppCompatActivity is base class for activities
* Bundle handles the orientation of the activity
*/
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Tharani on 1/21/2018.
 */
/**Created class DairyAdapter in this listview gets starts building
 *  An adapter is a bridge between UI component and data source that helps us to fill data in UI component
 *  and created constructors with parameters context resources and objects
 *  getView:
 *  taken getView() method which is used for storage of arguments
 * the adapter populates each list item with a View object by calling getView() on each row
 * covertView: The Adapter uses the convertView as a way of recycling old View objects that are no longer being used in other words
 * convertView is the ListView Item Cache that is not visible, and hence it can be reused. It lets the ListView need not create a lot of ListItems,
 * hence saving memory and making the ListView more smooth.
 * and sets the show preview of the content (not more than 50 char or more than one line!)*/
//created class DairyAdapter which extends ArrayAdapter with Dairy class
public class DairyAdapter extends ArrayAdapter<Dairy> {

    public static final int WRAP_CONTENT_LENGTH = 50;//not more than 50 char
    //created constructors which matches the super class or parent of this ArrayAdapters
    //with parameters context resources and Dairy class objects
    public DairyAdapter(Context context, int resource, List<Dairy> objects) {
        super(context, resource, objects);
    }
/*taken getView() method which is used for storage of arguments
 * the adapter populates each list item with a View object by calling getView() on each row
 * covertView: The Adapter uses the convertView as a way of recycling old View objects that are no longer being used in other words
 * convertView is the ListView Item Cache that is not visible, and hence it can be reused. It lets the ListView need not create a lot of ListItems,
 * hence saving memory and making the ListView more smooth.*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {//taking convertView is null
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.view_dairy_item, null);
            //inflate or filling  the layout file for the dairy
        }

        Dairy note = getItem(position);//gets item from position from Dairy

        if(note != null) {//if note is not null
            //starts building the listView here
            //taking reference to three textviews
            TextView title =  convertView.findViewById(R.id.list_note_title);
            TextView date =  convertView.findViewById(R.id.list_note_date);
            TextView content =  convertView.findViewById(R.id.list_note_content_preview);

            title.setText(note.getTitle());//sets title text and gets title
            /*.getDateTimeFormatted() method from Dairy class which gets date format in dd/MM/yyyy HH:mm:ss
            * .getContext() method  returns the context the view is currently running in activity*/
            date.setText(note.getDateTimeFormatted(getContext()));
            //correctly show preview of the content (not more than 50 char or more than one line!)
            int toWrap = WRAP_CONTENT_LENGTH;
            int lineBreakIndex = note.getContent().indexOf('\n');
            //not an elegant series of if statements...needs to be cleaned up!
            if(note.getContent().length() > WRAP_CONTENT_LENGTH || lineBreakIndex < WRAP_CONTENT_LENGTH) {
                if(lineBreakIndex < WRAP_CONTENT_LENGTH) {
                    toWrap = lineBreakIndex;//wraps the line of text to next line
                }
                if(toWrap > 0) {//if the char length is more it gets the text to dotted line format
                    content.setText(note.getContent().substring(0, toWrap) + "...");
                } else {
                    content.setText(note.getContent());//or else gets the text without dotted line
                }
            } else { //if less than 50 chars...leave it as is
                content.setText(note.getContent());//getsContent
            }
        }

        return convertView;//returns convertView
    }

}