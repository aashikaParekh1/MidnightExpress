
package com.example.midnightexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {


    EditText emailId;
    EditText pass;
    Button toHomeL;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.id_email);
        pass = findViewById(R.id.id_pass);
        toHomeL = findViewById(R.id.id_enter);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                Log.d("CHECKER", String.valueOf(mFirebaseUser));
                if(mFirebaseUser != null)
                {
                    Toast.makeText(LoginActivity.this, "You're logged in!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, CartActivity.class);
                    startActivity(i);
                }

/*
                else{
                    Toast.makeText(LoginActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
                    Log.d("CHECKER", "is it working??");
                }

                 */


            }
        };


        toHomeL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailId.getText().toString();
                String password = pass.getText().toString();

                if(email.isEmpty())
                {
                    emailId.setError("Please enter a valid email");
                    emailId.requestFocus();
                }
                else if(password.isEmpty())
                {
                    pass.setError("Please enter a password");
                    pass.requestFocus();
                }
                else if(email.isEmpty() && password.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Enter an email and password!", Toast.LENGTH_SHORT).show();
                }
                else if(!email.isEmpty() && !password.isEmpty())
                {
                    mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(!task.isSuccessful())
                            {
                                Toast.makeText(LoginActivity.this, "Login Unsuccessful! Please try again.", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Intent intToHome = new Intent(LoginActivity.this, CartActivity.class);
                                startActivity(intToHome);
                            }

                        }
                    });
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();

                }

            }
        });



/*
        toSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intSignUp);
            }
        });

         */





}

    public void goToMain (View view){
        Intent intent = new Intent (LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void goToOrder (View view){
        Intent i = new Intent (LoginActivity.this, CartActivity.class);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }



}
