/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructurasDatos;

/**
 *
 * @author Personal
 */
public class nodo {
    
     private Object info;
    private nodo siguiente;

    public nodo() {
        this.info = info;
        this.siguiente = null;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info){
        this.info=info;
    }
    
    public nodo getSiguiente(){
        return siguiente;
    }
    
    public void setSiguiente(nodo nodo){
        this.siguiente=nodo;
    }
    
}
