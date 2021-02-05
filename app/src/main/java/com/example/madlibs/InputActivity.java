package com.example.madlibs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InputActivity extends AppCompatActivity {

    private String title;
    private JSONArray blanks;
    private JSONArray text;
    private TextView textView_title;
    private LinearLayout linearLayout_fields;
    private ArrayList<TextInputLayout> user_inputs= new ArrayList<>();
    private Button button_generate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        textView_title = findViewById(R.id.textView_title);
        linearLayout_fields = findViewById(R.id.linearLayout_fields);
        button_generate = findViewById(R.id.button_generate);

        Intent intent = getIntent();
        JSONObject receivedMessage;
        try {
            // unpack the string to json
            receivedMessage = new JSONObject(intent.getStringExtra("madlibs"));
            title = receivedMessage.getString("title");
            blanks = receivedMessage.getJSONArray("blanks");
            text = receivedMessage.getJSONArray("value");
            generate_fields();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        textView_title.setText(title);

        button_generate.setOnClickListener(v -> {
            try {
                generate(v);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    // generate fields for each blank in textInputLayout
    private void generate_fields() throws JSONException {
        for (int i = 0; i < blanks.length(); i++){
            // create textinputlayout dynamatically
            TextInputLayout textInput = new TextInputLayout(this);
            LinearLayout.LayoutParams inputLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            inputLayoutParams.setMargins(8,8,8,8);
            TextInputEditText editText = new TextInputEditText(textInput.getContext());
            LinearLayout.LayoutParams editTextLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            editText.setLayoutParams(editTextLayoutParams);
            textInput.setLayoutParams(inputLayoutParams);
            textInput.addView(editText);
            textInput.setHint(blanks.getString(i)); // set hint

            linearLayout_fields.addView(textInput);
            user_inputs.add(textInput);
        }
    }

    // generate full text
    // check user input, not empty
    private void generate(View v) throws JSONException {
        String fulltext = text.getString(0);
        for (int i = 0; i < blanks.length(); i++){
            // remove leading trailing spaces
            String input = user_inputs.get(i).getEditText().getText().toString().trim();
            if (input.equals("")){
                Toast toast = Toast.makeText(InputActivity.this, R.string.miss_input, Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            fulltext = fulltext.concat(input);
            fulltext = fulltext.concat(text.getString(i + 1));
        }

        // send fulltext to result activity
        Intent intent = new Intent(InputActivity.this, ResultActivity.class);
        intent.putExtra("fulltext", fulltext);
        startActivity(intent);
    }

}
