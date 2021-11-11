package com.example.project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
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

import java.util.HashMap;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
// ...
// Initialize Firebase Auth
EditText edit_email;
EditText edit_password;
RadioButton radio_parent;
RadioButton radio_child;
Button btn_login;
Button btn_register;
String userType ="n";
String s;
HashMap<String,Object> user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        edit_email = findViewById(R.id.et_login_email);
        edit_password = findViewById(R.id.et_login_password);
        btn_register = findViewById(R.id.btn_login_register);
        btn_register.setOnClickListener(this);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        radio_parent = findViewById(R.id.Rbtn_login_parent);
        radio_child = findViewById(R.id.Rbtn_login_child);
        radio_parent.setOnClickListener(this);
        radio_child.setOnClickListener(this);
       // Log.d("KEY", userType);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_login_register:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.btn_login:
                if(userType == "n"){
                    Toast.makeText(this, "Please specify if parent or child", Toast.LENGTH_SHORT).show();
                }else {
                        Login();
                    }
                break;
            case R.id.Rbtn_login_parent:

                    userType = "Parent";
                    Log.d("KEY", userType);
                    break;
            case R.id.Rbtn_login_child:
                    userType = "Child";
                     Log.d("KEY", userType);
                    break;
        }
    }

    public void Login(){
        mAuth.signInWithEmailAndPassword(edit_email.getText().toString().trim(), edit_password.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Login.this, "Authentication Successes.", Toast.LENGTH_SHORT).show();

                            Log.d("KEY","get data was called");

                            if(userType == "Child")
                            {
                                startActivity(new Intent(Login.this, Home.class));
                            }

                            if(userType == "Parent")
                            {
                                startActivity(new Intent(Login.this, ParentPortal.class));
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            Log.e("ERROR", task.getException().toString());
                        }
                    }
                });
        //Log.d("LETS HOPE THIS WORKS", user.toString());
    }

//    private void getdata() {
//        FirebaseDatabase firebaseDatabase;
//        // creating a variable for our
//        // Database Reference for Firebase.
//        DatabaseReference databaseReference;
//        // below line is used to get the instance
//        // of our Firebase database.
//        firebaseDatabase = FirebaseDatabase.getInstance();
//
//        // below line is used to get
//        // reference for our database.
//        databaseReference = firebaseDatabase.getReference("user").child(mAuth.getCurrentUser().getUid());
//        // calling add value event listener method
//        // for getting the values from database.
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                // this method is call to get the realtime
//                // updates in the data.
//                // this method is called when the data is
//                // changed in our Firebase console.
//                // below line is for getting the data from
//                // snapshot of our database.
//                snapshot.getValue(User.class);
//                 user = (HashMap<String, Object>) snapshot.getValue();
//
//                Log.d("KEY", user.toString());
//                Intent intent = new Intent(Login.this, Home.class);
//                intent.putExtra("USER", user);
//                startActivity(intent);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // calling on cancelled method when we receive
//                // any error or we are not able to get the data.
//                Log.d("KEY","Fail to get data.");
//            }
//        });
//
//    }
}