package game.items;

import java.io.Serializable;

/**
 *  Diese Klasse ist ein Stapel von Gegenstaenden, jeder hat eine bestimmte maximale Groesse.
 */
public class Stapel implements Comparable<Stapel>, Serializable {

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
	public Stapel(Gegenstand gegenstand, int anzahl) {
		this.gegenstand = (gegenstand == null) ? -1 :(short) gegenstand.getId();
	    this.anzahl = anzahl;
	}
	  
	/**
	 *  Diese Methode gibt den Gegenstand zurueck, der sich in dem Stapel befindet.
	 */
	public Gegenstand getGegenstand() {
	    return Gegenstand.getGegenstand(gegenstand);
	}
	  
	/**
	 *  Diese Methode gibt direkt den Name des Gegenstands zurueck.
	 */
	public String getName() {
	    return (anzahl > 1) ? Gegenstand.getGegenstand(gegenstand).getPlural() : Gegenstand.getGegenstand(gegenstand).getName();
	}
	  
	/**
	 *  Diese Methode gibt die aktuelle Anzahl an Gegenstaenden zurueck.
	 */
	public int getAnzahl() {
	    return anzahl;
	}
	  
	/**
	 *  Fuegt diesem Stapel mehr Gegenstaende hinzu.
	 *  anzahl: Anzahl neuer Gegenstaende.
	 */
	public void addAnzahl(int anzahl) {
	    this.anzahl += anzahl;
	}
	  
	/**
	 *  Entfernt von diesem Stapel eine gewisse Anzahl an Gegenstaenden.
	 *  anzahl: Anzahl, der zu entfernenden Gegenstaende.
	 */
	public boolean removeAnzahl(int anzahl) {
		if(this.anzahl - anzahl < 1){
			return false;
	    } else {
	    	this.anzahl -= anzahl;
	    	return true;
	    }
	}
	  
	/**
	 * Entfernt alle Gegenstaende aus dem Stapel.
	 */
	public void removeAll() {
		try {
			finalize();
	    } catch(Throwable e) {
	    	System.err.println("Der Stapel konnte nicht entfernt werden.");
	    }
	}
	  
	/**
	 *  Fuegt diesem Stapel einen Stapel mit dem gleichen Gegenstand hinzu.
	 *  stapel: der Stapel, der hinzugefuegt werden soll.
	 */
	public void addStapel(Stapel stapel) {
		this.anzahl += stapel.getAnzahl();
	}
	
	/**
	 * Testet, ob zwei Stapel gleich sind, das bedeutet, dass der Gegenstand und die Anzahl gleich sein muessen.
	 * @param stapel Der Stapel, der getestet werden soll.
	 * @return True, wenn der Gegenstand und die Anzahl gleich sind oder wenn die super Methode true zurueck gibt, ansonsten false.
	 */
	@Override
	public boolean equals(Object stapel) {
		if(super.equals(stapel))
			return true;
		else if(stapel instanceof Stapel) {
			Stapel s = (Stapel) stapel;
			if(s.getGegenstand() == null)
				return false;
			return (s.getGegenstand().equals(this.getGegenstand()) && s.getAnzahl() == this.getAnzahl());
		} else
			return false;
	}
	
	/**
	 * Vergleicht diesen Stapel mit einem anderen und gibt eien Zahl zurueck basierened darauf,
	 * ob der andere Stapel kleiner(-1), gleich(0) oder groesser(1) ist als dieser. Es wird
	 * dabei lediglich der Name der Gegenstaende beachtet.
	 * @param stapel Der Stapel mit dem dieser hier verglichen wird.
	 * @return -1, wenn der andere Stapel kleiner ist als dieser. 0, wenn Beide gleich sind und
	 * 1, wenn der andere Stapel groesser ist als dieser.
	 */
	@Override
	public int compareTo(Stapel stapel) {
		if(this.equals(stapel))
			return 0;
		else
			return this.getGegenstand().getNameExtended().compareTo(stapel.getGegenstand().getNameExtended());
	}

}