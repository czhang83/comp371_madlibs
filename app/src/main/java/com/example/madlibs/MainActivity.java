package com.example.madlibs;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;


public class MainActivity extends AppCompatActivity {

    private Button button_start;

    // Databse API
    private String api_url = "http://madlibz.herokuapp.com/api/random";
    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_start = findViewById(R.id.button_start);

        button_start.setOnClickListener(v -> launchNextActivity(v));
    }

    // create a new intent from main to input
    // get a matlib from API
    public void launchNextActivity(View view){
        // send a get request to the api url
        client.get(api_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // when get a 200 status code
                Log.d("api response", new String(responseBody));

                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                // send the response in string form
                intent.putExtra("madlibs", new String(responseBody));
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // when get 400
                Log.e("api error", new String(responseBody));
                //pop up error message
                Toast toast = Toast.makeText(MainActivity.this, R.string.api_error, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}