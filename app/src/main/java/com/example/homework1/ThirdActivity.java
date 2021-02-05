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
    private LinearLayout linearLayout_story;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        textView_storyTitle = findViewById(R.id.textView_storyTitle);
        textView_story = findViewById(R.id.textView_story);
        button_goHome = findViewById(R.id.button_goHome);

        Intent intent = getIntent();
        textView_storyTitle.setText(intent.getStringExtra("title"));
        ArrayList<String> inputList = intent.getStringArrayListExtra("inputs");

        String values = intent.getStringExtra("values");
        ArrayList<String> valuesList = new ArrayList<String>();
        String story = "";
        try {
            JSONArray valuesArray = new JSONArray(values);
            for (int i = 0; i < valuesArray.length() ; i++){
                valuesList.add(valuesArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < valuesList.size()-1; i++) {
            story += valuesList.get(i);
            if (i < inputList.size()) {
                story += inputList.get(i);
            }
        }

        // System.out.println(story);
        textView_story.setText(story);

        button_goHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // handle what happens after I click
                launchNextActivity(v);
            }
        });

    }

    private void launchNextActivity(View v) {
        Intent intent = new Intent(ThirdActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
