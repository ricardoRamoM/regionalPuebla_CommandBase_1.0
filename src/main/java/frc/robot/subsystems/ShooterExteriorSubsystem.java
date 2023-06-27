package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterExteriorSubsystem extends SubsystemBase{
    byte idMotorExternoShooterDer = 9;
    byte idMotorExternoShooterIzq = 10;
  
    WPI_TalonSRX motorExternoShooterDer = new WPI_TalonSRX(idMotorExternoShooterDer);
    WPI_TalonSRX motorExternoShooterIzq = new WPI_TalonSRX(idMotorExternoShooterIzq);
    MotorControllerGroup motoresExternosShooter = new MotorControllerGroup(motorExternoShooterDer, motorExternoShooterIzq);
   
    
    public ShooterExteriorSubsystem() {
        motorExternoShooterIzq.setInverted(false);
        motorExternoShooterDer.setInverted(!motorExternoShooterIzq.getInverted());
    }


    @Override
    public void periodic() {
    }

    public void setMotoresInternosShootSpeed(double speed){
        motoresExternosShooter.set(speed);
        SmartDashboard.putNumber("Motor Ext Speed", speed);
    }
}
