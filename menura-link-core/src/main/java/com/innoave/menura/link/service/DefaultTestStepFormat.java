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
package com.innoave.menura.link.service;

/**
 *
 *
 * @version $Revision: 9275 $
 * @author haraldmaida
 * @author $Author: maida $
 *
 */
public class DefaultTestStepFormat {
	
	public static enum Section {
		TEST_SETUP,
		MESSAGE_HEADER,
		MESSAGE_BODY;
	}
	
	public static class HeaderParam {
		public static final String APPLICATION = "applikation";
		public static final String FUNCTION = "funktion";
		public static final String ACTION = "aktion";
		public static final String CORRELATION_ID = "correlationId";
	}
	
	public static class TestSetupParam {
		public static final String TEST_STEP_NAME = "test.step.name";
		public static final String SYSTEM_ADAPTER = "system.adapter";
		public static final String MESSAGE_HANDLER = "message.handler";
	}

}
