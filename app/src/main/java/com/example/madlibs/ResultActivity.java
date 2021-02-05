package com.example.madlibs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    private TextView textView_fulltext;
    private Button button_gohome;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        textView_fulltext = findViewById(R.id.textView_fulltext);
        button_gohome = findViewById(R.id.button_gohome);

        Intent intent = getIntent();
        String fulltext = intent.getStringExtra("fulltext");
        textView_fulltext.setText(fulltext);

        button_gohome.setOnClickListener(v -> {
            Intent intent1 = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(intent1);
        });
    }
}
