// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.*;
import frc.robot.Subsystems.Drivetrain;
import frc.robot.Subsystems.PID;

import static frc.robot.Constants.motorControllers.*;

/**
 *
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public static double startTime;
  public static double time;
  
  @Override
  public void robotInit() {
   leftMotor2.follow(leftMotor1);
   rightMotor2.follow(rightMotor1);
   rightMotor1.setInverted(true);
   rightMotor2.setInverted(true);
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {
    PID.limeLight();
  }

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    Drivetrain.tankDrive();
  }
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {
  }
}
