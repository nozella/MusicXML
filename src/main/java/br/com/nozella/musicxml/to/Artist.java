package br.com.nozella.musicxml.to;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class Artist {

    private final String name;
    private Map<Integer, Album> albuns;

    public Artist(String name) {
        if (name == null) {
            throw new NullPointerException();
        }
        this.name = name;
        this.albuns = new HashMap<Integer, Album>();
    }

    /**
     * The name of Artist set on constructor
     *
     * @return never null
     */
    public String getName() {
        return name;
    }

    public Collection<Album> getAlbuns() {
        return albuns.values();
    }

    public void setAlbuns(Collection<Album> albuns) {
        for (Album album : albuns) {
            this.putAlbum(album);
        }
    }

    public void putAlbum(Album album) {
        this.albuns.put(album.getName().hashCode(), album);
    }

    public Album getAlbum(String albumName) {
        return this.albuns.get(albumName.toUpperCase().hashCode());
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
        if (obj instanceof Artist) {
            return this.getName().equals(((Artist) obj).getName());
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Artist [name=%s, albuns=%s]", this.getName(), this.getAlbuns());
    }

}
