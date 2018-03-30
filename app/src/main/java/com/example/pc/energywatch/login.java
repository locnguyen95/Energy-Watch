package com.example.pc.energywatch;

import android.support.v7.app.AppCompatActivity;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class login extends AppCompatActivity {

    private Button login;
    private EditText mail, pass;
    private TextView forget_btn, register;
    FirebaseAuth auth;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhxa();
        auth= FirebaseAuth.getInstance();

        //kiem tra dang co nguoi dung khong
        if(auth.getCurrentUser() !=null){
            startActivity(new Intent(com.example.pc.energywatch.login.this, Main.class));
            finish();
        }

        //lay du lieu account & pass
        //Intent intent=getIntent();
        //mail.setText(intent.getStringExtra(regis.account));
        //pass.setText(intent.getStringExtra(regis.password));



        // nut bam Login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email =mail.getText().toString().trim();
                final String Passcode = pass.getText().toString().trim();
                if (TextUtils.isEmpty(Email)){
                    Toast.makeText(com.example.pc.energywatch.login.this, "Please enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(Passcode)){
                    Toast.makeText(com.example.pc.energywatch.login.this, "Please enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Passcode.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.signInWithEmailAndPassword(Email,Passcode).addOnCompleteListener(com.example.pc.energywatch.login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){

                                Toast.makeText(login.this, "fail login, your Email or password might be wrong", Toast.LENGTH_LONG).show();

                        } else {
                            Intent intent = new Intent(login.this, Main.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });


        //nut bam "register"
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regis_screen = new Intent(login.this, regis.class);
                startActivity(regis_screen);
            }
        });

        // nut bam "forget"
        forget_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forget_screen = new Intent(com.example.pc.energywatch.login.this, forget.class);
                startActivity(forget_screen);
            }
        });



    }
    private void anhxa(){
        //khai bao cao bien
        login = (Button) findViewById(R.id.login);
        mail =(EditText) findViewById(R.id.mail);
        pass =(EditText) findViewById(R.id.pass);
        forget_btn =(TextView) findViewById(R.id.forget);
        register =(TextView) findViewById(R.id.regis);
    }

}