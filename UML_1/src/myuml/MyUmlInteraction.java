package myuml;

import com.oocourse.uml1.interact.common.AttributeClassInformation;
import com.oocourse.uml1.interact.common.AttributeQueryType;
import com.oocourse.uml1.interact.common.OperationQueryType;
import com.oocourse.uml1.interact.exceptions.user.AttributeDuplicatedException;
import com.oocourse.uml1.interact.exceptions.user.AttributeNotFoundException;
import com.oocourse.uml1.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml1.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml1.interact.format.UmlInteraction;
import com.oocourse.uml1.models.common.Visibility;
import com.oocourse.uml1.models.elements.UmlAssociation;
import com.oocourse.uml1.models.elements.UmlAttribute;
import com.oocourse.uml1.models.elements.UmlElement;
import com.oocourse.uml1.models.elements.UmlGeneralization;
import com.oocourse.uml1.models.elements.UmlParameter;
import myuml.function.Cache;
import myuml.function.Find;
import myuml.function.Relation;
import myuml.structure.MyClass;
import myuml.structure.MyOperation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MyUmlInteraction implements UmlInteraction {
    private Cache cache;

    public MyUmlInteraction(UmlElement... elements) {
        cache = new Cache();
        buildMap(elements);
    }

    @Override
    public int getClassCount() {
        return Find.getClassCount();
    }

    @Override
    public int getClassOperationCount(String className,
                                      OperationQueryType queryType)
            throws ClassNotFoundException, ClassDuplicatedException {
        cache.checkClass(className);
        return Find.getClassByName(className).getOperationCount(queryType);
    }

    @Override
    public int getClassAttributeCount(String className,
                                      AttributeQueryType queryType)
            throws ClassNotFoundException, ClassDuplicatedException {
        cache.checkClass(className);
        if (queryType.equals(AttributeQueryType.SELF_ONLY)) {
            return Find.getClassByName(className).getAttributeSize();
        } else if (cache.hasAttributeCount(className)) {
            return cache.getAttributeCount(className);
        } else {
            int count = 0;
            for (MyClass c : Find.getClassByName(className).getClassList()) {
                count += c.getAttributeSize();
            }
            cache.addAttributeCount(className, count);
            return count;
        }
    }

    @Override
    public int getClassAssociationCount(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        cache.checkClass(className);
        if (cache.hasAssociationCount(className)) {
            return cache.getAssociationCount(className);
        } else {
            int count = 0;
            for (MyClass c : Find.getClassByName(className).getClassList()) {
                count += c.getAssociationCount();
            }
            cache.addAssociationCount(className, count);
            return count;
        }
    }

    @Override
    public List<String> getClassAssociatedClassList(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        cache.checkClass(className);
        if (cache.hasAssociationClassName(className)) {
            return cache.getAssociationClassName(className);
        } else {
            HashSet<String> set = new HashSet<>();
            LinkedList<String> list = new LinkedList<>();
            for (MyClass c : Find.getClassByName(className).getClassList()) {
                set.addAll(c.getAssociatedClassIdSet());
            }
            for (String id : set) {
                list.add(Find.getById(id).getName());
            }
            cache.addAssociationClassName(className, list);
            return list;
        }
    }

    @Override
    public Map<Visibility, Integer> getClassOperationVisibility(
            String className, String operationName)
            throws ClassNotFoundException, ClassDuplicatedException {
        cache.checkClass(className);
        return Find.getClassByName(className).getOperationVisibility(
                operationName);
    }

    @Override
    public Visibility getClassAttributeVisibility(
            String className, String attributeName)
            throws ClassNotFoundException, ClassDuplicatedException,
            AttributeNotFoundException, AttributeDuplicatedException {
        cache.checkClass(className);
        Visibility visibility = null;
        int flag = 0;
        for (MyClass c : Find.getClassByName(className).getClassList()) {
            if (c.containsAttribute(attributeName)) {
                visibility = c.getAttributeVisibility(attributeName);
                flag++;
            }
            if (flag == 2) {
                throw new AttributeDuplicatedException(className,
                        attributeName);
            }
        }
        if (flag == 0) {
            throw new AttributeNotFoundException(className, attributeName);
        }
        return visibility;
    }

    @Override
    public String getTopParentClass(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        cache.checkClass(className);
        return Find.getClassByName(className).getTopFatherName();
    }

    @Override
    public List<String> getImplementInterfaceList(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        cache.checkClass(className);
        if (cache.hasInterfaceName(className)) {
            return cache.getInterfaceName(className);
        } else {
            HashSet<String> set = new HashSet<>();
            for (MyClass c : Find.getClassByName(className).getClassList()) {
                set.addAll(c.getInterfaceIdSet());
            }
            LinkedList<String> list = new LinkedList<>();
            for (String id : set) {
                list.add(Find.getById(id).getName());
            }
            cache.addInterfaceName(className, list);
            return list;
        }
    }

    @Override
    public List<AttributeClassInformation> getInformationNotHidden(
            String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        cache.checkClass(className);

        if (cache.hasNotHidden(className)) {
            return cache.getNotHidden(className);
        } else {
            LinkedList<AttributeClassInformation> list = new LinkedList<>();
            for (MyClass c : Find.getClassByName(className).getClassList()) {
                list.addAll(c.getInformationNotHidden());
            }
            cache.addNotHidden(className, list);
            return list;
        }
    }

    private void buildMap(UmlElement[] elements) {
        HashMap<String, MyOperation> operationMap = new HashMap<>();
        LinkedList<UmlGeneralization> generalizationList = new LinkedList<>();
        LinkedList<UmlAssociation> associationList = new LinkedList<>();
        LinkedList<UmlParameter> parameterList = new LinkedList<>();
        LinkedList<UmlAttribute> attributeList = new LinkedList<>();
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
                    generalizationList.add((UmlGeneralization) uml);
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
                default:
                    break;
            }
        }
        for (UmlParameter p : parameterList) {
            operationMap.get(p.getParentId()).addParameter(p);
        }
        for (UmlAssociation a : associationList) {
            Relation.addAssoication(a);
        }
        for (UmlGeneralization g : generalizationList) {
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
}
