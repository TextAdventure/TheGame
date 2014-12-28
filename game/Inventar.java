package game;

import java.io.Serializable;
import java.util.Collections;
import java.util.Vector;

import util.InventoryEvent;
import util.InventoryListener;
import util.StringEvent;
import util.StringListener;

/**
 *  Diese Klasse ist des Inventar des Spielers. Es enthaelt Informationen ueber die Gegenstaende darin.
 */
public class Inventar implements Serializable {

	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;

	// Alle Gegenstaende, die sich im Inventar befinden.
	private Vector<Stapel> gegenstaende;
	  
	// Alle Listener fuer dieses Inventar.
	private Vector<Object> listeners;

	/**
	 *  Eine neues Inventar wird leer erzeugt.
	 */
	public Inventar(){
	    gegenstaende = new Vector<Stapel>();
	    listeners = new Vector<Object>();
	}
	  
	/**
	 *  Diese Methode fuegt ein neuen Gegenstand zum Inventar hinzu.
	 *  gegenstand: der neue Gegenstand im Inventar.
	 *  anzahl: die Anzahl des Gegenstands.
	 */
	public void addGegenstand(Gegenstand gegenstand, int anzahl){
		if(gegenstand == null) return;
	    for(Stapel s: gegenstaende.toArray(new Stapel[0])){
	    	if(s.getGegenstand() == gegenstand){
	    		s.addAnzahl(anzahl);
	    		notifyListeners(gegenstand.getName());
	    		return;
	    	}
	    }
	    gegenstaende.add(new Stapel(gegenstand, anzahl));
	    notifyListeners(gegenstand.getName());
	}
	  
	/**
	 *  Diese Methode fuegt einen Stapel dem Inventar hinzu.
	 *  stapel: der neue Stapel im Inventar.
	 */
	public void addGegenstand(Stapel stapel){
		addGegenstand(stapel.getGegenstand(), stapel.getAnzahl());
	}
	  
	/**
	 *  Diese Methode entfernt einen Gegenstand aus dem Inventar.
	 *  gegenstand: der zu entfernende Gegenstand.
	 */
	public void removeGegenstand(Gegenstand gegenstand, int anzahl){
	    clearNull();
	    if(gegenstand == null) return;
	    for(Stapel s: gegenstaende.toArray(new Stapel[0])){
	    	if(s.getGegenstand() == gegenstand){
	    		if(!s.removeAnzahl(anzahl)){
	    			gegenstaende.remove(s);
	    		}
	    	}
	    }
	    notifyListeners();
	}
	  
	/**
	 *  Diese Methode entfernt einen Gegenstand aus dem Inventar basierend auf einem Stapel.
	 *  stapel: der zu entfernende Stapel.
	 */
	public void removeGegenstand(Stapel stapel){
		if(stapel == null) return;
	    removeGegenstand(stapel.getGegenstand(), stapel.getAnzahl());
	}
	  
	/**
	 *  Diese Methode entfernt alle vorhandenen Gegenstaende eines bestimmten Typs aus dem Inventar.
	 *  gegenstand: der zu entfernende Gegenstand.
	 */
	public void removeAlleGegenstand(Gegenstand gegenstand){
	    clearNull();
	    if(gegenstand == null) return;
	    for(Stapel s: gegenstaende.toArray(new Stapel[0])){
	    	if(s.getGegenstand() == gegenstand){
	    		gegenstaende.remove(s);
	    	}	
	    }
	    notifyListeners();
	}
	  
	/**
	 *  Diese Methode ueberprueft, ob sich der angegeben Gegenstand im Inventar befindet.
	 *  gegenstand: der Gegenstand, auf den ueberprueft werden soll.
	 */
	public boolean containsGegenstand(Gegenstand gegenstand){
	    clearNull();
	    if(gegenstand == null) return false;
	    for(Stapel s: gegenstaende.toArray(new Stapel[0])){
	    	if(s.getGegenstand() == gegenstand) return true;
	    }
	    return false;
	}
	  
