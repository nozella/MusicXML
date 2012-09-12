package br.com.nozella.musicxml.to;

public class Music {
	
	private String tiltle;
	
	public Music(String tiltle){
		this.setTiltle(tiltle);
	}
	
	public String getTiltle() {
		return tiltle;
	}
	
	public void setTiltle(String tiltle) {
		this.tiltle = tiltle.toUpperCase();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tiltle == null) ? 0 : tiltle.hashCode());
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
		Music other = (Music) obj;
		if (tiltle == null) {
			if (other.tiltle != null)
				return false;
		} else if (!tiltle.equals(other.tiltle))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Music [tiltle=%s]", this.getTiltle());
	}
}
