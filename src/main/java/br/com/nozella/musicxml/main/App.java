package br.com.nozella.musicxml.main;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class App {
	private static Log log = LogFactory.getLog( App.class );
	
	public static void main(String[] args) {
		log.debug("initialise");
		for(String arg : Arrays.asList(args)) {
			log.debug(arg);
		}
	}

}
