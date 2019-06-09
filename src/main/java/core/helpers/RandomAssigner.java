package core.helpers;

import java.util.List;
import java.util.Random;

import core.simulation.Constants;

/**
 * Assigns random values in order to make the simulation more truthful
 */
public class RandomAssigner {

    /**
     * Random that will be used.
     * TODO: make it receive a seed so that the results are consistent
     */
    private static Random random = new Random(123);

    
    /**
     * @param min
     * @param max
     * @return random number in between min and max
     */
    private static Integer getRandomNumberInRange(Integer min, Integer max) {
        return random.nextInt((max - min) + 1) + min;
    }

    /**
     * @return random time units until next migration
     */
    public static Integer assignWork() {
        Integer workTime = getRandomNumberInRange(Constants.MIGRATION_FREQUENCY / 10, Constants.MIGRATION_FREQUENCY);
        return workTime;
    }
    
    /**
     * @param <T>
     * @param allElements
     * @param exception
     * @return random element from the list with the exception of the T element
     */
    public static <T> T getRandomElement(List<T> allElements, T exception) {
    	T randomElement = allElements.get(random.nextInt(allElements.size()));
	    if(randomElement.equals(exception)) {
	        return getRandomElement(allElements, exception);
	    }
	    return randomElement;
    }
}