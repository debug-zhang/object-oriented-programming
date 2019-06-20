package myuml.structure.classmodel;

import com.oocourse.uml2.models.common.Visibility;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlOperation;
import com.oocourse.uml2.models.elements.UmlParameter;

import java.util.HashMap;

public class MyOperation {
    private UmlOperation operation;
    private HashMap<String, UmlParameter> parameterMap;     // key = id

    public MyOperation(UmlElement operation) {
        this.operation = (UmlOperation) operation;
        this.parameterMap = new HashMap<>();
    }

    public void addParameter(UmlElement parameter) {
        parameterMap.put(parameter.getId(), (UmlParameter) parameter);
    }

    public String getName() {
        return operation.getName();
    }

    public String getId() {
        return operation.getId();
    }

    public String getClassId() {
        return operation.getParentId();
    }

    public Visibility getVisibility() {
        return operation.getVisibility();
    }

    public boolean[] getDirection() {
        boolean[] flag = new boolean[2];
        for (String id : parameterMap.keySet()) {
            switch (parameterMap.get(id).getDirection()) {
                case IN:
                case OUT:
                case INOUT:
                    flag[0] = true;
                    break;
                case RETURN:
                    flag[1] = true;
                    break;
                default:
                    break;
            }
        }
        return flag;
    }
}
