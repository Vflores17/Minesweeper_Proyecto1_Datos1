/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package controller;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelo.tablero;

/**
 *
 * @author Vidal Flores Montero
 */
public class Main extends Application{

    /**se declara el contructor del metodo main para poder hacer ejecutable el proyecto
     * @param args se envian los argumentos y comandos de linea
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    /** Método principal que genera el hilo necesario para poder ejecutar la GUI y todos sus elementos.
     *@param stage se envia como parametro el escenario de la GUI
     **/
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/menuInicial.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.show();
        tablero tablero = new tablero(5, 5, 5);
        tablero.imprimirTablero();
        System.out.println("-----");
        tablero.imprimirPistas();
    }
    
}
