import java.util.TimerTask;

public class MyTimer extends TimerTask {
    long duration = 0;
    long seconds = 0;
    long minutes = 0;
    long hours = 0;
    long minuteMark = 60;
    long hourMark = 3600;
    long minuteMarker = minuteMark;
    long hourMarker = hourMark;
    boolean isDone = false;

    public MyTimer(long duration) {
        this.duration = duration;
    }

    @Override
    public void run(){
        seconds++;
        if(seconds == minuteMarker){
            minutes++;
            minuteMarker += minuteMark;
        }
        if(seconds == hourMarker){
            hours++;
            hourMarker += hourMark;
        }

        if(seconds == duration){
            seconds = 0;
            minutes = 0;
            hours = 0;
            minuteMarker = minuteMark;
            hourMarker = hourMark;
            isDone = true;
            System.out.println("Done pinging!");
            cancel();
        }
        System.out.println("Seconds: " + seconds + "| " + "Minutes: " + minutes + "| " + "Hours: " + hours);

    }
    public boolean isDone(){
        return isDone;
    }

}
