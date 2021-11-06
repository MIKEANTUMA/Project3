package com.example.project3;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class level1Animations extends AppCompatActivity {
    User user;
    MyStrtDrggngLstnr mStrtDrg;
    MyEndDrgLstnr mEndDrg;
    Button drop1;
    Button drop2;
    Button drop3;
    Button drop4;
    Button drop5;
    Button up;
    Button down;
    Button right;
    Button left;
    Button play;
    ImageView ball;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_one_animations);



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
        drop4 = findViewById(R.id.drop_four);
        drop4.setOnDragListener(mEndDrg);
        drop5 = findViewById(R.id.drop_five);
        drop5.setOnDragListener(mEndDrg);
        play = findViewById(R.id.play);

        FirebaseDatabase firebaseDatabase;

        // creating a variable for our
        // Database Reference for Firebase.

        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get
        // reference for our database.
        databaseReference = firebaseDatabase.getReference("Data");

        // initializing our object class variable.


        // calling method
        // for getting data.


        ball = findViewById(R.id.ball);
        play.setOnClickListener(v -> {
            if(drop1.getBackground() == right.getBackground() && drop2.getBackground() == down.getBackground() && drop3.getBackground() == right.getBackground() && drop4.getBackground() == up.getBackground() && drop5.getBackground() == right.getBackground())
            {
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                Animation iv2anim = AnimationUtils.loadAnimation(this, R.anim.level1easy_anim);
                ball.startAnimation(iv2anim);
                startActivity(new Intent(this, Home.class));
            }
            else{
                getdata();
                Log.d("KEY", String.valueOf(user));
                Toast.makeText(this, "Wrong, try again", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void getdata() {

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
                    user = snapshot.getValue(User.class);
                   // drop1.setText(user);
                // after getting the value we are setting
                // our value to our text view in below line.
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(level1Animations.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
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
}