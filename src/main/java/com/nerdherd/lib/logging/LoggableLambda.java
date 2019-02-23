/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.logging;

import java.util.function.Supplier;

import com.nerdherd.lib.logging.Loggable;

/**
 * Add your docs here.
 */
public class LoggableLambda implements Loggable {

  private Supplier<Double> m_lambda;
  private String m_name;

  public LoggableLambda(String name, Supplier<Double> lambda) {
    m_name = name;
    m_lambda = lambda;
  }

  @Override
  public void initLoggingData() {
    NerdyBadlog.createTopic(m_name, m_lambda);
  }
}
