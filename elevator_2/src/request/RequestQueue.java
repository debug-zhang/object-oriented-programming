package request;

import java.util.Vector;

public class RequestQueue {
    private Vector<Request> queue;
    private int count;

    public RequestQueue() {
        this.queue = new Vector<>();
        this.count = 0;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int getCount() {
        return count;
    }

    public Vector<Request> getQueue() {
        return queue;
    }

    public Request getFirst() {
        if (queue.isEmpty()) {
            return null;
        }
        return queue.firstElement();
    }

    public Request getLast() {
        if (queue.isEmpty()) {
            return null;
        }
        return queue.lastElement();
    }

    public void addRequest(Request request) {
        this.queue.add(request);
    }

    public synchronized void addAndSort(Request request) {
        if (queue.isEmpty()) {
            queue.add(request);
            return;
        } else {
            for (Request r : queue) {
                if (request.getTo() <= r.getTo()) {
                    queue.add(queue.indexOf(r), request);
                    return;
                }
            }
        }
        queue.add(request);
    }

    public Request getRequest(int index) {
        return queue.get(index);
    }

    public int size() {
        return queue.size();
    }

    public void removeRequest(int index) {
        this.queue.remove(index);
    }

    public void removeRequest(Request request) {
        this.queue.remove(request);
    }

    public synchronized void getPiggy(Request taker) {
        boolean up1 = taker.getTo() - taker.getFrom() > 0;
        RequestQueue queue1 = new RequestQueue();
        RequestQueue queue2 = new RequestQueue();
        for (int i = 0; i < queue.size(); i++) {
            Request r = queue.get(i);
            boolean up2 = r.getTo() - r.getFrom() > 0;
            boolean synchrony = r.getFrom() - taker.getFrom() >= 0;
            if ((up1 == up2) && (up1 == synchrony)) {
                queue1.addAndSort(r);
                count++;
            } else {
                queue2.addRequest(r);
            }
        }
        for (Request r : queue2.getQueue()) {
            queue1.addRequest(r);
        }
    }
}
