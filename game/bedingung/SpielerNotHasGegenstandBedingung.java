package game.bedingung;

import game.Gegenstand;
import game.SpielWelt;
import game.aktion.Aktion;

public class SpielerNotHasGegenstandBedingung extends Bedingung {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Gegenstand, auf den das Inventar des Spielers ueberprueft wird.
	private Gegenstand gegenstand;
	// Die SpielWelt wird von Bedingung uebernommen.
	// Die Aktionen ebenfalls.
	// Der Zaehler auch.
	// Und der boolean fuer das Erfuellen der Bedingung.
	
	/**
	 * Eine SpielerNotHasGegenstandBedingung ueberprueft das Inventar des Spielers auf den @gegenstand und fuehrt gegebenenfalls @aktionen aus.
	 * @param spielwelt Die SpielWelt in der die Bedingung ueberprueft werden soll.
	 * @param gegenstand Der Gegenstand, den der Spieler nicht in seinem Inventar haben darf, damit sie erfuellt ist.
	 * @param aktionen Die Aktionen, die bei erfolgreicher ueberpruefung ausgefuerht werden.
	 */
	public SpielerNotHasGegenstandBedingung(SpielWelt spielwelt, Gegenstand gegenstand, Aktion... aktionen) {
		this.spielwelt = spielwelt;
		this.gegenstand = gegenstand;
		this.aktionen = aktionen;
		for(Aktion a: aktionen) {
			a.setWelt(spielwelt);
		}
		zaehler = 1;
	}
	
	/**
	 * Diese Methode loest die Ueberpruefung aus(falls der Spieler den Gegenstand nicht hat, werden die Aktionen ausgefuehrt).
	 */
	@Override
	public boolean pruefen() {
		if(!spielwelt.getInventar().containsGegenstand(gegenstand)) {
			// Die Bedingung wurde damit mindestens ein mal erfuellt.
			bedingungErfuellt = true;
			// Alle Aktionen werden ausgefuehrt.
			for(Aktion a: aktionen) {
				a.ausfuehren();
			}
			if(zaehler > 1) zaehler--;
			else if(zaehler > -1) spielwelt.loescheEreignis(this);
			// Falls der Zaehler auf -1 eingestellt wird bedeutet dies undelich mal ausfuehren.
			
			return true;
		}
		return false;
	}
	
}
