package com.nerdherd.lib.misc;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoChooser {

    protected SendableChooser<StartingPosition> startingPositionChooser;
    protected SendableChooser<Double> directionChooser;
    public AutoChooser() {
        startingPositionChooser = new SendableChooser<>();
        startingPositionChooser.setDefaultOption("Center", StartingPosition.CENTER);
        startingPositionChooser.addOption("Left", StartingPosition.LEFT);
        startingPositionChooser.addOption("Right", StartingPosition.RIGHT);
        directionChooser = new SendableChooser<>();
        directionChooser.setDefaultOption("Forwards", 0.0);
        directionChooser.addOption("Backwards", 180.0);
        // Add additional choosers based on what the game needs
        SmartDashboard.putData(startingPositionChooser);
        SmartDashboard.putData(directionChooser);
    }

    public double getDirection() {
        return directionChooser.getSelected();
    }

    public StartingPosition getStartingPosition() {
        return startingPositionChooser.getSelected();
    }

    public CommandGroup getSelectedAuto() {
        return new CommandGroup();
        // replace with auto selection logic
    }
    
    protected enum StartingPosition {
        CENTER, LEFT, RIGHT;
    }

    
}

