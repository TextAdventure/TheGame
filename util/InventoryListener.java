package util;

/**
 * Empfaengt InventoryEvents, die dann verarbeitet werden koennen
 */
public interface InventoryListener {
	// Diese Methode muss ueberschrieben werden.
	public void inventoryUpdate(InventoryEvent evt);
}
