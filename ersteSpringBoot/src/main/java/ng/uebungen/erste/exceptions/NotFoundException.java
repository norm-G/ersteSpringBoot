package ng.uebungen.erste.exceptions;

public class NotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotFoundException(Long id, String item){
		super("Konnte "+item+" mit ID:"+id+" nicht finden");
	}
	
	public NotFoundException(String item){
		super("Konnte "+item+" nicht finden!");
	}	
}
