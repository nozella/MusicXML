package br.com.nozella.musicxml.to;

import java.util.HashMap;
import java.util.Map;

public class Artist {
	
	private String name;
	private Map<String, Album> albuns;
	
	public Artist(String name){
		this.albuns = new HashMap<String, Album>();
		this.setName(name);
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Map<String, Album> getAlbuns() {
		return albuns;
	}
	
	public void setAlbuns(Map<String, Album> albuns) {
		this.albuns = albuns;
	}
	
	public void putAlbum(Album album) {
		this.getAlbuns().put(album.getName(), album);
	}
	
	public Album getAlbum(String albumName) {
		return this.getAlbuns().get(albumName);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artist other = (Artist) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Artist [name=%s, albuns=%s]", name, albuns);
	}
	
}
