package com.example.spidertask3_v2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import javax.xml.transform.Templates;

public class ListAdapter extends ArrayAdapter<HistoryDataClass> {

    Context context;
    int resourcefile;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

         String Head = getItem(position).getWord();
         String ETY = getItem(position).getEty();

         HistoryDataClass temp = new HistoryDataClass(Head,ETY);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resourcefile,parent,false);

        TextView TV1 = (TextView) convertView.findViewById(R.id.TV1);
        TextView TV2 = (TextView) convertView.findViewById(R.id.TV2);

        TV1.setText(Head);
        TV2.setText(ETY);

        return convertView;
    }

    public ListAdapter(Context con, int resource, List<HistoryDataClass> objects) {
        super(con, resource, objects);

        context=con;
        resourcefile=resource;
    }
}
