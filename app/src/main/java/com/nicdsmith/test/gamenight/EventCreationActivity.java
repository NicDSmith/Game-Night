package com.nicdsmith.test.gamenight;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class EventCreationActivity extends AppCompatActivity {

    Intent intent = getIntent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);

    }
    public void saveEvent(View view){
        Intent intent = new Intent(this, MainActivity.class);
        Bundle extras = new Bundle();
        EditText editText = (EditText) findViewById(R.id.event_title_capture);
        String eventTitleCapture = editText.getText().toString();

        editText = (EditText) findViewById(R.id.event_desc_capture);
        String eventDescCapture = editText.getText().toString();

        extras.putString("EXTRA_EVENTTITLE",eventTitleCapture);
        extras.putString("EXTRA_EVENTDESC", eventDescCapture);

        intent.putExtras(extras);
        setResult(RESULT_OK, intent);
        finish();
    }

}
