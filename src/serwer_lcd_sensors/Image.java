/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwer_lcd_sensors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import static java.lang.Thread.sleep;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.tw.pi.framebuffer.FrameBuffer;

/**
 *
 * @author miquel
 */
public class Image extends Thread {
    //Ustaw przed wyslaniem na raspberry
    String SPI="/dev/fb1";
    //Ustaw przy testowaniu na komputerze
    String glowny="dummy_128x160";
    
    //Zmienna dla testowania/debugowania na komputerze
    String monitor=glowny;
    private final int width;
    private final int height;
    public final FrameBuffer fb;
    
    MainMenu menu;
    //--------------------------------------------------------------------
    protected enum Tryb { monitor, spi }
    //-------------------------------------------------------------------- 
    public Image(){
        width = 0;
        height = 0;
        fb = null;
    }
    public Image(Tryb mode)
    {
       
        if(mode == Tryb.monitor){
            fb = new FrameBuffer(glowny);

            width = fb.getScreen().getWidth();
            height = fb.getScreen().getHeight();
        }
        else if (mode == Tryb.spi){
            fb = new FrameBuffer(SPI);

            width = fb.getScreen().getWidth();
            height = fb.getScreen().getHeight();
        }
        else{
            System.err.println("Niestety nie odnalezliono urzadzenia, sprobuj wybrac inny tryb opisany w dokumentacji");
            width = 0;
            height = 0;
            fb = null;
        }       
    }
    //--------------------------------------------------------------------
    public synchronized void showDesktopScreenCopy(boolean switcher){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
//				TestFrameBuffer mt = new TestFrameBuffer("/dev/fb1");


                    if (switcher) {
                            JFrame f = new JFrame("APPP");
                            f.setSize(400,300);
                            f.setLocation(0,0);
                            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            f.getContentPane().add(BorderLayout.CENTER, fb.getScreenPanel());

                            f.setVisible(true);                             
                    }
            }
        });
    }
    //--------------------------------------------------------------------
    protected void funkcje(){
        //blink();
        //test();
        ekranik();
        //System.out.println("Koniec wyswietlania ;)");

        
    }      
    //--------------------------------------------------------------------                
    protected void test(){

        new Thread("Test") {
                @Override
                public void run() {
                        BufferedImage img = fb.getScreen();

                        Graphics2D g = img.createGraphics();

                        // RenderingHints.VALUE_ANTIALIAS_ON must before rotate !
                        // Rotated font drawing behaves strange without that....
                        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(0, 0, width, height);

                        g.setColor(Color.WHITE);
                        g.drawString("Hello world !", 22, 45);

                        int y = 17;
                        g.setColor(Color.RED);
                        g.fillRect(0, y, 20,20);
                        y += 21;

                        g.setColor(Color.GREEN);
                        g.fillRect(0, y, 20,20);
                        y += 21;

                        g.setColor(Color.BLUE);
                        g.fillRect(0, y, 20,20);
                        y += 21;

                        AffineTransform st = g.getTransform();
                        g.translate(width/2, height/2+5);

                        AffineTransform stt = g.getTransform();

                        for (int i=0; i<360; i += 4) {

                                g.rotate(Math.toRadians(i));

                                g.setColor(Color.WHITE);
                                g.drawString("Nice !!!", 0,0);

                                try {
                                        sleep(150);
                                } catch (InterruptedException e) {
                                        return;
                                }

                                g.setColor(Color.LIGHT_GRAY);
                                g.drawString("Nice !!!", 0,0);

                                g.setTransform(stt);
//					g.rotate(Math.toRadians(-i));
                        }
                        g.setTransform(st);


                        g.setFont(new Font("Serif", Font.BOLD, 30));
                        Color c1 = new Color(0, 0, 0, 0);
                        Color c2 = new Color(0, 0, 0, 100);
                        GradientPaint gradient = new GradientPaint(10, 8, c1, 10, 40, c2, true);

                        g.setColor(Color.GREEN);
                        g.fillRect(0, 0, width , height);
                        g.setColor(Color.BLACK);
                        g.setPaint(gradient);
                        g.fillRoundRect(100, 100, 200, 50, 25, 25);
                        g.setPaint(Color.BLACK);
                        g.drawRoundRect(100, 100, 200, 50, 25, 25);
                        g.drawString("Hello World!", 118, 135);

                        try {
                                sleep(2000);
                        } catch (InterruptedException e) {
                                return;
                        }


                        Random r = new Random();

                        while (true) {
                                int x1 = r.nextInt(width);
                                int x2 = r.nextInt(width);
                                int y1 = r.nextInt(height);
                                int y2 = r.nextInt(height);

                                g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
                                g.drawLine(x1, y1, x2, y2);
                        }
                }
        }.start();
    }
    //--------------------------------------------------------------------    
    protected void blink(){
      
        new Thread("Blink_show") {
                @Override
                public void run() {
                    BufferedImage img = fb.getScreen();
                    
                    Graphics2D g = img.createGraphics();
                    
                    // RenderingHints.VALUE_ANTIALIAS_ON must before rotate !
                    // Rotated font drawing behaves strange without that....
                    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    g.setColor(Color.RED);
                    g.fillRect(0, 0, width, height);
            }
        }.start();      
    }
    //--------------------------------------------------------------------    
    protected void ekranik(){
      
        new Thread("Blink_show") {
                @Override
                public void run() {
                    BufferedImage img = fb.getScreen();
                    
                    int w = img.getWidth();
                    int h = img.getHeight();
                    
                    Graphics2D g = img.createGraphics();
                    g.clearRect(0, 0, 160, 128);
                    try{
                    menu = new MainMenu(g);
                   
                    menu.addRow("True", "False", "False", "TRUE");
                    Thread.sleep(700);
                    menu.addRow("True", "False", "False", "TRUE");
                    Thread.sleep(700);
                    menu.addRow("True", "False", "False", "TRUE");
                    Thread.sleep(700);
                    menu.addRow("True", "False", "False", "TRUE");
                    Thread.sleep(700);
                    menu.addRow("True", "False", "False", "TRUE");
                    Thread.sleep(700);
                    menu.addRow("True", "False", "False", "TRUE");
                    Thread.sleep(700);
                    menu.addRow("True", "False", "False", "TRUE");
                    Thread.sleep(700);
                    menu.addRow("True", "False", "False", "TRUE");
                    Thread.sleep(700);
                    menu.addRow("True", "False", "False", "TRUE");
                    Thread.sleep(700);
                    menu.addRow("True", "False", "False", "TRUE");
                    Thread.sleep(700);
                    menu.addRow("True", "False", "False", "TRUE");
                    Thread.sleep(700);
                    menu.addRow("True", "False", "False", "TRUE");
                    Thread.sleep(700);
                            
                    }
                    catch(InterruptedException e){ }
                            
            }
        }.start();   
    }
    //--------------------------------------------------------------------
    public synchronized FrameBuffer getFrameBuffer(){
        return fb;
    }
    //--------------------------------------------------------------------    
    protected int getHeight(){ return height; }
    //--------------------------------------------------------------------    
    protected int getWidth(){ return width; }    
    //--------------------------------------------------------------------    
    protected void addRow(String number,String name,String place,String state){
        menu.addRow(number, name, place, state);

    }
}
