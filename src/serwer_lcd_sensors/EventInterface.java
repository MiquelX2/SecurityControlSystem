/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwer_lcd_sensors;

/**
 *
 * @author miquel
 */
public interface EventInterface {
    public void eventOccured(String portGPIO,String name,String place,String value);
}
