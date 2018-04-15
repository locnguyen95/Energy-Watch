package com.example.pc.energywatch;

import android.app.Application;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by PC on 14/04/2018.
 */

public class MainApplication extends Application {
    DatabaseReference database;
    List<test> tests = new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        database = FirebaseDatabase.getInstance().getReference();
        tests.clear();
        database.child("Data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> ts = dataSnapshot.getChildren();
                Iterator<DataSnapshot> it = ts.iterator();
                while(it.hasNext()){
                    try {
                        test t = it.next().getValue(test.class);
                        tests.add(t);
                        Log.e("TAGG", t.Date + "  " + t.Time);
                        Log.e("TAGG","list size = "+tests.size());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAGG","error" + databaseError.getDetails());
            }
        });
    }
}
