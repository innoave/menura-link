/**
 *  Copyright (c) 2014 Innoave.com
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.innoave.menura.link.api;

/**
 *
 *
 * @author haraldmaida
 *
 */
public interface NamingStrategie {

	<T extends LinkComponent> ComponentNameBuilder<T> forComponent(Class<T> component); 
	
	interface ComponentNameBuilder<T> {
		
		StepComponentNameBuilder<T> withName(String name);
		
		StepComponentNameBuilder<T> withNumber(int number);
		
		String withStepName(String stepName);

	}
	
	interface StepComponentNameBuilder<T> {
		
		String withStepName(String stepName);
		
	}
	
}
