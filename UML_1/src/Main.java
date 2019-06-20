import com.oocourse.uml1.interact.AppRunner;
import myuml.MyUmlInteraction;

public class Main {
    public static void main(String[] args) throws NoSuchMethodException {
        AppRunner appRunner = AppRunner.newInstance(MyUmlInteraction.class);
        appRunner.run(args);
    }
}
