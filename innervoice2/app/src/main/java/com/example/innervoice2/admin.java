package com.example.innervoice2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

public class admin extends AppCompatActivity {

    EditText amail,apassword;
    Button alogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        amail=findViewById(R.id.adminmail);
        apassword=findViewById(R.id.adminpassword);
        alogin=findViewById(R.id.alogin);
        alogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amail.getText().toString().equals("sahithicharumathi@gmail.com")  &&
                apassword.getText().toString().equals("abc123")){
                    startActivity(new Intent(getApplicationContext(),add.class));
                }
                else{
                    Toast.makeText(admin.this, "Invalid email and password",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}