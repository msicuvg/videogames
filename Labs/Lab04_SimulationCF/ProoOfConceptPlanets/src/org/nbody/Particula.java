/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.nbody;

import java.awt.Image;

/**
 *
 * @author servkey
 */
public class Particula {
    protected double masa = 0;
    protected Image image;
    protected String imageName;
    protected double xPosition = 0;
    protected double yPosition = 0;
    protected double xVelocity = 0;
    protected double yVelocity = 0;
    //Datos procesados
    protected double rx = 0;
    protected double ry = 0;
    protected double r = 0;
    protected double f = 0;
    protected double fx = 0;
    protected double fy = 0;    
    protected Particula netforce;
}
