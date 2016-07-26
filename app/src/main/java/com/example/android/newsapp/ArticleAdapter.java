package com.example.android.newsapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ArticleAdapter extends ArrayAdapter<Article> {

    public ArticleAdapter(Activity context, ArrayList<Article> articles){
        super(context, 0, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Article currentArticle = getItem(position);

        TextView headline = (TextView) listItemView.findViewById(R.id.headline);
        headline.setText(currentArticle.getmTitle());

        TextView publicationDate = (TextView) listItemView.findViewById(R.id.publishDate);
        try {
            publicationDate.setText(parseDateString(currentArticle.getmPublicationDate()).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TextView contributors = (TextView) listItemView.findViewById(R.id.contributors);
        if(currentArticle.hasContributors()) {
            contributors.setText("Contributors: " + currentArticle.getmContributors());
            contributors.setVisibility(View.VISIBLE);
        }
        else{
            contributors.setVisibility(View.GONE);
        }

        return listItemView;
    }

    public Date parseDateString(String dtStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(getContext().getString(R.string.date_format));
        return format.parse(dtStr);
    }



}
