package org.circles.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.circles.ICircle; 
import org.circles.Presentation;

/**
 * 
 * @author romansen
 * 
 * Draft for the implementation of main class
 *
 */
public abstract class MovingCirclesPresentation implements Presentation{

	/**
	 * the gravitational constant
	 */
	private final float gravitationalConstant = -0.00001f;
	
	/**
	 * {@inheritDoc }
	 */
	@Override
	public float getGravitationalConstant() { 
		return gravitationalConstant;
	}

	/**
	 * amount of visible circles
	 */
    private final int numberOfCircles = 5000;
    
    /**
     * {@inheritDoc }
     */
    @Override
	public int getNumberOfCircles() { 
		return numberOfCircles;
	}

	/**
	 * the list of visible circles
	 */
    private final List<ICircle> listOfCircles = new ArrayList<>();
    
    /**
     * {@inheritDoc }
     */
    @Override
	public List<ICircle> getListOfCircles() { 
		return listOfCircles;
	}

	/**
	 * generator for random numbers
	 */
    private final Random random = new Random();
    
    /**
     * {@inheritDoc }
     */
    @Override
	public Random getRandomNumberGenerator() { 
		return random;
	}

	/**
	 * amount of substeps
	 */
    private final int amountOfSubSteps = 1;
    
    /**
     * {@inheritDoc }
     */
    @Override
	public int getAmountOfSubSteps() { 
		return amountOfSubSteps;
	}
    
}
