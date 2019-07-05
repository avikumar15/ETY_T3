package com.example.spidertask3_v2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.spidertask3_v2.HistoryActivity.historyDatabase;

public class MainActivity extends AppCompatActivity {

    EditText INPUT;
    TextView INVALID;
    Button SearchWord;

    GetDataInterface INTERFACE;
    List<WordDetails> wordclass;

    TextView temp;
    final String app_id = "9f993c2f";
    final String app_key = "f15b9ccfabae1514cc33f9989e74e747";
    String theword;
    final String TheAdd = "https://od-api.oxforddictionaries.com:443/api/v2/entries/en-gb/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        INPUT = (EditText)findViewById(R.id.INPUT);
        INVALID = (TextView) findViewById(R.id.INVALID);
        temp = (TextView)findViewById(R.id.temp);
        SearchWord=(Button)findViewById(R.id.SearchWord);

        temp.setText("Loading...");

        {
            INVALID.setVisibility(View.INVISIBLE);
            temp.setVisibility(View.INVISIBLE);
        }

        INPUT.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.equals(""))
                {
                    temp.setVisibility(View.INVISIBLE);
                    INPUT.setText("");
                }

            }
        });
    }

    public void wordentered(View view)
    {
        historyDatabase = new HistoryDatabase(this);

        theword="";

        INPUT = (EditText)findViewById(R.id.INPUT);
        INVALID = (TextView) findViewById(R.id.INVALID);

        String WORD;
        WORD = "";

        if(!INPUT.getText().toString().equals(""))
            WORD=INPUT.getText().toString();
        else
            temp.setVisibility(View.INVISIBLE);

        if(!WORD.equals(""))
            theword=WORD.toLowerCase();


        temp = (TextView)findViewById(R.id.temp);

        wordclass = new ArrayList<>();


        if(!theword.equals("")) {
            temp.setText("Loading...");
            temp.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"Searching...",Toast.LENGTH_SHORT).show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://od-api.oxforddictionaries.com:443/api/v2/entries/en-gb/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            INTERFACE = retrofit.create(GetDataInterface.class);
            Call<WordDetails> call = INTERFACE.getData(theword, app_id, app_key);

            call.enqueue(new Callback<WordDetails>() {
                @Override
                public void onResponse(Call<WordDetails> call, Response<WordDetails> response) {
                    if (!response.isSuccessful()) {
                        System.out.println("Server Error.");
                        temp.setText("Word not found. Try entering some other word.");
                        return;
                    }

                    wordclass.add(response.body());
                    try {
                        if ((wordclass.get(0).getResults().get(0).getLexicalEntries().get(0).getEntries().get(0).getEtymologies().get(0) != null) && (wordclass.get(0).getResults().get(0).getLexicalEntries().get(0).getEntries().get(0).getEtymologies().get(0) != "Something went wrong")) {
                            temp.setText(theword + "\n\nETYMOLOGY- " + wordclass.get(0).getResults().get(0).getLexicalEntries().get(0).getEntries().get(0).getEtymologies().get(0));
                            historyDatabase.InsertData(theword,wordclass.get(0).getResults().get(0).getLexicalEntries().get(0).getEntries().get(0).getEtymologies().get(0));
                        } else
                            temp.setText("Word not found. Try entering some other word.");
                    }
                    catch(Exception e)
                    {
                        temp.setText("Word not found. Try entering some other word.");
                        System.out.println(e);
                    }
                    }

                @Override
                public void onFailure(Call<WordDetails> call, Throwable t) {
                    System.out.println("Failed to fetch. " + t);
                    temp.setText("Word not found. Try entering some other word.");
                }
            });

            INPUT.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {}

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    if(s.equals(""))
                    {
                        temp.setVisibility(View.INVISIBLE);
                        INPUT.setText("");
                    }

                }
            });

        }

        else
        {
            INVALID.setVisibility(View.VISIBLE);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    INVALID.setVisibility(View.INVISIBLE);
                }
            }, 1000);
        }
    }

    public void StartHistory(View view)
    {
        Intent intent = new Intent(this,HistoryActivity.class);
        startActivity(intent);
    }
}