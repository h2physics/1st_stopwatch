package com.example.h2physics.stopwatch.Class;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.h2physics.stopwatch.R;

import java.util.List;

/**
 * Created by H2PhySicS on 12/22/16.
 */

public class TimeAdapter extends ArrayAdapter<TimerInformation> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<TimerInformation> mTime;

    public TimeAdapter(Context context, List<TimerInformation> mTime){
        super(context, 0, mTime);
        this.mContext = context;
        this.mTime = mTime;
        this.mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TimerInformation timerInformation = mTime.get(position);

        convertView = mLayoutInflater.inflate(R.layout.layout_timer, parent, false);

        //TextView textViewNumber = (TextView) convertView.findViewById(R.id.textViewNumber);
        TextView textViewTimeResult = (TextView) convertView.findViewById(R.id.textViewTimerResult);

        //textViewNumber.setText(timerInformation.getIndex());
        textViewTimeResult.setText(timerInformation.getTimeClock());

        return convertView;
    }
}
