package util;

/**
 * Ein Listener, der Spielerveraenderungen wahrnimmt.
 */
public interface PlayerListener {
	// Diese Methode muss ueberschrieben werden.
	public abstract void playerChanged(PlayerEvent evt); 
}
