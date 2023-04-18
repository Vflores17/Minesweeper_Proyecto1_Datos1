/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.casilla;
import modelo.tablero;
import estructurasDatos.listaSimple;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import static java.lang.Math.random;
import java.util.Random;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.event.ActionEvent;
import modelo.cronometroJuego;
import estructurasDatos.pila;

/**
 * FXML Controller class Metodo constructor del controlador de la vista grafica
 *
 * @author Vidal Flores Montero 2021579554
 */
public class JuegoController implements Initializable {

    private pila listaPistas = new pila();

    private menuInicialController menuController = new menuInicialController();

    boolean turnoCompuDummy = false;
    boolean turnoCompuAdvanced = false;

    private Button btnSalirJuego;
    int numFilas = 8;
    int numColumnas = 8;
    int numMinas = 15;

    int numConstante;

    int numMinasAlrededor;

    Button[][] botonesTablero;
    @FXML
    private AnchorPane tableroPane;

    tablero tableroBuscaminas;
    @FXML
    private Label labelTiempo;
    private int MinasEncontradas;

    int bombasEncontradas = 0;

    cronometroJuego crono = new cronometroJuego(this);
    @FXML
    private Label labelMinasEncontradas;
    @FXML
    private Label labelMostrarMinas;
    private String nivel;

    private ArduinoJava control;

    private boolean serialConectado;
    @FXML
    private Button btnArduino;

    private listaSimple listaSegura = new listaSimple();
    private listaSimple listaIncertidumbre = new listaSimple();

    private boolean click1 = true;
    private int contador = 0;
    @FXML
    private Button btnPistas;
    @FXML
    private Label labelPistas;

    private int turnosUsuario = 0;

    private int pistasUsadas = 0;

    /**
     * Initializes the controller class.
     *
     * @param url parametro con la ubicacion del FXML
     * @param rb parametro que carga los recursos especificos del controlador
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarControles();
        crearTableroBuscaminas();
        crono.iniciarCronometro();
        control = new ArduinoJava();
        serialConectado = false;
        btnArduino.setOnAction(event -> {
            if (!serialConectado) {
                control.conectar();
                serialConectado = true;
                btnArduino.setText("Desconectar");

            } else {
                try {
                    control.desconectar();
                    serialConectado = false;
                    btnArduino.setText("Conectar");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            if (serialConectado) {
                try {
                    control.procesarMensajes();

                } catch (IOException ex) {
                    Logger.getLogger(JuegoController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (AWTException ex) {
                    Logger.getLogger(JuegoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }, 0, 10, TimeUnit.MILLISECONDS); // adjust the delay and period to suit your needs

    }

    /**
     * Metodo que crea el tablero del juego y se setea los eventos de partida
     * perdida, partida ganada, y el abrir las casillas.
     */
    private void crearTableroBuscaminas() {
        tableroBuscaminas = new tablero(numFilas, numColumnas, numMinas);
        tableroBuscaminas.setEventoPartidaPerdida(new Consumer<listaSimple>() {
            @Override
            public void accept(listaSimple t) {
                for (casilla casillaConMinas : t.getCasillas()) {
                    botonesTablero[casillaConMinas.getPosFila()][casillaConMinas.getPosColumna()].setText("*");
                }
                tableroPane.setDisable(true);
                crono.detenerCronometro();
                if (serialConectado) {
                    control.enviarDatos("P");
                }
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Perdiste");
                alert.setContentText("Acabas de perder la partida.");
                alert.showAndWait();

            }
        });
        tableroBuscaminas.setEventoPartidaGanada(new Consumer<listaSimple>() {
            @Override
            public void accept(listaSimple t) {
                for (casilla casillaConMinas : t.getCasillas()) {
                    botonesTablero[casillaConMinas.getPosFila()][casillaConMinas.getPosColumna()].setText(":)");
                }
                tableroPane.setDisable(true);
                crono.detenerCronometro();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ganaste");
                alert.setContentText("¡Felicidades! Ganaste la partida.");
                alert.showAndWait();
            }
        });
        tableroBuscaminas.setEventoCasillaAbierta(new Consumer<casilla>() {
            @Override
            public void accept(casilla t) {
                botonesTablero[t.getPosFila()][t.getPosColumna()].setDisable(true);
                botonesTablero[t.getPosFila()][t.getPosColumna()].setText(t.getNumMinasAlrededor() == 0 ? ""
                        : t.getNumMinasAlrededor() + "");

            }
        });
        tableroBuscaminas.imprimirTablero();
        tableroBuscaminas.imprimirPistas();
    }

