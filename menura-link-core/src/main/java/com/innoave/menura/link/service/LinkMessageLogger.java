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

import com.innoave.menura.link.api.LinkMessage;
import com.innoave.menura.link.api.ObjectLogger;

/**
 *
 *
 * @author haraldmaida
 *
 */
public class LinkMessageLogger implements ObjectLogger<LinkMessage> {

	@Override
	public String buildLogMessage(final LinkMessage value) {
		final StringBuilder sb = new StringBuilder();
		sb.append("aktion=");
		sb.append(value.getAktion());
		sb.append(", correlationId=");
		sb.append(value.getCorrelationId());
		sb.append(", funktion=");
		sb.append(value.getFunktion());
		sb.append(", applikation=");
		sb.append(value.getApplikation());
		sb.append("\n   inhalt: ");
		sb.append(value.getInhalt());
		return sb.toString();
	}
	
}
