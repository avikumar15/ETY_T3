package com.example.spidertask3_v2;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    ListView list;
    static HistoryDatabase historyDatabase;
    List<String> TheWord;
    List<String> TheEtymology;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        list=(ListView)findViewById(R.id.LIST);
        historyDatabase = new HistoryDatabase(this);
        TheWord = new ArrayList<>();
        TheEtymology = new ArrayList<>();

        Cursor res = historyDatabase.getDATA();

        if(res.getCount() == 0)
        {
            Toast.makeText(getApplicationContext(),"Nothing to show", Toast.LENGTH_SHORT).show();
            return;
        }

        while(res.moveToNext())
        {
            TheWord.add(res.getString(0));
            TheEtymology.add(res.getString(1));
        }

        List<HistoryDataClass> h = new ArrayList<HistoryDataClass>();

        for(int i=0; i<TheWord.size(); i++)
        {
            h.add(new HistoryDataClass(TheWord.get(i),TheEtymology.get(i)));
        }

        ListAdapter adapter = new ListAdapter(this,R.layout.list_theme,h);
        list.setAdapter(adapter);

    }

    public void GOBACK(View view)
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }

    public void DELETEDATA(View view)
    {
        if(TheWord.size()!=0)
        Toast.makeText(this,"Deleted Data",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Nothing to delete",Toast.LENGTH_SHORT).show();

        historyDatabase.DeleteEntireTable();

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);

    }

}
