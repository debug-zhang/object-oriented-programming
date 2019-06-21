package myuml.structure.classmodel;

import com.oocourse.uml2.interact.common.AttributeClassInformation;
import com.oocourse.uml2.interact.common.OperationQueryType;
import com.oocourse.uml2.models.common.Visibility;
import com.oocourse.uml2.models.elements.UmlAttribute;
import com.oocourse.uml2.models.elements.UmlClass;
import com.oocourse.uml2.models.elements.UmlElement;
import myuml.function.Find;
import myuml.function.Relation;
import myuml.function.UmlRule;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MyClass {
    private UmlClass umlClass;
    private HashMap<String, UmlAttribute> attributeNameMap;
    private HashMap<String, MyOperation> operationIdMap;
    private HashMap<OperationQueryType, Integer> operaCountMap;
    private HashMap<String, HashMap<Visibility, Integer>> operaVisiMap;
    private LinkedList<MyClass> fatherList;
    private LinkedList<AttributeClassInformation> attributeInformationList;
    private HashSet<String> interfaceIdSet;
    private int associationCount;
    private HashSet<String> associationIdSet;
    private boolean cycleFlag;

    public MyClass(UmlElement umlClass) {
        this.umlClass = (UmlClass) umlClass;
        attributeNameMap = new HashMap<>();
        operationIdMap = new HashMap<>();
        operaCountMap = new HashMap<>();
        operaVisiMap = new HashMap<>();
        fatherList = new LinkedList<>();
        attributeInformationList = new LinkedList<>();
        interfaceIdSet = new HashSet<>();
        associationCount = 0;
        associationIdSet = new HashSet<>();
        cycleFlag = false;
    }

    public void addAttribute(UmlElement attribute) {
        if (containsAttribute(attribute.getName())) {
            UmlRule.addUml002(attribute.getName(), getName());
        } else {
            attributeNameMap.put(attribute.getName(), (UmlAttribute) attribute);
        }
    }

    public void addOperation(MyOperation operation) {
        operationIdMap.put(operation.getId(), operation);
    }

    public String getName() {
        return umlClass.getName();
    }

    public String getId() {
        return umlClass.getId();
    }

    public int getAttributeSize() {
        return attributeNameMap.size();
    }

    public int getOperationCount(OperationQueryType queryType) {
        if (!operaCountMap.isEmpty()) {
            return operaCountMap.get(queryType);
        } else {
            int nonReturn = 0;
            int isReturn = 0;
            int nonParam = 0;
            int isParam = 0;
            for (String id : operationIdMap.keySet()) {
                MyOperation o = operationIdMap.get(id);
                boolean[] flag = o.getDirection();
                if (!flag[0]) {
                    nonParam++;
                } else {
                    isParam++;
                }
                if (!flag[1]) {
                    nonReturn++;
                } else {
                    isReturn++;
                }

            }
            operaCountMap.put(OperationQueryType.NON_RETURN, nonReturn);
            operaCountMap.put(OperationQueryType.RETURN, isReturn);
            operaCountMap.put(OperationQueryType.NON_PARAM, nonParam);
            operaCountMap.put(OperationQueryType.PARAM, isParam);
            operaCountMap.put(OperationQueryType.ALL, nonReturn + isReturn);
            return operaCountMap.get(queryType);
        }
    }

    public Map<Visibility, Integer> getOperationVisibility(
            String operationName) {
        if (operaVisiMap.containsKey(operationName)) {
            return operaVisiMap.get(operationName);
        } else {
            int publ = 0;
            int prot = 0;
            int priv = 0;
            int pack = 0;
            for (String id : operationIdMap.keySet()) {
                MyOperation o = operationIdMap.get(id);
                if (o.getName().equals(operationName)) {
                    switch (o.getVisibility()) {
                        case PUBLIC:
                            publ++;
                            break;
                        case PROTECTED:
                            prot++;
                            break;
                        case PRIVATE:
                            priv++;
                            break;
                        case PACKAGE:
                            pack++;
                            break;
                        default:
                            break;
                    }
                }
            }
            HashMap<Visibility, Integer> map = new HashMap<>();
            map.put(Visibility.PUBLIC, publ);
            map.put(Visibility.PROTECTED, prot);
            map.put(Visibility.PRIVATE, priv);
            map.put(Visibility.PACKAGE, pack);
            operaVisiMap.put(operationName, map);
            return map;
        }
    }

    public String getTopFatherName() {
        if (fatherList.isEmpty()) {
            if (Relation.hasGeneralization(getId())) {
                String id = Relation.getClassFather(getId()).getId();
                fatherList.addAll(Find.getClassById(id).getClassList());
            }
            fatherList.addFirst(this);
        }
        return fatherList.getLast().getName();
    }

    public LinkedList<MyClass> getClassList() {
        if (fatherList.isEmpty()) {
            getTopFatherName();
        }
        return fatherList;
    }

    public List<AttributeClassInformation> getInformationNotHidden() {
        if (attributeNameMap.isEmpty() || !attributeInformationList.isEmpty()) {
            return attributeInformationList;
        } else {
            for (String name : attributeNameMap.keySet()) {
                UmlAttribute a = attributeNameMap.get(name);
                if (!a.getVisibility().equals(Visibility.PRIVATE)) {
                    attributeInformationList.add(new AttributeClassInformation(
                            a.getName(), getName()));
                }
            }
            return attributeInformationList;
        }
    }

    public boolean containsAttribute(String name) {
        return attributeNameMap.containsKey(name);
    }

    public Visibility getAttributeVisibility(String name) {
        return attributeNameMap.get(name).getVisibility();
    }

    public HashSet<String> getInterfaceIdSet() {
        if (Relation.hasInterfaceRealition(getId())
                && interfaceIdSet.isEmpty()) {
            interfaceIdSet.addAll(Relation.getInterfaceIdSet(getId()));
            HashSet<String> tempSet = new HashSet<>();
            for (String id : interfaceIdSet) {
                tempSet.addAll(Find.getInterfaceById(id).getInterfaceIdSet());
            }
            interfaceIdSet.addAll(tempSet);
        }
        return interfaceIdSet;
    }

    public int getAssociationCount() {
        if (Relation.hasAssoication(getId()) && associationCount == 0) {
            associationCount = Relation.getAssociationEndSize(getId());
        }
        return associationCount;
    }

    public HashSet<String> getAssociatedClassIdSet() {
        if (Relation.hasAssoicationClass(getId())
                && associationIdSet.isEmpty()) {
            associationIdSet = Relation.getAssociationClassIdSet(getId());
        }
        return associationIdSet;
    }

    public void checkUml002() {
        if (Relation.hasAssoication(getId())) {
            HashSet<UmlElement> set = Relation.getAssociationEndById(getId());
            for (UmlElement e : set) {
                if (attributeNameMap.containsKey(e.getName())) {
                    UmlRule.addUml002(e.getName(), getName());
                }
            }
        }
    }
}
