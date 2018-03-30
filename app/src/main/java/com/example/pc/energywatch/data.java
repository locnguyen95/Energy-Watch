package com.example.pc.energywatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class data extends AppCompatActivity {
        ListView lvdata;
        ArrayList<test> mangdata =new ArrayList();
        DatabaseReference database;
        data_adapter adapter= null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        database = FirebaseDatabase.getInstance().getReference();
        lvdata = (ListView) findViewById(R.id.data_table);
        adapter = new data_adapter(this, R.layout.listview_one,mangdata);
        lvdata.setAdapter(adapter);
        load_data();

    }

    private void load_data(){
        database.child("test").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                test data1 = dataSnapshot.getValue(test.class);
                mangdata.add(new test(data1.gio,data1.phut, data1.giay, data1.date));
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
