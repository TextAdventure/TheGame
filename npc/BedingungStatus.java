package npc;

import game.bedingung.Bedingung;

public class BedingungStatus extends Status {
	private static final long serialVersionUID = 1L;
	
	private String textPos, textNeg;
	private Bedingung bed;
	
	/**
	 * Konstruktor
	 * @param textErfuellt Text, der angezeigt wird, falls die Bedingung erfüllt.
	 * @param textNichtErfuellt Text, der angezeigt wird, falls die Bedingung nicht erfüllt ist.
	 * @param bed Die Bedingung, die gerüft wird, sobald dieser Status erreicht wird.
	 */
	public BedingungStatus(String textErfuellt, String textNichtErfuellt, Bedingung bed) {
		this.textPos = textErfuellt;
		this.textNeg = textNichtErfuellt;
		this.bed = bed;
	}
	
	@Override
	protected String getText() { 
		if(bed.pruefen())
			return textPos;
		else
			return textNeg;
	}

}
