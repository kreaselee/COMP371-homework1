package com.example.homework1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

public class ThirdActivity extends AppCompatActivity {

    private TextView textView_storyTitle;
    private TextView textView_story;
    private Button button_goHome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        // ook up views by ids
        textView_storyTitle = findViewById(R.id.textView_storyTitle);
        textView_story = findViewById(R.id.textView_story);
        button_goHome = findViewById(R.id.button_goHome);

        // extract intent extras info
        Intent intent = getIntent();
        textView_storyTitle.setText(intent.getStringExtra("title"));
        ArrayList<String> inputList = intent.getStringArrayListExtra("inputs");

        String values = intent.getStringExtra("values");

        // add each string inside JSONArray to a list
        ArrayList<String> valuesList = new ArrayList<String>();
        try {
            JSONArray valuesArray = new JSONArray(values);
            for (int i = 0; i < valuesArray.length() ; i++){
                valuesList.add(valuesArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // construct story by alternating between values and inputs
        String story = "";
        for (int i = 0; i < valuesList.size()-1; i++) {
            story += valuesList.get(i);
            if (i < inputList.size()) {
                story += inputList.get(i);
            }
        }

        // set textView to story
        textView_story.setText(story);

        // add a click listener for the home button
        button_goHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // handle what happens after clicked
                launchNextActivity(v);
            }
        });

    }

    private void launchNextActivity(View v) {
        // go back to the home page
        Intent intent = new Intent(ThirdActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
