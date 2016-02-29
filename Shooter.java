package org.usfirst.frc.team2495.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class Shooter extends Command {
	Talon	fly = new Talon(3);//Shooter Motor
	Talon arm = new Talon(4);//Arm Motor
	Talon back = new Talon(5);//Back Wheels
	RobotDrive tank = new RobotDrive(1,2);
	Timer getTime = new Timer();
	boolean flag;

	public Shooter() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		getTime.reset();
		flag = false;
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		fly.set(-1);//Start Spin up
		getTime.start();
		while(!getTime.hasPeriodPassed(1));//Wait 1sec
		back.set(-1);//Back Wheel Spin
		getTime.reset();
		getTime.start();
		while(!getTime.hasPeriodPassed(.25));//Wait 1/4 sec
		fly.set(0);//Stop
		back.set(0);
		flag = true;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return flag;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
