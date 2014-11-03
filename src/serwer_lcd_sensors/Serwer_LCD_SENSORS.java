/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwer_lcd_sensors;

import com.pi4j.io.gpio.RaspiPin;
import java.awt.image.BufferedImage;

/**
 *
 * @author miquel
 */
public class Serwer_LCD_SENSORS {

    //--------------------------------------------------------------------    

	public static void main(String[] args) {
            AlarmSystem system = new AlarmSystem("Michal", "Deska", "Stanislawow");
            system.runAlarmSystem();
	}
    //--------------------------------------------------------------------    


}
