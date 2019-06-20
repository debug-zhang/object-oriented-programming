package myuml.structure.classmodel;

import com.oocourse.uml2.interact.common.AttributeClassInformation;
import com.oocourse.uml2.interact.common.AttributeQueryType;
import com.oocourse.uml2.interact.common.OperationQueryType;
import com.oocourse.uml2.interact.exceptions.user.AttributeDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.AttributeNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml2.interact.format.UmlClassModelInteraction;
import com.oocourse.uml2.models.common.Visibility;
import myuml.function.Cache;
import myuml.function.Find;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MyUmlClassModelInteraction implements UmlClassModelInteraction {
    private Cache cache;

    public MyUmlClassModelInteraction(Cache cache) {
        this.cache = cache;
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
}
