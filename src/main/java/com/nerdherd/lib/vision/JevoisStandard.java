/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.vision;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;



import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.hal.util.UncleanStatusException;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class JevoisStandard extends AbstractCamera implements Runnable {
  private SerialPort m_cam;
	private UsbCamera m_visionCam;
	private Thread m_stream;
	private boolean m_send;
	public double m_offset;
	public double m_distinguish = 0.0;
	private double m_threshold = 25.4;

	private boolean writeException = false;
	String[] parts;
	private String sendValue;
	private String m_filePath1 = "/media/sda1/logs/";
	private String m_filePath2 = "/home/lvuser/logs/";
	private File m_file;
	public FileWriter m_writer;
	private double m_logStartTime = 0;

	// Jevois Serial Output Data
    private double m_contourNum, m_area, m_centerX, m_centerY, m_distance, m_theta, m_thetaOld, m_distanceOld;
    
    // Jevois Intrinsic Constants
    private double m_kHorizontalFOV, m_kVerticalFOV, m_kHorizontalPixels, m_kVerticalPixels, m_kXFocalLength, m_kYFocalLength;
    
    // Jevois Physical Constants
    private double m_kCameraHorizontalMountAngle, m_kCameraMountHeight, m_kCameraHorizontalOffset, m_kTargetHeight, m_kDriveRotationDeadband, m_kDetectDistance;

	public JevoisStandard(int baud, SerialPort.Port port)  {
		m_send = false;
		sendValue = "None";
		try {
			m_cam = new SerialPort(baud, port);
			m_stream = new Thread(this);
			m_stream.start();
		} catch (UncleanStatusException e) {
			e.printStackTrace();
		}
	}

	public void startCameraStream() {
		try {
			m_visionCam = CameraServer.getInstance().startAutomaticCapture();
			m_visionCam.setVideoMode(PixelFormat.kMJPEG, 320, 240, 30);
		} catch (Exception e) {
		}
	}

	public void run() {
		while (m_stream.isAlive()) {
			Timer.delay(0.001);
			if (m_send) {
				m_cam.writeString(sendValue);
				m_send = false;
			}
			if (m_cam.getBytesReceived() > 0) {
				String read = m_cam.readString();
				if (read.charAt(0) == '/') {
					parts = dataParse(read);
					m_contourNum = Integer.parseInt(getData(1));
          m_distance = Double.parseDouble(getData(2));
          m_theta = Double.parseDouble(getData(3));
		  m_distanceOld = Double.parseDouble(getData(4));
		  m_thetaOld = Double.parseDouble(getData(5));
          m_centerX = Double.parseDouble(getData(6));
          m_centerY = Double.parseDouble(getData(7));
				} else {
					System.out.println(read);
				}
			}
		}
	}

	// use if jevois is centered on robot
	@Override
	public double getXThetaOffset() {
		return xPixelToDegree(getTargetX());
	}
	
	@Override
	public double getYThetaOffset(){
		//finish later
		return 0;
	}

	// use if jevois is horizontally offset from center
	public double getAngleToTurn() {
		double radians = (Math.PI / 180) * (xPixelToDegree(getTargetX()) 
						+ m_kCameraHorizontalMountAngle);
		double horizontalAngle = Math.PI / 2 - radians;
		double distance = getOldDistance();
		double f = Math.sqrt(distance * distance + Math.pow(m_kCameraHorizontalOffset, 2)
				- 2 * distance * m_kCameraHorizontalOffset * Math.cos(horizontalAngle));
		double c = Math.asin(m_kCameraHorizontalOffset * Math.sin(horizontalAngle) / f);
		double b = Math.PI - horizontalAngle - c;
		double calculatedAngle = (180 / Math.PI) * (Math.PI / 2 - b);
		if (getTargetX() == 0) {
			return 0;
		} else {
			return calculatedAngle;
		}
	}

	private double xPixelToDegree(double pixel) {
		double radian = Math.signum(pixel) * Math.atan(Math.abs(pixel / m_kXFocalLength));
		double degree = 180 / Math.PI * radian;
		return degree;
	}

	public double getDistance() {
		return m_distance;
  }
  public double getOldDistance(){
    return m_distanceOld;
  }

	public double getDistanceFeet() {
		return m_distance / 12;
	}

	// use if overshooting the turn
	public double getXThetaHorizOffset() {
		double angularError = getXThetaOffset();
		if (-m_threshold < angularError && angularError < m_threshold) {
			m_offset = 0.0;
		} else if (m_threshold > 0) {
			m_offset = angularError - m_threshold;
		} else if (m_threshold < 0) {
			m_offset = angularError + m_threshold;
		}
		return m_offset;
	}

	@Override
	public double getContourNum() {
		return m_contourNum;
	}

	public double getTargetX() {
		return m_centerX;
	}

	public double getTargetY() {
		return m_centerY;
	}

	public double getTargetArea() {
		return m_area;
  }
  
  public double getAngle(){
    return m_theta;
  }


	public void end() {
		m_stream.interrupt();
	}

	private void sendCommand(String value) {
		sendValue = value + "\n";
		m_send = true;
		Timer.delay(0.1);
	}

	private String[] dataParse(String input) {
		return input.split("/");
	}

	private String getData(int data) {
		return parts[data];
	}

	public void ping() {
		sendCommand("ping");
	}

	public void enableStream() {
		sendCommand("streamon");
	}

	public void disableStream() {
		sendCommand("streamoff");
    }
    
    public void configCameraIntrinsics(double kHorizontalFOV, double kVerticalFOV, double kHorizontalPixels, double kVerticalPixels, double kXFocalLength, double kYFocalLength){
        m_kHorizontalFOV = kHorizontalFOV;
        m_kVerticalFOV = kVerticalFOV;
        m_kHorizontalPixels = kHorizontalPixels;
        m_kXFocalLength = kXFocalLength;
        m_kYFocalLength  = kYFocalLength;
    }
    public void configCameraPhysical(double kCameraHorizontalMountAngle, double kCameraMountHeight, double kCameraHorizontalOffset, double kTargetHeight, double kDriveRotationDeadband, double kDetectDistance){
        m_kCameraHorizontalMountAngle = kCameraHorizontalMountAngle;
        m_kCameraMountHeight = kCameraMountHeight;
        m_kCameraHorizontalOffset = kCameraHorizontalOffset;
        m_kTargetHeight = kTargetHeight;
        m_kDriveRotationDeadband = kDriveRotationDeadband;
        m_kDetectDistance = kDetectDistance;
    }

	@Override
	public void reportToSmartDashboard() {
		SmartDashboard.putNumber("Total contours", getContourNum());
		//SmartDashboard.putNumber("Area", getTargetArea());
		SmartDashboard.putNumber("Y coord", getTargetY());
		SmartDashboard.putNumber("X coord", getTargetX());
    SmartDashboard.putNumber("Old Angle to Turn", getAngleToTurn());
    SmartDashboard.putNumber("New Theta", getAngle());
		SmartDashboard.putNumber("Distance", getOldDistance());
		SmartDashboard.putBoolean("Contour detected", getContourNum() > 0);
		SmartDashboard.putBoolean("Is Locked on", Math.abs(getAngleToTurn()) <= m_kDriveRotationDeadband);

	//	SmartDashboard.putNumber("Exposure", getExp());

	}

		
	}


