/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DroneSimulator;

import java.io.Serializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

/**
 *
 * @author RORY JACKSON
 */
public class LightDrone extends Drone implements Serializable {
    private static final long serialVersionUID = 1L ; 
    
    /**
     * Constructor
     * @param x x position
     * @param y y position
     * @param r radians 
     */
    LightDrone(int x, int y, int r){
        super(x,y,r) ; 
        col = 'y';
    }
    
    /**
     * This function will create a sentence with information regarding the robot
     * @return string with information
     */
    protected String getStrType(){
        return "LightDrone ID: " + droneID + " x: " + x + " y: " + y ; 
    }
    
    /**
     * Method used to draw the drone in relation to the drone interface.
     * This fills a circle with the colour yellow, as initiated in the constructor
     * @param di DroneInterface
     */
    @Override
    public void drawDrone(DroneInterface di){
        GraphicsContext gc = di.getGC(); 
        gc.setFill(di.colFromChar(col)); //set fill colour
        gc.fillArc(x - rad, y - rad, rad * 2, rad * 2, 0, 360, ArcType.ROUND) ; 
    }
    
    /**
     * Abstract method carried but unimplemented
     * @param d DroneArena
     */
    @Override
    protected void adjustDrone(DroneArena d){
        
    }
}
