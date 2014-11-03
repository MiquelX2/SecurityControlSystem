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

//Najwyzsza klasa, baza calego systemu ktora bedzie zarzadzala wszystkimi modulami
//uruchamiana i Tworzona bezposrednio w main klasy "Serwer_LCD_SENSORS", dla zachowania czytelnosci kodu
public class AlarmSystem implements EventInterface {
    //Zmienne opisujace system i zawierajace jego moduly
    private final String localization;
    private final String ownerName;
    private final String ownerSurname;
    private final DevicesDatabase devicesDatabase;
    private final Image desktop;
    //--------------------------------------------------------------------    
    public AlarmSystem(){
        //konstruktor domyslny inicjujacy pusta klase gotowa do inicjalizacji
        ownerName = null;
        ownerSurname = null;
        localization = null;

        devicesDatabase = new DevicesDatabase();
        desktop = new Image();

    }
    //-------------------------------------------------------------------- 
    public AlarmSystem(String _ownerName,String _ownerSurname,String _localization){
        //konstruktor inicjalizujacy w pelni system, wypelnia wszystkie pola 
        ownerName = _ownerName;
        ownerSurname = _ownerSurname;
        localization = _localization;

        devicesDatabase = new DevicesDatabase();
        desktop = new Image();
    }
    //--------------------------------------------------------------------    
    protected boolean runAlarmSystem(){
        //uruchomienie wszystkich modulow systemu ( urzadzenia, wyswietlanie, aktualizacje SQL, etc.) 
        addAllDevices();
        runAllDevices();
        runSqlUpdate();
        //dopisac kontrole poprawnosci uruchomienia systemow
        return true;
    }    
    //--------------------------------------------------------------------
    protected boolean runSqlUpdate(){
        //uruchamia automatyczne aktualizacje bazy danych SQL
        devicesDatabase.runSqlUpdateThread();
        //dopisac kontrole poprawnosci
        return true;
    }
    //--------------------------------------------------------------------
    protected boolean stopSqlUpdate(){
        //Zatrzymuje automatyczne aktualizacje bazy SQL
        devicesDatabase.stopSqlUpdateThread();
                //dopisac kontrole poprawnosci
        return false;
    }
    //--------------------------------------------------------------------
    //popracuj nad ta funkcja
    protected boolean addAllDevices(){
        //lista  inicjalizacyjna bedzie edytowalna z bazy danych SQL
        
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
        
        
        //dopisac kontrole poprawnosci

        return true;
    }
    //--------------------------------------------------------------------
    protected void addDevice(Device.DeviceType type,String name,int portGpio,String localization){
        //dodanie urzadzenia do bazy urzadzen
        devicesDatabase.addDevice(new Device(type,name,portGpio,localization));
        //dodaj kontrole poprawnosci dodania
    }
    //--------------------------------------------------------------------    
    protected void removeDevice(Device.DeviceType type,String name,int portGpio,String localization){
        //usuniecie urzadzenia z bazy
        devicesDatabase.removeDevice(type,name,portGpio,localization);
        //dodaj kontrole poprawnosci usuniecia urzadzenia
    }
    //--------------------------------------------------------------------    
    protected boolean runAllDevices(){
        //uruchamia wszystkie urzadzenia
        devicesDatabase.runAllDevices();
                //dopisac kontrole poprawnosci

        return true;
    }
    //-------------------------------------------------------------------- 
    protected boolean stopDevice(Device.DeviceType type,String name,int portGpio,String localization){
        //uruchamia wybrane urzadzenie
        devicesDatabase.stopDevice(type,name,portGpio,localization);
                //dopisac kontrole poprawnosci
        return true;
    }
    //-------------------------------------------------------------------- 
    protected boolean stopAllDevices(){
        //zatrzymuje wszystkie urzadzenia
        devicesDatabase.stopAllDevices();
                //dopisac kontrole poprawnosci

        return true;
    }
    //-------------------------------------------------------------------- 
    protected boolean updateScreen(String portGPIO, String name, String place, String value){
        //Aktualizuj ekran niezaleznie od systemu
        desktop.addRow(portGPIO, name, place, value);
        //dodac kontrole poprawnosci dodania wiersza do aktualnego ekranu
        //przemyslec dzialanie i sensownosc tej funkcji
        
        //dopisac kontrole poprawnosci

        return true;
    }
    //-------------------------------------------------------------------- 

    @Override
    public void eventOccured(String portGPIO, String name, String place, String value) {
        //Metoda aktualizuje ekran zgodnie z otrzymanymi danymi ( dodaje wiersz do ekranu ) 
        updateScreen(portGPIO, name, place, value);
        //Dodac kontrole poprawnosci aktualizacji ekranu
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
