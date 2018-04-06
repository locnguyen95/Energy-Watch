package com.example.pc.energywatch;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.SimpleTimeZone;
import java.util.zip.DataFormatException;

public class data extends AppCompatActivity {
        ListView lvdata;
        ArrayList<test> mangdata =new ArrayList();
        DatabaseReference database;
        data_adapter adapter= null ;
        TextView gio1, gio2, ngay1, ngay2;
        Button from, to, search;
        DatePickerDialog.OnDateSetListener myfromdate;
        Calendar c1= Calendar.getInstance();
        Calendar c2= Calendar.getInstance();
        Calendar c3= Calendar.getInstance();
        Calendar t1= Calendar.getInstance();
        Calendar t2= Calendar.getInstance();
        //int hour1, hour2, min1, min2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        database = FirebaseDatabase.getInstance().getReference();
        lvdata = (ListView) findViewById(R.id.data_table);
        from =(Button) findViewById(R.id.from);
        to = (Button) findViewById(R.id.to);
       // so_ngay= (TextView) findViewById(R.id.songay);
        gio1 = (TextView) findViewById(R.id.gio1);
        gio2 = (TextView) findViewById(R.id.gio2);
        ngay1 = (TextView) findViewById(R.id.ngay1);
        ngay2 = (TextView) findViewById(R.id.ngay2);
        search =(Button) findViewById(R.id.search);
        adapter = new data_adapter(this, R.layout.listview_one,mangdata);
        lvdata.setAdapter(adapter);
        //load_data();

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chongio(gio1,t1);
                chonngay(ngay1,c1 );


            }
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chongio(gio2,t2);
                chonngay(ngay2,c2);
                //c2.set(2018,4,10);
                //SimpleDateFormat format =new SimpleDateFormat("dd/MM/yyyy");
                //to.setText(format.format(c2.getTime()));
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            //int day1 , month1, year1;
            @Override
            public void onClick(View view) {
                /*
                int b= (int) ((c2.getTimeInMillis()-c1.getTimeInMillis())/(1000*3600*24));
                so_ngay.setText(""+b);
                String a= (String) ngay1.getText();
                String[] s= a.split("/");
                int day1 =Integer.parseInt(s[0]);
                int month1 =Integer.parseInt(s[1]);
                int year1 =Integer.parseInt(s[2]);
                System.out.println(day1);
                System.out.println(month1);
                System.out.println(year1);
                */
                if(gio1.getText().toString().equals("") || gio2.getText().toString().equals("")|| ngay1.getText().toString().equals("") || ngay2.getText().toString().equals("")){
                    Toast.makeText(data.this, "Please insert From and To information", Toast.LENGTH_SHORT).show();
                    return;
                }

                // tach gio cua from
                String[] time1 = (gio1.getText().toString()).split(":");
                final Integer gio1 = Integer.parseInt(time1[0]);
                final Integer phut1 = Integer.parseInt(time1[1]);


                // tach gio cua to
                String[] time2 = (gio2.getText().toString()).split(":");
                final Integer gio2 = Integer.parseInt(time2[0]);
                final Integer phut2 = Integer.parseInt(time2[1]);


                // thong bao loi thoi gian
                if((c2.getTimeInMillis()+gio2*60*60*1000+phut2*60*1000-c1.getTimeInMillis()-gio1*3600*1000-phut1*60*1000)/(1000*60) <0){
                    Toast.makeText(data.this, "Please insert To time greater than or equal to From time!", Toast.LENGTH_SHORT).show();
                    return;
                }


                mangdata.clear();

                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int sodem = (int) dataSnapshot.child("test").child("numberdata").getValue().hashCode();
                        int i;
                        int dem=0;
                        for ( i = 1; i <= sodem; i++) {
                            String temp_date = (String) dataSnapshot.child("test/data/" + i).child("date").getValue();
                            String[] x = temp_date.split("-");
                            Integer day1 = Integer.parseInt(x[0]);
                            Integer month1 = Integer.parseInt(x[1]);
                            Integer year1 = Integer.parseInt(x[2]);
                            c3.set(year1,(month1-1),day1);

                            // tach gio cua mang data
                            String temp_time = (String) dataSnapshot.child("test/data/" + i).child("time").getValue();
                            String[] y = temp_time.split(":");
                            Integer h = Integer.parseInt(y[0]);
                            Integer m = Integer.parseInt(y[1]);
                            //Integer s = Integer.parseInt(x[2]);



                            if( ((c3.getTimeInMillis()+h*60*60*1000+m*60*1000-c1.getTimeInMillis()-gio1*3600*1000-phut1*60*1000)/(1000*60) >=0)
                                    && ((c3.getTimeInMillis()+h*3600*1000+m*60*1000-c2.getTimeInMillis()-gio2*3600*1000-phut2*60*1000)/(1000*60)<=0))
                            {
                                //in ra dư lieu da loc
                                mangdata.add(new test( dataSnapshot.child("test/data/" + i).child("gio").getValue().hashCode(),dataSnapshot.child("test/data/" + i).child("phut").getValue().hashCode(), dataSnapshot.child("test/data/" + i).child("giay").getValue().hashCode(),dataSnapshot.child("test/data/" + i).child("date").getValue().toString(),dataSnapshot.child("test/data/" + i).child("time").getValue().toString()));
                                adapter.notifyDataSetChanged();
                                dem++;
                            }

                        }
                        if(dem==0){
                            mangdata.clear();
                            adapter.notifyDataSetChanged();     //can co dong nay de nhan biet du lieu co thay doi hay khong va cap nhat len listview
                            Toast.makeText(data.this, "There is no data at this time!", Toast.LENGTH_SHORT).show();
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

    private void chongio(final TextView a, final Calendar c){
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timechoose = new TimePickerDialog(data.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                a.setText(i+":"+i1);
            }
        }, hour, minute, true);
        timechoose.show();

    }


    private void load_data(){
        database.child("test").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                test data1 = dataSnapshot.getValue(test.class);
                mangdata.add(new test(data1.gio, data1.phut, data1.giay, data1.date, data1.time));
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
