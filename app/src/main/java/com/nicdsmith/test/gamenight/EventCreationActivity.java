package com.nicdsmith.test.gamenight;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class EventCreationActivity extends AppCompatActivity {

    private static final String TAG = EventCreationActivity.class.getSimpleName();
    Intent intent = getIntent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);

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

        dataSource.createEvent(eventTitleText, eventDescText);

        finish();
        Log.i(TAG, "saveEvent: ended");
    }

}