	/**
	 *  Diese Methode ueberprueft, ob sich der angegeben Gegenstand im Inventar befindet und ob es auch mehr als die uebergebene Anzahl sind.
	 *  gegenstand: der Gegenstand, auf den ueberprueft werden soll.
	 *  anzahl: die Anzahl des Gegenstands im Inventar.
	 */
	public boolean containsGegenstand(Gegenstand gegenstand, int anzahl){
	    clearNull();
	    if(gegenstand == null) return false;
	    for(Stapel s: gegenstaende.toArray(new Stapel[0])){
	    	if(s.getGegenstand() == gegenstand && s.getAnzahl() >= anzahl) return true;
	    }
	    return false;
	}
	  
	/**
	 *  Diese Methode ueberprueft, ob sich der angegeben Gegenstand im Inventar befindet und ob es auch mehr als die uebergebene Anzahl sind.
	 *  stapel: der Stapel des Gegenstands.
	 */
	public boolean containsGegenstand(Stapel stapel){
	    if(stapel != null) return containsGegenstand(stapel.getGegenstand(), stapel.getAnzahl());
	    else return false;
	}
	  
	/**
	 *  Diese Methode gibt zurueck, ob das Inventar leer ist.
	 */
	public boolean istLeer(){
	    if(gegenstaende.size() == 0){
	    	return true;
	    }else{
	    	return false;
	    }
	}
	  
	/**
	 *  Diese Methode gibt ein Array mit allen Gegenstaenden zurueck.
	 */
	public Gegenstand[] getAlleGegenstaende(){
	    Gegenstand[] g = new Gegenstand[gegenstaende.size()];
	    for(int i = 0; i < gegenstaende.size(); i++){
	    	g[i] = gegenstaende.get(i).getGegenstand();
	    }
	    return g;
	}
	  
	/**
	 *  Diese Methode gibt ein Array mit allen Stapeln zurueck.
	 */
	public Stapel[] getAlleStapel(){
	    return gegenstaende.toArray(new Stapel[0]).clone();
	}

	/**
	 *  Diese Methode gibt den Stapel entsprechend fuer einen Gegenstand zurueck.
	 *  gegenstand: der Gegenstand des zusuchenden Stapels.
	 */
	public Stapel getStapel(Gegenstand gegenstand){
	    for(Stapel s: gegenstaende.toArray(new Stapel[0])){
	    	if(s.getGegenstand() == gegenstand) return s;
	    }
	    return null;
	}
	  
	/**
	 *  Diese Methode sortiert die Gegenstaende im Inventar alphabetisch.
	 */
	public void sortiere(){
		Vector<String> v = new Vector<String>();
	    for(Gegenstand g: getAlleGegenstaende()){
	    	v.add(g.getName());
	    }
	    Collections.sort(v);
	    Vector<Stapel> g = new Vector<Stapel>();
	    for(String s: v.toArray(new String[0])){
	    	Gegenstand ge = Gegenstand.getGegenstand(s);
	    	if(containsGegenstand(ge)) g.add(new Stapel(ge, getStapel(ge).getAnzahl()));
	    }
	    gegenstaende = g;
	}
	  
	/**
	 *  Diese Methode gibt eine Kopie des Inventars zurueck.
	 */
	public Inventar clone(){
	    Inventar i = this;
	    return i;
	}
	  
	/**
	 *  Diese Methode entfernt alle null-Gegenstaende im Inventar.
	 */
	private void clearNull(){
	    for(Gegenstand g: getAlleGegenstaende()){
	    	if(g == null) gegenstaende.remove(g);
	    }
	}
	  
	/**
	 *  Diese Methode fuegt dem Ineventar einen neuen Listener hinzu.
	 *  listener: der neue Listener, der hinzugefuegt werden soll.
	 */
	public void addListener(Object listener){
		if(!listeners.contains(listener)){
			listeners.add(listener);
			notifyListeners();
	    }
	}
	  
	/**
	 *  Diese Methode updated alle StringListener dieses Inventars.
	 *  gegenstandName: der Name des neuen Gegenstands.
	 */
	public void notifyListeners(String... gegenstandsName){
		for(Object listener: listeners.toArray(new Object[0])){
			if(listener instanceof InventoryListener){
				InventoryListener il = (InventoryListener)listener;
				il.inventoryUpdate(new InventoryEvent(this));
			}
			if(listener instanceof StringListener && gegenstandsName.length > -1){
				StringListener sl = (StringListener)listener;
				for(String s: gegenstandsName){
					sl.actionPerformed(new StringEvent(s));
				}
			}
	    }
	}
}