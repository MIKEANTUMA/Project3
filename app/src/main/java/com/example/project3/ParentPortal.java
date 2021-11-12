package com.example.project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.Dictionary;
import java.util.HashMap;

public class ParentPortal extends AppCompatActivity {
    public HashMap<String, Integer> attempt = new HashMap<>();
    EditText childemail;
    GraphView bar_Graph;
    Button submit;
    String childEmail;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_portal);
        MediaPlayer music = MediaPlayer.create(this, R.raw.music);
        music.start();
        childemail = findViewById(R.id.child_email);
        findViewById(R.id.childemailsubmit).setOnClickListener(v -> {
            Log.d("KEY","get child attempts was called");
            childEmail = childemail.getText().toString();
            getChildAttempt();
        });
        bar_Graph = (GraphView) findViewById(R.id.bar_graph);
    }
    private void getChildAttempt(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase;
        // creating a variable for our
        // Database Reference for Firebase.
        DatabaseReference databaseReference;
        // below line is used to get the instance
        // of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();
        // below line is used to get
        // reference for our database.
        databaseReference = firebaseDatabase.getReference().child("user");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    Log.d("KEY",childemail.getText().toString().trim());
                    email = user.getEmail();
                    Log.d("childEmail", childEmail);
                    Log.d("email", email);
                    HashMap<String,Integer> map = new HashMap<>();
                    if(email.equals(childEmail)){
                        Log.d("YEOOOOOOOOOOOOO", user.getEmail());
                        BarGraphSeries<DataPoint> barGraph_Data = new BarGraphSeries<>(new DataPoint[] {
                                new DataPoint(1, user.getAttempt("easyLevel1")),
                                new DataPoint(2, user.getAttempt("easyLevel2")),
                                new DataPoint(3, user.getAttempt("easyLevel3")),
                                new DataPoint(4, user.getAttempt("hardLevel1")),
                                new DataPoint(5, user.getAttempt("hardLevel2")),
                                new DataPoint(6, user.getAttempt("hardLevel3"))
                        });
                        Log.d("KEY",user.getAttempt("easyLevel1").toString());
                        bar_Graph.addSeries(barGraph_Data);
                        barGraph_Data.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                            @Override
                            public int get(DataPoint info) {
                                return Color.rgb((int) info.getX()*255/4, (int) Math.abs(info.getY()*255/6), 100);
                            }
                        });
                        barGraph_Data.setSpacing(10);
                        barGraph_Data.setDrawValuesOnTop(true);
                        barGraph_Data.setValuesOnTopColor(Color.RED);
                    }
                    else{
                        Log.d("KEYYY", "email not found");
                    }
                    //Log.d("KEY",user.getEmail());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


}