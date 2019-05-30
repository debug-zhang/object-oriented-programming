package request;

import java.util.ArrayList;

public class RequestQueue {
    private ArrayList<Request> queue;

    public RequestQueue() {
        this.queue = new ArrayList<Request>();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public synchronized void addRequest(Request request) {
        this.queue.add(request);
    }

    public synchronized Request getRequest() {
        return queue.get(0);
    }

    public synchronized void removeRequest() {
        this.queue.remove(0);
    }
}
