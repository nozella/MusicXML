package br.com.nozella.musicxml.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagTextField;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import br.com.nozella.musicxml.exception.SystemException;
import br.com.nozella.musicxml.service.FileProcessorService;
import br.com.nozella.musicxml.to.Album;
import br.com.nozella.musicxml.to.Artist;
import br.com.nozella.musicxml.to.Library;
import br.com.nozella.musicxml.to.Music;

public class FileProcessorServiceImpl implements FileProcessorService {
	
	private static enum SUPPORT_FILES{MP3}

	private static Log log = LogFactory.getLog(FileProcessorServiceImpl.class);
	private static Log sameRowLogger = LogFactory.getLog("sameRowLogger");

	public FileProcessorServiceImpl(){
		Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
	}
	
	public void processFile(String directoryPath, String path) throws SystemException {
		this.generateXml(this.generateLibrary(this.getFiles(new File(directoryPath))), path);
	}

	private void generateXml(Library library, String path) throws SystemException {
		log.info("preparing file...");
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(this.generateDocument(library));
			StreamResult result = new StreamResult(new File(path, "musicXml.xml"));
			transformer.transform(source, result);
			log.info(String.format("generate file on path %s", path));
		} catch (Exception e) {
			String message = "error to generate xml file";
			log.error(message, e);
			throw new SystemException(message, e);
		}
	}

	private Document generateDocument(Library library) throws ParserConfigurationException {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		doc.appendChild(this.populateRootElement(library, doc));
		return doc;
	}

	private Element populateRootElement(Library library, Document doc) {
		Element rootElement = doc.createElement("library");
		for (Artist artist : library.getArtists()) {
			rootElement.appendChild(this.populateArtisElement(doc, artist));
		}
		return rootElement;
	}

	private Element populateArtisElement(Document doc, Artist artist) {
		Element artistElement = doc.createElement("artist");
		Element nameArtist = doc.createElement("name");
		nameArtist.appendChild(doc.createTextNode(artist.getName()));
		artistElement.appendChild(nameArtist);
		for (Album album : artist.getAlbuns()) {
			artistElement.appendChild(this.populateAlbumElement(doc, album));
		}
		return artistElement;
	}

	private Element populateAlbumElement(Document doc, Album album) {
		Element albumElement = doc.createElement("album");
		Element nameAlbum = doc.createElement("name");
		nameAlbum.appendChild(doc.createTextNode(album.getName()));
		albumElement.appendChild(nameAlbum);
		for (Music music : album.getMusics()) {
			albumElement.appendChild(this.populateMusicElement(doc, music));
		}
		return albumElement;
	}

	private Element populateMusicElement(Document doc, Music music) {
		Element musicElement = doc.createElement("music");
		Element tiltleMusic = doc.createElement("tiltle");
		tiltleMusic.appendChild(doc.createTextNode(music.getTiltle()));
		musicElement.appendChild(tiltleMusic);
		return musicElement;
	}

	private Collection<File> getFiles(File directory) {
		List<File> files = new ArrayList<File>();
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				files.addAll(this.getFiles(file));
			} else if (file.getName().toUpperCase().endsWith(SUPPORT_FILES.MP3.toString())) {
				files.add(file);
			}
		}
		return files;
	}

	private Library generateLibrary(Collection<File> fileList) {
		log.info(String.format("%s files were found, this process might take a several time", fileList.size()));
		Library library = new Library();
		for (File file : fileList) {
			sameRowLogger.info(".");
			try {
				Tag tag = AudioFileIO.read(file).getTag();

				String artistName = ((TagTextField) tag.getFirstField(FieldKey.ARTIST)).getContent();
				Artist artist = library.getArtist(artistName);
				if (artist == null) {
					artist = new Artist(artistName);
					library.putArtist(artist);
				}

				String albumName = ((TagTextField) tag.getFirstField(FieldKey.ALBUM)).getContent();
				Album album = artist.getAlbum(albumName);
				if (album == null) {
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
				sameRowLogger.info("\n");
				log.warn(new SystemException(String.format("maybe the ID3 of your file can be corrupted: error to read audio file: %s", file)));
			}
		}
		sameRowLogger.info("\n");
		return library;
	}
}