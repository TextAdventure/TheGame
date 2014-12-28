package game.bedingung;

import game.Gegenstand;
import game.Ort;
import game.SpielWelt;
import game.aktion.Aktion;

public class OrtNotHasGegenstandBedingung extends Bedingung {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Ort, der auf einen Gegenstand ueberprueft wird.
	private Ort ort;
	// Der Gegenstand, der sich an nicht dem Ort befinden darf, sodass die Bedingung erfuellt ist.
	private Gegenstand gegenstand;
	// Die SpielWelt wird von Bedingung uebernommen.
	// Die Aktionen ebenfalls.
	// Der Zaehler auch.
	// Und der boolean fuer das Erfuellen der Bedingung.
	
	/**
	 * Eine OrtHotHasGegenstandBedingung ueberprueft einen @ort auf einen @gegenstand und fuehrt gegebenenfalls @aktionen aus.
	 * @param spielwelt Die SpielWelt in der die Bedingung ueberprueft werden soll.
	 * @param ort Der Ort, der keinen solchen Gegenstand haben darf, sodass die Bedingung erfuellt sein kann.
	 * @param gegenstand Dieser Gegenstand darf sich nicht an dem Ort befinden.
	 * @param aktionen Die Aktionen, die bei erfolgreicher ueberpruefung ausgefuerht werden.
	 */
	public OrtNotHasGegenstandBedingung(SpielWelt spielwelt, Ort ort, Gegenstand gegenstand, Aktion... aktionen) {
		this.spielwelt = spielwelt;
		this.ort = ort;
		this.gegenstand = gegenstand;
		this.aktionen = aktionen;
		for(Aktion a: aktionen) {
			a.setWelt(spielwelt);
		}
		zaehler = 1;
	}
	
	/**
	 * Diese Methode loest die Ueberpruefung aus(falls der Ort keinen solchen Gegenstand hat, werden die Aktionen ausgefuehrt).
	 */
	@Override
	public boolean pruefen() {
		if(!ort.hasGegenstand(gegenstand)) {
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
