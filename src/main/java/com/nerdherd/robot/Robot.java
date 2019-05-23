/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.robot;

import com.nerdherd.lib.logging.SubscribedLoggable;
import com.nerdherd.lib.misc.AutoChooser;
import com.nerdherd.lib.motor.single.SingleMotorTalonSRX;
// import com.nerdherd.lib.sensor.analog.LinearAnalogSensor;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
  // public static Drivetrain drive;
  public static AutoChooser chooser;
  public static SubscribedLoggable tester;
  // public static SingleMotorElevator elevator;
  // public static SSTalonSRXPos testMotor;
  // public static SubscribedLoggable motProfPos, motProfVel;
  public static SingleMotorTalonSRX talon;
  // public static LinearAnalogSensor manifoldAbsolutePressure;

  public static OI oi;

  public Robot() {
    super(0.01);
  }
  
  @Override
  public void robotInit() {
    chooser = new AutoChooser();
    // drive = new Drivetrain(RobotMap.kLeftMasterTalonID, RobotMap.kRightMasterTalonID, 	    
    // new NerdyTalon[]{new NerdyTalon(RobotMap.kLeftSlaveTalonID), new NerdyTalon(RobotMap.kLeftSlaveTalon2ID)}, 	  
    // new NerdyTalon[]{new NerdyTalon(RobotMap.kRightSlaveTalonID), new NerdyTalon(RobotMap.kRightSlaveTalon2ID)}, 	
    // true, false);
    // drive.configAutoChooser(chooser);
    // // drive.configMaxVelocity(30000);
    // drive.configSensorPhase(false, false);
    
    // drive.configTicksPerFoot(17000, 17000);
    // drive.configDate("2019_1_26_");
    // // floor
    // drive.configLeftPIDF(0.05, 0, 0, 0.028004625);
    // drive.configRightPIDF(0.05, 0, 0, 0.030084725);
    // drive.configStaticFeedforward(1.152, 1.228);

    // cart
    // drive.configLeftPIDF(0.05, 0, 0, 0.026995605);
    // drive.configRightPIDF(0.05, 0, 0, 0.026487175);
    // drive.configStaticFeedforward(0.760, 1.386);

    // elevator = new SingleMotorElevator(0, "Elevator", false, false);
    // testMotor = new SSTalonSRXPos(1, "testMotor", true, true, 
    //   TestSSGains.testGains, new Matrix(new double[][] {
    //     {0},
    //     {0}
    //   }), 0);
    // testMotor.configPIDF(0.9264515394429866 / 6175.881918041717 * 1023. / 12., 
    // 0, 0.0930904218144866 / 617.5881918041716 * 1023. / 12., 0);
    // testMotor.configTalonDeadband(0.004);
    // testMotor.configObserver(false);
    // testMotor.configStaticFF(0);
    talon = new SingleMotorTalonSRX(1, "yay", true, true);


    // motProfPos = new SubscribedLoggable("motProfPos");
    // motProfVel = new SubscribedLoggable("motProfVel");

    // manifoldAbsolutePressure = new LinearAnalogSensor("temp", 0);
  
    oi = new OI();
    // drive.configDefaultCommand(new ArcadeDrive(drive, oi));
    // NerdyBadlog.initAndLog("/media/sda1/logs/", "wooo_testing", 0.02, testMotor, motProfPos, motProfVel);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // NerdyBadlog.log();
    // drive.reportToSmartDashboard();
    // drive.calcXY();
    // // arm.reportToSmartDashboard();
    // climberWheelRight.reportToSmartDashboard();
    // testMotor.reportToSmartDashboard();
    // manifoldAbsolutePressure.reportToSmartDashboard();
    // SmartDashboard.putBoolean("Is not moving", testMotor.isNotMoving());
    // SmartDashboard.putNumber("FF if not moving", testMotor.getFFIfNotMoving(testMotor.u.get(0,0)));
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
    // drive.stopLog();
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // drive.startLog();

  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    // drive.logToCSV();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
