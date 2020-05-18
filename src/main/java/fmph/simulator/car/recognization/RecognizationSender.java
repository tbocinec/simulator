package fmph.simulator.car.recognization;

import fmph.simulator.Math.Geometric;
import fmph.simulator.Math.Point2d;
import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.com.Message;
import fmph.simulator.map.LaserTag;
import fmph.simulator.map.Segment;
import fmph.simulator.car.CarState;
import fmph.simulator.visualization.component.Function;
import fmph.simulator.visualization.console.MessageType;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.lang3.SerializationUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static fmph.simulator.visualization.view.NoiseController.ifProbably;

public class RecognizationSender {
    private PropertiesConfiguration config =  config = ContextBuilder.getContext().config;
    private boolean waitAfterRecognization = false;
    private HistoryElement lastRecognizationHistoryElement;
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(config.getInt("app.threedPoolSize"));
    private ArrayList<ScheduledFuture<?>> futureTask  = new ArrayList<>();
    AtomicBoolean enableSend  = new AtomicBoolean(true);



    public  void planSend(NeartTags neartTag) {
        showNearsTagInfo(neartTag);
        int countSend = 1;
        TreeMap<Double, RecognizationElement> recognizationElement = neartTag.getNextTags();
        if(recognizationElement.size() > 1){
        for(Double second : recognizationElement.keySet())
            {
                sendAfter((long)(second*1000),recognizationElement.get(second));
                countSend-=1;
                if(countSend <= 0){break;}
            }
        }
    }

    private void sendAfter(long miliseconds,RecognizationElement recognizationElement){
        ScheduledFuture<?> future = executor.schedule(() -> {
            NeartTags.addSend(recognizationElement.getLaserTag().getType());
            if(enableSend.get()){
                sendNow(recognizationElement.getLaserTag(),recognizationElement.getSegment(),null); //todoDistanceFromX
            }

        }, miliseconds,  TimeUnit.MILLISECONDS);
        futureTask.add(future);
    }

    public void killAllFutureSend(){
        enableSend.set(false);
        futureTask.forEach(e -> e.cancel(true));
        futureTask.clear();
        enableSend.set(true);
    }


    public void sendNow(LaserTag laserTag, Segment segment, Double distanceFromX) {

        CarState carState = ContextBuilder.getContext().getRunManagement().getActualRun().getCarState();
        double rTime = System.currentTimeMillis();
        //double rTime = ContextBuilder.getContext().getRunManagement().getActualRun().getRunTimeSecond();
        BigDecimal time = new BigDecimal(rTime);
        new fmph.simulator.visualization.console.Message("Recognized new tag with id " + laserTag.getType()+" at time "+rTime, MessageType.INFO);

        if (config.getBoolean("app.waitAfterRecognization"))  {
            waitAfterRecognization = true;
            ContextBuilder.getContext().getRunManagement().pause();
        }


        sendRecognizedInfo(segment, laserTag, distanceFromX,time);
        lastRecognizationHistoryElement = new HistoryElement(rTime, SerializationUtils.clone(carState),segment,laserTag);
        ContextBuilder.getContext().getRunManagement().getActualRun().getRecognitionHistory().addTag(lastRecognizationHistoryElement);
    }

    private void sendRecognizedInfo(Segment segment, LaserTag laserTag, Double distanceFromX,BigDecimal time) {
        if(ifProbably(ContextBuilder.getContext().getNoiseController().getOmmitingRec())){
            new fmph.simulator.visualization.console.Message("[Noise] ommiting tag " +laserTag.getType(), MessageType.INFO);
            return;
        }
        if(null == distanceFromX){
            Point2d centerBeen = ContextBuilder.getContext().getCarManagement().getCenterBeen();
            distanceFromX = Geometric.distance(new Point2d(laserTag.getX(),laserTag.getY()),centerBeen);
        }
        CarState carState = ContextBuilder.getContext().getRunManagement().getActualRun().getCarState();
        Message msg = new Message();
        msg.setTime_stamp(time.toPlainString());
        msg.setTag_id(Integer.parseInt(laserTag.getType()));


        Double alfa = Math.toRadians(laserTag.getGamma());
        Double beta = Math.toRadians(Function.TurnAngle(carState.getCarAngle()));
        double titl = Function.angle_difference(beta, alfa);
        //zmenit smer titl
        msg.setTilt(Math.abs(titl));
        msg.setCenter_x(distanceFromX*1000) ;//m to  mm;

        if(ifProbably(ContextBuilder.getContext().getNoiseController().getDifRecognization())){
            int tag = new Random().nextInt(10);
            new fmph.simulator.visualization.console.Message("[Noise] Sent diff recognization tag to "+ tag, MessageType.INFO);
            msg.setTag_id(tag);
            return;
        }
        if(ifProbably(ContextBuilder.getContext().getNoiseController().getDifAttribute())){

            titl = titl * ((new Random().nextDouble()*2)-1);
            msg.setTilt(titl);
            new fmph.simulator.visualization.console.Message("[Noise] Sent diff recognization angle"+ titl, MessageType.INFO);
            return;
        }
        ContextBuilder.getContext().getServer().sendMsg(msg.serialize());
    }

    public void updateRecognization() {
        waitAfterRecognization = false;
        CarState carState = ContextBuilder.getContext().getRunManagement().getActualRun().getCarState();
        if(lastRecognizationHistoryElement != null){
            lastRecognizationHistoryElement.addRecognizationInfo(ContextBuilder.getContext().getRunManagement().getActualRun().getRunTimeSecond(),SerializationUtils.clone(carState));
            lastRecognizationHistoryElement = null;
        }
    }
    private void showNearsTagInfo(NeartTags nextTagWrapper) {
        AtomicInteger numberVisibleMax = new AtomicInteger(2);
        TreeMap<Double, RecognizationElement> nextTag = nextTagWrapper.getNextTags();
        if(nextTag.size() >0) {

            StringBuffer tagsInfo = new StringBuffer();
            for(double e : nextTag.keySet()){
                numberVisibleMax.addAndGet(-1);
                if(numberVisibleMax.get() ==0 ){
                    break;
                }
                tagsInfo.append(nextTag.get(e).getLaserTag().getType() + " at time ["+String.valueOf(e)+"]");
            }
            ContextBuilder.getContext().getMainController().setRightStatusText("Nears tags: " + tagsInfo.toString() );
        }
    }
}
