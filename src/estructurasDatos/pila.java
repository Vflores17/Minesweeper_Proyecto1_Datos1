package estructurasDatos;

import modelo.casilla;

/**
 * Clase pila, maneja la logica de una lista en forma de pila
 *
 * @author Vidal Flores Montero 2021579554
 */
public class pila {

    private nodo head;
    private int size;

    /**
     * Constructor de la clase
     */
    public pila() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Metodo para agregar nodos a la pila
     *
     * @param data informacion que se almacenar en el nodo de la pila
     */
    public void push(casilla data) {
        nodo newNode = new nodo();
        newNode.setInfo(data);
        newNode.setSiguiente(head);
        head = newNode;
        size++;
    }

    /**
     * Metodo para eliminar el ultimo elemento de la pila
     */
    public void pop() {
        if (isEmpty()) {
            throw new RuntimeException("La pila está vacía.");
        }
        head = head.getSiguiente();
        size--;
    }

    /**
     * Metodo para obtener el valor del ultimo elemento agregado a la pila
     *
     * @return la infromacion almacenada en el nodo
     */
    public Object peek() {
        if (isEmpty()) {
            throw new RuntimeException("La pila está vacía.");
        }
        return head.getInfo();
    }

    /**
     * Metodo para saber si la lista esta vacia o no
     *
     * @return valor booleano indican el estado de la lista
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Metodo para saber el tamaño de la pila
     *
     * @return valor integer del tamaño de la pila
     */
    public int size() {
        return size;
    }

    /**
     * Metodo para poder imprimir la pila de manera mas visual
     */
    public void imprimir() {
        if (!isEmpty()) {
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
     * Metodo para eliminar la pila, sin importar la cantidad de elementos que
     * tenga
     */
    public void eliminarPila() {
        head = null;
    }
}
