package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.androidjokelibrary.JokeActivity;

public class MainActivity extends AppCompatActivity {

    private final String JOKE_FRAGMENT_TAG = "FLTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.container, new MainActivityFragment(), JOKE_FRAGMENT_TAG).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void StartJokeActivity() {

        EndpointsAsyncTask task = new EndpointsAsyncTask();
        task.setListener(new EndpointsAsyncTask.EndpointsGetTaskListener() {
            @Override
            public void onComplete(String jsonString, Exception e) {
                MainActivityFragment ff = (MainActivityFragment) getSupportFragmentManager().findFragmentByTag(JOKE_FRAGMENT_TAG);
                ff.showProgress(false);

                Intent JokeIntent = new Intent(MainActivity.this, JokeActivity.class);
                JokeIntent.putExtra(JokeActivity.JokeKey, jsonString);
                startActivity(JokeIntent);

            }
        }).execute();
    }

    public void tellJoke(View view) {

        MainActivityFragment ff = (MainActivityFragment) getSupportFragmentManager().findFragmentByTag(JOKE_FRAGMENT_TAG);
        ff.showProgress(true);

        StartJokeActivity();
    }

}
