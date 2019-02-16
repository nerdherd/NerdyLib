/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.misc;

import java.io.File;
import java.util.function.Supplier;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;

/**
 * Add your docs here.
 */
public class NerdyBadlog {

  private static final int kMaxNumFiles = 971;
  private static final String kDefaultPath = "/home/lvuser/logs/";

  public static BadLog log;
  private static Notifier logger;

  // TODO: adjust path so that there's path, filename, and adds a number depending on how many pre-existing logs there are
  // TODO: also add a thing to check whether a directory exists because Badlog dies if the flash drive isn't there
  public static void init(String directory, String filename, Loggable... toLog) {
    // Check that the directory exists
    String pathToUse;
    File dir = new File(directory);
    if (!(dir.isDirectory())) {
      pathToUse = kDefaultPath;
    } else {
      pathToUse = directory;
    }

    int fileNumber = 0;
    for (int i = 0; i < kMaxNumFiles; i++) {
      File file = (new File(pathToUse + filename + String.valueOf(i) + ".csv"));
      if (!file.exists() && !file.isDirectory()) {
        fileNumber = i;
        break;
      }
    }
    log = BadLog.init(pathToUse + filename + String.valueOf(fileNumber) + ".csv");
    createTopic("Time", () -> Timer.getFPGATimestamp());
    for (Loggable loggableItem : toLog) {
      loggableItem.initLoggingData();
    }
    log.finishInitialization();
  }

  public static void initAndLog(String directory, String filename, double period, Loggable... toLog) {
    init(directory, filename, toLog);
    logger = new Notifier(() -> {
      NerdyBadlog.log();
    });
    logger.startPeriodic(period);
  }

  public static void log() {
    log.updateTopics();
    log.log();
  }

  public static void createTopic(String name, Supplier<Double> supplier) {
    BadLog.createTopic(name, "ul", supplier);
  }

}
