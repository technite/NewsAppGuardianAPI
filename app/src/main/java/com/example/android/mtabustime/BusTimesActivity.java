package com.example.android.mtabustime;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class BusTimesActivity extends AppCompatActivity {

    ListView listview;
    SwipeRefreshLayout swipeRefreshLayout;
    Button searchBtn;
    EditText editText;
    String stopId = "400134";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_times);


        //Connect to Google Play Services


        //Hide keyboard when launching the app
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        listview = (ListView) findViewById(R.id.articleList);

        FetchBusTimes fetchArticles = new FetchBusTimes(listview, BusTimesActivity.this);
        fetchArticles.execute();

        editText = (EditText) findViewById(R.id.stopID);


        searchBtn = (Button) findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopId = editText.getText().toString();
                FetchBusTimes fetchArticles = new FetchBusTimes(listview, BusTimesActivity.this, stopId);
                fetchArticles.execute();
            }
        });


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FetchBusTimes fetchArticles = new FetchBusTimes(listview, BusTimesActivity.this, stopId);
                fetchArticles.execute();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }
}
