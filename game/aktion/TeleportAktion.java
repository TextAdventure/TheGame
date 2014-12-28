package game.aktion;

import game.Ort;

/**
 * Teleportiert den Spieler an den angegebenen Zielort.
 * @author Felix
 *
 */
public class TeleportAktion extends Aktion {
	private static final long serialVersionUID = 1L;
	
	private final Ort zielort;
	
	public TeleportAktion(Ort zielort) {
		this.zielort = zielort;
	}
	
	@Override
	public void ausfuehren() {
		spielwelt.setAktuellePositon(zielort);
	}
}
