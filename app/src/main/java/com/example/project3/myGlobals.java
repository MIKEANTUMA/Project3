package com.example.project3;

import android.util.Log;

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

    public static void Getuser() {
        HashMap<String,Object> user;
        user = new HashMap<>();

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser userr = mAuth.getCurrentUser();
        DatabaseReference familyListReference = FirebaseDatabase.getInstance().getReference().child(userr.getUid());
        familyListReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.d("KEY", dataSnapshot.toString());
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //Log.d("KEY", ds.toString());
                    String key = (String) ds.getKey();
                    user.put(ds.getKey(),ds.getValue().toString());

                    DatabaseReference keyReference = FirebaseDatabase.getInstance().getReference().child(userr.getUid()).child(key);
                }
                Log.d("USERRRRR", user.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.d("KEY", "Read failed");
            }
        });

        //return user;
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
