import com.oocourse.uml2.interact.AppRunner;
import myuml.MyUmlGeneralInteraction;

public class Main {
    public static void main(String[] args) throws NoSuchMethodException {
        AppRunner appRunner = AppRunner.newInstance(
                MyUmlGeneralInteraction.class);
        appRunner.run(args);
    }
}
