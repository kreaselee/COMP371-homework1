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

        textView_title = findViewById(R.id.textView_title);
        linearLayout_blanks = findViewById(R.id.linearLayout_blanks);
        button_generate = findViewById(R.id.button_generate);

        Intent intent = getIntent();
        textView_title.setText(intent.getStringExtra("title"));
        String blanksArray = intent.getStringExtra("blanks");
        try {
            blanks = new JSONArray(blanksArray);
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
        values = intent.getStringExtra("values");


        button_generate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // handle what happens after I click
                launchNextActivity(v);
            }
        });

    }

    private void launchNextActivity(View v) {
        ArrayList<EditText> editTextList = new ArrayList<EditText>();

        for (int i = 0; i < linearLayout_blanks.getChildCount(); i++) {
            if (linearLayout_blanks.getChildAt(i) instanceof EditText)
                editTextList.add((EditText) linearLayout_blanks.getChildAt(i));
        }

        ArrayList<String> inputList = new ArrayList<String>();
        Boolean allFieldsComplete = true;

        for (int i = 0; i < editTextList.size(); i++) {
            String input = editTextList.get(i).getText().toString().trim();
            inputList.add(input);
            if (input.matches("")) {
                allFieldsComplete = false;
            }
        }

        if (allFieldsComplete) {
            Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);

            intent.putExtra("title", textView_title.getText());
            intent.putExtra("inputs", inputList);
            intent.putExtra("values", values);

            // System.out.println(inputList);

            startActivity(intent);
        }
        else {
            // create a toast with a warning message for missing fields
            Toast toast = Toast.makeText(this, R.string.toast_missing, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
