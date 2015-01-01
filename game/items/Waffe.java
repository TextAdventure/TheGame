package game.items;

import java.util.Vector;

import game.entity.Attribut;
import game.entity.EntityAttribut;
import util.NumerusGenus;

/**
 * Eine Waffe kann ausgeruestet werden und manche Arten von Waffen werden benoetigt, um bestimmte Faehigkeiten einzusetzen.
 * @author Marvin
 */
public class Waffe extends AusruestbarerGegenstand {
	
	// Die serielle Versionsnummer
	private static final long serialVersionUID = 1L;
	
	/* --- statische Konstanten --- */
	
	// Variable fuer Schwerthand
	public static final byte SCHWERTHAND = 0;
	// Variable fuer Schildhand
	public static final byte SCHILDHAND = 1;
	// Variable fuer eine der beiden Haende
	public static final byte BEIDHAENDIG = 2;
	// Variable fuer einen Zweihaender
	public static final byte ZWEIHAENDIG = 3;	
	
	// Der Namen der Waffenhaende.
	private static final String[] WAFFENHAND = new String[] {
		"Schwerthand",
		"Schildhand",
		"Beide Hände",
		"Zweihändig"
	};
	
	/* --- Variablen --- */
	
	// Die Hand, in der die Waffe gefuehrt wird.
	private byte hand;
	
	// Die Art der Waffe.
	private Waffenart waffenart;
	
	/* --- Konstruktor --- */
	
	/**
	 * Eine Waffe erbt die Eigenschaften von Gegenstand und fuegt diesen dann noch die Hand hinzu, in der die Waffe gefuehrt
	 * werden kann, die Art der Waffe, dies muss im WeltenGenerator genauer angegeben werden, welche Arten von Waffen es im
	 * Abenteuer gibt. Zum Schluss kommt der Bonus auf Lebenspunkte, Magiepunkte und alle Attribute, in der Reihenfolge in der
	 * sie im WeltenGenerator erstellt wurden.
	 * @param namenWaffe Die Namen dieser Waffe.
	 * @param plural Der Plural der Waffe.
	 * @param numerusGenus Der Numerus und Genus der Waffe.
	 * @param beschreibung Die Beschreibung der Waffe.
	 * @param hand Die Hand, in der die Waffe gefuehrt werden kann.
	 * @param art Die Art der Waffe, muss im WeltenGenerator genauer festgelegt werden, welche Arten von Waffen es in diesem Abenteuer gibt.
	 * @param lebenspunkte Der Lebenspunktebonus beim Tragen dieser Waffe.
	 * @param magiepunkte Der Magiepunktebonus beim Tragen dieser Waffe.
	 * @param attributswerte Die Attributsboni beim Tragen dieser Waffe.
	 */
	public Waffe(String[] namenWaffe, String plural, NumerusGenus numerusGenus, String beschreibung, byte hand, Waffenart art, int lebenspunkte, int magiepunkte,
			int... attributswerte) {
	    super(namenWaffe, plural, numerusGenus, beschreibung);
	    this.hand = hand;
	    this.waffenart = art;
	    
	    this.lp = lebenspunkte;
	    this.mp = magiepunkte;
	    for(int i = 0; i < Attribut.getMaxId(); i++)
			attribute[i] = new EntityAttribut(Attribut.ATTRIBUTE[i], attributswerte[i]);
	}
	
	/* --- Methoden --- */
	
	/**
	 * Gibt die Hand zurueck, in der die Waffe gefuehrt werden kann.
	 * @return Die verschiedenen Konstanten in dieser Klasse: SCHWERTHAND, SCHILDHAND, BEIDHAENDIG, ZWEIHAENDIG
	 */
	public byte getHand() {
		return hand;
	}
	  
	/**
	 * Gibt die Art der Waffe zurueck.
	 * @return Die Arten von Waffen in einem Abenteuer koennen im WeltenGenerator genauer bestimmt werden.
	 */
	public Waffenart getWaffenart() {
	    return waffenart;
	}
	
	/**
	 * Gibt die Art des Gegenstands zurueck.
	 * @return "Waffe"
	 */
	public String getGegenstandsart() {
		return "Waffe";
	}
	
	@Override
	public String getParam(String param) {
		if(super.getParam(param) != "Ungültig")
			return super.getParam(param);
		
		if(param == "typ") return this.getWaffenart().getName();
		if(param == "hand") return Waffe.getWaffenhandNamen(this.getHand());
		
		return "Ungültig";
	}
	
	@Override
	public String[] getParams() {
		Vector<String> s = new Vector<String>();
		
		for(String param : super.getParams())
			s.add(param);
		
		s.add("typ");
		s.add("hand");
		
		return s.toArray(new String[0]);
	}
	
	/* --- statische Methoden --- */
	
	/**
	 * Gibt den ausgeschriebenen Namen der Hand zurueck, in der die Waffe getragen werden kann.
	 * @param hand Eine der statischen Konstanten in dieser Klassse: SCHWERTHAND, SCHILDHAND, BEIDHAENDIG, ZWEIHAENDIG
	 * @return Den Namen der Hand, in der die Waffe gehalten werden kann.
	 */
	public static String getWaffenhandNamen(byte hand) {
		return WAFFENHAND[hand];
	}

}