/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.statespace;

import com.nerdherd.lib.motor.single.SingleMotorTalonSRX;

import Jama.Matrix;

/**
 * Add your docs here.
 */
public class SSTalonSRX extends SingleMotorTalonSRX {

    public DiscreteSSGains gains;
    private DiscreteSSGainsGroup m_gainsGroup;
    public Matrix u, y, xHat, r;

    public SSTalonSRX(int kTalonID, String name, boolean inversion, boolean sensorPhase,
        DiscreteSSGainsGroup gains, Matrix x0, Matrix u0) {
        
        super(kTalonID, name, inversion, sensorPhase);

        this.m_gainsGroup = gains;
        this.gains = this.m_gainsGroup.getGains(0);

        this.xHat = x0;
        this.y = new Matrix(this.gains.q, 1);
        this.u = u0;
    }

    public SSTalonSRX(int kTalonID, String name, boolean inversion, boolean sensorPhase,
        DiscreteSSGainsGroup gains, Matrix x0, double u0) {

        this(kTalonID, name, inversion, sensorPhase, gains, x0, JamaUtils.matrixFromDouble(u0));
    }

    public SSTalonSRX(int kTalonID, String name, boolean inversion, boolean sensorPhase,
        DiscreteSSGains gains, Matrix uMin, Matrix uMax, Matrix x0, Matrix u0) {

        this(kTalonID, name, inversion, sensorPhase, new DiscreteSSGainsGroup(gains), x0, u0);
    }

    public SSTalonSRX(int kTalonID, String name, boolean inversion, boolean sensorPhase,
        DiscreteSSGains gains, double uMin, double uMax, Matrix x0, double u0) {

        this(kTalonID, name, inversion, sensorPhase, new DiscreteSSGainsGroup(gains), x0, JamaUtils.matrixFromDouble(u0));
    }

    public void setGains(int index) {
        this.gains = this.m_gainsGroup.getGains(index);
    }

    @Override
    public void periodic() {
        this.updateObserver();
        this.setVoltage(this.u.get(0, 0));
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
        this.u = JamaUtils.boundMatrix(
            this.gains.K.times(this.r.minus(this.xHat)).times(-1).plus(
            this.gains.Kff.times(r.minus(this.gains.A.times(this.r)))), 
            this.gains.u_min, this.gains.u_max);
        this.r = r;
    }

    public void update() {
        this.update(this.r);
    }

}
