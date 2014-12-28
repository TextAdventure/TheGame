package game.bedingung;

import game.SpielWelt;
import game.UntersuchbaresObjekt;
import game.aktion.Aktion;

public class UntersuchbaresObjektIsUntersuchtBedingung extends Bedingung {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Das UntersuchbareObjekt, welches untersucht sein muss.
	private UntersuchbaresObjekt untersuchbaresObjekt;
	// Die SpielWelt wird von Bedingung uebernommen.
	// Die Aktionen ebenfalls.
	// Der Zaehler auch.
	// Und der boolean fuer das Erfuellen der Bedingung.
	
	/**
	 * Eine UntersuchbaresObjektIsUntersuchtBedingung ueberprueft ein @untersuchbaresObjekt, ob es bereits untersucht wurde und fuehrt
	 * gegebenenfalls @aktionen aus.
	 * @param spielwelt Die SpielWelt in der die Bedingung ueberprueft werden soll.
	 * @param untersuchbaresObjekt Das UntersuchbareObjekt, das ueberprueft werden soll.
	 * @param aktionen Die Aktionen, die bei erfolgreicher ueberpruefung ausgefuerht werden.
	 */
	public UntersuchbaresObjektIsUntersuchtBedingung(SpielWelt spielwelt, UntersuchbaresObjekt untersuchbaresObjekt, Aktion... aktionen) {
		this.spielwelt = spielwelt;
		this.untersuchbaresObjekt = untersuchbaresObjekt;
		this.aktionen = aktionen;
		for(Aktion a: aktionen) {
			a.setWelt(spielwelt);
		}
		zaehler = 1;
	}
	
	/**
	 * Diese Methode loest die Ueberpruefung aus(falls das UntersuchbareObjekt bereits untersucht wurde, werden die Aktionen ausgefuehrt).
	 */
	@Override
	public boolean pruefen() {
		if(untersuchbaresObjekt.wurdeUntersucht()) {
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
