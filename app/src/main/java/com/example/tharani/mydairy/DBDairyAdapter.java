package com.example.tharani.mydairy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Tharani on 2/16/2018.
 */
/**created DBDairyAdapter which extending ArrayAdapter which displays saved notes
 *  An adapter is a bridge between UI component and data source that helps us to fill data in UI component
 *  here taken DBDairyAdapter in which list view gets start building*/
public class DBDairyAdapter extends ArrayAdapter<Dairy> {

    Context context;//taking context
    int resource;//initializing resources
    List<Dairy> dairyList;//List Dairy for dairyList
    //taken constructors
    public DBDairyAdapter(@NonNull Context context, int resource, @NonNull List<Dairy> objects) {
        super(context, resource, objects);
        this.context =context;//taking context
        this.resource =resource;//resource
        this.dairyList = objects;//objects

    }
    /*taken getView() method which is used for storage of arguments
         * the adapter populates each list item with a View object by calling getView() on each row
         * covertView: The Adapter uses the convertView as a way of recycling old View objects that are no longer being used in other words
         * convertView is the ListView Item Cache that is not visible, and hence it can be reused. It lets the ListView need not create a lot of ListItems,
         * hence saving memory and making the ListView more smooth.*/
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {//taking convertView is null
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.view_db_dairy_data, null);
            //inflate or filling  the layout file for the dairy
        }

        Dairy notes = dairyList.get(position);//gets item from position from Dairy

        if(notes != null) {//if DB notes is not null
            //starts building the listView here
            //taking reference to three textviews
            TextView title =  convertView.findViewById(R.id.dblist_note_title);
            TextView date =  convertView.findViewById(R.id.dblist_note_date);
            TextView content =  convertView.findViewById(R.id.dblist_note_content_preview);

            title.setText(notes.getTitle());//sets title text and gets title
            /*.getDateTimeFormatted() method from Dairy class which gets date format in dd/MM/yyyy HH:mm:ss
            * .getContext() method  returns the context the view is currently running in activity*/
            date.setText(notes.getDbdate());
            //correctly show preview of the content (not more than 50 char or more than one line!)

            content.setText(notes.getContent());
        }

        return convertView;//returning convertView

    }
}

