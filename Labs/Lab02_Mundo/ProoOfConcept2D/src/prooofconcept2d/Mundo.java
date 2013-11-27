/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package prooofconcept2d;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.Clip;
import sun.audio.AudioStream;

/**
 *
 * @author servkey
 */
public final class Mundo extends GameObject implements Runnable{
       private BufferedImage mundoSprite;
       private boolean avanzando = false;
       private long tiempoFrameCambio = 0;
       private int minX = -5140;
       private int velocidadNormal = 400;
       private int velocidadMax = 100;
       
       private ArrayList<GameObject> objects = new ArrayList<GameObject>();
       private AudioStream audio;
       private Clip clip;
       private Clip clipCompleted = new GameUtil().getClip("./sounds/06 Level Complete.wav");
       
       public void aumentarVelocidad(){
            if (getVelocidad() == velocidadNormal)
                setVelocidad(velocidadMax);
        }
        
        public void disminuirVelocidad(){
            if (getVelocidad() == velocidadMax)
                setVelocidad(velocidadNormal);
        }
       
       public void iniciarSonido(String audiofile, String mundo){
            try{                
               setClip(new GameUtil().getClip(audiofile));
               getClip().start();
               getClip().loop(1000);
            }catch (Exception ex) {
                Logger.getLogger(Mundo.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
       public Mundo(CanvasDib c, Graphics g, String audiofile, String mundo){
            setX(0);
            setY(0);
            setVelocidad(velocidadNormal);
            iniciarSonido(audiofile, mundo);
            mundoSprite = cargarImagen(mundo);
      }
    
      public void dibujar(Graphics g)
      {
          g.drawImage(mundoSprite, getX(), getY(), getCanvas());       
      }      

    @Override
    public void run() {
         while(true){
                try {
                    Thread.sleep(10);
                    if (tiempoFrameCambio == 0){
                        tiempoFrameCambio = System.currentTimeMillis();
                    }
                    if (isAvanzando()){
                       if((System.currentTimeMillis() - tiempoFrameCambio) > getVelocidad()){
                           if (getX() >= getMinX())
                           {
                               if (getVelocidad() != velocidadMax)
                                   setX(getX() - 2);
                               else
                                   setX(getX() - 7);
                           }
                         }
                    }
                }catch(Exception e){
                }
         }
          
    }

    /**
     * @return the avanzando
     */
    public boolean isAvanzando() {
        return avanzando;
    }

    /**
     * @param avanzando the avanzando to set
     */
    public void setAvanzando(boolean avanzando) {
        this.avanzando = avanzando;
    }

    /**
     * @return the minX
     */
    public int getMinX() {
        return minX;
    }

    /**
     * @param minX the minX to set
     */
    public void setMinX(int minX) {
        this.minX = minX;
    }

    /**
     * @return the clip
     */
    public Clip getClip() {
        return clip;
    }

    /**
     * @param clip the clip to set
     */
    public void setClip(Clip clip) {
        this.clip = clip;
    }

    /**
     * @return the clipCompleted
     */
    public Clip getClipCompleted() {
        return clipCompleted;
    }

    /**
     * @param clipCompleted the clipCompleted to set
     */
    public void setClipCompleted(Clip clipCompleted) {
        this.clipCompleted = clipCompleted;
    }
      
}
