/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package Team4450.Robot19;

import Team4450.Lib.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climb {

    private Robot robot;
    private boolean extended, retracted;

    public Climb(Robot robot) {
        Util.consoleLog();
        this.robot = robot;
    }

    public void dispose() {
        Util.consoleLog();
        if (Devices.frontClimbValve != null) Devices.frontClimbValve.dispose();
		if (Devices.rearClimbValve != null) Devices.rearClimbValve.dispose();
    }

    public void climbExtend() {
        Devices.frontClimbValve.SetA();
        Devices.rearClimbValve.SetA();
        extended = true;
        retracted = false;
    }

    public void climbRetract() {
        Devices.frontClimbValve.SetB();
        Devices.rearClimbValve.SetB();
        extended = false;
        retracted = true;
    }

    public void dashDisplay() {
        Util.consoleLog();
        SmartDashboard.putBoolean("Climb Enabled", extended);
        SmartDashboard.putBoolean("Climb Disabled", retracted);
    }
}