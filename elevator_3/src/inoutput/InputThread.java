package inoutput;

import com.oocourse.elevator3.ElevatorInput;
import com.oocourse.elevator3.PersonRequest;
import request.Request;
import request.RequestQueue;
import scheduler.End;

import java.io.IOException;

public class InputThread extends Thread {
    private RequestQueue queue;
    private End end;

    public InputThread(RequestQueue queue, End end) {
        this.queue = queue;
        this.end = end;
    }

    @Override
    public void run() {
        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        while (true) {
            PersonRequest personRequest = elevatorInput.nextPersonRequest();
            if (personRequest == null) {
                end.setNull(true);
                break;
            } else {
                Request request = new Request(personRequest.getPersonId(),
                        personRequest.getFromFloor(),
                        personRequest.getToFloor());
                queue.addRequest(request);
            }
        }
        try {
            elevatorInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
