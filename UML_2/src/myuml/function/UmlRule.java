package myuml.function;

import com.oocourse.uml2.interact.common.AttributeClassInformation;
import myuml.structure.classmodel.MyClass;
import myuml.structure.classmodel.MyClassOrInterface;

import java.util.HashSet;

public class UmlRule {
    private static HashSet<AttributeClassInformation> uml002Set
            = new HashSet<>();

    private static HashSet<MyClassOrInterface> uml008Set = new HashSet<>();
    private static HashSet<String> uml008IdSet = new HashSet<>();

    private static HashSet<MyClassOrInterface> uml009Set = new HashSet<>();
    private static HashSet<String> uml009IdSet = new HashSet<>();

    private static HashSet<String> visitedSet = new HashSet<>();

    public static void addUml002(String attributeName, String className) {
        uml002Set.add(new AttributeClassInformation(attributeName, className));
    }

    public static boolean isUml002() {
        return !uml002Set.isEmpty();
    }

    public static HashSet<AttributeClassInformation> getUml002Set() {
        return uml002Set;
    }

    public static void addUml008(String id) {
        if (!uml008IdSet.contains(id)) {
            uml008Set.add(new MyClassOrInterface(Find.getById(id)));
            uml008IdSet.add(id);
        }
    }

    public static boolean isUml008() {
        return !uml008Set.isEmpty();
    }

    public static HashSet<MyClassOrInterface> getUml008Set() {
        return uml008Set;
    }

    public static void addUml009(String id) {
        if (!uml009IdSet.contains(id)) {
            uml009Set.add(new MyClassOrInterface(Find.getById(id)));
            uml009IdSet.add(id);
        }
    }

    public static boolean isUml009() {
        return !uml009Set.isEmpty();
    }

    public static boolean containsUml009Id(String id) {
        return uml009IdSet.contains(id);
    }

    public static HashSet<MyClassOrInterface> getUml009Set() {
        return uml009Set;
    }

    public static void clearVisitedSet() {
        visitedSet.clear();
    }

    public static void checkCycle(boolean isFirst, String fatherId, String id) {
        if (!isFirst) {
            if (fatherId.equals(id)) {
                addUml008(fatherId);
                return;
            } else {
                visitedSet.add(id);
            }
        }

        if (Find.containsClassId(id) && Relation.hasGeneralization(id)) {
            String nextId = Relation.getClassFather(id).getId();
            if (!visitedSet.contains(nextId)) {
                checkCycle(false, fatherId, nextId);
            }
        }
        if (Find.containsInterfaceId(id) && Relation.hasGeneralization(id)) {
            for (String nextId : Relation.getInterfaceFather(id)) {
                if (!visitedSet.contains(nextId)) {
                    checkCycle(false, fatherId, nextId);
                }
            }
        }
    }

    public static void checkDup(String classId) {
        boolean isDup = false;
        HashSet<String> interfaceIdSet = new HashSet<>();
        for (MyClass c : Find.getClassById(classId).getClassList()) {
            String id = c.getId();
            if (!isDup && Relation.hasInterfaceRealition(id)) {
                for (String tempId : Relation.getRealiIdList(id)) {
                    if (checkClass(tempId, interfaceIdSet)) {
                        isDup = true;
                        break;
                    }
                }
            }
        }

        if (isDup) {
            addUml009(classId);
        }
    }

    public static boolean checkClass(String id, HashSet<String> idSet) {
        if (idSet.contains(id) || containsUml009Id(id)) {
            return true;
        } else {
            idSet.add(id);
        }
        if (Relation.hasGeneralization(id)) {
            for (String tempId : Relation.getInterfaceFather(id)) {
                if (idSet.contains(tempId) || containsUml009Id(id)) {
                    return true;
                } else {
                    idSet.add(tempId);
                }
            }
        }
        return false;
    }
}
