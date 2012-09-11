package br.com.nozella.musicxml.main;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.nozella.musicxml.facade.FileProcessorFacade;
import br.com.nozella.musicxml.facade.impl.FileProcessorFacadeImpl;

public class App {
	private static Log log = LogFactory.getLog( App.class );
	
	private static FileProcessorFacade fileProcessor = new FileProcessorFacadeImpl();
	
	public static void main(String[] args) {
		log.debug("initializing...");
		Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
		
		try {
			fileProcessor.processFile(Arrays.asList(args));
		} catch (Exception e) {
			log.error("error when processing file", e);			
		}
	}

}
