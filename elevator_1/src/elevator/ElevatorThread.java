package elevator;

import request.RequestQueue;

public class ElevatorThread extends Thread {
    private Elevator elevator;
    private RequestQueue queue;
    private boolean end;

    public ElevatorThread(Elevator elevator, RequestQueue queue) {
        this.elevator = elevator;
        this.queue = queue;
        end = false;
    }

    public synchronized void setEnd(boolean end) {
        this.end = end;
    }

    private synchronized boolean isEnd() {
        return end & queue.isEmpty();
    }

    public void run() {
        while (!isEnd()) {
            if (!queue.isEmpty()) {
                try {
                    elevator.work(queue.getRequest());
                    queue.removeRequest();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
