/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructurasDatos;

import modelo.casilla;

/**
 * Clase nodo para utilizar la lista simple y la pila
 *
 * @author Vidal Flores Montero 2021579554
 */
public class nodo {

    private casilla info;
    private nodo siguiente;

    /**
     * Constructor de la clase nodo
     */
    public nodo() {
        this.info = info;
        this.siguiente = null;
    }

    /**
     * Metodo para obtener la informacio guardada en el nodo
     *
     * @return valor de la informacion almacenada
     */
    public Object getInfo() {
        return info;
    }

    /**
     * Metodo para setear el valor del nodo
     *
     * @param info informacion que se quiere guardar
     */
    public void setInfo(casilla info) {
        this.info = info;
    }

    /**
     * Metodo para obtener el nodo siguiente en la lista
     *
     * @return el nodo siguiente
     */
    public nodo getSiguiente() {
        return siguiente;
    }

    /**
     * Metodo para colocar el nodo siguiente
     *
     * @param nodo siguiente nodo en la lista
     */
    public void setSiguiente(nodo nodo) {
        this.siguiente = nodo;
    }

    /**
     * Metodo para obtener el valor de la casilla del objeto guardado en el nodo
     *
     * @return informacion de la casilla guardad.
     */
    public casilla getCasilla() {
        return info;
    }

}
