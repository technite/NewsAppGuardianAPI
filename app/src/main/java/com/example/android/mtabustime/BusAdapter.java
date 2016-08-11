package com.example.android.mtabustime;

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

/**
 * Created by Daniel on 8/3/2016.
 */
public class BusAdapter extends ArrayAdapter<Bus> {

    public BusAdapter(Activity context, ArrayList<Bus> buses) {
        super(context, 0, buses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Bus currentBus = getItem(position);

        TextView route = (TextView) listItemView.findViewById(R.id.route);
        route.setText(currentBus.getmLineRef());

        TextView eta = (TextView) listItemView.findViewById(R.id.eta);
        if (!(currentBus.getmExpectedArrivalTime().isEmpty())) {
            eta.setText(parseDate(currentBus.getmExpectedArrivalTime()));
        }

        TextView destination = (TextView) listItemView.findViewById(R.id.destination);
        destination.setText(currentBus.getmDestinationName());

        TextView distance = (TextView) listItemView.findViewById(R.id.presentableDistance);
        distance.setText(currentBus.getmPresentableDistance());

        TextView progress = (TextView) listItemView.findViewById(R.id.progressStatus);

        if (!(currentBus.getmProgressStatus().isEmpty())) {
            progress.setText(currentBus.getmProgressStatus());
            progress.setVisibility(View.VISIBLE);
        } else {
            progress.setVisibility(View.GONE);
        }

        return listItemView;
    }

    public String parseDate(String originalString) {

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss").parse(originalString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String newString = new SimpleDateFormat("h:mm a").format(date); // 9:00

        return newString;
    }


}
