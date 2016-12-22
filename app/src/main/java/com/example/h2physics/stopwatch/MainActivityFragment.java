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
import com.example.h2physics.stopwatch.Class.TimeAdapter;
import com.example.h2physics.stopwatch.Class.TimerInformation;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener{
    TextView textViewTimer;
    ListView listViewTimer;
    Button buttonStart;
    Button buttonLeft;
    Button buttonRight;
    //TextView textViewNumber;

    String timeClock;

    ArrayList<TimerInformation> mTime = new ArrayList<>();

    TimeAdapter timeAdapter;

    final int MSG_START_TIMER = 0;
    final int MSG_STOP_TIMER = 1;
    final int MSG_UPDATE_TIMER = 2;
    int indexArrayList = 0;

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

                    Log.e("Time", hour + " : " + min + " : " + sec);

                    timeClock = hour + " : " + min + " : " + sec;

                    textViewTimer.setText(timeClock);
                    handler.sendEmptyMessageDelayed(MSG_UPDATE_TIMER,REFRESH_RATE); //text view is updated every second,
                    break;                                  //though the timer is still running

                case MSG_STOP_TIMER:
                    handler.removeMessages(MSG_UPDATE_TIMER); // no more updates.
                    timer.stop();//stop timer
                    textViewTimer.setText(""+ timer.getElapsedTime()/1000);
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
        buttonStart = (Button) rootView.findViewById(R.id.buttonStart);
        buttonLeft = (Button) rootView.findViewById(R.id.buttonLeft);
        buttonRight = (Button) rootView.findViewById(R.id.buttonRight);
        //textViewNumber = (TextView) rootView.findViewById(R.id.textViewNumber);

        buttonStart.setOnClickListener(this);
        buttonLeft.setOnClickListener(this);
        buttonRight.setOnClickListener(this);

        timeAdapter = new TimeAdapter(getActivity(), mTime);

        listViewTimer.setAdapter(timeAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.buttonLeft){
            mTime.add(new TimerInformation(String.valueOf(indexArrayList + 1), timeClock));
            indexArrayList++;
        } else if (id == R.id.buttonRight){
            handler.sendEmptyMessage(MSG_STOP_TIMER);
            textViewTimer.setText("0 : 0 : 0");
            buttonStart.setVisibility(View.VISIBLE);
            buttonLeft.setVisibility(View.INVISIBLE);
            buttonRight.setVisibility(View.INVISIBLE);
        } else if (id == R.id.buttonStart){
            handler.sendEmptyMessage(MSG_START_TIMER);
            timeAdapter.clear();
            buttonStart.setVisibility(View.INVISIBLE);
            buttonLeft.setVisibility(View.VISIBLE);
            buttonRight.setVisibility(View.VISIBLE);
        }
    }
}
