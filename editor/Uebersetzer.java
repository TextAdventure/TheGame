package editor;

import game.Ausgang;
import game.Ort;

import java.io.FileInputStream;
import java.io.IOException;


/**
 * Übersetzt ein WeltObjekt in eine Java-Klasse, die bei der Ausführung die durch das WeltObejkt dargestellte Welt
 * erzeugt und speichert.
 * 
 * TODO:
 *  - Übersetzung direkt in .dat-Datei
 *  
 * @author Felix
 *
 */
public class Uebersetzer {

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
	
	
	private static int indexOf(OrtErweitert[] orte, Ort o) {
		int index = 0;
		while(index < orte.length && orte[index].ort != o) index++;
		return index == orte.length ? -1 : index;
	}
}
