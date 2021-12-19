package net.yoedtos.lequotes.persistence;

public class DAOException extends Exception {

	private static final long serialVersionUID = 1L;

	public DAOException() {
		
	}
	
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DAOException(String message) {
		super(message);
	}
	
	public DAOException(Throwable cause) {
		super(cause);
	}
}
