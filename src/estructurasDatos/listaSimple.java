/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructurasDatos;

import java.util.function.Consumer;
import modelo.casilla;

/**
 * Clase lista Simple maneja una lista enlazada simple
 *
 * @author Vidal Flores Montero 2021579554
 */
public class listaSimple {

    private nodo head;
    private int tamaño;

    /**
     * Contructor de la clase
     */
    public listaSimple() {
        this.head = null;
        this.tamaño = 0;
    }

    /**
     * Metodo para poder obtener la casilla del boton seleccionado
     *
     * @return el valor del casilla del boton
     */
    public casilla[] getCasillas() {
        casilla[] casillas = new casilla[tamaño];
        nodo auxNode = head;
        int i = 0;
        while (auxNode != null) {
            casillas[i] = (casilla) auxNode.getInfo();
            auxNode = auxNode.getSiguiente();
            i++;
        }
        return casillas;
    }

    /**
     * Metodo para determinar la lista esta vacia o no
     *
     * @return valor booleano si la lista esta vacia o no
     */
    public boolean estaVacia() {
        return head == null;
    }

    /**
     * Metodo que retorna el tamaño de la lista
     *
     * @return valor integer del tamaño de la lista
     */
    public int tamaño() {
        return tamaño;
    }

    /**
     * Metodo para poder agregar un nodo al final de la lista
     *
     * @param info parametro que se quiere guardar en el nuevo nodo
     */
    public void agregarFinal(Object info) {
        nodo nuevoNodo = new nodo();
        nuevoNodo.setInfo((casilla) info);
        if (estaVacia()) {
            head = nuevoNodo;
        } else {
            nodo aux = head;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(nuevoNodo);
        }
        tamaño++;
    }

    /**
     * Aplica una accion proporcionada a cada elemento de la lista
     *
     * @param action la accion a realizar en cada elemento de la lista
     */
    public void forEach(Consumer<casilla> action) {
        nodo auxNode = head;
        while (auxNode != null) {
            action.accept((casilla) auxNode.getInfo());
            auxNode = auxNode.getSiguiente();
        }
    }

    /**
     * Metodo para obtener el valor de un nodo segun la posicionde del nodo
     *
     * @param posicion parametro integer de la posicion de la lista que se desea
     * obtener
     * @return la informacion almanecada en ese nodo
     */
    public Object getValorNodo(int posicion) {
        if (head == null || posicion < 0) {
            return null;
        }

        if (posicion == 0) {
            return head.getInfo();
        } else {
            nodo aux = head;
            for (int i = 0; i < posicion; i++) {
                aux = aux.getSiguiente();
                if (aux == null) {
                    return "no se encontro el valor";
                }
            }
            return aux.getInfo();
        }
    }

    /**
     * Metodo para poder imprimir la lista de forma amigable para el
     * desarrollador y usuario
     */
    public void imprimir() {
        if (!estaVacia()) {
            nodo aux = head;
            int contador = 0;
            while (aux != null) {
                System.out.print(contador + ".[" + aux.getInfo() + "]" + "->");
                aux = aux.getSiguiente();
                contador++;
            }
        }

    }

    /**
     * Metodo para obtener la cantidad de casillas tienen pistas distintas a
     * cero
     *
     * @return un contador de la cantidad de casillas que tiene al menos 1 mina
     * alrededor
     */
    public int casillasConValoresDistintosDeCero() {
        int contador = 0;
        nodo aux = head;
        while (aux != null) {
            casilla c = (casilla) aux.getInfo();
            if (c.getNumMinasAlrededor() != 0) {
                contador++;
            }
            aux = aux.getSiguiente();
        }
        return contador;
    }

    /**
     * Metodo para poder agregar de primero en la lista sin importar los
     * elementos que esta tenga
     *
     * @param info informacion que se quiere almacenar en el nodo
     */
    public void insertaPrimero(casilla info) {
        nodo nuevoNodo = new nodo();
        nuevoNodo.setInfo(info);
        nuevoNodo.setSiguiente(head);
        head = nuevoNodo;
        tamaño++;
    }

    /**
     * Metodo para eliminar el primer elemento de la lista
     */
    public void eliminarPrimero() {
        if (estaVacia()) {
            System.out.println("La lista esta vacia");
        }
        if (head.getSiguiente() == null) {
            head = null;
        } else {
            head = head.getSiguiente();
        }
        tamaño--;
    }

    /**
     * Metodo para obtener la cabeza de la lista
     *
     * @return el valor de la cabeza de la lista
     */
    public nodo getHead() {
        return head;
    }

    /**
     * Metodo para obtener el tamaño de la lista
     *
     * @return valor integer del tamaño de la lista
     */
    public int getTamano() {
        return tamaño;
    }

    /**
     * Metodo para agregar elementos a la lista pero la informacion a almacenar
     * es tipo casilla
     *
     * @param data valor de la casilla que se quiere almacenar en el nodo
     */
    public void agregarElemento(casilla data) {
        nodo nuevoNodo = new nodo();
        nuevoNodo.setInfo((casilla) data);
        if (estaVacia()) {
            head = nuevoNodo;
        } else {
            nodo aux = head;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(nuevoNodo);
        }
        tamaño++;
    }

    /**
     * Metodo para eliminar un nodo segun la informacion que se introduzca como
     * parametro
     *
     * @param nodoBuscar valor el cual se busca en la lista y se elimina de la
     * lista
     */
    public void eliminarNodo(casilla nodoBuscar) {
        if (head.getCasilla() == nodoBuscar) {
            head = head.getSiguiente();
        } else {
            nodo aux = head;
            while (aux.getSiguiente() != null && aux.getSiguiente().getCasilla() != nodoBuscar) {
                aux = aux.getSiguiente();
            }
            if (aux.getSiguiente() == null) {
                System.out.println("El nodo a eliminar no se encontro");
            } else {
                nodo siguiente = aux.getSiguiente().getSiguiente();
                aux.setSiguiente(siguiente);
                tamaño--;
            }
        }
    }

}
