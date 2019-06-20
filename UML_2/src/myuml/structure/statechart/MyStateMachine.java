package myuml.structure.statechart;

import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlStateMachine;

public class MyStateMachine {
    private UmlStateMachine stateMachine;
    private MyRegion region;

    public MyStateMachine(UmlElement stateMachine) {
        this.stateMachine = (UmlStateMachine) stateMachine;
    }

    public String getName() {
        return stateMachine.getName();
    }

    public String getId() {
        return stateMachine.getId();
    }

    public void setRegion(MyRegion region) {
        this.region = region;
    }

    public int getStateCount() {
        return region.getStateCount();
    }

    public int getTransitionCount() {
        return region.getTransitionCount();
    }

    public boolean containsState(String stateName) {
        return region.containsState(stateName);
    }

    public boolean containsDupState(String stateName) {
        return region.containsDupState(stateName);
    }

    public int getSubsequentStateCount(String stateName) {
        return region.getSubsequentStateCount(stateName);
    }
}
