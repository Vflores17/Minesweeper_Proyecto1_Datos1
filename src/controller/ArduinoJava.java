/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.InputStream;
import java.io.OutputStream;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortIOException;
import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.lang.System.Logger;
import java.util.Arrays;

/**
 * Clase para conexion entre Arduino y Java
 * @author Vidal Flores Montero 2021579554
 *
 */
public class ArduinoJava extends JuegoController {

    private SerialPort serialPort;
    private InputStream inputStream;
    private OutputStream outputStream;
    private static final int BAUD_RATE = 9600; // Velocidad de transmisión en baudios
    private static final int TIMEOUT = 1000; // Tiempo de espera en milisegundos
    private static final String PORT_NAME = "COM3"; // Nombre del puerto serial

    /**
     *Constructor de la clase ArduinoJava
     */
    public ArduinoJava() {
    }

    /**
     * Establece la conexión con un dispositivo a través de un puerto serial
     * Utiliza la librería "JserialComm" para detectar y seleccionar el puerto
     * serial correcto.
     *
     */
    public void conectar() {
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort port : ports) {
            if (port.getSystemPortName().equals(PORT_NAME)) {
                serialPort = port;
                serialPort.setBaudRate(BAUD_RATE);
                serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, TIMEOUT, 0);
                System.out.println(serialPort + "");

                try {
                    serialPort.openPort();
                    inputStream = serialPort.getInputStream();
                    outputStream = serialPort.getOutputStream();
                    serialPort.addDataListener(new MySerialPortDataListener());
                    System.out.println("se conecto el control");
                } catch (Exception ex) {
                    ex.printStackTrace();

                }
            }
        }

    }

    /**
     * Metodo para enviar cualquier informacion al dispositivo conectado a
     * traves del puerto serial.
     *
     * @param data el parametro de entrada es lo enviado a traves del puerto
     */
    public void enviarDatos(String data) {
        try {
            System.out.println(Arrays.toString(data.getBytes()) + "" + "se envia esta info");
            outputStream.write(data.getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Metodo que se llama automaticamente cuando se recibe un evento del puerto
     * serial Lee los datos recibidos y se imprimen en consola.
     *
     * @param spe el evento que se ha recibido del puerto serial
     */
    public void serialEvent(SerialPortEvent spe) {
        if (spe.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
            try {
                int available = inputStream.available();
                byte[] buffer = new byte[available];
                inputStream.read(buffer);
                String data = new String(buffer);
                System.out.println("Datos recibidos: " + data);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    /**
     * Metodo para obtener eventos escuchados en el puerto seral
     * @return una excepcion que no se pudo soportar
     */
    public int getListeningEvents() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Metodo para poder desconectar el dispositivo del puerto serial de manera
     * correcta.
     *
     * @throws IOException en caso de un error aparece el IOException.
     */
    public void desconectar() throws IOException {
        try {
            inputStream.close();
            outputStream.close();
            serialPort.closePort();
            System.out.println("se cerro el puerto: " + serialPort);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para manejar la informacion que se recibe a traves del puerto
     * serial y segun sea la informacion realizar una u otra accion.
     *
     * @throws IOException maneja las excepiones en la parte de enviar/recibir
     * datos del puerto serial.
     * @throws AWTException maneja las excepciones relacionadas a las clases y
     * GUI utilizadas durante la ejecucion del metodo.
     */
    public void procesarMensajes() throws IOException, AWTException {
        byte[] buffer = new byte[1024];
        boolean botonPresionado = false;
        while (!botonPresionado) {
            int bytesRead = inputStream.read(buffer);
            //System.out.println(bytesRead + "" + "se imprime bytesRead /1");
            if (bytesRead > 0) {
                byte[] mensaje = Arrays.copyOf(buffer, bytesRead);
                //System.out.println(Arrays.toString(mensaje)+"/"+"se imprime mensaje");
                if (mensaje.length == 27) {
                    //System.out.println("tecla 27");
                    procesarMensaje(27);
                } else if (mensaje.length == 24) {
                    //System.out.println("tecla 24");
                    procesarMensaje(24);
                } else if (mensaje.length == 23) {
                    //System.out.println("tecla 23");
                    procesarMensaje(23);
                } else if (mensaje.length == 25) {
                    //System.out.println("tecla 25");
                    procesarMensaje(25);
                }
            }
        }
    }

    /**
     * Metodo para realizar llamar a un metodo segun el valor del datos recibido
     * por el puerto serial.
     *
     * @param mensaje valor del datos recibido del dispositivo
     * @throws AWTException maneja las excepciones de las clases y GUI
     * utilizadas
     */
    public void procesarMensaje(int mensaje) throws AWTException {
        switch (mensaje) {
            case 27:
                //tecla arriba
                enviarTecla(38);
                break;
            case 24:
                //tecla abajo
                enviarTecla(40);
                break;
            case 23:
                //tecla izquierda
                enviarTecla(37);
                break;
            case 25:
                //tecla derecha
                enviarTecla(39);
                break;
            default:
                throw new AssertionError();
        }
    }

    /**
     * Metodo para simular la accion de un usuario para poder acceder al teclado
     * desde el dispositivo conectado por el puerto serial.
     *
     * @param keyCode parametro con el keyCode de la accion que se espera
     * realizar
     * @throws AWTException maneja las excepciones de las clases y GUI
     * utilizadas
     */
    private void enviarTecla(int keyCode) throws AWTException {
        Robot robot = new Robot();
        System.out.println(keyCode);
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
    }
}
