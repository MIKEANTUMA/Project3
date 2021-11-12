package com.example.project3.levels;




import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project3.Home;
import com.example.project3.R;
import com.example.project3.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class easyLevel2 extends AppCompatActivity {
    final String name = "easyLevel2";
    MyStrtDrggngLstnr mStrtDrg;
    MyEndDrgLstnr mEndDrg;
    Button drop1;
    Button drop2;
    Button drop3;
    Button up;
    Button down;
    Button right;
    Button left;
    Button play;
    ImageView ball;
    int s;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_level2);



        mStrtDrg=new MyStrtDrggngLstnr();
        mEndDrg=new MyEndDrgLstnr();

        up = findViewById(R.id.btn_up);
        up.setOnLongClickListener(mStrtDrg);
        down = findViewById(R.id.btn_down);
        down.setOnLongClickListener(mStrtDrg);
        left = findViewById(R.id.btn_left);
        left.setOnLongClickListener(mStrtDrg);
        right = findViewById(R.id.btn_right);
        right.setOnLongClickListener(mStrtDrg);

        drop1 = findViewById(R.id.drop_one);
        drop1.setOnDragListener(mEndDrg);
        drop2 = findViewById(R.id.drop_two);
        drop2.setOnDragListener(mEndDrg);
        drop3 = findViewById(R.id.drop_three);
        drop3.setOnDragListener(mEndDrg);
        play = findViewById(R.id.play);




        Log.d("KEY","Get user was called");
        // Log.d("USERAAAAAAAAA",user.toString());

        ball = findViewById(R.id.ball);
        play.setOnClickListener(v -> {
            if(drop1.getBackground() == right.getBackground() && drop2.getBackground() == up.getBackground() && drop3.getBackground() == right.getBackground())
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Correct!").create().show();
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                Animation iv2anim = AnimationUtils.loadAnimation(this, R.anim.level2easy_anim);
                ball.startAnimation(iv2anim);
                iv2anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        UpdateScore(10);
                        UpdateAttempt();
                        startActivity(new Intent(easyLevel2.this, Home.class));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Wrong, try again").create().show();
                UpdateAttempt();
                Toast.makeText(this, "Wrong, try again", Toast.LENGTH_SHORT).show();
            }

        });
    }


    private class MyStrtDrggngLstnr implements View.OnLongClickListener{

        @Override
        public boolean onLongClick(View v) {
            WithDragShadow shadow= new WithDragShadow(v);
            ClipData data = ClipData.newPlainText("","");
            v.startDrag(data,shadow,v,0);
            return false;
        }
    }

    private class MyEndDrgLstnr implements View.OnDragListener{

        @Override
        public boolean onDrag(View v, DragEvent event) {
            if(event.getAction()==DragEvent.ACTION_DROP){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    v.setBackground(((Button) event.getLocalState()).getBackground());
                }
            }
            return true;
        }
    }

    private class WithDragShadow extends View.DragShadowBuilder{
        public WithDragShadow(View v){super(v);}

        @Override public void onDrawShadow(Canvas canvas){ super.onDrawShadow(canvas);}

        @Override
        public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint){
            super.onProvideShadowMetrics(outShadowSize, outShadowTouchPoint);
        }

    }

    private void UpdateAttempt(){
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
        databaseReference = firebaseDatabase.getReference("user").child(mAuth.getCurrentUser().getUid());
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("KEY", String.valueOf(task.getResult().getValue(User.class).getScore()));
                    Integer at = task.getResult().getValue(User.class).getAttempt(name) + 1;
                    databaseReference.child("attempt").child(name).setValue(at);
                }
            }
        });
    }

    private void UpdateScore(final int score) {
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
        databaseReference = firebaseDatabase.getReference("user").child(mAuth.getCurrentUser().getUid());
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("KEY", String.valueOf(task.getResult().getValue(User.class).getScore()));
                    int sc = task.getResult().getValue(User.class).getScore() + 10;
                    databaseReference.child("score").setValue(sc);
                }
            }
        });
    }
}