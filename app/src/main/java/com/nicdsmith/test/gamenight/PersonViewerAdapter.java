package com.nicdsmith.test.gamenight;

/**
 * Created by nicsmith on 2/6/16.
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import android.widget.TextView;

/**
 * Created by nicsmith on 1/28/16.
 */

public class PersonViewerAdapter extends RecyclerView.Adapter<PersonViewerAdapter.ViewHolder> {
    private List<Person> mDataset;
    List<Person> PersonsList = new ArrayList<Person>();
    private static final String TAG = MainActivity.class.getSimpleName();
    private static View.OnClickListener clickListener;
    private List<Person> personsChecked;




    //class to define out view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View cardView;
        public TextView personName;

        public ViewHolder(View v) {
            super(v);
            cardView = v;

            personName = (TextView) cardView.findViewById(R.id.show_person);
        }


    }



    public PersonViewerAdapter() {
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

    public Person getPerson(int personID){
        return mDataset.get(personID);
    }
    @Override
    public PersonViewerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_name_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Log.i(TAG, "card created");
        //inserts information in to the Person title text field on the card\
        final Person tempPerson = mDataset.get(position);
        String personName = tempPerson.getfirstName() + " " + tempPerson.getlastName();
        holder.personName.setText(personName);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.i(TAG, "item count is " + mDataset.size());
        return mDataset.size();
    }

}




