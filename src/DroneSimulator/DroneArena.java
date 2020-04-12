/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DroneSimulator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author RORY JACKSON
 */
public class DroneArena implements Serializable {
    private static final long serialVersionUID = 1L ; 
    private int xSize, ySize, maxDrones ; 
    private ArrayList<Drone> allDrones ;
    private double actualDrones ; 
    
    /**
     * Constructor for the drone arena, initialises a new arraylist of drones.
     * initialises the counter and sets the max number 
     * @param x x position
     * @param y y position
     */
    DroneArena(int x, int y){
        xSize = x ; 
        ySize = y ; 
        allDrones = new ArrayList<Drone>();
        actualDrones = 0 ; 
        maxDrones = 20 ; 
    }
    
    /**
     * Creates an arrayList of strings, then for each drone in the array of drones,
     * their toString function will be added to this array
     * which details their droneID, x, y position and speed
     * @return  string of drone names, coordinates, id and speed
     */
    public ArrayList<String> describeAll(){
        ArrayList<String> ans = new ArrayList<String>() ; 
        for (Drone d : allDrones){
            ans.add(d.toString()) ; //add drone to string in the list
        }
        return ans ; 
    }
    
    /**
     * Function to draw the drone arena in reference to the drone interface
     * @param di DroneInterface
     */
    public void drawArena(DroneInterface di){
        for (Drone d : allDrones){
            d.drawDrone(di);
        }
    }
    
    /**
     * this procedure attempts to add a drone to the drone arena, as well as 
     * there is not another drone currently in this position and as long as 
     * this does not exceed the max drone value.
     * @param d drone
     * @return TRUE if can add drone
     */
    public boolean tryAddDrone (Drone d){
        for (Drone other : allDrones){
            if (new BoxCollider(d.x, other.x, d.y, other.y, d.rad, other.rad,
                d.rad, other.rad).isHitting()){
                return false ;
            }
            if (actualDrones >= maxDrones){
                System.out.println("Too many drones") ; 
            }
        }
        allDrones.add(d);
        actualDrones++ ; 
        return true ; 
    }
    
    /**
     * this procedure will adjust all drones. 
     * for each drone in the array, adjust them in context to this arena
     */
    public void adjustDrones(){
        for (Drone d : allDrones){
            d.adjustDrone(this) ; 
        }
    }
    
    /**
     * return arraylist of drones
     * @return allDrones
     */
    public ArrayList<Drone> getAllDrones(){
        return allDrones ;
    }
    
    /**
     * Add normal drone to the arena
     */
    public void addNormalDrone(){
        Drone newDrone ; 
        Random rand = new Random() ; 
        do {
            int randomXInt = rand.nextInt(xSize - 60) + 30 ; 
            int randomYInt = rand.nextInt(ySize - 60) + 30 ; 
            int randomSpeed = rand.nextInt(1) + 2; 
            int randomAngle = rand.nextInt() ; 
            newDrone = new NormalDrone(randomXInt, randomYInt, 10, randomAngle,
                randomSpeed + 1) ; 
        } while (!tryAddDrone(newDrone));
    }
    
    /**
     * Add the LightDrone to the arena
     */
    public void addLightDrone(){
        Drone newDrone ; 
        Random rand = new Random() ; 
        do{
            int randomXInt = rand.nextInt(xSize - 60) + 30 ; 
            int randomYInt = rand.nextInt(ySize - 60) + 30 ; 
            newDrone = new LightDrone(randomXInt, randomYInt, 10) ; 
        } while (!tryAddDrone(newDrone));
    }
    
    
    /**
     * add light sensor drone to the arena
     */
    public void addLightSensorDrone(){
        Drone newDrone ; 
        Random rand = new Random();
        do{
            int randomXInt = rand.nextInt(xSize - 60) + 30 ; 
            int randomYInt = rand.nextInt(ySize - 60) + 30 ; 
            int randomSpeed = rand.nextInt(1) + 2 ;
            int randomAngle = rand.nextInt() ; 
            newDrone = new LightSensorDrone(randomXInt, randomYInt, 10,
                randomAngle, randomSpeed + 1) ; 
        } while (!tryAddDrone(newDrone)) ; 
    }
    /**
     * function to get the x size of the arena
     * @return xSize
     */
    public int getXSize(){
        return xSize ; 
    }
    
    /**
     * Set the x size of the arena
     * @param x x position
     * @return x
     */
    public int setXSize(int x) {
        xSize = x ; 
        return x;
    }
    
    /**
     * get the y size of the arena
     * @return ySize
     */
    public int getYSize(){
        return ySize ;
    }
    
    /**
     * set the y size of the arena
     * @param y y position
     * @return y
     */
    public int setYSize(int y){
        ySize = y ; 
        return y ;
    }
    
    /**
     * get the max amount of drones
     * @return maxDrones
     */
    public int getMaxDrones(){
        return maxDrones ;
    }
    
    /**
     * set the max drones
     * @param maxDrones max drones number
     */
    public void setMaxDrones(int maxDrones){
        this.maxDrones = maxDrones;
    }
}