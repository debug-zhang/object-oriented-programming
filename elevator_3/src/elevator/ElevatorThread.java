package elevator;

import request.RequestQueue;
import scheduler.End;

public class ElevatorThread extends Thread {
    private Elevator elevator;
    private End end;

    public ElevatorThread(RequestQueue baseQueue, RequestQueue queue,
                          char name, int runTime, int size, End end) {
        this.elevator = new Elevator(baseQueue, queue, runTime, name, size);
        this.end = end;
    }

    public Elevator getElevator() {
        return elevator;
    }

    public void run() {
        while (end.notEnd()) {
            while (elevator.isEmpty() && end.notEnd()) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                elevator.work();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
