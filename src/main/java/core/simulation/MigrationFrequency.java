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
public enum MigrationFrequency {

	EXTREMELY_LOW(1000),
	LOW(500),
	NORMAL(100),
	HIGH(50),
	EXTREMELY_HIGH(10);
	
	private int value;

	private MigrationFrequency(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
