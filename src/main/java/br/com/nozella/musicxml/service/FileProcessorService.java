package br.com.nozella.musicxml.service;

import java.io.File;
import java.util.List;

import br.com.nozella.musicxml.to.Artist;

public interface FileProcessorService {
	
	List<File> getFileList(File fileDir);

	List<Artist> generateArtistList(List<File> fileList);

}
