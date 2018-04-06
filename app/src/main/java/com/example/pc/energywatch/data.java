package com.example.pc.energywatch;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class data extends AppCompatActivity {
        ListView lvdata;
        ArrayList<test> mangdata =new ArrayList();
        DatabaseReference database;
        data_adapter adapter= null ;
        TextView from, to,so_ngay;
        Button search;
        DatePickerDialog.OnDateSetListener myfromdate;
        Calendar c1= Calendar.getInstance();
        Calendar c2= Calendar.getInstance();
        Calendar c3= Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        database = FirebaseDatabase.getInstance().getReference();
        lvdata = (ListView) findViewById(R.id.data_table);
        from =(TextView) findViewById(R.id.from);
        to = (TextView) findViewById(R.id.to);
        so_ngay= (TextView) findViewById(R.id.songay);
        search =(Button) findViewById(R.id.search);
        adapter = new data_adapter(this, R.layout.listview_one,mangdata);
        lvdata.setAdapter(adapter);
        //load_data();

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonngay(from,c1 );

            }
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonngay(to,c2);
                //c2.set(2018,4,10);
                //SimpleDateFormat format =new SimpleDateFormat("dd/MM/yyyy");
                //to.setText(format.format(c2.getTime()));
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            //int day1 , month1, year1;
            @Override
            public void onClick(View view) {
                int b= (int) ((c2.getTimeInMillis()-c1.getTimeInMillis())/(1000*3600*24));
                so_ngay.setText(""+b);
                String a= (String) from.getText();
                String[] s= a.split("/");
                int day1 =Integer.parseInt(s[0]);
                int month1 =Integer.parseInt(s[1]);
                int year1 =Integer.parseInt(s[2]);
                System.out.println(day1);
                System.out.println(month1);
                System.out.println(year1);
                mangdata.clear();
                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int sodem = (int) dataSnapshot.child("test").child("numberdata").getValue().hashCode();
                        int i;
                        for ( i = 1; i <= sodem; i++) {
                            String temp = (String) dataSnapshot.child("test/data/" + i).child("date").getValue();
                            String[] x = temp.split("-");
                            Integer day1 = Integer.parseInt(x[0]);
                            Integer month1 = Integer.parseInt(x[1]);
                            Integer year1 = Integer.parseInt(x[2]);
                            //System.out.println(day1 + "so" + i);
                            //System.out.println(month1 + "so" + i);
                            //System.out.println(year1 + "so" + i);
                            c3.set(year1,(month1-1),day1);
                            System.out.println(""+ ((int)(c2.getTimeInMillis()-c3.getTimeInMillis())/(1000*3600*24)));
                            if( ((c3.getTimeInMillis()-c1.getTimeInMillis())/(1000*3600*24) >=0) && ((c3.getTimeInMillis()-c2.getTimeInMillis())/(1000*3600*24)<=0))
                            {
                                //in ra dư lieu da loc
                                mangdata.add(new test( dataSnapshot.child("test/data/" + i).child("gio").getValue().hashCode(),dataSnapshot.child("test/data/" + i).child("phut").getValue().hashCode(), dataSnapshot.child("test/data/" + i).child("giay").getValue().hashCode(),dataSnapshot.child("test/data/" + i).child("date").getValue().toString()));
                                adapter.notifyDataSetChanged();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    private void tachso (String a, int day, int month, int year){
        String[] s= a.split("/");
        day =Integer.parseInt(s[0]);
        month =Integer.parseInt(s[1]);
        year =Integer.parseInt(s[2]);
    }

    private  void chonngay(final TextView a, final Calendar c){
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datechoose = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                c.set(i,i1,i2);
                SimpleDateFormat format =new SimpleDateFormat("dd/MM/yyyy");
                a.setText(format.format(c.getTime()));
            }
        }, year, month, day);
        datechoose.show();
    }

    private void load_data(){
        database.child("test").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                test data1 = dataSnapshot.getValue(test.class);
                mangdata.add(new test(data1.gio, data1.phut, data1.giay, data1.date));
                adapter.notifyDataSetChanged();
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

    }

}
