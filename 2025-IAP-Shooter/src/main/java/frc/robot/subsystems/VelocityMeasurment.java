// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.AsynchronousInterrupt;
import edu.wpi.first.wpilibj.DigitalInput;

import java.util.concurrent.atomic.AtomicBoolean;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

@SuppressWarnings("unused")
public class VelocityMeasurment extends SubsystemBase {
  /**Note:
   * Their are multiple copies of each declaration because eventually their will be two beam breaks.
   */
  // Create digital inputs on pins 0 & 1
  private final DigitalInput beamBreakOne = new DigitalInput(0);
  // private DigitalInput beamBreakTwo = new DigitalInput(1);
  // Create Asynchonous Interupts
  private final AtomicBoolean interuptOneTriggered = new AtomicBoolean(false);
  //private final AtomicBoolean interuptTwoTriggered = new AtomicBoolean(false);

  private final AsynchronousInterrupt asynchronousInterruptOne;
  //private final AsynchronousInterrupt asynchronousInterruptOne;
  private ShuffleboardTab tab = Shuffleboard.getTab("Beam Breaks");
  private GenericEntry beamBroken =
      tab.add("Beam broken? ", false)
         .getEntry();

  
  public VelocityMeasurment() {
    asynchronousInterruptOne = new AsynchronousInterrupt(beamBreakOne, (rising, falling) -> {
      if (rising) {
        interuptOneTriggered.set(true);
      }
    });
    asynchronousInterruptOne.setInterruptEdges(true, false);
    asynchronousInterruptOne.enable();
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if (interuptOneTriggered.getAndSet(false)){
      beamBroken.setBoolean(true);
    }
    
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
