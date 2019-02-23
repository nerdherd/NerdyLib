/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.logging;

import badlog.lib.BadLog;
import badlog.lib.DataInferMode;

/**
 * Add your docs here.
 */
public class SubscribedLoggable implements Loggable {

    private String m_name;
    private DataInferMode m_dataInferMode;

    public SubscribedLoggable(String name, DataInferMode dataInferMode) {
        m_name = name;
        m_dataInferMode = dataInferMode;
    }

    public SubscribedLoggable(String name) {
        this(name, DataInferMode.DEFAULT);
    }

    @Override
    public void initLoggingData() {
        BadLog.createTopicSubscriber(m_name, "ul", m_dataInferMode);
    }

    public void publish(String data) {
        BadLog.publish(m_name, data);
    }

    public void publish(double data) {
        BadLog.publish(m_name, data);
    }

}
