package game.items;

import java.io.Serializable;

/**
 *  Diese Klasse ist ein Stapel von Gegenstaenden, jeder hat eine bestimmte maximale Groesse.
 */
public class Stapel implements Serializable {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	// Der Gegenstand, der gelagert ist.
	private short gegenstand;
	// Die aktuelle Anzahl an Gegenstaenden.
	private int anzahl;

	/**
	 *  Ein neuer Stapel wird aufgrund eines Gegenstands erzeugt.
	 *  gegenstand: der Typ des neuen Stapels
	 *  anzahl: die Anzahl an Gegenstaenden, die zu Beginn in dem Stapel sind.
	 */
	public Stapel(Gegenstand gegenstand, int anzahl){
		this.gegenstand = gegenstand == null ? -1 :(short) gegenstand.getId();
	    this.anzahl = anzahl;
	}
	  
	/**
	 *  Diese Methode gibt den Gegenstand zurueck, der sich in dem Stapel befindet.
	 */
	public Gegenstand getGegenstand(){
	    return Gegenstand.getGegenstand(gegenstand);
	}
	  
	/**
	 *  Diese Methode gibt direkt den Name des Gegenstands zurueck.
	 */
	public String getName(){
	    return anzahl != 1 ? Gegenstand.getGegenstand(gegenstand).getPlural() : Gegenstand.getGegenstand(gegenstand).getName();
	}
	  
	/**
	 *  Diese Methode gibt die aktuelle Anzahl an Gegenstaenden zurueck.
	 */
	public int getAnzahl(){
	    return anzahl;
	}
	  
	/**
	 *  Fuegt diesem Stapel mehr Gegenstaende hinzu.
	 *  anzahl: Anzahl neuer Gegenstaende.
	 */
	public void addAnzahl(int anzahl){
	    this.anzahl += anzahl;
	}
	  
	/**
	 *  Entfernt von diesem Stapel eine gewisse Anzal an Gegenstaenden.
	 *  anzahl: Anzahl, der zu entfernenden Gegenstaende.
	 */
	public boolean removeAnzahl(int anzahl){
		if(this.anzahl - anzahl < 1){
			return false;
	    }else{
	    	this.anzahl -= anzahl;
	    	return true;
	    }
	}
	  
	/**
	 *  Entfernt alle Gegenstaende aus dem Stapel.
	 */
	public void removeAll(){
		try{
			finalize();
	    }catch(Throwable e){
	    	System.err.println("Der Stapel konnte nicht entfernt werden.");
	    }
	}
	  
	/**
	 *  Fuegt diesem Stapel einen Stapel mit dem gleichen Gegenstand hinzu.
	 *  stapel: der Stapel, der hinzugefuegt werden soll.
	 */
	public void addStapel(Stapel stapel){
		this.anzahl += stapel.getAnzahl();
	}
}