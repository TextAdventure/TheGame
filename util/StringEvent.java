package util;

/**
 * Dieses Event uebergibt an den Listener nur einen String.
 */
public class StringEvent {
	// Das Kommando des Spielers
	private String command;
	  
	// Ein neues Event wird mit einem Befehl erstellt.
	public StringEvent(String command){
		this.command = command;
	}
	  
	// Gibt den Befehl zurueck.
	public String getCommand(){
	    return command;
	}
}