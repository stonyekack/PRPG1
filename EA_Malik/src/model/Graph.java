package model;

import util.Haversine;
import util.Strings;
import view.MyIO;
import java.util.Iterator;
import java.util.List;

/**
 * Diese Klasse erstellt einen Graphen fuer unsere Ladestation Collection
 * @author Tonye Malik
 * @since 20.01.2023
 */
public class Graph
{
    private final int[][] adjacencyMatrix;
    private final List<Ladestation> ladestationen;

    /**
     * Ein Konstruktor, der eine Liste von Ladestationen, eine Epsilonentfernung und eine maximale Entfernung erhaelt.
     * Es initialisiert die Adjazenzmatrix und entfernt Ladestationen, die weiter als maxEntfernung entfernt sind.
     * @param ladestationen Liste von Ladestationen.
     * @param epsilon Epsilon-Wert.
     * @param maxEntfernung Maximale Entfernung.
     */
    public Graph(List<Ladestation> ladestationen, int epsilon, int maxEntfernung)
    {
        MyIO.ausgeben(Strings.ARGUMENT +epsilon+" "+maxEntfernung);
        for (int k = 0; k < ladestationen.size(); k++)
        {
            Ladestation reference = ladestationen.get(k);
            Iterator<Ladestation> it = ladestationen.iterator();
            while (it.hasNext())
            {
                Ladestation current = it.next();
                double distance = Haversine.haversine(reference.getBreitengrad(), reference.getLaengengrad(),
                        current.getBreitengrad(), current.getLaengengrad());
                if (distance > maxEntfernung && !reference.equals(current))
                {
                    it.remove();
                }
            }
        }
        this.ladestationen = ladestationen;
        int n = ladestationen.size();
        adjacencyMatrix = new int[n][n];
        for (int i = 0; i < n; i++)
        {
            for (int j = i + 1; j < n; j++)
            {
                adjacencyMatrix[i][j] = 1;
                adjacencyMatrix[j][i] = 1;
            }
        }
    }

    /**
     * Gibt eine lesbare Darstellung des Graphen aus, indem sie die Ladestationen und ihre Verbindungen durchläuft.
     * @return ein String, der ausgibt, wie die Ladestationen im Graphen vorgestellt sein sollen
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ladestationen.size(); i++)
        {
            Ladestation a = ladestationen.get(i);
            sb.append(a.getPostleitzahl()).append(" ").append(a.getOrt()).append(" -> ");
            for (int j = 0; j < ladestationen.size(); j++)
            {
                if (adjacencyMatrix[i][j] == 1)
                {
                    Ladestation b = ladestationen.get(j);
                    sb.append(b.getPostleitzahl()).append(" ").append(b.getOrt()).append(", ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * This method checks if an edge is available between two nodes in the graph.
     *
     * @param startNode the starting node
     * @param endNode the ending node
     * @return true if an edge is available between the two nodes, false otherwise
     */
    public boolean isWayAvailable(Ladestation startNode, Ladestation endNode)
    {
        int startIndex = ladestationen.indexOf(startNode);
        int endIndex = ladestationen.indexOf(endNode);
        return adjacencyMatrix[startIndex][endIndex] == 1;
    }

    /**
     *
     * @return eine Liste der Ladestationen
     */
    public List<Ladestation> getLadestationen()
    {
        return ladestationen;
    }

}
