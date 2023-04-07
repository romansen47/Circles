package org.circles.impl;

import org.circles.ICircle;

/**
 * 
 * @author romansen
 *
 * Implementation for a circle.
 * 
 */
public class Circle implements ICircle{
	
	/** 
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @param radius the radius
	 * @param vx the horizontal velocity
	 * @param vy the vertical velocity
	 */
	public Circle(float x, float y, float radius, float vx, float vy) {
        this.setXcoordinate(x);
        this.setYcoordinate(y);
        this.setRadius(radius);
        this.setXvelocity(vx);
        this.setYvelocity(vy);
        this.setMass(5*(float) Math.pow(radius, 3)); // mass is proportional to the qube of the radius
    }

 
	/**
	 * the x-coordinate
	 */
    private float x;
    
    /**
     * {@inheritDoc }
     */
    public float getXcoordinate() {
        return x;
    }

    /**
     * {@inheritDoc }
     */ 
    public void setXcoordinate(float x) {
        this.x = x;
    }

    /**
     * the y-coordinate
     */
    private float y;
    
    /**
     * {@inheritDoc }
     */
    public float getYcoordinate() {
        return y;
    }

    /**
     * {@inheritDoc }
     */    
    public void setYcoordinate(float y) {
        this.y = y;
    }

    /**
     * the horizontal velocity component
     */
    private float vx;
    
    /**
     * {@inheritDoc }
     */
    public float getXvelocity() {
        return vx;
    }

    /**
     * {@inheritDoc }
     */
    public void setXvelocity(float vx) {
        this.vx = vx;
    }

    /**
     * the vertical velocity component
     */
    private float vy; 
    
    /**
     * {@inheritDoc }
     */
    public float getYvelocity() {
        return vy;
    }

    /**
     * {@inheritDoc }
     */
    public void setYvelocity(float vy) {
        this.vy = vy;
    }
    
    /**
     * the radius
     */
    private float radius;
    
    /**
     * {@inheritDoc }
     */
    public float getRadius() {
        return radius;
    }

    /**
     * {@inheritDoc }
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }

    /**
     * the mass
     */
    private float mass;
    
    /**
     * {@inheritDoc }
     */
    public float getMass() {
        return mass;
    }

    /**
     * {@inheritDoc }
     */
    public void setMass(float mass) {
        this.mass = mass;
    }
   
}

