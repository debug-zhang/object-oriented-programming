package myuml.function;

import com.oocourse.uml2.models.elements.UmlAssociationEnd;
import com.oocourse.uml2.models.elements.UmlElement;
import myuml.structure.classmodel.MyClass;
import myuml.structure.classmodel.MyInterface;
import myuml.structure.collaboration.MyInteraction;
import myuml.structure.statechart.MyStateMachine;

import java.util.HashMap;

public class Find {
    private static HashMap<String, UmlElement> idMap = new HashMap<>();

    private static HashMap<String, MyClass> classNameMap = new HashMap<>();
    private static HashMap<String, MyClass> classIdMap = new HashMap<>();
    private static HashMap<String, MyClass> classDupMap = new HashMap<>();
    private static HashMap<String, MyInterface> interfaceIdMap
            = new HashMap<>();

    private static HashMap<String, MyStateMachine> stateMachineNameMap
            = new HashMap<>();
    private static HashMap<String, MyStateMachine> stateMachineDupMap
            = new HashMap<>();

    private static HashMap<String, MyInteraction> interactionNameMap
            = new HashMap<>();
    private static HashMap<String, MyInteraction> interactionDupMap
            = new HashMap<>();

    public static void addId(UmlElement element) {
        idMap.put(element.getId(), element);
    }

    public static UmlElement getById(String id) {
        return idMap.get(id);
    }

    public static void addClass(UmlElement element) {
        MyClass c = new MyClass(element);
        classIdMap.put(element.getId(), c);
        if (!classNameMap.containsKey(element.getName())) {
            classNameMap.put(element.getName(), c);
        } else {
            classDupMap.put(element.getName(), c);
        }
    }

    public static boolean containsClassId(String id) {
        return classIdMap.containsKey(id);
    }

    public static boolean containsClassName(String name) {
        return classNameMap.containsKey(name);
    }

    public static boolean containsClassDupName(String name) {
        return classDupMap.containsKey(name);
    }

    public static int getClassCount() {
        return classIdMap.size();
    }

    public static MyClass getClassByName(String name) {
        return classNameMap.get(name);
    }

    public static MyClass getClassById(String id) {
        return classIdMap.get(id);
    }

    public static HashMap<String, MyClass> getClassIdMap() {
        return classIdMap;
    }

    public static void addInterfaceId(UmlElement element) {
        interfaceIdMap.put(element.getId(), new MyInterface(element));
    }

    public static boolean containsInterfaceId(String id) {
        return interfaceIdMap.containsKey(id);
    }

    public static MyInterface getInterfaceById(String id) {
        return interfaceIdMap.get(id);
    }

    public static HashMap<String, MyInterface> getInterfaceIdMap() {
        return interfaceIdMap;
    }

    public static String getIdByEndId(String id) {
        return ((UmlAssociationEnd) idMap.get(id)).getReference();
    }

    public static void addStateMachine(UmlElement element) {
        MyStateMachine s = new MyStateMachine(element);
        if (!stateMachineNameMap.containsKey(s.getName())) {
            stateMachineNameMap.put(s.getName(), s);
        } else {
            stateMachineDupMap.put(s.getName(), s);
        }
    }

    public static boolean containsStateMachineName(String name) {
        return stateMachineNameMap.containsKey(name);
    }

    public static boolean containsStateMachineDupName(String name) {
        return stateMachineDupMap.containsKey(name);
    }

    public static MyStateMachine getStateMachineByName(String name) {
        return stateMachineNameMap.get(name);
    }

    public static MyStateMachine getStateMachineById(String id) {
        return stateMachineNameMap.get(idMap.get(id).getName());
    }

    public static void addInteraction(UmlElement element) {
        MyInteraction s = new MyInteraction(element);
        if (!interactionNameMap.containsKey(s.getName())) {
            interactionNameMap.put(s.getName(), s);
        } else {
            interactionDupMap.put(s.getName(), s);
        }
    }

    public static boolean containsInteractionName(String name) {
        return interactionNameMap.containsKey(name);
    }

    public static boolean containsInteractionDupName(String name) {
        return interactionDupMap.containsKey(name);
    }

    public static MyInteraction getInteractionByName(String name) {
        return interactionNameMap.get(name);
    }

    public static MyInteraction getInteractionById(String id) {
        return interactionNameMap.get(idMap.get(id).getName());
    }
}
