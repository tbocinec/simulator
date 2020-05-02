package fmph.simulator.Running.Time;

public interface TimeManagement {
    void run();
    double getRunTime();
    void startPause();
    void setTime(double time);
}
