package com.example.authlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signupact extends AppCompatActivity {
    private Button signup;
    private EditText username, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupact);
        signup = findViewById(R.id.signup2);
        username = findViewById(R.id.username2);
        password = findViewById(R.id.password2);
        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser();
            }
        });

    }


    private void RegisterUser() {
        String un = username.getText().toString();
        String pw = password.getText().toString();
        if (!(TextUtils.isEmpty(un) || TextUtils.isEmpty(pw) || !Patterns.EMAIL_ADDRESS.matcher(un).matches() || (pw.length() < 6))) {
            CreateAccount(un, pw);
        } else {
            Toast.makeText(this, "One/Both Of The Fields Is/Are Empty", Toast.LENGTH_LONG).show();
            if (TextUtils.isEmpty(un)) {
                username.setError("Compulsory Field : Email ");
                username.requestFocus();
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(un).matches()) {
                username.setError("Enter A Valid Email Address!!");
                username.requestFocus();
            }

            if (pw.length() < 6) {
                if (TextUtils.isEmpty(pw)) {
                    password.setError("Compulsory Field : Password ");
                    password.requestFocus();
                } else {
                    password.setError("Minimum length of password is 6");
                    password.requestFocus();
                }
            }
        }


    }

    public void CreateAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Registration Unsuccessful", Toast.LENGTH_LONG).show();
                            String exception = task.getException().toString();
                            Toast.makeText(getApplicationContext(), exception, Toast.LENGTH_LONG).show();

                        }

                    }
                });
    }
}


