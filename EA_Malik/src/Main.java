import model.*;
import util.Haversine;
import util.LadestationComparator;
import util.Strings;
import view.MyIO;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import static util.Strings.*;

/**
 * Die Hauptklasse "Main" enthält die Hauptmethode "main()", die beim Starten des Programms aufgerufen wird.
 * Sie ruft die Methode "zaubern()" auf, die eine Reihe von Schritten ausführt, um Ladestationen zu sortieren,
 * zu organisieren und zu visualisieren. Die Methode "zaubern()" ruft weitere Methoden auf, wie z.B. "begruessen()", "leseDaten()",
 * "sortiereDaten()", "loescheIrrelevanteDaten()" und "erzeugeGraph()", um die Ladestationen zu verarbeiten und zu visualisieren. Es gibt
 * auch eine Methode "findePfad()", die den kürzesten Weg zwischen zwei Knoten im Graphen findet, die die übergebenen Postleitzahlen besitzen.
 * Die Methode "getPostleitzahlFromUser()" fordert den Nutzer auf, die Postleitzahl des Start- und Zielknotens einzugeben und verwendet die Methode
 * "findePfad()" um den Weg zwischen diesen beiden Knoten zu finden. Der gefundene Pfad wird ausgegeben und die Ausführungszeit des Algorithmus wird angezeigt.
 *
 * @author Tonye Malik
 * @since 20.01.2023
 */

public class Main
{
    private static Graph graph;

    /**Dies ist die Hauptmethode eines Java-Programms. Es enthält eine einzige Zeile Code, die die Methode "zaubern()" aufruft.
     *
     * @param args Array von String
     */
    public static void main(String[] args)
    {
        zaubern();
    }

    /**
     * Diese Methode führt eine Reihe von Schritten aus, um Ladestationen zu sortieren, zu organisieren und zu visualisieren.
     */
    private static void zaubern()
    {
        begruessen();
        leseDaten();
        List<Ladestation> ladestationen = sortiereDaten(organisiereDaten());
        loescheIrrelevanteDaten(ladestationen, 25, 250);
        erzeugeGraph(ladestationen);
        findeWeg(ladestationen);
    }

    /**
     * Die Methode findet einen Weg zwischen zwei Knoten (angegeben durch die Postleitzahl des Start- und Zielknotens) im Graph.
     * @param ladestationen Liste von Ladestationen, die zum Erstellen des Graphen verwendet werden sollen.
     * Vorbedingung: Die Methode muss mit einer nicht-leeren Liste von Ladestation-Objekten aufgerufen werden.
     * Die Liste enthält alle Ladestationen, die für die Erzeugung des Graphen verwendet werden sollen.
     * Nachbedingung: Die Methode gibt die Informationen darüber auf der Konsole aus, ob es eine Verbindung zwischen zwei von Benutzer angegebenen Ladestationen gibt.
     * Die Methode gibt die Informationen darüber auf der Konsole aus, dass die Start-Ladestation erreichbar ist von Ziel-Ladestation.
     * Der Zustand des Graphen nach der Initialisierung sollte korrekt sein und entsprechend der Eingangsliste von Ladestationen konfiguriert sein.
     */
    private static void findeWeg(List<Ladestation> ladestationen)
    {
        graph = new Graph(ladestationen, 50, 400);
        long startTime = System.currentTimeMillis();
        // Get the starting and ending nodes from the user
        Ladestation startNode = getLadestationFromUser(EINGABE1);
        Ladestation endNode = getLadestationFromUser(EINGABE2);
        // Check if an edge is available between the two nodes
        if (graph.isWayAvailable(startNode, endNode))
        {
            MyIO.ausgeben(SUCHE);
        }
        else
        {
            MyIO.ausgeben(AUSGABE1);
        }
        long stopTime = System.currentTimeMillis();
        long executionTime = stopTime - startTime;
        assert endNode != null;
        assert startNode != null;
        MyIO.ausgeben(AUSGABE2 +startNode.getPostleitzahl()+ " erreichbar : " +endNode.getPostleitzahl()+ " ("+executionTime+ " Millisekunden"+")");
    }

    /**
     * Diese Methode fragt den Benutzer, eine Ladestation einzugeben und gibt diese vom Graphen zurück.
     *
     * @param message die Nachricht, die der nutzenden Person gezeigt wird.
     * @return die Ladestation, die von der nutzenden Person eingegeben wurde
     * Vorbedingung: Der graph muss initialisiert sein und enthält alle Ladestationen, die für die Suche verwendet werden sollen.
     * Nachbedingungen: Die Methode gibt eine Ladestation mit der übergebenen Postleitzahl auf der Konsole aus, wenn vorhanden.
     * Die Methode gibt eine entsprechende Nachricht auf der Konsole aus, wenn keine Ladestation mit der übergebenen Postleitzahl gefunden wird.
     * Rückgabewert der Methode sollte ein Ladestation-Objekt sein, das die übergebene Postleitzahl enthält, oder null, wenn keine Ladestation gefunden wurde.
     */
    private static Ladestation getLadestationFromUser(String message)
    {
        MyIO.ausgeben(message);
        int postleitzahl = MyIO.leseInt();
        for (Ladestation ladestation : graph.getLadestationen())
        {
            if (ladestation.getPostleitzahl() == postleitzahl)
            {
                return ladestation;
            }
        }
        MyIO.ausgeben(AUSGABE3);
        return null;
    }

