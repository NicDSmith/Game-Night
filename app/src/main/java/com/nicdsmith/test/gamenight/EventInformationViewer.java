package com.nicdsmith.test.gamenight;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventInformationViewer extends AppCompatActivity {


    private String EVENT_ID = "event_id";
    public static final String TAG = EventInformationViewer.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private PersonViewerAdapter mAdapter;
    private Event eventToBeDisplayed;
    private PersonDataSource personDataSource;
    private EventPersonsDataSource eventPersonsDataSource;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_information_viewer);

        Intent intent = getIntent();
        long eventID = intent.getLongExtra(EVENT_ID, -1);

        EventDataSource dataSource = new EventDataSource(this);
        dataSource.open();
        Log.i(TAG, "onCreate: " + eventID);
         eventToBeDisplayed = dataSource.getEventByID(eventID);

        mRecyclerView = (RecyclerView) findViewById(R.id.show_person_recycler_view);

        //sets the LayoutManager of our RecyclerView
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //passes in our dataset to our adapter
        mAdapter = new PersonViewerAdapter();

        //sets the adapter to our RecyclerView
        mRecyclerView.setAdapter(mAdapter);


        if(eventToBeDisplayed != null) {
            EditText tempEditText = (EditText) findViewById(R.id.event_title_view);
            tempEditText.setText(eventToBeDisplayed.getEventTitle());

            tempEditText = (EditText) findViewById(R.id.event_desc_view);
            tempEditText.setText(eventToBeDisplayed.getEventDesc());

            tempEditText = (EditText) findViewById(R.id.start_date_view);
            tempEditText.setText(convertEpochToString(eventToBeDisplayed.getStartDate()));

            tempEditText = (EditText) findViewById(R.id.end_date_view);
            tempEditText.setText(convertEpochToString(eventToBeDisplayed.getEndDate()));

            tempEditText = (EditText) findViewById(R.id.event_cycle_length_view);
            tempEditText.setText(Integer.toString(eventToBeDisplayed.getEventCycleLength()));
        }
    }

    public String convertEpochToString(long epoch){
        return new SimpleDateFormat("MM/dd/yyyy").format(new Date(epoch));
    }

    protected void onResume() {
        super.onResume();

        new AsyncTask<Context, Void, List<Person>>() {
            @Override
            protected List<Person> doInBackground(Context... context) {
                personDataSource = new PersonDataSource(context[0]);
                personDataSource.open();
                eventPersonsDataSource = new EventPersonsDataSource(context[0]);
                eventPersonsDataSource.open();
                List<Long> personIDs = eventPersonsDataSource.getAllPersonsForEventID(eventToBeDisplayed.getId());
                List<Person> personsForEvent = new ArrayList<Person>();
                for(int i = 0; i < personIDs.size(); i++){
                    personsForEvent.add(personDataSource.getPersonByID(personIDs.get(i)));
                }
                return personsForEvent;

            }

            @Override
            protected void onPostExecute(List<Person> persons) {
                updateAdapter(persons);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this);
    }

    protected void updateAdapter(List<Person> personsList){
        mAdapter.setData(personsList);
        Log.i(TAG, "updateAdapter: personsList " + personsList.toString());
    }

}
