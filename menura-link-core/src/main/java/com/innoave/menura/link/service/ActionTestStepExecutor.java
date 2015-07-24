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

import java.util.Map;

import com.innoave.menura.link.api.LinkMessage;
import com.innoave.menura.link.api.LinkMessageHandler;
import com.innoave.menura.link.api.SystemAdapter;
import com.innoave.menura.link.expressions.LinkExpressionRenderer;

/**
 *
 *
 * @author haraldmaida
 *
 */
public class ActionTestStepExecutor extends AbstractTestStepExecutor {
	
	
	public ActionTestStepExecutor(
			final String name,
			final LinkMessage message,
			final LinkMessageHandler messageHandler,
			final SystemAdapter systemAdapter
			) {
		super(name, message, messageHandler, systemAdapter);
	}
	
	@Override
	public void execute(final SessionContext context) {
		final LinkMessage message = getMessage();
		final LinkExpressionRenderer expressionRenderer = new LinkExpressionRenderer();
		final Map<String, Object> attributes = context.getAllCurrentAttributes();
		final String preparedContent = expressionRenderer.render(
				message.getInhalt(), attributes);
		final LinkMessage preparedMessage = new LinkMessage(
				message.getApplikation(), message.getFunktion(), message.getAktion(),
				message.getCorrelationId(), preparedContent);
		getMessageHandler().handleMessage(preparedMessage);
	}
	
}
