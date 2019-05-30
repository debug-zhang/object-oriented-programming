import com.oocourse.specs1.AppRunner;
import com.oocourse.specs1.exceptions.AppRunnerInstantiationException;
import path.MyPath;
import path.MyPathContainer;

public class Main {
    public static void main(String[] args) {
        try {
            AppRunner appRunner = AppRunner.newInstance(MyPath.class,
                    MyPathContainer.class);

            appRunner.run(args);
        } catch (AppRunnerInstantiationException e) {
            e.printStackTrace();
        }

    }
}
