package com.swervedrivespecialties.examples.mk3testchassis;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
    @SuppressWarnings("unused")
    private final RobotContainer container = new RobotContainer();

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }
}
