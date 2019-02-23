/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.singlespeed;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import com.nerdherd.lib.misc.AutoChooser;
import com.nerdherd.lib.motor.NerdyTalon;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Standard drivetrain class for any sort of tank drive (West Coast Drive,
 * kitbot, etc), supporting any number of motors per drivetrain side
 */
public class Drivetrain extends AbstractDrivetrain {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	protected NerdyTalon m_leftMaster, m_rightMaster;
	protected IMotorController[] m_leftSlaves, m_rightSlaves;
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
	public Command defaultCommand;

	/**
	 * Standard Tank Drive
	 * @param leftTalonMasterID  ID for left master talon
	 * @param rightTalonMasterID ID for right master talon
	 * @param leftSlaves         array of NerdyTalons to follow left master
	 * @param rightSlaves        array of NerdyTalons to follow right master
	 * @param leftInversion      inversion of left side of drivetrain
	 * @param rightInversion     inversion of right side of drivetrain
	 */
	public Drivetrain(int leftTalonMasterID, int rightTalonMasterID, NerdyTalon[] leftSlaves, NerdyTalon[] rightSlaves,
			boolean leftInversion, boolean rightInversion) {
		m_leftMaster = new NerdyTalon(leftTalonMasterID);
		m_rightMaster = new NerdyTalon(rightTalonMasterID);
		m_leftMaster.setInverted(leftInversion);
		m_rightMaster.setInverted(rightInversion);
		m_leftMaster.setNeutralMode(NeutralMode.Brake);
		m_rightMaster.setNeutralMode(NeutralMode.Brake);
		m_leftMaster.configDefaultSettings();
		m_rightMaster.configDefaultSettings();
		m_leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
		m_rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
		m_leftMaster.configFollowerTalons(leftSlaves);
		m_rightMaster.configFollowerTalons(rightSlaves);
		m_leftSlaves = leftSlaves;
		m_rightSlaves = rightSlaves;
		m_nav = new AHRS(SPI.Port.kMXP);
	}

	/**
	 * Standard Tank Drive
	 * 
	 * @param leftTalonMasterID  ID for left master talon
	 * @param rightTalonMasterID ID for right master talon
	 * @param leftSlaves         array of VictorSPX to follow left master
	 * @param rightSlaves        array of VictorSPXs to follow right master
	 * @param leftInversion      inversion of left side of drivetrain
	 * @param rightInversion     inversion of right side of drivetrain
	 */
	public Drivetrain(int leftTalonMasterID, int rightTalonMasterID, VictorSPX[] leftSlaves, VictorSPX[] rightSlaves,
			boolean leftInversion, boolean rightInversion) {
		m_leftMaster = new NerdyTalon(leftTalonMasterID);
		m_rightMaster = new NerdyTalon(rightTalonMasterID);
		m_leftMaster.setInverted(leftInversion);
		m_rightMaster.setInverted(rightInversion);
		m_leftMaster.setNeutralMode(NeutralMode.Brake);
		m_rightMaster.setNeutralMode(NeutralMode.Brake);
		m_leftMaster.configDefaultSettings();
		m_rightMaster.configDefaultSettings();
		m_leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
		m_rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
		m_leftMaster.configFollowerVictors(leftSlaves);
		m_rightMaster.configFollowerVictors(rightSlaves);
		m_leftSlaves = leftSlaves;
		m_rightSlaves = rightSlaves;
		m_nav = new AHRS(SPI.Port.kMXP);
	}

	public void configNeutralMode(NeutralMode mode) {
		m_leftMaster.setNeutralMode(mode);
		m_rightMaster.setNeutralMode(mode);
		for (IMotorController follower : m_leftSlaves) {
			follower.setNeutralMode(mode);
		}
		for (IMotorController follower : m_rightSlaves) {
			follower.setNeutralMode(mode);
		}
	}

	public void setBrakeMode() {
		configNeutralMode(NeutralMode.Brake);
	}

	public void setCoastMode() {
		configNeutralMode(NeutralMode.Coast);
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
	public void configDefaultCommand(Command defaultCom) {
		defaultCommand = defaultCom;
	}

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
		m_leftMaster.set(ControlMode.PercentOutput, leftPower);
		m_rightMaster.set(ControlMode.PercentOutput, rightPower);
	}

	@Override
	public void setPowerFeedforward(double leftPower, double rightPower) {
		m_leftMaster.set(ControlMode.PercentOutput, leftPower, DemandType.ArbitraryFeedForward, kLeftStatic * Math.signum(leftPower));
		m_rightMaster.set(ControlMode.PercentOutput, rightPower, DemandType.ArbitraryFeedForward, kRightStatic * Math.signum(rightPower));
	}

