/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.experimental;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.kauailabs.navx.frc.AHRS;
import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;
import com.nerdherd.lib.logging.NerdyBadlog;
import com.nerdherd.lib.misc.AutoChooser;
import com.nerdherd.lib.motor.motorcontrollers.CANMotorController;
import com.nerdherd.lib.motor.motorcontrollers.SmartCANMotorController;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

import edu.wpi.first.math.util.Units;

// import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
// import edu.wpi.first.wpilibj.geometry.Pose2d;
// import edu.wpi.first.wpilibj.geometry.Rotation2d;
// import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
// import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
// import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
// import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj.util.Units;

/**
 * Add your docs here.
 */
public class Drivetrain extends AbstractDrivetrain {

    protected SmartCANMotorController m_leftMaster, m_rightMaster;
    protected CANMotorController[] m_leftSlaves, m_rightSlaves;
    private AHRS m_nav;
	private AutoChooser m_chooser;
	private double m_previousDistance, m_currentX, m_currentY;
	private String m_date;
	private String m_filePath1 = "/media/sda1/logs/";
	private String m_filePath2 = "/home/lvuser/logs/";
	private String m_fileName;
	private File m_file;
	public FileWriter m_writer;
	private boolean writeException = false;
	private double m_logStartTime = 0;
	private double m_leftDesiredVel, m_rightDesiredVel;
	public double m_lookaheadX, m_lookaheadY;
	public double kLeftStatic, kRightStatic, kMaxVelocity, kLeftTicksPerFoot, kRightTicksPerFoot;
	public double kLeftV, kRightV;
	public double kLeftA, kRightA;

	public DifferentialDriveKinematics m_kinematics;
	private DifferentialDriveOdometry m_odometry;
	private SimpleMotorFeedforward m_leftFeedforward, m_rightFeedforward;
	private Field2d m_field = new Field2d();

	public double simLeftVolt, simRightVolt;
	
    public Drivetrain(SmartCANMotorController leftMaster, SmartCANMotorController rightMaster, CANMotorController[] leftSlaves, CANMotorController[] rightSlaves, boolean leftInversion, boolean rightInversion, double trackwidth) {
        m_leftMaster = leftMaster;
		m_rightMaster = rightMaster;
		m_leftSlaves = leftSlaves;
		m_rightSlaves = rightSlaves;
		m_leftMaster.configDefaultSettings();
		m_rightMaster.configDefaultSettings();
		m_leftMaster.setInversion(leftInversion);
		m_rightMaster.setInversion(rightInversion);
		m_leftMaster.setBrakeMode();
		m_rightMaster.setBrakeMode();
		m_leftMaster.configFollowers(m_leftSlaves);
		m_rightMaster.configFollowers(m_rightSlaves);
		m_nav = new AHRS(SPI.Port.kMXP);
		m_kinematics = new DifferentialDriveKinematics(trackwidth);
		m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getRawYaw()));
    }



	public void setBrakeMode() {
        m_leftMaster.setBrakeMode();
        m_rightMaster.setBrakeMode();
        for (CANMotorController follower : m_leftSlaves) {
            follower.setBrakeMode();
        }
        for (CANMotorController follower : m_rightSlaves) {
            follower.setBrakeMode();
        }
	}

	public void setCoastMode() {
		m_leftMaster.setCoastMode();
        m_rightMaster.setCoastMode();
        for (CANMotorController follower : m_leftSlaves) {
            follower.setCoastMode();
        }
        for (CANMotorController follower : m_rightSlaves) {
            follower.setCoastMode();
        }
	}

	public void configAutoChooser(AutoChooser chooser) {
		m_chooser = chooser;
	}
	/**
	 * set static friction feedforwards for use in velocity control, view Oblarg's
	 * drive charcterization paper for more info
	 * 
	 * @param leftStatic  left static friction feedforward, in volts
	 * @param rightStatic right static friction feedforward, in volts
	 */
	public void configStaticFeedforward(double leftStatic, double rightStatic) {
		kLeftStatic = leftStatic / 12;
		kRightStatic = rightStatic / 12;
	}

	/**
	 * set a maximum velocity, this will keep the code from requesting a velocity
	 * faster than the robot is capable of
	 * 
	 * @param maxVel maximum velocity in talon velocity units
	 */
	public void configMaxVelocity(double maxVel) {
		kMaxVelocity = maxVel;
	}

	/**
	 * configure how many ticks (encoder units) are in a foot, this value is used
	 * for odometry and to set velocities at feet per second
	 * 
	 * @param leftTicks  left ticks per foot, can be same as right
	 * @param rightTicks right ticks per foot, can be same as left
	 */
	public void configTicksPerFoot(double leftTicks, double rightTicks) {
		kLeftTicksPerFoot = leftTicks;
		kRightTicksPerFoot = rightTicks;
	}

	/**
	 * set a default teleop drive command, this should be called after OI is
	 * initialized
	 * 
	 * @param defaultCom teleop drive command
	 */
	
	/**
	 * Set the sensor phase, if moving the robot forwards doesn't increase the
	 * encoder position positively, switch the sensor phase
	 * 
	 * @param leftPhase
	 * @param rightPhase
	 */
	public void configSensorPhase(boolean leftPhase, boolean rightPhase) {
		m_leftMaster.setSensorPhase(leftPhase);
		m_rightMaster.setSensorPhase(rightPhase);
	}

	/**
	 * Set the date for the internal csv logging
	 * 
	 * @param date
	 */
	public void configDate(String date) {
		m_date = date;
		m_fileName = m_date + "drive";
	}	
