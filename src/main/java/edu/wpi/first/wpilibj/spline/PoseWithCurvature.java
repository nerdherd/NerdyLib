package edu.wpi.first.wpilibj.spline;

import edu.wpi.first.wpilibj.geometry.Pose2d;

/**
 * Represents a pair of a pose and a curvature.
 */
@SuppressWarnings("MemberName")
public class PoseWithCurvature {
  // Represents the pose.
  public Pose2d poseMeters;

  // Represents the curvature.
  public double curvatureRadPerMeter;

  /**
   * Constructs a PoseWithCurvature.
   *
   * @param poseMeters           The pose.
   * @param curvatureRadPerMeter The curvature.
   */
  public PoseWithCurvature(Pose2d poseMeters, double curvatureRadPerMeter) {
    this.poseMeters = poseMeters;
    this.curvatureRadPerMeter = curvatureRadPerMeter;
  }

  /**
   * Constructs a PoseWithCurvature with default values.
   */
  public PoseWithCurvature() {
    poseMeters = new Pose2d();
  }
}