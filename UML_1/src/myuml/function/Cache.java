package myuml.function;

import com.oocourse.uml1.interact.common.AttributeClassInformation;
import com.oocourse.uml1.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml1.interact.exceptions.user.ClassNotFoundException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Cache {
    private HashMap<String, Integer> attributeCount;
    private HashMap<String, Integer> associationCount;
    private HashMap<String, LinkedList<String>> associationClassName;
    private HashMap<String, LinkedList<AttributeClassInformation>> notHidden;
    private HashMap<String, LinkedList<String>> interfaceName;

    public Cache() {
        attributeCount = new HashMap<>();
        associationCount = new HashMap<>();
        associationClassName = new HashMap<>();
        notHidden = new HashMap<>();
        interfaceName = new HashMap<>();
    }

    public void checkClass(String className)
            throws ClassDuplicatedException, ClassNotFoundException {
        if (!Find.containsClassName(className)) {
            throw new ClassNotFoundException(className);
        } else if (Find.containsDupName(className)) {
            throw new ClassDuplicatedException(className);
        }
    }

    public boolean hasNotHidden(String className) {
        return notHidden.containsKey(className);
    }

    public List<AttributeClassInformation> getNotHidden(String className) {
        return notHidden.get(className);
    }

    public void addNotHidden(String className,
                             LinkedList<AttributeClassInformation> list) {
        notHidden.put(className, list);
    }

    public boolean hasAttributeCount(String className) {
        return attributeCount.containsKey(className);
    }

    public int getAttributeCount(String className) {
        return attributeCount.get(className);
    }

    public void addAttributeCount(String className, int count) {
        attributeCount.put(className, count);
    }

    public boolean hasAssociationCount(String className) {
        return associationCount.containsKey(className);
    }

    public int getAssociationCount(String className) {
        return associationCount.get(className);
    }

    public void addAssociationCount(String className, int count) {
        associationCount.put(className, count);
    }

    public boolean hasAssociationClassName(String className) {
        return associationClassName.containsKey(className);
    }

    public LinkedList<String> getAssociationClassName(String className) {
        return associationClassName.get(className);
    }

    public void addAssociationClassName(String className,
                                        LinkedList<String> list) {
        associationClassName.put(className, list);
    }

    public boolean hasInterfaceName(String className) {
        return interfaceName.containsKey(className);
    }

    public LinkedList<String> getInterfaceName(String className) {
        return interfaceName.get(className);
    }

    public void addInterfaceName(String className, LinkedList<String> list) {
        interfaceName.put(className, list);
    }
}
