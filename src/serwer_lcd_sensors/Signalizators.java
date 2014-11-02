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
public class Signalizators extends Device {
    String sigName;
    //--------------------------------------------------------------------    

    public Signalizators(String _sigN,String _name){
        sigName = _sigN;
    }
    //--------------------------------------------------------------------    

    
    public Signalizators(String _name){
        sigName = "";
    }
    //--------------------------------------------------------------------    

    public Signalizators(){
        super();
        sigName = "";
    }    
    //--------------------------------------------------------------------    

}
