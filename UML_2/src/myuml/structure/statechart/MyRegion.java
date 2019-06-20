package myuml.structure.statechart;

import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlFinalState;
import com.oocourse.uml2.models.elements.UmlPseudostate;
import com.oocourse.uml2.models.elements.UmlRegion;
import com.oocourse.uml2.models.elements.UmlState;

import java.util.HashMap;
import java.util.HashSet;

public class MyRegion {
    private UmlRegion region;
    private int pseudostate;
    private int finalState;
    private HashMap<String, UmlState> stateNameMap;
    private HashMap<String, UmlState> stateDupMap;
    private int stateSize;
    private int transitionSize;
    private HashMap<String, HashSet<String>> transitionMap;

    public MyRegion(UmlElement region) {
        this.region = (UmlRegion) region;
        this.pseudostate = 0;
        this.finalState = 0;
        this.stateNameMap = new HashMap<>();
        this.stateDupMap = new HashMap<>();
        this.stateSize = 0;
        this.transitionMap = new HashMap<>();
        this.transitionSize = 0;
    }

    public String getName() {
        return region.getName();
    }

    public String getId() {
        return region.getId();
    }

    public String getParaentId() {
        return region.getParentId();
    }

    public void setPseudostate(UmlPseudostate pseudostate) {
        this.pseudostate = 1;
    }

    public void setFinalState(UmlFinalState finalState) {
        this.finalState = 1;
    }

    public void addState(UmlState umlState) {
        stateSize++;
        if (stateNameMap.containsKey(umlState.getName())) {
            stateDupMap.put(umlState.getName(), umlState);
        } else {
            stateNameMap.put(umlState.getName(), umlState);
        }
    }

    public void addTransition(MyTransition transition) {
        transitionSize++;
        String source = transition.getSource();
        if (!transitionMap.containsKey(source)) {
            transitionMap.put(source, new HashSet<>());
        }
        transitionMap.get(source).add(transition.getTarget());
    }

    public int getStateCount() {
        return stateSize + pseudostate + finalState;
    }

    public int getTransitionCount() {
        return transitionSize;
    }

    public boolean containsState(String stateName) {
        return stateNameMap.containsKey(stateName);
    }

    public boolean containsDupState(String stateName) {
        return stateDupMap.containsKey(stateName);
    }

    public int getSubsequentStateCount(String stateName) {
        if (transitionMap.containsKey(stateNameMap.get(stateName).getId())) {
            HashSet<String> set = new HashSet<>(
                    transitionMap.get(stateNameMap.get(stateName).getId()));
            HashSet<String> temp = new HashSet<>(
                    transitionMap.get(stateNameMap.get(stateName).getId()));

            int preSize = 0;
            while (preSize != set.size()) {
                preSize = set.size();
                for (String id : set) {
                    if (transitionMap.containsKey(id)) {
                        temp.addAll(transitionMap.get(id));
                    }
                }
                set.addAll(temp);
            }

            return set.size();
        } else {
            return 0;
        }
    }
}
