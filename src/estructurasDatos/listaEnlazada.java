/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructurasDatos;

/**
 *
 * @author Personal
 */
public class listaEnlazada {
    
    private nodo head;
    private int tamaño;

    public void listaEnlazada() {
        this.head = null;
        this.tamaño = 0;
    }
    
    public boolean estaVacia(){
        return head==null;
    }
    public int tamaño(){
        return tamaño;
    }
    
    public void agregarFinal(Object info) {
        nodo nuevoNodo = new nodo();
        nuevoNodo.setInfo(info);
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
    /*
    public Object obtenerValor(int index) {
        int contador=0;
        while(index!=contador){
            nodo temp = new nodo(info);
            
            
        }
        return null;
            
    }
    */
    public Object getValorNodo(int posicion){
    if (posicion==0){
        return head.getInfo();
    }else{
        nodo aux=head;
        for(int i=0;i < posicion ; i++){
            aux=aux.getSiguiente();
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
    
    public void insertarPorPosicion(int posicion,Object info) throws Exception{
        if(posicion<tamaño()){
            nodo aux=head;
            int i=0;
            while(i<posicion){
                aux=aux.getSiguiente();
                i++;}
            
                nodo nuevoNodo=new nodo();
                nuevoNodo.setInfo(info);
                nuevoNodo.setSiguiente(aux.getSiguiente());
                aux.setSiguiente(nuevoNodo);
           
            
        }else{
            throw new NullPointerException( "error" );
        }
    }
    
}
