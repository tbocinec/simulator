package fmph.simulator.Running.Time;

public class TimeManagment {

    double startTime;
    double endTime;
    double pauseTime;

    double pauseStart;

    public TimeManagment(){
        startTime = 0;
        pauseTime = 0;
    }

    public void run(){
        if(startTime == 0){
            startTime = System.currentTimeMillis();
        }
        System.out.println(pauseStart);
        if(pauseStart != 0){
            pauseTime += (getRunTime() - pauseStart);
            pauseStart = 0;
        }



    }

    private double getRunTime(){
        return  System.currentTimeMillis() - startTime;
    }

    public double getRunTimeWithoutPause(){
        if(pauseStart != 0){
            return pauseStart;
        }
        return  System.currentTimeMillis() - startTime  -  pauseTime;
    }

    public void startPause() {
        pauseStart = getRunTime();
    }
}
