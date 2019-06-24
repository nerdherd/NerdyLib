/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.trajectory;

import java.util.ArrayList;

import com.nerdherd.lib.misc.NerdyMath;
import com.nerdherd.lib.trajectory.geometry.Arc2D;
import com.nerdherd.lib.trajectory.geometry.Pose2D;
import com.nerdherd.lib.trajectory.geometry.Pose2DWithCurvature;
import com.nerdherd.lib.trajectory.geometry.QuinticHermiteSpline;
import com.nerdherd.lib.trajectory.geometry.TrajectoryPoint2D;

/**
 * Add your docs here.
 */
public class Trajectory {

    private ArrayList<QuinticHermiteSpline> splines;
    private ArrayList<Arc2D> arcs;
    private ArrayList<TrajectoryPoint2D> points;
    private double m_acceleration, m_startVelocity, m_endVelocity, m_centripetalAcceleration, m_dt, m_cruiseVelocity;


    public Trajectory(double acceleration, double startVelocity, double endVelocity, double cruiseVelocity, double centripetalAcceleration, double dt, Pose2D... controlPoints) {
        m_acceleration = acceleration;
        m_startVelocity = startVelocity;
        m_endVelocity = endVelocity;
        m_centripetalAcceleration = centripetalAcceleration;
        m_dt = dt;
        m_cruiseVelocity = cruiseVelocity;
        splines = new ArrayList<>();
        arcs = new ArrayList<>();
        points = new ArrayList<>();
        for (int i = 0; i < controlPoints.length - 1; i++){
            splines.add(new QuinticHermiteSpline(controlPoints[i], controlPoints[i+1]));
            splines.get(i).arcParameterize(0, 0.01, 0.075);
            arcs.addAll(splines.get(i).arcParameterizedSpline);
        }
    }

    public void generateMotionProfileFirstPass() {
        double vel, lastVel, dist, currentCentripAccel, accel, deltaTheta;
        int index = 0;
        lastVel = m_startVelocity;
        // Pose2D pose = new Pose2D(arcs.get(0).start.x, arcs.get(0).start.y, arcs.get(0).start.theta);
        // for (Arc2D arc : arcs) {
        Arc2D arc = arcs.get(index);
        Pose2DWithCurvature pose = arc.start;
        Pose2DWithCurvature lastPose = arc.start;
        while (NerdyMath.distanceFormula(pose, arcs.get(arcs.size()-1).end) >= 0.01) {
            vel = lastVel + m_acceleration * m_dt;
            currentCentripAccel = vel * vel * arc.averageCurvature;
            if (currentCentripAccel > m_centripetalAcceleration) {
                vel = Math.sqrt(m_centripetalAcceleration/arc.averageCurvature);
            }
            if (vel > m_cruiseVelocity) {
                vel = m_cruiseVelocity;
            }
            accel = (vel - lastVel)/m_dt;
            dist = lastVel * m_dt + 0.5 * accel * m_dt * m_dt;
            // if (deltaTheta < Math.abs(arc.start.theta - arc.end.theta)) {
            //     pose = arc.advanceAlongArc(deltaTheta);
            //     points.add(new TrajectoryPoint2D(pose, accel, vel));
            // }
            // else {
            //     dist = dist - arc.length;
            //     index++;
            //     arc = arcs.get(index);
            //     while ()
            // }
            // while (dist > 0) {
                if (dist > arc.length) {
                    dist -= (lastPose.theta - arc.end.theta)/arc.averageCurvature;
                    lastPose = arc.end;
                    index ++;
                    arc = arcs.get(index);
                } else {
                    pose = arc.advanceByDistance(lastPose, dist);
                    points.add(new TrajectoryPoint2D(pose, accel, vel));
                    lastPose = pose;
                }
            // }
            lastVel = vel;
        }
    }
}