    /**
     * Metodo que controla cuando se cierra la ventana del juego y se cargue la
     * GUI de la ventana del menu inicial.
     *
     * @throws IOException controla posibles excepciones al cerrar la GUI
     */
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

    /**
     * Metodo que carga los controles del juego a cada botones creado
     */
    private void cargarControles() {
        int posXreferencia = 70;
        int posYreferencia = 60;
        int anchoControl = 30;
        int altoControl = 30;
        botonesTablero = new Button[numFilas][numColumnas];
        for (int i = 0; i < botonesTablero.length; i++) {
            for (int j = 0; j < botonesTablero[i].length; j++) {
                botonesTablero[i][j] = new Button();
                botonesTablero[i][j].setId(i + "," + j);
                botonesTablero[i][j].setBorder(null);
                //MouseEvent dobleClic = new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 2, true, true, true, true, true, true, true, true, true, true, null);
                //botonesTablero[i][j].fireEvent(dobleClic);
                if (i == 0 && j == 0) {
                    botonesTablero[i][j].setLayoutX(posXreferencia);
                    botonesTablero[i][j].setLayoutY(posYreferencia);
                    botonesTablero[i][j].setPrefWidth(anchoControl);
                    botonesTablero[i][j].setPrefHeight(altoControl);
                } else if (i == 0 && j != 0) {
                    botonesTablero[i][j].setLayoutX(botonesTablero[i][j - 1].getLayoutX()
                            + botonesTablero[i][j - 1].getPrefWidth());
                    botonesTablero[i][j].setLayoutY(posYreferencia);
                    botonesTablero[i][j].setPrefWidth(anchoControl);
                    botonesTablero[i][j].setPrefHeight(altoControl);
                } else {
                    botonesTablero[i][j].setLayoutX(botonesTablero[i - 1][j].getLayoutX());
                    botonesTablero[i][j].setLayoutY(botonesTablero[i - 1][j].getLayoutY()
                            + botonesTablero[i - 1][j].getPrefHeight());
                    botonesTablero[i][j].setPrefWidth(anchoControl);
                    botonesTablero[i][j].setPrefHeight(altoControl);
                }
                botonesTablero[i][j].setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        btnClick(e);
                    }
                    if (e.getButton() == MouseButton.SECONDARY) {
                        Button btn = (Button) e.getSource();
                        btn.setText(":0");
                        try {
                            control.enviarDatos("S");
                        } catch (Exception exception) {
                        }
                        MinasEncontradas++;
                        labelMostrarMinas.setText(MinasEncontradas + "");
                        return; // Salir del método
                    }

                });

