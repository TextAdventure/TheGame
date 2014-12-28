package game;

import game.bedingung.Bedingung;
import util.NumerusGenus;

public class Schluessel extends Gegenstand {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Diese Bedingung muss auch erfuellt sein, sodass die Tuer geoffnet werden kann.
	private Bedingung bedingung;
	
	/**
	 * Ein Schluessel wird wie ein Gegenstand erstellt, mit @namenGegenstand und einem entsprechenden @numerusGenus und einer @beschreibung.
	 * @param namenGegenstand Alle Namen, die fuer diesen Schluessel gelten.
	 * @param numerusGenus Der Numerus und der Genus des Schluessels.
	 * @param beschreibung Die Beschreibung fuer diesen Schluessel.
	 */
	public Schluessel(String[] namenGegenstand, NumerusGenus numerusGenus, String beschreibung, Bedingung bedingung) {
		super(namenGegenstand, numerusGenus, beschreibung);
		this.bedingung = bedingung;
	}
	public Schluessel(String[] namenGegenstand, NumerusGenus numerusGenus, String beschreibung) {
		super(namenGegenstand, numerusGenus, beschreibung);
	}
	
	public void setBedingung(Bedingung bed) {
		this.bedingung = bed;
	}
	
	/**
	 * Der Schluessel wird verwendet und es wird die Bedingung geprueft und dann wird die "Tuer" geoffnet oder auch nicht.
	 */
	public boolean verwenden() {
		return bedingung.pruefen();
	}

}
