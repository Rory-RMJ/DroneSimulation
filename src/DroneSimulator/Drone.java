/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DroneSimulator;

import java.io.Serializable;

/**
 *
 * @author RORY JACKSON
 */
public abstract class Drone implements Serializable {
    private static final long serialVersionUID = 1L ; 
    protected int x, y ;
    protected double rad ; //position and size of the drone
    protected char col ; // colour used
    protected static int droneCtr ; //each drone has to have a unique id
    protected int droneID ; //id for drone
    
    /**
     * Constructor
     * @param ix x position
     * @param iy y position
     * @param ir radians
     */
    Drone(int ix, int iy, double ir){
        x = ix ; 
        y = iy;
        rad = ir ; 
        droneID = droneCtr ++ ; 
        col = 'r' ; 
    }
    
    /**
     * abstract method for drawing the drone
     * @param di DroneInterface
     */
    public abstract void drawDrone(DroneInterface di);
    
    /**
     * Creating the string giving the drones details
     * @return information regarding the drones
     */
    protected String getStrType(){
        return "Drone ID: " + droneID + "x: " + x + "y: " + y ;
    }
    
    /**
     * This converts the information to a string using the toString function
     * @return getStrType()
     */
    public String toString(){
        return getStrType();
    }
    
    /**
     * abstract method for adjusting a drone in the arena
     * @param d DroneArena
     */
    protected abstract void adjustDrone(DroneArena d);
    
    /**
     * return x position
     * @return x
     */
    public int getX(){
        return x ; 
    }
    
    /**
     * return y position
     * @return y
     */
    public int getY(){
        return y; 
    }
    
    /**
     * return radius of drone
     * @return rad
     */
    public double getRad(){
        return rad ; 
    }
    
    /**
     * set up the drone at position nx, ny
     * @param nx new X position
     * @param ny  new y position
     */
    public void setXY(int nx, int ny){
        x = nx ; 
        y = ny ; 
    }
    
    /**
     * return the id of the drone
     * @return droneID
     */
    public int getID(){
        return droneID ; 
    }
}
