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
 * Created by nicsmith on 1/4/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Event> mDataset;
    List<Event> eventsList = new ArrayList<Event>();


   //class to define out view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

       public View cardView;
       public TextView eventTitle;
       public TextView eventDesc;
       public ViewHolder(View v) {
            super(v);
           cardView = v;
           eventTitle = (TextView) cardView.findViewById(R.id.event_title);
           eventDesc = (TextView) cardView.findViewById(R.id.event_desc);
        }
    }

    public RecyclerViewAdapter() {
        mDataset = new ArrayList<Event>();
    }
    public void appendToDataSet(Event event){
        mDataset.add(event);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Log.i("creating card", "card created");
        //inserts information in to the event title text field on the card\
        Event tempEvent = mDataset.get(position);
        holder.eventTitle.setText(tempEvent.getEventTitle());

        holder.eventDesc.setText(tempEvent.getEventDesc());



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.i("getItemCount", "item count is " + mDataset.size());
        return mDataset.size();
    }

}


