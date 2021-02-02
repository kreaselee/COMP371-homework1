package com.example.homework1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

public class SecondActivity extends AppCompatActivity {

    private TextView textView_title;
    private LinearLayout linearLayout_blanks;
    private JSONArray blanks;
    private Button button_generate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView_title = findViewById(R.id.textView_title);
        linearLayout_blanks = findViewById(R.id.linearLayout_blanks);
        button_generate = findViewById(R.id.button_generate);

        Intent intent = getIntent();
        textView_title.setText(intent.getStringExtra("title"));
        String jsonArray = intent.getStringExtra("blanks");
        try {
            blanks = new JSONArray(jsonArray);
            for (int i = 0; i < blanks.length(); i++){
                EditText editText = new EditText(this);
                linearLayout_blanks.addView(editText);
                TextView textView = new TextView(this);
                textView.setText(blanks.get(i).toString());
                // textView.setId
                linearLayout_blanks.addView(textView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
