/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.nbody;

/**
 *
 * @author servkey
 */
public class Newton {
    //Fórmulas para el cálculo de la ley de newton
    public static double  delta(double centerPosition, double particlePosition){
        return centerPosition - particlePosition;
    }

    public static double r(double x, double y){

        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public static double F(double masaParticle, double masaCenter, double r){
        return (Universo.G * (masaParticle * masaCenter))/(Math.pow(r, 2));
    }

    public static double calcularF(double F, double position, double r){
        return (F * (position/r));
    }

    public static double aceleracion(double f, double position, double r, double masa){
        return (f * (position/r))/masa;
    }

}
