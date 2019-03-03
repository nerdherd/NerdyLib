/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.statespace;

/**
 * Add your docs here.
 */
public class SSMotionProfile {

    private double m_vMax, m_aMax, m_xi, m_xf, m_vi, m_vf, m_xa, m_ta, m_xc,
                    m_tc, m_xd, m_td;
    private boolean m_isTriangle;

    public SSMotionProfile(double vMax, double aMax, double xi, double xf, 
                            double vi, double vf) {

        m_vMax = vMax;
        m_aMax = aMax;
        // If going negative direction
        if (xf - xi < 0) {
            m_vMax = -m_vMax;
            m_aMax = -m_aMax;
        }
        m_xi = xi;
        m_xf = xf;
        m_vi = vi;
        m_vf = vf;

        m_isTriangle = (m_xf - m_xi) < (m_vMax * m_vMax / m_aMax);

        if (m_isTriangle) {
            m_vMax = Math.sqrt(m_aMax * (m_xf - m_xi));
        }

        m_ta = (m_vMax - m_vi) / m_aMax;
        m_xa = m_vi * m_ta + 0.5 * m_aMax * m_ta * m_ta;
        m_td = (m_vMax - m_vf) / m_aMax;
        m_xd = m_vMax * m_td - 0.5 * m_aMax * m_td * m_td;
        m_xc = (m_xf - m_xi) - m_xa - m_xd;
        m_tc = m_xc / m_vMax;

        System.out.println((0.5 * m_ta * (m_vMax - m_vi)) + (m_tc * m_vMax) + (0.5 * m_td * (m_vMax - m_vf)));
        System.out.println(m_xi);
        System.out.println(m_xf);
        System.out.println(m_isTriangle);
    }

    public double getPosAtTime(double t) {
        if (t >= m_tc + m_ta + m_td) {
            return m_xf;
        } else if (t >= m_ta + m_tc) {
            double dt = t - m_ta - m_tc;
            return m_xi + m_xa + m_xc + m_vMax * dt - 0.5 * m_aMax * dt * dt;
        } else if (t >= m_ta) {
            return m_xi + m_xa + (t - m_ta) * m_vMax;
        } else if (t >= 0) {
            return m_xi + m_vi * t + 0.5 * m_aMax * t * t;
        } else {
            return m_xi;
        }
    }

    public double getVelAtTime(double t) {
        if (t >= m_tc + m_ta + m_td) {
            // Finished
            return m_vf;
        } else if (t >= m_ta + m_tc) {
            // Deceleration
            double dt = t - m_ta - m_tc;
            return m_vMax - m_aMax * dt;
        } else if (t >= m_ta) {
            // Cruise zone
            return m_vMax;
        } else if (t >= 0) {
            // Acceleration
            return m_vi + m_aMax * t;
        } else {
            // Start
            return m_vi;
        }
    }

}
