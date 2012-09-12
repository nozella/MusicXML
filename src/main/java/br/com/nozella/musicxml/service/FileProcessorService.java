package br.com.nozella.musicxml.service;

import br.com.nozella.musicxml.exception.SystemException;

public interface FileProcessorService {
	void processFile(String directoryPath, String path) throws SystemException;
}
