package elevator;

import request.RequestQueue;

import java.util.concurrent.atomic.AtomicBoolean;

public class ElevatorThread extends Thread {
    private Elevator elevator;
    private AtomicBoolean end;
    private RequestQueue queue;

    public ElevatorThread(Elevator elevator, RequestQueue queue) {
        this.elevator = elevator;
        this.end = new AtomicBoolean(false);
        this.queue = queue;
        setPriority(Thread.MIN_PRIORITY);
    }

    public void setEnd(boolean end) {
        this.end.set(end);
    }

    private synchronized boolean isEnd() {
        return end.get() & elevator.isEmpty();
    }

    public void run() {
        while (!isEnd()) {
            try {
                Thread.sleep(1);
                elevator.work();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