public void configFeedforwardRight(double kV, double kS, double kA){
	kRightV = kV;
	kRightStatic = kS;
	kRightA = kA;
}

public void configFeedforwardLeft(double kV, double kS, double kA){
	kLeftV = kV;
	kLeftStatic = kS;
	kLeftA = kA;
}

	/**
	 * configure a peak and continuous current limit
	 * 
	 * @param peak
	 * @param continuous
	 */
	public void configCurrentLimit(int peak, int continuous) {
		m_leftMaster.configCurrentLimitContinuous(continuous);
		m_leftMaster.configCurrentLimitPeak(peak);
		m_rightMaster.configCurrentLimitContinuous(continuous);
		m_rightMaster.configCurrentLimitPeak(peak);
	}

	/**
	 * configure PIDF for the left side of the drivetrain
	 * 
	 * @param kP
	 * @param kI
	 * @param kD
	 * @param kF
	 */
	public void configLeftPIDF(double kP, double kI, double kD, double kF) {
		m_leftMaster.configPIDF(kP, kI, kD, kF, 0);
	}

	/**
	 * Configure PIDF for the right side of the drivetrain
	 * 
	 * @param kP
	 * @param kI
	 * @param kD
	 * @param kF
	 */
	public void configRightPIDF(double kP, double kI, double kD, double kF) {
		m_rightMaster.configPIDF(kP, kI, kD, kF, 0);
	}
	
	/**Set power to the drivetrain, in units of -1 to 1
	 * @param leftPower
	 * @param rightPower
	 */
	public void setPower(double leftPower, double rightPower) {
        m_leftMaster.setPower(leftPower);
        m_rightMaster.setPower(rightPower);
	}

	@Override
	public void setPowerFeedforward(double leftPower, double rightPower) {
		m_leftMaster.setPower(leftPower, kLeftStatic * Math.signum(leftPower));
        m_rightMaster.setPower(rightPower, kRightStatic * Math.signum(rightPower));
    }

	/** Set voltage to the drivetrain, from -12 to 12 volts
	 * @param leftVoltage
	 * @param rightVoltage
	 */
	public void setVoltage(double leftVoltage, double rightVoltage) {
		m_leftMaster.setVoltage(leftVoltage);
		m_rightMaster.setVoltage(rightVoltage);

		simLeftVolt = leftVoltage; 
		simRightVolt = rightVoltage;
	}

	/**
	 * Stop the drivetrain
	 */
	public void setPowerZero() {
		m_leftMaster.setPower(0);
		m_rightMaster.setPower(0);
	}

	public void addDesiredVelocities(double leftVel, double rightVel) {
		m_leftDesiredVel = leftVel;
		m_rightDesiredVel = rightVel;
	}

	/**Use motion magic to drive a distance, check Talon SRX documentation for more on Motion Magic
	 * @param leftPosition left position to move in ticks
	 * @param rightPosition right position to move in ticks
	 * @param acceleration acceleration to use in ticks/decisecond/second
	 * @param cruiseVelocity cruise velocity in ticks/second
	 */
	public void setPositionMotionMagic(double leftPosition, double rightPosition, int acceleration,
			int cruiseVelocity) {
		m_leftMaster.configMotionMagic(acceleration, cruiseVelocity);
		m_rightMaster.configMotionMagic(acceleration, cruiseVelocity);
		double leftError = leftPosition - getLeftMasterPosition();
		double rightError = rightPosition - getRightMasterPosition();
        m_leftMaster.setPositionMotionMagic(leftPosition, kLeftStatic * Math.signum(leftError));
        m_rightMaster.setPositionMotionMagic(rightPosition, kRightStatic * Math.signum(rightError));
	}

	/**Set a velocity to the drivetrain in talon velocity units (ticks/decisecond)
	 * @param leftVel
	 * @param rightVel
	 */
	public void setVelocity(double leftVel, double rightVel) {
		if (Math.abs(leftVel) > kMaxVelocity) {
			leftVel = kMaxVelocity * Math.signum(leftVel);
		}
		if (Math.abs(rightVel) > kMaxVelocity) {
			rightVel = kMaxVelocity * Math.signum(rightVel);
		}
        m_leftMaster.setVelocity(leftVel, kLeftStatic * Math.signum(leftVel));
        m_rightMaster.setVelocity(rightVel, kRightStatic * Math.signum(rightVel));
	}

	/**
	 * set encoder positions to 0
	 */
	public void resetEncoders() {
		m_leftMaster.resetEncoder();
		m_rightMaster.resetEncoder();
	}

	public double getLeftOutputVoltage() {
		return m_leftMaster.getVoltage();
	}

	public double getLeftMasterCurrent() {
		return m_leftMaster.getCurrent();
	}

	public double getLeftMasterPosition() {
		return m_leftMaster.getPosition();
	}

	public double getLeftMasterVelocity() {
		return m_leftMaster.getVelocity();
	}

	public double getRightOutputVoltage() {
		return m_rightMaster.getVoltage();
	}

	public double getRightMasterCurrent() {
		return m_rightMaster.getCurrent();
	}

	public double getRightMasterPosition() {
		return m_rightMaster.getPosition();
	}

	public double getRightMasterVelocity() {
		return m_rightMaster.getVelocity();
	}

	public double getRawYaw() {
		double temp = -m_nav.getAngle();
		// return -m_nav.getAngle(); //negative for right hand rule
		if(temp < -180){
			return temp + 360;
		}
		if(temp > 180){
			return temp - 360;
		}
		return temp;
	}

	public double getAngularVelocity() {
		return -m_nav.getRate();
	}
	
	public void resetYaw() {
		m_nav.reset();
	}

	public double getAverageEncoderPosition() {
		return (getRightMasterPosition() + getLeftMasterPosition()) / 2;
	}

	public void setPose(Pose2d pose){
		m_nav.setAngleAdjustment(pose.getRotation().getDegrees());
		m_odometry.resetPosition(pose, new Rotation2d(pose.getRotation().getRadians()));
		}
	public double getXPosMeters(){
		return m_odometry.getPoseMeters().getTranslation().getX();
	}

	public double getYPosMeters(){
		return m_odometry.getPoseMeters().getTranslation().getY();
	}

	public void resetXY(){
		// m_nav.reset();
		m_odometry.resetPosition(new Pose2d(0,0, new Rotation2d(0)), Rotation2d.fromDegrees(getRawYaw()));
	}

	public void setXY(double x, double y) {
		m_currentX = x;
		m_currentY = y;
	}

	public void calcXY() {
		double m_currentDistance = (getRightPositionFeet() + getLeftPositionFeet()) / 2;
		double m_distanceTraveled = (m_currentDistance - m_previousDistance);
		double angle = getRawYaw();
		m_currentX = m_currentX + m_distanceTraveled * Math.cos(Math.toRadians(angle));
		m_currentY = m_currentY + m_distanceTraveled * Math.sin(Math.toRadians(angle));
		m_previousDistance = m_currentDistance;
	}

	public double getXpos() {
		return m_currentX;
	}

	public double getYpos() {
		return m_currentY;
	}

	public double ticksToFeet(double ticks, double ticksPerFoot) {
		return ticks / ticksPerFoot;
	}

	public double feetToTicks(double feet, double ticksPerFoot) {
		return feet * ticksPerFoot;
	}

	public double getLeftVelocityFeet() {
		return ticksToFeet(m_leftMaster.getVelocity() / 0.1, kLeftTicksPerFoot);
	}

	public double getRightVelocityFeet() {
		return ticksToFeet(m_rightMaster.getVelocity() / 0.1, kRightTicksPerFoot);
	}

	public double getLeftPositionFeet() {
		return m_leftMaster.getPosition() / kLeftTicksPerFoot;
	}

	public double getLeftPositionMeters(){
		return Units.feetToMeters(getLeftPositionFeet());
	}

	public double getRightPositionFeet() {
		return m_rightMaster.getPosition() / kRightTicksPerFoot;
	}

	public double getRightPositionMeters(){
		return Units.feetToMeters(getRightPositionFeet());
	}

	public double fpsToTalonVelocityUnits(double fps, double ticksPerFoot) {
		return feetToTicks(fps, ticksPerFoot) / 10;
	}

	@Override
	public void periodic(){
		updateOdometry();
		m_field.setRobotPose(m_odometry.getPoseMeters());
	}
	/**set velocity to Talon SRXs in units of feet/s, unit conversions are handled internally
	 * @param leftVel
	 * @param rightVel
	 */
	public void setVelocityFPS(double leftVel, double rightVel) {
		addDesiredVelocities(leftVel, rightVel);
		setVelocity(fpsToTalonVelocityUnits(leftVel, kLeftTicksPerFoot),
				fpsToTalonVelocityUnits(rightVel, kRightTicksPerFoot));
	}

	public void setVelocityFPS(double leftVel, double rightVel, double leftAccel, double rightAccel){
		m_rightMaster.setVelocity(fpsToTalonVelocityUnits(rightVel, kRightTicksPerFoot), kRightStatic * Math.signum(rightVel) + rightVel * kRightV + rightAccel * kRightA);
		m_leftMaster.setVelocity(fpsToTalonVelocityUnits(leftVel, kLeftTicksPerFoot), kLeftStatic * Math.signum(leftVel) + leftVel * kLeftV + leftAccel * kLeftA);	
	}

	public double getPitch() {
		return m_nav.getPitch();
	}

	public double getRoll() {
		return m_nav.getRoll();
	}

	public double getAngularVelocityX() {
		return m_nav.getRawGyroX();
	}

	public double getAngularVelocityY() {
		return m_nav.getRawGyroY();
	}

	public double getAngularVelocityZ() {
		return m_nav.getRawGyroZ();
	}

	public double getAccelX() {
		return m_nav.getWorldLinearAccelX();
	}

	public double getAccelY() {
		return m_nav.getWorldLinearAccelY();
	}

	public double getAccelZ() {
		return m_nav.getWorldLinearAccelZ();
	}

	public void updateLookahead(double x, double y) {
		m_lookaheadX = x;
		m_lookaheadY = y;
	}

	public void reportToSmartDashboard() {
		SmartDashboard.putData("Field", m_field);	  
		SmartDashboard.putNumber("Left Master Voltage", getLeftOutputVoltage());
		SmartDashboard.putNumber("Right Master Voltage", getRightOutputVoltage());

		SmartDashboard.putNumber("Left Master Position", getLeftMasterPosition());
		SmartDashboard.putNumber("Right Master Position", getRightMasterPosition());

		SmartDashboard.putNumber("Left Master Position Feet", getLeftPositionFeet());
		SmartDashboard.putNumber("Right Master Position Feet", getRightPositionFeet());

		SmartDashboard.putNumber("left Velocity", getLeftMasterVelocity());
		SmartDashboard.putNumber("Right Velocity", getRightMasterVelocity());
		SmartDashboard.putNumber("Yaw", getRawYaw());
		
		SmartDashboard.putNumber("Odometry Angle", m_odometry.getPoseMeters().getRotation().getDegrees());
		SmartDashboard.putNumber("X pos meters", getXPosMeters());
		SmartDashboard.putNumber("Y pos meters", getYPosMeters());
		

	}

	public void startLog() {
		writeException = false;
		// Check to see if flash drive is mounted.
		File logFolder1 = new File(m_filePath1);
		File logFolder2 = new File(m_filePath2);
		Path filePrefix = Paths.get("");
		if (logFolder1.exists() && logFolder1.isDirectory())
			filePrefix = Paths.get(logFolder1.toString(), m_fileName);
		else if (logFolder2.exists() && logFolder2.isDirectory())
			filePrefix = Paths.get(logFolder2.toString(), SmartDashboard.getString("log_file_name", m_fileName));
		else
			writeException = true;

		if (!writeException) {
			int counter = 0;
			while (counter <= 99) {
				m_file = new File(filePrefix.toString() + String.format("%02d", counter) + ".csv");
				if (m_file.exists()) {
					counter++;
				} else {
					break;
				}
				if (counter == 99) {
					System.out.println("file creation counter at 99!");
				}
			}
			try {
				m_writer = new FileWriter(m_file);
				m_writer.append(
						"Time,RightPosition,LeftPosition,RightVelocity,LeftVelocity,RightDesiredVel,LeftDesiredVel,RightVoltage,LeftVoltage,"
								+ "RightMasterCurrent,LeftMasterCurrent,Yaw,Pitch,Roll,"
								+ "LeftVelocityFPS,RightVelocityFPS,RobotX,RobotY,LookaheadX,LookaheadY,AngularVelX,AngularVelY,AngularVelZ,AccelX,AccelY,AccelZ\n");
				m_writer.flush();
				m_logStartTime = Timer.getFPGATimestamp();
			} catch (IOException e) {
				e.printStackTrace();
				writeException = true;
			}
		}
	}

	public void stopLog() {
		try {
			if (null != m_writer)
				m_writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			writeException = true;
		}
	}

	public void logToCSV() {
		if (!writeException) {
			try {
				double timestamp = Timer.getFPGATimestamp() - m_logStartTime;
				m_writer.append(String.valueOf(timestamp) + "," + String.valueOf(getRightMasterPosition()) + ","
						+ String.valueOf(getLeftMasterPosition()) + ","
						+ String.valueOf(m_rightMaster.getVelocity()) + ","
						+ String.valueOf(m_leftMaster.getVelocity()) + ","
						+ String.valueOf(m_rightDesiredVel) + "," + String.valueOf(m_leftDesiredVel) + ","
						+ String.valueOf(m_rightMaster.getVoltage()) + ","
						+ String.valueOf(m_leftMaster.getVoltage()) + ","
						+ String.valueOf(m_rightMaster.getCurrent()) + ","
						+ String.valueOf(m_leftMaster.getCurrent()) + "," + String.valueOf(getRawYaw()) + ","
						+ String.valueOf(getPitch()) + "," + String.valueOf(getRoll()) + ","
						+ String.valueOf(getLeftVelocityFeet()) + "," + String.valueOf(getRightVelocityFeet()) + ","
						+ String.valueOf(m_currentX) + "," + String.valueOf(m_currentY) + ","
						+ String.valueOf(m_lookaheadX) + "," + String.valueOf(m_lookaheadY) + ","
						+ String.valueOf(getAngularVelocityX()) + "," + String.valueOf(getAngularVelocityY()) + ","
						+ String.valueOf(getAngularVelocityZ()) + "," + String.valueOf(getAccelX()) + ","
						+ String.valueOf(getAccelY()) + "," + String.valueOf(getAccelZ()) + "\n");
				m_writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
				writeException = true;
			}
		}
	}

	@Override
	public void initLoggingData() {
		NerdyBadlog.createTopicStr("Drive/RightPosition", () -> String.valueOf(getRightMasterPosition()));
		NerdyBadlog.createTopicStr("Drive/LeftPosition", () -> String.valueOf(getLeftMasterPosition()));
		NerdyBadlog.createTopicStr("Drive/RightVelocity", () -> String.valueOf(m_rightMaster.getVelocity()));
		NerdyBadlog.createTopicStr("Drive/LeftVelocity", () -> String.valueOf(m_leftMaster.getVelocity()));
		NerdyBadlog.createTopic("Drive/RightDesiredVel", () -> m_rightDesiredVel);
		NerdyBadlog.createTopic("Drive/LeftDesiredVel", () -> m_leftDesiredVel);
		NerdyBadlog.createTopic("Drive/RightVoltage", () -> m_rightMaster.getVoltage());
		NerdyBadlog.createTopic("Drive/LeftVoltage", () -> m_leftMaster.getVoltage());
		NerdyBadlog.createTopic("Drive/RightCurrent", () -> m_rightMaster.getCurrent());
		NerdyBadlog.createTopic("Drive/LeftCurrent", () -> m_leftMaster.getCurrent());
		NerdyBadlog.createTopic("Drive/Yaw", () -> getRawYaw());
		NerdyBadlog.createTopic("Drive/Pitch", () -> getPitch());
		NerdyBadlog.createTopic("Drive/Roll", () -> getRoll());
		NerdyBadlog.createTopic("Drive/RightVelFeet", () -> getRightVelocityFeet());
		NerdyBadlog.createTopic("Drive/LeftVelFeet", () -> getLeftVelocityFeet());
		NerdyBadlog.createTopic("Drive/XPos", () -> m_currentX);
		NerdyBadlog.createTopic("Drive/YPos", () -> m_currentY);
		NerdyBadlog.createTopic("Drive/LookaheadX", () -> m_lookaheadX);
		NerdyBadlog.createTopic("Drive/LookaheadY", () -> m_lookaheadY);
		NerdyBadlog.createTopic("Drive/AngularVelocityX", () -> getLeftVelocityFeet());
		NerdyBadlog.createTopic("Drive/AngularVelocityY", () -> getLeftVelocityFeet());
		NerdyBadlog.createTopic("Drive/AngularVelocityZ", () -> getLeftVelocityFeet());
		NerdyBadlog.createTopic("Drive/AccelX", () -> getLeftVelocityFeet());
		NerdyBadlog.createTopic("Drive/AccelY", () -> getLeftVelocityFeet());
		NerdyBadlog.createTopic("Drive/AccelZ", () -> getLeftVelocityFeet());
	}

	public Rotation2d getAngle() {
		return new Rotation2d(-m_nav.getAngle());
	}

	public double getRightVelocityMeters() {
		return Units.feetToMeters(getRightVelocityFeet());
	}

	public Pose2d getPose2d(){
		return m_odometry.getPoseMeters();
	}

	public double getLeftVelocityMeters() {
		return Units.feetToMeters(getLeftVelocityFeet());
	}

	public void configKinematics(double trackwidth, Rotation2d startingAngle, Pose2d startingPose) {
		m_kinematics = new DifferentialDriveKinematics(trackwidth);
		m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getRawYaw()));
	}

	public void updateOdometry() {
		m_odometry.update(Rotation2d.fromDegrees(getRawYaw()), getLeftPositionMeters(), getRightPositionMeters());
	}

	public DifferentialDriveWheelSpeeds getCurrentSpeeds() {
		return new DifferentialDriveWheelSpeeds(getLeftVelocityMeters(),getRightVelocityMeters());
	}

	public void configFeedforwards(SimpleMotorFeedforward left, SimpleMotorFeedforward right) {
		m_leftFeedforward = left;
		m_rightFeedforward = right;
	}

	public void setSpeeds(DifferentialDriveWheelSpeeds speeds, double leftAccel, double rightAccel) {
		var leftVel = feetToTicks(Units.metersToFeet(speeds.leftMetersPerSecond), kLeftTicksPerFoot);
		var rightVel = feetToTicks(Units.metersToFeet(speeds.rightMetersPerSecond), kRightTicksPerFoot);
		m_leftMaster.setVelocity(leftVel, m_leftFeedforward.calculate(speeds.leftMetersPerSecond, leftAccel));
		m_rightMaster.setVelocity(rightVel, m_rightFeedforward.calculate(speeds.rightMetersPerSecond, rightAccel));
	}

	public void setChasisSpeeds(ChassisSpeeds speeds, double leftAccel, double rightAccel){
		setSpeeds(m_kinematics.toWheelSpeeds(speeds), leftAccel, rightAccel); 
	}



	@Override
	public void setSwervePower(double frontLeftPower, double frontRightPower, double backLeftPower,
			double backRightPower) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void setSwerveAnglePower(double frontLeftAnglePower, double frontRightAnglePower, double backLeftAnglePower,
			double backRightAnglePower) {
		// TODO Auto-generated method stub
		
	}

}
