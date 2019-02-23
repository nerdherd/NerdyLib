/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.trajectory.falconlib;

import java.util.ArrayList;

import com.nerdherd.lib.misc.NerdyMath;

import jaci.pathfinder.Pathfinder;

/**
 * Add your docs here.
 */
public class FalconTrajectoryFollower {
    
    private ArrayList<TrajectoryPoint> m_trajectory;
    private TrajectoryPoint m_robotSegment, m_lookaheadSegment;
    private Boolean m_goingForwards;

    private double m_robotX, m_robotY, m_targetAngle, m_velocity,
            m_error, m_leftDesiredVel, m_rightDesiredVel, m_kP, m_kD, m_turn, m_lastError;
    private int m_lookaheadIndex, m_robotIndex, m_lookahead;

    public FalconTrajectoryFollower(ArrayList<TrajectoryPoint> traj, int lookahead, boolean goingForwards, double kP, double kD) {
        m_trajectory = traj;
        m_lookahead = lookahead;
        m_goingForwards = goingForwards;
        m_robotIndex = 0;
        m_kP = kP;
        m_kD = kD;
        m_lookaheadIndex = 0;
        m_lastError = 0;
    }

    public void calculate(double robotX, double robotY, double robotTheta, double dT) {
        m_robotX = robotX;
        m_robotY = robotY;
        robotTheta = Pathfinder.boundHalfDegrees(robotTheta);
        // Get point closest to the robot, based off the last point to the robot was closest to
        m_robotSegment = getClosestSegment(m_robotX, m_robotY, m_trajectory, m_robotIndex, 3);
        m_robotIndex = m_trajectory.indexOf(m_robotSegment);
        m_lookaheadIndex = m_robotIndex + m_lookahead;
        if (m_lookaheadIndex > m_trajectory.size() - 1) {
            m_lookaheadIndex = m_trajectory.size() - 1;
        }
        m_lookaheadSegment = m_trajectory.get(m_lookaheadIndex);
        m_velocity = m_lookaheadSegment.velocity;
        m_targetAngle = m_lookaheadSegment.theta;
        if (!m_goingForwards) {
            robotTheta += 180;
            m_velocity = -m_velocity; 
        }
        m_error = Pathfinder.boundHalfDegrees(m_targetAngle - robotTheta);
        m_turn = m_error * m_kP + (m_error - m_lastError)/dT * m_kD;
        m_leftDesiredVel = m_velocity - m_turn;
        m_rightDesiredVel = m_velocity + m_turn;
    }

    public boolean isFinished() {
        return m_trajectory.size() - 3 < m_robotIndex;
    }

    public double getLeftVelocity() {
        return m_leftDesiredVel;
    }

    public double getRightVelocity() {
        return m_rightDesiredVel;
    }

    public static TrajectoryPoint getClosestSegment(double x, double y, ArrayList<TrajectoryPoint> trajectory, int index, int range) {
        double min = 1000000;
        int counter = index - range;
        int max = index + range;
        if (max > trajectory.size() - 1) {
            max = trajectory.size() - 1;
        }
        TrajectoryPoint seg;
        TrajectoryPoint closestSeg = trajectory.get(0);
        double dist;
        while (counter != max) {
            if (counter < 0) {
                counter = 0;
            }
            seg = trajectory.get(counter);
            dist = NerdyMath.distanceFormula(x, y, seg.x, seg.y);
            if (dist < min) {
                min = dist;
                closestSeg = seg;
            }
            counter += 1;
        }
        return closestSeg;
    }
}

