// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  private SparkMax shooterWheel;
  private RelativeEncoder shooterEncoder;
  private DigitalInput beamBreak;
  /** Creates a new ExampleSubsystem. */
  public Shooter() {
    shooterWheel = new SparkMax(30, MotorType.kBrushless);
    shooterEncoder = shooterWheel.getEncoder();  
    shooterEncoder.setPosition(0);
    SparkMaxConfig config = new SparkMaxConfig();
    beamBreak=new DigitalInput(0);

  }

  public void feed(){
    shooterWheel.set(0.25);
  }
  public void stopFeed(){
    shooterWheel.set(0.25);
  }
  public boolean getBeamBreak(){
    return beamBreak.get();
  }
  
  public double getFlywheelVelocity(){
    return shooterEncoder.getVelocity();
  }

  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */

        });
  }
  public void index(){

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
