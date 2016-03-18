
package org.usfirst.frc.team2495.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team2495.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team2495.robot.commands.Shooter;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;
import edu.wpi.first.wpilibj.CameraServer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	public static OI oi;
    Command autonomousCommand;
    SendableChooser autochooser;
    Talon fly,back,arm,lift,tankRR,tankRF,tankLF,tankLR;
    Joystick right,left,op;
    CameraServer cam0;
    Servo liftCont;
    Shooter shoot;
    Timer getTime;
    Preferences auton;
    double TIME = 2.5;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    

    
    
    public void robotInit() {
    	
    	
    	
		//oi = new OI();
       // autochooser = new SendableChooser();
        //autochooser.addDefault("Default Auto", new ExampleCommand());
//     chooser.addObject("My Auto", new MyAutoCommand());
       // SmartDashboard.putData("Auto mode", autochooser);
        cam0 = CameraServer.getInstance();
        cam0.setQuality(25);
        cam0.setSize(100);
        cam0.startAutomaticCapture();
      	right = new Joystick(0);//Joysticks left, right, and operator
		left = new Joystick(1);
	//	op = new Joystick(2);
		fly = new Talon(4);//Shooter Motor PWM Port 2
    	arm = new Talon(5);//Arm Motor PWM Port 3
    	back = new Talon(6);//Back Wheels PWM Port 4
    	tankRR = new Talon(0);
    	tankRF = new Talon(1);
    	tankLR= new Talon(2);
    	tankLF = new Talon(3);
    	liftCont = new Servo(7);  
    	
    	tankRR.set(0);
    	tankRF.set(0);
    	tankLR.set(0);
    	tankLF.set(0);
    	
    	
        
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
    	
    	

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
    
    	
    	
       // autonomousCommand = (Command) autochooser.getSelected();
        
		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
		} */
    	
    	// schedule the autonomous command (example)
       // if (autonomousCommand != null) autonomousCommand.start();
    	getTime = new Timer();
    	getTime.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    
        Scheduler.getInstance().run();
        
       
        while(getTime.get() < TIME)
        {
        	tankRR.set(.75);
        	tankRF.set(.75);
        	tankLR.set(-.75);
        	tankLF.set(-.75);
        }
       tankRR.set(0);
       tankRF.set(0);
       tankLR.set(0);
       tankLF.set(0);
      
    }

    public void teleopInit() {
    getTime = new Timer();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	
   
 
    	   Scheduler.getInstance().run();
        
    	if(right.getTrigger() && left.getTrigger())//If driver Triggers slow speed by 2
    	{
    		tankRR.set(-1 * right.getY());
    		tankRF.set(-1 * right.getY());
    		tankLR.set(left.getY());
    		tankLF.set(left.getY());
    	}
    	else
    	{
    		tankRR.set(-1 * right.getY() / 2);
    		tankRF.set(-1 * right.getY() / 2);
    		tankLR.set(left.getY() / 2);
    		tankLF.set(left.getY() / 2);
    	}
    	/*
    	
    	if(op.getTrigger())//If operator Trigger Launch Sequence
    	{
    		getTime.reset();
    		fly.set(-1);//Start Spin up
    		getTime.start();
    		while(!getTime.hasPeriodPassed(1));//Wait 1sec
    		back.set(-1);//Back Wheel Spin
    		getTime.reset();
    		getTime.start();
    		while(!getTime.hasPeriodPassed(.25));//Wait 1/4 sec
    		fly.set(0);//Stop
    		back.set(0);
    		
    	}
		
    	if(op.getRawButton(2))//Intake
    	{
    		fly.set(1);//Front Motors Reverse
    	}
    	else
    	{
    		fly.set(0);//If Button not pressed Motor Off
    	}
    	
    	if(op.getY() <= -.5 && op.getY() < 0)//If Joystick is up, go up?
     	{
    		arm.set(-1);
    	}
    	
    	else if(op.getY() >= .5)
    	{
    	arm.set(1);
    	}
    	
    	else
    	{
    		arm.set(0);
    	}
    	
        if(op.getRawButton(8))
        {
        	liftCont.set(-1);
        }
        else
        {
        	liftCont.set(0);
        }
      /*
        if(op.getRawButton(6))
        {
        	lift.set(-1);
        }
        else
        {
        	lift.set(0);
        }
        */
       
        
    	
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
