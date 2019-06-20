package myuml.structure;

import com.oocourse.uml1.models.elements.UmlAttribute;
import com.oocourse.uml1.models.elements.UmlElement;
import com.oocourse.uml1.models.elements.UmlInterface;
import myuml.function.Relation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class MyInterface {
    private UmlInterface umlInterface;
    private HashMap<String, UmlAttribute> attributeMap;     // key = name
    private HashMap<String, MyOperation> operationMap;      // key = id
    private HashSet<String> fatherSet;

    public MyInterface(UmlElement umlInterface) {
        this.umlInterface = (UmlInterface) umlInterface;
        attributeMap = new HashMap<>();
        operationMap = new HashMap<>();
        fatherSet = new HashSet<>();
    }

    public void addAttributeMap(UmlElement attribute) {
        attributeMap.put(attribute.getName(), (UmlAttribute) attribute);
    }

    public void addOperation(MyOperation operation) {
        operationMap.put(operation.getId(), operation);
    }

    public String getName() {
        return umlInterface.getName();
    }

    public String getId() {
        return umlInterface.getId();
    }

    public HashSet<String> getInterfaceIdSet() {
        if (fatherSet.isEmpty()) {
            Queue<String> queue = new LinkedList<>();
            queue.offer(getId());
            while (!queue.isEmpty()) {
                String id = queue.poll();
                if (Relation.hasGeneralization(id)) {
                    for (String tempId : Relation.getInterfaceFather(id)) {
                        queue.offer(tempId);
                        fatherSet.add(tempId);
                    }
                }
            }
        }
        return fatherSet;
    }
}
