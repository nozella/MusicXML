package br.com.nozella.musicxml.exception;

public class InvalidArgumentException extends Exception {

	private static final long serialVersionUID = 4803554231795589051L;
	
	public InvalidArgumentException(){
		super();
	}
	
	public InvalidArgumentException(String msg){
		super(msg);
	}
	
	public InvalidArgumentException(Throwable cause){
		super(cause);
	}
	
	public InvalidArgumentException(String msg, Throwable cause){
		super(msg, cause);
	}

}
