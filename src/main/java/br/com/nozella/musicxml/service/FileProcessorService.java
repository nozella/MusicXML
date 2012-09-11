package br.com.nozella.musicxml.service;

import java.io.File;
import java.util.List;
import java.util.Set;

import br.com.nozella.musicxml.exception.SystemException;
import br.com.nozella.musicxml.to.Artist;

public interface FileProcessorService {
	
	List<File> getFileList(File fileDir);

	Set<Artist> generateArtistList(List<File> fileList) throws SystemException;

}
