package view;

import java.util.Scanner;

/**
 * Diese Klasse enthaelt eine Methode, die String auf der Konsole ausgibt und eine ander, die
 * ganze Zahlen von der Eingabeaufforderung einliest.
 */
public class MyIO
{
    /**
     * Gibt einen uebergebenen String auf der Konsole aus.
     * @param ausgabe Der String, der auf der Konsole ausgegeben werden soll.
    */
    public static void ausgeben (String ausgabe)
    {
        System.out.println(ausgabe);
    }

    /**
     * Diese Methode liest eine ganze Zahl von der Eingabeaufforderung ein.
     * @return einen Integer-Wert
     */
    public static int leseInt()
    {
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }
}
