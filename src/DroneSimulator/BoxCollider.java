/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DroneSimulator;

/**
 *
 * @author RORY JACKSON
 */
public class BoxCollider {
    private double droneOneX ; // first drone to compare to x
    private double droneTwoX ; //second drone to compare to x
    private double droneOneY ; //first drone to compare to y
    private double droneTwoY ; //second drone to compare to y
    private double droneOneHeight ; // drone one height
    private double droneTwoHeight;  //drone two height
    private double droneOneWidth ; //drone one width
    private double droneTwoWidth ; //drone two width
    
    /**
     * Constructor method
     * @param x Drone One X
     * @param x2 Drone Two X
     * @param y Drone One Y
     * @param y2 Drone Two Y
     * @param width Drone one Width
     * @param width2 Drone Two width
     * @param height Drone One Height
     * @param height2 Drone Two Height
     */
    public BoxCollider(double x, double x2, double y, double y2, double width,
            double width2, double height, double height2){
        droneOneX = x ; 
        droneTwoX = x2;
        droneOneY = y ; 
        droneTwoY = y2;
        droneOneWidth = width ; 
        droneTwoWidth = width2;
        droneOneHeight = height ; 
        droneTwoHeight = height2 ; 
    }
    
    /**
     * Function to see if the drones are hitting 
     * @return TRUE if there they are hitting on both x and y planes
     */
    public boolean isHitting(){
        double leftX ;
        double leftWidth ; 
        double rightX ; 
        double rightWidth ; 
        double topY ; 
        double topHeight ; 
        double lowerY ; 
        double lowerHeight ; 
        
        if (droneOneX < droneTwoX ) {   //if first drone has a smaller x coord than second
            leftX = droneOneX ; //set left drone to droneOne
            rightX = droneTwoX ; //set right drone to droneTwo
            leftWidth = droneOneWidth ;
            rightWidth = droneTwoWidth ; 
        } else { // invert
            leftX = droneTwoX ; 
            rightX = droneOneX ; 
            leftWidth = droneTwoWidth ; 
            rightWidth = droneOneWidth ; 
        }
        boolean xColl = leftX + leftWidth > rightX - rightWidth ; //condition
        
        if (droneOneY < droneTwoY){//if first drone has smaller y coord than second
            topY = droneOneY ; //set the top y to first drone
            lowerY = droneTwoY ; //set the lower y to second drone
            topHeight = droneOneHeight ;
            lowerHeight = droneTwoHeight ; 
        } else {//invert
            topY = droneTwoY ; 
            lowerY = droneOneY ;
            topHeight = droneTwoHeight ; 
            lowerHeight = droneOneHeight ; 
        }
        boolean yColl = topY + topHeight > lowerY - lowerHeight ; //condition
        
        return xColl && yColl ; 
    }
}
