/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.nbody;

import org.nbody.io.Audio;
import org.nbody.ui.Dibujo;
import org.nbody.io.Entrada;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;



/**
 *
 * @author servkey
 * Luis Gerardo Montané Jiménez
 *
 */


public class Universo extends Dibujo implements Runnable {
    //private Particle center = new Particle();
    public static final String PATH_IMG = "images/";
    public static final String PATH_SOUND = "sounds/2001.mid";
    public static final String PATH_TEST = "test/";
    public static final double G = 6.67e-11;    
    private ArrayList<Particula> particles = new ArrayList<Particula>();
    //public Dibujo plano;
    private double N; //Número de particulas
    private double R; //Dominio
    private int velocidad = 15;
    //Manejo de hilos
    private boolean stop = false;
    private boolean suspend = false;
    

    public Universo(){
    }

    public Universo(String filename) throws IOException,Exception{
        super(filename);
        initSetting(filename);
        agregarEventos();
        setJMenuBar(createMenuBar());
        setVisible(true);        
    }

    //Agregar nuevos eventos al universo
    private void agregarEventos(){
           this.addWindowListener(new WindowListener(){

                public void windowOpened(WindowEvent e) {
                }

                public void windowClosing(WindowEvent e) {
                    setStop();
                }

                public void windowClosed(WindowEvent e) {
                }

                public void windowIconified(WindowEvent e) {
                }

                public void windowDeiconified(WindowEvent e) {
                }

                public void windowActivated(WindowEvent e) {
                }

                public void windowDeactivated(WindowEvent e) {
                }

            });

    }


    // Agregar barra de menu, extender funcionalidad de la libreria de dibujo
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opciones");
        menuBar.add(menu);

