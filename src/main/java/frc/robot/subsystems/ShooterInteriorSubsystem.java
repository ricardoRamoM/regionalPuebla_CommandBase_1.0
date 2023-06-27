package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterInteriorSubsystem extends SubsystemBase{
    byte idMotorInternoShooterIzq = 5;
    byte idMotorInternoShooterDer = 6;

    CANSparkMax motorInternoShooterIzq = new CANSparkMax(idMotorInternoShooterIzq, MotorType.kBrushless);
    CANSparkMax motorInternoShooterDer = new CANSparkMax(idMotorInternoShooterDer, MotorType.kBrushless);
 
    
    public ShooterInteriorSubsystem() {
        motorInternoShooterDer.setIdleMode(IdleMode.kBrake);
        motorInternoShooterIzq.setIdleMode(IdleMode.kBrake);
    }


    @Override
    public void periodic() {
    }

    public void setMotoresInternosShootSpeed(double speed){
        motorInternoShooterDer.set(speed);
        SmartDashboard.putNumber("Motor Int Speed", speed);
    }
}
