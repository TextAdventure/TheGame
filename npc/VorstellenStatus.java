package npc;

/**
 * In diesem Gespr‰chsstatus stellt sich der NPC dem Spieler vor, sodass dieser absofort den Namen des NPC weiﬂ.
 * @author Felix
 *
 */
public class VorstellenStatus extends Status {
	private static final long serialVersionUID = 1L;
	
	private NPC npc;
	private String name;
	
	public VorstellenStatus(NPC npc, String name, String text) {
		super(text);
		this.npc = npc;
		this.name = name;
	}
	
	public VorstellenStatus(NPC npc, String name, String text, Schluessel... schluessel) {
		super(text, schluessel);
		this.npc = npc;
		this.name = name;
	}
	
	
	@Override
	protected String getText() {
		npc.setName(name);
		return super.getText();
	}

}
