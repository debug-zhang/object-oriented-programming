package myuml.structure.statechart;

import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlEvent;
import com.oocourse.uml2.models.elements.UmlOpaqueBehavior;
import com.oocourse.uml2.models.elements.UmlTransition;

public class MyTransition {
    private UmlTransition transition;
    private UmlEvent event;
    private UmlOpaqueBehavior opaqueBehavior;

    public MyTransition(UmlElement transition) {
        this.transition = (UmlTransition) transition;
    }

    public String getName() {
        return transition.getName();
    }

    public String getId() {
        return transition.getId();
    }

    public String getParaentId() {
        return transition.getParentId();
    }

    public String getSource() {
        return transition.getSource();
    }

    public String getTarget() {
        return transition.getTarget();
    }

    public void setEvent(UmlEvent event) {
        this.event = event;
    }

    public void setOpaqueBehavior(UmlOpaqueBehavior opaqueBehavior) {
        this.opaqueBehavior = opaqueBehavior;
    }
}
