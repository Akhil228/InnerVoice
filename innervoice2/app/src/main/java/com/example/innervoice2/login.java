package com.example.innervoice2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    Button login;
    EditText l_mail, l_password;
    FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.ulogin);
        l_password = findViewById(R.id.loginpassword);
        l_mail = findViewById(R.id.loginmail);
        mauth=FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userlogin();
            }
        });
    }

    private void userlogin() {
        String usermaill=l_mail.getText().toString().trim();
        String password=l_password.getText().toString().trim();
        mauth.signInWithEmailAndPassword(usermaill,password).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(),sample.class));
                        }
                        else{
                            Toast.makeText(login.this, "invalid login", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}



