package com.example.pc.energywatch;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PC on 30/03/2018.
 */

public class data_adapter extends BaseAdapter{

    Context myContext;
    int myLayout;
    List<test> arraydata;

    public data_adapter(Context myContext, int myLayout, List<test> arraydata) {
        this.myContext = myContext;
        this.myLayout = myLayout;
        this.arraydata = arraydata;
    }

    @Override
    public int getCount() {
        return arraydata.size();
    }

    @Override
    public Object getItem(int i) {
        return arraydata.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class viewholder{
        TextView _date,_gio, _phut, _giay, _time;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowview = view;
        viewholder holder = new viewholder();

        if(rowview == null){
            rowview = inflater.inflate(myLayout, null);
            holder._date = (TextView) rowview.findViewById(R.id.thoigian);
            holder._time =(TextView) rowview.findViewById(R.id.time);
            holder._gio =(TextView) rowview.findViewById(R.id.gio);
            holder._phut =(TextView) rowview.findViewById(R.id.phut);
            holder._giay =(TextView) rowview.findViewById(R.id.giay);
            rowview.setTag(holder);
        }else {
            holder = (viewholder) rowview.getTag();
        }
        //gan gia tri
        holder._date.setText(arraydata.get(i).Date);
        holder._time.setText(arraydata.get(i).Time);
        holder._gio.setText(arraydata.get(i).Hour.toString());           //nho them toString()  vì kiểu gio là Integer
        holder._phut.setText(arraydata.get(i).Minute.toString());
        holder._giay.setText(arraydata.get(i).Second.toString());
        return rowview;

    }

}
