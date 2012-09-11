package br.com.nozella.musicxml.to;

import java.util.List;

public class Album {

	private String name;
	private List<Music> musics;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Music> getMusics() {
		return musics;
	}
	public void setMusics(List<Music> musics) {
		this.musics = musics;
	}
	
}
