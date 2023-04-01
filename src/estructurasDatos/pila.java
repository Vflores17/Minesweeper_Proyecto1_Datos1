
package estructurasDatos;

import modelo.casilla;

/**
 *
 * @author Vidal Flores Montero
 */
public class pila {

    private nodo head;
    private int size;

    public pila() {
        this.head = null;
        this.size = 0;
    }

    public void push(casilla data) {
        nodo newNode = new nodo();
        newNode.setInfo(data);
        newNode.setSiguiente(head);
        head = newNode;
        size++;
    }

    public void pop() {
        if (isEmpty()) {
            throw new RuntimeException("La pila está vacía.");
        }
        head = head.getSiguiente();
        size--;
    }

    public Object peek() {
        if (isEmpty()) {
            throw new RuntimeException("La pila está vacía.");
        }
        return head.getInfo();
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }
}
