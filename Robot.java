
package org.usfirst.frc.team2495.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
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
	
	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;
    Command autonomousCommand;
    SendableChooser autochooser;
    Talon fly,back,arm,lift,tankR,tankL;
    Joystick right,left,op;
    USBCamera usb;
    CameraServer usbServer;
    Servo liftCont;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	
		oi = new OI();
       // autochooser = new SendableChooser();
        //autochooser.addDefault("Default Auto", new ExampleCommand());
//        chooser.addObject("My Auto", new MyAutoCommand());
       // SmartDashboard.putData("Auto mode", autochooser);
        
      startCompetition();
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
    	right = new Joystick(0);//Joysticks left, right, and operator
		left = new Joystick(1);
		op = new Joystick(2);
		fly = new Talon(2);//Shooter Motor PWM Port 2
    	arm = new Talon(3);//Arm Motor PWM Port 3
    	back = new Talon(4);//Back Wheels PWM Port 4
    	tankR = new Talon(0);
    	tankL= new Talon(1);
    	liftCont = new Servo(5);
    	
    	
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
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	fly = new Talon(2);//Shooter Motor
    	arm = new Talon(3);//Arm Motor
    	back = new Talon(4);//Back Wheels
    	tankR = new Talon(0);
    	tankL = new Talon(1);
        Scheduler.getInstance().run();
        
        Timer getTime = new Timer();
        getTime.start();
        while(!getTime.hasPeriodPassed(2.0))
        {
        	tankR.set(-.75);
        	tankL.set(.75);
        }
       tankR.set(0);
       tankL.set(0);
      
    }

    public void teleopInit() {
    	//usb = new USBCamera();
    	//usb.openCamera();
    	//usb.startCapture();
    	//usbServer.startAutomaticCapture("Cam0");//TODO Put usb Camera Name
		
        if (autonomousCommand != null) autonomousCommand.cancel();
        right = new Joystick(0);//Joysticks left, right, and operator
		left = new Joystick(1);
		op = new Joystick(2);
		fly = new Talon(2);//Shooter Motor PWM Port 2
    	arm = new Talon(3);//Arm Motor PWM Port 3
    	back = new Talon(4);//Back Wheels PWM Port 4
    	tankR = new Talon(0);
    	tankL= new Talon(1);
    	liftCont = new Servo(5);
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	
    	right = new Joystick(0);//Joysticks left, right, and operator
		left = new Joystick(1);
		op = new Joystick(2);
		fly = new Talon(2);//Shooter Motor PWM Port 2
    	arm = new Talon(3);//Arm Motor PWM Port 3
    	back = new Talon(4);//Back Wheels PWM Port 4
    	tankR = new Talon(0);
    	tankL= new Talon(1);
    	liftCont = new Servo(5);
    	
   
    	Shooter shoot = new Shooter();
    	
    	if(right.getTrigger() && left.getTrigger())//If driver Triggers slow speed by 2
    	{
    		tankR.set(right.getY());
    		tankL.set(left.getY());
    	}
    	else
    	{
    		tankR.set(right.getY() / 2);
    		tankL.set(left.getY() / 2);
    	}
    	
    	if(op.getTrigger())//If operator Trigger Launch Sequence
    	{
    		shoot.execute();
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
    	else
    	{
    		arm.set(0);
    	}
    	
    	if(op.getY() >= .5)//IF Joystick is down, go down?
    	{
    		arm.set(1);
    	}
    	else
    	{
    		arm.set(0);
    	}
    	
       Scheduler.getInstance().run();
        
        if(op.getRawButton(8))
        {
        	liftCont.set(-1);
        }
        else
        {
        	liftCont.set(0);
        }
       
        if(op.getRawButton(6))
        {
        	lift.set(-1);
        }
        else
        {
        	lift.set(0);
        }
        
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
