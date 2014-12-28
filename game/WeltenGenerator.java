package game;

import game.aktion.AusgabeAktion;
import game.aktion.SpielerRemoveGegenstandAktion;
import game.bedingung.SpielerHasGegenstandBedingung;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import util.NumerusGenus;

/**
 *  Diese Klasse erstellt ein neues Spiel und speichert es in einer Datei.
 */
public final class WeltenGenerator {
	// Der Name der Datei, in der der  Spielstand gespeichert wird.
	public static String dateiName = "spielstand.dat";
	  
	/**
	 *  Wenn dieses Programm ausgefuehrt wird, erzeugt es eine neue Welt in der angegebenen Datei.
	 */
	public static void main(String[] args){
		// Die Welt, die generiert wird.
	    SpielWelt welt = new SpielWelt();
	 	    
	    // alle Gegenstaende
	    Gegenstand PAPIER = new Gegenstand(new String[]{"Papier", "Blatt"}, NumerusGenus.NEUTRAL, "Ein Blatt Papier, auf dem nichts steht, es muss schon sehr alt sein, da es bereits vergilbt.");
	    Gegenstand FEUERSTEIN = new Gegenstand(new String[]{"Feuerstein"}, NumerusGenus.MASKULIN, "Ein Stein der die Macht des Feuers in sich hat.");
	    Gegenstand DUNKELSTEIN = new Gegenstand(new String[]{"Dunkelheitsstein"}, NumerusGenus.MASKULIN, "Ein Stein der die Macht der Finsternis in sich hat.");
	    Gegenstand SILBER = new Gegenstand(new String[]{"Silber"}, NumerusGenus.NEUTRAL, "Dieser Klumpen Silber glänzt, da er sehr stark poliert wurde.");
	    //VerwendbarerGegenstand GOLD = new VerwendbarerGegenstand(new String[]{"Gold"}, NumerusGenus.NEUTRAL, "Ein Klumpen Gold. Er fühlt sich kühl an und glänzt im Licht, er ist bestimmt wertvoll.", new Faehigkeit("Goldrausch", NumerusGenus.MASKULIN, "Mit der Macht des Goldes holst du zu einem allvernichtenden Schlag aus, der alle Gegner trifft und ihnen # Schaden zufügt!", Faehigkeit.PHYSISCH, "666%", "0"), VerwendbarerGegenstand.NURKAMPF);
	    VerwendbarerGegenstand GOLD = new VerwendbarerGegenstand(new String[]{"Gold"}, NumerusGenus.NEUTRAL, "Ein Klumpen Gold. Er fühlt sich kühl an und glänzt im Licht, er ist bestimmt wertvoll.", new Effekt(Effekt.ANGBONUS, "4;3"), VerwendbarerGegenstand.NURKAMPF);
	    VerwendbarerGegenstand HEILKRAUT = new VerwendbarerGegenstand(new String[]{"Heilkraut"}, NumerusGenus.NEUTRAL, "Ein grünes Kraut, das eine heilende Wirkung besitzt, wenn man es isst.", new Effekt(Effekt.HEILEN, "2r10;4r2"), VerwendbarerGegenstand.UEBERALL);
	    Ruestung STIEFEL = new Ruestung(new String[]{"Stiefel"}, NumerusGenus.PLURAL, "Ein Paar Stiefel, die aus braunem Leder bestehen und schon älter sein müssen, da es bereits brüchig wird.", 0, 0, 0, 2, 0, 0, 0, 0, Ruestung.SCHUHE);
	    Ruestung RUESTUNG = new Ruestung(new String[]{"Stahlbrustpanzer"}, NumerusGenus.MASKULIN, "Dieser legendäre Brustpanzer hat einst einem berühmten Krieger gehört, er galt schon seit Jahrhunderten als verschollen, doch du hast ihn wiedergefunden.", 179, 0, 0, 453, 0, 384, 0, 28, Ruestung.BRUSTPANZER);
	    Ruestung SAMURAIRUESTUNG = new Ruestung(new String[]{"Samurairüstung"}, NumerusGenus.FEMININ, "Ein Brustpanzer, der mit Papierlamellen verstärkt ist, wodurch er einen größeren Schutz bietet und eine bessere Beweglichkeit als ein normeler Brustpanzer.", 194, 0, 0, 569, 0, 327, 0, 73, Ruestung.BRUSTPANZER);
	    Ruestung STAND = new Ruestung(new String[]{"Helm der Standhaftigkeit"}, NumerusGenus.MASKULIN, "Dieser Helm steigert die Abwehr seines Trägers gegenüber Angriffen enorm.", 138, 0, 0, 326, 0, 24, 0, 24, Ruestung.HELM);
	    Waffe SCHILD = new Waffe(new String[]{"Schild"}, NumerusGenus.MASKULIN, "Ein Schild aus stabilem Holz, der mit Leder bezogen ist um ihn widerstandsfähiger zu machen.", 0, 0, 0, 72, 0, 14, 0, 0, Waffe.SCHILDHAND, Waffe.SCHILD);
	    Waffe DUNKELSCHILD = new Waffe(new String[]{"Schild der Dunkelheit"}, NumerusGenus.MASKULIN, "Ein pechschwarzer Schild, von dem eine dunkle Aura ausgeht.", 0, 0, 0, 242, 0, 638, 0, 0, Waffe.SCHILDHAND, Waffe.SCHILD);
	    Waffe SCHWERT = new Waffe(new String[]{"Schwert"}, NumerusGenus.NEUTRAL, "Ein starkes Schwert, welches unscheibar aussieht.", 0, 0, 302, 0, 94, 0, 78, 0, Waffe.SCHWERTHAND, Waffe.SCHWERT);
	    Waffe DFSCHWERT = new Waffe(new String[]{"Dunkelfeuer Schwert"}, NumerusGenus.NEUTRAL, "Dieses Schwert wurde mit der Macht der Dunkelhiet und des Feuers verstärkt.", 0, 0, 437, 0, 294, 0, 91, 0, Waffe.SCHILDHAND, Waffe.SCHWERT);
	    Waffe DOLCH = new Waffe(new String[]{"Dolch"}, NumerusGenus.MASKULIN, "Ein einfacher Dolch aus Stahl.", 0, 0, 20, 0, 0, 0, 12, 0, Waffe.SCHWERTHAND, Waffe.SCHWERT);
	    Waffe SILBERDOLCH = new Waffe(new String[]{"Silberdolch"}, NumerusGenus.MASKULIN, "Ein Dolch, der mit Silber überzogen wurde, dadurch kann er sogar Untote töten.", 0, 0, 162, 0, 84, 0, 92, 0, Waffe.SCHWERTHAND, Waffe.SCHWERT);
	    Waffe DRACHENSPALTER = new Waffe(new String[]{"Über-Drachentöter"}, NumerusGenus.MASKULIN, "Mit diesem Schwert kann man jeden Drachen im nu zu kleinen handlichen Stücken zerteilen.", 0, 0, 739, 0, 498, 0, 352, 0, Waffe.ZWEIHAENDIG, Waffe.SCHWERT);
	    Waffe MORGEN = new Waffe(new String[]{"Morgendämmerung"}, NumerusGenus.FEMININ, "Diese legendäre Klinge schimmert immer in einem Zwielicht, sodass alles um sie herum in ein dumpfes Licht gehüllt wird, ausserdem enthält sie eine gewaltige Macht.", 0, 0, 841, 0, 1362, 0, 294, 0, Waffe.SCHWERTHAND, Waffe.SCHWERT);
	   
	    Schluessel KEY = new Schluessel(new String[]{"Chipkarte"}, NumerusGenus.FEMININ, "Ein elektronischer Schlüssel.");
	    KEY.setBedingung(new SpielerHasGegenstandBedingung(welt, KEY, new SpielerRemoveGegenstandAktion(KEY, 1, "Die Tür öffnet sich und behält die Karte ein.")));
	    
	    // alle Kombinationen
	    new Kombination(1000, new Stapel(PAPIER, 12), new Stapel(RUESTUNG, 1), new Stapel(SAMURAIRUESTUNG, 1));
	    new Kombination(1500, new Stapel(SCHWERT, 1), new Stapel(FEUERSTEIN, 4), new Stapel(DUNKELSTEIN, 3), new Stapel(DFSCHWERT, 1));
	    new Kombination(750, new Stapel(DOLCH, 1), new Stapel(SILBER, 4), new Stapel(SILBERDOLCH, 1));
	    new Kombination(3000, new Stapel(SILBERDOLCH, 1), new Stapel(DFSCHWERT, 1), new Stapel(DRACHENSPALTER, 1));
	    new Kombination(2500, new Stapel(SCHILD, 1), new Stapel(SILBER, 1), new Stapel(DUNKELSTEIN, 5), new Stapel(DUNKELSCHILD, 2));
	    new Kombination(1350, new Stapel(FEUERSTEIN, 2), new Stapel(HEILKRAUT, 3), new Stapel(STAND, 1));
	    new Kombination(6666, new Stapel(DRACHENSPALTER, 1), new Stapel(DUNKELSCHILD, 1), new Stapel(MORGEN, 1));
	    
	    // alle Gegner
	    Gegner gegner = new Gegner("Schleim", NumerusGenus.MASKULIN, 5, 0, 8, 3, 0, 1, 2, 2, 1);
	    gegner.addFaehigkeit(new Faehigkeit("Angriff", NumerusGenus.MASKULIN, "§0 hüpft dich an und fügt dir # Schaden zu.", Faehigkeit.PHYSISCH, "0%", "0", new byte[0]));
	    Gegner drache = new Gegner("Drache", NumerusGenus.MASKULIN, 3892, 0, 536, 648, 120, 341, 278, 5, 20);
	    drache.addFaehigkeit(new Faehigkeit("Feuer spucken", NumerusGenus.NEUTRAL, "§0 spuckt lodernde Flammen, die dich umhüllen und dir # Schaden zufügen.", Faehigkeit.PHYSISCH, "10%", "0", new byte[0]));
	    
	    // alle Orte
	    Ort test = new Ort("Haus", "Ein kleines Haus, in dem sich nur ein paar Stiefel befinden und ein Blatt Papier, eine Tür führt nach draußen.");
	    Ort garten = new Ort("Garten", "Du betrachtest das Haus, von außen sieht es klein und heruntergekommen aus, es erinnert dich an etwas, dass du bereits gesehen hast.");
	    Ort strasse = new Ort("Straße", "Eine einsame Straße erstreckt sich vor dir, an ihrem Ende erkennst du etwas, du kannst aber nicht genau sagen, was es ist.");
	    Ort platz = new Ort("Platz", "Dieser Platz ist unspektakulär.");

	    //test.addAusgang(Ausgang.SUEDEN, garten);
	    test.addTuer(KEY, new Ausgang(Ausgang.SUEDEN, garten));
	    
	    test.addGegenstand(STIEFEL, 1);
	    test.addGegenstand(PAPIER, 16);
	    test.addGegenstand(RUESTUNG, 1);
	    test.addGegenstand(SCHWERT, 1);
	    test.addGegenstand(FEUERSTEIN, 7);
	    test.addGegenstand(DUNKELSTEIN, 8);
	    test.addGegenstand(DOLCH, 2);
	    test.addGegenstand(SILBER, 5);
	    test.addGegenstand(SCHILD, 1);
	    
	    test.addGegenstand(KEY, 1);
	    
	    test.addBehaelter(new Behaelter(Behaelter.KISTE, new Stapel(GOLD, 4), new Stapel(SILBER, 2), new Stapel(HEILKRAUT, 12)));
	    
	    garten.addAusgang(Ausgang.NORDEN, test);
	    garten.addAusgang(Ausgang.SUEDWESTEN, strasse);
	    garten.addGegner(gegner, 50.0);
	    
	    strasse.addAusgang(Ausgang.NORDOSTEN, garten);
	    strasse.addAusgang(Ausgang.SUEDWESTEN, platz);
	    
	    platz.addAusgang(Ausgang.NORDOSTEN, strasse);
	    platz.addGegner(drache, 100.0);
	    
	    welt.setAktuellePositon(test);
	    
	    System.out.println("Welt erstellt");
	    
	    /**
	     *  Das Programm schreibt die SpielWelt in die angegebene Datei.
	     */
	    try{
	    	FileOutputStream fos = new FileOutputStream(dateiName);
	    	// Der OutputStream kann nun Objekte schreiben.
	    	ObjectOutputStream oos = new ObjectOutputStream(fos);
	    	// Die SpielWelt wird in die Datei gespeichert.
	    	oos.writeObject(welt);
	    	// Der OutputStream wird geschlossen.
	    	oos.close();
	      
	    	// Meldung, das die SpielWelt erstellt wurde.
	    	System.out.println("Eine neue Spielwelt wurde in " + dateiName + " erstellt.");
	    }catch(IOException e){
	    	e.printStackTrace();
	    	System.err.println("ACHTUNG: Es konnte keine neue Spielwelt erstellt werden.");
	    }
	}	
}