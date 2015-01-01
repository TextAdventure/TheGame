package game.items;

import java.io.Serializable;
import java.util.Collections;
import java.util.Vector;

import util.InventarListener;
import util.StringListener;

/**
 *  Diese Klasse ist ein Inventar. Es enthaelt Informationen ueber die Gegenstaende darin.
 */
public class Inventar implements Serializable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- Variablen --- */
	
	// Alle Gegenstaende, die sich im Inventar befinden.
	private Vector<Stapel> gegenstaende;
	// Der Geldbeutel in diesem Inventar.
	private Geldbeutel geldbeutel;
	  
	// Alle Listener fuer dieses Inventar.
	transient private Vector<Object> listeners;

	/* --- Der Konstruktor --- */
	
	/**
	 * Ein neues Inventar wird leer erzeugt, ohne Gegenstaende darin.
	 */
	public Inventar() {
	    gegenstaende = new Vector<Stapel>();
	    geldbeutel = new Geldbeutel();
	    listeners = new Vector<Object>();
	}
	  
	/**
	 * Fuegt einen neuen Gegenstand dem Inventar hinzu.
	 * @parma gegenstand Der neue Gegenstand fuer das Inventar.
	 * @param anzahl Die Anzahl des Gegenstands.
	 */
	public void addGegenstand(Gegenstand gegenstand, int anzahl) {
		if(gegenstand == null) 
			return;
		
		if(gegenstand instanceof Waehrung) {
			geldbeutel.addMenge((Waehrung) gegenstand, anzahl);
			notifyListeners(gegenstand.getName());
			return;
		}
		
	    for(Stapel s: gegenstaende.toArray(new Stapel[0])) {
	    	if(s.getGegenstand() == gegenstand) {
	    		s.addAnzahl(anzahl);
	    		notifyListeners(gegenstand.getName());
	    		return;
	    	}
	    }
	    gegenstaende.add(new Stapel(gegenstand, anzahl));
	    notifyListeners(gegenstand.getName());
	}
	  
	/**
	 * Fuegt einen Stapel dem Inventar hinzu.
	 * @param stapel Der neue Stapel fuer Inventar.
	 */
	public void addGegenstand(Stapel stapel) {
		addGegenstand(stapel.getGegenstand(), stapel.getAnzahl());
	}
	  
	/**
	 * Entfernt einen Gegenstand aus dem Inventar.
	 * @param gegenstand Der zu entfernende Gegenstand.
	 * @param anzahl Die Anzahl an Gegenstaenden, die entfernt werden soll.
	 */
	public void removeGegenstand(Gegenstand gegenstand, int anzahl) {
	    //clearNull(); TODO
	    if(gegenstand == null) 
	    	return;
	    for(Stapel s: gegenstaende.toArray(new Stapel[0])) {
	    	if(s.getGegenstand() == gegenstand) {
	    		if(!s.removeAnzahl(anzahl)) {
	    			gegenstaende.remove(s);
	    		}
	    	}
	    }
	    notifyListeners();
	}
	  
	/**
	 * Entfernt einen Gegenstand aus dem Inventar basierend auf einem Stapel.
	 * @param stapel Der zu entfernende Stapel.
	 */
	public void removeGegenstand(Stapel stapel) {
		if(stapel == null) 
			return;
	    removeGegenstand(stapel.getGegenstand(), stapel.getAnzahl());
	}
	  
	/**
	 * Entfernt alle vorhandenen Gegenstaende eines bestimmten Gegenstands aus dem Inventar.
	 * @param gegenstand Der zu entfernende Gegenstand.
	 */
	public void removeAlleGegenstand(Gegenstand gegenstand) {
	    clearNull();
	    if(gegenstand == null) 
	    	return;
	    for(Stapel s: gegenstaende.toArray(new Stapel[0])) {
	    	if(s.getGegenstand() == gegenstand) {
	    		gegenstaende.remove(s);
	    	}	
	    }
	    notifyListeners();
	}
	  
	/**
	 * Ueberprueft, ob sich der angegebene Gegenstand im Inventar befindet.
	 * @param gegenstand Der Gegenstand, auf den ueberprueft werden soll.
	 * @return Gibt zurueck, ob sich der Gegenstand im Inventar befindet.
	 */
	public boolean containsGegenstand(Gegenstand gegenstand) {
	    return containsGegenstand(new Stapel(gegenstand, 1));
	}
	  
	/**
	 * Ueberprueft, ob sich der angegeben Gegenstand im Inventar befindet und ob es auch mehr als die uebergebene Anzahl sind.
	 * @param gegenstand Der Gegenstand, auf den ueberprueft werden soll.
	 * @param anzahl Die Mindestanzahl des Gegenstands im Inventar.
	 * @return Gibt zurueck, ob sich der Gegenstand im Inventar befindet in einer gewissen Anzahl.
	 *
	public boolean containsGegenstand(Gegenstand gegenstand, int anzahl) {
	    clearNull();
	    if(gegenstand == null) 
	    	return false;
	    for(Stapel s: gegenstaende)
	    	if(s.getGegenstand() == gegenstand && s.getAnzahl() >= anzahl) 
	    		return true;
	    for(Stapel s : geldbeutel.getAlleStapel())
	    	if(s.getGegenstand() == gegenstand && geldbeutel.getMenge((Waehrung) s.getGegenstand()) >= anzahl)
	    		return true;
	    return false;
	}
	  
	/**
	 * Ueberprueft, ob sich der angegeben Gegenstand im Inventar befindet und ob es auch mehr als die uebergebene Anzahl sind.
	 * @param stapel dDer Stapel des Gegenstands.
	 * @return Gibt zurueck, ob sich der Gegenstand im Inventar befindet in einer gewissen Anzahl.
	 */
	public boolean containsGegenstand(Stapel stapel) {
		clearNull();
	    if(stapel == null || stapel.getGegenstand() == null) 
	    	return false;
	    for(Stapel s: gegenstaende)
	    	if(s.getGegenstand() == stapel.getGegenstand() && s.getAnzahl() >= stapel.getAnzahl()) 
	    		return true;
	    for(Stapel s : geldbeutel.getAlleStapel())
	    	if(s.getGegenstand() == stapel.getGegenstand() && geldbeutel.getMenge((Waehrung) s.getGegenstand()) >= stapel.getAnzahl())
	    		return true;
	    return false;
	}
	  
	/**
	 * Gibt zurueck, ob das Inventar leer ist.
	 * @return  Gibt true zurueck, falls das Inventar leer ist.
	 */
	public boolean istLeer() {
		if(gegenstaende.size() == 0 && geldbeutel.istLeer())
	    	return true;
	    else
	    	return false;	    
	}
	  
	/**
	 * Gibt ein Array mit allen Gegenstaenden zurueck.
	 * @return Alle Gegenstaende die sich im Inventar befinden.
	 */
	public Gegenstand[] getAlleGegenstaende() {
	    Gegenstand[] g = new Gegenstand[gegenstaende.size()];
	    for(int i = 0; i < gegenstaende.size(); i++)
	    	g[i] = gegenstaende.get(i).getGegenstand();
	    return  g;
	}
	
	/**
	 * Gibt ein Array mit allen Stapel zurueck.
	 * @return Alle Gegenstaende als Stapel im Inventar.
	 */
	public Stapel[] getStapel() {
		return gegenstaende.toArray(new Stapel[0]);
	}
	/**
	 * Gibt ein Array mit allen Stapeln zurueck inklusive der Waehrungen.
	 * @return Alle Gegenstaende als Stapel im Inventar.
	 */
	public Stapel[] getAlleStapel() {
		Vector<Stapel> s = new Vector<Stapel>();
		for(Stapel stapel : gegenstaende)
			s.add(stapel);
		for(Stapel stapel : geldbeutel.getAlleStapel())
			s.add(stapel);
		return s.toArray(new Stapel[0]);
	}

	/**
	 * Gibt den Stapel entsprechend fuer einen Gegenstand zurueck.
	 * @param gegenstand Der Gegenstand des gesuchten Stapels.
	 * @return Der Stapel fuer den Gegenstand.
	 */
	public Stapel getStapel(Gegenstand gegenstand) {
	    for(Stapel s : gegenstaende) {
	    	if(s.getGegenstand() == gegenstand) 
	    		return s;
	    }
	    return null;
	}
	  
	/**
	 * Sortiert die Gegenstaende im Inventar alphabetisch.
	 */
	public void sortiere() {
		Vector<String> v = new Vector<String>();
	    for(Gegenstand g: getAlleGegenstaende()) {
	    	v.add(g.getName());
	    }
	    Collections.sort(v);
	    Vector<Stapel> g = new Vector<Stapel>();
	    for(String s: v.toArray(new String[0])) {
	    	Gegenstand ge = Gegenstand.getGegenstand(s);
	    	if(containsGegenstand(ge)) 
	    		g.add(new Stapel(ge, getStapel(ge).getAnzahl()));
	    }
	    gegenstaende = g;
	}
	  
	/**
	 * Gibt eine Kopie des Inventars zurueck.
	 * @return Eine Kopie des Inventars. 
	 */
	public Inventar clone() {
	    Inventar i = new Inventar();
	    for(Stapel s: this.getAlleStapel()) {
	    	i.addGegenstand(s);
	    }
	    return i;
	}
	  
	/**
	 * Entfernt alle null-Gegenstaende im Inventar.
	 */
	private void clearNull() {
	    for(Gegenstand g : this.getAlleGegenstaende()) {
	    	if(g == null) 
	    		gegenstaende.remove(g);
	    }
	}
	
	/**
	 * Gibt den Geldbeutel des Inventars zurueck.
	 * @return Den Geldbeutel des Inventars.
	 */
	public Geldbeutel getGeldbeutel() {
		return geldbeutel;
	}
	
	
	  
	/**
	 * Fuegt dem Ineventar einen neuen Listener hinzu.
	 * @param listener Der neue Listener, der hinzugefuegt werden soll.
	 */
	public void addListener(Object listener) {
		// Das Spiel wurde gerade geladen, deshalb ist kein Listener mehr da.
		if(listeners == null)
			listeners = new Vector<Object>();
		
		if(!listeners.contains(listener)) {
			listeners.add(listener);
			notifyListeners();
	    }
	}
	  
	/**
	 * Updated alle StringListener und InventoryListener dieses Inventars.
	 * @param gegenstandName Die Namen der neuen Gegenstaende.
	 */
	public void notifyListeners(String... gegenstandsName) {
		// Bei allen Dingen, die kein Spieler sind werden die Listener nicht benoetigt und sind einfach null.
		if(listeners == null)
			return;
		
		for(Object listener : listeners) {			
			if(listener instanceof InventarListener)
				((InventarListener) listener).inventarUpdate(this);
			
			if(listener instanceof StringListener && gegenstandsName.length > -1) {
				StringListener sl = (StringListener)listener;
				for(String s : gegenstandsName)
					sl.actionPerformed(s);
			}
	    }
	}
	
}