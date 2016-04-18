package com.example.androidjokelibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    public static final String JokeKey = "Joke_Key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        Intent intent = getIntent();
        if(intent.hasExtra(JokeKey)) {
            TextView tvJoke = (TextView) this.findViewById(R.id.textViewJOKE);
            tvJoke.setText(intent.getStringExtra(JokeKey));
        }
    }
}
