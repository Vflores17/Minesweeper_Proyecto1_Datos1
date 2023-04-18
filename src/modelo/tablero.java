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
 * Clase para manejar el tablero del juego
 *
 * @author Vidal Flores Montero 2021579554
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

    /**
     * Contructor de la clase
     *
     * @param numFilas valor integer de la cantidad de filas
     * @param numColumnas valor integer de la cantidad de columnas
     * @param numMinas valor integer de la cantidad de minas
     */
    public tablero(int numFilas, int numColumnas, int numMinas) {
        this.numFilas = numFilas;
        this.numMinas = numMinas;
        this.numColumnas = numColumnas;
        inicializarCasillas();

    }

    /**
     * Metodo para generar las casillas y las minas
     */
    public void inicializarCasillas() {
        casillas = new casilla[this.numFilas][this.numColumnas];
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                casillas[i][j] = new casilla(i, j);
            }
        }
        generarMinas();
    }

    /**
     * Metodo para generar las minas y colocarlas aleatoriamente en el tablero
     */
    private void generarMinas() {
        int minasGeneradas = 0;
        while (minasGeneradas != numMinas) {
            int posTempFila = (int) (Math.random() * casillas.length);
            int posTempColumna = (int) (Math.random() * casillas[0].length);
            if (!casillas[posTempFila][posTempColumna].hayMina()) {
                casillas[posTempFila][posTempColumna].setMina(true);
                minasGeneradas++;
            }
        }
        ActualizarMinasAlrededor();

    }

    /**
     * Metodo para imprimir el tablero del juego en consola
     */
    public void imprimirTablero() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                System.out.print(casillas[i][j].hayMina() ? "*" : "0");;
            }
            System.out.println("");
        }
    }

    /**
     * Metodo para imprimir las pistas de las casillas en consola
     */
    public void imprimirPistas() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                System.out.print(casillas[i][j].getNumMinasAlrededor());;
            }
            System.out.println("");
        }
    }

    /**
     * Metodo para actualizar la cantidad de minas alrededor que tiene la
     * casilla
     */
    private void ActualizarMinasAlrededor() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].hayMina()) {
                    listaSimple casillasAlrededor = obtenerCasillasAlrededor(i, j);
                    casillasAlrededor.forEach((c) -> c.incrementarMinasAlrededor());
                }
            }

        }
    }

    /**
     * Metodo para obtener las casillas alrededor de una casilla
     *
     * @param posFila valor integer de la posicion fila de la casilla
     * @param posColumna valor integer de la posicion columna de la casilla
     * @return una lista con las casillas que tiene alrededor
     */
    public listaSimple obtenerCasillasAlrededor(int posFila, int posColumna) {
        listaSimple listaCasillas = new listaSimple();
        for (int i = 0; i < 8; i++) {
            int tempPosFila = posFila;
            int tempPosColumna = posColumna;
            switch (i) {
                case 0:
                    tempPosFila--;
                    break; //arriba
                case 1:
                    tempPosFila--;
                    tempPosColumna++;
                    break; //arriba derecha
                case 2:
                    tempPosColumna++;
                    break; //derecha
                case 3:
                    tempPosColumna++;
                    tempPosFila++;
                    break; //derecha abajo
                case 4:
                    tempPosFila++;
                    break; //abajo
                case 5:
                    tempPosFila++;
                    tempPosColumna--;
                    break; //abajo izquierda
                case 6:
                    tempPosColumna--;
                    break; //izquierda
                case 7:
                    tempPosFila--;
                    tempPosColumna--;
                    break; //izquierda arriba
                }

            if (tempPosFila >= 0 && tempPosFila < this.casillas.length
                    && tempPosColumna >= 0 && tempPosColumna < this.casillas[0].length) {
                listaCasillas.agregarFinal(this.casillas[tempPosFila][tempPosColumna]);
            }
        }
        return listaCasillas;
    }

    /**
     * Metodo para obtener las casillas con minas
     *
     * @return una lista enlazada con las casillas que tienen "minas"
     */
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

    /**
     * Metodo para seleccionar la casilla y agrarla a un evento
     *
     * @param posFila valor integer de la posicion fila
     * @param posColumna valor integer de la posicion columna
     */
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
        } else {
            marcarCasillaAbierta(posFila, posColumna);
        }
        if (partidaGanada()) {
            eventoPartidaGanada.accept(obtenerCasillasConMinas());
        }
    }

    /**
     * Metodo para marcar la casillas seleccionada como abierta
     *
     * @param posFila valor integer de la posicion fila
     * @param posColumna valor integer de la posicion columna
     */
    public void marcarCasillaAbierta(int posFila, int posColumna) {
        if (!this.casillas[posFila][posColumna].isAbierta()) {
            numCasillasAbiertas++;
            this.casillas[posFila][posColumna].setAbierta(true);
        }
    }

    /**
     * Verifica si la partida ha sido ganada
     *
     * @return true si el número de casillas abiertas es igual o mayor al número
     * de casillas totales menos el número de minas
     */
    boolean partidaGanada() {
        return numCasillasAbiertas >= numFilas * numColumnas - numMinas;
    }

    /**
     * Establece el evento a realizar en caso de que la partida sea perdida
     *
     * @param eventoPartidaPerdida l Consumer que maneja el evento
     */
    public void setEventoPartidaPerdida(Consumer<listaSimple> eventoPartidaPerdida) {
        this.eventoPartidaPerdida = eventoPartidaPerdida;
    }

    /**
     * Establece el evento a realizar en caso de que la partida sea ganada
     *
     * @param eventoPartidaGanada el Consumer que maneja el evento
     */
    public void setEventoPartidaGanada(Consumer<listaSimple> eventoPartidaGanada) {
        this.eventoPartidaGanada = eventoPartidaGanada;
    }

    /**
     * Retorna el evento a realizar en caso de que la partida sea perdida
     *
     * @param eventoPartidaPerdida el Consumer que maneja el evento
     * @return
     */
    public boolean getEventoPartidaPerdida(Consumer<listaSimple> eventoPartidaPerdida) {
        return true;
    }

    /**
     * Retorna el evento a realizar en caso de que la partida sea ganada
     *
     * @param eventoPartidaGanada el Consumer que maneja el evento
     * @return
     */
    public boolean getEventoPartidaGanada(Consumer<listaSimple> eventoPartidaGanada) {
        return true;
    }

    /**
     * Establece el evento a realizar al abrir una casilla
     *
     * @param eventoCasillaAbierta el Consumer que maneja el evento
     */
    public void setEventoCasillaAbierta(Consumer<casilla> eventoCasillaAbierta) {
        this.eventoCasillaAbierta = eventoCasillaAbierta;
    }

    /**
     * Devuelve el número de minas alrededor de la casilla especificada por su
     * posición en el tablero
     *
     * @param posFila posición de la fila de la casilla
     * @param posColumna posición de la columna de la casilla
     * @return el número de minas alrededor de la casilla
     */
    public int getNumeroMinasAlrededor(int posFila, int posColumna) {
        listaSimple minasAlrededor = obtenerCasillasAlrededor(posFila, posColumna);
        System.out.println(minasAlrededor.casillasConValoresDistintosDeCero());
        return 0;
    }

    /**
     * Devuelve la casilla en la posición especificada del tablero
     *
     * @param i posición de la fila de la casilla
     * @param j posición de la columna de la casilla
     * @return la casilla en la posición especificada
     */
    public casilla getCasilla(int i, int j) {
        return casillas[i][j];
    }

}
