package org.circles.implementation;

public interface Circle {
	
	/**
     * @return the x-coordinate
     */
    public float getXcoordinate();

    /**
     * setter for x-coordinate
     * @param x the x-coordinate
     */
    public void setXcoordinate(float x);

    /**
     *  @return the y-coordinate
     */
    public float getYcoordinate() ;

    /**
     * setter for y-coordinate
     * @param y the y-coordinate
     */
    public void setYcoordinate(float y) ;

    /**
     *  @return the horizontal velocity component
     */
    public float getXvelocity();

    /**
     * setter for the the horizontal velocity component
     * @param vx the horizontal velocity component
     */
    public void setXvelocity(float vx) ;

    /**
     *  @return the vertical velocity component
     */
    public float getYvelocity() ;

    /**
     * setter for the vertical velocity component
     */
    public void setYvelocity(float vy) ;

    /**
     *  @return the radius
     */
    public float getRadius();

    /**
     * setter for the radius
     * @param radius the radius
     */
    public void setRadius(float radius);
    
    /**
     *  @return the mass
     */
    public float getMass() ;

    /**
     * setter for mass
     * @param mass the mass
     */
    public void setMass(float mass);
    
    /** 
     * @return the energy 
     */ 
    default float calculateKineticEnergy() {
        return getMass() * (-getYcoordinate()-getXvelocity() * getXvelocity() - getYvelocity() * getYvelocity());
    }

	float getInitialEnergy();

}
