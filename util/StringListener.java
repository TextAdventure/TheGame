package util;

/**
 * Der Listener empfaengt einen String von einem Event.
 */
public interface StringListener {
	// Diese Methode muss ueberschrieben werden.
	public abstract void actionPerformed(StringEvent evt);
}
