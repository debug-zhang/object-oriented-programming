package myuml.structure.classmodel;

import com.oocourse.uml2.interact.exceptions.user.UmlRule002Exception;
import com.oocourse.uml2.interact.exceptions.user.UmlRule008Exception;
import com.oocourse.uml2.interact.exceptions.user.UmlRule009Exception;
import com.oocourse.uml2.interact.format.UmlStandardPreCheck;
import myuml.function.Find;
import myuml.function.UmlRule;

public class MyUmlStandardPreCheck implements UmlStandardPreCheck {

    @Override
    public void checkForUml002() throws UmlRule002Exception {
        if (UmlRule.isUml002()) {
            throw new UmlRule002Exception(UmlRule.getUml002Set());
        }
    }

    @Override
    public void checkForUml008() throws UmlRule008Exception {
        if (UmlRule.isUml008()) {
            throw new UmlRule008Exception(UmlRule.getUml008Set());
        }
    }

    @Override
    public void checkForUml009() throws UmlRule009Exception {
        for (String id : Find.getInterfaceIdMap().keySet()) {
            Find.getInterfaceById(id).getInterfaceIdSet();
        }
        for (String id : Find.getClassIdMap().keySet()) {
            UmlRule.checkDup(id);
        }
        if (UmlRule.isUml009()) {
            throw new UmlRule009Exception(UmlRule.getUml009Set());
        }
    }

    public void checkPrepare() {
        for (String id : Find.getInterfaceIdMap().keySet()) {
            UmlRule.clearVisitedSet();
            UmlRule.checkCycle(true, id, id);
        }

        for (String id : Find.getClassIdMap().keySet()) {
            UmlRule.clearVisitedSet();
            UmlRule.checkCycle(true, id, id);
            Find.getClassById(id).checkUml002();
        }
    }
}
