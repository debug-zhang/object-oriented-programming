package myuml.function;

import com.oocourse.uml1.models.elements.UmlElement;
import myuml.structure.MyAssociation;
import myuml.structure.MyGeneralization;
import myuml.structure.MyInterfaceRealition;

import java.util.HashSet;

public class Relation {
    private static MyGeneralization generMap = new MyGeneralization();
    private static MyInterfaceRealition realiMap = new MyInterfaceRealition();
    private static MyAssociation assocMap = new MyAssociation();

    public static boolean hasGeneralization(String id) {
        return generMap.contains(id);
    }

    public static boolean hasInterfaceRealition(String id) {
        return realiMap.contains(id);
    }

    public static boolean hasAssoication(String id) {
        return assocMap.contains(id);
    }

    public static boolean hasAssoicationClass(String id) {
        return assocMap.containsClass(id);
    }

    public static void addGeneralization(UmlElement element) {
        generMap.add(element);
    }

    public static void addInterfaceRealition(UmlElement element) {
        realiMap.add(element);
    }

    public static void addAssoication(UmlElement element) {
        assocMap.add(element);
    }

    public static UmlElement getClassFather(String id) {
        return generMap.getClassFather(id);
    }

    public static HashSet<String> getInterfaceFather(String id) {
        return generMap.getInterfaceFather(id);
    }

    public static HashSet<String> getInterfaceIdSet(String id) {
        return realiMap.getIdSet(id);
    }

    public static int getAssociationEndSize(String id) {
        return assocMap.getEndSize(id);
    }

    public static HashSet<String> getAssociationClassIdSet(String id) {
        return assocMap.getClassIdSet(id);
    }
}
