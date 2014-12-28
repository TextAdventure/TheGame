package game.entity;

import java.io.Serializable;

/**
 * Ein Level fuer den Spieler mit allen moeglichen Attributsaenderungen.
 */
public class Level implements Serializable {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	/* --- Die Variablen --- */
	
	// Die Erfahrung bis zu diesem Level.
	private int erfahrung;
	
	// Aenderungen der Hauptwerte.
	private int lpBonus;
	private int mpBonus;
	
	// Aenderungen der Attribute.	
	private EntityAttribut[] attributsBonus;
	
	/* --- Der Konstruktor --- */
	
	/**
	 * Ein neues Level wird mit allen Veraenderungen der Statuswerte generiert.
	 * @param erfahrung Die benoetigte Erfahrung bis zu diesem Level.
	 * @param lpBonus Der Bonus auf die Lebenspunkte.
	 * @param mpBonus Der Bonus auf die Magiepunkte.
	 * @param attributsBonus Der Bonus auf das Attribut mit der entsprechenden ID.
	 */
	public Level(int erfahrung, int lpBonus, int mpBonus, int... attributsBonus) {
		this.erfahrung = erfahrung;
		this.lpBonus = lpBonus;
		this.mpBonus = mpBonus;
		this.attributsBonus = new EntityAttribut[Attribut.getMaxId()];
		for(int i = 0; i < Attribut.getMaxId(); i++)
			this.attributsBonus[i] = new EntityAttribut(Attribut.ATTRIBUTE[i], attributsBonus[i]);
			
	}
	
	/* --- Die Methoden --- */
	
	/**
	 * Gibt die Erfahrung aus, die fuer diese Stufe benoetigt wird. 
	 * @return Die Erfahrung fuer dieses Level.
	 */
	public int getErfahrung() {
		return erfahrung;
	}
	/**
	 * Gibt den Bonus fuer die Lebenspunkte fuer dieses Level zurueck.
	 * @return Der Bonus fuer die Lebenspunkte.
	 */
	public int getLpBonus() {
		return lpBonus;
	}
	/**
	 * Gibt den Bonus fuer die Magiepunkte duer dieses Level zurueck.
	 * @return Der Bonus fuer die Magiepunkte.
	 */
	public int getMpBonus() {
		return mpBonus;
	}
	/**
	 * Gibt den Bonus fuer ein bestimmtes Attribut zurueck, basierend auf dem Namen oder der Parameterschreibweise.
	 * @param nameOderParam Der Name oder die Parameterschreibweise des Attributs.
	 * @return Den Bonuswert fuer das Attribut, -1 wenn es das Attribut nicht gibt.
	 */
	public int getAttributsBonus(String nameOderParam) {
		for(EntityAttribut ea : attributsBonus)
			if(ea.getAttribut().getName().equals(nameOderParam) || ea.getAttribut().getParam().equals(nameOderParam))
				return ea.getWert();
		return -1;
	}
	
	/* --- Die statischen Methoden --- */
	
	/**
	 * Erzeugt aus Anfangswerten und Zielwerten, ein Array aller Level, die dafuer benoetigt werden, aufgrund einer linearen Verteilung.
	 * @param levelAnzahl Die Anzahl an Leveln.
	 * @param anfangsWerte Die anfaengliche Werte als Level uebergeben.
	 * @param zielWerte Die Zielwerte des Charakters auf dem hoechsten Level.
	 * @return Alle Level fuer den Spieler, die 0 sind die Startwerte fuer den Spieler.
	 */
	public static Level[] createLinearLevels(int levelAnzahl, Level anfangsWerte, Level zielWerte) {
		Level[] level = new Level[levelAnzahl + 2];
		// Zuerst werden die Faktoren fuer die einzelnen Werte berechnet.
		int erfFak = (zielWerte.getErfahrung() - anfangsWerte.getErfahrung()) / levelAnzahl;
		int lpFak = (zielWerte.getLpBonus() - anfangsWerte.getLpBonus()) / levelAnzahl;
		int mpFak = (zielWerte.getMpBonus() - anfangsWerte.getMpBonus()) / levelAnzahl;
		int[] attributFaks = new int[anfangsWerte.attributsBonus.length];
		for(int i = 0; i < attributFaks.length; i++)
			attributFaks[i] = (zielWerte.getAttributsBonus(Attribut.ATTRIBUTE[i].getName()) 
					- anfangsWerte.getAttributsBonus(Attribut.ATTRIBUTE[i].getName())) / levelAnzahl;
		
		int sigmaErf = anfangsWerte.getErfahrung();
		int sigmaLp = anfangsWerte.getLpBonus();
		int sigmaMp = anfangsWerte.getMpBonus();
		int[] sigmaAttribute = new int[attributFaks.length];
		
		level[0] = anfangsWerte;
		for(int i = 1; i <= levelAnzahl; i++) {
			level[i] = new Level(erfFak, lpFak, mpFak, attributFaks);
			sigmaErf += erfFak;
			sigmaLp += lpFak;
			sigmaMp += mpFak;
			for(int j = 0; j < sigmaAttribute.length; j++)
				sigmaAttribute[j] += attributFaks[j];
		}
		
		for(int i = 0; i < sigmaAttribute.length; i++) {
			sigmaAttribute[i] += anfangsWerte.getAttributsBonus(Attribut.ATTRIBUTE[i].getName());
			sigmaAttribute[i] = zielWerte.getAttributsBonus(Attribut.ATTRIBUTE[i].getName()) - sigmaAttribute[i] + attributFaks[i];
		}			
		
		level[levelAnzahl + 1] = new Level(zielWerte.getErfahrung() - sigmaErf, zielWerte.getLpBonus() - sigmaLp + lpFak, zielWerte.getMpBonus() - sigmaMp + mpFak
				, sigmaAttribute);
		
		return level;
	}
}
