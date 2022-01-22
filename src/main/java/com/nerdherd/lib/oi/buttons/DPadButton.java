package com.nerdherd.lib.oi.buttons;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Button;

public class DPadButton extends Button {
    
    GenericHID joystick;
    Direction direction;

    public DPadButton(GenericHID joystick, Direction direction) {
        this.joystick = joystick;
        this.direction = direction;
    }

    public static enum Direction {
        UP(0), RIGHT(90), DOWN(180), LEFT(270);

        int direction;

        private Direction(int direction) {
            this.direction = direction;
        }
    }

    public boolean get() {
        int dpadValue = joystick.getPOV();
        return (dpadValue == direction.direction) 
            || (dpadValue == (direction.direction + 45) % 360) 
            || (dpadValue == (direction.direction + 315) % 360);
    }

}
