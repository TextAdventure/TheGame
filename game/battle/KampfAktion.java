package game.battle;

import game.SpielWelt;
import game.entity.Entity;
import game.entity.Faehigkeit;
import game.items.VerwendbarerGegenstand;

/**
 * Eine Aktion eines Entities im Kampf, wird hierdurch repraesentiert. Dies bedeutet ein Angriff
 * oder den Einsatz eines Gegenstand gegen ein Ziel.
 * @author Marvin
 */
public class KampfAktion implements Comparable<KampfAktion> {

	/* --- Variablen --- */
	
	// Das angreifende Entity.
	private Entity angreifer;
	// Die Faehigkeit, die das Entity einsetzt.
	private Faehigkeit faehigkeit;
	// Der Gegenstand, der eingesetzt werden soll.
	private VerwendbarerGegenstand gegenstand;
	// Die effektive Flinkheit des Angreifers.
	private int flk;
	// Das Ziel, das angegriffen wird.
	private Entity ziel;
	
	/* --- Konstruktor --- */
	
	/**
	 * Eine Kampfaktion, bei der ein Entity ein anderes angreift mit der uebergebenen Faehigkeit.
	 * @param angreifer Das Entity, das angreift.
	 * @param faehigkeit Die Faehigkeit, die das Entity einsetzt.
	 * @param ziel Das Ziel, das angegriffen wird.
	 */
	public KampfAktion(Entity angreifer, Faehigkeit faehigkeit, Entity ziel) {
		this.angreifer = angreifer;
		this.faehigkeit = faehigkeit;
		this.gegenstand = null;
		this.flk = (int) (angreifer.getTempAttribut("flk") * (SpielWelt.WELT.r.nextInt(31) + 85) / 100.0);
		this.ziel = ziel;
	}
	
	/**
	 * Ein Entity benutzt einen Gegenstand gegen ein ziel.
	 * @param benutzer Das Entity, das den Gegenstand einsetzt.
	 * @param gegenstand Der Gegenstand, der eingesetzt werden soll.
	 * @param ziel Das Ziel, auf den der Gegenstand eingesetzt werden soll.
	 */
	public KampfAktion(Entity benutzer, VerwendbarerGegenstand gegenstand, Entity ziel) {
		this.angreifer = benutzer;
		this.gegenstand = gegenstand;
		this.faehigkeit = gegenstand.getFaehigkeit();
		this.flk = (int) (angreifer.getTempAttribut("flk") * (SpielWelt.WELT.r.nextInt(31) + 85) / 100.0);
		this.ziel = ziel;
	}

	/* --- Methoden --- */
	
	/**
	 * Gibt die Flinkheit des Angreifers zurueck.
	 * @return Die Flinkheit des Angreifers.
	 */
	public int getFlk() {
		return flk;
	}
	
	/**
	 * Gibt den Angreifer zurueck.
	 * @return Den Angreifer.
	 */
	public Entity getAngreifer() {
		return angreifer;
	}
	
	/**
	 * Gibt das Ziel der Aktion zurueck.
	 * @return Das Ziel.
	 */
	public Entity getZiel() {
		return ziel;
	}
	
	/**
	 * Macht die Aktion ungueltig.
	 */
	public void ungueltig() {
		this.faehigkeit = null;
		this.gegenstand = null;
	}
	
	/**
	 * Ueberprueft, ob es eine gueltige Aktion ist oder nicht.	
	 * @return true, wenn sie gueltig ist, ansonsten false.
	 */
	public boolean isGueltigeAktion() {
		if(ziel == null)
			return false;
		if(faehigkeit != null)
			return true;
		if(gegenstand != null)
			return true;
		return false;
	}
	
	/**
	 * Fuehrt die Aktion aus.
	 */
	public void fuehreAktionAus() {
		if(faehigkeit == null && gegenstand != null) {
			SpielWelt.WELT.verwendeGegenstand(gegenstand);
			return;
		}
		
		if(faehigkeit != null) {
			int angriff = (int) (faehigkeit.getBonus(angreifer) * (SpielWelt.WELT.r.nextInt(31) + 85) / 100.0);
			angriff = angreifer.getSchadensBonus(faehigkeit.getSchadensart(), angriff);
			// Der Schaden ist auf 99.999 begrenz, aber ist gleichzeitig immer groesser als 0t.
			int schaden = Math.min(Math.max(ziel.fuegeSchadenZu(angriff, faehigkeit.getSchadensart()), 0), 99999);
			// Ausgabe
			String ausgabe = faehigkeit.getAusgabe();
			ausgabe = ausgabe.replaceAll("#", Integer.toString(schaden));
			
			for(char c : ausgabe.toCharArray()) {
	    		if(c == '§') {
	    			int fall = Integer.valueOf(ausgabe.substring(ausgabe.indexOf(c) + 1, ausgabe.indexOf(c) + 2));
	    			ausgabe = ausgabe.replaceFirst(String.valueOf(fall), "");
	    			
	    			if(ausgabe.startsWith("§"))
	    				ausgabe = ausgabe.replaceFirst("§", angreifer.getNumGen().getBest(fall) + angreifer.getName());
	    			else
	    				ausgabe = ausgabe.replaceFirst("§", angreifer.getNumGen().getBest(fall).toLowerCase() + angreifer.getName());
	    		}
	    		if(c == '&') {
	    			int fall = Integer.valueOf(ausgabe.substring(ausgabe.indexOf(c) + 1, ausgabe.indexOf(c) + 2));
	    			ausgabe = ausgabe.replaceFirst(String.valueOf(fall), "");
	    			
	    			if(ausgabe.startsWith("&"))
	    				ausgabe = ausgabe.replaceFirst("&", ziel.getNumGen().getBest(fall) + ziel.getName());
	    			else
	    				ausgabe = ausgabe.replaceFirst("&", ziel.getNumGen().getBest(fall).toLowerCase() + ziel.getName());
	    		}
	    	}				
			SpielWelt.WELT.println(ausgabe);
			// Ausgabe Ende
			return;
		}
		
		SpielWelt.WELT.println("Es konnte keine Aktion für " + angreifer.getNumGen().getBest(2).toLowerCase() + angreifer.getName() + " ausgeführt werden.");
	}

	/**
	 * Vergleicht zwei Aktionen und gibt einen int Wert zurueck, dieser ist kleiner, wenn die
	 * andere KampfAktion kleiner(frueher dran) ist und umgkehrt einen positiven Wert.
	 * @param aktion Eine andere KampfAktion.
	 * @return -1 wenn die Aktion schneller ist, 0 bedeutet gleich und 1 langsamer als diese Aktion.
	 */
	@Override
	public int compareTo(KampfAktion aktion) {
		if(this.flk > aktion.getFlk())
			return -1;
		else if(this.flk == aktion.getFlk())
			return 0;
		else
			return 1;
	}
	
}
