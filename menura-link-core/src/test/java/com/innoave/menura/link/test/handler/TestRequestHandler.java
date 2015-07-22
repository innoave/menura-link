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
package com.innoave.menura.link.test.handler;

import com.innoave.menura.link.api.AbstractLinkComponent;
import com.innoave.menura.link.api.Configuration;
import com.innoave.menura.link.api.FunctionalType;
import com.innoave.menura.link.api.LinkException;
import com.innoave.menura.link.api.LinkMessage;
import com.innoave.menura.link.api.LinkMessageHandler;
import com.innoave.menura.link.api.Test;
import com.innoave.menura.link.api.TestStep;

/**
 *
 *
 * @author haraldmaida
 *
 */
@Test(FunctionalType.ACTION)
public class TestRequestHandler extends AbstractLinkComponent
		implements LinkMessageHandler {


	@Override
	public void startup(Configuration configuration, TestStep testStep) throws LinkException {
		
	}

	@Override
	public void shutdown() throws LinkException {
		
	}

	@Override
	public void handleMessage(LinkMessage linkMessage) {
		
	}

}
