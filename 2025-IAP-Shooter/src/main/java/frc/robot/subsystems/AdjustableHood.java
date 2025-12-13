// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.LimitSwitchConfig;
import com.revrobotics.spark.config.LimitSwitchConfig.Type;
import com.revrobotics.spark.config.SoftLimitConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AdjustableHood extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  public SparkLimitSwitch FWDLimit;
  public SparkLimitSwitch REVLimit;
  private final SparkMax hoodGear = new SparkMax(22, MotorType.kBrushless);
  private SparkClosedLoopController PIDController;
  private RelativeEncoder rel_encoder;
  private double input;
  private double currentPos;
  private double setpoint;
  private boolean enableTeleop;
  private boolean homedStartup=false;


  

  public AdjustableHood() {

    this.PIDController = hoodGear.getClosedLoopController();
    this.rel_encoder = hoodGear.getEncoder();
    SparkMaxConfig config = new SparkMaxConfig();
    SoftLimitConfig softLimitConfig = new SoftLimitConfig();
    LimitSwitchConfig limitSwitchConfig = new LimitSwitchConfig();
    limitSwitchConfig.forwardLimitSwitchType(Type.kNormallyClosed);
    limitSwitchConfig.reverseLimitSwitchType(Type.kNormallyClosed);
    limitSwitchConfig.forwardLimitSwitchEnabled(true);
    limitSwitchConfig.reverseLimitSwitchEnabled(true);
    

    config.closedLoop.pid(
    .01, //p
    0, //i
    0 //d
    );

    config.closedLoop.maxMotion
       .maxVelocity(5000) //in rpm
       .maxAcceleration(4400) // in rpm/s
       .allowedClosedLoopError(1);

    config.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder);

    softLimitConfig.forwardSoftLimitEnabled(true); //enables the forward soft limit
    softLimitConfig.reverseSoftLimitEnabled(true); //enables the reverse soft limit
    softLimitConfig.forwardSoftLimit(0);
    softLimitConfig.reverseSoftLimit(5);

    REVLimit = hoodGear.getReverseLimitSwitch();
    FWDLimit = hoodGear.getForwardLimitSwitch();

    if (downLimitSwitch()) {
      this.rel_encoder.setPosition(0);
      homedStartup = true;
    }
  }


  

  public boolean downLimitSwitch() {
    // Query some boolean state, such as a digital sensor.
    return REVLimit.isPressed();
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
  
  public Command state1() {
    return runOnce(
        () -> {
          if (this.homedStartup){
          //L1 height is inches
          //setting the height to be 10 inches 
          setpoint = 1;
          PIDController.setReference(setpoint * 25, SparkMax.ControlType.kMAXMotionPositionControl);
          //System.out.println("Elevator L1");
          //https://docs.revrobotics.com/revlib/spark/closed-loop/position-control-mode
          }
        });
  }
  public Command state2() {
    return runOnce(
        () -> {
          if (this.homedStartup){
          //L1 height is inches
          //setting the height to be 10 inches 
          setpoint = 2;
          PIDController.setReference(setpoint * 25, SparkMax.ControlType.kMAXMotionPositionControl);
          //System.out.println("Elevator L1");
          //https://docs.revrobotics.com/revlib/spark/closed-loop/position-control-mode
          }
        });
  }
  
  public Command homeHood() {
  return this.runOnce(() -> {
    if (homedStartup) {
      PIDController.setReference(setpoint, SparkMax.ControlType.kMAXMotionPositionControl);
    } else {
      hoodGear.set(-0.1);
    }
  });
  }
  public Command resetEncoder() {
    return this.runOnce(() -> {
      //System.out.println("Elevator reset encoder");
      homedStartup = true;
      rel_encoder.setPosition(0);
    });   
  }

}