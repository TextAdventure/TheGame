package game.bedingung;

import game.Gegenstand;
import game.SpielWelt;
import game.aktion.Aktion;

public class SpielerHasAusgeruestetBedingung extends Bedingung {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Gegenstand, den der Spieler ausgeruestet haben muss, sodass die Bedingung erfuellt ist.
	private Gegenstand gegenstand;
	// Die SpielWelt wird von Bedingung uebernommen.
	// Die Aktionen ebenfalls.
	// Der Zaehler auch.
	// Und der boolean fuer das Erfuellen der Bedingung.
	
	/**
	 * Eine SpielerHasAusgeruestetBedingung prueft, ob der Spieler einen @gegenstand ausgeruestet hat und fuehrt gegebenfalls @aktionen aus.
	 * @param spielwelt Die SpielWelt in der die Bedingung ueberprueft werden soll.
	 * @param gegenstand Der Gegenstand, den der Spieler ausgeruestet haben muss.
	 * @param aktionen Die Aktionen, die bei erfolgreicher ueberpruefung ausgefuerht werden.
	 */
	public SpielerHasAusgeruestetBedingung(SpielWelt spielwelt, Gegenstand gegenstand, Aktion... aktionen) {
		this.spielwelt = spielwelt;
		this.gegenstand = gegenstand;
		this.aktionen = aktionen;
		for(Aktion a: aktionen) {
			a.setWelt(spielwelt);
		}
		zaehler = 1;
	}
	
	/**
	 * Diese Methode loest die Ueberpruefung aus(falls der Spieler diesen Gegenstand ausgeruestet hat, werden die Aktionen ausgefuehrt).
	 */
	@Override
	public boolean pruefen() {
		if(spielwelt.getSpieler().getAusruestung().istAusgeruestet(gegenstand)) {
			// Die Bedingung wurde damit mindestens ein mal erfuellt.
			bedingungErfuellt = true;
			// Alle Aktionen werden ausgefuehrt.
			for(Aktion a: aktionen) {
				a.ausfuehren();
			}
			if(zaehler > 1) zaehler--;
			else if(zaehler > -1) spielwelt.loescheEreignis(this);
			// Falls der Zaehler auf -1 eingestellt wird bedeutet dies undelich.
			
			return true;
		}
		return false;
	}
	
}
