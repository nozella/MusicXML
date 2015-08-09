package br.com.nozella.musicxml.exception;

public class SystemException extends Exception {

    private static final long serialVersionUID = 2356033870186210411L;

    public SystemException() {
        super();
    }

    public SystemException(String msg) {
        super(msg);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
