package com.example.android.newsapp;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView listview;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.articleList);

        FetchArticlesFromGuardianAPITask fetchArticles = new FetchArticlesFromGuardianAPITask(listview, MainActivity.this);
        fetchArticles.execute();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FetchArticlesFromGuardianAPITask fetchArticles = new FetchArticlesFromGuardianAPITask(listview, MainActivity.this);
                fetchArticles.execute();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
