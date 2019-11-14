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
package core.simulation;

@SuppressWarnings("javadoc")
/**
 * TODO this will be removed and those parameters will be received through a json 
 */
public class Constants {

    public static int NO_WORKING_STEPS = 100000;
	public static int STEPS_WAITING_FOR_LAST_MESSAGES = NO_WORKING_STEPS / 2;
	public static int STEPS_WAITING_FOR_INIT =  NO_WORKING_STEPS / 2;
	
	public static int MIGRATION_FREQUENCY = MigrationFrequency.NORMAL.getValue();
	public static int MESSAGE_FREQUENCY = MessageFrequency.NORMAL.getValue();
	public static int MAXIMUM_CPU_POWER = CPUPower.NORMAL.getValue();
	public static boolean HEADERS = false;
}
	
