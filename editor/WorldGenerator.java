package editor;

import game.Ausgang;
import game.Gegenstand;
import game.Ort;
import game.SpielWelt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import util.NumerusGenus;


/**
 *  Automatisch Generierter Code. Erzeugt eine neue Spielwelt und speichert diese in NeueWelt.dat.
 */
public final class WorldGenerator {
  // Daufault Dateiname: NeueWelt.dat
  public static String dateiName = "NeueWelt.dat";
    
  //Alle Gegenstände der Welt erzeugen
  
  
  public static void main(String[] args){
      SpielWelt welt = new SpielWelt();
      
      
      // - - - alle Orte - - -
      Ort o0 = new Ort("Park", "Ein grüner Park mit vielen Blumen. Im Westen liegt eine Kirche, im Süden eine Einkaufspassage.");
Ort o1 = new Ort("Kirche", "Es ist eine kleine Kapelle, die aber scheinbar nicht mehr genutzt wird.");
Ort o2 = new Ort("Einkaufspassage", "Ein lebendiger Straßenzug mit vielen Menschen und Geschäften.");




      // - - - Alle Ausgänge - - -
      o0.addAusgang(Ausgang.UNDEFINIERT, o1);
o1.addAusgang(Ausgang.UNDEFINIERT, o0);
o0.addAusgang(Ausgang.UNDEFINIERT, o2);
o2.addAusgang(Ausgang.UNDEFINIERT, o0);


	welt.setAktuellePositon(o0);
       
      /*
       *  Speichern
       */
      try{
        FileOutputStream fos = new FileOutputStream(dateiName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(welt);
        oos.close();
        
        System.out.println("Eine neue Spielwelt wurde in " + dateiName + " erstellt.");
      }catch(IOException e){
        e.printStackTrace();
        System.err.println("ACHTUNG: Es konnte keine neue Spielwelt erstellt werden.");
      }
  } 
}