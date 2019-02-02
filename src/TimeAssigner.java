import java.util.Random;

public class TimeAssigner {

    private static Random random = new Random();

    public static int assignMigrateTime(Host source, Host destination) {
        return Math.abs(destination.getId() - source.getId()) + getNetworkOverhead();
    }

    public static int getNetworkOverhead() {
        return getRandomNumberInRange(0, Constants.NETWORK_MAXIMUM_OVERHEAD);
    }

    private static int getRandomNumberInRange(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public static int assignWork() {
        return getRandomNumberInRange(1, Constants.MAXIMUM_WORK_TIME);
    }
}
