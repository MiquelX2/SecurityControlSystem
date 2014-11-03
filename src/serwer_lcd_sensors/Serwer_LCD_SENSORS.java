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
            /*
            Image wyswietlacz = new Image(Image.Tryb.spi);
            wyswietlacz.showDesktopScreenCopy(true);
            wyswietlacz.funkcje();
            
            DevicesDatabase kk = new DevicesDatabase();
            kk.wyswietlacz = wyswietlacz;
            
            Device x = new Device(Device.DeviceType.MOTION,"Motion1R",RaspiPin.GPIO_00,"prawa");
            Device y = new Device(Device.DeviceType.MOTION,"Motion2M",RaspiPin.GPIO_02,"srodek");
            Device z = new Device(Device.DeviceType.MOTION,"Motion3L",RaspiPin.GPIO_03,"lewa");
            
            
            Device a = new Device(Device.DeviceType.NOISE,"Light",RaspiPin.GPIO_09,"prawaD");
            Device b = new Device(Device.DeviceType.LIGHT,"Noise",RaspiPin.GPIO_08,"lewaD");
            
            kk.addDevice(b);
            kk.addDevice(x);
            kk.addDevice(y);
            kk.addDevice(z);
            kk.addDevice(a);
            
            
            kk.runAllDevices();
           */
            AlarmSystem system = new AlarmSystem("Michal", "Deska", "Stanislawow");
            system.runAlarmSystem();
	}
    //--------------------------------------------------------------------    


}
