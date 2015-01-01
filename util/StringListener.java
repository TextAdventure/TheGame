package util;

/**
 * Kann von einer Klasse implementiert werden, sodass diese Strings empfangen kann und diese dann verarbeiten.
 * @author Marvin
 */
public interface StringListener {
	/**
	 * Wird immer aufgerufen, wenn ein String weitergegeben werden soll.
	 * @param evt Der String, der uebergeben werden soll.
	 */
	public abstract void actionPerformed(String string);
}
