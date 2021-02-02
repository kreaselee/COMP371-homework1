package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private Button button_start;

    private static final String api_url="http://madlibz.herokuapp.com/api/random";
    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_start = findViewById(R.id.button_start);

        // add a click listener for our button
        button_start.setOnClickListener(v -> {
            launchNextActivity(v);
        });
    }

    private void launchNextActivity(View v) {
        // when the button is clicked
        // I want to send a get request to the API
        // add the data received from the response to the intent
        // send it to third activity to be displayed

        // set the header because of the api endpoint
        client.addHeader("Accept", "application/json");
        // send a get request to the api url
        client.get(api_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // when you get a 200 status code
                Log.d("api response", new String(responseBody));

                try {
                    JSONObject json = new JSONObject(new String(responseBody));
                    JSONArray blanks = json.getJSONArray("blanks");
                    JSONArray value = json.getJSONArray("value");

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                    // add the fields into the intent
                    /*
                    for (int i = 0; i < blanks.length(); i++){
                        intent.putExtra(blanks.get(i).toString(), blanks.get(i).toString());
                    }

                     */

                    intent.putExtra("title", json.getString("title"));
                    intent.putExtra("blanks", blanks.toString());
                    intent.putExtra("value", value.toString());

                    // convert any json data into a string to put into the intent
                    // when you receive the intent in the next activity
                    // convert it back into the json data
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // when you get a 400-499 status code
                Log.e("api error", new String(responseBody));
            }
        });


    }
}