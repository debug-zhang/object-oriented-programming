package scheduler;

import elevator.Elevator;
import request.RequestQueue;

public class End {

    private boolean isNull;
    private RequestQueue queue;
    private Elevator elevatorA;
    private Elevator elevatorB;
    private Elevator elevatorC;

    public End(RequestQueue queue) {
        this.queue = queue;
        this.isNull = false;
    }

    public void setNull(boolean isNull) {
        this.isNull = isNull;
    }

    public void setElevatorA(Elevator elevatorA) {
        this.elevatorA = elevatorA;
    }

    public void setElevatorB(Elevator elevatorB) {
        this.elevatorB = elevatorB;
    }

    public void setElevatorC(Elevator elevatorC) {
        this.elevatorC = elevatorC;
    }

    public boolean notEnd() {
        return !(elevatorA.isEmpty() & elevatorB.isEmpty() & elevatorC.isEmpty()
                & queue.isEmpty() & isNull);
    }
}
