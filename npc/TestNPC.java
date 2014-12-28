package npc;

import game.SpielWelt;

import util.NumerusGenus;

/**
 * Ein Test-NPC, der die Verwendung dieses Packages veranschaulicht.
 * Der NPC ist ein Grampa Simpson.
 * Um das Programm zu beenden, das Kommando Stop eingeben.
 * @author Felix
 *
 */

public class TestNPC extends NPC {
	private static final long serialVersionUID = 1L;


	public TestNPC(SpielWelt welt) {
		super(welt, "Abe", NumerusGenus.MASKULIN);
		
		Status endeGespr = new Status("Das wird nie angezeigt", new Schluessel(new Status("Leb wohl!"), "tschüss", "leb wohl"));
		this.setEndeGespraech(endeGespr);
		
		//Grampa kann verschiedene espräche mit dir führen, die hier gebaut werden.
		//1. Man beleidigt ihn (Homer)
		Status unhoeflich = new Status("Was willst du, du verdammter Bengel?");
		Status wut = new Status("Was fällt dir ein?!? Wir sehen uns in der Hölle!");
		Status beruhigt = new Status("Na schon besser, was willst du?");
		
		//die Schlüssel dazu
		unhoeflich.addSchluessel(new Schluessel(wut, "ich rede mit dir", "ich erzittere", "halts maul alter"));
		unhoeflich.addSchluessel(new Schluessel(beruhigt, "entschuldigung, war nicht so gemeint", "ach du bist es", "verzeihung, nicht böse sein"));
		
		
		
		//2. Man ist frech zu ihm (Bart)
		Status belehrung = new Status("Hey du Jungspund, nicht so schnell. Zu meiner Zeit wäre das nicht gegangen. Damals haben haben wir uns Zitteraale um die Hüften gebunden...");
		Status geschichte = new Status("Also mal sehen... Es war ein verregneter Novembertag um Jahre 1837. Mein Bataillion rückte gerade an der Normandie aus, als ich plötzlich diesen Ring fand. Seit dem gehört er mir!");
		
		//schluessel
		belehrung.addSchluessel(new Schluessel(geschichte, "erzähl"));
		
		
		
		//3. man ist lieb zu ihm (Lisa)
		Status liebe = new Status("Was möchtest du von deinem Grampa?");
		Status geschenke = new Status("So, du kommst also nur, wenn du was brauchst? Verschwinde du Göre!");
		Status geschichte2 = new Status("Na da kann ich nicht nein sagen. Also, damals, als ich noch bei den Höllenfischen war, hatten die Menschen nichts zu essen und sind fast verhungert. Aber dann kam dein Grampa und den Deutschen das Essen geklaut und allen ging es wieder gut!");
		
		
		//schluessel
		liebe.addSchluessel(new Schluessel(geschenke, "geschenke"));
		liebe.addSchluessel(new Schluessel(geschichte2, "geschichte"));
		geschichte2.addSchluessel(new Schluessel(liebe, ""));
		
		
		
		Status startGespr = new Status("", new Schluessel(belehrung, "hey", "was geht"),
										new Schluessel(unhoeflich, "alter sack"),
										new Schluessel(liebe, "grampa", "hallo"));
		this.setStartGespraech(startGespr);
		
		setKeineAhnung(new String[]{"Keine Ahnung"});
		
		
	}
	
	@Override
	public Status beginneGespraech(Status ziel) {
		return ziel;
	}
	
	
	public static void main(String[] args) {
		/*NPC grampa = new ();
		
		Scanner scan = new Scanner(System.in);
		String antwort = scan.nextLine().toLowerCase();
		while(!antwort.equals("stop")) {			
			String grampasAntwort = grampa.ansprechen(antwort); 
			
			if(grampasAntwort == "")
				System.out.println("Niemand antwortet dir.");
			else 
				System.out.println(grampasAntwort);
			
			antwort = scan.nextLine().toLowerCase();
		}
		scan.close();*/
		
		
	}
}
