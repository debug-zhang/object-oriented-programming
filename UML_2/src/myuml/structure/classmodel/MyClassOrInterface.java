package myuml.structure.classmodel;

import com.oocourse.uml2.models.common.ElementType;
import com.oocourse.uml2.models.common.Visibility;
import com.oocourse.uml2.models.elements.UmlClass;
import com.oocourse.uml2.models.elements.UmlClassOrInterface;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlInterface;

import java.util.Map;

public class MyClassOrInterface implements UmlClassOrInterface {
    private UmlElement element;

    public MyClassOrInterface(UmlElement umlElement) {
        this.element = umlElement;
    }

    @Override
    public Visibility getVisibility() {
        if (element instanceof UmlClass) {
            return ((UmlClass) element).getVisibility();
        } else {
            return ((UmlInterface) element).getVisibility();
        }
    }

    @Override
    public ElementType getElementType() {
        return element.getElementType();
    }

    @Override
    public String getId() {
        return element.getId();
    }

    @Override
    public String getName() {
        return element.getName();
    }

    @Override
    public String getParentId() {
        return element.getParentId();
    }

    @Override
    public Map<String, Object> toJson() {
        return element.toJson();
    }
}
