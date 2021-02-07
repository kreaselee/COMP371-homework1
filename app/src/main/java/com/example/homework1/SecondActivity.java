package com.example.homework1;

import android.content.Intent;
import android.graphics.Typeface;
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

public class SecondActivity extends AppCompatActivity {

    private TextView textView_title;
    private LinearLayout linearLayout_blanks;
    private JSONArray blanks;
    private Button button_generate;
    private String values;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // look up views by ids
        textView_title = findViewById(R.id.textView_title);
        linearLayout_blanks = findViewById(R.id.linearLayout_blanks);
        button_generate = findViewById(R.id.button_generate);

        // extract intent extras info
        Intent intent = getIntent();
        textView_title.setText(intent.getStringExtra("title"));
        String blanksArray = intent.getStringExtra("blanks");
        values = intent.getStringExtra("values");

        // for each blank, create an EditText for the input and TextView for the category
        try {
            blanks = new JSONArray(blanksArray);
            for (int i = 0; i < blanks.length(); i++){
                EditText editText = new EditText(this);
                linearLayout_blanks.addView(editText);
                TextView textView = new TextView(this);
                textView.setText(blanks.get(i).toString());
                linearLayout_blanks.addView(textView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // add a click listener for the generate button
        button_generate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // handle what happens after clicked
                launchNextActivity(v);
            }
        });

    }

    private void launchNextActivity(View v) {

        // create a list to hold all EditTexts in the linear layout
        ArrayList<EditText> editTextList = new ArrayList<EditText>();

        for (int i = 0; i < linearLayout_blanks.getChildCount(); i++) {
            if (linearLayout_blanks.getChildAt(i) instanceof EditText)
                editTextList.add((EditText) linearLayout_blanks.getChildAt(i));
        }

        // create a list for all inputs converted to a string and trimmed
        ArrayList<String> inputList = new ArrayList<String>();

        // to determine if all fields have been filled out
        Boolean allFieldsComplete = true;

        // if no input, mark allFieldsComplete as false
        // otherwise, keep true and add element to inputList
        for (int i = 0; i < editTextList.size(); i++) {
            String input = editTextList.get(i).getText().toString().trim();
            if (input.matches("")) {
                allFieldsComplete = false;
            }
            else {
                inputList.add(input);
            }
        }

        // move onto the next activity if all fields have been filled out
        // otherwise, show warning toast
        if (allFieldsComplete) {
            Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);

            intent.putExtra("title", textView_title.getText());
            intent.putExtra("inputs", inputList);
            intent.putExtra("values", values);

            startActivity(intent);
        }
        else {
            // create a toast with a warning message for missing fields
            Toast toast = Toast.makeText(this, R.string.toast_missing, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
