package com.example.innervoice2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registration extends AppCompatActivity {
    EditText umail,upasssword;
    Button submit;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        umail=findViewById(R.id.regmail);
        upasssword=findViewById(R.id.regpassword);
        submit=findViewById(R.id.uregister);
        mAuth = FirebaseAuth.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }


    private void register() {
        String username=umail.getText().toString().trim();
        String password=upasssword.getText().toString().trim();
        if(username.isEmpty()){
            umail.setError("please fill email");
            return;
        }
        if(password.isEmpty()){
            upasssword.setError("please fill password");
            return;
        }
        if(password.length() < 6){
            upasssword.setError("password should be more than 6 characters");
            return;
        }
        mAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    finish();
                    startActivity(new Intent(getApplicationContext(),sample.class));
                }
            }
        });
    }
}