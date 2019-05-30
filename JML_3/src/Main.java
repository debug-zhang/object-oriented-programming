import com.oocourse.specs3.AppRunner;
import com.oocourse.specs3.exceptions.AppRunnerInstantiationException;
import railwaysystem.MyPath;
import railwaysystem.MyRailwaySystem;

public class Main {
    public static void main(String[] args) {
        try {
            AppRunner appRunner = AppRunner.newInstance(MyPath.class,
                    MyRailwaySystem.class);

            appRunner.run(args);
        } catch (AppRunnerInstantiationException e) {
            e.printStackTrace();
        }

    }
}
