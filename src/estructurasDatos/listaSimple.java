/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructurasDatos;

import java.util.function.Consumer;
import modelo.casilla;

/**
 *
 * @author Personal
 */
public class listaSimple {
    
    private nodo head;
    private int tamaño;

    public listaSimple() {
        this.head = null;
        this.tamaño = 0;
    }
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
    
    public boolean estaVacia(){
        return head==null;
    }
    public int tamaño(){
        return tamaño;
    }
    
    public void agregarFinal(Object info) {
        nodo nuevoNodo = new nodo();
        nuevoNodo.setInfo((casilla) info);
        if(estaVacia()){
            head=nuevoNodo;
        } else {
        nodo aux =head;
        while(aux.getSiguiente()!=null){
            aux = aux.getSiguiente();
        }
        aux.setSiguiente(nuevoNodo);
        }
        tamaño++;
    }
  
    public void forEach(Consumer<casilla> action) {
    nodo auxNode = head;
    while (auxNode != null) {
        action.accept((casilla) auxNode.getInfo());
        auxNode = auxNode.getSiguiente();
        }
    }
    
    public Object getValorNodo(int posicion){
        if(head==null||posicion<0){
            return null;
        }
        
        if (posicion==0){
        return head.getInfo();
    }else{
        nodo aux=head;
        for(int i=0;i < posicion ; i++){
            aux=aux.getSiguiente();
            if (aux==null){
                return "no se encontro el valor";
            }
        }
        return aux.getInfo();
        }
    }
    
    public void imprimir(){
    if(!estaVacia()){
            nodo aux=head;
            int contador=0;
            while(aux!=null){
                System.out.print(contador+".["+aux.getInfo()+"]"+"->");
                aux=aux.getSiguiente();
                contador++;
                }
        }
    
    }
    
    public int casillasConValoresDistintosDeCero() {
        int contador = 0;
        nodo aux = head;
        while (aux != null) {
            casilla c = (casilla) aux.getInfo();
            if (c.getNumMinasAlrededor()!= 0) {
                contador++;
            }
            aux = aux.getSiguiente();
        }
        return contador;
    }
    public void insertaPrimero(casilla info){
        nodo nuevoNodo=new nodo();
        nuevoNodo.setInfo(info);
        nuevoNodo.setSiguiente(head);
        head=nuevoNodo;
        tamaño++;
    }
    public void eliminarPrimero(){
        if(estaVacia()){
            System.out.println("La lista esta vacia");
        }
        if (head.getSiguiente() == null) {
            head = null;
        } else {
            head = head.getSiguiente();
        }
        tamaño--;
    }
    public nodo getHead(){
        return head;
    } 
    public int getTamano(){
        return tamaño;
    }
    
    public void agregarElemento(casilla data) {
        nodo nuevoNodo = new nodo();
        nuevoNodo.setInfo((casilla) data);
        if(estaVacia()){
            head=nuevoNodo;
        } else {
        nodo aux =head;
        while(aux.getSiguiente()!=null){
            aux = aux.getSiguiente();
        }
        aux.setSiguiente(nuevoNodo);
        }
        tamaño++;
    }

    public void eliminarNodo(casilla nodoBuscar) {
        if (head.getCasilla()== nodoBuscar) {
            head = head.getSiguiente();
        } else {
            nodo aux = head;
            while (aux.getSiguiente()!= null && aux.getSiguiente().getCasilla()!= nodoBuscar) {
                aux = aux.getSiguiente();
            }
            if(aux.getSiguiente()==null){
                System.out.println("El nodo a eliminar no se encontro");
            } else {
                nodo siguiente = aux.getSiguiente().getSiguiente();
                aux.setSiguiente(siguiente);
                tamaño--;
            }
        }
    }
   
}
        
