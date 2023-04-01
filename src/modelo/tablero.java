/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import modelo.casilla;
import estructurasDatos.listaSimple;
import modelo.tablero;
import java.awt.BorderLayout;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Personal
 */
public class tablero {
    
        casilla[][] casillas;
    
        int numFilas;
        int numColumnas;
        int numMinas;
        int numCasillasAbiertas;
        boolean juegoTerminado;
        
        private Consumer<listaSimple> eventoPartidaPerdida;
        private Consumer<listaSimple> eventoPartidaGanada;
        
        private Consumer<casilla> eventoCasillaAbierta;

    public tablero(int numFilas, int numColumnas,int numMinas) {
        this.numFilas = numFilas;
        this.numMinas = numMinas;
        this.numColumnas = numColumnas;
        inicializarCasillas();
        
    }
    public void inicializarCasillas(){
        casillas=new casilla[this.numFilas][this.numColumnas];
        for(int i=0;i<casillas.length;i++){
            for (int j=0;j<casillas[i].length;j++){
                casillas[i][j]=new casilla(i,j);
            }
        }
        generarMinas();
    }
    
    private void generarMinas(){
        int minasGeneradas=0;
        while (minasGeneradas!=numMinas){
            int posTempFila=(int)(Math.random()*casillas.length);
            int posTempColumna=(int)(Math.random()*casillas[0].length);
            if (!casillas[posTempFila][posTempColumna].hayMina()){
                casillas[posTempFila][posTempColumna].setMina(true);
                minasGeneradas++;
            }
        }
        ActualizarMinasAlrededor();
        
    }
    
    public void imprimirTablero(){
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                System.out.print(casillas[i][j].hayMina()?"*":"0");;
            }
            System.out.println("");
        }
    }
    
    public void imprimirPistas(){
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                System.out.print(casillas[i][j].getNumMinasAlrededor());;
            }
            System.out.println("");
        }
    }
    private void ActualizarMinasAlrededor(){
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if(casillas[i][j].hayMina()){
                    listaSimple casillasAlrededor=obtenerCasillasAlrededor(i, j);
                    casillasAlrededor.forEach((c)->c.incrementarMinasAlrededor());
                }   
            }
            
        }
    }
    public listaSimple obtenerCasillasAlrededor(int posFila,int posColumna){
            listaSimple listaCasillas=new listaSimple();
            for (int i = 0; i < 8; i++) {
                int tempPosFila=posFila;
                int tempPosColumna=posColumna;
                switch (i) {
                    case 0: tempPosFila--;break; //arriba
                    case 1: tempPosFila--;tempPosColumna++;break; //arriba derecha
                    case 2: tempPosColumna++;break; //derecha
                    case 3: tempPosColumna++;tempPosFila++;break; //derecha abajo
                    case 4: tempPosFila++;break; //abajo
                    case 5: tempPosFila++;tempPosColumna--;break; //abajo izquierda
                    case 6: tempPosColumna--;break; //izquierda
                    case 7: tempPosFila--;tempPosColumna--;break; //izquierda arriba
                }
                
                if(tempPosFila>=0 && tempPosFila<this.casillas.length
                        && tempPosColumna>=0 && tempPosColumna<this.casillas[0].length){
                    listaCasillas.agregarFinal(this.casillas[tempPosFila][tempPosColumna]);
                }
        }
        return listaCasillas;    
    }

    listaSimple obtenerCasillasConMinas() {
        listaSimple casillasConMinas = new listaSimple();
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].hayMina()) {
                    casillasConMinas.agregarFinal(casillas[i][j]);
                }
            }

        }
        return casillasConMinas;
    }
    
    public void seleccionarCasilla(int posFila, int posColumna) {
        eventoCasillaAbierta.accept(this.casillas[posFila][posColumna]);
        if (this.casillas[posFila][posColumna].hayMina()) {
            
            eventoPartidaPerdida.accept(obtenerCasillasConMinas());
        } else if (this.casillas[posFila][posColumna].getNumMinasAlrededor() == 0) {
            marcarCasillaAbierta(posFila, posColumna);
            listaSimple casillasAlrededor = obtenerCasillasAlrededor(posFila, posColumna);
            for (casilla casilla : casillasAlrededor.getCasillas()) {
                if (!casilla.isAbierta()) {
                    seleccionarCasilla(casilla.getPosFila(), casilla.getPosColumna());
                }
            }
        }else{
            marcarCasillaAbierta(posFila, posColumna);
        }
        if(partidaGanada()){
            eventoPartidaGanada.accept(obtenerCasillasConMinas());
        }
    }
    
    void marcarCasillaAbierta(int posFila,int posColumna){
        if(!this.casillas[posFila][posColumna].isAbierta()){
            numCasillasAbiertas++;
            this.casillas[posFila][posColumna].setAbierta(true);
        }
    }
    
    boolean partidaGanada(){
        return numCasillasAbiertas>=numFilas*numColumnas-numMinas;
    }

    public void setEventoPartidaPerdida(Consumer<listaSimple> eventoPartidaPerdida) {
        this.eventoPartidaPerdida = eventoPartidaPerdida;
    }
    public void setEventoPartidaGanada(Consumer<listaSimple> eventoPartidaGanada) {
        this.eventoPartidaGanada = eventoPartidaGanada;
    }
    public boolean getEventoPartidaPerdida(Consumer<listaSimple> eventoPartidaPerdida) {
        return true;
    }
    public boolean getEventoPartidaGanada(Consumer<listaSimple> eventoPartidaGanada) {
        return true;
    }
    public void setEventoCasillaAbierta(Consumer<casilla> eventoCasillaAbierta) {
        this.eventoCasillaAbierta = eventoCasillaAbierta;
    }
    
    public int getNumeroMinasAlrededor(int posFila, int posColumna) {
        listaSimple minasAlrededor=obtenerCasillasAlrededor(posFila, posColumna);
        System.out.println(minasAlrededor.casillasConValoresDistintosDeCero());
        return 0;
    }
    public casilla getCasilla(int i, int j){
        return casillas[i][j];
    }
    
    /*public void minasEncontradas(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            Button("Clicked!");
        }
    }*/

}

