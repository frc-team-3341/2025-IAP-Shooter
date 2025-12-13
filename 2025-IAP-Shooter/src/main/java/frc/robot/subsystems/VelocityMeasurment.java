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


public class VelocityMeasurment extends SubsystemBase {
  // Create digital inputs on pins 0 & 1
  private final DigitalInput beamBreakOne = new DigitalInput(0); 
  private final DigitalInput beamBreakTwo = new DigitalInput(1);

  // Create Asynchonous Interupts
  private final AtomicBoolean interuptOneTriggered = new AtomicBoolean(false);
  private final AtomicBoolean interuptTwoTriggered = new AtomicBoolean(false);
  
  // Time variables
  private long timeOne;
  private long timeTwo;
  private long timeDifference;
  private double velocity; 

  // Distance between beam breaks in millimeters
  private final double distanceBetweenBeams = 0.5; 

  private final AsynchronousInterrupt asynchronousInterruptOne;
  private final AsynchronousInterrupt asynchronousInterruptTwo;

  // Logging variables
  private ShuffleboardTab tab = Shuffleboard.getTab("Beam Breaks");
  private GenericEntry beamOneBroken = tab.add("Beam one broken? ", false).getEntry();
  private GenericEntry beamTwoBroken = tab.add("Beam two broken? ", false).getEntry();
  private GenericEntry velocityEntry = tab.add("Velocity (m/s)", 0).getEntry();

  public VelocityMeasurment(double distanceBetweenBeams) {
    asynchronousInterruptOne = new AsynchronousInterrupt(beamBreakOne, (rising, falling) -> {
      if (falling) {
        timeOne = System.currentTimeMillis();
        interuptOneTriggered.set(true);
      }
    });
    asynchronousInterruptOne.setInterruptEdges(false, true);
    asynchronousInterruptOne.enable();

    asynchronousInterruptTwo = new AsynchronousInterrupt(beamBreakTwo, (rising, falling) -> {
      if (falling) {
        timeTwo = System.currentTimeMillis();
        interuptTwoTriggered.set(true);
      }
    });
    asynchronousInterruptTwo.setInterruptEdges(false, true);
    asynchronousInterruptTwo.enable();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    synchronized (this) {
      if (interuptOneTriggered.getAndSet(false)) {
        beamOneBroken.setBoolean(true);
        if (interuptTwoTriggered.getAndSet(false)) {
          beamTwoBroken.setBoolean(true);

          // Calculate velocity
          timeDifference = timeTwo - timeOne; // in milliseconds
          double timeDifferenceInSeconds = timeDifference / 1000.0; // Convert to seconds
          velocity = distanceBetweenBeams / timeDifferenceInSeconds; // Velocity in m/s
          velocityEntry.setDouble(velocity);

          // Reset beam states
          beamOneBroken.setBoolean(false);
          beamTwoBroken.setBoolean(false);
        }
      }
    }
  }
  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}

