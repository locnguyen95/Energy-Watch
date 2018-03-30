package com.example.pc.energywatch;

import android.content.Intent;
import android.icu.util.TimeZone;
import android.media.TimedText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;



public class Main extends AppCompatActivity {

    private Button acc_btn, data_btn,test_btn;
    private TextView gio, phut, giay;
    private DatabaseReference data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        acc_btn =(Button) findViewById(R.id.account);
        data_btn = (Button) findViewById(R.id.data);
        test_btn = (Button) findViewById(R.id.test);

        gio =(TextView) findViewById(R.id.hour);
        phut= (TextView) findViewById(R.id.minute);
        giay= (TextView) findViewById(R.id.second);

        data=FirebaseDatabase.getInstance().getReference();                 //realtime database

        //data.child("gio").setValue(0);
        //data.child("phut").setValue(0);
        //data.child("giay").setValue(0);


        test_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c= Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                int second = c.get(Calendar.SECOND);
                int millis = c.get(Calendar.MILLISECOND);


                test Test =new test(hour,minute,second, (day+"-"+month+"-"+year).toString());
                data.child("test").push().setValue(Test);

                System.out.println("Năm: " + year);
                System.out.println("Tháng: " + (month+1));
                System.out.println("Ngày: " + day);
                System.out.println("giờ: " + hour);
                System.out.println("Phút: " + minute);
                System.out.println("Giây: " + second);
                System.out.println("Milli giây: " + millis);
            }
        });


        //gui xuong


        //gui du lieu len
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //gio.setText(data.);
                //gio.setText(dataSnapshot.child("test").child("gio").getValue().toString());
                //phut.setText(dataSnapshot.child("phut").getValue().toString());
                //giay.setText(dataSnapshot.child("giay").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        data_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main.this, data.class));
            }
        });


        acc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main.this, infor.class));
            }
        });


    }
}
