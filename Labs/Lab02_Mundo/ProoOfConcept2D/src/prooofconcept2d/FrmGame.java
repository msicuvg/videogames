/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package prooofconcept2d;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author servkey
 */

class WindowEventsAdapter extends KeyAdapter{
     private CanvasDib c;
     public WindowEventsAdapter(CanvasDib c){
         this.c = c;
     }
     public void keyPressed(KeyEvent e){
        System.out.println("KeyPressed: " + e.getKeyCode());
        if (e.getKeyCode() == 32)
        {    
             c.getJugador().saltar();
        }else if (e.getKeyCode() == 65){
            c.getJugador().aumentarVelocidad();
            c.getMundo().aumentarVelocidad();
                   
        }
     }
     public void keyReleased(KeyEvent e){
        if (e.getKeyCode() == 65){
         c.getJugador().disminuirVelocidad();
          c.getMundo().disminuirVelocidad();
        }
     }
}

public class FrmGame extends JFrame{
    private Thread t;
    private final CanvasDib c;
    public FrmGame(CanvasDib c){
       
        this.c = c;
        add(c);
        //Iniciar hilo
        t = new Thread(){
              public void run(){
                  updating();
              }
        };
        t.start();
        this.addKeyListener(new WindowEventsAdapter(c));
         
        this.getContentPane().setBackground(Color.BLUE);
    }
    private long time = System.currentTimeMillis();
    public void updating(){
        while (true){
            if ((System.currentTimeMillis() - time) > 40){
                try {
                    //Thread.sleep(50);
                    //c.setX(c.getX() + 5);
                    c.repaint();
                    //c.getJugador().Dibujar();
                    //c.getJugador().Caminar();
                } catch (Exception ex) {
                    Logger.getLogger(FrmGame.class.getName()).log(Level.SEVERE, null, ex);
                }
                time = System.currentTimeMillis();
            }
        
        }
    }
}
