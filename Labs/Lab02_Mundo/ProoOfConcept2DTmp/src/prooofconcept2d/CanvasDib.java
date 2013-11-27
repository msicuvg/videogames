/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package prooofconcept2d;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import javax.imageio.ImageIO;

/**
 *
 * @author servkey
 */
public class CanvasDib extends Canvas{
    private Jugador jugador;
    private Mundo mundo;
    private ArrayList<BufferedImage> marios = new ArrayList<BufferedImage>();
   
    public CanvasDib(){
         BufferedImage mario1 = cargarImagen("./images/mario_1a.png");
         BufferedImage mario2 = cargarImagen("./images/mario_2a.png");
         BufferedImage mario3 = cargarImagen("./images/mario_3a.png");
         BufferedImage mario4 = cargarImagen("./images/mario1_jump.png");
          BufferedImage mario5 = cargarImagen("./images/mario_0a.png");
         marios.add(mario1);
         marios.add(mario2);
         marios.add(mario3);
         marios.add(mario4);
         marios.add(mario5);
         jugador = new Jugador(this,this.getGraphics(), marios);
         Thread tr = new Thread(jugador);
         tr.start();
         mundo = new Mundo(this,this.getGraphics(),"./sounds/01-overworld.wav","./images/NESMarioBros_World1_1_b.png");
         Thread trMundo = new Thread(getMundo());
         trMundo.start();
         //".sounds/01-main-theme-overworld.mp3"
        
    }
 
    public BufferedImage cargarImagen(String file){
        BufferedImage img = null;
        try{
            URL image = getClass().getResource(file);
            img = ImageIO.read(image);
        }catch(Exception e){
        }
         return img;
    }
    
    @Override
    public void paint(Graphics g)
    {    
        if(jugador.getX() >= 400)
            getMundo().setAvanzando(true);
        
        if (getMundo().getX() <= getMundo().getMinX()){
            jugador.detener(g);
            mundo.getClip().stop();
            mundo.getClipCompleted().start();
        }
            
        getMundo().dibujar(g);
         
         //g.setColor(Color.WHITE);            //Activa el color azul
         //g.fillOval(getX(), getY(), 60, 60); //Dibuja un óvalo
         /*if (getJugador() == null) setJugador(new Jugador(this,this.getGraphics(),marios));
         
         if (indexMario >= 3)
             indexMario = 0;
         g.drawImage(marios.get(indexMario), x, 110, this);
         indexMario++;*/
        //if (g != null)
         //jugador.avanzar(g);
         jugador.avanzar(g);
         jugador.dibujar(g);
        // jugador.dibujar(g);

    }   

    @Override
    public void update(Graphics g)
    {
        //super.update(g);
        paint(g);
    }
    

    /**
     * @return the jugador
     */
    public Jugador getJugador() {
        return jugador;
    }

    /**
     * @param jugador the jugador to set
     */
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    /**
     * @return the mundo
     */
    public Mundo getMundo() {
        return mundo;
    }

    /**
     * @param mundo the mundo to set
     */
    public void setMundo(Mundo mundo) {
        this.mundo = mundo;
    }
    
        
}
