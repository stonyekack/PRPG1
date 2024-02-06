package util;

import model.Ladestation;
import java.util.Comparator;

/**
 * Die Klasse "LadestationComparator" implementiert das "Comparator"-Interface und definiert eine Methode "compare(Ladestation l1, Ladestation l2)"
 * um Ladestationen anhand ihrer Postleitzahl und Anschlussleistung zu vergleichen.
 * Die Methode "compare" vergleicht die Postleitzahl der beiden Ladestationen miteinander, wenn diese unterschiedlich sind.
 * wird der Unterschied zurückgegeben. Wenn die Postleitzahl gleich ist, wird die Anschlussleistung verglichen und der Unterschied zurückgegeben.
 * Diese Klasse wird verwendet, um die Knoten in der "PriorityQueue" nach ihrer Postleitzahl und Anschlussleistung zu sortieren.
 */
public class LadestationComparator implements Comparator<Ladestation>
{
    /**
     * Vergleicht zwei Ladestationen anhand ihrer Postleitzahl und der Anschlussleistung.
     *
     * @param l1 die erste Ladestation zum Vergleich
     * @param l2 die zweite Ladestation zum Vergleich
     * @return einen negativen Wert, wenn die Postleitzahl
     * von l1 kleiner ist als die von l2 oder die Anschlussleistung von l1
     * größer ist als die von l2, 0, wenn die Postleitzahlen gleich sind und einen
     * positiven Wert, wenn die Postleitzahl von l1 größer ist als die von l2 oder
     * die Anschlussleistung von l1 kleiner ist als die von l2
     */
    @Override
    public int compare(Ladestation l1, Ladestation l2)
    {
        int postleitzahlDiff = l1.getPostleitzahl() - l2.getPostleitzahl();
        if (postleitzahlDiff != 0)
        {
            return postleitzahlDiff;
        }
        else
        {
            return (int) (l2.getAnschlussleistung() - l1.getAnschlussleistung());
        }
    }
}
