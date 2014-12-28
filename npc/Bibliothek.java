package npc;

/**
 * 
 * @author Felix
 * Statische Klasse, nicht Instanziierbar.
 * Enthält Standard-Bibliotheken mit Sätze, die für NPCs zur Unterhaltung dienen können.
 */

public final class Bibliothek {
	private Bibliothek() { // nicht instanziieren
	}
	
	public static String randomSatz(String[] array) {
		return array[(int)(Math.random()*array.length)];
	}
	
	public static final String[] BEGRUESSUNGEN = new String[]{
		"Hallo, wie geht es dir?",
	    "Guten Tag, möchtest du mich etwas fragen?",
	    "Ah, Hallo, geht es dir gut?",
	    "Ah, Hallo, wie es scheint geht es dir gut.",
	    "Was kann ich für dich tun?",
	    "Heute ist ein guter Tag, findest du nicht auch?",
	    "Wie geht es dir?",
	    "Habst du Neuigkeiten?"
	};
	public static final String[] VERABSCHIEDUNGEN = new String[]{
		"Auf Wiedersehen!",
	    "Bis bald!",
	    "Hoffentlich sehen wir uns noch einmal.",
	    "Ich würde mich freuen, wenn wir uns irgendwann nochmal sprechen würden.",
	    "Vielleicht sehen wir uns noch einmal, bis dann!",
	    "Ich hoffe, dass wir uns wiedersehen.",
	    "Hoffentlich begegen wir uns noch einmal.",
	    "Wir werden uns bestimmt noch einmal begegnen."
	};
	
	
	public static final String[] NICHT_VERSTANDEN = new String[]{
		"Was meint ihr damit?",
		"Was soll das heißen?",
		"Damit kann ich nichts anfangen.",
		"Ich verstehe euch nicht."
	};
	
	public static final String[] NAME_SAGEN = new String[]{		//$ ist Platzhalten für den Namen
		"Mein Name ist $.",
		"Ich heiße $. Und Ihr seid?",
		"$ nennt man mich."
	};
	
	public static final String[] BERUF_SAGEN = new String[]{	//$ ist Platzhalter für Beruf
		"Ich bin $ von Beruf.",
		"Ich arbeite als $.",
		"Mein Geld verdiene ich als $."
	};
	
	
	public static final String[] NCIHTS_ZU_BERICHTEN = new String[]{
		"Hier ist zur Zeit nichts los.",
		"Hier passiert nie etwas interessantes.",
		"Es gibt nichts zu berichten.",
		"Ich habe nichts neues gehört.",
		"Ich weiß von keinen Neuigkeiten."
	};
	
	//Sätze, die Händler zum Verkaufen nutzen können
	public static final String[] HAENDLER_VERKAUF = new String[]{
		"Ich habe einiges anzubieten. Seht her!",
		"Wollt ihr etwas kaufen?",
		"Ich würde gerne Geschäfte mit euch machen."
	};
	
	public static final String[] HAENDLER_EINKAUF = new String[]{
		"Was wollt ihr mir denn verkaufen?",
		"Habt ihr etwas zu verkaufen?",
		"Nur her mit der Ware!",
		"Ich kaufe so ziemlich alles. Was habt ihr denn da?"
	};
}
