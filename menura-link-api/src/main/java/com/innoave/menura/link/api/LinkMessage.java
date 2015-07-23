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
public class LinkMessage {

	protected String applikation;
	
	protected String funktion;
	
	protected String aktion;

	protected String correlationId;
	
	protected String inhalt;
	
	
	public LinkMessage() {
	}
	
	public LinkMessage(
			final String applikation,
			final String funktion,
			final String aktion,
			final String correlationId,
			final String inhalt
			) {
		this.applikation = applikation;
		this.funktion = funktion;
		this.aktion = aktion;
		this.correlationId = correlationId;
		this.inhalt = inhalt;
	}
	
	public String getApplikation() {
		return applikation;
	}

	public void setApplikation(String applikation) {
		this.applikation = applikation;
	}

	public String getFunktion() {
		return funktion;
	}

	public void setFunktion(String funktion) {
		this.funktion = funktion;
	}

	public String getAktion() {
		return aktion;
	}

	public void setAktion(String aktion) {
		this.aktion = aktion;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getInhalt() {
		return inhalt;
	}

	public void setInhalt(String inhalt) {
		this.inhalt = inhalt;
	}
	
}
