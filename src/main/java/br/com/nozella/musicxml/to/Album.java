package br.com.nozella.musicxml.to;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class Album {

    private final String name;
    private Map<Integer, Music> musics;

    public Album(String name) {
        if (name == null) {
            throw new NullPointerException();
        }
        this.name = name;
        this.musics = new HashMap<Integer, Music>();
    }

    /**
     * The name of Album set on constructor
     *
     * @return never null
     */
    public String getName() {
        return this.name;
    }

    public Collection<Music> getMusics() {
        return this.musics.values();
    }

    public void setMusics(Collection<Music> musics) {
        for (Music music : musics) {
            this.putMusic(music);
        }
    }

    public void putMusic(Music music) {
        this.musics.put(music.getTiltle().hashCode(), music);
    }

    public Music getMusic(String tiltleMusic) {
        return this.musics.get(tiltleMusic.toUpperCase().hashCode());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.getName().hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Album) {
            return this.getName().equals(((Album) obj).getName());
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Album [name=%s, musics=%s]", this.getName(), this.getMusics());
    }

}
