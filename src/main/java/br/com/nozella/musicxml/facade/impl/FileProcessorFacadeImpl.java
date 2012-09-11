package br.com.nozella.musicxml.facade.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.nozella.musicxml.exception.InvalidArgumentException;
import br.com.nozella.musicxml.exception.SystemException;
import br.com.nozella.musicxml.facade.FileProcessorFacade;
import br.com.nozella.musicxml.service.FileProcessorService;
import br.com.nozella.musicxml.service.impl.FileProcessorServiceImpl;
import br.com.nozella.musicxml.to.Artist;

public class FileProcessorFacadeImpl implements FileProcessorFacade {

	private static Log log = LogFactory.getLog(FileProcessorFacadeImpl.class);
	
	private FileProcessorService fileProcessorService = new FileProcessorServiceImpl();
	
	public void processFile(List<String> args) throws SystemException, InvalidArgumentException {
		log.debug(args);
		
		this.validateArguments(args);
		List<Artist> artists = fileProcessorService.generateArtistList(fileProcessorService.getFileList(new File(args.get(0))));
		
		throw new SystemException("not supported yet");
	}

	private void validateArguments(List<String> args) throws InvalidArgumentException {
		if (args.size() != 2) {
			throw new InvalidArgumentException("waiting for exactly two arguments");
		}
		for(String arg : args) {
			if (!new File(arg).isDirectory()) {
				throw new InvalidArgumentException("at least one of the arguments is an invalid directory");
			} 
		}
	}

}
