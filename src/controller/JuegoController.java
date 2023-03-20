/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import com.sun.javafx.stage.PopupWindowHelper;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.casilla;
import modelo.tablero;

/**
 * FXML Controller class
 *
 * @author Personal
 */
public class JuegoController implements Initializable {

    private Button btnSalirJuego;
    int numFilas=8;
    int numColumnas=8;
    int numMinas=8;
    
    Button[][] botonesTablero;
    @FXML
    private AnchorPane tableroPane;
    
    tablero tableroBuscaminas;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarControles();
        crearTableroBuscaminas();
    }    
    
    private void crearTableroBuscaminas(){
        tableroBuscaminas = new tablero(numFilas, numColumnas, numMinas);
        tableroBuscaminas.setEventoPartidaPerdida(new Consumer<List<casilla>>(){
            @Override
            public void accept (List<casilla> t) {
                for (casilla casillaConMinas:t){
                    botonesTablero[casillaConMinas.getPosFila()][casillaConMinas.getPosColumna()].setText("*");
                }
            }
        });    
        tableroBuscaminas.imprimirTablero();
    }

    void closeWindows() {
       try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/menuInicial.fxml"));
            
            Parent root = loader.load();
            
            menuInicialController controlador = loader.getController();
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            stage.setScene(scene);
            stage.show();
            
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(menuInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void cargarControles(){
        int posXreferencia =70;
        int posYreferencia =60;
        int anchoControl =30;
        int altoControl =30;
        botonesTablero =new Button[numFilas][numColumnas];
        for (int i = 0; i < botonesTablero.length; i++) {
            for (int j = 0; j < botonesTablero[i].length; j++) {
                botonesTablero[i][j]=new Button();
                botonesTablero[i][j].setId(i+","+j);
                botonesTablero[i][j].setBorder(null);
                if (i==0 && j==0){
                    botonesTablero[i][j].setLayoutX(posXreferencia);
                    botonesTablero[i][j].setLayoutY(posYreferencia);
                    botonesTablero[i][j].setPrefWidth(anchoControl);
                    botonesTablero[i][j].setPrefHeight(altoControl);
                }else if (i==0 && j!=0){
                    botonesTablero[i][j].setLayoutX(botonesTablero[i][j-1].getLayoutX()
                            +botonesTablero[i][j-1].getPrefWidth());
                    botonesTablero[i][j].setLayoutY(posYreferencia);
                    botonesTablero[i][j].setPrefWidth(anchoControl);
                    botonesTablero[i][j].setPrefHeight(altoControl);
                }else{
                    botonesTablero[i][j].setLayoutX(botonesTablero[i-1][j].getLayoutX());
                    botonesTablero[i][j].setLayoutY(botonesTablero[i-1][j].getLayoutY()
                            +botonesTablero[i-1][j].getPrefHeight());
                    botonesTablero[i][j].setPrefWidth(anchoControl);
                    botonesTablero[i][j].setPrefHeight(altoControl);
                }
                botonesTablero[i][j].setOnMouseClicked(e -> {
                    btnClick(e);
                });

            tableroPane.getChildren().add(botonesTablero[i][j]);
                
            }
            
        }
    }
    private void btnClick(javafx.scene.input.MouseEvent e) {
        Button btn = (Button) e.getSource();
        String[] coordenada = btn.getId().split(",");
        int posFila = Integer.parseInt(coordenada[0]);
        int posColumna = Integer.parseInt(coordenada[1]);
        btn.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setContentText(posFila + "," + posColumna);
            alert.showAndWait();
            tableroBuscaminas.seleccionarCasilla(posFila, posColumna);
        });

    }
    
} 

