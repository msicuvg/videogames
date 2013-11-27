/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package prooofconcept2d;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author servkey
 */
public class GameObject {
        private int velocidad = 45;
        private CanvasDib canvas;
        private Graphics g;
        private int x = 10;
        private int y = 10;
        
        public BufferedImage cargarImagen(String file){
            BufferedImage img = null;
            try{
                URL image = getClass().getResource(file);
                img = ImageIO.read(image);
            }catch(Exception e){
            }
             return img;
        }
        
         /**
         * @return the velocidad
         */
        public int getVelocidad() {
            return velocidad;
        }

        /**
         * @param velocidad the velocidad to set
         */
        public void setVelocidad(int velocidad) {
            this.velocidad = velocidad;
        }

        /**
         * @return the canvas
         */
        public CanvasDib getCanvas() {
            return canvas;
        }

        /**
         * @param canvas the canvas to set
         */
        public void setCanvas(CanvasDib canvas) {
            this.canvas = canvas;
        }

        /**
         * @return the g
         */
        public Graphics getG() {
            return g;
        }

        /**
         * @param g the g to set
         */
        public void setG(Graphics g) {
            this.g = g;
        }

        /**
         * @return the x
         */
        public synchronized int getX() {
            return x;
        }

        /**
         * @param x the x to set
         */
        public synchronized void setX(int x) {
            this.x = x;
        }

        /**
         * @return the y
         */
        public synchronized int getY() {
            return y;
        }

        /**
         * @param y the y to set
         */
        public synchronized void setY(int y) {
            this.y = y;
        }
}
