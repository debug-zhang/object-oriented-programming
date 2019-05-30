package elevator;

import com.oocourse.TimableOutput;
import request.Request;
import request.RequestQueue;

public class Elevator {
    private char name;
    private int size;
    private int runTime;

    private RequestQueue baseQueue;
    private RequestQueue queue;
    private RequestQueue pigQueue;

    private int doorTime = 200;

    private int floor;
    private Request taker;
    private boolean get;

    Elevator(RequestQueue baseQueue, RequestQueue queue, int runTime,
             char name,
             int size) {
        this.name = name;
        this.size = size;
        this.runTime = runTime;

        this.baseQueue = baseQueue;
        this.queue = queue;
        this.pigQueue = new RequestQueue();

        this.floor = 1;
        this.taker = null;
        get = false;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty() & pigQueue.isEmpty();
    }

    private void arriveFloor(int floor) throws InterruptedException {
        Thread.sleep(runTime);
        TimableOutput.println("ARRIVE-" + floor + "-" + name);
    }

    private void openDoor(int floor) throws InterruptedException {
        TimableOutput.println("OPEN-" + floor + "-" + name);
        Thread.sleep(doorTime);
    }

    private void closeDoor(int floor) throws InterruptedException {
        Thread.sleep(doorTime);
        TimableOutput.println("CLOSE-" + floor + "-" + name);
    }

    private boolean notFull() {
        return pigQueue.size() < size;
    }

    private synchronized void setTaker() {
        get = false;
        if (!pigQueue.isEmpty()) {
            taker = pigQueue.getFirst();
            get = true;
        } else if (!queue.isEmpty()) {
            taker = queue.getFirst();
            queue.getPiggy(taker);
        } else {
            taker = null;
        }
    }

    private void waitTime(int from, int to, boolean up)
            throws InterruptedException {
        if (up) {
            for (int i = from; i <= to; i++) {
                if (i == 0) {
                    continue;
                }
                arriveFloor(i);
                addPigQueue(i, false);
            }
        } else {
            for (int i = from; i >= to; i--) {
                if (i == 0) {
                    continue;
                }
                arriveFloor(i);
                addPigQueue(i, false);
            }
        }
    }

    private synchronized void addPigQueue(int from, boolean openAndClose)
            throws InterruptedException {
        if (openAndClose) {
            for (int i = 0; i < pigQueue.size(); i++) {
                Request r = pigQueue.getRequest(i);
                if (from == r.getTo()) {
                    i = leaveAndTransfer(i, r);
                }
            }

            for (int i = 0; notFull() && (i < queue.size()); i++) {
                Request r = queue.getRequest(i);
                if (from == r.getFrom()) {
                    r.inElevator(name);
                    queue.removeRequest(r);
                    pigQueue.addAndSort(r);
                    i--;
                }
            }
        } else {

            int open = 0;

            for (int i = 0; i < pigQueue.size(); i++) {
                Request r = pigQueue.getRequest(i);
                if (from == r.getTo()) {
                    if (open == 0) {
                        openDoor(from);
                        open = 1;
                    }
                    i = leaveAndTransfer(i, r);
                }
            }

            for (int i = 0; notFull() && (i < queue.size()); i++) {
                Request r = queue.getRequest(i);
                if (from == r.getFrom()) {
                    if (open == 0) {
                        openDoor(from);
                        open = 1;
                    }
                    r.inElevator(name);
                    queue.removeRequest(r);
                    pigQueue.addAndSort(r);
                    i--;
                }
            }

            if (open == 1) {
                closeDoor(from);
            }
        }
    }

    private synchronized int leaveAndTransfer(int i, Request r) {
        r.outElevator(name);
        if (r.isTransfer()) {
            r.setFromAndTo();
            baseQueue.addRequest(r);
        }
        pigQueue.removeRequest(r);
        return i - 1;
    }

    private void runTime(int from, int to, boolean up)
            throws InterruptedException {
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
        arriveFloor(i);
        addPigQueue(i, false);

        if (pigQueue.isEmpty()) {
            return;
        }

        if (pigQueue.getLast().getTo() == i
                | pigQueue.getFirst().getTo() == i) {
            openDoor(i);
            addPigQueue(i, true);
            closeDoor(i);
        }
    }

    private boolean isUp(int from, int to) {
        return to > from;
    }

    void work() throws InterruptedException {
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
        boolean up = isUp(from, to);
        if (up) {
            from++;
        } else {
            from--;
        }

        waitTime(from, to, up);
        Thread.sleep(1);
        addPigQueue(to, false);

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

        up = isUp(from, to);
        if (up) {
            from++;
        } else {
            from--;
        }
        runTime(from, to, up);
    }
}
