// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.Pair;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import java.util.ArrayList;
import java.util.List;

import com.revrobotics.CANSparkMax;

//import com.pathplanner.lib.server.PathPlannerServer;

public class Robot extends TimedRobot {
  public static final ArrayList<Pair<Runnable, Double>> periodics = new ArrayList<>();
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  @Override
  public void robotInit() {
    DriverStation.silenceJoystickConnectionWarning(true); // false
  //  PathPlannerServer.startServer(5811);
    DataLogManager.start();
    m_robotContainer = new RobotContainer();

    // try {
    //   CameraServer.startAutomaticCapture();
    // } catch (VideoException e) {
    //   Logger.l("Unable to start automatic capture for CameraServer!");
    // }

    // initialization should have finished, so register periodics
    for (var period : periodics) {
      addPeriodic(period.getFirst(), period.getSecond());
    }
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run(); // unremark after tests are done
    m_robotContainer.robotPeriodic();
  }

  @Override
  public void disabledInit() {
    m_robotContainer.disabledInit();
  }

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    m_robotContainer.teleopInit();
  }

  @Override
  public void teleopPeriodic() {
    m_robotContainer.teleopPeriodic();
  }

  @Override
  public void teleopExit() {}

/*  @Override
   public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {} 

  @Override
  public void testExit() {}  */

  @Override
  public void simulationInit() {
    DriverStation.silenceJoystickConnectionWarning(true);
  }

  int testSelector;
  //boolean selected[] = { false, false, false, false, false };
  boolean buttonTestNextServiced;
  boolean buttonTestPrevServiced;
  Joystick testStick;
  JoystickButton buttonTestNext;
  JoystickButton buttonTestPrev;
  JoystickButton buttonTestZeroEncoder;
  List<CANSparkMax> driveMotors;

  public void testInit() {
    testSelector = 0;
    //selected[testSelector] = true;
    testStick = new Joystick(2);
    buttonTestNext = new JoystickButton(testStick, 4);
    buttonTestPrev = new JoystickButton(testStick, 3);
    buttonTestZeroEncoder = new JoystickButton(testStick, 5);
    driveMotors = m_robotContainer.getSystems().getDrivebase().getMotors();
  }
  

  public void testPeriodic() {
    System.out.println("getY() = " + testStick.getY() + "    getAngle()" + m_robotContainer.drivebase.getAngle(testSelector));
    if(buttonTestNext.getAsBoolean()) {
      if(!buttonTestNextServiced) {
        //selected[testSelector] = false;
        testSelector++;
        if(testSelector > 7) {
          testSelector = 0;
        }
        //selected[testSelector] = true;
        buttonTestNextServiced = true;
      }
    } else {
      buttonTestNextServiced = false;
    }
  
    if(buttonTestPrev.getAsBoolean()) {
      if(!buttonTestPrevServiced) {
        //selected[testSelector] = false;
        testSelector--;
        if(testSelector < 0) {
          testSelector = 7;
        }
        //selected[testSelector] = true;
        buttonTestPrevServiced = true;
      }
    } else {
      buttonTestPrevServiced = false;
    }

    CANSparkMax driveMotor;

    //if(buttonTestZeroEncoder.getAsBoolean()) {
      /*SwerveModule swerveModule;
      switch (testSelector) {
        case 1:
          swerveModule = m_swerve.getFrontRight();
          break;
        case 2:
          swerveModule = m_swerve.getFrontLeft();
          break;
        case 3:
          swerveModule = m_swerve.getBackRight();
          break;
        default:
          swerveModule = m_swerve.getBackLeft();
          break;    
      }
      swerveModule.getTurningEncoder().setPositionToAbsolute();
    } */

    driveMotor = driveMotors.get(testSelector);
    double driveSpeed = testStick.getY();
    //double turnSpeed = testStick.getX();
  /*
    switch(testSelector) {
      case 1:
        m_swerve.getFrontRight().getDriveMotor().set(driveSpeed);
        m_swerve.getFrontRight().getTurningMotor().set(turnSpeed);
//        System.out.println("testSelector = FL");
        break;
      case 2:
        m_swerve.getFrontLeft().getDriveMotor().set(driveSpeed);
        m_swerve.getFrontLeft().getTurningMotor().set(turnSpeed);
//        System.out.println("testSelector = FR");
        break;
      case 3:
        m_swerve.getBackLeft().getDriveMotor().set(driveSpeed);
        m_swerve.getBackLeft().getTurningMotor().set(turnSpeed);
//        System.out.println("testSelector = BL");
        break;
      case 4:
        m_swerve.getBackRight().getDriveMotor().set(driveSpeed);
        m_swerve.getBackRight().getTurningMotor().set(turnSpeed);
//        System.out.println("testSelector = BR");
        break;
    } */

    driveMotor.set(driveSpeed);
    
    //dashboardUpdate(testSelector);
  }
}
