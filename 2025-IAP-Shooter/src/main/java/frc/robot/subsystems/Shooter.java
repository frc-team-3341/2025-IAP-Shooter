// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  private SparkMax shooterWheel;
  private SparkMax feedWheel;
  private RelativeEncoder shooterEncoder;
  private RelativeEncoder feedEncoder;
  private DigitalInput beamBreak;
  private DigitalInput beamBreak2;
  private SparkClosedLoopController feedWheeelController;
  private SparkClosedLoopController shootWheeelController;
  /*public final WPI_TalonSRX flywheel = new WPI_TalonSRX(4);
  public final WPI_TalonSRX feedwheel = new WPI_TalonSRX(3);*/
  public double feedSpeed=0;
  public double shootSpeed=0;
  /** Creates a new ExampleSubsystem. */
  public Shooter() {
    shooterWheel = new SparkMax(3, MotorType.kBrushless);
    feedWheeelController = this.feedWheel.getClosedLoopController();
    shooterEncoder = shooterWheel.getEncoder();  
    shooterEncoder.setPosition(0);
    SparkMaxConfig fconfig = new SparkMaxConfig();

    feedWheel = new SparkMax(2, MotorType.kBrushless);
    shootWheeelController=this.shooterWheel.getClosedLoopController();
    feedEncoder = shooterWheel.getEncoder();  
    feedEncoder.setPosition(0);
    SparkMaxConfig sconfig = new SparkMaxConfig();
    
    beamBreak=new DigitalInput(0);
    beamBreak2 =new DigitalInput(1);

    /** Creates a new BallShooter. */
    /*flywheel.configFactoryDefault();
    feedwheel.configFactoryDefault();
    flywheel.setInverted(false);
    flywheel.setNeutralMode(NeutralMode.Coast);
    flywheel.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);*/

    sconfig.closedLoop.pid(
    .01, //p
    0, //i
    0 //d
    );
    feedWheel.configure(fconfig,ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    shooterWheel.configure(fconfig,ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

  }

  public Command feed() {
    return runOnce(
        () -> {
          shooterWheel.set( -0.1);
        });
  }
  public Command stopFeed() {
    return runOnce(
        () -> {
          feedWheel.set( 0);
        });
  }
  public Command shoot() {
    return runOnce(
        () -> {
          // shooterWheel.set(0.5);
          feedWheeelController.setReference(5, ControlType.kPosition);
        });
  }
  public Command stopShoot() {
    return runOnce(
        () -> {
          shooterWheel.set(0);
        });
  }
  public Command feedFromBeam() {
    return runOnce(
        () -> {
          feedWheel.set(0.2);
        });
  }
  public Command StopMotors() {
    return runOnce(
        () -> {
          feedWheel.set(0);
          shooterWheel.set(0);
        });
  }
  public Command beamBreakfly(){
    return runOnce(()->{
      shootWheeelController.setReference(4000, SparkMax.ControlType.kVelocity);
      if(shooterEncoder.getPosition()>3900 && shooterEncoder.getVelocity()<4100){
        feedWheeelController.setReference(5, ControlType.kPosition);
      }
      else{
        feedWheel.set(0);
      }
    });
  }

  public Command beamBreakFly() {
    if(beamBreak1()) {
      return runOnce(
      () -> {
      feedWheel.set(0.5);
      });
    }
    else {
      return runOnce(
        () -> {
          feedWheel.set(0);
        });
    }
  }

  public double getShooterVelocity(){
    return shooterEncoder.getVelocity();
  }

  public boolean beamBreak1(){
    return beamBreak.get();
  }

  public boolean beamBreak2(){
    return beamBreak2.get();
  }

  public double getShooterSpeed(){
    return shooterEncoder.getVelocity();
  }

//---------------------------------------------------------------------
/*
  public Command shoot() {
    return runOnce(
        () -> {
          flywheel.set(ControlMode.PercentOutput, 0.5);
        });
  }
  public Command feed() {
    return runOnce(
        () -> {
          feedwheel.set(ControlMode.PercentOutput, 0.25);
        });
  }
  public Command stopShoot() {
    return runOnce(
        () -> {
          flywheel.set(ControlMode.PercentOutput, 0);
        });
  }
  public Command stopFeed() {
    return runOnce(
        () -> {
          feedwheel.set(ControlMode.PercentOutput, 0);
        });
  }
*/  
//-------------------------------------------------------------------------------------------------

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
    double rpm = SmartDashboard.getNumber("Shooter Speed", 0);

    shootWheeelController.setReference(rpm, SparkMax.ControlType.kVelocity);
    feedWheel.set(0.5);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
