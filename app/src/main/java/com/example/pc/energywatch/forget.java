package com.example.pc.energywatch;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class forget extends AppCompatActivity {
    Button send;
    EditText mail;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        auth = FirebaseAuth.getInstance();
        send = (Button) findViewById(R.id.send_btn);
        mail = (EditText) findViewById(R.id.input_mail);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mail.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(com.example.pc.energywatch.forget.this, "Please enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(forget.this,"We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(forget.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}