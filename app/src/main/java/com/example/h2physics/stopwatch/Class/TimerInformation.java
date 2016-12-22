package com.example.h2physics.stopwatch.Class;

/**
 * Created by H2PhySicS on 12/22/16.
 */

public class TimerInformation {
    String index;
    String timeClock;

    public TimerInformation(String index, String timeClock){
        this.index = index;
        this.timeClock = timeClock;
    }

    public String getIndex() {
        return index;
    }

    public String getTimeClock() {
        return timeClock;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setTimeClock(String timeClock) {
        this.timeClock = timeClock;
    }
}
