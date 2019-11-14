/*******************************************************************************
 * Copyright 2019 dragos
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
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
     * @return true if the random states that a message should be delivered
     */
    public static boolean wantsToSendMessage() {
    	return random.nextInt(Constants.MESSAGE_FREQUENCY) == 0;
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
