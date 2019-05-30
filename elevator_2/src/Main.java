import com.oocourse.TimableOutput;
import com.oocourse.elevator2.ElevatorInput;
import com.oocourse.elevator2.PersonRequest;
import elevator.Elevator;
import elevator.ElevatorThread;
import request.Request;
import request.RequestQueue;

public class Main {
    public static void main(String[] args) throws Exception {
        TimableOutput.initStartTimestamp();

        RequestQueue queue = new RequestQueue();
        Elevator elevator = new Elevator(queue);

        ElevatorThread elevatorThread = new ElevatorThread(elevator,queue);
        elevatorThread.start();

        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        while (true) {
            PersonRequest personRequest = elevatorInput.nextPersonRequest();
            if (personRequest == null) {
                elevatorThread.setEnd(true);
                break;
            } else {
                Request request = new Request(personRequest.getPersonId(),
                        personRequest.getFromFloor(),
                        personRequest.getToFloor());
                queue.addRequest(request);
            }
        }
        elevatorInput.close();
    }
}
