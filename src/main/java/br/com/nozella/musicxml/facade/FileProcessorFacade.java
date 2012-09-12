package br.com.nozella.musicxml.facade;

import br.com.nozella.musicxml.exception.SystemException;

public interface FileProcessorFacade {
	void processFile(String directoryPath, String path) throws SystemException;
}
