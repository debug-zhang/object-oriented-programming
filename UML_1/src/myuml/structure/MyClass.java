package myuml.structure;

import com.oocourse.uml1.interact.common.AttributeClassInformation;
import com.oocourse.uml1.interact.common.OperationQueryType;
import com.oocourse.uml1.models.common.Visibility;
import com.oocourse.uml1.models.elements.UmlAttribute;
import com.oocourse.uml1.models.elements.UmlClass;
import com.oocourse.uml1.models.elements.UmlElement;
import myuml.function.Find;
import myuml.function.Relation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MyClass {
    private UmlClass umlClass;
    private HashMap<String, UmlAttribute> attributeMap;     // key = name
    private HashMap<String, MyOperation> operationMap;      // key = id
    private HashMap<OperationQueryType, Integer> operaCountMap;
    private HashMap<String, HashMap<Visibility, Integer>> operaVisiMap;
    private LinkedList<MyClass> fatherList;
    private LinkedList<AttributeClassInformation> attributeInformationList;
    private HashSet<String> interfaceIdSet;
    private int assoicationCount;
    private HashSet<String> assoicationIdSet;

    public MyClass(UmlElement umlClass) {
        this.umlClass = (UmlClass) umlClass;
        attributeMap = new HashMap<>();
        operationMap = new HashMap<>();
        operaCountMap = new HashMap<>();
        operaVisiMap = new HashMap<>();
        fatherList = new LinkedList<>();
        attributeInformationList = new LinkedList<>();
        interfaceIdSet = new HashSet<>();
        assoicationCount = 0;
        assoicationIdSet = new HashSet<>();
    }

    public void addAttribute(UmlElement attribute) {
        attributeMap.put(attribute.getName(), (UmlAttribute) attribute);
    }

    public void addOperation(MyOperation operation) {
        operationMap.put(operation.getId(), operation);
    }

    public String getName() {
        return umlClass.getName();
    }

    public String getId() {
        return umlClass.getId();
    }

    public int getAttributeSize() {
        return attributeMap.size();
    }

    public int getOperationCount(OperationQueryType queryType) {
        if (!operaCountMap.isEmpty()) {
            return operaCountMap.get(queryType);
        } else {
            int nonReturn = 0;
            int isReturn = 0;
            int nonParam = 0;
            int isParam = 0;
            for (String id : operationMap.keySet()) {
                MyOperation o = operationMap.get(id);
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
            for (String id : operationMap.keySet()) {
                MyOperation o = operationMap.get(id);
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
        if (attributeMap.isEmpty() || !attributeInformationList.isEmpty()) {
            return attributeInformationList;
        } else {
            for (String name : attributeMap.keySet()) {
                UmlAttribute a = attributeMap.get(name);
                if (!a.getVisibility().equals(Visibility.PRIVATE)) {
                    attributeInformationList.add(new AttributeClassInformation(
                            a.getName(), getName()));
                }
            }
            return attributeInformationList;
        }
    }

    public boolean containsAttribute(String name) {
        return attributeMap.containsKey(name);
    }

    public Visibility getAttributeVisibility(String name) {
        return attributeMap.get(name).getVisibility();
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
        if (Relation.hasAssoication(getId()) && assoicationCount == 0) {
            assoicationCount = Relation.getAssociationEndSize(getId());
        }
        return assoicationCount;
    }

    public HashSet<String> getAssociatedClassIdSet() {
        if (Relation.hasAssoicationClass(getId())
                && assoicationIdSet.isEmpty()) {
            assoicationIdSet = Relation.getAssociationClassIdSet(getId());
        }
        return assoicationIdSet;
    }
}
