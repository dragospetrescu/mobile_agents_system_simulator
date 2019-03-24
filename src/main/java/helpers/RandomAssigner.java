package helpers;

import host.communication.CommunicatingHostInterface;
import simulation.Constants;

import java.util.List;
import java.util.Random;

public class RandomAssigner {

    private static Random random = new Random();

    private static int getRandomNumberInRange(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public static int assignWork() {
        return getRandomNumberInRange(1, Constants.MAXIMUM_WORK_TIME);
    }
    
    public static <T> T getRandomElement(List<T> allElements, T exception) {
    	T randomElement = allElements.get(random.nextInt(allElements.size()));
	    if(randomElement.equals(exception)) {
	        return getRandomElement(allElements, exception);
	    }
	    return randomElement;
    }

//	public static <T> getRandomElement(List<T> allHosts, <T> exceptionHost) {
////		CommunicatingHostInterface randomElement = allHosts.get(random.nextInt(allHosts.size()));
////        if(randomElement.equals(exceptionHost)) {
////            return getRandomHost(allHosts, exceptionHost);
////        }
////        return randomElement;
//		return null;
//	}
}
