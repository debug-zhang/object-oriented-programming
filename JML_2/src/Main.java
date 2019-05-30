import com.oocourse.specs2.AppRunner;
import com.oocourse.specs2.exceptions.AppRunnerInstantiationException;
import graph.MyGraph;
import graph.MyPath;

public class Main {
    public static void main(String[] args) {
        try {
            AppRunner appRunner = AppRunner.newInstance(MyPath.class,
                    MyGraph.class);

            appRunner.run(args);
        } catch (AppRunnerInstantiationException e) {
            e.printStackTrace();
        }

    }
}
