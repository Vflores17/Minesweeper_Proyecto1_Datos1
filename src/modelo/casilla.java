/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Personal
 */
public class casilla {
    
        private int posFila;
        private int posColumna;
        private boolean mina;
        private int numMinasAlrededor;
        
    public casilla(int posFila, int posColumna) {
        this.posFila = posFila;
        this.posColumna = posColumna;
    }
    

    public int getPosFila() {
        return posFila;
    }

    public void setPosFila(int posFila) {
        this.posFila = posFila;
    }

    public int getPosColumna() {
        return posColumna;
    }

    public void setPosColumna(int posColumna) {
        this.posColumna = posColumna;
    }

    public boolean hayMina() {
        return mina;
    }

    public void setMina(boolean mina) {
        this.mina = mina;
    }
    public void incrementarMinasAlrededor(){
        this.numMinasAlrededor++;
    }

    public int getNumMinasAlrededor() {
        return numMinasAlrededor;
    }

    public void setNumMinasAlrededor(int numMinasAlrededor) {
        this.numMinasAlrededor = numMinasAlrededor;
    }
    
    
        
        
            
    
    
    
}