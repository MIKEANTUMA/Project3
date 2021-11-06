package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity implements View.OnClickListener {
Button btn_easy_1;
Button btn_easy_2;
Button btn_easy_3;
Button btn_hard_1;
Button btn_hard_2;
Button btn_hard_3;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_easy_1 = findViewById(R.id.easy_level_1);
        btn_easy_2 = findViewById(R.id.easy_level_2);
        btn_easy_3 = findViewById(R.id.easy_level_3);
        btn_hard_1 = findViewById(R.id.hard_level_1);
        btn_hard_2 = findViewById(R.id.hard_level_2);
        btn_hard_3 = findViewById(R.id.hard_level_3);
        btn_easy_1.setOnClickListener(this);
        btn_easy_2.setOnClickListener(this);
        btn_easy_3.setOnClickListener(this);
        btn_hard_1.setOnClickListener(this);
        btn_hard_2.setOnClickListener(this);
        btn_hard_3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.easy_level_1:
                startActivity(new Intent(this, level1Animations.class));
                break;
            case R.id.easy_level_2:
                break;
            case R.id.easy_level_3:
                break;
            case R.id.hard_level_1:
                break;
            case R.id.hard_level_2:
                break;
            case R.id.hard_level_3:
                break;
        }
    }
}