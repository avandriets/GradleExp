package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.androidjokelibrary.JokeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {

    private final String JOKE_FRAGMENT_TAG = "FLTAG";
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                StartJokeActivity();
            }
        });

        requestNewInterstitial();

        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.container, new MainActivityFragment(), JOKE_FRAGMENT_TAG).commit();

    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
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

    public void StartJokeActivity(){

        EndpointsAsyncTask task = new EndpointsAsyncTask();
        task.setListener(new EndpointsAsyncTask.EndpointsGetTaskListener() {
            @Override
            public void onComplete(String jsonString, Exception e) {
                MainActivityFragment ff = (MainActivityFragment)getSupportFragmentManager().findFragmentByTag(JOKE_FRAGMENT_TAG);
                ff.showProgress(false);

                Intent JokeIntent = new Intent(MainActivity.this, JokeActivity.class);
                JokeIntent.putExtra(JokeActivity.JokeKey,jsonString);
                startActivity(JokeIntent);

            }
        }).execute();
    }

    public void tellJoke(View view){

        MainActivityFragment ff = (MainActivityFragment)getSupportFragmentManager().findFragmentByTag(JOKE_FRAGMENT_TAG);
        ff.showProgress(true);

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            StartJokeActivity();
        }

    }

}
