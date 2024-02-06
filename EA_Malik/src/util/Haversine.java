package util;

 /**
 * Die Klasse Haversine implementiert die Haversine-Formel, die verwendet wird, um die Entfernung zwischen zwei
  * geografischen Koordinaten auf der Erde zu berechnen. Die Methode haversine nimmt vier Parameter entgegen:
  * lat1, lon1, lat2, lon2, die die geografischen Koordinaten der beiden Punkte repräsentieren.
 */
public class Haversine
{
    /**
     * Berechnet die Entfernung zwischen zwei geografischen Koordinaten auf der Erde anhand der Haversine Formel.
     *
     * @param breitengrad1 Latitude des ersten Punktes.
     * @param laengengrad1 Longitude des ersten Punktes.
     * @param breitengrad2 Latitude des zweiten Punktes.
     * @param laengengrad2 Longitude des zweiten Punktes.
     * @return Entfernung zwischen den beiden Punkten in Kilometern.
     * Vorbedingung: breitengrad1, laengengrad1, breitengrad2 und laengengrad2 sind gültige double-Werte, die die Breiten-
     * und Längengrad-Koordinaten von zwei Punkten auf der Erde in Dezimalgrad-Format darstellen.
     * Nachbedingung: Die Methode sollte die korrekte Entfernung zwischen den beiden Punkten in Kilometern zurückgeben.
     */
    public static double haversine(double breitengrad1, double laengengrad1, double breitengrad2, double laengengrad2)
    {
        // Abstand zwischen Breitengrad und Laengengrad
        double dLat = Math.toRadians(breitengrad2 - breitengrad1);
        double dLon = Math.toRadians(laengengrad2 - laengengrad1);

        //Umrechnung zu Radianten
        breitengrad1 = Math.toRadians(breitengrad1);
        breitengrad2 = Math.toRadians(breitengrad2);

        // Anwendung der Formel
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(breitengrad1) *
                        Math.cos(breitengrad2);
        double rad = MeineKonstante.UMRECHNUNG;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
    }
}
