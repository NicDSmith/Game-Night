package com.nicdsmith.test.gamenight;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicsmith on 1/28/16.
 */

public class PersonPickerAdapter extends RecyclerView.Adapter<PersonPickerAdapter.ViewHolder> {
    private List<Person> mDataset;
    List<Person> PersonsList = new ArrayList<Person>();
    private static final String TAG = MainActivity.class.getSimpleName();


    //class to define out view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View cardView;
        public TextView Name;
        public ViewHolder(View v) {
            super(v);
            cardView = v;
            Name = (TextView) cardView.findViewById(R.id.personName);
        }
    }

    public PersonPickerAdapter() {
        mDataset = new ArrayList<Person>();
    }
    public void appendToDataSet(Person person){
        mDataset.add(person);
        notifyDataSetChanged();
    }

    public void setData(List<Person> PersonList){
        mDataset = PersonList;
        notifyDataSetChanged();
    }

    @Override
    public PersonPickerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.name_list, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Log.i(TAG, "card created");
        //inserts information in to the Person title text field on the card\
        Person tempPerson = mDataset.get(position);
        String personName = tempPerson.getfirstName() + " " + tempPerson.getlastName();
        holder.Name.setText(personName);




    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.i(TAG, "item count is " + mDataset.size());
        return mDataset.size();
    }

}




