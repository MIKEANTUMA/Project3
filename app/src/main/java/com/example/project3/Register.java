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

public class Register extends AppCompatActivity implements View.OnClickListener {
    EditText edit_email;
    EditText edit_password;
    RadioButton radio_parent;
    RadioButton radio_child;
    Button btn_register;
    String userType;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();


        edit_email = findViewById(R.id.et_email);
        edit_password = findViewById(R.id.et_password);
        radio_parent = findViewById(R.id.Rbtn_parent);
        radio_child = findViewById(R.id.Rbtn_child);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        radio_parent.setOnClickListener(v -> {
            boolean checked = ((RadioButton) v).isChecked();
            // Check which radiobutton was pressed
            if (checked){
                userType = "Parent";
            }
        });
        radio_child.setOnClickListener(v -> {
            boolean checked = ((RadioButton) v).isChecked();
            // Check which radiobutton was pressed
            if (checked){
                userType = "Child";
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                RegisterUser();
                break;
        }
    }
public void RegisterUser(){
    User userr = new User(edit_email.getText().toString().trim(), edit_password.getText().toString().trim(), userType);

    mAuth.createUserWithEmailAndPassword(edit_email.getText().toString().trim(), edit_password.getText().toString().trim())
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        FirebaseDatabase.getInstance().getReference("user").child(user.getUid()).setValue(userr).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(Register.this, "User added", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                        Toast.makeText(Register.this, "Authentication Successes.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this, Login.class));
                    } else {
                        String tag ="";
                        // If sign in fails, display a message to the user.
                        Log.d(tag, String.valueOf(task.getException()));
                        Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                    }
                }
            });
}


}
