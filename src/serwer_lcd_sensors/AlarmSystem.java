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
public class AlarmSystem {
    private final String localization;
    private final String ownerName;
    private final String ownerSurname;
    private final DevicesDatabase devicesDatabase;
    private final Image desktop;
    //--------------------------------------------------------------------    
    public AlarmSystem(){
        ownerName = null;
        ownerSurname = null;
        localization = null;

        devicesDatabase = new DevicesDatabase();
        desktop = new Image();

    }
    //-------------------------------------------------------------------- 
    public AlarmSystem(String _ownerName,String _ownerSurname,String _localization){
        ownerName = _ownerName;
        ownerSurname = _ownerSurname;
        localization = _localization;

        devicesDatabase = new DevicesDatabase();
        desktop = new Image();
    }
    //--------------------------------------------------------------------    
    protected boolean runAlarmSystem(){
        addAllDevices();
        runAllDevices();
        runSqlUpdate();
        
        return true;
    }    
    //--------------------------------------------------------------------
    protected boolean runSqlUpdate(){
        devicesDatabase.runSqlUpdateThread();
        return true;
    }
    //--------------------------------------------------------------------
    protected boolean stopSqlUpdate(){
        devicesDatabase.stopSqlUpdateThread();
        return false;
    }
    //--------------------------------------------------------------------
    protected boolean addAllDevices(){
        //Dodanie wszystkich urządzeń i zwrocenie stanu dodawania
        //Dopisac rzucanie wyjatku gdy ktores urzadzenie zawiedzie
        addDevice(Device.DeviceType.MOTION, "Ruch 1", 0, "prawa");
        addDevice(Device.DeviceType.MOTION, "Ruch 2", 2, "srodkowa");
        addDevice(Device.DeviceType.MOTION, "Ruch 3", 3, "lewa");
        
        
        addDevice(Device.DeviceType.NOISE, "Halasu", 8, "lewa_blisk");
        addDevice(Device.DeviceType.LIGHT, "Swiatla", 9, "prawa_blisk");
        
        addDevice(Device.DeviceType.TEMPERATURE, "Temp1", 7, "prototyp");
        addDevice(Device.DeviceType.TEMPERATURE, "Temp2", 7, "prototyp");
        addDevice(Device.DeviceType.TEMPERATURE, "Temp3", 7, "prototyp");
        
        return true;
    }
    //--------------------------------------------------------------------
    protected void addDevice(Device.DeviceType type,String name,int portGpio,String localization){
        devicesDatabase.addDevice(new Device(type,name,portGpio,localization));
    }
    //--------------------------------------------------------------------    
    protected void removeDevice(Device.DeviceType type,String name,int portGpio,String localization){
        devicesDatabase.removeDevice(type,name,portGpio,localization);
    }
    //--------------------------------------------------------------------    
    protected boolean runAllDevices(){
        devicesDatabase.runAllDevices();
        return true;
    }
    //-------------------------------------------------------------------- 
    protected boolean stopDevice(Device.DeviceType type,String name,int portGpio,String localization){
        devicesDatabase.stopDevice(type,name,portGpio,localization);
        return true;
    }
    //-------------------------------------------------------------------- 
    protected boolean stopAllDevices(){
        devicesDatabase.stopAllDevices();
        return true;
    }
    //-------------------------------------------------------------------- 
    protected boolean updateScreen(){
        //Aktualizuj ekran niezaleznie od systemu
        return true;
    }
    //-------------------------------------------------------------------- 


}
