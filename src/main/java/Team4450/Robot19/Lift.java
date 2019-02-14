/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package Team4450.Robot19;
import Team4450.Lib.Util;
import Team4450.Robot19.Devices;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* REMINDER TO SELF!!! */
/* Most of this lift code is being reused from last year */
/* Double check to make sure that it still applies */
/* Although, from talking to build team, it's pretty much the same lift as last year */


public class Lift {

    private Robot robot;
    private final PIDController PIDController;
    private boolean pausedHeight;

    public Lift(Robot robot) {
        Util.consoleLog();
        this.robot = robot;
        PIDController = new PIDController(0.0, 0.0, 0.0, Devices.winchEncoder, Devices.winchDrive);
        Devices.winchEncoder.reset();
    }

    public void dispose() {
        Util.consoleLog();
        PIDController.disable();
        PIDController.free(); //is now deprecated. Replacement? //TODO: Replace with its replacement
    }
    
    public void powerControl(double power) {
        if (Devices.winchEncoderEnabled) {
            if ((power > 0 && Devices.winchEncoder.get() < 1000) || (power < 0 && !Devices.winchSwitch.get())) {
                //1000 value ought to be changed as this is not the correct number
                //TODO: Change this number
                Devices.winchDrive.set(power);
            } else {
                if (Devices.winchSwitch.get()) {
                    Devices.winchEncoder.reset();
                }
                
                Devices.winchDrive.set(0);  
        }
        else {
            Devices.winchDrive.set(power);
        }
    }
    

    public void manipulateLift(int PIDCount) {
        Util.consoleLog("%d", PIDCount);
        if (PIDCount >= 0)
			{
				if (isPaused()) 
					pauseLift(0);
				
				if (PIDController.isEnabled())
					PIDController.disable();
				
                PIDController.setPID(0, 0, 0, 0);
                /*PID values are temporary. TODO: CHANGE THESE VALUES!*/
				PIDController.setOutputRange(-1, 1);
				PIDController.setSetpoint(PIDCount);
				PIDController.setPercentTolerance(1);
                PIDController.enable();
                pausedHeight = true;
			} else {
                PIDController.disable();
                pausedHeight = false;
			}
		
    }

    public void pauseLift(double speed) {
        Util.consoleLog("%f", speed);
        if (speed != 0)
			{
				if (isPaused()) {
					manipulateLift(-1);
				}
                PIDController.setPID(0, 0, 0, speed);
                /*PID values are temporary. TODO: CHANGE THESE VALUES!*/
				PIDController.setSetpoint(Devices.winchEncoder.get());
				PIDController.setPercentTolerance(1);
				PIDController.enable();
			} else {
				PIDController.disable();
        }
    }

    public boolean isPaused()
	{
		return pausedHeight;
    }
}
