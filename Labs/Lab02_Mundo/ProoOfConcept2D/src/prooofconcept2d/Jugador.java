/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package prooofconcept2d;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author servkey
 */
public class Jugador extends GameObject implements Runnable{
        private ArrayList<BufferedImage> jugadoresSprite;
        private BufferedImage currentJugadorSprite;
        private int indexSprite;
        private long tiempoFrameCambio = 0;
        private boolean saltando = false;
        private boolean avanzando = false;
        private boolean detenido = false;
       
        private boolean saltandoAsc = false;
        private int startY = 252;
        private int jumpMax = startY - 60;
        private int indexSpriteJump = 3;
        private int indexSpriteDetenido = 4;
        private int velocidadNormal = 45;
        private int velocidadMax = velocidadNormal - 30;
        private int posicionMaximaX = 400;
        
        public Jugador(CanvasDib c, Graphics g, ArrayList<BufferedImage> jugadores){
            setCanvas(c);
            setG(g);
            setX(0);
            setY(252);
            setVelocidad(velocidadNormal);
            this.jugadoresSprite = jugadores;
            setCurrentJugadorSprite();
        }
        
        public void saltar(){
            new GameUtil().getClip("./sounds/smb_jump-small.wav").start();
            saltando = true;
            saltandoAsc = true;
        }
         public void avanzar(Graphics g){
            this.setG(g);
            avanzando = true;
        }
         
         public void detener(Graphics g){
            this.setG(g);
            detenido = true;
        }
            
        public void aumentarVelocidad(){
            if (getVelocidad() == velocidadNormal) {
                setVelocidad(velocidadMax);
            }
        }
        
        public void disminuirVelocidad(){
            if (getVelocidad() == velocidadMax) {
                setVelocidad(velocidadNormal);
            }
        }
        
        /*public void caminar(Graphics g){
            this.setG(g);
            caminando = true;
         }*/
        
    /*   public synchronized void avanzar(Graphics g){
            if((System.currentTimeMillis() - tiempoFrameCambio) > getVelocidad()){
                if (indexSprite >= jugadoresSprite.size()) {
                    indexSprite = 0;
                }
                tiempoFrameCambio = System.currentTimeMillis();
                setX(getX() + 5);
                dibujar(g);
                if (getX() > 800) {
                    setX(10);
                }
                indexSprite++;
            }else {
                dibujar(g);
            }
         }
              */  
        
        private void setCurrentJugadorSprite(){
            BufferedImage br = null;
            if (!detenido){
                if (!saltando){
                    if (indexSprite >= 3) {
                        indexSprite = 0;
                    }
                    br = jugadoresSprite.get(indexSprite);
                }else if(!detenido){
                    br = jugadoresSprite.get(indexSpriteJump);
              }
            }else {
                br = jugadoresSprite.get(indexSpriteDetenido);
            }
            currentJugadorSprite = br;
        }
        public synchronized void dibujar(Graphics g)
        {  
            /*if (indexSprite >= jugadoresSprite.size()) {
                indexSprite = 0;
            }*/
            //System.out.println("Dibujando..." + getY());
           // g.drawImage(jugadoresSprite.get(indexSprite), getX(), getY(), getCanvas());   
            if (currentJugadorSprite != null){
                g.drawImage(currentJugadorSprite, getX(), getY(), getCanvas());       
            }
        }
        /**
         * @return the mario1
         */
        public List<BufferedImage> getJugadoresSprite() {
            return jugadoresSprite;
        }

        /**
         * @param mario1 the mario1 to set
         */
        public void addJugadorSprite(BufferedImage jugador) {
            this.jugadoresSprite.add(jugador);
        }

        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(10);
                    if (tiempoFrameCambio == 0){
                        tiempoFrameCambio = System.currentTimeMillis();
                    }
                    if (detenido){
                            setCurrentJugadorSprite();
                    }else if (avanzando){
                       if((System.currentTimeMillis() - tiempoFrameCambio) > getVelocidad()){
                            if (indexSprite >= 3) {
                                indexSprite = 0;
                            }
                            tiempoFrameCambio = System.currentTimeMillis();
                            if (getX() <= 400){
                                setX(getX() + 5);
                            }
                            if (getX() > 800) {
                                setX(10);
                            }
                            if (saltando){                  
                              if (saltandoAsc){
                                  setY(getY() - 5);
                                  if (getY() <= jumpMax){
                                     saltandoAsc = false;
                                   }  
                              }else{
                                  setY(getY() + 5);
                                  if (getY() == startY){
                                      saltando = false;
                                   }
                              }
                            }
                          //}
                            setCurrentJugadorSprite();
                            indexSprite++;
                       }else {
                            //dibujar(getG());
                       }
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } 

    /**
     * @return the posicionMaximaX
     */
    public int getPosicionMaximaX() {
        return posicionMaximaX;
    }

    /**
     * @param posicionMaximaX the posicionMaximaX to set
     */
    public void setPosicionMaximaX(int posicionMaximaX) {
        this.posicionMaximaX = posicionMaximaX;
    }
}
