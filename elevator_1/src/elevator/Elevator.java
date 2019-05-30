package elevator;

import com.oocourse.TimableOutput;
import request.Request;

public class Elevator {
    private int floor;

    private static final int doorTime = 250;

    public Elevator() {
        this.floor = 1;
    }

    private long getWaitTime(Request taker) {
        return Math.abs(taker.getFrom() - floor) * 500;
    }

    private long getRunTime(Request taker) {
        return Math.abs(taker.getFrom() - taker.getTo()) * 500;
    }

    void work(Request taker) throws InterruptedException {
        Thread.sleep(getWaitTime(taker));

        TimableOutput.println("OPEN-" + taker.getFrom());
        Thread.sleep(doorTime);
        TimableOutput.println("IN-" + taker.getId() + "-" + taker.getFrom());
        Thread.sleep(doorTime);
        TimableOutput.println("CLOSE-" + taker.getFrom());


        Thread.sleep(getRunTime(taker));

        TimableOutput.println("OPEN-" + taker.getTo());
        Thread.sleep(doorTime);
        TimableOutput.println("OUT-" + taker.getId() + "-" + taker.getTo());
        Thread.sleep(doorTime);
        TimableOutput.println("CLOSE-" + taker.getTo());
        floor = taker.getTo();
    }
}
