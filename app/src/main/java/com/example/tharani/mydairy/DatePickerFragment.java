package com.example.tharani.mydairy;
//Package objects contain version information about the implementation and specification of a Java package
/*import is libraries imported for writing the code
* AppCompatActivity is base class for activities
* Bundle handles the orientation of the activity
*/
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DatePickerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DatePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
/**created calss datePickerFragment which extends Dialog Fragment and implemenenting DatePickerDialog
 * A Fragment represents a behavior or a portion of user interface in an Activity*/
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    List<Integer> integerList;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";//for  param1
    private static final String ARG_PARAM2 = "param2";//for param2

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DatePickerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DatePickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DatePickerFragment newInstance(String param1, String param2) {
        DatePickerFragment fragment = new DatePickerFragment();//created new DatePickerFragment
        Bundle args = new Bundle();//and new Bundle
        args.putString(ARG_PARAM1, param1);//puts String fro param1
        args.putString(ARG_PARAM2, param2);//for param2
        fragment.setArguments(args);//sets arggs
        return fragment;//returns fragment
    }
/*onCreate is the first method in the life cycle of an activity
       savedInstance passes data to super class,data is pull to store state of application
       * setContentView is used to set layout for the activity
       *R is a resource and it is auto generate file
         */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);//gets agrumnets pf PARAM!
            mParam2 = getArguments().getString(ARG_PARAM2);//gets arguments of PARAM@
        }
    }
/*taken onCreateView method*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_date_picker_fragment, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(List<Integer> uri) {
        if (mListener != null) {//if mListener not null
            mListener.onFragmentInteraction(uri);//then taken uri
        }
    }
/*taken onCreateDailog method*/
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();//gets instance of calender
        int year = c.get(Calendar.YEAR);//for year
        int month = c.get(Calendar.MONTH);//for month
        int day = c.get(Calendar.DAY_OF_MONTH);//for DAY_OF_MONTH

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);

    }
/*Taken onAttach method*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;//taken onFragmentInteraction inf if condition
        } else {
            throw new RuntimeException(context.toString()//throws new Runtime Exception
                    + " must implement OnFragmentInteractionListener");
        }
    }
/*taken onDetach method*/
    @Override
    public void onDetach() {
        super.onDetach();//returning super
        mListener = null;//sets mListener to null
    }
/*Taken onDateset method to set date*/
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        integerList = new ArrayList<>();//created new Arraylist
        integerList.add(i);//adding i i.e Year
        integerList.add(i1);//adding i1 for month
        integerList.add(i2);//adding i2 for DAY_OF_MONTH
        onButtonPressed(integerList);//onButtonPressed in intergerList
        Toast.makeText(getActivity(), "Date set to "+i+"/"+i1+"/"+i2, Toast.LENGTH_SHORT).show();
    }//displays toast message when date was setted

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(List<Integer> uri);//void doesnot return any method
    }
}
