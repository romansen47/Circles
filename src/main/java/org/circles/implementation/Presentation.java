package org.circles.implementation;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.util.Random;

import org.circles.implementation.impl.CircleImpl;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
 

public interface Presentation extends MovingCircles{
    
    default long init() {

        String nativesPath = System.getProperty("java.library.path");
        System.setProperty("org.lwjgl.librarypath", nativesPath);

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        long window = GLFW.glfwCreateWindow(getWindowsWidth(), getWindowHeight(), "Moving Circles", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(window);

        GL.createCapabilities();
        GLFW.glfwSwapInterval(1); // V-Sync aktivieren
        return window;
    };

    default void loop(long window){
        initCircles();

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            applyGravity();
            checkCollisions();
            updateCircles();
            drawCircles();

            applyGlfwMethods(window);
        }
    }

    default void initCircles() {
        for (int i = 0; i < getNumberOfCircles(); i++) {
            float radius = 250* (0.05f + getRandomNumberGenerator().nextFloat() * 0.1f)/getNumberOfCircles();
            float x = getRandomNumberGenerator().nextFloat() * 2 - 1 - radius;
            float y = getRandomNumberGenerator().nextFloat() * 2 - 1 - radius;
            float vx = 0f;//getRandomNumberGenerator().nextFloat() * 0.01f - 0.005f;
            float vy = 0f;//getRandomNumberGenerator().nextFloat() * 0.01f - 0.005f;
            getListOfCircles().add(new CircleImpl(x, y, radius, vx, vy));
        }
    }
     
    
    default void applyGlfwMethods(long window){
        glfwSwapBuffers(window);
        glfwPollEvents();
    }
    
    default void run(long window){
        window = init();
        loop(window);
    }

    /**
     * getter for the generator for random numbers
     */
    Random getRandomNumberGenerator();

	int getWindowsWidth();

	int getWindowHeight();

}
