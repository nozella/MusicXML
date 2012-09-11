package br.com.nozella.musicxml.facade;

import java.util.List;

import br.com.nozella.musicxml.exception.InvalidArgumentException;
import br.com.nozella.musicxml.exception.SystemException;

public interface FileProcessorFacade {
	void processFile(List<String> args) throws SystemException, InvalidArgumentException;
}
