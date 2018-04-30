package com.example.ninja.chatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextInputLayout disptxt , emailtxt , passtxt;
    Button signup_btn;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = findViewById(R.id.reg_tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register Here");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        disptxt = findViewById(R.id.sign_up_disp_name);
        emailtxt = findViewById(R.id.sign_up_email);
        passtxt = findViewById(R.id.sign_up_password);
        signup_btn = findViewById(R.id.signup_btn);
        mAuth = FirebaseAuth.getInstance();



        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String display_name = disptxt.getEditText().getText().toString();
                String email = emailtxt.getEditText().getText().toString();
                String password = passtxt.getEditText().getText().toString();
                signup(display_name,email,password);
            }
        });

    }

    private void signup(final String display_name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            // Write a message to the database
                            database = FirebaseDatabase.getInstance();
                            myRef = database.getReference().child(uid).child("Users").child("name");
                            myRef.setValue(display_name);
                            gotoHome();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void gotoHome() {
        Intent i = new Intent(SignUpActivity.this,HomeActivity.class);
        startActivity(i);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }
}
