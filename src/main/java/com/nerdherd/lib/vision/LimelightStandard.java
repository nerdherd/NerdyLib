/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.vision;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimelightStandard extends AbstractCamera {
  /**
   * Creates a new Limelight.
   */
  
   // Limelight Networking
  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx;
  NetworkTableEntry ty;
  NetworkTableEntry ta;
  NetworkTableEntry ts;
  NetworkTableEntry tv;
  NetworkTableEntry pipeline;
  NetworkTableEntry ledMode;
  NetworkTableEntry camMode;

  //Limelight Physical Constants
  private double m_kCameraHorizontalMountAngle, m_kCameraMountHeight, m_kCameraHorizontalOffset, m_kTargetHeight, m_kDriveRotationDeadband, m_kDetectDistance;



  public LimelightStandard() {
    tx = table.getEntry("tx");
    ty = table.getEntry("ty");
    ta = table.getEntry("ta");
    ts = table.getEntry("ts");
    tv = table.getEntry("tv");
    pipeline = table.getEntry("pipeline");
    camMode = table.getEntry("camMode");
    ledMode = table.getEntry("ledMode");

    pipeline.setValue(7);
    camMode.setValue(1);

  }

  @Override
  public double getXThetaOffset() {
    double x = tx.getDouble(0.0);
    return x;
  }

  @Override
  public double getYThetaOffset() {
    double y = ty.getDouble(0.0);
    return y;
  }

  public double getTargetArea() {
    double area = ta.getDouble(0.0);
    return area;
  }

  public double getTargetAngle(){
    double skew = ts.getDouble(0.0);
    return skew;
  }

  @Override
  public double getDistance(){
    double dist = (m_kTargetHeight-m_kCameraMountHeight) / Math.tan(getYThetaOffset());
    return dist;
}

  @Override
  public double getContourNum(){
    double num = tv.getDouble(0.0);
    return num;
  }

  public void blink() {
    ledMode.setNumber(2);
  }

  public void setOff(){
    ledMode.setNumber(1);
  }
  
  public void configCameraPhysical(double kCameraHorizontalMountAngle, double kCameraMountHeight, double kCameraHorizontalOffset, double kTargetHeight){
    m_kCameraHorizontalMountAngle = kCameraHorizontalMountAngle;
    m_kCameraMountHeight = kCameraMountHeight;
    m_kCameraHorizontalOffset = kCameraHorizontalOffset;
    m_kTargetHeight = kTargetHeight;
  }

  public void reportToSmartDashboard() {
    // SmartDashboard.putNumber("Limelight X", getXOffsetFromTarget());
    // SmartDashboard.putNumber("Limelight Y", getYOffsetFromTarget());
    // SmartDashboard.putNumber("Limelight Area", getTargetArea());
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
