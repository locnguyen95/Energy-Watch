package com.example.pc.energywatch;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;
import org.w3c.dom.NameList;

import java.text.BreakIterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.zip.DataFormatException;

public class data extends AppCompatActivity {
        ListView lvdata;
        ArrayList<test> mangdata =new ArrayList();
        DatabaseReference database;
        data_adapter adapter= null ;
        TextView H1, H2, ngay1, ngay2,error,error_text;
        Button from, to, search;
        DatePickerDialog.OnDateSetListener myfromdate;
        Calendar c1= Calendar.getInstance();
        Calendar c2= Calendar.getInstance();
        Calendar c3= Calendar.getInstance();
        Calendar t1= Calendar.getInstance();
        Calendar t2= Calendar.getInstance();
        //int hour1, hour2, min1, min2;
    int i,from_so, to_so, flag1=0, flag2=0, flag=0;
    static int top, bottom, mid,tam,top1, mid1, bottom1, tam1;
    int gio1, phut1, gio2, phut2;

    List<test> tests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        database = FirebaseDatabase.getInstance().getReference();
        lvdata = (ListView) findViewById(R.id.data_table);
        from =(Button) findViewById(R.id.from);
        to = (Button) findViewById(R.id.to);
       // so_ngay= (TextView) findViewById(R.id.songay);
        H1 = (TextView) findViewById(R.id.gio1);
        H2 = (TextView) findViewById(R.id.gio2);
        ngay1 = (TextView) findViewById(R.id.ngay1);
        ngay2 = (TextView) findViewById(R.id.ngay2);
        error= (TextView) findViewById(R.id.temp);
        error_text= (TextView) findViewById(R.id.error);
        search =(Button) findViewById(R.id.search);
        adapter = new data_adapter(this, R.layout.listview_one,mangdata);
        lvdata.setAdapter(adapter);
        //load_data();

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chongio(H1,t1);
                chonngay(ngay1,c1 );


            }
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chongio(H2,t2);
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

                if(H1.getText().toString().equals("") || H2.getText().toString().equals("")|| ngay1.getText().toString().equals("") || ngay2.getText().toString().equals("")){
                    Toast.makeText(data.this, "Please insert From and To information", Toast.LENGTH_SHORT).show();
                    return;
                }

                // tach gio cua from
                String[] time1 = (H1.getText().toString()).split(":");
                gio1 = Integer.parseInt(time1[0]);
                phut1 = Integer.parseInt(time1[1]);

                // tach gio cua to
                String[] time2 = (H2.getText().toString()).split(":");
                gio2 = Integer.parseInt(time2[0]);
                phut2 = Integer.parseInt(time2[1]);

                // thong bao loi thoi gian
                if((c2.getTimeInMillis()+gio2*60*60*1000+phut2*60*1000-c1.getTimeInMillis()-gio1*3600*1000-phut1*60*1000)/(1000*60) <0){
                    Toast.makeText(data.this, "Please insert To time greater than or equal to From time!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mangdata.clear();
                flag1=0; flag2=0;

                database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        tam= bottom=bottom1 = (int) dataSnapshot.child("numberdata").getValue().hashCode();
                        top=top1=1;

                        //check dau
                        String time1 = (String) dataSnapshot.child("Data/" + 1).child("Time").getValue();
                        String[] t1 = time1.split(":");
                        Integer h1_ = Integer.parseInt(t1[0]);
                        Integer m1_ = Integer.parseInt(t1[1]);
                        String day = (String) dataSnapshot.child("Data/" + 1).child("Date").getValue();
                        String[] d1 = day.split("-");
                        Integer day1_ = Integer.parseInt(d1[0]);
                        Integer month1_ = Integer.parseInt(d1[1]);
                        Integer year1_ = Integer.parseInt(d1[2]);
                        c3.set(year1_, (month1_ - 1), day1_);
                        long check1 = c1.getTimeInMillis() / 6000 + gio1 * 60 + phut1 - c3.getTimeInMillis() / 6000 - h1_ * 60 - m1_;


                        if((c2.getTimeInMillis() / 6000 + gio2 * 60 + phut2 - c3.getTimeInMillis() / 6000 - h1_ * 60 - m1_)<0){
                            flag1=1; flag2=1; from_so=to_so=top1;
                        }       //thêm vô sau 30-5


                        //check cuoi
                        String time2 = (String) dataSnapshot.child("Data/" + bottom1).child("Time").getValue();
                        String[] t2 = time2.split(":");
                        Integer h2_ = Integer.parseInt(t2[0]);
                        Integer m2_ = Integer.parseInt(t2[1]);
                        String day2 = (String) dataSnapshot.child("Data/" + bottom1).child("Date").getValue();
                        String[] d2 = day2.split("-");
                        Integer day2_ = Integer.parseInt(d2[0]);
                        Integer month2_ = Integer.parseInt(d2[1]);
                        Integer year2_ = Integer.parseInt(d2[2]);
                        c3.set(year2_, (month2_ - 1), day2_);
                        long check2 = c2.getTimeInMillis() / 6000 + gio2 * 60 + phut2 - c3.getTimeInMillis() / 6000 - h2_ * 60 - m2_;

                        if(check1<=0) {flag1=1; from_so=0;}
                        if(check2>=0) {flag2=1; to_so=bottom1+1;}


                        if((c1.getTimeInMillis() / 6000 + gio1 * 60 + phut1 - c3.getTimeInMillis() / 6000 - h2_ * 60 - m2_)>0){
                            flag1=1; flag2=1; from_so=to_so=bottom1;
                        }           //thêm vô sau 30-5





                        //ham de quy
                        for(mid= (bottom1+top1-1)/2; flag1==0; mid= (bottom+top-1)/2){
                            String temp_time = (String) dataSnapshot.child("Data/" + mid).child("Time").getValue();
                            if(temp_time==null){
                                top++;
                            }
                            else {
                                String[] y = temp_time.split(":");
                                Integer h = Integer.parseInt(y[0]);
                                Integer m = Integer.parseInt(y[1]);
                                String temp_date = (String) dataSnapshot.child("Data/" + mid).child("Date").getValue();
                                String[] x = temp_date.split("-");
                                Integer day1 = Integer.parseInt(x[0]);
                                Integer month1 = Integer.parseInt(x[1]);
                                Integer year1 = Integer.parseInt(x[2]);
                                c3.set(year1, (month1 - 1), day1);

                                long a = c1.getTimeInMillis() / 6000 + gio1 * 60 + phut1 - c3.getTimeInMillis() / 6000 - h * 60 - m;
                                if (a < 0) {
                                    if(bottom-top<3)
                                    { from_so=mid-1; flag1=1;}else
                                    bottom = mid;
                                }
                                if (a > 0) {
                                    if(bottom-top<3)
                                    { from_so=bottom-1; flag1=1;}else
                                    top = mid + 1;
                                }
                                if (a == 0) {
                                    int i;
                                    for (i = mid; i > 0; i--) {
                                        String temp_ = (String) dataSnapshot.child("Data/" + i).child("Time").getValue();
                                        if(temp_==null){
                                        }else{
                                        String[] z = temp_.split(":");
                                        Integer m_temp = Integer.parseInt(z[1]);
                                        if (m_temp != m) {
                                            from_so = i;
                                            bottom = bottom1;
                                            top = from_so;
                                            //database.removeEventListener(this);
                                            flag1 = 1;
                                            break;
                                        }
                                    }}
                                }
                            }
                            if (flag1 == 1)
                            break;
                        }


                        for(mid= (bottom1+top1-1)/2; flag1==1 && flag2==0 ; mid= (bottom+top-1)/2) {
                            String temp_time = (String) dataSnapshot.child("Data/" + mid).child("Time").getValue();
                            if (temp_time == null) {
                                top++;
                            } else{
                                String[] y = temp_time.split(":");
                            Integer h = Integer.parseInt(y[0]);
                            Integer m = Integer.parseInt(y[1]);
                            String temp_date = (String) dataSnapshot.child("Data/" + mid).child("Date").getValue();
                            String[] x = temp_date.split("-");
                            Integer day1 = Integer.parseInt(x[0]);
                            Integer month1 = Integer.parseInt(x[1]);
                            Integer year1 = Integer.parseInt(x[2]);
                            c3.set(year1, (month1 - 1), day1);

                            long a = c2.getTimeInMillis() / 6000 + gio2 * 60 + phut2 - c3.getTimeInMillis() / 6000 - h * 60 - m;
                            if (a < 0) {
                                if(bottom-top<3)
                                { to_so=top; flag2=1; }else
                                bottom = mid;
                            }
                            if (a > 0) {
                                if(bottom-top<3)
                                {to_so= mid+1; flag2=1;}else
                                top = mid + 1;
                            }
                            if (a == 0) {
                                int i;
                                for (i = mid; i > 0; i++) {
                                    String temp_ = (String) dataSnapshot.child("Data/" + i).child("Time").getValue();
                                    if(temp_==null){
                                    }else{
                                    String[] z = temp_.split(":");
                                    Integer m_temp = Integer.parseInt(z[1]);
                                    if (m_temp != m) {
                                        to_so = i;
                                        bottom = tam;
                                        top = 1;
                                        database.removeEventListener(this);
                                        flag2 = 1;
                                        break;
                                    }
                                }}
                            }
                        }
                            if (flag2 == 1)
                                break;
                        }

                        int dem = 0;
                        int error_number=0;
                        if(flag1==1 && flag2==1){
                        int i;



                        for (i = from_so+1 ; i < to_so; i++) {
                            test data1= dataSnapshot.child("Data/"+i).getValue(test.class);
                            if(data1==null || data1.Hour==null || data1.Minute==null|| data1.Second==null|| data1.Date==null|| data1.Time==null)
                            {mangdata.add(new test(0, 0, 0, "null", "null")); error_number++;}
                            else{
                                //mangdata.add(new test(data1.Hour, data1.Minute, data1.Second, data1.Date, data1.Time));
                                mangdata.add(new test(dataSnapshot.child("Data/" + i).child("Hour").getValue().hashCode(), dataSnapshot.child("Data/" + i).child("Minute").getValue().hashCode(), dataSnapshot.child("Data/" + i).child("Second").getValue().hashCode(), dataSnapshot.child("Data/" + i).child("Date").getValue().toString(), dataSnapshot.child("Data/" + i).child("Time").getValue().toString()));
                            }
                            //mangdata.add(new test(dataSnapshot.child("Data/" + i).child("Hour").getValue().hashCode(), dataSnapshot.child("Data/" + i).child("Minute").getValue().hashCode(), dataSnapshot.child("Data/" + i).child("Second").getValue().hashCode(), dataSnapshot.child("Data/" + i).child("Date").getValue().toString(), dataSnapshot.child("Data/" + i).child("Time").getValue().toString()));
                            adapter.notifyDataSetChanged();
                            dem++;}
                        }
                        if(dem==0){
                            mangdata.clear();
                            adapter.notifyDataSetChanged();     //can co dong nay de nhan biet du lieu co thay doi hay khong va cap nhat len listview
                            Toast.makeText(data.this, "There is no data at this time!", Toast.LENGTH_SHORT).show();
                        }

                        if(dem!=0 ){
                        error.setText(""+error_number);
                        error_text.setText("Errors");
                        }
                        //database.removeEventListener(this);
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
        database.child("Data").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                test data1 = dataSnapshot.getValue(test.class);
                if(data1.Hour==null || data1.Minute==null|| data1.Second==null|| data1.Date==null|| data1.Time==null) {
                    mangdata.add(new test(0, 0, 0, "null", "null"));
                }else{
                mangdata.add(new test(data1.Hour, data1.Minute, data1.Second, data1.Date, data1.Time));}
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
