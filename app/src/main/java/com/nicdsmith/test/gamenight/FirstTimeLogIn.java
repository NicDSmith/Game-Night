package com.nicdsmith.test.gamenight;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class FirstTimeLogIn extends AppCompatActivity {
    private static final String TAG = FirstTimeLogIn.class.getSimpleName();
    private PersonDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_log_in);

    }

    public void savePerson(View view) {

        EditText editText = (EditText) findViewById(R.id.first_name);
        final String firstName = editText.getText().toString();
        editText = (EditText) findViewById(R.id.last_name);
        final String lastName = editText.getText().toString();

        new AsyncTask<Context, Void, Void>() {
            @Override
            protected Void doInBackground(Context... context) {
                datasource = new PersonDataSource(context[0]);
                datasource.open();

                datasource.createPerson(firstName,lastName);
                finish();
                return null;
            }


        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this);
    }


}
