package game.bedingung;

import game.SpielWelt;
import game.aktion.Aktion;

public class KeineBedingung extends Bedingung {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Keine eigenen Variablen noetig.
	
	// Die SpielWelt wird von Bedingung uebernommen.
	// Die Aktionen ebenfalls.
	// Der Zaehler auch.
	// Und der boolean fuer das Erfuellen der Bedingung.
	
	/**
	 * Eine KeineBedingung fuehrt nur die @aktionen aus, ohne eine Bedingung zu ueberpruefen.
	 * @param spielwelt Die SpielWelt in der die Bedingung ueberprueft werden soll.
	 * @param aktionen Die Aktionen, die bei erfolgreicher ueberpruefung ausgefuerht werden.
	 */
	public KeineBedingung(SpielWelt spielwelt, Aktion... aktionen) {
		this.spielwelt = spielwelt;
		this.aktionen = aktionen;
		for(Aktion a: aktionen) {
			a.setWelt(spielwelt);
		}
		zaehler = 1;
	}
	
	/**
	 * Diese Methode loest die Ueberpruefung aus(die Aktionen werden einfach nur ausgefuehrt).
	 */
	@Override
	public boolean pruefen() {
		// Die Bedingung ist immer erfuellt.
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
	
}
