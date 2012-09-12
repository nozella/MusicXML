package br.com.nozella.musicxml.main;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.nozella.musicxml.facade.FileProcessorFacade;
import br.com.nozella.musicxml.facade.impl.FileProcessorFacadeImpl;

public class App extends Thread {
	private static Log log = LogFactory.getLog(App.class);
	private static FileProcessorFacade fileProcessor = new FileProcessorFacadeImpl();
	
	public static void main(String[] args) {
		if (args.length != 2) {
			log.error("waiting for exactly two arguments");
			return;
		}
		for (String arg : args) {
			if (!new File(arg).isDirectory()) {
				log.error("at least one of the arguments is an invalid directory");
				return;
			}
		}
		
		log.info("initializing...");
		try {
			fileProcessor.processFile(args[0], args[1]);
		} catch (Exception e) {
			log.error("error when processing file", e);
		}
	}
}
