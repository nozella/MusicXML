package br.com.nozella.musicxml.to;

public final class Music {

    private final String tiltle;
    private volatile int hashCode;

    /**
     * If the parameter is <b>null</b>, throws a  {@link NullPointerException}
     *
     * @param tiltle of the music
     */
    public Music(String tiltle) {
        /**
         * use toString() to throw a NullPointerException if the parameter is null
         */
        this.tiltle = tiltle.toString();
    }

    /**
     * The tiltle of Music set on constructor
     *
     * @return never null
     */
    public String getTiltle() {
        return tiltle;
    }

    @Override
    public int hashCode() {
        int result = this.hashCode;
        if (result == 0) {
            result = 1;
            result = 31 * result + this.getTiltle().hashCode();
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Music) {
            return this.getTiltle().equals(((Music) obj).getTiltle());
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Music [tiltle=%s]", this.getTiltle());
    }
}
