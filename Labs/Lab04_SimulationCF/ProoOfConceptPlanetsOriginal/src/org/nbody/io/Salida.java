/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.nbody.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 *
 * @author servkey
 */
public class Salida {
        private BufferedWriter bw;
        public Salida(String filename){
            try {
                bw = new BufferedWriter(new FileWriter(new File(filename)));
            } catch (IOException ex) {
                System.out.println("Error al crear archivo");
            }
        }

        public void cerrar(){
            try{
                bw.close();
            }catch(IOException e){
                System.out.println("Error al cerrar archivo");
            }
        }

        public void escribir(String line){
            try{
                bw.write(line);
                bw.flush();
            }catch(IOException e){
                System.out.println("Error al escribir archivo");
            }
        }


}
