package com.example.pc.energywatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;



public class Main extends AppCompatActivity {

    private Button acc_btn, data_btn,test_btn;
    private TextView gio_, phut_, giay_;
    DatabaseReference data;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        acc_btn =(Button) findViewById(R.id.account);
        data_btn = (Button) findViewById(R.id.data);
        test_btn = (Button) findViewById(R.id.test);

        gio_ =(TextView) findViewById(R.id.hour);
        phut_= (TextView) findViewById(R.id.minute);
        giay_= (TextView) findViewById(R.id.second);

        data=FirebaseDatabase.getInstance().getReference();                 //realtime database

        //data.child("gio").setValue(0);
        //data.child("phut").setValue(0);
        //data.child("giay").setValue(0);

/*
        test_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //i = dataSnapshot.child("test/numberdata").getValue().hashCode();
                        //int i=0;
                        int i = (int) dataSnapshot.child("numberdata").getValue().hashCode();
                        Calendar c= Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);
                        int hour = c.get(Calendar.HOUR_OF_DAY);
                        int minute = c.get(Calendar.MINUTE);
                        int second = c.get(Calendar.SECOND);
                        //int millis = c.get(Calendar.MILLISECOND);
                        i++;
                        test Test =new test(hour,minute,second, (day+"-"+(month+1)+"-"+year).toString(), (hour+":"+minute+":"+second).toString());
                        data.child("Data/"+i).setValue(Test);
                        data.child("numberdata").setValue(i);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //data.child("test").push().setValue(Test);


            }
        });
*/




//// nhận dữ liệu phiên bản v1
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int data_num= dataSnapshot.child("numberdata").getValue().hashCode();
                test data1 = dataSnapshot.child("Data/"+data_num).getValue(test.class);
                gio_.setText(data1.Hour.toString());
                phut_.setText(data1.Minute.toString());
                giay_.setText(data1.Second.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




/*
        //gui du lieu len
        data.child("Data").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                test data1 = dataSnapshot.getValue(test.class);
                gio_.setText(data1.Hour.toString());
                phut_.setText(data1.Minute.toString());
                giay_.setText(data1.Second.toString());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/




        data_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main.this, com.example.pc.energywatch.data.class));
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
