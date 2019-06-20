package myuml;

import com.oocourse.uml2.interact.common.AttributeClassInformation;
import com.oocourse.uml2.interact.common.AttributeQueryType;
import com.oocourse.uml2.interact.common.OperationQueryType;
import com.oocourse.uml2.interact.exceptions.user.AttributeDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.AttributeNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.InteractionDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.InteractionNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.LifelineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.LifelineNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.PreCheckRuleException;
import com.oocourse.uml2.interact.exceptions.user.StateDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.StateNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.UmlRule002Exception;
import com.oocourse.uml2.interact.exceptions.user.UmlRule008Exception;
import com.oocourse.uml2.interact.exceptions.user.UmlRule009Exception;
import com.oocourse.uml2.interact.format.UmlGeneralInteraction;
import com.oocourse.uml2.models.common.Visibility;
import com.oocourse.uml2.models.elements.UmlAssociation;
import com.oocourse.uml2.models.elements.UmlAttribute;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlFinalState;
import com.oocourse.uml2.models.elements.UmlGeneralization;
import com.oocourse.uml2.models.elements.UmlLifeline;
import com.oocourse.uml2.models.elements.UmlMessage;
import com.oocourse.uml2.models.elements.UmlParameter;
import com.oocourse.uml2.models.elements.UmlPseudostate;
import com.oocourse.uml2.models.elements.UmlState;
import myuml.function.Cache;
import myuml.function.Find;
import myuml.function.Relation;
import myuml.structure.classmodel.MyOperation;
import myuml.structure.classmodel.MyUmlClassModelInteraction;
import myuml.structure.classmodel.MyUmlStandardPreCheck;
import myuml.structure.collaboration.MyUmlCollaborationInteraction;
import myuml.structure.statechart.MyRegion;
import myuml.structure.statechart.MyTransition;
import myuml.structure.statechart.MyUmlStateChartInteraction;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MyUmlGeneralInteraction implements UmlGeneralInteraction {
    private MyUmlClassModelInteraction classModel;
    private MyUmlCollaborationInteraction collaboration;
    private MyUmlStateChartInteraction stateChart;
    private MyUmlStandardPreCheck standardPreCheck;

    public MyUmlGeneralInteraction(UmlElement... elements) {
        classify(elements);
        buildClass();
        buildState();
        buildInteraction();
        Cache cache = new Cache();
        classModel = new MyUmlClassModelInteraction(cache);
        collaboration = new MyUmlCollaborationInteraction(cache);
        stateChart = new MyUmlStateChartInteraction(cache);
        standardPreCheck = new MyUmlStandardPreCheck();
    }

    @Override
    public int getClassCount() {
        return classModel.getClassCount();
    }

    @Override
    public int getClassOperationCount(String className,
                                      OperationQueryType queryType)
            throws ClassNotFoundException, ClassDuplicatedException {
        return classModel.getClassOperationCount(className, queryType);
    }

    @Override
    public int getClassAttributeCount(String className,
                                      AttributeQueryType queryType)
            throws ClassNotFoundException, ClassDuplicatedException {
        return classModel.getClassAttributeCount(className, queryType);
    }

    @Override
    public int getClassAssociationCount(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        return classModel.getClassAssociationCount(className);
    }

    @Override
    public List<String> getClassAssociatedClassList(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        return classModel.getClassAssociatedClassList(className);
    }

    @Override
    public Map<Visibility, Integer> getClassOperationVisibility(
            String className, String operationName)
            throws ClassNotFoundException, ClassDuplicatedException {
        return classModel.getClassOperationVisibility(
                className, operationName);
    }

    @Override
    public Visibility getClassAttributeVisibility(
            String className, String attributeName)
            throws ClassNotFoundException, ClassDuplicatedException,
            AttributeNotFoundException, AttributeDuplicatedException {
        return classModel.getClassAttributeVisibility(
                className, attributeName);
    }

    @Override
    public String getTopParentClass(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        return classModel.getTopParentClass(className);
    }

    @Override
    public List<String> getImplementInterfaceList(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        return classModel.getImplementInterfaceList(className);
    }

    @Override
    public List<AttributeClassInformation> getInformationNotHidden(
            String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        return classModel.getInformationNotHidden(className);
    }

    @Override
    public int getParticipantCount(String interactionName)
            throws InteractionNotFoundException,
            InteractionDuplicatedException {
        return collaboration.getParticipantCount(interactionName);
    }

    @Override
    public int getMessageCount(String interactionName)
            throws InteractionNotFoundException,
            InteractionDuplicatedException {
        return collaboration.getMessageCount(interactionName);
    }

    @Override
    public int getIncomingMessageCount(String interactionName,
                                       String lifelineName)
            throws InteractionNotFoundException, InteractionDuplicatedException,
            LifelineNotFoundException, LifelineDuplicatedException {
        return collaboration.getIncomingMessageCount(
                interactionName, lifelineName);
    }

    public void checkForAllRules() throws PreCheckRuleException {
        standardPreCheck.checkPrepare();
        standardPreCheck.checkForUml002();
        standardPreCheck.checkForUml008();
        standardPreCheck.checkForUml009();
    }

    @Override
    public void checkForUml002() throws UmlRule002Exception {
        standardPreCheck.checkForUml002();
    }

    @Override
    public void checkForUml008() throws UmlRule008Exception {
        standardPreCheck.checkForUml008();
    }

    @Override
    public void checkForUml009() throws UmlRule009Exception {
        standardPreCheck.checkForUml009();
    }

    @Override
    public int getStateCount(String stateMachineName)
            throws StateMachineNotFoundException,
            StateMachineDuplicatedException {
        return stateChart.getStateCount(stateMachineName);
    }

    @Override
    public int getTransitionCount(String stateMachineName)
            throws StateMachineNotFoundException,
            StateMachineDuplicatedException {
        return stateChart.getTransitionCount(stateMachineName);
    }

    @Override
    public int getSubsequentStateCount(String stateMachineName,
                                       String stateName)
            throws StateMachineNotFoundException,
            StateMachineDuplicatedException,
            StateNotFoundException, StateDuplicatedException {
        return stateChart.getSubsequentStateCount(stateMachineName, stateName);
    }

    private HashMap<String, MyOperation> operationMap = new HashMap<>();
    private LinkedList<UmlGeneralization> generalizatiList = new LinkedList<>();
    private LinkedList<UmlAssociation> associationList = new LinkedList<>();
    private LinkedList<UmlParameter> parameterList = new LinkedList<>();
    private LinkedList<UmlAttribute> attributeList = new LinkedList<>();

    private LinkedList<UmlState> stateList = new LinkedList<>();
    private LinkedList<UmlPseudostate> pseudostateList = new LinkedList<>();
    private LinkedList<UmlFinalState> finalStateList = new LinkedList<>();
    private LinkedList<MyTransition> transitionList = new LinkedList<>();
    private HashMap<String, MyRegion> regionMap = new HashMap<>();

    private LinkedList<UmlLifeline> lifelineList = new LinkedList<>();
    private LinkedList<UmlMessage> messageList = new LinkedList<>();

    private void classify(UmlElement[] elements) {
        for (UmlElement uml : elements) {
            Find.addId(uml);
            switch (uml.getElementType()) {
                case UML_CLASS:
                    Find.addClass(uml);
                    break;
                case UML_INTERFACE:
                    Find.addInterfaceId(uml);
                    break;
                case UML_GENERALIZATION:
                    generalizatiList.add((UmlGeneralization) uml);
                    break;
                case UML_INTERFACE_REALIZATION:
                    Relation.addInterfaceRealition(uml);
                    break;
                case UML_OPERATION:
                    operationMap.put(uml.getId(), new MyOperation(uml));
                    break;
                case UML_ATTRIBUTE:
                    attributeList.add((UmlAttribute) uml);
                    break;
                case UML_PARAMETER:
                    parameterList.add((UmlParameter) uml);
                    break;
                case UML_ASSOCIATION:
                    associationList.add((UmlAssociation) uml);
                    break;
                case UML_STATE_MACHINE:
                    Find.addStateMachine(uml);
                    break;
                case UML_STATE:
                    stateList.add((UmlState) uml);
                    break;
                case UML_PSEUDOSTATE:
                    pseudostateList.add((UmlPseudostate) uml);
                    break;
                case UML_FINAL_STATE:
                    finalStateList.add((UmlFinalState) uml);
                    break;
                case UML_TRANSITION:
                    transitionList.add(new MyTransition(uml));
                    break;
                case UML_REGION:
                    regionMap.put(uml.getId(), new MyRegion(uml));
                    break;
                case UML_INTERACTION:
                    Find.addInteraction(uml);
                    break;
                case UML_LIFELINE:
                    lifelineList.add((UmlLifeline) uml);
                    break;
                case UML_MESSAGE:
                    messageList.add((UmlMessage) uml);
                    break;
                default:
                    break;
            }
        }
    }

    private void buildClass() {
        for (UmlParameter p : parameterList) {
            operationMap.get(p.getParentId()).addParameter(p);
        }
        for (UmlAssociation a : associationList) {
            Relation.addAssoication(a);
        }
        for (UmlGeneralization g : generalizatiList) {
            Relation.addGeneralization(g);
        }
        for (UmlAttribute a : attributeList) {
            if (Find.containsClassId(a.getParentId())) {
                Find.getClassById(a.getParentId()).addAttribute(a);
            }
        }
        for (String id : operationMap.keySet()) {
            MyOperation o = operationMap.get(id);
            if (Find.containsClassId(o.getClassId())) {
                Find.getClassById(o.getClassId()).addOperation(o);
            }
        }
    }

    private void buildState() {
        for (UmlState s : stateList) {
            regionMap.get(s.getParentId()).addState(s);
        }
        for (UmlPseudostate p : pseudostateList) {
            regionMap.get(p.getParentId()).setPseudostate(p);
        }
        for (UmlFinalState f : finalStateList) {
            regionMap.get(f.getParentId()).setFinalState(f);
        }
        for (MyTransition t : transitionList) {
            regionMap.get(t.getParaentId()).addTransition(t);
        }
        for (String id : regionMap.keySet()) {
            Find.getStateMachineById(regionMap.get(id).getParaentId(
            )).setRegion(regionMap.get(id));
        }
    }

    private void buildInteraction() {
        for (UmlLifeline l : lifelineList) {
            Find.getInteractionById(l.getParentId()).addLifeline(l);
        }
        for (UmlMessage m : messageList) {
            Find.getInteractionById(m.getParentId()).addmessage(m);
        }
    }
}
