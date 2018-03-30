package com.example.pc.energywatch;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class regis extends AppCompatActivity {
    Button regis;
    EditText r_mail, r_pass,r_pass1;
    static String account, password ;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);
        anhxa1();
        auth = FirebaseAuth.getInstance();

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
                //Intent log_screen = new Intent(regis.this, login.class);
                //log_screen.putExtra(account,r_mail.getText().toString().trim());
                //log_screen.putExtra(password,r_pass.getText().toString().trim());
                //startActivity(log_screen);

            }
        });
    }
    private  void registerUser(){
        String acc= r_mail.getText().toString().trim();
        String passcode= r_pass.getText().toString().trim();

        if(TextUtils.isEmpty(acc)){
            Toast.makeText(com.example.pc.energywatch.regis.this, "Please enter Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(passcode)){
            Toast.makeText(com.example.pc.energywatch.regis.this, "Please enter Password",Toast.LENGTH_SHORT).show();
            return;
        }

        if (passcode.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        //progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(acc, passcode)
                .addOnCompleteListener(regis.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(regis.this, "Sign up successly, congratulation :)", Toast.LENGTH_SHORT).show();
                            Intent regis_scr = new Intent(regis.this, login.class);
                            startActivity(regis_scr);
                        } else {

                            Toast.makeText(com.example.pc.energywatch.regis.this, "Opps! Sign up fail, please try again :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
    private void anhxa1(){
        //khai bao cao bien
        regis = (Button) findViewById(R.id.regis_regisbutton);
        r_mail =(EditText) findViewById(R.id.regis_mail);
        r_pass =(EditText) findViewById(R.id.regis_pass);
        r_pass1 =(EditText) findViewById(R.id.regis_pass1);
    }
}
