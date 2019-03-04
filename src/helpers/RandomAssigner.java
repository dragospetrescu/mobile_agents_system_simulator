package helpers;

import host.Host;
import simulation.Constants;

import java.util.Random;

public class RandomAssigner {

    private static Random random = new Random();

    public static int assignMigrateTime(Host source, Host destination) {
        return Math.abs(destination.getId() - source.getId()) + getNetworkOverhead();
    }

    public static int getNetworkOverhead() {
        return getRandomNumberInRange(0, Constants.NETWORK_MAXIMUM_OVERHEAD);
    }

    public static int getRandomAgent() {
        return getRandomNumberInRange(0, Constants.NO_AGENTS);
    }
    private static int getRandomNumberInRange(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public static int assignWork() {
        return getRandomNumberInRange(1, Constants.MAXIMUM_WORK_TIME);
    }
}
