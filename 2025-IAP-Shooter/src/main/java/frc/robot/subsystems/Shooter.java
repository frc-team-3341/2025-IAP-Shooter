// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.config.ClosedLoopConfig;
import edu.wpi.first.wpilibj.DigitalInput;
//--------------------------------------------------
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.math.controller.BangBangController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
//-----------------------------------------------------------

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  //private SparkMax shooterWheel;
  //private SparkMax feedWheel;
  private RelativeEncoder shooterEncoder;
  private DigitalInput beamBreak;
  private BangBangController bang = new BangBangController();
  public final WPI_TalonSRX flywheel = new WPI_TalonSRX(4);
  public final WPI_TalonSRX feedwheel = new WPI_TalonSRX(3);
  public double feedSpeed=0;
  public double shootSpeed=0;
  /** Creates a new ExampleSubsystem. */
  public Shooter() {
    /*shooterWheel = new SparkMax(30, MotorType.kBrushless);
    shooterEncoder = shooterWheel.getEncoder();  
    shooterEncoder.setPosition(0);
    SparkMaxConfig config = new SparkMaxConfig();*/
    beamBreak=new DigitalInput(0);
      /** Creates a new BallShooter. */
    flywheel.configFactoryDefault();
    feedwheel.configFactoryDefault();
    flywheel.setInverted(false);
    flywheel.setNeutralMode(NeutralMode.Coast);
    flywheel.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
     // Use addRequirements() here to declare subsystem dependencies.

  }

/* public void feed(){
    feedWheel.set(0.1);
  }
  public void stopFeed(){
    feedWheel.set(0);
  }
  public void shoot(){
    shooterWheel.set(1);
  }
  public void stopShoot(){
    shooterWheel.set(0);
  }*/


  public boolean getBeamBreak(){
    return beamBreak.get();
  }
  
  public double getShooterVelocity(){
    return shooterEncoder.getVelocity();
  }

  /**
   * Example command factory method.
   *
   * @return a command
   */

  public Command shoot() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
          flywheel.set(ControlMode.PercentOutput, 0.5);
        });
  }
  public Command feed() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
          feedwheel.set(ControlMode.PercentOutput, 0.25);
        });
  }
  public Command stopShoot() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
          flywheel.set(ControlMode.PercentOutput, 0);
        });
  }
  public Command stopFeed() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
          feedwheel.set(ControlMode.PercentOutput, 0);
        });
  }

  public void index(){
    if (getBeamBreak()){

    }
  }
  public void startWheel(){

  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
