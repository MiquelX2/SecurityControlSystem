/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwer_lcd_sensors;

//--------------------------------------------------------------------    
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
//--------------------------------------------------------------------    
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.Pin;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
//--------------------------------------------------------------------    
/**
 *
 * @author miquel
 */
public class Device extends Thread {
    
   public enum DeviceType{
        MOTION,NOISE,LIGHT,HUMIDITY,TEMPERATURE,NOT_DEFINED
    }
    //Klasa odpowiadajaca za poszczegolne sensory, przechowujaca ich stan, nazwe, status inicjalizacji
    //date ostatniego zdarzenia
    //przechowuje tez metody inicjalizacyjne,ustawiajace i pobierajace poszczegolne pola
    static int counter;
    DeviceType type;
    String name;
    boolean state;
    boolean initialized;
    Date latestActionDate;
    int portGpio;
    Pin port;
    String localization;
    public List<Action> Actions;
    EventInterface listener;
    //-------------------------------------------------------------------
    public void addListener(EventInterface toAdd){
        listener = toAdd;
    }
    //--------------------------------------------------------------------    
    public Device(){
        counter++;
        type = DeviceType.NOT_DEFINED;
        name = "";
        state = false;
        initialized = false;
        latestActionDate = new Date(0, 0, 0, 0, 0);
        portGpio = 0;
        localization = "";
        Actions = new ArrayList<Action>();
    }
    //--------------------------------------------------------------------    

    public Device(DeviceType _type,String _name,int _portGpio,String _localization){
        counter++;
        type = _type;
        name = _name;
        state = false;
        initialized = true;
        latestActionDate = new Date(0, 0, 0, 0, 0);
        portGpio = _portGpio;
        localization = _localization;
        Actions = new ArrayList<Action>();
    }    //--------------------------------------------------------------------    

    public Device(DeviceType _type,String _name,Pin _port,String _localization){
        counter++;
        type = _type;
        name = _name;
        state = false;
        initialized = true;
        latestActionDate = new Date(0, 0, 0, 0, 0);
        port = _port;
        localization = _localization;
        Actions = new ArrayList<Action>();
    }
    //--------------------------------------------------------------------    

    protected String getDeviceName(){return name;}
    //--------------------------------------------------------------------    

    protected boolean getDeviceState(){return state;}
    //--------------------------------------------------------------------    

    protected boolean isInitializedDevice(){ return initialized; } 
    //--------------------------------------------------------------------    

    protected void setDeviceName(String _name){ name = _name; }
    //--------------------------------------------------------------------    

    protected void setState(){ state = true; }
    //--------------------------------------------------------------------    

    protected void initialize(){ initialized = true; }
    //--------------------------------------------------------------------    

    protected void setLatestActionDate(){
        latestActionDate = new Date();
    }
    //--------------------------------------------------------------------    

    protected Date getLatestActionDate(){
        return latestActionDate;
    }
    //--------------------------------------------------------------------    

    protected DeviceType getDeviceType(){ return type; }
    //--------------------------------------------------------------------    

    protected String getLatestActionDateString(){
        SimpleDateFormat converter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	String date = converter.format(latestActionDate);
        return date; //2014/08/06 15:59:48
    }
    //--------------------------------------------------------------------    

    protected static int getCount(){ return counter; }
    //--------------------------------------------------------------------    

    protected void addAction(Action t){
        Actions.add(t);
    }
    //--------------------------------------------------------------------    

    protected Action getLatestAction(){
        return Actions.get(counter-1);
    }
    //--------------------------------------------------------------------    
    //dopisac sprawdzanie co x sekund, lub generowanie przerwania po wykryciu sygnalu
    protected boolean runThread() throws InterruptedException{
        new Thread("Blink_show") {
        @Override
        public void run() {
            System.out.println("Nasluch urzadzenia: " + name + " wiringPiport:" + portGpio);
            // create gpio controller
            final GpioController gpio = GpioFactory.getInstance();

            // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
            final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(port, PinPullResistance.PULL_DOWN);

            // create and register gpio pin listener
            myButton.addListener(new GpioPinListenerDigital() {
                @Override
                public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                    // display pin state on console
                    System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                    listener.eventOccured(event.getPin().toString(),name,localization,event.getState().toString());

                }

            });

            System.out.println(" ... complete the GPIO #08 circuit and see the listener feedback here in the console.");

            // keep program running until user aborts (CTRL-C)
            for (;;) {
                try{
                Thread.sleep(500);
                }
                catch(InterruptedException x){}
            }

            // stop all GPIO activity/threads by shutting down the GPIO controller
            // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
            // gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller        
                }
        }.start();      
        return false;
    }
    //--------------------------------------------------------------------    
    protected boolean stopThread(){
        return false;
    }
    //--------------------------------------------------------------------
    protected boolean generateAlarm(){

        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin "port" as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(port, PinPullResistance.PULL_DOWN);

        // create and register gpio pin listener
        myButton.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
            }

        });

        System.out.println(" ... complete the GPIO #08 circuit and see the listener feedback here in the console.");

        // keep program running until user aborts (CTRL-C)
        for (;;) {
            try{
            Thread.sleep(500);
            }
            catch(InterruptedException x){}
        }

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        // gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller        
        }

    
    //--------------------------------------------------------------------
    /**
     *
     * @param _type
     * @param _name
     * @param _portGpio
     * @param _localization
     * @return
     */
      
    protected boolean stopThread(DeviceType _type,String _name,int _portGpio,String _localization){
        
        return false;
    }
    
}
