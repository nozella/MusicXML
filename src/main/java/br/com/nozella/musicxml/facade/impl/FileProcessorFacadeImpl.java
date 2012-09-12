package br.com.nozella.musicxml.facade.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.nozella.musicxml.exception.SystemException;
import br.com.nozella.musicxml.facade.FileProcessorFacade;
import br.com.nozella.musicxml.service.FileProcessorService;
import br.com.nozella.musicxml.service.impl.FileProcessorServiceImpl;

public class FileProcessorFacadeImpl implements FileProcessorFacade {

	private static Log log = LogFactory.getLog(FileProcessorFacadeImpl.class);
	
	private FileProcessorService fileProcessorService = new FileProcessorServiceImpl();
	
	public FileProcessorFacadeImpl(){
		Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
	}
	
	public void processFile(String directoryPath, String path) throws SystemException {
		log.info(String.format("directory to read: %s, directory to write %s", directoryPath, path));
		fileProcessorService.processFile(directoryPath, path);
	}
}
