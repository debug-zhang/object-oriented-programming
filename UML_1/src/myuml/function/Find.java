package myuml.function;

import com.oocourse.uml1.models.elements.UmlAssociationEnd;
import com.oocourse.uml1.models.elements.UmlElement;
import myuml.structure.MyClass;
import myuml.structure.MyInterface;

import java.util.HashMap;

public class Find {
    private static HashMap<String, UmlElement> idMap = new HashMap<>();
    private static HashMap<String, MyClass> classNameMap = new HashMap<>();
    private static HashMap<String, MyClass> classIdMap = new HashMap<>();
    private static HashMap<String, MyClass> classDupMap = new HashMap<>();
    private static HashMap<String, MyInterface> interfaceIdMap
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

    public static boolean containsDupName(String name) {
        return classDupMap.containsKey(name);
    }

    public static boolean containsInterfaceId(String id) {
        return interfaceIdMap.containsKey(id);
    }

    public static int getClassCount() {
        return classIdMap.size();
    }

    public static MyClass getClassByName(String className) {
        return classNameMap.get(className);
    }

    public static MyClass getClassById(String id) {
        return classIdMap.get(id);
    }

    public static void addInterfaceId(UmlElement element) {
        interfaceIdMap.put(element.getId(), new MyInterface(element));
    }

    public static MyInterface getInterfaceById(String id) {
        return interfaceIdMap.get(id);
    }

    public static String getIdByEndId(String id) {
        return ((UmlAssociationEnd) idMap.get(id)).getReference();
    }
}
