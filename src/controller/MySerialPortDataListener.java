/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

/**
 * Metodo @Override del SerialPortListener que procesa los bytes recibidos y
 * retorna la informacion disponible
 *
 * @author Vidal Flores Montero 2021579554
 */
public class MySerialPortDataListener implements SerialPortDataListener {

    /**
     * Metodo para retornar la informacion escuchada en el puerto
     *
     * @return infromacion disponible del puerto serial
     */
    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
    }

    /**
     * Metodo @Override serialEvent que proceso los bytes recibidos por el
     * puerto serial
     *
     * @param event parametro del puerto serial
     */
    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
            return;
        }
        byte[] newData = event.getReceivedData();
        String serialData = new String(newData);
        System.out.println("Datos recibidos: " + serialData);

    }

}
