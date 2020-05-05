package fmph.simulator.Running.Time;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

@JsonDeserialize(as = TimeManagementSimple.class)
@JsonSerialize(as = TimeManagementSimple.class)
public interface TimeManagement extends Serializable {
    void run();
    double getRunTime();
    void startPause();
    void setTime(double time);
}
