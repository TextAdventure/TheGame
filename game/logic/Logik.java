package game.logic;

import java.io.Serializable;

/**
 * Alle Klassen, die mit Logik zu tuen haben muessen diese Klasse implementieren.
 */
public abstract class Logik implements Serializable {
	// Die serielle Versionsnummer.
	private static final long serialVersionUID = 1L;

	public abstract boolean pruefe();
}
