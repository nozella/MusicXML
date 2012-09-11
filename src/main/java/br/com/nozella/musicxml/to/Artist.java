package br.com.nozella.musicxml.to;

import java.util.List;

public class Artist {
	
	private String name;
	private List<Album> albuns;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Album> getAlbuns() {
		return albuns;
	}
	public void setAlbuns(List<Album> albuns) {
		this.albuns = albuns;
	}
	
}
