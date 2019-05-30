package scheduler;

import request.Request;
import request.RequestQueue;

public class SchedulerThread extends Thread {
    private RequestQueue queue;
    private RequestQueue queueA;
    private RequestQueue queueB;
    private RequestQueue queueC;

    private End end;

    private final int[] floorA = {-3, -2, -1, 1, 15, 16, 17, 18, 19, 20};
    private final int[]
            floorB = {-2, -1, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    private final int[] floorC = {1, 3, 5, 7, 9, 11, 13, 15};

    public SchedulerThread() {
        this.queue = new RequestQueue();
        this.queueA = new RequestQueue();
        this.queueB = new RequestQueue();
        this.queueC = new RequestQueue();
    }

    public void setEnd(End end) {
        this.end = end;
    }

    public RequestQueue getQueue() {
        return queue;
    }

    public RequestQueue getQueueA() {
        return queueA;
    }

    public RequestQueue getQueueB() {
        return queueB;
    }

    public RequestQueue getQueueC() {
        return queueC;
    }

    private boolean contains(int[] gather, int floor) {
        for (int i1 : gather) {
            if (i1 == floor) {
                return true;
            }
        }
        return false;
    }

    private boolean contains(int[] gather, int from, int to) {
        for (int i = 0; i < gather.length; i++) {
            if (gather[i] == from) {
                for (i = 0; i < gather.length; i++) {
                    if (gather[i] == to) {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    private void setTemp(Request request) {
        if (request.getFrom() < 7) {
            request.setTempTo(1);
        } else {
            request.setTempTo(15);
        }
    }

    @Override
    public void run() {
        Request r;
        int from;
        int to;
        while (end.notEnd()) {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            while (!queue.isEmpty() && (queueA.size() < 5
                    || queueB.size() < 7 || queueC.size() < 6)) {
                r = queue.getFirst();
                from = r.getFrom();
                to = r.getTo();
                if (contains(floorA, from, to)) {
                    if (queueA.size() < 5) {
                        queueA.addAndSort(r);
                        queue.removeRequest();
                    }
                } else if (contains(floorB, from, to)) {
                    if (queueB.size() < 7) {
                        queueB.addAndSort(r);
                        queue.removeRequest();
                    }
                } else if (contains(floorC, from, to)) {
                    if (queueC.size() < 6) {
                        queueC.addAndSort(r);
                        queue.removeRequest();
                    }
                } else if (contains(floorA, from)) {
                    if (queueA.size() < 5) {
                        setTemp(r);
                        queueA.addAndSort(r);
                        queue.removeRequest();
                    }
                } else if (contains(floorB, from)) {
                    if (queueB.size() < 7) {
                        setTemp(r);
                        queueB.addAndSort(r);
                        queue.removeRequest();
                    }
                } else if (contains(floorC, from)) {
                    if (queueC.size() < 6) {
                        setTemp(r);
                        queueC.addAndSort(r);
                        queue.removeRequest();
                    }
                } else {
                    queue.addRequest(r);
                    queue.removeRequest();
                }
            }
        }
    }
}
