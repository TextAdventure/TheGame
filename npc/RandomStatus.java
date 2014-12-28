package npc;

public class RandomStatus extends Status {
	private static final long serialVersionUID = 1L;

	private String[] texte;
	
	public RandomStatus(String... text) {
		this.texte = text;
	}
	
	public RandomStatus(String[] text, Schluessel... schluessel) {
		super(text[0], schluessel);
		this.texte = text;
	}
	
	
	@Override
	public String getText() {
		super.getText();		//fall das Gespräch hier terminiert wird
		return texte[(int)(texte.length*Math.random())];
	}
}
