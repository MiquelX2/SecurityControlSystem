/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwer_lcd_sensors;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author miquel
 */
public class MainMenu {
    public Graphics2D g;
    int width;
    int height;
    int latestRow;
    //--------------------------------------------------------------------    

    public MainMenu(Graphics2D x){
        g = x;
        width = 160;
        height = 128;
        latestRow = 20;
        drawAll("Nazwa","Pomieszczenie","Dlugosc(s)","Temperatura");
    }
    //--------------------------------------------------------------------    

    public void drawAll(String col1,String col2,String col3,String col4){
        //Minimalna slabo czytelna czcionka
        //g.setFont(new Font("TimesRoman",Font.PLAIN,7));
        //Minimalna dobrze czytelna czcionka
        g.setFont(new Font("TimesRoman",Font.PLAIN,8));
        drawTitle("System alarmowy"+g.getFont().getSize());
        drawFirstColumn(col1);
        drawSecondColumn(col2);
        drawThirthColumn(col3);
        drawFourthColumn(col4);
    }
    //--------------------------------------------------------------------    

    protected void drawTitle(String s){
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, 160, 10);
        g.setColor(Color.BLACK);
        g.drawString(s, 0, 10);
        g.setColor(Color.WHITE);
        g.drawLine(0, 10, 160, 10);
    }
    //--------------------------------------------------------------------    

    protected void drawFirstColumn(String value){                  
        g.setColor(Color.GREEN);
        g.fillRect(0, 10, 40, 128);
        g.setColor(Color.BLACK);
        g.drawString(value, 0, 20);
        g.setColor(Color.WHITE);
        g.drawLine(0, 20, 160, 20);
        
    }
    //--------------------------------------------------------------------    

    protected void drawSecondColumn(String value){
        g.setColor(Color.BLUE);
        g.fillRect(40, 10, 80, 128);
        g.setColor(Color.BLACK);
        g.drawString(value, 40, 20);
        g.drawLine(0, 20, 160, 20);

        
    }
    //--------------------------------------------------------------------    

    protected void drawThirthColumn(String value){                   
        g.setColor(Color.YELLOW);
        g.fillRect(80, 10, 120, 128);
        g.setColor(Color.BLACK);
        g.drawString(value, 80, 20);
        g.setColor(Color.WHITE);
        g.drawLine(0, 20, 160, 20);
    }
    //--------------------------------------------------------------------    

    protected void drawFourthColumn(String value){
        g.setColor(Color.RED);
        g.fillRect(120, 10, 160, 128);
        g.setColor(Color.BLACK);
        g.drawString(value, 120, 20);
        g.setColor(Color.WHITE);
        g.drawLine(0, 20, 160, 20);
    }
    //--------------------------------------------------------------------    

    protected void addRow(String one, String two,String three,String four){
        latestRow += 10;
        if (latestRow >= height ) {
            drawAll("Nazwa","Pomieszczenie","Dlugosc(s)","Temperatura");
            latestRow = 30;
        }
        g.drawString(one, 0 , latestRow );
        g.drawString(two, 40 , latestRow );
        g.drawString(three, 80 , latestRow );
        g.drawString(four, 120 , latestRow );
    }
    //--------------------------------------------------------------------    

   
}
