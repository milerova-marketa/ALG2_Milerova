/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

/**
 * Třída představující pokoj v objektu
 *
 * @author Marketa.Milerova
 */
public class Room {

    //počet jednolůžek
    private int nSingles;
    //počet dvoulůžek
    private int nDoubles;

    /**
     * Konstruktor
     *
     * @param nSingles počet jednolůžek
     * @param nDoubles počet dvoulůžek
     */
    public Room(int nSingles, int nDoubles) {
        if (nSingles < 0 || nDoubles < 0) {
            throw new IllegalArgumentException("Chybná inicializace pokoje");
        }
        if (nDoubles == 0 && nSingles == 0) {
            throw new IllegalArgumentException("Pokoj je prázdný");
        }
        this.nSingles = nSingles;
        this.nDoubles = nDoubles;
    }

    /**
     * Metoda vrací počet jednolůžek v pokoji
     *
     * @return počet jednolůžek
     */
    public int getnSingles() {
        return nSingles;
    }

    /**
     * Metoda vrací počet dvoulůžek v pokoji
     *
     * @return počet dvoulůžek
     */
    public int getnDoubles() {
        return nDoubles;
    }

    @Override
    public String toString() {
        return String.format("%2dx jednolůžko, %2dx dvoulůžko", nSingles, nDoubles);
    }

    /**
     * Metoda vracející celkovou kapacitu pokoje
     *
     * @return kapacita
     */
    public int getCapacity() {
        return nSingles + 2 * nDoubles;
    }

}
