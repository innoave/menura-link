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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 *
 *
 * @version $Revision: 1.0 $
 * @author haraldmaida
 * @author $Author: haraldmaida $
 *
 */
public interface TestStepReader {
	
	TestStep readTestStep(File file) throws IOException, ParseException;
	
	TestStep readTestStep(InputStream in) throws IOException, ParseException;
	
	TestStep readTestStep(String text) throws IOException, ParseException;

	TestStep readTestStep(Reader reader) throws IOException, ParseException;
	
}
