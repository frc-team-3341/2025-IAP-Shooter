// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class feedWheel extends SubsystemBase {
  /** Creates a new feedWheel. */
  public feedWheel() {
    // Use addRequirements() here to declare subsystem dependencies.
    public final WPI_TalonSRX feedwheel = new WPI_TalonSRX(Constants.BallHandlerPorts.FeedwheelPort);
    // Sets up the controller
    private PIDController pid = new PIDController(kp, ki, kd, period);
    // Sets the speed needed for the wheel
    private double ticksToRPM = 600/(Constants.encoderTicks);
    private double speed = Constants.midSpeed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
