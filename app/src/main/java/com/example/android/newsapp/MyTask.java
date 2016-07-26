package com.example.android.newsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MyTask extends AsyncTask <Void, Void, Void> {

    Context context;
    ArrayList<Article> articles = new ArrayList<>();
    ListView listview;

   
    public MyTask(ListView listview,Context context) {
        this.listview = listview;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader;
        String resultJSON;

        URL url = null;
        try {
            String linkText = context.getString(R.string.guardian_api_technology);
            url = new URL(linkText);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            resultJSON = buffer.toString();
            fetchArticles(resultJSON);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        ArticleAdapter adapter = new ArticleAdapter((Activity) context, articles);
        listview = (ListView) listview.findViewById(R.id.articleList);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openWebPage(articles.get(i).getmUrl());
            }
        });
    }

    private ArrayList<Article> fetchArticles (String jsonString) throws JSONException {

        articles = new ArrayList<>();

        JSONObject root = new JSONObject(jsonString);
        JSONObject response = root.getJSONObject(context.getString(R.string.json_response));
        JSONArray results = response.getJSONArray(context.getString(R.string.json_results));

        for(int i = 0; i < results.length(); i++) {
            JSONObject singleArticle = results.getJSONObject(i);
            String title = singleArticle.optString(context.getString(R.string.json_web_title));
            String webURL = singleArticle.optString(context.getString(R.string.json_web_url));
            String publicationDate = singleArticle.optString(context.getString(R.string.json_web_publication_date));

            articles.add(new Article(publicationDate,title, webURL));
        }
        return articles;
    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
}
