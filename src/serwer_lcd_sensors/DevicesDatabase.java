/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwer_lcd_sensors;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author miquel
 */
public class DevicesDatabase implements EventInterface{
    //Baza urzadzen ktora bedzie przechowywac lokalnie baze oraz aktualizowac wpisy zdalne w SQL
    public List<Device> Devices;
    boolean allInitialized;
    
    Image wyswietlacz;
    //-------------------------------------------------------------------- 
    //napisana
    protected boolean checkInitialization(){
        boolean state = true;
        for(Device x : Devices){
            if( !x.isInitializedDevice() ){
                state = false;
                System.out.println("Nie wszystkie urzadzenia zostaly zainicjalizowane!");
                break;
            }
            System.out.println("Wszystkie urzadzenia w pelni zainicjalizowane!");
        }
    
        return state;
    }
    //--------------------------------------------------------------------    
    public DevicesDatabase(){
        Devices = new ArrayList<Device>();
        //wyswietlacz = new Image(x);
        //wyswietlacz.showDesktopScreenCopy(desktopScreenCopy);

    }
    //--------------------------------------------------------------------    
    protected void addDevice(Device _device){
        Devices.add(_device);
    }
    //--------------------------------------------------------------------    
    //Do napisania !
    protected boolean removeDevice(Device _device){
        Iterator<Device> it = Devices.iterator();
        while(it.hasNext()){
            Device x = it.next();
           
        
        }
        return false;
    }    
    //--------------------------------------------------------------------    
    protected boolean removeDevice(Device.DeviceType type,String name,int portGpio,String localization){
        return false;
    }
    //--------------------------------------------------------------------    
    protected void initializeDevice(Device a){
        
    }
    //--------------------------------------------------------------------    
    protected Device getLatestActiveDevice(){
        return null;
    }
    //--------------------------------------------------------------------    
    protected Date getDateOfLatestAction(){
        return null;
    }
    //--------------------------------------------------------------------    
    protected void updateSQL(){
        
    }
    //--------------------------------------------------------------------    
    protected boolean runAllDevices(EventInterface listener){
        for(Device x : Devices)
        {
            try{
                //removed DeviceDatabase listener
                //x.addListener(this);
                x.addListener(listener);
                x.runThread();
            }
            catch(InterruptedException f){}
        }
        return true;
    }
    //--------------------------------------------------------------------    
    protected boolean stopAllDevices(){
        Iterator<Device> dev = Devices.iterator();
        while(dev.hasNext()){
            dev.next().stopThread();
        }
        return true;
    }
        //--------------------------------------------------------------------    
    protected boolean stopDevice(Device.DeviceType _type,String _name,int _portGpio,String _localization){
        return false;
    }
    //--------------------------------------------------------------------    
    protected boolean runSqlUpdateThread(){
        return true;
    }
    //--------------------------------------------------------------------    
    protected boolean stopSqlUpdateThread(){
        return true;
    }
    //-------------------------------------------------------------------
    @Override
    public void eventOccured(String portGPIO,String name,String place,String value) {
            if(value.equals("HIGH")){
                
            System.out.println(portGPIO + " : " + name + " : " + place + " : " + value);
            wyswietlacz.addRow(portGPIO, name, place, value);
        
            }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
