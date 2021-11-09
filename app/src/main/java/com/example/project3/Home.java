package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class Home extends AppCompatActivity implements View.OnClickListener {
Button btn_easy_1;
Button btn_easy_2;
Button btn_easy_3;
Button btn_hard_1;
Button btn_hard_2;
Button btn_hard_3;
TextView welcomeText;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        welcomeText = findViewById(R.id.welcome);
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
        Bundle bundle = getIntent().getExtras();
        Object user = bundle.get("USER");
        HashMap<String,Object> u = (HashMap<String, Object>) user;
        welcomeText.setText("Welcome, "+ u.get("email"));
        Log.d("USERR",user.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.easy_level_1:
                startActivity(new Intent(this, easyLevel1.class));
                break;
            case R.id.easy_level_2:
                startActivity(new Intent(this, easyLevel2.class));
                break;
            case R.id.easy_level_3:
                startActivity(new Intent(this, easylevel3.class));
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