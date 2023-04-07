package org.circles.implementation; 
 
import org.lwjgl.opengl.GL11;

import java.util.List;

public interface MovingCircles {

	/**
	 * the gravitational constant
	 */
	float getGravitationalConstant();
    
	/**
     * the amount of visible circles
     */
	int getNumberOfCircles();
    
	/**
     * the list of visible circles
     */
	List<Circle> getListOfCircles(); 

    /**
     * the amount of substeps
     */
	int getAmountOfSubSteps();

    default void applyGravity(){
        for (Circle circle : getListOfCircles()) {
            circle.setYvelocity(circle.getYvelocity() + getGravitationalConstant());
        }
    };

    default void checkCollisions(){
        for (int i = 0; i < getListOfCircles().size(); i++) {
            Circle circleA = getListOfCircles().get(i);
            for (int j = i + 1; j < getListOfCircles().size(); j++) {
                Circle circleB = getListOfCircles().get(j);
                if (circleCollision(circleA, circleB)) {
                    resolveCollision(circleA, circleB);
                }
            }
        }
    };

    default boolean circleCollision(Circle a, Circle b) {
        float dx = a.getXcoordinate() - b.getXcoordinate();
        float dy = a.getYcoordinate() - b.getYcoordinate();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < a.getRadius() + b.getRadius();
    }

    default void updateCircles() {
        float epsilon = 1e-3f; // Ruhezone-Schwellenwert
        float damping = 0.99f; // Dämpfungsfaktor, um die Geschwindigkeit zu reduzieren

        for (int step = 0; step < getAmountOfSubSteps(); step++) {
            for (Circle circle : getListOfCircles()) {
                circle.setXcoordinate(circle.getXcoordinate() + circle.getXvelocity());
                circle.setYcoordinate(circle.getYcoordinate() + circle.getYvelocity());

                // Kollision mit Wänden
                if (circle.getXcoordinate() - circle.getRadius() < -1) {
                    circle.setXvelocity(-circle.getXvelocity() * damping);
                    circle.setXcoordinate(-1 + circle.getRadius() + epsilon);
                } else if (circle.getXcoordinate() + circle.getRadius() > 1) {
                    circle.setXvelocity(-circle.getXvelocity() * damping);
                    circle.setXcoordinate(1 - circle.getRadius() - epsilon);
                }

                if (circle.getYcoordinate() - circle.getRadius() < -1) {
                    circle.setYvelocity(-circle.getYvelocity() * damping);
                    circle.setYcoordinate(-1 + circle.getRadius() + epsilon);
                }
            }
        }
    };

    default float calculateKineticEnergy(Circle circle) {
        return 1e15f * circle.getMass() * (circle.getXvelocity() * circle.getXvelocity() + circle.getYvelocity() * circle.getYvelocity());
    }

    default void drawCircles() {
        float maxEnergy = 20.0f; // Wählen Sie einen geeigneten Maximalwert für Ihre Simulation

        // Zeichnen Sie die Kreise mit einer Farbe, die von ihrer kinetischen Energie
        // abhängt
        for (Circle circle : getListOfCircles()) {
            float energy = calculateKineticEnergy(circle);
            //System.out.println(energy);
            float energyRatio = energy*energy / maxEnergy;
            energyRatio = Math.max(0.0f, Math.min(1.0f, energyRatio));
            float r = energyRatio;
            float g = 0.0f;
            float b = 1.0f - energyRatio;

            drawCircle(circle, r, g, b);
        }
    }

    default void drawCircle(Circle circle, float r, float g, float b) {
        int numSegments = 50;
        float angleStep = 2 * (float) Math.PI / numSegments;

        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        GL11.glColor3f(0.0f, 0.0f, 1.0f); // Blaue Farbe
        GL11.glVertex2f(circle.getXcoordinate(), circle.getYcoordinate()); // Zentrum des Kreises

        for (int i = 0; i <= numSegments; i++) {
            float angle = i * angleStep;
            float x = circle.getXcoordinate() + circle.getRadius() * (float) Math.cos(angle);
            float y = circle.getYcoordinate() + circle.getRadius() * (float) Math.sin(angle);
            GL11.glVertex2f(x, y);
        }
        GL11.glColor3f(r, g, b); // Setzt die Farbe des Kreises auf Basis der übergebenen RGB-Werte

        GL11.glEnd();
    }

    default void resolveCollision(Circle c1, Circle c2) {
        float dx = c2.getXcoordinate() - c1.getXcoordinate();
        float dy = c2.getYcoordinate() - c1.getYcoordinate();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        // Kollision
        if (distance < c1.getRadius() + c2.getRadius()) {
            // Berechnen Sie den Normalvektor
            float nx = dx / distance;
            float ny = dy / distance;

            // Berechnen Sie die Relativgeschwindigkeit
            float rvx = c2.getXvelocity() - c1.getXvelocity();
            float rvy = c2.getYvelocity() - c1.getYvelocity();

            // Berechnen Sie die Geschwindigkeitskomponente entlang der Normalen
            float normalVelocity = rvx * nx + rvy * ny;

            // Trennen Sie die Kreise nur, wenn sie sich aufeinander zubewegen
            if (normalVelocity < 0) {
                // Berechnen Sie den Impuls
                float impulse = -(1 + 0.8f) * normalVelocity / (1 / c1.getMass() + 1 / c2.getMass());

                // Aktualisieren Sie die Geschwindigkeiten der Kreise
                c1.setXvelocity(c1.getXvelocity() - impulse * nx / c1.getMass());
                c1.setYvelocity(c1.getYvelocity()-impulse * ny / c1.getMass());
                c2.setXvelocity(c2.getXvelocity()+ impulse * nx / c2.getMass());
                c2.setYvelocity(c2.getYvelocity()+ impulse * ny / c2.getMass());

                // Positionskorrektur
                float overlap = c1.getRadius() + c2.getRadius() - distance;
                float correctionRatio = 0.5f; // Wählen Sie einen Wert zwischen 0 und 1
                float correctionX = nx * overlap * correctionRatio;
                float correctionY = ny * overlap * correctionRatio;
                c1.setXcoordinate(c1.getXcoordinate()- correctionX);
                c1.setYcoordinate(c1.getYcoordinate()- correctionY);
                c2.setXcoordinate(c2.getXcoordinate()+ correctionX);
                c2.setYcoordinate(c2.getYcoordinate()+ correctionY);
            }
        }
    }
    
}
