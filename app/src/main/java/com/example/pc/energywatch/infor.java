package com.example.pc.energywatch;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class infor extends AppCompatActivity {
    Button logout_btn,change_btn,delete_btn,us_btn;
    TextView id,name;
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor);

        logout_btn= (Button) findViewById(R.id.logout);
        change_btn=(Button) findViewById(R.id.change_pass);
        delete_btn =(Button) findViewById(R.id.delete);
        us_btn=(Button) findViewById(R.id.us);
        id= (TextView) findViewById(R.id.id);
        name = (TextView) findViewById(R.id.name);

        auth= FirebaseAuth.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();

        name.setText(user.getUid());
        id.setText(user.getEmail());

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(infor.this, login.class));
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user !=null)
                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(infor.this, "Account has been deleted",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(infor.this,"Failed to delete your account",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });


        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        us_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_about_us();
            }
        });
    }
    private void dialog_about_us(){
        Dialog dialog= new Dialog(this);
        dialog.setContentView(R.layout.about_us);
        dialog.show();
    }
}
