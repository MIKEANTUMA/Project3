package com.example.project3;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project3.levels.easyLevel1;
import com.example.project3.levels.easyLevel2;
import com.example.project3.levels.easylevel3;
import com.example.project3.levels.hardLevel1;
import com.example.project3.levels.hardLevel2;
import com.example.project3.levels.hardLevel3;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
public class Home extends AppCompatActivity implements View.OnClickListener {
Button btn_easy_1;
Button btn_easy_2;
Button btn_easy_3;
Button btn_hard_1;
Button btn_hard_2;
Button btn_hard_3;

MediaPlayer music;
Button logout;
TextView welcomeText;
TextView scoreText;
HashMap<String,Object> u;
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        scoreText = findViewById(R.id.score);
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

        logout = findViewById(R.id.btn_logout);
        logout.setOnClickListener(this);
        new Thread(new Runnable() {
            public void run() {
                getdata();
            }
        }).start();
        music = MediaPlayer.create(this, R.raw.music);
    }
    @Override
    protected void onStart() {
        super.onStart();
        getdata();
        music.start();
        music.setLooping(true);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.easy_level_1:
                getdata();
                startActivity(new Intent(this, easyLevel1.class));
                break;
            case R.id.easy_level_2:
                startActivity(new Intent(this, easyLevel2.class));
                break;
            case R.id.easy_level_3:
                startActivity(new Intent(this, easylevel3.class));
                break;
            case R.id.hard_level_1:
                startActivity(new Intent(this, hardLevel1.class));
                break;
            case R.id.hard_level_2:
                startActivity(new Intent(this, hardLevel2.class));
                break;
            case R.id.hard_level_3:
                startActivity(new Intent(this, hardLevel3.class));
                break;
            case R.id.btn_logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Login.class));
                break;

        }
    }


    private void getdata() {
        FirebaseDatabase firebaseDatabase;
        // creating a variable for our
        // Database Reference for Firebase.
        DatabaseReference databaseReference;
        // below line is used to get the instance
        // of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();
        // below line is used to get
        // reference for our database.
        databaseReference = firebaseDatabase.getReference("user").child(mAuth.getCurrentUser().getUid());
        // calling add value event listener method
        // for getting the values from database.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                welcomeText.setText("Welcome, "+ snapshot.getValue(User.class).getEmail());
                scoreText.setText("Score: "+snapshot.getValue(User.class).getScore());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Log.d("KEY","Fail to get data.");
            }
        });
    }
}