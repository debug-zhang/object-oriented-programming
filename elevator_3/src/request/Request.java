package request;

import com.oocourse.TimableOutput;

public class Request {
    private int id;
    private int from;
    private int to;
    private int tempTo;

    public Request(int id, int from, int to) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.tempTo = to;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public boolean isTransfer() {
        return tempTo != to;
    }

    public void setFromAndTo() {
        this.from = to;
        this.to = tempTo;
    }

    public void setTempTo(int temp) {
        this.tempTo = to;
        this.to = temp;
    }

    public void inElevator(char name) {
        TimableOutput.println("IN-" + id + "-" + from + "-" + name);
    }

    public void outElevator(char name) {
        TimableOutput.println("OUT-" + id + "-" + to + "-" + name);
    }
}
