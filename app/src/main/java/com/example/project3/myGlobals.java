package com.example.project3;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class myGlobals {
    public static HashMap<String,Object> user;

    public static HashMap<String, Object> getUser() {
        return user;
    }

    public void setUser(HashMap<String, Object> user) {
        this.user = user;
    }
    // creating a variable for
    // our Firebase Database.


    public User u;



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
        databaseReference = firebaseDatabase.getReference("user");
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
                String value = snapshot.getValue(String.class);

                Log.d("KEY", value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Log.d("KEY","Fail to get data.");
            }
        });
    }



    public static void UpdateUserScore(int s){
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseDatabase db;
        DatabaseReference myRef;
        db=FirebaseDatabase.getInstance();
        myRef=db.getReference(user.getUid());
        HashMap<String,Object> update = new HashMap<>();
        update.put("score", s);
        myRef.updateChildren(update, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                //Toast.makeText(level1Animations.this,"Score Updated",Toast.LENGTH_LONG).show();
            }
        });
    }

}