	/** Set voltage to the drivetrain, from -12 to 12 volts
	 * @param leftVoltage
	 * @param rightVoltage
	 */
	public void setVoltage(double leftVoltage, double rightVoltage) {
		m_leftMaster.set(ControlMode.PercentOutput, leftVoltage / 12);
		m_rightMaster.set(ControlMode.PercentOutput, rightVoltage / 12);
	}

	/**
	 * Stop the drivetrain
	 */
	public void setPowerZero() {
		m_leftMaster.set(ControlMode.PercentOutput, 0);
		m_rightMaster.set(ControlMode.PercentOutput, 0);
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
		m_leftMaster.set(ControlMode.MotionMagic, leftPosition, DemandType.ArbitraryFeedForward, kLeftStatic * Math.signum(leftError));
		m_rightMaster.set(ControlMode.MotionMagic, rightPosition, DemandType.ArbitraryFeedForward, kRightStatic * Math.signum(rightError));
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
		m_rightMaster.set(ControlMode.Velocity, rightVel, DemandType.ArbitraryFeedForward,
				kRightStatic * Math.signum(rightVel));
		m_leftMaster.set(ControlMode.Velocity, leftVel, DemandType.ArbitraryFeedForward,
				kLeftStatic * Math.signum(leftVel));
	}

	/**
	 * set encoder positions to 0
	 */
	public void resetEncoders() {
		m_leftMaster.setSelectedSensorPosition(0, 0, 0);
		m_rightMaster.setSelectedSensorPosition(0, 0, 0);
	}

	public double getLeftOutputVoltage() {
		return m_leftMaster.getMotorOutputVoltage();
	}

	public double getLeftMasterCurrent() {
		return m_leftMaster.getOutputCurrent();
	}

	public double getLeftMasterPosition() {
		return m_leftMaster.getSelectedSensorPosition();
	}

	public double getLeftMasterVelocity() {
		return m_leftMaster.getSelectedSensorVelocity(0);
	}

	public double getRightOutputVoltage() {
		return m_rightMaster.getMotorOutputVoltage();
	}

	public double getRightMasterCurrent() {
		return m_rightMaster.getOutputCurrent();
	}

	public double getRightMasterPosition() {
		return m_rightMaster.getSelectedSensorPosition();
	}

	public double getRightMasterVelocity() {
		return m_rightMaster.getSelectedSensorVelocity(0);
	}

	public double getRawYaw() {
		return -m_nav.getAngle() + m_chooser.getDirection();
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

	public void initDefaultCommand() {
		setDefaultCommand(defaultCommand);
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
		return ticksToFeet(m_leftMaster.getSelectedSensorVelocity(0) / 0.1, kLeftTicksPerFoot);
	}

	public double getRightVelocityFeet() {
		return ticksToFeet(m_rightMaster.getSelectedSensorVelocity(0) / 0.1, kRightTicksPerFoot);
	}

	public double getLeftPositionFeet() {
		return m_leftMaster.getSelectedSensorPosition(0) / kLeftTicksPerFoot;
	}

	public double getRightPositionFeet() {
		return m_rightMaster.getSelectedSensorPosition(0) / kRightTicksPerFoot;
	}

	public double fpsToTalonVelocityUnits(double fps, double ticksPerFoot) {
		return feetToTicks(fps, ticksPerFoot) / 10;
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
		SmartDashboard.putNumber("Left Master Voltage", getLeftOutputVoltage());
		SmartDashboard.putNumber("Right Master Voltage", getRightOutputVoltage());

		SmartDashboard.putNumber("Left Master Position", getLeftMasterPosition());
		SmartDashboard.putNumber("Right Master Position", getRightMasterPosition());

		SmartDashboard.putNumber("Left Master Position Feet", getLeftPositionFeet());
		SmartDashboard.putNumber("Right Master Position Feet", getRightPositionFeet());

		SmartDashboard.putNumber("left Velocity", getLeftMasterVelocity());
		SmartDashboard.putNumber("Right Velocity", getRightMasterVelocity());
		SmartDashboard.putNumber("Yaw", getRawYaw());
		SmartDashboard.putNumber("X pos", m_currentX);
		SmartDashboard.putNumber("Y pos", m_currentY);

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
						+ String.valueOf(m_rightMaster.getSelectedSensorVelocity(0)) + ","
						+ String.valueOf(m_leftMaster.getSelectedSensorVelocity(0)) + ","
						+ String.valueOf(m_rightDesiredVel) + "," + String.valueOf(m_leftDesiredVel) + ","
						+ String.valueOf(m_rightMaster.getMotorOutputVoltage()) + ","
						+ String.valueOf(m_leftMaster.getMotorOutputVoltage()) + ","
						+ String.valueOf(m_rightMaster.getOutputCurrent()) + ","
						+ String.valueOf(m_leftMaster.getOutputCurrent()) + "," + String.valueOf(getRawYaw()) + ","
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

}
