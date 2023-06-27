package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MunecaSubsystem extends SubsystemBase{
    
    byte idCajaShooterAtras = 7;
    byte idCajaShooterAdelante = 8;

    CANSparkMax motorCajaShooterAdelante = new CANSparkMax(idCajaShooterAdelante, MotorType.kBrushless);
    CANSparkMax motorCajaShooterAtras = new CANSparkMax(idCajaShooterAtras, MotorType.kBrushless);
    MotorControllerGroup motoresCajaShooter = new MotorControllerGroup(motorCajaShooterAdelante, motorCajaShooterAtras);
  
    RelativeEncoder encoderCajaShootAdel;
    RelativeEncoder encoderCajaShootAtras;
  
    double valorEncoderCajaAdel;
    double valorEncoderCajaAtras;

    public void getEncodersMuneca(){
        valorEncoderCajaAdel = -encoderCajaShootAdel.getPosition();
        valorEncoderCajaAtras = -encoderCajaShootAtras.getPosition();
    }
  
    public MunecaSubsystem() {
        encoderCajaShootAdel = motorCajaShooterAdelante.getEncoder();
        encoderCajaShootAtras = motorCajaShooterAtras.getEncoder();

        motorCajaShooterAdelante.setIdleMode(IdleMode.kBrake);
        motorCajaShooterAdelante.setIdleMode(IdleMode.kBrake);

    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        SmartDashboard.putNumber("valorEncoderCajaAdelante ", valorEncoderCajaAdel);
        SmartDashboard.putNumber("valorEncoderCajaAtras ", valorEncoderCajaAtras);
    }

    


    public void setMunecaSpeed(double speed){
        motoresCajaShooter.set(speed);
    }
    
}
