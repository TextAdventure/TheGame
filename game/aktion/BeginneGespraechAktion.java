package game.aktion;

import npc.NPC;
import npc.Status;
import game.SpielWelt;

public class BeginneGespraechAktion extends Aktion {
	private static final long serialVersionUID = 1L;
	
	//Der NPC, mit dem das Gespraech gestartet wird
	private NPC npc;
	//Der Status, in dem das Gespräch beginnt
	private Status startStatus;
	
	public BeginneGespraechAktion(NPC npc, Status startStatus) {
		this.npc = npc;
		this.startStatus = startStatus;
	}
	
	@Override
	public void ausfuehren() {
		spielwelt.initGespraech(npc);
		npc.setImGespraech(true);
		npc.setStatus(startStatus);
		npc.promt();
	}

}
