package com.nicdsmith.test.gamenight;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("mainactivity on create", "onCreate: " + this);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0){
            if(resultCode == RESULT_OK) {
                //generates a temporary event with the information passes from the other activity
                Event tempevent = new Event(data.getStringExtra("EXTRA_EVENTTITLE"), data.getStringExtra("EXTRA_EVENTDESC"));

                //adds the new even to the dataset
                mAdapter.appendToDataSet(tempevent);
            }
        }
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
