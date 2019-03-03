/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.statespace;

import com.nerdherd.lib.logging.NerdyBadlog;
import com.nerdherd.lib.motor.single.SingleMotorTalonSRX;
import com.nerdherd.lib.motor.single.mechanisms.StaticFrictionMechanism;

import Jama.Matrix;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class SSTalonSRXPos extends StaticFrictionMechanism {

    public DiscreteSSGains gains;
    private DiscreteSSGainsGroup m_gainsGroup;
    public Matrix u, y, xHat, r;
    private boolean m_observerEnabled;

    public SSTalonSRXPos(int kTalonID, String name, boolean inversion, boolean sensorPhase,
        DiscreteSSGainsGroup gains, Matrix x0, Matrix u0) {
        
        super(kTalonID, name, inversion, sensorPhase);

        this.m_gainsGroup = gains;
        this.gains = this.m_gainsGroup.getGains(0);

        this.xHat = x0;
        this.r = xHat;
        this.y = new Matrix(this.gains.q, 1);
        this.u = u0;

        this.m_observerEnabled = false;

        super.m_staticFF = 0;
    }

    public void configObserver(boolean enabled) {
        this.m_observerEnabled = enabled;
    }

    public SSTalonSRXPos(int kTalonID, String name, boolean inversion, boolean sensorPhase,
        DiscreteSSGainsGroup gains, Matrix x0, double u0) {

        this(kTalonID, name, inversion, sensorPhase, gains, x0, JamaUtils.matrixFromDouble(u0));
    }

    public SSTalonSRXPos(int kTalonID, String name, boolean inversion, boolean sensorPhase,
        DiscreteSSGains gains, Matrix x0, Matrix u0) {

        this(kTalonID, name, inversion, sensorPhase, new DiscreteSSGainsGroup(gains), x0, u0);
    }

    public SSTalonSRXPos(int kTalonID, String name, boolean inversion, boolean sensorPhase,
        DiscreteSSGains gains, Matrix x0, double u0) {

        this(kTalonID, name, inversion, sensorPhase, new DiscreteSSGainsGroup(gains), x0, JamaUtils.matrixFromDouble(u0));
    }

    public void setGains(int index) {
        this.gains = this.m_gainsGroup.getGains(index);
    }

    @Override
    public void resetEncoder() {
        super.resetEncoder();
        this.r = new Matrix(this.gains.n, 1);
        if (m_observerEnabled) {
            this.xHat = new Matrix(this.gains.n, 1);
        }
    }

    @Override
    public void periodic() {
        if (m_observerEnabled) {
            this.updateObserver();
        } else {
            this.xHat.set(0, 0, this.getPosition() / this.gains.C.get(0, 0));
            this.xHat.set(1, 0, this.getVelocity() / this.gains.C.get(1, 1));
        }
        if (!JamaUtils.isApproximatelyZero(this.r.minus(this.xHat), 0.01)) {
            this.setVoltageWithFF(this.u.get(0, 0));
        }
        SmartDashboard.putBoolean("isClose", JamaUtils.isApproximatelyZero(this.r.minus(this.xHat), 0.01));
    }

    public void updateObserver() {
        if ((this.gains.isAugmented && this.gains.n == 3) || (!this.gains.isAugmented && this.gains.n == 2)) {
            this.y.set(0, 0, this.getPosition());
            this.y.set(1, 0, this.getVelocity());
        } else {
            this.y.set(0, 0, this.getVelocity());
        }

        /*
        x_hat[k+1] = Ax_hat[k] + Bu[k] + L(y[k] - y_hat[k])
        x_hat[k+1] = Ax_hat[k] + Bu[k] + L(y[k] - C(x_hat[k]))
        x_hat[k+1] = Ax_hat[k] + Bu[k] + Ly[k] - LCx_hat[k]
        x_hat[k+1] = (A - LC)x_hat[k] + Bu[k] + Ly[k]
        */

        // (A - LC)x_hat[k]
        this.xHat = (gains.A.minus(gains.L.times(gains.C)).times(this.xHat))
            // Bu[k]
            .plus(gains.B.times(this.u)
            // Ly[k]
            .plus(gains.L.times(this.y)));
    }

    public void update(Matrix r) {
        // u = -K*(r-x) + Kff * (nextR - A*currentR)
        // or u = K * (x-r) + Kff * (nextR - A*currentR) for if 
        // you care about not doing 8 million matrix operations
        this.u = JamaUtils.boundMatrix(
            this.gains.K.times(this.r.minus(this.xHat))
            .plus(
            this.gains.Kff.times(r.minus(this.gains.A.times(this.r)))), 
            this.gains.u_min, this.gains.u_max);
        this.r = r;
    }

    public void update() {
        this.update(this.r);
    }

    @Override
    public void reportToSmartDashboard() {
        super.reportToSmartDashboard();
        for (int i = 0; i < this.xHat.getRowDimension(); i++) {
            SmartDashboard.putNumber(m_name + " x_hat item " + i, this.xHat.get(i, 0));
        }
        for (int i = 0; i < this.r.getRowDimension(); i++) {
            SmartDashboard.putNumber(m_name + " r item " + i, this.r.get(i, 0));
        }
        for (int i = 0; i < this.u.getRowDimension(); i++) {
            SmartDashboard.putNumber(m_name + " u item " + i, this.u.get(i, 0));
        }
    }

    @Override
    public void initLoggingData() {
        super.initLoggingData();
        // for (int i = 0; i < this.xHat.getRowDimension(); i++) {
        //     NerdyBadlog.createTopic(m_name + "/x_hat_item_" + i, () -> this.xHat.get(0, i));
        // }
    }

    @Override
    public double getFFIfMoving() {
        return 0;
    }

    @Override
    public double getFFIfNotMoving(double error) {
        return Math.signum(error) * m_staticFF;
    }

}
