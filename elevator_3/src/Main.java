import com.oocourse.TimableOutput;
import elevator.ElevatorThread;
import inoutput.InputThread;
import scheduler.End;
import scheduler.SchedulerThread;

public class Main {
    public static void main(String[] args) {
        TimableOutput.initStartTimestamp();

        SchedulerThread scheduler = new SchedulerThread();

        End end = new End(scheduler.getQueue());
        scheduler.setEnd(end);

        ElevatorThread elevatorA = new ElevatorThread(scheduler.getQueue(),
                scheduler.getQueueA(), 'A', 400, 6, end);
        ElevatorThread elevatorB = new ElevatorThread(scheduler.getQueue(),
                scheduler.getQueueB(), 'B', 500, 8, end);
        ElevatorThread elevatorC = new ElevatorThread(scheduler.getQueue(),
                scheduler.getQueueC(), 'C', 600, 7, end);

        end.setElevatorA(elevatorA.getElevator());
        end.setElevatorB(elevatorB.getElevator());
        end.setElevatorC(elevatorC.getElevator());

        InputThread input = new InputThread(scheduler.getQueue(), end);

        input.start();
        scheduler.start();
        elevatorA.start();
        elevatorB.start();
        elevatorC.start();
    }
}
