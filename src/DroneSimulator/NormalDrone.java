/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DroneSimulator;

import java.io.Serializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

/**
 *
 * @author RORY JACKSON
 */
public class NormalDrone extends Drone implements Serializable {
    private static final long serialVersionUID = 1L ; 
    protected double dAngle, dSpeed ; //angle and speed of travel
    
    /**
     * Constructor for Normal Drone
     * @param ix x position
     * @param iy y position
     * @param ir radians
     * @param ia angle
     * @param is speed
     */
    public NormalDrone(int ix, int iy, double ir, int ia, int is) {
        super(ix, iy, ir) ; 
        dAngle = ia ; 
        dSpeed = is ; 
    }
    
    /**
     * Create a line of information regarding the drone
     * @return information regarding the drone
     */
    protected String getStrType(){
        return "NormalDrone ID: " + droneID + " x: " + x + " y: " + y +
                " Speed: " + dSpeed ; 
    }
    
    /**
     * Return string of converted information
     * @return getStrType()
     */
    public String toString(){
        return getStrType();
    }
    
    /**
     * Details how the drone will move.
     * If the drone is either hititng another drone, or if the drone has reached
     * the boundary of the arena, it will alter the angle it is travelling at 
     * by 90 degrees
     * @param arena DroneArena
     */
    public void adjustDrone(DroneArena arena){
        int arenaX = arena.getXSize();
        int arenaY = arena.getYSize() ; 
        
        int numCollisionsCtr = 0 ; //counter for collisions
        for (Drone d : arena.getAllDrones()){ //for each drone in the arena
            if (new BoxCollider(d.x, this.x, d.y, this.y, d.rad, this.rad,
                d.rad, this.rad).isHitting()){ //check collisions
                numCollisionsCtr++ ; 
            }
            
        }
        
        //arena boundary collision
        if(y <= 0 + rad + dSpeed || y >= arenaY - rad - dSpeed ||
                x <= 0 + rad + dSpeed || x >= arenaX - rad - dSpeed ||
                numCollisionsCtr > 1){
            //turn 90 deg clockwise if hitting a wall
            x -= dSpeed * Math.cos(getAngleRad()); //change in x position
            y-= dSpeed * Math.cos(getAngleRad()) ; //change in y position
            
            dAngle += 90 ; 
        }
        x += dSpeed * Math.cos(getAngleRad()) ; 
        y += dSpeed * Math.cos(getAngleRad()) ; 
    }
    
    /**
     * return angle in degrees
     * @return angle in degrees
     */
    public double getAngleDeg(){
        return dAngle ; 
    }
    
    /**
     * return angle as radians
     * @return angle in radians
     */
    public double getAngleRad(){
        return dAngle * (Math.PI/180) ; 
    }
    
    /**
     * Draw the drone in the arena.
     * @param di DroneInterface 
     */
    @Override
    public void drawDrone(DroneInterface di){
        GraphicsContext gc = di.getGC() ; 
        double angle = getAngleRad();
        gc.setFill(di.colFromChar(col)) ; //set fill colour
        gc.fillArc(x - rad, y - rad, rad * 2, rad * 2, 0, 360, ArcType.ROUND) ;
        
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(4) ; 
    }
}
