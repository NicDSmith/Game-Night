package com.nicdsmith.test.gamenight;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
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
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

        EventDataSource dataSource = new EventDataSource(this);
        dataSource.open();

        dataSource.createEvent(eventTitleText, eventDescText,1,1,1);

        finish();
        Log.i(TAG, "saveEvent: ended");
    }

    public void showDatePicker(View v){
        DialogFragment dialogFragment = new StartDatePicker();
        dialogFragment.show(getFragmentManager(), "start_date_picker");
    }

    Calendar c = Calendar.getInstance();
    int startYear = c.get(Calendar.YEAR);
    int startMonth = c.get(Calendar.MONTH);
    int startDay = c.get(Calendar.DAY_OF_MONTH);

    class StartDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            // Use the current date as the default date in the picker
            DatePickerDialog dialog = new DatePickerDialog(EventCreationActivity.this, this, startYear, startMonth, startDay);
            return dialog;

        }
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            // Do something with the date chosen by the user
            startYear = year;
            startMonth = monthOfYear;
            startDay = dayOfMonth+1;
            updateDateDisplay();


        }
        public void updateDateDisplay(){
            EditText start_date = (EditText) findViewById(R.id.start_date);
            start_date.setText(startMonth + "/" + startDay + "/" + startYear);
            Log.i(TAG, "updateDateDisplay: it worked");
        }
    }






}
