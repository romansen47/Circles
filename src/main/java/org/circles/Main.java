package org.circles;

import org.circles.implementation.impl.MovingCirclesPresentation;

public class Main extends MovingCirclesPresentation {
 
    public static void main(String[] args) {
        new Main().run(0);
    } 

    private final int windowsWidth=1200;
    
    @Override
	public int getWindowsWidth() {
		return windowsWidth;
	}

    private final int windowHeight=1000;
    
    @Override
	public int getWindowHeight() {
		return windowHeight;
	}
 

}
