package model;

import util.Strings;
import util.MeineKonstante;

/**
 * Die Klasse "Ladestation" stellt eine Ladestation dar und enthält Eigenschaften wie den Betreiber, die Adresse,
 * die Postleitzahl, den Ort, das Bundesland, die geografischen Koordinaten und die Anschlussleistung.
 * Der Konstruktor der Klasse "Ladestation" nimmt ein Array von Strings entgegen, welches die Eigenschaften der Ladestation enthält.
 * Diese Werte werden dann in die entsprechenden Attribute der Ladestation gespeichert. Es gibt Getter-Methoden für jede Eigenschaft,
 * sowie eine toString Methode, die eine lesbare Darstellung der Ladestation als String zurückgibt. Es gibt auch eine exception
 * handling um das Programm nicht abstürzt, falls die Daten nicht richtig sind.
 */
public class Ladestation
{
    private String betreiber=null;
    private String strasse=null;
    private String hausnummer=null;
    private int postleitzahl=0;
    private String ort=null;
    private String bundesland=null;
    private double breitengrad=0;
    private double laengengrad=0;
    private float anschlussleistung=0;

    /**
     * Dies ist der Konstruktor von der Klasse Ladestation.
     * @param values Ein Array von Strings, das die Eigenschaften der Ladestation enthaelt.
    */
    public Ladestation(String[] values)
    {
        try
        {
            this.betreiber = values[MeineKonstante.BETREIBER_INDEX];
            this.strasse = values[MeineKonstante.STRASSE_INDEX];
            this.hausnummer = values[MeineKonstante.HAUSNUMMER_INDEX];
            this.postleitzahl = Integer.parseInt(values[MeineKonstante.POSTLEIZAHL_INDEX]);
            this.ort = values[MeineKonstante.ORT_INDEX];
            this.bundesland = values[MeineKonstante.BUNDESLAND_INDEX];
            this.breitengrad = Double.parseDouble(values[MeineKonstante.BREITENGRAD_INDEX]);
            this.laengengrad = Double.parseDouble(values[MeineKonstante.LAENGENGRAD_INDEX]);
            this.anschlussleistung = Float.parseFloat(values[MeineKonstante.ANSCHLUSSLEISTUNG_INDEX]);
        }
        catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e0)
        {
            System.out.println(e0.getMessage());
        }
    }

    /**
     * Gibt eine lesbare Darstellung der Ladestation als String zurueck.
     * @return Ein String, der die Eigenschaften der Ladestation enthaelt.
    */
    @Override
    public String toString()
    {
        return getBetreiber() + Strings.TRENNZEICHEN + getStrasse() + Strings.TRENNZEICHEN + getHausnummer()
                + Strings.TRENNZEICHEN +getPostleitzahl() + Strings.TRENNZEICHEN+ getOrt() + Strings.TRENNZEICHEN
                + getBundesland() + Strings.TRENNZEICHEN + getBreitengrad() + Strings.TRENNZEICHEN+ getLaengengrad()
                + Strings.TRENNZEICHEN+ getAnschlussleistung();
    }
    public String getBetreiber()
    {
        return betreiber;
    }
    public String getStrasse()
    {
        return strasse;
    }
    public String getHausnummer()
    {
        return hausnummer;
    }
    public int getPostleitzahl()
    {
        return postleitzahl;
    }

    public String getOrt()
    {
        return ort;
    }
    public String getBundesland()
    {
        return bundesland;
    }
    public double getBreitengrad()
    {
        return breitengrad;
    }
    public double getLaengengrad()
    {
        return laengengrad;
    }
    public float getAnschlussleistung()
    {
        return anschlussleistung;
    }
}
