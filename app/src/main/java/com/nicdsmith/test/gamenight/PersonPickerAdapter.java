package com.nicdsmith.test.gamenight;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicsmith on 1/28/16.
 */

public class PersonPickerAdapter extends RecyclerView.Adapter<PersonPickerAdapter.ViewHolder> {
    private List<Person> mDataset;
    List<Person> PersonsList = new ArrayList<Person>();
    private static final String TAG = MainActivity.class.getSimpleName();
    private static View.OnClickListener clickListener;
    private List<Person> personsChecked;




    //class to define out view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View cardView;
        public CheckBox checked;

        public ViewHolder(View v) {
            super(v);
            cardView = v;

            checked = (CheckBox) cardView.findViewById(R.id.person_selected);
        }


    }


    public void setOnClickListener(View.OnClickListener clickListener){
        this.clickListener = clickListener;
    }

    public PersonPickerAdapter() {
        mDataset = new ArrayList<Person>();
        personsChecked = new ArrayList<>();
    }

    public void appendToDataSet(Person person){
        mDataset.add(person);
        notifyDataSetChanged();
    }

    public void setData(List<Person> PersonList){
        mDataset = PersonList;
        notifyDataSetChanged();
    }

    public Person getPerson(int personID){
        return mDataset.get(personID);
    }
    @Override
    public PersonPickerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.name_list, parent, false);
        v.setOnClickListener(clickListener);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Log.i(TAG, "card created");
        //inserts information in to the Person title text field on the card\
        final Person tempPerson = mDataset.get(position);
        String personName = tempPerson.getfirstName() + " " + tempPerson.getlastName();
        holder.checked.setText(personName);
        holder.checked.setChecked(personsChecked.contains(tempPerson));
        holder.checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checked.isChecked()){
                    personsChecked.add(tempPerson);
                }else{
                    personsChecked.remove(tempPerson);
                }

            }
        });


    }

    public List<Person> getCheckedPersons(){
        return personsChecked;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.i(TAG, "item count is " + mDataset.size());
        return mDataset.size();
    }

}




