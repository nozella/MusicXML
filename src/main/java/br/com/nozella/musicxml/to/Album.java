package br.com.nozella.musicxml.to;

import java.util.HashMap;
import java.util.Map;

public class Album {

	private String name;
	private Map<String, Music> musics;
	
	public Album(String name) {
		this.musics = new HashMap<String, Music>();
		this.setName(name);
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Map<String, Music> getMusics() {
		return this.musics;
	}
	
	public void setMusics(Map<String, Music> musics) {
		this.musics = musics;
	}
	
	public void putMusic(Music music) {
		this.getMusics().put(music.getTiltle(), music);
	}
	
	public Music getMusic(String tiltleMusic) {
		return this.getMusics().get(tiltleMusic);
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
		Album other = (Album) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Album [name=%s, musics=%s]", name, musics);
	}
	
}
