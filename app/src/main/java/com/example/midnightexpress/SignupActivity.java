package com.example.midnightexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//AKA SIGN UP PAGE
public class SignupActivity extends AppCompatActivity {

    EditText emailId;
    EditText pass;
    Button toHome;
    FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.id_email);
        pass = findViewById(R.id.id_pass);
        toHome = findViewById(R.id.id_enter);


        toHome.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(SignupActivity.this, "Enter an email and password!", Toast.LENGTH_SHORT).show();
                }
                else if(!email.isEmpty() && !password.isEmpty())
                {
                    Log.d("TAG", "inside!!!");
                    mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(!task.isSuccessful())
                            {
                                Log.d("MESSAGE_E", String.valueOf(task.getException()));
                                //Log.d("MESSAGE_R", String.valueOf(task.getResult()));
                                Toast.makeText(SignupActivity.this, "Password needs to be more than 6 symbols long!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                startActivity(new Intent(SignupActivity.this, CartActivity.class));
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(SignupActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    public void goToMain (View view){
        Intent intent = new Intent (SignupActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void goToOrder (View view){
        Intent i = new Intent (SignupActivity.this, CartActivity.class);
        startActivity(i);
    }
}
