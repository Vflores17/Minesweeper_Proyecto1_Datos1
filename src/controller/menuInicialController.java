/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.awt.AWTException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import modelo.cronometroJuego;

/**
 * FXML Controller class
 *
 * @author Personal
 */
public class menuInicialController implements Initializable {
    
    @FXML
    private Button btnIniciar;
    @FXML
    private ComboBox<String> boxNivel;
    private cronometroJuego crono;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        boxNivel.getItems().addAll("Dummy Level","Advanced Level");
        boxNivel.setValue("Advanced Level");
    }

    @FXML
    private void correrJuego(javafx.event.ActionEvent event) throws InterruptedException, AWTException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Juego.fxml"));

            Parent root = loader.load();

            JuegoController controlador = loader.getController();
            
            
            String nivel=boxNivel.getValue();
            
            controlador.setNivel(nivel);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            
            System.out.println(nivel);
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controlador.closeWindows());

            Stage myStage = (Stage) this.btnIniciar.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(menuInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