    /**
     * Die Methode erstellt einen Graphen aus einer Liste von Ladestationen und gibt diesen auf der Konsole aus.
     *
     * @param ladestationen Liste von Ladestationen die genutzt werden, um den Graphen zu erstellen
     * Vorbedingung: Die Methode muss mit einer nicht-leeren Liste von Ladestation-Objekten aufgerufen werden.
     * Die Liste enthält alle Ladestationen, die für die Erzeugung des Graphen verwendet werden sollen.
     * Nachbedingungen: Die Methode gibt die Anzahl der Ladestationen und die Ausführungszeit in Millisekunden auf der Konsole aus.
     * Der Zustand des Graphen nach der Initialisierung sollte korrekt sein und entsprechend der Eingangsliste von Ladestationen konfiguriert sein.
     * Der Graph sollte in einer lesbaren Form auf der Konsole ausgegeben werden.
     */
    private static void erzeugeGraph(List<Ladestation> ladestationen)
    {
        long startTime = System.currentTimeMillis();
        Graph graph = new Graph(ladestationen, 50, 400);
        long stopTime = System.currentTimeMillis();
        long executionTime = stopTime - startTime;
        System.out.println(ANZAHL+ladestationen.size()+" ("+executionTime+" Millisekunden"+")");
        System.out.println(graph);
    }

    /**
     * Die Methode entfernt Ladestationen aus einer Liste, die sich innerhalb eines bestimmten Abstands (epsilon) und maximaler
     * Entfernung (maxEntfernung) befinden und gibt auch die Ausführungszeit des Algorithmus an.
     *
     * @param ladestationen Liste von Ladestationen aus der irrelevanten Daten entfernt werden sollen.
     * @param epsilon Abstand innerhalb dem Ladestationen als irrelevant betrachtet werden.
     * @param maxEntfernung Maximale Entfernung innerhalb der Ladestationen als irrelevant betrachtet werden.
     * Vorbedingungen: Die Methode muss mit einer nicht-leeren Liste von Ladestation-Objekten aufgerufen werden.
     * epsilon und maxEntfernung müssen positiv sein.
     * Nachbedingungen: Die Methode gibt alle Ladestationen, die sich innerhalb eines bestimmten Abstands (epsilon) von einer anderen Ladestation befinden, auf der Konsole aus und entfernt diese Ladestationen aus der Eingangsliste.
     * Der Zustand der Eingangsliste ladestationen sollte aktualisiert sein.
     */
    private static void loescheIrrelevanteDaten(List<Ladestation> ladestationen, int epsilon, int maxEntfernung)
    {
        MyIO.ausgeben(ARGUMENT+epsilon+" "+maxEntfernung);
        if (epsilon < 0 || maxEntfernung <0)
        {
            MyIO.ausgeben(ARGUMENT2);
            return;
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < ladestationen.size(); i++)
        {
            Ladestation reference = ladestationen.get(i);
            Iterator<Ladestation> it = ladestationen.iterator();
            while (it.hasNext())
            {
                Ladestation current = it.next();
                double distance = Haversine.haversine(reference.getBreitengrad(), reference.getLaengengrad(),
                        current.getBreitengrad(), current.getLaengengrad());
                if (distance < epsilon && !reference.equals(current))
                {
                    it.remove();
                }
            }
        }
        long stopTime = System.currentTimeMillis();
        long executionTime = stopTime - startTime;
        System.out.println(Strings.ANZAHL_NACH_LOESCHUNG +ladestationen.size() + " (" + executionTime + " Millisekunden"+")");

        for (Ladestation ladestation : ladestationen)
        {
            MyIO.ausgeben("Betreiber : " + ladestation.getBetreiber() + ITEM_TRENNZEICHEN + "Strasse : "
                    + ladestation.getStrasse() + ITEM_TRENNZEICHEN + "Hausnummer : " + ladestation.getHausnummer()
                    + ITEM_TRENNZEICHEN + "Postleitzahl : " + ladestation.getPostleitzahl() + ITEM_TRENNZEICHEN + "Ort : "
                    + ladestation.getOrt() + ITEM_TRENNZEICHEN + "Bundesland : " + ladestation.getBundesland()
                    + ITEM_TRENNZEICHEN + "Anschlussleistung : " + ladestation.getAnschlussleistung());
        }
        System.out.println();
    }

