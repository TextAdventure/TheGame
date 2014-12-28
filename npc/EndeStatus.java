package npc;

/**
 * Spezialstatus, der ein das aktuelle Gespräch automatisch beendet, sobald er erreicht wird.
 * @author Felix
 *
 */

public class EndeStatus extends Status {
	private static final long serialVersionUID = 1L;
	NPC npc;
	
	public EndeStatus(NPC npc, String text) {
		super(text);
		this.npc = npc;
	}
	
	public EndeStatus(NPC npc, String text, Schluessel... schluessel) {
		super(text, schluessel);
		this.npc = npc;
	}
	
	
	@Override
	protected String getText() {
		npc.setImGespraech(false);
		return super.getText();
	}
}
