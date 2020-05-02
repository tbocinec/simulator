package fmph.simulator.Running.Time;

public class TimeManagementWithPause implements TimeManagement {

    double startTime;
    double endTime;
    double pauseTime;

    double pauseStart;

    public TimeManagementWithPause(){
        startTime = 0;
        pauseTime = 0;
    }
    @Override
    public void run(){
        if(startTime == 0){
            startTime = System.currentTimeMillis();
        }
        if(pauseStart != 0){
            pauseTime += (getRunTimeWithPause() - pauseStart);
            pauseStart = 0;
        }



    }

    private double getRunTimeWithPause(){
        return  System.currentTimeMillis() - startTime;
    }
    @Override
    public double getRunTime(){
        if(pauseStart != 0){
            return pauseStart;
        }
        return  System.currentTimeMillis() - startTime  -  pauseTime;
    }

    public void startPause() {
        pauseStart = getRunTimeWithPause();
    }

    @Override
    public void setTime(double time){
        //todo
    }
}
