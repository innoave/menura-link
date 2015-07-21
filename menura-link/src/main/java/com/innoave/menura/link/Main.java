/**
 * @(#) $Id: Main.java 11840 2015-04-03 13:57:51Z haraldmaida $
 *
 * Copyright (c) 2014-2015 Innoave.com
 * 
 *
 * Changes:
 *
 * $Log: Main.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innoave.menura.link.api.LinkException;
import com.innoave.menura.link.api.ParseException;
import com.innoave.menura.link.api.TestStep;
import com.innoave.menura.link.service.LinkService;

/**
 *
 *
 * @version $Revision: 11840 $
 * @author haraldmaida
 * @author $Author: haraldmaida $
 *
 */
public class Main {
	
	public static final String DEFAULT_TEST_LIST = "default";

	public static void main(final String[] args) {
		final Main main = new Main();
		main.go(args);
		System.exit(0);
	}
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private CliParams cliParams = new CliParams();
	
	public void logProperties(final Properties properties) {
		for (final String name : properties.stringPropertyNames()) {
			log.debug(" {}: {}", name, properties.get(name));
		}
	}
	
	private void go(final String[] args) {
		log.info("----------------------------------------");
		log.info("=== Menura Link Version {} ===", AppManifest.getVersion(Main.class));
		log.info("----------------------------------------");
		log.debug("--- Environment Properties ---");
		final Properties envProps = new Properties(); 
		envProps.putAll(System.getenv());
		logProperties(envProps);
		log.debug("----------------------------------------");
		log.debug("--- System Properties ---");
		final Properties sysProps = System.getProperties();
		logProperties(sysProps);
		log.debug("----------------------------------------");
		final LinkService link = new LinkService();
		log.debug("Pruefe ob link.properties existiert");
		final File linkPropsFile = new File("link.properties");
		if (linkPropsFile.exists()) {
			try {
				log.debug("Lese Einstellungen von Datei: {}", linkPropsFile.getPath());
				link.loadConfiguration(new File("link.properties"));
			} catch (IOException e) {
				log.warn("Fehler beim Lesen der {} Datei. Verwende Vorgabeeinstellungen!", linkPropsFile.getPath());
				log.debug(e.getMessage(), e);
			}
		}
		try {
			//Parse command line options
			final CmdLineParser cliParser = new CmdLineParser(cliParams);
			cliParser.parseArgument(args);
			if (cliParams.getSteps() == null || cliParams.getSteps().length == 0) {
				throw new CmdLineException(cliParser, "Ein oder mehrere Steps Dateien erforderlich");
			}
			//Setup test steps
			for (final String stepFilename : cliParams.getSteps()) {
				final TestStep testStep;
				final File stepFile = new File(stepFilename);
				testStep = link.readTestStep(stepFile);
				link.registerTestStep(DEFAULT_TEST_LIST, testStep);
			}
			link.executeRegisteredTestSteps(DEFAULT_TEST_LIST);
			
			waitUntilInterrupted();

			link.shutdown();
		} catch (LinkException e) {
			log.error("Fehler beim Ausfuehren des Test Steps", e);
		} catch (ParseException e) {
			log.error("Fehler beim Interpretieren der Test Step Datei", e);
		} catch (IOException e) {
			log.error("Fehler beim Lesen der Test Step Datei", e);
		} catch (CmdLineException e) {
			log.error(e.getLocalizedMessage());
			final StringWriter writer = new StringWriter();
			writer.append("Options: ");
			e.getParser().printUsage(writer, null);
			try { writer.close(); } catch (IOException e1) {}
			log.error(writer.toString());
			System.out.print(e.getLocalizedMessage());
			System.out.print("Options: ");
			e.getParser().printUsage(System.out);
		}
		log.info("...Auf Wiedersehen...!");
	}
	
	protected void waitUntilInterrupted() {
		try {
			while (true) Thread.sleep(600);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