    /**
     * Die Methode sortiert eine Liste von Ladestationen mithilfe des LadestationComparator.
     *
     * @param ladestationen Liste von Ladestationen die sortiert werden sollen.
     * @return Die sortierte Liste von Ladestationen.
     * Vorbedingungen: Die Methode muss mit einer nicht-leeren Liste von Ladestation-Objekten aufgerufen werden.
     * Der LadestationComparator muss implementiert sein.
     * Nachbedingungen: Die Methode gibt eine sortierte Liste von Ladestation-Objekten zurück, die mithilfe des LadestationComparator sortiert wurde.
     * Der Zustand der Eingangsliste ladestationen sollte sortiert sein.
     * Die sortierte Liste sollte in der gleichen Reihenfolge sortiert sein wie das Vergleichsergebnis des LadestationComparator.
     */
    private static List<Ladestation> sortiereDaten(List<Ladestation> ladestationen)
    {
        ladestationen.sort(new LadestationComparator());
        return ladestationen;
    }

    /**
     * Die Methode organisiert die Daten aus einer CSV-Datei in eine Liste von Ladestationen-Objekten.
     * Ungueltige Geo-Positionen werden ausgelassen und die Anzahl gueltiger Ladestationen wird ausgegeben.
     * @return Eine Liste von Ladestationen-Objekten aus den gelesenen Daten.
     * Vorbedingungen: Der Dateiname, der in der Konstante "Strings.FILE_NAME" gespeichert ist, muss vorhanden und lesbar sein.
     * Der Dateityp der Datei muss Textdatei sein.
     * Die Struktur der Textdatei muss dem Ladestation-Format entsprechen.
     * Nachbedingungen: Die Methode gibt die Anzahl der gültigen Ladestationen und die Ausführungszeit in Millisekunden auf der Konsole aus.
     * Der Zustand des BufferedReader-Objekts nach dem Lesen sollte geschlossen sein.
     * Der Rückgabewert der Methode sollte eine Liste von Ladestation-Objekten sein, die alle gültigen Ladestationen enthält.
     * Jede Ladestation, die nicht den Anforderungen entspricht, sollte auf der Konsole ausgegeben werden.
     */
    private static List<Ladestation> organisiereDaten()
    {
        List<Ladestation> ladestationen = new ArrayList<>();
        String line;
        int index = 0;
        long startTime = System.currentTimeMillis();
        try (BufferedReader br = new BufferedReader(new FileReader(Strings.FILE_NAME)))
        {
            while ((line = br.readLine()) != null)
            {
                String[] values = line.split(Strings.TRENNZEICHEN);
                Ladestation ladestation = new Ladestation(values);
                if (ladestation.getBreitengrad() < 99 && ladestation.getLaengengrad() < 99)
                {
                    ladestationen.add(ladestation);
                    index++;
                }
                else
                {
                    MyIO.ausgeben(TRAGE_NICHT + " GeoPosition" + " breitenGrad : "+values[6] + " und laengenGrad : "+values[7]);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        long stopTime = System.currentTimeMillis();
        long executionTime = stopTime - startTime;
        MyIO.ausgeben(Strings.ANZAHL_VALIDER_LADESTATIONEN + index + " ("+executionTime + " Millisekunden" + ")");
        return ladestationen;
    }

    /**
     * Die Methode liest die Anzahl der Zeilen in einer CSV-Datei und gibt diese Anzahl sowie die Ausfuehrungszeit aus.
     * Vorbedingungen: Der Dateiname, der in der Konstante "Strings.FILE_NAME" gespeichert ist, muss vorhanden und lesbar sein.
     * Der Dateityp der Datei muss Textdatei sein.
     * Nachbedingungen: Die Methode gibt die Anzahl der gelesenen Zeilen und die Ausführungszeit in Millisekunden auf der Konsole aus.
     * Der Zustand des BufferedReader-Objekts nach dem Lesen sollte geschlossen sein.
     */
    private static void leseDaten()
    {
        int counter = 0;
        long startTime = System.currentTimeMillis();
        try (BufferedReader br = new BufferedReader(new FileReader(Strings.FILE_NAME)))
        {
            while (br.readLine() != null)
            {
                counter++;
            }
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        long stopTime = System.currentTimeMillis();
        long executionTime = stopTime - startTime;
        MyIO.ausgeben(Strings.ZEILEN_ANZAHL + counter + " (" + executionTime + " Millisekunden"+")");
    }

    /**
     * Die Methode gibt einen Begruessungstext auf der Konsole aus.
     */
    private static void begruessen()
    {
        MyIO.ausgeben(Strings.BEGRUESSUNGS_TEXT);
    }
}