/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import controller.JuegoController;

/**
 * Clase para poder mostrar un cronometro en el juego
 *
 * @author Vidal Flores Montero 2021579554
 */
public class cronometroJuego {

    private int segundos = 0;
    private Timeline timeline;
    JuegoController cronometro;

    /**
     * Contructor de la clase
     *
     * @param cronometro controlador
     */
    public cronometroJuego(JuegoController cronometro) {
        this.cronometro = cronometro;
    }

    /**
     * Metodo para iniciar el cronometro el juego
     */
    public void iniciarCronometro() {
        if (timeline != null) {
            timeline.stop();
        }

        segundos = 0;

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            segundos++;
            actualizarLabel();
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Metodo para detener el cronometro del juego
     */
    public void detenerCronometro() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    /**
     * Metodo para actualizar el label de la GUI con el tiempo correspondiente
     */
    private void actualizarLabel() {
        int horas = segundos / 3600;
        int minutos = (segundos % 3600) / 60;
        int segundosRestantes = segundos % 60;
        String tiempo = String.format("%02d:%02d:%02d", horas, minutos, segundosRestantes);
        cronometro.setLabel(tiempo);
    }

}
