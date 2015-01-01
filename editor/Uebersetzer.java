package editor;

import game.Ausgang;
import game.Ort;
import game.SpielWelt;
import game.items.Gegenstand;

import java.io.FileInputStream;
import java.io.IOException;


/**
 * Stellt Methoden bereit, um ein WeltObjekt in JavaCode für einen WeltenGenerator zu übersetzen, oder gleich in eine
 * .dat-Datei.
 *  
 * TODO: toJava: Directory, Code anpassen, etc.
 * TODO: toSpielWelt: Ausgäng nich doppelt adden!
 *  
 * @author Felix
 *
 */
public class Uebersetzer {

	/**
	 * Übersetzt das übergeben WeltObjekt in Java-Code für einen WeltenGenerator.
	 * @param welt Die Welt, die übersetzt werden soll.
	 * @return Den fertigen Java Code.
	 * @throws IOException Falls das Lese der Code-Vorlage gescheitert ist.
	 */
	public static String toJavaCode(WeltObjekt welt) throws IOException {
		//Code einlesen
		FileInputStream fis = new FileInputStream("WeltenGenerator.java");
		byte[] buffer = new byte[255];
		String code = "";
		for(int length = fis.read(buffer); length > -1; length = fis.read(buffer)) 
			code += new String(buffer, 0, length);
		fis.close();
		
		
		//Gegenstände hinzufügen
		code = code.replaceFirst("#G#", "");
		
		
		//Orte hizufügen
		OrtErweitert[] orte = welt.getOrte();
		for(int i = 0; i < orte.length; i++) {
			String ortKonstruktor = "Ort o" + i + " = new Ort(\"" + orte[i].ort.getName() + "\", \"" + orte[i].ort.getBeschreibung() + "\");\n"; 
			code = code.replaceFirst("#O#", ortKonstruktor + "#O#");
		}
		code = code.replaceFirst("#O#", "\n");
		
		//Ausgänge hinzufügen
		AusgangErweitert[] ausgaenge = welt.getAusgaenge();
		for(int i = 0; i < ausgaenge.length; i++) {
			String ausgangCode = "";
			if(ausgaenge[i].von1nach2) {
				ausgangCode = "o" + indexOf(orte, ausgaenge[i].ort1) + ".addAusgang(Ausgang.";
				ausgangCode += Ausgang.richtungen[ausgaenge[i].bez_von1nach2].toUpperCase() + ", o";
				ausgangCode += indexOf(orte, ausgaenge[i].ort2) + ");\n";
			}
			if(ausgaenge[i].von2nach1) {
				ausgangCode += "o" + indexOf(orte, ausgaenge[i].ort2) + ".addAusgang(Ausgang.";
				ausgangCode += Ausgang.richtungen[ausgaenge[i].bez_von2nach1].toUpperCase() + ", o";
				ausgangCode += indexOf(orte, ausgaenge[i].ort1) + ");\n";
			}
			code = code.replaceFirst("#A#", ausgangCode + "#A#");
		}
		code = code.replaceFirst("#A#", "\n");
		
		
		
		return code;
	}
	
	
	/**
	 * Hilfsmethode. Gibt den Index i zurück, sodass orte[i].ort == o.
	 * @param orte Das Array der Ort, das durchsucht werden soll.
	 * @param o Der Ort, der gefunden werden soll.
	 * @return Der Index i.
	 */
	private static int indexOf(OrtErweitert[] orte, Ort o) {
		int index = 0;
		while(index < orte.length && orte[index].ort != o) index++;
		return index == orte.length ? -1 : index;
	}
	
	/**
	 * Übersetzt ein WeltObjekt in ein SpielWelt Okjekt, welches an anderer Stelle in eine .dat-Datei gespeichert werden kann.
	 * TODO: Ausgänge nur einmal adden
	 * @param welt Die Welt, die übersetzt werden soll.
	 * @return Das fertige SpielWelt-Objekt.
	 */
	public static SpielWelt toSpielWelt(WeltObjekt welt) {
		//1. Neue SpielWelt vorbereiten.
		SpielWelt spielWelt = new SpielWelt();
		
		//2. Gegenstände eintragen
		Gegenstand.GEGENSTAENDE = welt.getGegenstaende();
		
		//3. Ausgänge bauen
		AusgangErweitert[] ausgaenge = welt.getAusgaenge();
		for(AusgangErweitert ae : ausgaenge) {
			if(ae.von1nach2) {
				Ausgang a = new Ausgang(ae.bez_von1nach2, ae.ort2);
				if(ae.bez_von1nach2 == Ausgang.EIGENE)
					a.setRichtungsName(ae.eigeneBez_von1nach2);
				ae.ort1.addAusgang(a);
			}
			
			if(ae.von2nach1) {
				Ausgang a = new Ausgang(ae.bez_von2nach1, ae.ort1);
				if(ae.bez_von2nach1 == Ausgang.EIGENE)
					a.setRichtungsName(ae.eigeneBez_von2nach1);
				ae.ort2.addAusgang(a);
			}
		}
		
		//4. Startposition festlegen
		spielWelt.setAktuellePositon(welt.getStartOrt().ort);
		
		return spielWelt;		
	}
}
