package com.example.project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

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
String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();


        edit_email = findViewById(R.id.et_login_email);
        edit_password = findViewById(R.id.et_login_password);
        radio_parent = findViewById(R.id.Rbtn_login_parent);
        btn_register = findViewById(R.id.btn_login_register);
        btn_register.setOnClickListener(this);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

       // if(radio_parent.isChecked()) userType = "Parent";
       // if(radio_child.isChecked()) userType = "Child";





    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_register:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.btn_login:
                Login();
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
                            startActivity(new Intent(Login.this, Home.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}