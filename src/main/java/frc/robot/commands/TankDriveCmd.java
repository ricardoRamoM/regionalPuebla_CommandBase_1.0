// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ExampleSubsystem;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;


/** An example command that uses an example subsystem. */
public class TankDriveCmd extends CommandBase {

  private final DriveSubsystem driveSubsystem;

  /*Se usa supplier porque son valores que no son estáticos, sino que cada vez que lo 
   * llames necesitas un valor distinto y cambia constantemente
  */
  private final Supplier<Double> velocidadDer, velocidadIzq;
  

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public TankDriveCmd(DriveSubsystem driveSubsystem, //
      Supplier<Double> velocidadIzq, Supplier<Double> velocidadDer) {
        
    this.driveSubsystem = driveSubsystem;
    this.velocidadDer = velocidadDer;
    this.velocidadIzq = velocidadIzq;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveSubsystem);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("TankDriveCmd.initialize()");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {


    double realTime_velocidadDer = velocidadDer.get();
    double realTime_velocidadIzq = velocidadIzq.get();
    DriveSubsystem.setMotoresVelocidadTanque(realTime_velocidadIzq, realTime_velocidadDer);
    //DriveSubsystem.moverChasis(realTime_velocidadDer_Giro, realTime_velocidadIzq_AdelAtras);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("TankDriveCmd.end()");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
