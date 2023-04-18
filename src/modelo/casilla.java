/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import modelo.casilla;

/**
 * Clase casilla que tiene toda la informacion de cada casilla del juego
 *
 * @author Vidal Flores Montero 2021579554
 */
public class casilla {

    private casilla casilla;
    private int posFila;
    private int posColumna;
    private boolean mina;
    private int numMinasAlrededor;
    private boolean abierta;

    /**
     * Constructor de la clase
     *
     * @param posFila parametro integer de la posicion fila de la casilla
     * @param posColumna parametro integer de la posicion columna de la casilla
     */
    public casilla(int posFila, int posColumna) {
        this.posFila = posFila;
        this.posColumna = posColumna;
    }

    /**
     * Metodo para obtener el valor de la casilla
     *
     * @return la variable casilla con su informacion
     */
    public casilla getCasilla() {
        return casilla;
    }

    /**
     * Metodo para obtener la posicion fila de la casilla
     *
     * @return valor integer de la posicion fila de la casilla
     */
    public int getPosFila() {
        return posFila;
    }

    /**
     * Metodo para setear el valor de la posicion fila de la casilla
     *
     * @param posFila valor integer de la posicion fila que se quiere colocar
     */
    public void setPosFila(int posFila) {
        this.posFila = posFila;
    }

    /**
     * Metodo para obtener la posicion columna de la casilla
     *
     * @return valor integer de la posicion columna de la casilla
     */
    public int getPosColumna() {
        return posColumna;
    }

    /**
     * Metodo para setear la posicion columna de la casilla
     *
     * @param posColumna valor integer de la posicion columna que se quiere
     * colocar
     */
    public void setPosColumna(int posColumna) {
        this.posColumna = posColumna;
    }

    /**
     * Metodo de valor booleano para saber si la casilla esta relacionada a una
     * "mina"
     *
     * @return valor booleano
     */
    public boolean hayMina() {
        return mina;
    }

    /**
     * Metodo para relacionar la casilla con una "mina"
     *
     * @param mina valor booleano
     */
    public void setMina(boolean mina) {
        this.mina = mina;
    }

    /**
     * Metodo para aumetar contador de la cantidad de minas que la casilla tiene
     * alrededor
     */
    public void incrementarMinasAlrededor() {
        this.numMinasAlrededor++;
    }

    /**
     * Metodo para obtener la cantidad de minas que la casilla tiene alrededor
     *
     * @return valor integer de la cantidad de minas alrededro
     */
    public int getNumMinasAlrededor() {
        return numMinasAlrededor;
    }

    /**
     * Metodo para colocar la cantidad de minas que la casilla tiene alrededor
     *
     * @param numMinasAlrededor valor integer de la cantidad de minas que tiene
     * cerca
     */
    public void setNumMinasAlrededor(int numMinasAlrededor) {
        this.numMinasAlrededor = numMinasAlrededor;
    }

    /**
     * Metodo booleano para saber si la casilla esta abierta o no
     *
     * @return valor booleano
     */
    boolean isAbierta() {
        return abierta;
    }

    /**
     * Metodo para poder colocar una casilla como abierta
     *
     * @param abierta valor booleano
     */
    void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }

    /**
     * Metodo @Override para poder imprimir el valor de la casilla de manera mas
     * visual
     *
     * @return String de la casilla
     */
    @Override
    public String toString() {
        return "[" + posFila + "," + posColumna + "]";
    }

}