                tableroPane.getChildren().add(botonesTablero[i][j]);

            }

        }
    }

    /**
     * Metodo para controlar cuando el usuario realiza un click dentro del juego
     *
     * @param e parametro de entrada, de cuando el usuario realiza un click
     */
    private void btnClick(javafx.scene.input.MouseEvent e) {

        Button btn = (Button) e.getSource();
        String[] coordenada = btn.getId().split(",");
        int posFila = Integer.parseInt(coordenada[0]);
        int posColumna = Integer.parseInt(coordenada[1]);
        tableroBuscaminas.seleccionarCasilla(posFila, posColumna);
        turnosUsuario++;
        System.out.println(turnosUsuario + "cantidad de turno de jugador");

        if (nivel.equals("Dummy Level")) {
            if (turnoCompuDummy) {
                btn.fire();
                tableroBuscaminas.seleccionarCasilla(posFila, posColumna);
            } else {
                try {
                    btn.fire();
                    dummyLevel(turnoCompuDummy = true);
                } catch (InterruptedException ex) {
                    Logger.getLogger(JuegoController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (AWTException ex) {
                    Logger.getLogger(JuegoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                turnoCompuDummy = false;
            }

            turnoCompuDummy = !turnoCompuDummy; // Cambiar el turno
        } else if (nivel.equals("Advanced Level")) {
            if (turnoCompuAdvanced) {
                tableroBuscaminas.seleccionarCasilla(posFila, posColumna);
            } else {
                try {
                    advancedLevel(turnoCompuAdvanced = true);
                } catch (InterruptedException ex) {
                    Logger.getLogger(JuegoController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (AWTException ex) {
                    Logger.getLogger(JuegoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                turnoCompuAdvanced = false;
            }

            turnoCompuAdvanced = !turnoCompuAdvanced; // Cambiar el turno
        }
    }

    /**
     * Metodo para setear el nivel de juego en el que se esta corriendo
     *
     * @param nivel String que indica la dificultad del juego
     */
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    /**
     * Metodo para poder cambiar el label de la GUI
     *
     * @param labelString String informacion por la cual se va setear el
     * elemento grafico
     */
    public void setLabel(String labelString) {
        labelTiempo.setText(labelString);
    }

    /**
     * Metodo que ejecuta la logica del Dummy Level
     *
     * @param turnoCompuDummy parametro booleano se ejecuta solo cuando es el
     * turno de la compu
     * @throws InterruptedException maneja si el hilo del juego es interrumpido
     * @throws AWTException maneja las posibles excepciones al utilizar clases o
     * GUI externas
     */
    public void dummyLevel(boolean turnoCompuDummy) throws InterruptedException, AWTException {
        if (turnoCompuDummy) {
            int fila, columna;
            do {
                fila = (int) (Math.random() * numFilas);
                columna = (int) (Math.random() * numColumnas);
            } while (botonesTablero[fila][columna].isDisabled());

            if (!botonesTablero[fila][columna].isDisabled()) {
                MouseEvent event1 = new MouseEvent(MouseEvent.MOUSE_CLICKED, 100,
                        5, 5, 5, MouseButton.PRIMARY, 1000, false, true, true, true,
                        true, true, true, true, true, true, null);
                botonesTablero[fila][columna].fireEvent(event1);
                botonesTablero[fila][columna].setText(":/");

                Robot robot = new Robot();
                robot.mouseMove((int) botonesTablero[fila][columna].localToScreen(botonesTablero[fila][columna].getBoundsInLocal()).getMinX() + 5,
                        (int) botonesTablero[fila][columna].localToScreen(botonesTablero[fila][columna].getBoundsInLocal()).getMinY() + 5);
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);

            }
        }

    }

    /**
     * Metodo que ejecuta la logica del Advanced Level
     *
     * @param turnoCompuAdvanced parametro booleano para cuando es el turno del
     * computador
     * @throws AWTException maneja las posibles excepciones al al recibir
     * informacion de algun dispositivo conectado
     * @throws InterruptedException maneja las posibles exceciones si se
     * interrumpe el hilo del juego.
     */
    public void advancedLevel(boolean turnoCompuAdvanced) throws AWTException, InterruptedException {
        if (turnoCompuAdvanced) {
            int tamañoInicial = 0;
            listaSimple listaGeneral = new listaSimple();
            for (int i = 0; i < botonesTablero.length; i++) {
                for (int j = 0; j < botonesTablero[i].length; j++) {
                    if (!botonesTablero[i][j].isDisabled()) {
                        casilla cas = tableroBuscaminas.getCasilla(i, j);
                        listaGeneral.agregarFinal(cas);
                    }
                }
            }
            System.out.println("Lista General /n");
            listaGeneral.imprimir();
            System.out.println("/n");

            while (!listaGeneral.estaVacia()) {
                casilla cas = (casilla) listaGeneral.getValorNodo(0);
                listaGeneral.eliminarPrimero();

                if (!cas.hayMina()) {
                    listaSegura.agregarFinal(cas);
                } else {
                    listaIncertidumbre.agregarFinal(cas);
                }
            }
            System.out.println("ListaGeneral: ");
            listaGeneral.imprimir();
            System.out.println("ListaSegura: ");
            listaSegura.imprimir();
            System.out.println("/n");
            System.out.println("ListaIncertidumbre: ");
            listaIncertidumbre.imprimir();

            if (!listaSegura.estaVacia()) {
                casilla seleccionar = (casilla) listaSegura.getValorNodo(0);
                int fila = seleccionar.getPosFila();
                int columna = seleccionar.getPosColumna();

                while (botonesTablero[fila][columna].isDisabled()) {
                    listaSegura.eliminarPrimero();

                    if (listaSegura.estaVacia()) {
                        return;
                    }

                    seleccionar = (casilla) listaSegura.getValorNodo(0);
                    fila = seleccionar.getPosFila();
                    columna = seleccionar.getPosColumna();
                }

                Robot robot = new Robot();
                robot.mouseMove((int) botonesTablero[fila][columna].localToScreen(botonesTablero[fila][columna].getBoundsInLocal()).getMinX() + 5,
                        (int) botonesTablero[fila][columna].localToScreen(botonesTablero[fila][columna].getBoundsInLocal()).getMinY() + 5);
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                listaSegura.eliminarPrimero();
            } else {
                casilla seleccionar = (casilla) listaIncertidumbre.getValorNodo(0);
                int fila = seleccionar.getPosFila();
                int columna = seleccionar.getPosColumna();
                if (!botonesTablero[fila][columna].isDisabled()) {
                    System.out.println("seleccionad de lista incertidumbre");
                    Robot robot = new Robot();
                    robot.mouseMove((int) botonesTablero[fila][columna].localToScreen(botonesTablero[fila][columna].getBoundsInLocal()).getMinX() + 5,
                            (int) botonesTablero[fila][columna].localToScreen(botonesTablero[fila][columna].getBoundsInLocal()).getMinY() + 5);
                    robot.mousePress(InputEvent.BUTTON1_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    listaIncertidumbre.eliminarPrimero();
                }
            }
        }

    }

    /**
     * Metodo para generar las pistas que se agregan a la pila de sugerencias
     */
    public void buscarPistas() {

        int i = (int) (Math.random() * 7);
        int j = (int) (Math.random() * 7);

        if (!botonesTablero[i][j].isDisabled()) {
            System.out.println("recorre todo el tablero hasta encontrar un boton sin abrir");
            casilla casPista = tableroBuscaminas.getCasilla(i, j);
            System.out.println("se imprime el valor de casPista " + casPista);
            if (!casPista.hayMina() && listaPistas.size() < 1) { // se agrega la pista solo si el contador es menor a 1
                System.out.println("se agrega la casilla a la listaPistas");
                listaPistas.push(casPista);
            }
        }

    }

    /**
     * Metodo accionado por un ActionEvent de un boton para poder descubrir una
     * sugerencia
     *
     * @param event parametro de la entrada de una accion como un click
     */
    @FXML
    private void btnPistas(ActionEvent event) {

        if (turnosUsuario < 10) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sin pistas");
            alert.setContentText("Aun no tienes las interacciones "
                    + "necesarias para ayudarte con pistas");
            alert.showAndWait();
        } else {
            buscarPistas();
            System.out.println("se imprime listapistas");
            listaPistas.imprimir();
            if (listaPistas.isEmpty()) {
                if (turnosUsuario >= 20) {
                    while (listaPistas.isEmpty()) {
                        buscarPistasOrdenado();
                    }
                    casilla casPista = (casilla) listaPistas.peek();
                    System.out.println(casPista);
                    if (!casPista.hayMina()) {
                        labelPistas.setText("Una posible casilla sin mina es: " + casPista.toString());
                        listaPistas.pop();
                        System.out.println("se imprimio listapistas");
                        pistasUsadas++; // se incrementa el contador de pistas usadas
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sin pistas");
                    alert.setContentText("No tienes pistas disponibles");
                    alert.showAndWait();
                }
            } else {
                casilla casPista = (casilla) listaPistas.peek();
                System.out.println(casPista);
                if (!casPista.hayMina()) {
                    labelPistas.setText("Una posible casilla sin mina es: " + casPista.toString());
                    listaPistas.pop();
                    System.out.println("se imprimio listapistas");
                    pistasUsadas++; // se incrementa el contador de pistas usadas
                }
            }
        }
    }

    /**
     * Metodo para recorrer toda la matriz de botones para ver encontrar
     * casillas disponibles y asi agregarla como sugerencia a la pila
     */
    public void buscarPistasOrdenado() {
        for (int i = 0; i < botonesTablero.length; i++) {
            for (int j = 0; j < botonesTablero[i].length; j++) {

                if (!botonesTablero[i][j].isDisabled()) {
                    System.out.println("recorre todo el tablero hasta encontrar un boton sin abrir");
                    casilla casPista = tableroBuscaminas.getCasilla(i, j);
                    System.out.println("se imprime el valor de casPista " + casPista);
                    if (!casPista.hayMina() && listaPistas.size() < 1) { // se agrega la pista solo si el contador es menor a 1
                        System.out.println("se agrega la casilla a la listaPistas");
                        listaPistas.push(casPista);
                    }
                }
            }
        }
    }
}
