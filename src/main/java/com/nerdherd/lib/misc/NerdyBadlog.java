/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.misc;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;

/**
 * Add your docs here.
 */
public class NerdyBadlog {

  public static BadLog log;
  private static Notifier logger;

  // TODO: adjust path so that there's path, filename, and adds a number depending on how many pre-existing logs there are
  // TODO: also add a thing to check whether a directory exists because Badlog dies if the flash drive isn't there
  public static void init(String path, Loggable... toLog) {
    log = BadLog.init(path);
    BadLog.createTopic("Time", "s", () -> Timer.getFPGATimestamp(), "hide", "delta", "xaxis");
    for (Loggable loggableItem : toLog) {
      loggableItem.initLoggingData();
    }
    log.finishInitialization();
  }

  public static void initAndLog(String path, double period, Loggable... toLog) {
    init(path, toLog);
    logger = new Notifier(() -> {
      NerdyBadlog.log();
    });
    logger.startPeriodic(period);
  }

  public static void log() {
    log.updateTopics();
    log.log();
  }

}
