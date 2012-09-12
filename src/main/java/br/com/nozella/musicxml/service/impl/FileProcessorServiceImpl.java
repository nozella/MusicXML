package br.com.nozella.musicxml.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
import br.com.nozella.musicxml.to.Music;

public class FileProcessorServiceImpl implements FileProcessorService {

	private static Log log = LogFactory.getLog(FileProcessorServiceImpl.class);
	private static Log sameLineLogger = LogFactory.getLog("sameLineLogger");

	public FileProcessorServiceImpl(){
		Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
	}
	
	public void processFile(String directoryPath, String path) throws SystemException {
		this.generateXml(this.generateArtistList(this.getFileList(new File(directoryPath))), path);
	}

	private void generateXml(Collection<Artist> artits, String path) throws SystemException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("library");
			doc.appendChild(rootElement);

			for (Artist artist : artits) {
				Element artistElement = doc.createElement("artist");

				Element nameArtist = doc.createElement("name");
				nameArtist.appendChild(doc.createTextNode(artist.getName()));
				artistElement.appendChild(nameArtist);

				rootElement.appendChild(artistElement);
				for (Album album : artist.getAlbuns()) {
					Element albumElement = doc.createElement("album");

					Element nameAlbum = doc.createElement("name");
					nameAlbum.appendChild(doc.createTextNode(album.getName()));
					albumElement.appendChild(nameAlbum);

					artistElement.appendChild(albumElement);
					for (Music music : album.getMusics()) {
						Element musicElement = doc.createElement("music");

						Element tiltleMusic = doc.createElement("tiltle");
						tiltleMusic.appendChild(doc.createTextNode(music.getTiltle()));
						musicElement.appendChild(tiltleMusic);

						albumElement.appendChild(musicElement);
					}
				}
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(path, "musicXml.xml"));
			transformer.transform(source, result);
			log.info(String.format("generete file on path %s", path));
		} catch (Exception e) {
			String message = "error to generate xml file";
			log.error(message, e);
			throw new SystemException(message, e);
		}
	}

	private Collection<File> getFileList(File directory) {
		List<File> fileList = new ArrayList<File>();

		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				fileList.addAll(this.getFileList(file));
			} else if (file.getName().endsWith("mp3")) {
				fileList.add(file);
			}
		}

		return fileList;
	}

	private Collection<Artist> generateArtistList(Collection<File> fileList) throws SystemException {
		log.info(String.format("%s files were found, this process might take a several time", fileList.size()));
		Map<String, Artist> artists = new HashMap<String, Artist>();
		for (File file : fileList) {
			sameLineLogger.info(".");
			try {
				Tag tag = AudioFileIO.read(file).getTag();

				String artistName = ((TagTextField) tag.getFirstField(FieldKey.ARTIST)).getContent();
				Artist artist = artists.get(artistName);
				if (artist == null) {
					artist = new Artist(artistName);
					artists.put(artist.getName(), artist);
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
				sameLineLogger.info("\n");
				String message = String.format("error to read audio file: %s", file);
				log.warn("maybe the ID3 of your file can be corrupted");
				log.warn(message, e);
			}
		}
		sameLineLogger.info("\n");
		return artists.values();
	}
}