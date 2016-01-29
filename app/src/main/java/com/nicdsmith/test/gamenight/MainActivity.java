package com.nicdsmith.test.gamenight;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final String TAG = MainActivity.class.getSimpleName();
    private EventDataSource datasource;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: " + this);
        setContentView(R.layout.activity_main);
        //sets the recyclerview
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        //sets the LayoutManager of our RecyclerView
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //passes in our dataset to our adapter
        mAdapter = new RecyclerViewAdapter();
        //sets the adapter to our RecyclerView
        mRecyclerView.setAdapter(mAdapter);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        new AsyncTask<Context, Void, List<Event>>() {
            @Override
            protected List<Event> doInBackground(Context... context) {
                datasource = new EventDataSource(context[0]);
                datasource.open();
                List<Event> eventList = datasource.getAllEvents();
                return eventList;
            }

            @Override
            protected void onPostExecute(List<Event> events) {
                updateAdapter(events);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this);
    }

    protected void updateAdapter(List<Event> eventList){
        mAdapter.setData(eventList);
        Log.i(TAG, "updateAdapter: eventlist " + eventList.toString());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
    //this method starts our other activity when the FAB is pressed
    public void sendMessage(View view){
        Intent intent = new Intent(this, EventCreationActivity.class);
        ImageButton fabImageButton = (ImageButton) findViewById(R.id.fab_image_button);
        startActivityForResult(intent, 0);
    }






}
