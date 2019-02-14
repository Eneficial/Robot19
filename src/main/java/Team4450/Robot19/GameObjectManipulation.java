/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package Team4450.Robot19;

import Team4450.Lib.*;

public class GameObjectManipulation {

    private Robot robot;
    private Thread IntakeThread;
    private boolean intake, autoIntake, outtake;

    public GameObjectManipulation(Robot robot) {
        Util.consoleLog();
        this.robot = robot;
    }

    public void dispose() {
        Util.consoleLog();
        if (Devices.pickupValve != null) Devices.pickupValve.dispose(); //I *think* this is for the ball intake. This wasn't completely clear from the assignments & devices file as well as robot CAD
		if (Devices.hatchKickValve != null) Devices.hatchKickValve.dispose();
    }


    //In regards to the hatch mechanism, I am assuming that there are no linear actuators involved
    //This is from talking to build team and agreeing that a linear actuator is too slow and a piston is a much better solution
    public void hatchPickUp() {
        Devices.hatchKickValve.Open();
    }

    public void hatchPlace() {
        Devices.hatchKickValve.Close();
    }



    //Ball intake & outtake
    public void BallIntake(double power) {
        Util.consoleLog();
        Devices.pickupMotor.set(power);
    }

    public void BallOuttake(double power) {
        Util.consoleLog();
        Devices.ballSpit.set(power);
    }

    public void BallStop(double power) {
        Util.consoleLog();
        Devices.pickupMotor.set(0);
        Devices.ballSpit.set(0);
    }


    //Ball intake thread
    public void autoIntakeStart() {
        Util.consoleLog();
        if (IntakeThread != null)
            return;
        Util.consoleLog();
        IntakeThread = new IntakeThread();
        IntakeThread.start();
    }

    public void autoIntakeStop() {
        Util.consoleLog();
        if (IntakeThread != null) {
            IntakeThread.interrupt();
            IntakeThread = null;
        }
    }
    
    
    private class IntakeThread extends Thread {
        IntakeThread() {
            Util.consoleLog();
            this.setName("AutoIntakeThread");
        }

        public void run() {
            Util.consoleLog();
            double stopCurrent;
            stopCurrent = 15.0; //TODO: Figure out this actual value
            try {
                autoIntake = true;
                //dash display
                BallIntake(0.5);
                sleep(250);
                while (!isInterrupted() && robot.isEnabled() && Devices.pickupMotor.getOutputCurrent() < stopCurrent) {
					Timer.delay(0.02); //Why is this erroring?
				}
				
				if(!interrupted()) 
					Util.consoleLog("ERROR 404: Cube not found.");
            }
                catch (InterruptedException e) {BallStop();} //Why is ballstop erroring?
				catch (Throwable e) {e.printStackTrace(Util.logPrintStream);}
                finally {BallStop();}

                IntakeThread = null;
        }
    }
}
            
