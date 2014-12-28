package util;

import game.items.Inventar;

/**
 * Ein Event, das Invetar an den Listener uebergibt, der dieses dann ausgeben kann 
 */
public class InventoryEvent {
	// Das auszugebende Inventar
	private Inventar inventory;

	// Ein neues Event wird mit den auszugebenden Inventar erstellt.
	public InventoryEvent(Inventar inventory){
		this.inventory = inventory;
	}
	  
	// Gibt das Inventar zurueck.
	public Inventar getInventory(){
		return inventory;
	}
}
