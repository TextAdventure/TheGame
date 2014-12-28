package game.logic.aktion;

import game.SpielWelt;
import game.entity.Faehigkeit;

public class SpielerAddFaehigkeitAktion extends Aktion {
	
	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	// Die Faehigkeiten fuer den Spieler.
	private Faehigkeit faehigkeit;
	
	/**
	 * Eine SpielerAddFaehigkeitAktion fuegt dem Spieler eine Faehigkeit hinzu und gibt danach eine Meldung aus.
	 * @param faehigkeit Die neue Faehigkeit fuer den Spieler.
	 * @param meldung Die Meldung, die ausgegeben werden soll.
	 */
	public SpielerAddFaehigkeitAktion(Faehigkeit faehigkeit, String meldung) {
		this.faehigkeit = faehigkeit;
		this.meldung = meldung;
	}
	
	/**
	 * Diese Methode fuehrt die Aktion aus(fuegt dem Spieler eine Faehigkeit hinzu) und gibt danach eine Meldung aus.
	 */
	@Override
	public void ausfuehren() {
		SpielWelt.WELT.getSpieler().addFaehigkeit(faehigkeit);
		// Falls keine richtige Meldung vorhanden ist, wird nichts ausgegeben.
		super.ausfuehren();
	}

}
