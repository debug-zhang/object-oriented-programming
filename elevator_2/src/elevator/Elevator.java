package elevator;

import com.oocourse.TimableOutput;
import request.Request;
import request.RequestQueue;

public class Elevator {
    private int floor;
    private Request taker;
    private RequestQueue queue;
    private RequestQueue pigQueue;
    private boolean get;

    private static final int doorTime = 200;

    public Elevator(RequestQueue queue) {
        this.floor = 1;
        this.taker = null;
        this.queue = queue;
        this.pigQueue = new RequestQueue();
        get = false;
    }

    public boolean isEmpty() {
        return queue.isEmpty() & pigQueue.isEmpty();
    }

    public synchronized void setTaker() {
        get = false;
        if (!pigQueue.isEmpty()) {
            taker = pigQueue.getRequest(0);
            get = true;
        } else if (queue.isEmpty()) {
            taker = null;
        } else {
            taker = queue.getRequest(0);
            if (taker != null) {
                queue.getPiggy(taker);
            }
        }
    }

    void waitTime(int from, int to, boolean up) throws InterruptedException {
        if (up) {
            for (int i = from; i <= to; i++) {
                if (i == 0) {
                    continue;
                }
                Thread.sleep(400);
                TimableOutput.println("ARRIVE-" + i);
                addPigQueue(i);
            }
        } else {
            for (int i = from; i >= to; i--) {
                if (i == 0) {
                    continue;
                }
                Thread.sleep(400);
                TimableOutput.println("ARRIVE-" + i);
                addPigQueue(i);
            }
        }
    }

    private synchronized void addPigQueue(int from)
            throws InterruptedException {
        int open = 0;
        for (int i = 0; i < queue.size(); i++) {
            Request r = queue.getRequest(i);
            if (from == r.getFrom()) {
                if (open == 0) {
                    TimableOutput.println("OPEN-" + from);
                    Thread.sleep(doorTime);
                    open = 1;
                }
                TimableOutput.println("IN-" + r.getId() + "-" + r.getFrom());
                queue.removeRequest(i);
                pigQueue.addAndSort(r);
                i--;
            }
        }

        for (int i = 0; i < pigQueue.size(); i++) {
            Request r = pigQueue.getRequest(i);
            if (from == r.getTo()) {
                if (open == 0) {
                    TimableOutput.println("OPEN-" + from);
                    Thread.sleep(doorTime);
                    open = 1;
                }
                TimableOutput.println("OUT-" + r.getId() + "-" + r.getTo());
                pigQueue.removeRequest(i);
                i--;
            }
        }

        if (open == 1) {
            Thread.sleep(doorTime);
            TimableOutput.println("CLOSE-" + from);
        }
    }

    private synchronized void addPigQueue(int from, boolean open) {
        for (int i = 0; i < queue.size(); i++) {
            Request r = queue.getRequest(i);
            if (from == r.getFrom()) {
                TimableOutput.println("IN-" + r.getId() + "-" + r.getFrom());
                queue.removeRequest(i);
                pigQueue.addAndSort(r);
                i--;
            }
        }
    }

    void runTime(int from, int to, boolean up) throws InterruptedException {
        int updateTo = to;
        int i;
        if (up) {
            for (i = from; (!pigQueue.isEmpty()) & (i <= updateTo); i++) {
                if (pigQueue.getLast().getTo() > updateTo) {
                    updateTo = pigQueue.getLast().getTo();
                }
                if (i == 0) {
                    continue;
                }
                upAndDown(i);
            }
        } else {
            for (i = from; (!pigQueue.isEmpty()) & (i >= updateTo); i--) {
                if (pigQueue.getFirst().getTo() < updateTo) {
                    updateTo = pigQueue.getFirst().getTo();
                }
                if (i == 0) {
                    continue;
                }
                upAndDown(i);
            }
        }
        if (up) {
            floor = i - 1;
        } else {
            floor = i + 1;
        }
    }

    private void upAndDown(int i) throws InterruptedException {
        Thread.sleep(400);
        TimableOutput.println("ARRIVE-" + i);
        addPigQueue(i);

        if (pigQueue.isEmpty()) {
            return;
        }
        if (pigQueue.getLast().getTo() == i
                | pigQueue.getFirst().getTo() == i) {
            TimableOutput.println("OPEN-" + i);
            Thread.sleep(doorTime);

            addPigQueue(i, true);

            for (int j = 0; j < pigQueue.size(); j++) {
                Request r = pigQueue.getRequest(j);
                if (r.getTo() == i) {
                    TimableOutput.println("OUT-" + r.getId() + "-" + r.getTo());
                    pigQueue.removeRequest(j);
                    j--;
                }
            }

            Thread.sleep(doorTime);
            TimableOutput.println("CLOSE-" + i);
        }
    }

    void work() throws InterruptedException {
        Thread.sleep(1);
        setTaker();
        if (taker == null) {
            return;
        }

        int from = floor;
        int to;
        if (get) {
            to = taker.getTo();
        } else {
            to = taker.getFrom();
        }
        boolean up;
        if (from < to) {
            from++;
            up = true;
        } else {
            from--;
            up = false;
        }

        waitTime(from, to, up);
        Thread.sleep(1);
        addPigQueue(to);

        from = to;
        if (pigQueue.isEmpty()) {
            floor = to;
            return;
        }
        if (taker.getFrom() - taker.getTo() < 0) {
            to = pigQueue.getLast().getTo();
        } else {
            to = pigQueue.getFirst().getTo();
        }

        if (from < to) {
            from++;
            up = true;
        } else {
            from--;
            up = false;
        }
        runTime(from, to, up);
    }
}
