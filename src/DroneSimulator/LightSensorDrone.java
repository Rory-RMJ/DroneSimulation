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
public class LightSensorDrone extends NormalDrone {
    private static final long serialVersionUID = 1L ; 
    private double lastLight ; 
    
    /**
     * Constructor for LightSensorDrone
     * @param ix x position
     * @param iy y position
     * @param ir radians
     * @param ia angle
     * @param is speed
     */
    public LightSensorDrone(int ix, int iy, double ir, int ia, int is){
        super(ix, iy, ir, ia, is);
        col = 'b' ; 
        lastLight = 0 ; 
    }
    
    /**
     * Move the drone around the arena
     * @param arena DroneArena
     */
    @Override
    public void adjustDrone(DroneArena arena) {
            int arenaX = arena.getXSize();
            int arenaY = arena.getYSize();

            // Drone collisions
            int numCollisionsCtr = 0; // counter for number of collisions
            for (Drone d : arena.getAllDrones()) { // for each Drone in the arena
                    if (new BoxCollider(d.getX(), this.x, d.getY(), this.y, d.getRad(),
                        this.rad, d.getRad(), this.rad).isHitting()) { // check collisions
                            numCollisionsCtr++; // increment counter
                    }
            }

            // arena boundary collision
            if (y <= 0 + rad + dSpeed || y >= arenaY - rad - dSpeed
                    || x <= 0 + rad + dSpeed || x >= arenaX - rad - dSpeed
                    || numCollisionsCtr > 1) {
                    // turn 90 deg clockwise if hitting a wall
			x -= dSpeed * Math.cos(getAngleRad()); // back up when hiting wall
			y -= dSpeed * Math.sin(getAngleRad()); // back up when hitting wall
			dAngle += 90;
            } else {
                    double lightValue = 0;
                    for (Drone d : arena.getAllDrones()) { // calculate sum of squared
                    // differences between LightDrone
                        if (d instanceof LightDrone) {
                                lightValue += Math.pow(Math.pow(this.x - d.getX(), 2)
                                    + Math.pow(this.y - d.getY(), 2), -0.5); // 1 over the distance to each light													
                        }
                    }
                    if (lightValue < lastLight) { // if current light value is less than last light value 
                        dAngle += 5; // turn right 5 degrees
                    }
                lastLight = lightValue; // update light value
		}
            x += dSpeed * Math.cos(getAngleRad()); // change in x position
            y += dSpeed * Math.sin(getAngleRad()); // change in y position
	}

	/**
	 * This function will create a sentence with information regarding the Drone
	 */
	protected String getStrType() {
		return "LightSensorDrone ID: " + droneID + " x: " + x + " y: " + y
				+ " Speed: " + dSpeed;
	}
}