       JMenuItem menuItem2 = new JMenuItem(" Pausa   ");
       menuItem2.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                    setSuspend();
            }

       });
       menuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
                                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

       JMenuItem menuItem3 = new JMenuItem(" Reproducir   ");
       menuItem3.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                    setOn();
            }

       });

        menuItem3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
                                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

       JMenuItem menuItem4 = new JMenuItem(" Aumentar velocidad   ");
       menuItem4.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                    aumentarVelocidad();
            }

       });
       menuItem4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
                                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

       JMenuItem menuItem5 = new JMenuItem(" Reducir velocidad   ");
       menuItem5.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                    reducirVelocidad();
            }

       });
       menuItem5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
                                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));


       JMenuItem menuItem6 = new JMenuItem(" Cerrar   ");
       menuItem6.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                    setStop();
            }

       });

        menuItem6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
                                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        menu.add(menuItem2);
        menu.add(menuItem3);
        menu.add(menuItem4);
        menu.add(menuItem5);
        menu.add(menuItem6);
        return menuBar;
    }


    private void initSetting(String filename) throws IOException{
        URL url = null;

        File tmp = new File(filename);
        if (!tmp.exists())
            url = getClass().getResource(PATH_TEST + filename);

        if (url == null)
            Entrada.scannerFile(filename);
        else
            Entrada.scannerFile(url);
        N = Entrada.readDouble();
        R = Entrada.readDouble();
        setXscale((-1*Math.abs(R)),Math.abs(R));
        setYscale((-1*Math.abs(R)),Math.abs(R));
        Entrada.readLine();
        int index = 0;
        while (index < N){
            //Setter particulas
            String line = Entrada.readLine();
            if (line != null && !line.trim().equals("")){
                StringTokenizer lk = new StringTokenizer(line," ");
                Particula particle = new Particula();
                particle.xPosition = Double.parseDouble(lk.nextToken());
                particle.yPosition = Double.parseDouble(lk.nextToken());
                particle.xVelocity = Double.parseDouble(lk.nextToken());
                particle.yVelocity = Double.parseDouble(lk.nextToken());

                particle.masa = Double.parseDouble(lk.nextToken());
                particle.imageName =  lk.nextToken();
                URL tmp1 = getClass().getResource(PATH_IMG + particle.imageName);
                if (tmp1 == null){
                     System.out.println("No se cargo la imagen " + lk.nextToken());
                     System.exit(0);
                }
                particle.image = Dibujo.getImage(tmp1 != null? tmp1:null);

                particles.add(particle);
                index++;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////
    private Particula buscarFuezaDeAtraccion(Particula particule){
        particule.fx = 0;
        particule.fy = 0;
        particule.f = 0;
        particule.netforce = null;
        for (Particula tmp : particles){

            if (tmp != particule){
                  if (particule.netforce != null) //null
                  {                         
                       double x1 = Newton.delta(tmp.xPosition,particule.xPosition);
                       double y1 = Newton.delta(tmp.yPosition,particule.yPosition);
                       double r1 = Newton.r(x1,y1);
                       double f1 =  Newton.F(particule.masa, tmp.masa,r1);
                       double fx = Newton.calcularF(f1,x1,r1);
                       double fy = Newton.calcularF(f1,y1,r1);

                       particule.fx = fx;
                       particule.fy = fy;

                        if (f1 > particule.f){
                            particule.netforce = tmp;
                            particule.rx = x1;
                            particule.ry = y1;
                            particule.r = r1;
                            particule.f = f1;
                        }

                }else{  
                       //particle.netforce = tmp;
                       particule.netforce = tmp;
                       particule.rx = Newton.delta(tmp.xPosition,particule.xPosition);
                       particule.ry = Newton.delta(tmp.yPosition,particule.yPosition);
                       particule.r = Newton.r(particule.rx,particule.ry);
                       particule.f = Newton.F(particule.masa, tmp.masa,particule.r);
                       particule.fx = Newton.calcularF(particule.f,particule.rx,particule.masa);
                       particule.fy = Newton.calcularF(particule.f,particule.ry,particule.masa);
                 }
            }
        }
        return particule.netforce;
    }   


    public synchronized  void setOn(){
        if (suspend){
            System.out.println("Iniciando");
            suspend = false;
            stop = false;
            notifyAll();
        }
    }

    public synchronized  void setSuspend(){
        if (!suspend){
            System.out.println("Suspendiendo");
            suspend = true;
            stop = false;            
        }
    }

    public synchronized boolean isSuspend(){
        try{
            if (suspend)
              wait();
        }catch(InterruptedException e){
           System.out.println("Error en supender");
        }
        return suspend;
    }

    public void setStop(){
        stop = true;
        suspend = false;
        dispose();
    }

    public void aumentarVelocidad(){
        System.out.println("Aumentando velocidad " + velocidad);
        velocidad = velocidad > 0?--velocidad:velocidad;
    }

    public void reducirVelocidad(){
        System.out.println("Reduciendo velocidad " + velocidad);
        velocidad++;
    }
    

    /**
     * Crear simulación
     */
    
    /*
     /**
     * Dibujar planetas
     * @param particles colección con particulas o planetas
     * @param t number of milliseconds
     */
    private void repaint(ArrayList<Particula> particles, int t){
        for(Particula p: particles)
             picture(p.xPosition,p.yPosition,p.image);
        show(t);
    }

    public static void main(String args[]) throws IOException{
        args = new String[1];
        args[0] = "planets.txt";
         if (args.length == 1){
            System.out.println("Inicializando con el archivo: " + args[0]);
            try{
                Universo nb = new Universo(args[0]);
                //nb..start();
            }catch(Exception e){
                System.out.println("Error en archivo de configuración.");
            }
            
            
        }else if (args.length == 0){
            System.out.println("Argumentos inválidos.");
            System.out.println("Comando: java NBody <filaname>");
        }       
    }

    public void run() {
        int index = 0;
        double t = 25000;
        Audio.play(getClass().getResource(PATH_SOUND));
        Image background; background = Dibujo.getImage(getClass().getResource(PATH_IMG + "nightsky.jpg"));
         while (true && !stop){
                  //while (suspend){}
                  isSuspend();

                  picture(0,0,background);
                  index = 0;
                  repaint(particles, velocidad);
                  //Calcular posición y velocidad
                  while (index < particles.size()){
                        Particula p = particles.get(index);
                        buscarFuezaDeAtraccion(p);

                        if (p.netforce != null)
                        {
                            //Cacular aceleración para la velocidad y posición
                            double ax = Newton.aceleracion(p.f,p.rx,p.r,p.masa);
                            double ay = Newton.aceleracion(p.f,p.ry,p.r,p.masa);
                            p.xVelocity = p.xVelocity + (t * ax);
                            p.yVelocity = p.yVelocity + (t * ay);
                            p.xPosition = p.xPosition + (t * p.xVelocity);
                            p.yPosition = p.yPosition + (t * p.yVelocity);
                            p.netforce = null;
                        }
                        index++;
                }
            }
    }
}



