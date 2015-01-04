package game.logic.aktion;

import game.SpielWelt;
import game.entity.Faehigkeit;

public class SpielerAddFaehigkeitAktion extends Aktion {
	
	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Die Faehigkeiten fuer den Spieler.
	private Faehigkeit faehigkeit;
	// Die SpielWelt ist static.
	
	/**
	 * Eine SpielerAddFaehigkeitAktion fuegt dem Spieler eine Faehigkeit hinzu.
	 * @param faehigkeit Die neue Faehigkeit fuer den Spieler.
	 */
	public SpielerAddFaehigkeitAktion(Faehigkeit faehigkeit) {
		this.faehigkeit = faehigkeit;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(fuegt dem Spieler eine Faehigkeit hinzu).
	 */
	@Override
	public void ausfuehren() {
		SpielWelt.WELT.getSpieler().addFaehigkeit(faehigkeit);
	}

}
