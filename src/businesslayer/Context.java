package businesslayer;

/**
 * Created by gunbo on 12/9/2015.
 */
public class Context {
    private final static Context instance = new Context();

    public static Context getInstance() {
        return instance;
    }

    private DatabaseProgress databaseProgress = new DatabaseProgress();

    public DatabaseProgress getDatabaseProgress() {
        return databaseProgress;
    }
}