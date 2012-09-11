package br.com.nozella.musicxml.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagTextField;

import br.com.nozella.musicxml.exception.SystemException;
import br.com.nozella.musicxml.service.FileProcessorService;
import br.com.nozella.musicxml.to.Album;
import br.com.nozella.musicxml.to.Artist;
import br.com.nozella.musicxml.to.Music;

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

	public Set<Artist> generateArtistList(List<File> fileList) throws SystemException {
		Map<String, Artist> artists = new HashMap<String, Artist>();
		for (File file : fileList) {
			try {
				Tag tag = AudioFileIO.read(file).getTag();
				
				String artistName = ((TagTextField) tag.getFirstField(FieldKey.ALBUM_ARTIST)).getContent();
				Artist artist = artists.get(artistName);
				if (artist == null) {
					artist = new Artist(artistName);
					artists.put(artist.getName(), artist);
				}
				
				String albumName = ((TagTextField) tag.getFirstField(FieldKey.ALBUM)).getContent();
				Album album = artist.getAlbum(albumName);
				if(album == null) {
					album = new Album(albumName);
					artist.putAlbum(album);
				}
				
				String musicTiltle = ((TagTextField) tag.getFirstField(FieldKey.TITLE)).getContent();
				Music music = album.getMusic(musicTiltle);
				if (music == null) {
					music = new Music(musicTiltle);
					album.putMusic(music);
				}
				
				
			} catch (Exception e) {
				String message = "error to read audio file";
				log.error(message, e);
				throw new SystemException(message, e);
			}
		}
		return null;
	}

}
