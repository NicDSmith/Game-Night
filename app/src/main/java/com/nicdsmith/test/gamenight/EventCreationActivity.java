package com.nicdsmith.test.gamenight;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventCreationActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private PersonPickerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final String TAG = EventCreationActivity.class.getSimpleName();
    private PersonDataSource datasource;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);

        mRecyclerView = (RecyclerView) findViewById(R.id.name_list_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        //sets the LayoutManager of our RecyclerView
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //passes in our dataset to our adapter
        mAdapter = new PersonPickerAdapter();

        //sets the adapter to our RecyclerView
        mRecyclerView.setAdapter(mAdapter);

    }


    public Person getPersonById(long id) {
        datasource = new PersonDataSource(this);
        datasource.open();
        return datasource.getPersonByID(id);
    }


    protected void onResume() {
        super.onResume();

        new AsyncTask<Context, Void, List<Person>>() {
            @Override
            protected List<Person> doInBackground(Context... context) {
                datasource = new PersonDataSource(context[0]);
                datasource.open();
                return datasource.getAllPersons();

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
    public void saveEvent(View view){
        Log.i(TAG, "saveEvent: started");
        Intent intent = new Intent(this, MainActivity.class);

        EditText editText = (EditText) findViewById(R.id.event_title_capture);
        String eventTitleText = editText.getText().toString();
        Log.i(TAG, "saveEvent: string to be added " + eventTitleText);

        editText = (EditText) findViewById(R.id.event_desc_capture);
        String eventDescText = editText.getText().toString();
        Log.i(TAG, "saveEvent: string to be added " + eventDescText);

        editText = (EditText) findViewById(R.id.start_date);
        String eventStartDate = editText.getText().toString();
        Log.i(TAG, "saveEvent: string to be added " + eventDescText);

        editText = (EditText) findViewById(R.id.end_date);
        String eventEndDate = editText.getText().toString();
        Log.i(TAG, "saveEvent: string to be added " + eventDescText);

        editText = (EditText) findViewById(R.id.event_cycle_length);
        String eventCycleLength = editText.getText().toString();
        Log.i(TAG, "saveEvent: string to be added " + eventDescText);

        EventDataSource dataSource = new EventDataSource(this);
        dataSource.open();
        int cycleLength;
        try {
            cycleLength = Integer.parseInt(eventCycleLength);
        }catch (NumberFormatException e){
            cycleLength = 0;
        }

        long insertID = dataSource.createEvent(eventTitleText, eventDescText,getLongFromDateString(eventStartDate),getLongFromDateString(eventEndDate),cycleLength);


        EventPersonsDataSource eventPersonsDataSource = new EventPersonsDataSource(this);
        eventPersonsDataSource.open();
        List<Person> personsChecked = mAdapter.getCheckedPersons();

        Log.i(TAG, "saveEvent: personschecked.size = " + personsChecked.size());
        for(int i = 0; i < personsChecked.size(); i++) {
            Log.i(TAG, "saveEvent: person id = " + personsChecked.get(i).getId() + " person position = " + i );
            eventPersonsDataSource.createEventPersons(insertID,personsChecked.get(i).getId(),i);
        }
        finish();
        Log.i(TAG, "saveEvent: ended");
    }

    public long getLongFromDateString(String dateString){

        long epoch = 0;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date date = sdf.parse(dateString);

            epoch = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return epoch;
    }

    public void showDatePicker(View v){

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR) ;
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener dateListener = null;
        switch (v.getId()){
            case R.id.start_date:
               dateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        EditText start_date = (EditText) findViewById(R.id.start_date);
                        start_date.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
                    }
                };
                break;
            case R.id.end_date:
                dateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        EditText end_date = (EditText) findViewById(R.id.end_date);
                        end_date.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
                    }
                };
                break;
            default:
                return;
        }
        new DatePickerDialog(this,dateListener,year,month,day).show();

    }
}
