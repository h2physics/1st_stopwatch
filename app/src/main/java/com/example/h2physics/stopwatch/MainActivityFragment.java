package com.example.h2physics.stopwatch;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.h2physics.stopwatch.Class.StopWatch;
import com.example.h2physics.stopwatch.Class.Typefaces;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener{
    TextView textViewTimer;
    ListView listViewTimer;
    TextView textViewTimeResult;
    Button buttonGet;
    Button buttonStart;
    Button buttonStop;
    //TextView textViewNumber;

    String timeClock;

//    ArrayList<TimerInformation> mTime = new ArrayList<>();
    ArrayList<String> arrayList = new ArrayList<>();

//    TimeAdapter timeAdapter;

    ArrayAdapter<String> adapter;

    final int MSG_START_TIMER = 0;
    final int MSG_STOP_TIMER = 1;
    final int MSG_UPDATE_TIMER = 2;
    final int MSG_RESUME_TIMER = 3;
    final int MSG_PAUSE_TIMER = 4;
    int index = 1;

    StopWatch timer = new StopWatch();
    final int REFRESH_RATE = 1000;

    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_START_TIMER:
                    timer.start(); //start timer
                    handler.sendEmptyMessage(MSG_UPDATE_TIMER);
                    break;

                case MSG_UPDATE_TIMER:
                    long sec, min, hour;
                    sec = timer.getElapsedTime()/1000;
                    hour = sec / 3600;
                    sec = sec - 3600 * hour;
                    min = sec / 60;
                    sec = sec - 60 * min;

                    String secStr = null, minStr = null, hourStr = null;

                    if(sec < 10){
                        secStr = "0" + sec;
                    } else {
                        secStr = sec + "";
                    }
                    if(min < 10){
                        minStr = "0" + min;
                    } else {
                        minStr = min + "";
                    }
                    if(hour < 10){
                        hourStr = "0" + hour;
                    } else {
                        hourStr = hour + "";
                    }

                    timeClock = hourStr + ":" + minStr + "," + secStr;

                    //Log.e("Time",timeClock);

                    textViewTimer.setText(timeClock);
                    handler.sendEmptyMessageDelayed(MSG_UPDATE_TIMER,REFRESH_RATE); //text view is updated every second,
                    break;                                  //though the timer is still running

                case MSG_STOP_TIMER:
                    handler.removeMessages(MSG_UPDATE_TIMER); // no more updates.
                    timer.stop();//stop timer
                    textViewTimer.setText(timeClock);
                    break;


                default:
                    break;
            }
        }
    };

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        textViewTimer = (TextView) rootView.findViewById(R.id.textViewTimer);
        listViewTimer = (ListView) rootView.findViewById(R.id.listViewTime);
        textViewTimeResult = (TextView) rootView.findViewById(R.id.textViewTimerResult);

        textViewTimer.setTypeface(Typefaces.get(getContext(), "Roboto-Thin.ttf"));
//        textViewTimeResult.setTypeface(Typefaces.get(getContext(), "Roboto-Thin.ttf"));

        buttonGet = (Button) rootView.findViewById(R.id.buttonGet);
        buttonStart = (Button) rootView.findViewById(R.id.buttonStart);
        buttonStop = (Button) rootView.findViewById(R.id.buttonStop);
        //textViewNumber = (TextView) rootView.findViewById(R.id.textViewNumber);

        buttonGet.setOnClickListener(this);
        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);

        //timeAdapter = new TimeAdapter(getActivity(), mTime);

        adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.layout_timer,
                R.id.textViewTimerResult,
                new ArrayList<String>());

        listViewTimer.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.buttonStart && buttonStart.getVisibility() == View.VISIBLE){
            handler.sendEmptyMessage(MSG_START_TIMER);
            adapter.clear();
            index = 1;

            buttonStart.setVisibility(View.INVISIBLE);
            buttonStop.setVisibility(View.VISIBLE);

        } else if (id == R.id.buttonStop && buttonStop.getVisibility() == View.VISIBLE){
            handler.sendEmptyMessage(MSG_STOP_TIMER);

            buttonStart.setVisibility(View.VISIBLE);
            buttonStop.setVisibility(View.INVISIBLE);

        } else if (id == R.id.buttonGet){
            if (buttonStart.getVisibility() == View.INVISIBLE){
                adapter.add(index + ".   " + timeClock);
                index++;
            }
        }

    }
}
