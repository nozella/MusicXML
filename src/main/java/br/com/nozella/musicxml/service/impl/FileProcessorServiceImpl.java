package br.com.nozella.musicxml.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagTextField;

import br.com.nozella.musicxml.service.FileProcessorService;
import br.com.nozella.musicxml.to.Artist;

public class FileProcessorServiceImpl implements FileProcessorService {

	private static Log log = LogFactory.getLog(FileProcessorServiceImpl.class);
	
	public List<File> getFileList(File fileDir) {
		log.debug(fileDir);
		
		List<File> fileList = new ArrayList<File>();
		
		for (File file : fileDir.listFiles()) {
			if (file.isDirectory()) {
				fileList.addAll(this.getFileList(file));
			} else if (file.getName().endsWith("mp3")) {
					fileList.add(file);
			}
		}
		
		return fileList;
	}

	public List<Artist> generateArtistList(List<File> fileList) {
		for (File file : fileList) {
			AudioFile f;
			try {
				f = AudioFileIO.read(file);
				Tag tag = f.getTag();
				TagTextField artist = (TagTextField) tag.getFirstField(FieldKey.ALBUM_ARTIST);
				TagTextField album = (TagTextField) tag.getFirstField(FieldKey.ALBUM);
				TagTextField tiltle = (TagTextField) tag.getFirstField(FieldKey.TITLE);
				log.info( "artist " + artist.getContent() );
				log.info( "album " + album.getContent() );
				log.info( "tiltle " + tiltle.getContent() );

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		// TODO Auto-generated method stub
		return null;
	}

}
