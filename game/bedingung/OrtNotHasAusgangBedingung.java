package game.bedingung;

import game.Ausgang;
import game.Ort;
import game.SpielWelt;
import game.aktion.Aktion;

public class OrtNotHasAusgangBedingung extends Bedingung {

	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Der Ort, der auf einen Ausgang ueberprueft wird.
	private Ort ort;
	// Der Ausgang, der sich an dem Ort nicht befinden darf, sodass die Bedingung erfuellt ist.
	private Ausgang ausgang;
	// Die SpielWelt wird von Bedingung uebernommen.
	// Die Aktionen ebenfalls.
	// Der Zaehler auch.
	// Und der boolean fuer das Erfuellen der Bedingung.
	
	/**
	 * Eine OrtNotHasAusgangBedingung ueberprueft einen @ort auf einen @ausgang und fuehrt gegebenenfalls @aktionen aus, falls der @ort
	 * keinen solchen @ausgang hat.
	 * @param spielwelt Die SpielWelt in der die Bedingung ueberprueft werden soll.
	 * @param ort Der Ort, der keinen solchen Ausgang haben darf, sodass die Bedingung erfuellt sein kann.
	 * @param ausgang Diesen Ausgang darf der Ort nicht haben.
	 * @param aktionen Die Aktionen, die bei erfolgreicher ueberpruefung ausgefuerht werden.
	 */
	public OrtNotHasAusgangBedingung(SpielWelt spielwelt, Ort ort, Ausgang ausgang, Aktion... aktionen) {
		this.spielwelt = spielwelt;
		this.ort = ort;
		this.ausgang = ausgang;
		this.aktionen = aktionen;
		for(Aktion a: aktionen) {
			a.setWelt(spielwelt);
		}
		zaehler = 1;
	}
	
	/**
	 * Diese Methode loest die Ueberpruefung aus(falls der Ort diesen Ausgang nicht hat, werden die Aktionen ausgefuehrt).
	 */
	@Override
	public boolean pruefen() {
		if(!ort.hasAusgang(ausgang)) {
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
