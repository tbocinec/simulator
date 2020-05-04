package fmph.simulator.Running.Time;

import java.io.Serializable;

public interface TimeManagement extends Serializable {
    void run();
    double getRunTime();
    void startPause();
    void setTime(double time);
}
