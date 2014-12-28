package npc;

import game.logic.Logik;

public class BedingungStatus extends Status {
	private static final long serialVersionUID = 1L;
	
	private String textPos, textNeg;
	private Logik logik;
	
	/**
	 * Konstruktor
	 * @param textErfuellt Text, der angezeigt wird, falls die Bedingung erfüllt.
	 * @param textNichtErfuellt Text, der angezeigt wird, falls die Bedingung nicht erfüllt ist.
	 * @param logik Die Logik, die gerüft wird, sobald dieser Status erreicht wird.
	 */
	public BedingungStatus(String textErfuellt, String textNichtErfuellt, Logik logik) {
		this.textPos = textErfuellt;
		this.textNeg = textNichtErfuellt;
		this.logik = logik;
	}
	
	@Override
	protected String getText() { 
		if(logik.pruefe())
			return textPos;
		else
			return textNeg;
	}

}
