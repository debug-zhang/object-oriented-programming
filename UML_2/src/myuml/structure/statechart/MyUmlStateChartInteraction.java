package myuml.structure.statechart;

import com.oocourse.uml2.interact.exceptions.user.StateDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.StateNotFoundException;
import com.oocourse.uml2.interact.format.UmlStateChartInteraction;
import myuml.function.Cache;
import myuml.function.Find;

public class MyUmlStateChartInteraction implements UmlStateChartInteraction {
    private Cache cache;

    public MyUmlStateChartInteraction(Cache cache) {
        this.cache = cache;
    }

    @Override
    public int getStateCount(String stateMachineName)
            throws StateMachineNotFoundException,
            StateMachineDuplicatedException {
        cache.checkStateMachine(stateMachineName);
        return Find.getStateMachineByName(stateMachineName).getStateCount();
    }

    @Override
    public int getTransitionCount(String stateMachineName)
            throws StateMachineNotFoundException,
            StateMachineDuplicatedException {
        cache.checkStateMachine(stateMachineName);
        return Find.getStateMachineByName(
                stateMachineName).getTransitionCount();
    }

    @Override
    public int getSubsequentStateCount(String stateMachineName,
                                       String stateName)
            throws StateMachineNotFoundException,
            StateMachineDuplicatedException,
            StateNotFoundException, StateDuplicatedException {
        cache.checkStateMachine(stateMachineName);
        String name = stateMachineName + "_" + stateName;
        if (cache.hasSubStateCount(name)) {
            return cache.getSubStateCount(name);
        } else {
            MyStateMachine s = Find.getStateMachineByName(stateMachineName);
            if (!s.containsState(stateName)) {
                throw new StateNotFoundException(stateMachineName, stateName);
            } else if (s.containsDupState(stateName)) {
                throw new StateDuplicatedException(stateMachineName, stateName);
            } else {
                cache.addSubStateCount(name,
                        s.getSubsequentStateCount(stateName));
                return cache.getSubStateCount(name);
            }
        }
    }
}
